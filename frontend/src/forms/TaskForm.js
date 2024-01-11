import React, { useState } from 'react';

const CREATE_TASK_STR = 'Créer la tâche';
const ADD_NEW_TASK_PROP_STR = 'Ajouter une propriété';
const DELETE_PROP_STR = 'Retirer la propriété';

/**
 * Form to create a new task.
 * @returns {Element} - The form to create a new task.
 */
const TaskForm = ({ onCreate }) => {

    const [task, setTask] = useState({
        title: '',
        description: '',
        taskProperties: [{ propertyValue: { value: '', property: { name: '' } } }]
    });

    /**
     * Handles the form submission to create a new task.
     * @param event - The form submission event.
     * @returns {Promise<any>} - The promise returned by the API call to create the task.
     */
    const handleSubmit = async (event) => {
        event.preventDefault();
        const filledTaskProperties = task.taskProperties.filter(tp =>
            tp.propertyValue.property.name.trim() !== '' && tp.propertyValue.value.trim() !== ''
        );

        const taskDataToSend = {
            ...task,
            taskProperties: filledTaskProperties
        };

        try {
            onCreate(taskDataToSend);
        } catch (error) {
            console.log('Erreur lors de la création de la tâche', error);
            throw error;
        }
    };

    /**
     * Handles modifications of a task property (name or value).
     * @param index - Index of the task property to modify
     * @param propertyField - The field to modify (name or value)
     * @param value - The new value of the modified field
     */
    const handleTaskPropertyChange = (index, propertyField, value) => {
        const updatedTaskProperties = task.taskProperties.map((taskProperty, i) => {
            if (i === index) {
                switch (propertyField) {
                    case 'name':
                        return {
                            ...taskProperty,
                            propertyValue: {
                                ...taskProperty.propertyValue,
                                property: {
                                    ...taskProperty.propertyValue.property,
                                    name: value
                                }
                            }
                        };
                    case 'value':
                        return {
                            ...taskProperty,
                            propertyValue: {
                                ...taskProperty.propertyValue,
                                value: value
                            }
                        };
                    default:
                        return {...taskProperty};
                }
            }
            return taskProperty;
        });
        setTask({ ...task, taskProperties: updatedTaskProperties });
    };


    /**
     * Adds a new property to the task.
     */
    const addTaskProperty = () => {
        const newProperty = { propertyValue: { value: '', property: { name: '' } } };
        setTask({ ...task, taskProperties: [...task.taskProperties, newProperty] });
    };

    /**
     * Deletes a property from the task.
     * @param {number} index - Index of the property to delete.
     */
    const removeTaskProperty = index => {
        setTask({
            ...task,
            taskProperties: task.taskProperties.filter((_, i) => i !== index)
        });
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                value={task.title}
                onChange={e => setTask({...task, title: e.target.value})}
                placeholder="Titre"
            />
            <textarea
                value={task.description}
                onChange={e => setTask({...task, description: e.target.value})}
                placeholder="Description"
            />
            {task.taskProperties.map((property, index) => (
                <div key={index}>
                    <input
                        type="text"
                        value={property.propertyValue.property.name}
                        onChange={e => handleTaskPropertyChange(index, 'name', e.target.value)}
                        placeholder="Nom de la propriété"
                    />
                    <input
                        type="text"
                        value={property.propertyValue.value}
                        onChange={e => handleTaskPropertyChange(index, 'value', e.target.value)}
                        placeholder="Valeur"
                    />
                    <button type="button" onClick={() => removeTaskProperty(index)}>{DELETE_PROP_STR}</button>
                </div>
            ))}

            <button type="button" onClick={addTaskProperty}>{ADD_NEW_TASK_PROP_STR}</button>
            <button type="submit">{CREATE_TASK_STR}</button>
        </form>
    );
};

export default TaskForm;
