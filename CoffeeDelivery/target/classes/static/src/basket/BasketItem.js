import React from 'react';

export default function BasketItem({data}) {
    function deleteBasket(id) {
        $.ajax({
           url: `/basket/delete?id=${id}`,
           type: "POST",
           success: () => location.reload()
        });
    }

    return (
        <tr>
            <td>{data.id}</td>
            <td>{data.name}</td>
            <td>{data.price}</td>
            <td>{data.count}</td>
            <td className="main__basket-panel--action"><a onClick={() => deleteBasket(data.id)}>-</a></td>
        </tr>
    )
}