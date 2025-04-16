import React from 'react';

export default function BasketItem({data}) {
    let ids = data.ids;

    function deleteBasket(sequence) {
        $.ajax({
           url: `/basket/delete?id=${ids[sequence]}`,
           type: "DELETE",
           success: () => {
               location.reload()
               ids.length--;
           },
           error: () => {
               deleteBasket(sequence--);
           }
        });
    }

    function buyBasketProduct(id) {
        $.ajax({
            url: `/basket/buy?id=${id}`,
            type: "POST",
            success: (data, status, headers) => {
                window.location.href = headers.getResponseHeader("Location");
            }
        });
    }

    return (
        <tr>
            <td>{data.index}</td>
            <td>{data.name}</td>
            <td>{data.price*data.count}</td>
            <td>{data.count}</td>
            <td className="action-button action-button__delete"><a onClick={() => deleteBasket(ids.length-1)}>-</a></td>
            <td className="action-button action-button__buy"><a onClick={() => buyBasketProduct(data.productId)}>v</a></td>
        </tr>
    )
}