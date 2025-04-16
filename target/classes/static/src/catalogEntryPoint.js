import ReactDOM from 'react-dom';
import {createRoot} from "react-dom/client";
import React, {useState} from 'react';
import CatalogList from "./src/catalog/CatalogList";
import CatalogItemsNotFoundItem from "./src/catalog/CatalogItemsNotFoundItem";

const rootElement = document.getElementById("root");
const infoRootElement = document.getElementById("info-header");

const root = createRoot(rootElement);
const infoRoot = createRoot(infoRootElement);

export function searchItems() {
    const urlParam = $(".nav__search-panel--input").val();
    $(document).ready(() => {
        $.ajax({
            url: `/api/search?value=${urlParam}`,
            type: "GET",
            success: (data) => {
                const resultData = [];

                for(const key in data) {
                    resultData.push({
                        id: key,
                        name: data[key].name,
                        price: data[key].price
                    });
                }

                infoRoot.render(null);
                root.render(<CatalogList data={resultData} />);
            },
            error: (data) => {
                root.render(null);
                infoRoot.render(<CatalogItemsNotFoundItem name={urlParam} />);
            }
        });
    });
}

window.searchItems = searchItems;

