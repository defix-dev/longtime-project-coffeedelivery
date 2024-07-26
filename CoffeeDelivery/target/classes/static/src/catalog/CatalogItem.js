import React, { useState, useEffect, useRef } from 'react';
import defaultLogo from '../public/logo.png';

export default function CatalogItem({ data }) {
    const [icon, setIcon] = useState(defaultLogo);
    const priceRef = useRef(null);
    const imageRef = useRef(null);

    useEffect(() => {
        async function fetchIcon() {
            const image = await import((`../public/catalog_icons/product_${data.id}.png`));
            setIcon(image.default);
        }
        fetchIcon();
    }, []);

    useEffect(() => {
        if (!priceRef.current || !imageRef.current) return;

        const updatePosition = () => {
            const imageBounds = imageRef.current.getBoundingClientRect();
            const containerBounds = imageRef.current.parentElement.getBoundingClientRect();

            const bottomOffset = containerBounds.top - imageBounds.top - priceRef.current.style.top;
            const rightOffset = containerBounds.right - imageBounds.right - priceRef.current.style.right;

            priceRef.current.style.position = 'absolute';
            priceRef.current.style.top = `${bottomOffset}px`;
            priceRef.current.style.right = `${rightOffset}px`;
            priceRef.current.style.transform = 'rotate(45deg)';
        };

        updatePosition();

        window.addEventListener('resize', updatePosition);
        return () => {
            window.removeEventListener('resize', updatePosition);
        };
    }, [icon]);

    function addProductToBasket(id) {
        $.ajax({
            url: `/catalog/add_to_basket?id=${id}`,
            type: "POST",
            success: () => alert(`Товар с айди [${id}] успешно добавлен в корзину ;)`)
        })
    }

    return (
        <div className="catalog-item" id={"product_" + data.id}>
            <img className="catalog-item__preview" src={icon} alt={data.name} ref={imageRef}/>
            <p className="catalog-item__name">{data.name}</p>
            <a className="catalog-item__basket-btn" onClick={() => addProductToBasket(data.id)}>В корзину</a>
            <p className="catalog-item__price" ref={priceRef}>{data.price}</p>
        </div>
    );
}