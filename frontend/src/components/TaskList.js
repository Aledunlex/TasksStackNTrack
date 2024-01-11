import React from 'react';
import TaskComponent from './TaskComponent';

const NO_TASKS_FOUND_STR = "Il n'y a pas de tâches à afficher.";

/**
 * Display a list of tasks
 * @param tasks List of tasks to display
 * @param onDelete Callback when a task is deleted
 * @param onUpdate Callback when a task is updated
 * @returns {Element} JSX code for the TaskList component
 */
const TaskList = ({ tasks, onDelete, onUpdate }) => {
    if (tasks.length === 0) {
        return <p>{NO_TASKS_FOUND_STR}</p>;
    }

    return (
        <div>
            <ul>
                {tasks.map(task => (
                    <li key={task.id}>
                        <TaskComponent task={task} onDelete={onDelete} onUpdate={onUpdate} />
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TaskList;
