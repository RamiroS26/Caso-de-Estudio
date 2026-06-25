(function () {
    var form = document.querySelector("form[data-dirty-warning]");
    var dirty = false;

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
