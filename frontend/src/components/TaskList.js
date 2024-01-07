import React from 'react';
import TaskComponent from './TaskComponent';

const TaskList = ({ tasks, onDelete, onUpdate }) => {
    if (tasks.length === 0) {
        return <p>Il n'y a pas de tâches à afficher.</p>;
    }

    return (
        <div>
            <ul>
                {tasks.map(task => (
                    <li key={task.id}>
                        <TaskComponent task={task} />
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TaskList;
