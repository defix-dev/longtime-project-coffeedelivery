import React from 'react';

export default function BasketItem({data}) {
    function deleteBasket(id) {
        $.ajax({
           url: `/basket/delete?id=${id}`,
           type: "DELETE",
           success: () => location.reload()
        });
    }

    return (
        <tr>
            <td>{data.id}</td>
            <td>{data.name}</td>
            <td>{data.price}</td>
            <td>{data.count}</td>
            <td className="action-button action-button__delete"><a onClick={() => deleteBasket(data.id)}>-</a></td>
            <td className="action-button action-button__buy"><a>v</a></td>
        </tr>
    )
}