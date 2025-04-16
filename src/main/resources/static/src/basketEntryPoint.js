import ReactDOM from 'react-dom';
import {createRoot} from "react-dom/client";
import React, {useState} from 'react';
import CatalogList from "./src/catalog/CatalogList";
import BasketList from "./src/basket/BasketList";

const root = createRoot(document.getElementById("root"));

$(document).ready(() => {
    $.ajax({
        url: "/api/v1/baskets",
        type: "GET",
        success: (data) => {
            const resultData = {}

            let index = 1;
            for(const key in data) {
                const itemId = data[key].productId;
                if(resultData.hasOwnProperty(itemId)) {
                    resultData[itemId].count++;
                    resultData[itemId].ids.push(data[key].id);
                }
                else {
                    resultData[itemId] = {
                        name: data[key].productName,
                        price: data[key].productPrice,
                        count: 1,
                        ids: [
                            data[key].id
                        ],
                        index: index,
                        productId: data[key].productId
                    };
                    index++;
                }
            }

            console.log(resultData);
            loadBaskets(resultData)
        }
    });
});

function loadBaskets(data) {
    root.render(<BasketList data={data} />)
}