import React, { useState, useEffect } from 'react';
import TaskForm from '../forms/TaskForm';
import TaskList from '../components/TaskList';
import { taskService } from '../services/TaskService';
import { toast } from "react-toastify";

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
            console.error('Erreur lors du chargement des tâches:', error);
        }
    };

    const handleCreateTask = async (newTaskData) => {
        try {
            const newTask = await taskService.createTask(newTaskData);
            setTasks(prevTasks => [newTask, ...prevTasks]);
        } catch (error) {
            console.error('Erreur lors de la création de la tâche', error);
        }
    };

    const handleDeleteTask = async (taskId) => {
        try {
            await taskService.deleteTask(taskId);
            setTasks(prevTasks => prevTasks.filter(task => task.id !== taskId));
        } catch (error) {
            toast.error('Erreur lors de la suppression de la tâche', error);
        }
    }

    const handleUpdateTask = async (updatedData) => {
        try {
            await taskService.updateTask(updatedData);
            setTasks(prevTasks => prevTasks.map(task => task.id === updatedData.id ? updatedData : task));
        } catch (error) {
            toast.error('Erreur lors de la mise à jour de la tâche', error);
        }
    }

    return (
        <div>
            <TaskForm onCreate={handleCreateTask} />
            <TaskList tasks={tasks} onDelete={handleDeleteTask} onUpdate={handleUpdateTask} />
        </div>
    );
};

export default TasksContainer;
