package com.tsnt.services;

import com.tsnt.entities.Task;
import com.tsnt.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Provides services for Task entities (CRUD operations)
 */
@Service
public class TaskService {
  
  /**
   * Repository for Task entities
   */
  private final TaskRepository taskRepository;
  
  /**
   * Creates a new TaskService
   *
   * @param taskRepository Repository for Task entities
   */
  @Autowired
  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }
  
  /**
   * Creates a new Task after checking if it already exists and formatting its title
   *
   * @param task Task to be created
   * @return Created Task
   */
  @Transactional
  public Task createTask(Task task) {
    if (taskRepository.findAll().contains(task))
      throw new IllegalStateException("Task " + task.getTitle() + " already registered");
    
    if (task.getTitle().trim().isEmpty())
      throw new IllegalStateException("Task title cannot be empty");
    
    task.setTitle(
        task.getTitle().trim().substring(0, 1).toUpperCase() + task.getTitle().substring(1)
            .toLowerCase()
            .replaceAll("_", " ")
            .replaceAll("\\s+", " ")
            .replaceAll("([A-Z])", " $1")
            .trim());
    
    return taskRepository.save(task);
  }
  
  /**
   * Gets a Task by its id
   * @param id Id of the Task to be retrieved
   * @return Optional containing the Task if it exists
   */
  @Transactional(readOnly = true)
  public Optional<Task> getTaskById(Long id) {
    return taskRepository.findById(id);
  }
  
  /**
   * Gets all Tasks, ordered by recency
   * @return List of all Tasks, ordered by recency
   */
  @Transactional(readOnly = true)
  public List<Task> getTasksByRecency() {
    return taskRepository.findAllByOrderByCreationDateDesc(null).getContent();
  }
  
  /**
   * Gets all Tasks with a title containing the given String (case-insensitive), ordered by creation date
   * @param title String to be contained in the title of the Tasks to be retrieved (case-insensitive)
   * @return List of all Tasks with a title containing the given String, ordered by creation date
   */
  @Transactional(readOnly = true)
  public List<Task> getTasksByTitleContaining(String title) {
    return taskRepository.findAllByTitleContainingIgnoreCaseOrderByCreationDateDesc(title, null).getContent();
  }
  
  /**
   * Gets all Tasks with a description containing the given String (case-insensitive), ordered by creation date
   * @param description String to be contained in the description of the Tasks to be retrieved (case-insensitive)
   * @return List of all Tasks with a description containing the given String, ordered by creation date
   */
  @Transactional(readOnly = true)
  public List<Task> getTasksByDescriptionContaining(String description) {
    return taskRepository.findAllByDescriptionContainingIgnoreCaseOrderByCreationDateDesc(description, null).getContent();
  }
  
  /**
   * Gets all Tasks with a property whose name contains the given String (case-insensitive), ordered by creation date
   * @param name String to be contained in the name of the properties of the Tasks to be retrieved (case-insensitive)
   * @return List of all Tasks with a property whose name contains the given String, ordered by creation date
   */
  @Transactional(readOnly = true)
  public List<Task> getTasksHavingAPropertyNameContaining(String name) {
    return taskRepository.findAllByTaskPropertiesPropertyValuePropertyNameContainingIgnoreCaseOrderByCreationDateDesc(name, null).getContent();
  }
  
  /**
   * Deletes a Task by its id
   * @param id Id of the Task to be deleted
   */
  @Transactional
  public void deleteTask(Long id) {
    taskRepository.deleteById(id);
  }
  
}
