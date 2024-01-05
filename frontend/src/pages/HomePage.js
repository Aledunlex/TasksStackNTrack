import React, { useState, useEffect } from 'react';
import TaskComponent from '../components/TaskComponent';
import { taskService } from '../services/TaskService';
import TaskForm from "../forms/TaskForm";

const PAGE_TITLE = "Tâches";

/**
 * Page d'accueil de l'application: affiche la liste des tâches
 * @returns {Element} le composant React à afficher
 */
const HomePage = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const tasks = await taskService.retrieveAllTasks();
                setData(tasks);
            } catch (error) {
                console.error('Erreur lors du chargement des tâches:', error);
            }
        };

        fetchTasks();
    }, []);

    /* Si aucune tâche n'a été récupérée, on affiche un message */
    if (data.length === 0) {
        return (
            <div>
                <h1>{PAGE_TITLE}</h1>
                <TaskForm />
                <p>Aucune tâche à afficher</p>
            </div>
        );
    }

    return (
        <div>
            <h1>{PAGE_TITLE}</h1>
            <TaskForm />
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
