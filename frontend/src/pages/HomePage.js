import React from 'react';
import TasksContainer from '../components/TasksContainer';

const PAGE_TITLE = "Tasks Stack 'N Track";
const APP_DESCRIPTION_SHORT_STR = "Une application pour gérer vos tâches (en cours de développement)";

/**
 * Home page of the application
 * @returns {Element} The home page component
 */
const HomePage = () => {

    return (
        <>
            <div className="container my-4">
                <h1 className="display-4 text-center">{PAGE_TITLE}</h1>
                <h4 className="text-center">{APP_DESCRIPTION_SHORT_STR}</h4>
            </div>

            <TasksContainer />
        </>
    );

};

export default HomePage;
