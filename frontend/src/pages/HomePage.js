import React, { useState, useEffect } from 'react';
import TasksContainer from '../components/TasksContainer';

const PAGE_TITLE = "Tâches";

/**
 * Home page of the application
 * @returns {Element} The home page component
 */
const HomePage = () => {

    return (
        <div>
            <h1>{PAGE_TITLE}</h1>
            <TasksContainer />
        </div>
    );
};

export default HomePage;
