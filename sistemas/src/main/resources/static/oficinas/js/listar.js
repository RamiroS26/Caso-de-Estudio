(function () {
    var input = document.querySelector("[data-table-filter]");
    var table = input ? document.getElementById(input.dataset.tableFilter) : null;

    if (!input || !table) {
        return;
    }

    input.addEventListener("input", function () {
        var search = input.value.trim().toLowerCase();
        var rows = table.querySelectorAll("tbody tr");

        rows.forEach(function (row) {
            if (row.querySelector(".empty-state")) {
                return;
            }

            row.hidden = search !== "" && row.textContent.toLowerCase().indexOf(search) === -1;
        });
    });
})();
