import React, { useState, useEffect } from 'react';
import TaskForm from '../forms/TaskForm';
import TaskList from '../components/TaskList';
import { taskService } from '../services/TaskService';
import { toast } from "react-toastify";

const CREATE_SUCCESS_TOAST_STR = "Tâche créée avec succès !";
const UPDATE_SUCCESS_TOAST_STR = "Tâche mise à jour avec succès !";
const DELETE_SUCCESS_TOAST_STR = "Tâche supprimée avec succès !";
const CREATE_ERROR_TOAST_STR = "Erreur lors de la création de la tâche.";
const RETRIEVE_ERROR_TOAST_STR = "Erreur lors du chargement des tâches.";
const UPDATE_ERROR_TOAST_STR = "Erreur lors de la mise à jour de la tâche.";
const DELETE_ERROR_TOAST_STR = "Erreur lors de la suppression de la tâche.";

const TasksContainer = () => {
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        loadTasks().then(() => console.log('Tâches chargées !'));
    }, []);

    const loadTasks = async () => {
        try {
            const tasks = await taskService.retrieveAllTasks();
            setTasks(tasks);
        } catch (error) {
            console.error(error);
            toast.error(RETRIEVE_ERROR_TOAST_STR);
        }
    };

    const handleCreateTask = async (newTaskData) => {
        try {
            const newTask = await taskService.createTask(newTaskData);
            setTasks(prevTasks => [newTask, ...prevTasks]);
            toast.success(CREATE_SUCCESS_TOAST_STR);
        } catch (error) {
            console.error(error);
            toast.error(CREATE_ERROR_TOAST_STR);
        }
    };

    const handleDeleteTask = async (taskId) => {
        try {
            await taskService.deleteTask(taskId);
            setTasks(prevTasks => prevTasks.filter(task => task.id !== taskId));
            toast.success(DELETE_SUCCESS_TOAST_STR);
        } catch (error) {
            console.error(error);
            toast.error(DELETE_ERROR_TOAST_STR);
        }
    }

    const handleUpdateTask = async (updatedData) => {
        try {
            await taskService.updateTask(updatedData);
            setTasks(prevTasks => prevTasks.map(task => task.id === updatedData.id ? updatedData : task));
            toast.success(UPDATE_SUCCESS_TOAST_STR);
        } catch (error) {
            console.error(error);
            toast.error(UPDATE_ERROR_TOAST_STR);
        }
    }

    return (
        <div className="container-fluid my-4">
            <TaskForm onCreate={handleCreateTask}/>
            <div className="mt-5">
                <TaskList tasks={tasks} onDelete={handleDeleteTask} onUpdate={handleUpdateTask}/>
            </div>
        </div>
    );

};

export default TasksContainer;
