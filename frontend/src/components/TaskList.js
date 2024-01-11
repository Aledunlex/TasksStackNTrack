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
        return (
            <div className="alert alert-info" role="alert">
                <i className="bi bi-exclamation-circle">{NO_TASKS_FOUND_STR}</i>
            </div>
        );
    }

    return (
        <div className="row">
            {tasks.map(task => (
                <div key={task.id} className="col-md-6 mb-4">
                    <TaskComponent task={task} onDelete={onDelete} onUpdate={onUpdate} />
                </div>
            ))}
        </div>
    );

};


export default TaskList;
