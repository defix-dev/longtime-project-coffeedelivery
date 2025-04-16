import {createRoot} from "react-dom/client";
import CommentList from "./src/comment/CommentList";
import React from 'react';
import ReactDOM from "react-dom";

$(document).ready(() => {
   loadFeedbacks();
});

const root = createRoot(document.getElementById('root'));

export function loadFeedbacks() {
    $.ajax({
        url: "/api/v1/feedbacks",
        type: "GET",
        success: (data) => {
            const resultData = []

            for(const key in data) {
                resultData.push({
                    key: key,
                    clientName: data[key].clientName,
                    feedback: data[key].feedback
                });
            }

            root.render(<CommentList data={resultData} />)
        },
        error: (data) => {
            console.error(`Error: ${data.responseJSON}`)
        }
    });
}

window.loadFeedbacks = loadFeedbacks;