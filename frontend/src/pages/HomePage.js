import React, { useState, useEffect } from 'react';
import TasksContainer from '../components/TasksContainer';

const PAGE_TITLE = "Tâches";

/**
 * Page d'accueil de l'application: affiche la liste des tâches
 * @returns {Element} le composant React à afficher
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
