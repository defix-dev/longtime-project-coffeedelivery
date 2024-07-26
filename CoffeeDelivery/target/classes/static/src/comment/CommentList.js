import React, { useState, useEffect } from 'react';
import Comment from './Comment';

export default function CommentList({ data }) {
    const [commentsData, setCommentsData] = useState([]);

    useEffect(() => {
        setCommentsData(prevCommentsData => prevCommentsData.concat(data));
    }, [data]);

    return (
        <>
            {commentsData.map((comment, index) => (
                <Comment key={index} data={comment} />
            ))}
        </>
    );
}