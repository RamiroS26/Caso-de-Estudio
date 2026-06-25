(function () {
    var form = document.querySelector("[data-delete-form]");

    if (!form) {
        return;
    }

    form.addEventListener("submit", function (event) {
        if (!window.confirm("Confirmar eliminacion de la oficina?")) {
            event.preventDefault();
        }
    });
})();
