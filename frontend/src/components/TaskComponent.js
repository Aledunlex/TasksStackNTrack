/**
 * Composant React affichant une tâche et ses propriétés
 * @param task la tâche à afficher
 * @returns {JSX.Element} le composant React à afficher
 */
const TaskComponent = ({ task }) => {
    return (
        <div key={task.id}>
            <h2>{task.title}</h2>
            <p>{task.description}</p>
            {task.taskProperties && task.taskProperties.map(taskProperty =>
                <TaskPropertyComponent key={taskProperty.id} taskProperty={taskProperty} />
            )}
        </div>
    );
}

/**
 * Affiche une propriété d'une tâche donnée
 * @param taskProperty
 * @returns {JSX.Element}
 */
const TaskPropertyComponent = ({ taskProperty }) => {
    return (
        <div>
            <PropertyValueComponent key={taskProperty.id} propertyValue={taskProperty.propertyValue}/>
        </div>
    );
}

/**
 * Affiche une propriété et sa valeur pour une tâche donnée
 * @param propertyValue le couple propriété/valeur à afficher
 * @returns {JSX.Element} le composant React à afficher
 */
const PropertyValueComponent = ({ propertyValue }) => {
    const propertyName = propertyValue.property.name;
    return (
        <div>
            <p key={propertyName.id}>{propertyName} : {propertyValue.value}</p>
        </div>
    );
}

export default TaskComponent;