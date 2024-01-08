import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

/**
 * Créer une tâche
 * @param taskData Données de la tâche à créer
 * @returns {Promise<any>} La tâche créée
 */
const createTask = async (taskData) => {
    try {
        const response = await axios.post(`${API_URL}/tasks`, taskData);
        console.log('Tâche créée avec succès !');
        return response.data;
    } catch (error) {
        console.error('Erreur lors de la création de la tâche !', error);
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

/**
 * Mettre à jour une tâche
 * @param taskData Données de la tâche à mettre à jour
 * @returns {Promise<any>} La tâche mise à jour
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
 * Supprimer une tâche
 * @param taskId Id de la tâche à supprimer
 * @returns {Promise<any>} La tâche supprimée
 */
const deleteTask = async (taskId) => {
    try {
        const response = await axios.delete(`${API_URL}/tasks/${taskId}`);
        console.log('Tâche supprimée avec succès !');
        return response.data;
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
