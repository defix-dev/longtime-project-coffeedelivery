import ReactDOM from 'react-dom';
import {createRoot} from "react-dom/client";
import React, {useState} from 'react';
import CatalogList from "./src/catalog/CatalogList";
import BasketList from "./src/basket/BasketList";

const root = createRoot(document.getElementById("root"));

$(document).ready(() => {
    $.ajax({
        url: "/api/get_baskets",
        type: "GET",
        success: (data) => {
            const resultData = []

            for(const key in data) {
                resultData.push({
                    id: data[key].id,
                    name: data[key].productName,
                    price: data[key].productPrice,
                    count: data[key].count
                });
            }

            loadBaskets(resultData)
        }
    });
});

function loadBaskets(data) {
    root.render(<BasketList data={data} />)
}