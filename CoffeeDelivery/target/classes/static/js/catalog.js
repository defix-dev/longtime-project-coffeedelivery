$(document).ready(() => {
    $(".filter-type").click((event) => {
        $.ajax({
            url: "/catalog/change_filter_type",
            type: "POST",
            data: {type: event.target.innerHTML}
        });
    });

    $(".price-input__min-price--input").change((event) => {
        $.ajax({
            url: "/catalog/change_filter_minPrice",
            type: "POST",
            data: {minPrice: event.target.value}
        });
    });

    $(".price-input__max-price--input").change((event) => {
        $.ajax({
            url: "/catalog/change_filter_maxPrice",
            type: "POST",
            data: {maxPrice: event.target.value}
        });
    });
});

let filterOpenState = true;
const filterPanel = document.querySelector(".nav__filter-panel");

function executeFilterButton() {
    if (filterOpenState)
        hide();
    else
        show();

    function hide() {
        filterOpenState = false;
        filterPanel.style.transform = "scaleY(0)";
    }

    function show() {
        filterOpenState = true;
        filterPanel.style.transform = "scaleY(1)";
    }
}

document.querySelector(".search-btn").addEventListener("click", searchItems);
document.querySelector(".filter-btn").addEventListener("click", executeFilterButton);