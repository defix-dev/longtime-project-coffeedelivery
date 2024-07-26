import React from 'react';

export default function Comment({data}) {
    return (
        <div className="feedback-content__item">
            <p className="feedback-content__item--username">{data.clientName}</p>
            <p className="feedback-content__item--feedback">{data.feedback}</p>
        </div>
    )
}