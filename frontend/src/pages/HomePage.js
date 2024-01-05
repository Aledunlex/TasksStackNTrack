import React, { useState, useEffect } from 'react';
import TaskComponent from '../components/TaskComponent';
import axios from 'axios';

const PAGE_TITLE = "Tâches";

/**
 * Page d'accueil de l'application: affiche la liste des tâches
 * @returns {Element} le composant React à afficher
 */
const HomePage = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await axios('http://localhost:8080/api/tasks');
                setData(result.data);
            } catch (error) {
                console.error("Erreur lors de la récupération des tâches:", error);
            }
        };

        fetchData();
    }, []);

    if (data.length === 0) {
        return (
            <div>
                <h1>{PAGE_TITLE}</h1>
                <p>Aucune tâche à afficher</p>
            </div>
        );
    }

    return (
        <div>
            <h1>{PAGE_TITLE}</h1>
            <ul>
                {data.map(task => (
                    <li key={task.id}>
                        <TaskComponent task={task}/>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default HomePage;
