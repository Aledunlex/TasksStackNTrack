import {useState} from "react";

const DELETE_TASK_STR = "Supprimer la tâche";
const APPLY_CHANGES_STR = "Appliquer les modifications";
const DELETE_TASK_PROP_STR = "Retirer la propriété";

/**
 * React component to display a task and its properties
 * @param task the task to display
 * @param onDelete the function to call when the task is deleted
 * @param onUpdate the function to call when the task is updated (title, description, properties)
 * @returns {JSX.Element} the React component to display
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
            <button onClick={() => onDelete(task.id)}>{DELETE_TASK_STR}</button>
            <button onClick={handleUpdateTask}>{APPLY_CHANGES_STR}</button>
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
 * Displays a property for a given task
 * @param taskProperty the task property to display
 * @param onPropertyChange the function to call when the property is updated
 * @param onRemoveProperty the function to call when the property is removed
 * @returns {JSX.Element} the React component to display
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
            <button onClick={() => onRemoveProperty(taskProperty.id)}>{DELETE_TASK_PROP_STR}</button>
        </div>
    );

}

/**
 * Displays a property and its value for a given task
 * @param propertyValue the property/value pair to display
 * @param onValueChange the function to call when the value is updated
 * @returns {JSX.Element} the React component to display
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