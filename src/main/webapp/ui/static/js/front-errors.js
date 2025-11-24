window.onerror = function (message, source, lineno, colno, error) {
    const payload = {
        message: message,
        source: source,
        line: lineno,
        column: colno,
        stack: error && error.stack,
        url: location.href,
        userAgent: navigator.userAgent,
        time: new Date().toISOString()
    };

    fetch('/frontend-error-log', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(payload)
    }).catch(() => {
    });
};

// Errores de promesas no atrapadas
window.addEventListener('unhandledrejection', function (e) {
    fetch('/frontend-error-log', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            message: e.reason && e.reason.message,
            stack: e.reason && e.reason.stack,
            url: location.href,
            userAgent: navigator.userAgent,
            time: new Date().toISOString()
        })
    }).catch(() => {
    });
});