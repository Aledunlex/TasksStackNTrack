package com.tsnt.services;

import com.tsnt.dtos.TaskDto;
import com.tsnt.dtos.TaskPropertyDto;
import com.tsnt.entities.Task;
import com.tsnt.mappers.TaskMapper;
import com.tsnt.mappers.TaskPropertyMapper;
import com.tsnt.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    Task task = new Task(taskDto.getTitle());
    
    // Not saving the mapped task directly here: work-around because TaskProperty wouldn't be saved otherwise
    Task savedTask = taskRepository.save(task);
    Long id = savedTask.getId();
    taskDto.setId(id);
    
    updateTask(taskDto);
    
    return id;
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
  
  // Dans TaskService
  @Transactional
  public TaskDto updateTask(TaskDto taskDto) {
    Task task = taskRepository.findById(taskDto.getId())
        .orElseThrow(() -> new IllegalStateException("Task not found"));
    
    task.setTitle(formatString(taskDto.getTitle()));
    task.setDescription(formatString(taskDto.getDescription()));
    
    if (taskDto.getTaskProperties() != null) {
      for (TaskPropertyDto taskPropertyDto : taskDto.getTaskProperties()) {
        taskPropertyService.updateTaskPropertyFrom(taskPropertyDto, task);
      }
    }
    
    return taskMapper.taskToTaskDto(taskRepository.save(task));
  }
  
  @Transactional
  public void deleteTask(Long id) {
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
  private String formatString(String string) {
    if (string == null) return null;
    return string.substring(0, 1).toUpperCase() +
        string.substring(1).toLowerCase()
            .replaceAll("_", " ")
            .replaceAll("\\s+", " ");
  }
  
}
