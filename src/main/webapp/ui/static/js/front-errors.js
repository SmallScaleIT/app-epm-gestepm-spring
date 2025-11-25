(function () {
    const ENDPOINT = '/frontend-error-log'; // tu endpoint que ya escribe en disco
    // opcional: inyecta session/user desde JSP: window.__APP_SESSION_ID__ = "${session.id}";
    const SESSION_ID = window.__APP_SESSION_ID__ || null;

    function sendLog(payload) {
        try {
            payload.time = new Date().toISOString();
            payload.url = location.href;
            payload.userAgent = navigator.userAgent;
            if (SESSION_ID) payload.sessionId = SESSION_ID;

            const json = JSON.stringify(payload);

            // prefer sendBeacon para que funcione en unload; fallback a fetch
            if (navigator.sendBeacon) {
                try {
                    const blob = new Blob([json], {type: 'application/json'});
                    if (navigator.sendBeacon(ENDPOINT, blob)) return;
                } catch (e) { /* fallthrough a fetch */
                }
            }

            // fallback
            fetch(ENDPOINT, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: json,
                keepalive: true
            }).catch(() => { /* swallow */
            });

        } catch (e) {
            // no hacer nada si fallamos al logear el log
        }
    }

    // 1) Interceptar console.error
    (function () {
        const origConsoleError = console.error;
        console.error = function () {
            try {
                // construir mensaje representativo
                const args = Array.prototype.slice.call(arguments);
                const message = args.map(a => {
                    try {
                        return typeof a === 'object' ? JSON.stringify(a) : String(a);
                    } catch (ex) {
                        return String(a);
                    }
                }).join(' ');

                // si hay un Error object en args, intentar sacar stack
                let stack = null;
                for (const a of args) {
                    if (a && a.stack) {
                        stack = a.stack;
                        break;
                    }
                }

                sendLog({
                    type: 'console-error',
                    message: message,
                    stack: stack,
                    rawArgs: args.slice(0, 5) // evitar payloads enormes
                });
            } catch (e) {
            }
            // llamar al console.error original para mantener behavior
            try {
                origConsoleError.apply(console, arguments);
            } catch (e) {
            }
        };
    })();

    // 2) window.onerror (errores clásicos)
    window.onerror = function (message, source, lineno, colno, error) {
        sendLog({
            type: 'window-error',
            message: String(message),
            source: source,
            lineno: lineno,
            colno: colno,
            stack: error && error.stack
        });
        // return false to let browser handle default
        return false;
    };

    // 3) Unhandled promise rejections
    window.addEventListener('unhandledrejection', function (e) {
        const reason = e.reason;
        sendLog({
            type: 'unhandledrejection',
            message: reason && reason.message ? reason.message : String(reason),
            stack: reason && reason.stack
        });
    });

})();