import React, { useState } from 'react';

/**
 * Formulaire de création d'une nouvelle tâche.
 * @returns {Element} - Le formulaire de création d'une nouvelle tâche.
 */
const TaskForm = ({ onCreate }) => {
    /**
     * L'état de la tâche en cours de création.
     */
    const [task, setTask] = useState({
        title: '',
        description: '',
        taskProperties: [{ propertyValue: { value: '', property: { name: '' } } }]
    });

    /**
     * Gère la soumission du formulaire de création d'une nouvelle tâche.
     * @param event - L'évènement de soumission du formulaire
     * @returns {Promise<any>} - La tâche créée
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
        }
    };

    /**
     * Gère la modification d'un des 2 champs de création d'une propriété pour une nouvelle tâche
     * @param index - L'index de la propriété créée ou modifiée
     * @param propertyField - Le champ modifié ("name" ou "value")
     * @param value - La valeur du champ modifié
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
     * Ajoute une propriété à la tâche.
     */
    const addTaskProperty = () => {
        const newProperty = { propertyValue: { value: '', property: { name: '' } } };
        setTask({ ...task, taskProperties: [...task.taskProperties, newProperty] });
    };

    /**
     * Supprime une propriété de la tâche.
     * @param {number} index - L'index de la propriété à supprimer.
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
                    <button type="button" onClick={() => removeTaskProperty(index)}>Supprimer</button>
                </div>
            ))}

            <button type="button" onClick={addTaskProperty}>Ajouter une propriété</button>
            <button type="submit">Créer la tâche</button>
        </form>
    );
};

export default TaskForm;
