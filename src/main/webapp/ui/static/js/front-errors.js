(function () {
    const ENDPOINT = '/frontend-error-log';

    function safeJson(v) {
        try {
            return JSON.stringify(v);
        } catch (e) {
            return String(v);
        }
    }

    function sendLog(payload) {
        try {
            payload.ts = new Date().toISOString();
            payload.url = location.href;
            payload.userAgent = navigator.userAgent;
            payload.online = navigator.onLine;
            payload.connection = navigator.connection ? {
                effectiveType: navigator.connection.effectiveType,
                downlink: navigator.connection.downlink
            } : null;

            const json = JSON.stringify(payload);
            if (navigator.sendBeacon) {
                try {
                    navigator.sendBeacon(ENDPOINT, new Blob([json], {type: 'application/json'}));
                    return;
                } catch (e) {
                }
            }
            fetch(ENDPOINT, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: json,
                keepalive: true
            }).catch(() => {
            });
        } catch (e) {
        }
    }

    // Helper para extraer info de ProgressEvent (XHR)
    function extractFromProgressEvent(pe) {
        try {
            const t = pe && (pe.target || pe.srcElement);
            if (!t) return {kind: String(pe)};

            // intenta obtener campos seguros y truncados
            const info = {
                eventType: pe.type || null,
                readyState: t.readyState,
                status: t.status,
                statusText: t.statusText,
                responseURL: t.responseURL || t._responseURL || null,
                // response puede ser blob/arraybuffer; leer solo si es string
                responseSnippet: (typeof t.response === 'string') ? t.response.slice(0, 1000) : null,
                responseTextSnippet: (typeof t.responseText === 'string') ? t.responseText.slice(0, 1000) : null,
                // si añadimos nuestra marca _url/_method en wrapper XHR
                loggedUrl: t._loggedUrl || null,
                loggedMethod: t._loggedMethod || null
            };
            return info;
        } catch (e) {
            return {errorWhileExtracting: String(e)};
        }
    }

    // unhandledrejection mejorado
    window.addEventListener('unhandledrejection', function (ev) {
        try {
            const reason = ev.reason;
            if (reason && (reason instanceof ProgressEvent || reason.type === 'error' || reason.target)) {
                const peInfo = extractFromProgressEvent(reason);
                sendLog({type: 'unhandledrejection-progress-event', raw: safeJson(reason), progressInfo: peInfo});
                return;
            }

            // otros tipos (Error, TypeError, DOMException...)
            let message = (reason && reason.message) ? reason.message : String(reason);
            let stack = reason && reason.stack ? reason.stack : null;
            sendLog({type: 'unhandledrejection', message, stack});
        } catch (e) {
            // swallow
        }
    });

    // Intercept XHR para enriquecer ProgressEvent con URL y method
    (function () {
        const _open = XMLHttpRequest.prototype.open;
        const _send = XMLHttpRequest.prototype.send;

        XMLHttpRequest.prototype.open = function (method, url) {
            this._loggedMethod = method;
            this._loggedUrl = url;
            return _open.apply(this, arguments);
        };

        XMLHttpRequest.prototype.send = function (body) {
            // listeners para error/timeout/abort que suelen generar ProgressEvent
            this.addEventListener('error', (ev) => {
                const info = extractFromProgressEvent(ev);
                sendLog({type: 'xhr-error', loggedMethod: this._loggedMethod, loggedUrl: this._loggedUrl, info});
            });
            this.addEventListener('timeout', (ev) => {
                const info = extractFromProgressEvent(ev);
                sendLog({type: 'xhr-timeout', loggedMethod: this._loggedMethod, loggedUrl: this._loggedUrl, info});
            });
            this.addEventListener('abort', (ev) => {
                const info = extractFromProgressEvent(ev);
                sendLog({type: 'xhr-abort', loggedMethod: this._loggedMethod, loggedUrl: this._loggedUrl, info});
            });
            this.addEventListener('load', (ev) => {
                // si status no OK, loguea para inspección
                try {
                    const status = this.status;
                    if (status >= 400) {
                        const info = extractFromProgressEvent(ev);
                        sendLog({
                            type: 'xhr-load-nok',
                            loggedMethod: this._loggedMethod,
                            loggedUrl: this._loggedUrl,
                            status,
                            statusText: this.statusText,
                            info
                        });
                    }
                } catch (e) {
                }
            });

            return _send.apply(this, arguments);
        };
    })();

    // Intercept fetch para logear errores/respuestas no-ok
    if (window.fetch) {
        const _fetch = window.fetch;
        window.fetch = function (input, init) {
            const start = Date.now();
            const method = (init && init.method) || 'GET';
            const url = (typeof input === 'string') ? input : (input && input.url) || null;
            return _fetch.apply(this, arguments)
                .then(res => {
                    const duration = Date.now() - start;
                    if (!res.ok) {
                        // clonar y extraer texto limitado
                        try {
                            res.clone().text().then(text => {
                                sendLog({
                                    type: 'fetch-nok',
                                    method,
                                    url,
                                    status: res.status,
                                    statusText: res.statusText,
                                    durationMs: duration,
                                    responseSnippet: text.slice(0, 1000)
                                });
                            }).catch(() => {
                                sendLog({
                                    type: 'fetch-nok-no-body',
                                    method,
                                    url,
                                    status: res.status,
                                    statusText: res.statusText
                                });
                            });
                        } catch (e) {
                            sendLog({
                                type: 'fetch-nok-ex',
                                method,
                                url,
                                status: res.status,
                                statusText: res.statusText
                            });
                        }
                    }
                    return res;
                }).catch(err => {
                    // fetch falló (p. ej. red, CORS, DNS)
                    sendLog({
                        type: 'fetch-error',
                        method,
                        url,
                        message: err && err.message ? err.message : String(err)
                    });
                    throw err;
                });
        };
    }
})();