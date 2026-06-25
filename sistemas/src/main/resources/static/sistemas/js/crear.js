(function () {
    var textarea = document.querySelector("textarea[data-counter]");
    var counter = textarea ? document.getElementById(textarea.dataset.counter) : null;
    var form = document.querySelector("form[data-dirty-warning]");
    var dirty = false;

    if (textarea && counter) {
        var updateCounter = function () {
            counter.textContent = String(textarea.value.length);
        };

        textarea.addEventListener("input", updateCounter);
        updateCounter();
    }

    if (!form) {
        return;
    }

    form.addEventListener("input", function () {
        dirty = true;
    });

    form.addEventListener("submit", function () {
        dirty = false;
    });

    window.addEventListener("beforeunload", function (event) {
        if (!dirty) {
            return;
        }

        event.preventDefault();
        event.returnValue = "";
    });
})();
