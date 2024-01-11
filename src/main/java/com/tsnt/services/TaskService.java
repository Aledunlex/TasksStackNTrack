package com.tsnt.services;

import com.tsnt.dtos.TaskDto;
import com.tsnt.dtos.TaskPropertyDto;
import com.tsnt.entities.Task;
import com.tsnt.entities.TaskProperty;
import com.tsnt.mappers.TaskMapper;
import com.tsnt.mappers.TaskPropertyMapper;
import com.tsnt.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Provides services for Task entities (CRUD operations)
 */
@Service
public class TaskService {
  
  /**
   * Repository for Task entities
   */
  private final TaskRepository taskRepository;
  
  private final TaskPropertyService taskPropertyService;
  
  private final TaskMapper taskMapper;
  
  private final TaskPropertyMapper taskPropertyMapper;
  
  /**
   * Creates a new TaskService
   *
   * @param taskRepository Repository for Task entities
   */
  @Autowired
  public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, TaskPropertyMapper taskPropertyMapper, TaskPropertyService taskPropertyService) {
    this.taskRepository = taskRepository;
    this.taskMapper = taskMapper;
    this.taskPropertyMapper = taskPropertyMapper;
    this.taskPropertyService = taskPropertyService;
  }
  
  @Transactional
  public Long createTask(TaskDto taskDto) {
    Task task = new Task();

    Task savedTask = taskRepository.save(task);
    Long id = savedTask.getId();
    taskDto.setId(id);
    nullifyZeroIds(taskDto);

    // Handles TaskProperties creation/update/deletion, PropertyValues, Properties... linked to the new Task
    updateTask(taskDto);
    
    return id;
  }

  /**
   * Setting the TaskDto, its TaskProperties, their PropertyValue and their Propertie IDs to null IF they are set to 0
   * checks that they don't already exist will be handled by corresponding services called by updateTask()
   * This is required because some TaskDto (and entities within) could be created with null IDs, or with IDs set to 0
   * @param taskDto TaskDto for which inner IDs should be set to null if they are 0 (TaskProperty,
   *                Property, and PropertyValue objects)
   */
  private static void nullifyZeroIds(TaskDto taskDto) {
    if (taskDto.getTaskProperties() != null) {
      for (TaskPropertyDto taskPropertyDto : taskDto.getTaskProperties()) {
        if (taskPropertyDto.getId() != null && taskPropertyDto.getId() == 0) taskPropertyDto.setId(null);
        if (taskPropertyDto.getPropertyValue().getId() != null && taskPropertyDto.getPropertyValue().getId() == 0)
          taskPropertyDto.getPropertyValue().setId(null);
        if (taskPropertyDto.getPropertyValue().getProperty().getId() != null && taskPropertyDto.getPropertyValue().getProperty().getId() == 0)
          taskPropertyDto.getPropertyValue().getProperty().setId(null);
      }
    }
  }

  @Transactional(readOnly = true)
  public TaskDto getTaskById(Long id) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("Task " + id + " not found"));
    return taskMapper.taskToTaskDto(task);
  }
  
  @Transactional(readOnly = true)
  public List<TaskDto> getTasksByRecency() {
    List<Task> tasks = taskRepository.findAllByOrderByCreationDateDesc(null).getContent();
    return tasks.stream().map(taskMapper::taskToTaskDto).toList();
  }
  
  @Transactional(readOnly = true)
  public List<TaskDto> getTasksByTitleContaining(String title) {
    List<Task> tasks = taskRepository.findAllByTitleContainingIgnoreCaseOrderByCreationDateDesc(title, null).getContent();
    return tasks.stream().map(taskMapper::taskToTaskDto).toList();
  }
  
  @Transactional(readOnly = true)
  public List<TaskDto> getTasksByDescriptionContaining(String description) {
    List<Task> tasks = taskRepository.findAllByDescriptionContainingIgnoreCaseOrderByCreationDateDesc(description, null).getContent();
    return tasks.stream().map(taskMapper::taskToTaskDto).toList();
  }
  
  @Transactional(readOnly = true)
  public List<TaskDto> getTasksHavingAPropertyNameContaining(String name) {
    List<Task> tasks = taskRepository.findAllByTaskPropertiesPropertyValuePropertyNameContainingIgnoreCaseOrderByCreationDateDesc(name, null).getContent();
    return tasks.stream().map(taskMapper::taskToTaskDto).toList();
  }

  /**
   * Updates a Task entity with the provided TaskDto
   * @param taskDto TaskDto to update the Task entity with
   * @return Updated TaskDto
   */
  @Transactional
  public TaskDto updateTask(TaskDto taskDto) {
    Task task = taskRepository.findById(taskDto.getId())
        .orElseThrow(() -> new IllegalStateException("Task not found"));

      formatString(taskDto.getTitle()).ifPresent(task::setTitle);
      formatString(taskDto.getDescription()).ifPresent(task::setDescription);

    Set<Long> existingPropertyIds = new HashSet<>();
    if (taskDto.getTaskProperties() != null) {
      for (TaskPropertyDto taskPropertyDto : taskDto.getTaskProperties()) {
        TaskProperty updatedTaskProperty = taskPropertyService.updateTaskPropertyFrom(taskPropertyDto, task);
        if (updatedTaskProperty != null && updatedTaskProperty.getId() != null) {
          existingPropertyIds.add(updatedTaskProperty.getId());
        }
      }
    }

    task.getTaskProperties().removeIf(property -> property.getId() != null && !existingPropertyIds.contains(property.getId()));

    return taskMapper.taskToTaskDto(taskRepository.save(task));
  }
  
  @Transactional
  public void deleteTask(Long id) {
    if (!taskRepository.existsById(id)) throw new IllegalStateException("Task " + id + " not found");
    taskRepository.deleteById(id);
  }
  
  /**
   * Formats a string to be used as a title or description
   * Capitalizes the first letter, lowercases the rest, replaces underscores with spaces, removes extra spaces
   * and adds spaces before each capital letter
   *
   * @param string String to format
   * @return Formatted string
   */
  protected static Optional<String> formatString(String string) {
    if (string == null || string.isBlank()) return Optional.empty();
    if (string.length() == 1) return Optional.of(string.toUpperCase());
    return Optional.of(string.substring(0, 1).toUpperCase() +
                        string.substring(1).toLowerCase()
                              .replaceAll("_", " ")
                               .replaceAll("\\s+", " "));
  }
  
}
