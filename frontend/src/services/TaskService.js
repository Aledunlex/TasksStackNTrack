import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

/**
 * Créer une tâche
 * @param taskData Données de la tâche à créer
 * @returns {Promise<any>} La tâche créée
 */
const createTask = async (taskData) => {
    console.log('taskData:', taskData);
    try {
        const response = await axios.post(`${API_URL}/tasks`, taskData);
        return response.data;
    } catch (error) {
        console.error('Erreur lors de la création de la tâche:', error);
        throw error;
    }
};

/**
 * Récupérer toutes les tâches
 * @returns {Promise<any>} Les tâches
 */
const retrieveAllTasks = async () => {
    try {
        const response = await axios.get(`${API_URL}/tasks`);
        return response.data;
    } catch (error) {
        console.error('Erreur lors de la récupération des tâches:', error);
        throw error;
    }
}

export const taskService = {
    createTask,
    retrieveAllTasks,
    // updateTask,
    // deleteTask,
};
