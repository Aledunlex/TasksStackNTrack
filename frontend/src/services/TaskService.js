import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

/**
 * Create a task
 * @param taskData Data of the task to create
 * @returns {Promise<any>} The created task
 */
const createTask = async (taskData) => {
    try {
        const response = await axios.post(`${API_URL}/tasks`, taskData);
        console.log('Tâche créée avec succès !');
        const createdId = response.data;
        const createdTask = await axios.get(`${API_URL}/tasks/${createdId}`);
        return createdTask.data;
    } catch (error) {
        console.error('Erreur lors de la création de la tâche !', error);
        throw error;
    }
};

/**
 * Retrieve all tasks
 * @returns {Promise<any>} All tasks
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

/**
 * Update a task
 * @param taskData Data of the task to update
 * @returns {Promise<any>} The updated task
 */
const updateTask = async (taskData) => {
    try {
        const response = await axios.put(`${API_URL}/tasks/${taskData.id}`, taskData);
        console.log('Tâche mise à jour avec succès !');
        return response.data;
    } catch (error) {
        console.error('Erreur lors de la mise à jour de la tâche:', error);
        throw error;
    }
}

/**
 * Delete a task
 * @param taskId Id of the task to delete
 */
const deleteTask = async (taskId) => {
    try {
        await axios.delete(`${API_URL}/tasks/${taskId}`);
        console.log('Tâche supprimée avec succès !');
    } catch (error) {
        console.error('Erreur lors de la suppression de la tâche !', error);
        throw error;
    }
}

export const taskService = {
    createTask,
    retrieveAllTasks,
    updateTask,
    deleteTask,
};
