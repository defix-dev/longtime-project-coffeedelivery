import CatalogItem from "./CatalogItem";
import {useState} from "react";
import React from 'react';

export default function CatalogList({data}) {
    return (
        <>
            {data.map(item => <CatalogItem key={item.id} data={item} />)}
        </>
    );
}