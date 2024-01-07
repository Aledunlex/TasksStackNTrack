import React, { useState, useEffect } from 'react';
import TaskForm from '../forms/TaskForm';
import TaskList from '../components/TaskList';
import { taskService } from '../services/TaskService';

const TasksContainer = () => {
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        loadTasks();
    }, []);

    const loadTasks = async () => {
        try {
            const tasks = await taskService.retrieveAllTasks();
            setTasks(tasks);
        } catch (error) {
            console.error('Erreur lors du chargement des tâches:', error);
        }
    };

    const handleCreateTask = async (newTaskData) => {
        try {
            const newTask = await taskService.createTask(newTaskData);
            console.log("Nouvelle tâche créée: ", newTask); // s'affiche
            setTasks(prevTasks => [newTask, ...prevTasks]);
            console.log("Nouvel état des tâches: ", tasks); // ne s'affiche pas
        } catch (error) {
            console.error('Erreur lors de la création de la tâche:', error);
        }
    };

    const handleDeleteTask = async (taskId) => {
        console.log('taskId:', taskId);
    }

    const handleUpdateTask = async (taskId, updatedData) => {
        console.log('taskToUpdate:', updatedData);
    }

    return (
        <div>
            <TaskForm onCreate={handleCreateTask} onUpdate={handleUpdateTask} />
            <TaskList tasks={tasks} onDelete={handleDeleteTask} onUpdate={handleUpdateTask} />
        </div>
    );
};

export default TasksContainer;
