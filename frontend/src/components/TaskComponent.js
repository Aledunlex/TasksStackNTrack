import {useState} from "react";
import {toast} from "react-toastify";

const DELETE_TASK_STR = "X";
const APPLY_CHANGES_STR = "Appliquer";
const DELETE_TASK_PROP_STR = "X";
const PROPERTY_DELETE_WARNING_STR = "La propriété sera supprimée lors de l'application des modifications";
const CANCEL_UNSAVED_CHANGES_STR = "Annuler";
const UNSAVED_CHANGES_CANCELED_STR = "Modifications non sauvegardées annulées";
const NOTHING_TO_CANCEL_STR = "Aucune modification à annuler";
const ADD_NEW_PROPERTY_STR = "+";

/**
 * React component to display a task and its properties
 * @param task the task to display
 * @param onDelete the function to call when the task is deleted
 * @param onUpdate the function to call when the task is updated (title, description, properties)
 * @returns {JSX.Element} the React component to display
 */
const TaskComponent = ({ task, onDelete, onUpdate }) => {

    const originalTask = task;

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
        toast.warn(PROPERTY_DELETE_WARNING_STR);
        setModifiedTask({ ...modifiedTask, taskProperties: updatedProperties });
    };

    const handleCancelUnsavedChanges = () => {
        const modifiedTaskProperties = modifiedTask.taskProperties.map(taskProperty => {
            return { propertyName: taskProperty.propertyValue.property.name, propertyValue: taskProperty.propertyValue.value };
        });
        const originalTaskProperties = originalTask.taskProperties.map(taskProperty => {
            return { propertyName: taskProperty.propertyValue.property.name, propertyValue: taskProperty.propertyValue.value };
        });
        if (modifiedTask.title === originalTask.title
            && modifiedTask.description === originalTask.description
            && JSON.stringify(modifiedTaskProperties) === JSON.stringify(originalTaskProperties)) {
            toast.info(NOTHING_TO_CANCEL_STR);
            return;
        }
        toast.info(UNSAVED_CHANGES_CANCELED_STR);
        console.log("originalTask", originalTask);
        setModifiedTask(originalTask);
    }

    const handleAddProperty = () => {
        const newProperty = {
            id: Date.now(),  // Will be replaced by the backend
            propertyValue: {
                value: 'Valeur',
                property: {
                    name: 'Nouvelle Propriété'
                }
            }
        };

        setModifiedTask({
            ...modifiedTask,
            taskProperties: [...modifiedTask.taskProperties, newProperty]
        });
    };

    return (
        <div key={modifiedTask.id} className="card mb-3">
            <div className="card-body">
                <div className="d-flex justify-content-between align-items-center">
                    <h2 className="card-title mb-0">{modifiedTask.title}</h2>
                    <div>
                        <button onClick={() => handleCancelUnsavedChanges()}
                                className="btn btn-outline-secondary me-2">{CANCEL_UNSAVED_CHANGES_STR}</button>
                        <button onClick={() => onUpdate(modifiedTask)}
                                className="btn btn-primary me-2">{APPLY_CHANGES_STR}</button>
                        <button onClick={() => onDelete(modifiedTask.id)}
                                className="btn btn-danger">{DELETE_TASK_STR}</button>
                    </div>
                </div>
                <p className="card-text mt-2">{modifiedTask.description}</p>
                {modifiedTask.taskProperties && modifiedTask.taskProperties.map(taskProperty =>
                    <TaskPropertyComponent key={taskProperty.id} taskProperty={taskProperty}
                                           onPropertyChange={handlePropertyChange}
                                           onRemoveProperty={handleRemoveProperty}/>
                )}
                <button onClick={handleAddProperty} className="btn btn-primary">{ADD_NEW_PROPERTY_STR}</button>
            </div>
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
        <div className="d-flex justify-content-between align-items-center mb-3">
            <PropertyValueComponent
                key={taskProperty.propertyValue.id}
                propertyValue={taskProperty.propertyValue}
                onValueChange={(newValue) => {
                    onPropertyChange(taskProperty.id, newValue);
                }}
            />
            <button onClick={() => onRemoveProperty(taskProperty.id)} className="btn btn-outline-secondary ms-2">{DELETE_TASK_PROP_STR}</button>
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

    return (
        <div className="input-group">
            <label className="input-group-text">{propertyValue.property.name} :</label>
            <input
                onChange={e => onValueChange(e.target.value)}
                type="text"
                value={propertyValue.value}
                className="form-control"
            />
        </div>
    );

}

export default TaskComponent;