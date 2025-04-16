import BasketItem from "./BasketItem";
import React from 'react';

export default function BasketList({data}) {
    const isEmpty = Object.keys(data).length === 0;
    return (
        <table>
            <thead>
            <tr>
                <th>Порядковый номер</th>
                <th>Наименование</th>
                <th>Цена (RUB)</th>
                <th>Количество (шт)</th>
                <th className="action-button action-button__delete">Удалить</th>
                <th className="action-button action-button__buy">Купить</th>
            </tr>
            </thead>
            <tbody>
            {!isEmpty && Object.keys(data).map(key => <BasketItem data={data[key]} key={data[key].index} />)}
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