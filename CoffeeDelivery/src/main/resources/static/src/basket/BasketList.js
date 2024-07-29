import BasketItem from "./BasketItem";
import React from 'react';

export default function BasketList({data}) {
    const isEmpty = data.length === 0;
    return (
        <table>
            <thead>
            <tr>
                <th>УИД</th>
                <th>Название</th>
                <th>Цена</th>
                <th>Количество</th>
                <th className="action-button action-button__delete">Удалить</th>
                <th className="action-button action-button__buy">Купить</th>
            </tr>
            </thead>
            <tbody>
            {!isEmpty && data.map(item => <BasketItem data={item} key={item.id} />)}
            {isEmpty && <tr>
                <td>-</td>
                <td>-</td>
                <td>-</td>
                <td>-</td>
                <td>-</td>
                <td>-</td>
            </tr>}
            </tbody>
        </table>
    )
}