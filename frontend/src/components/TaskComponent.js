import {useState} from "react";

/**
 * Composant React affichant une tâche et ses propriétés
 * @param task la tâche à afficher
 * @param onDelete la fonction à appeler lors de la suppression d'une tâche
 * @param onUpdate la fonction à appeler lors de la mise à jour d'une tâche (titre, description, propriétés)
 * @returns {JSX.Element} le composant React à afficher
 */
const TaskComponent = ({ task, onDelete, onUpdate }) => {

    const [modifiedTask, setModifiedTask] = useState(task);

    const handlePropertyChange = (propertyId, newValue) => {
        const updatedProperties = modifiedTask.taskProperties.map(prop => {
            if (prop.id === propertyId) {
                return { ...prop, propertyValue: { ...prop.propertyValue, value: newValue }};
            }
            return prop;
        });
        setModifiedTask({ ...modifiedTask, taskProperties: updatedProperties });
    };

    const handleRemoveProperty = (propertyId) => {
        const updatedProperties = modifiedTask.taskProperties.filter(prop => prop.id !== propertyId);
        setModifiedTask({ ...modifiedTask, taskProperties: updatedProperties });
    };

    const handleUpdateTask = () => {
        onUpdate(modifiedTask);
    };

    return (
        <div key={task.id}>
            <button onClick={() => onDelete(task.id)}>Supprimer la tâche</button>
            <button onClick={handleUpdateTask}>Appliquer les modifications</button>
            <h2>{task.title}</h2>
            <p>{task.description}</p>
            {task.taskProperties && task.taskProperties.map(taskProperty =>
                <TaskPropertyComponent key={taskProperty.id} taskProperty={taskProperty}
                                       onPropertyChange={handlePropertyChange}
                                       onRemoveProperty={handleRemoveProperty} />
            )}
        </div>
    );

}

/**
 * Affiche une propriété d'une tâche donnée
 * @param taskProperty la propriété à afficher
 * @param onPropertyChange la fonction à appeler lors de la mise à jour de la propriété
 * @param onRemoveProperty la fonction à appeler lors de la suppression de la propriété
 * @returns {JSX.Element} le composant React à afficher
 */
const TaskPropertyComponent = ({ taskProperty, onPropertyChange, onRemoveProperty }) => {

    return (
        <div>
            <PropertyValueComponent
                propertyValue={taskProperty.propertyValue}
                onValueChange={(newValue) => {
                    onPropertyChange(taskProperty.id, newValue);
                }}
            />
            <button onClick={() => onRemoveProperty(taskProperty.id)}>Supprimer la propriété</button>
        </div>
    );

}

/**
 * Affiche une propriété et sa valeur pour une tâche donnée
 * @param propertyValue le couple propriété/valeur à afficher
 * @param onValueChange la fonction à appeler lors de la mise à jour de la valeur
 * @returns {JSX.Element} le composant React à afficher
 */
const PropertyValueComponent = ({ propertyValue, onValueChange }) => {

    const handleInputChange = (propertyValue, newValue) => {
        propertyValue.value = newValue;
        onValueChange(newValue);
    };

    return (
        <div>
            <label>{propertyValue.property.name} :</label>
            <input onChange={e => handleInputChange(propertyValue, e.target.value)} type="text" value={propertyValue.value} />
        </div>
    );

}

export default TaskComponent;