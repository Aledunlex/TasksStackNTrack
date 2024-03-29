package com.tsnt.services;

import com.tsnt.dtos.TaskPropertyDto;
import com.tsnt.entities.PropertyValue;
import com.tsnt.entities.Task;
import com.tsnt.entities.TaskProperty;
import com.tsnt.repositories.TaskPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Provides services for task properties (CRUD operations).
 */
@Service
public class TaskPropertyService {
  
  /**
   * The task property repository.
   */
  private final TaskPropertyRepository taskPropertyRepository;
  
  @Autowired
  private PropertyValueService propertyValueService;
  
  /**
   * Creates a new task property service.
   * @param taskPropertyRepository the task property repository
   */
  @Autowired
  public TaskPropertyService(TaskPropertyRepository taskPropertyRepository) {
    this.taskPropertyRepository = taskPropertyRepository;
  }
  
  /**
   * Creates a task property after checking that it does not already exist.
   * @param taskProperty the task property to create
   * @return the created task property
   */
  @Transactional
  public TaskProperty createTaskProperty(TaskProperty taskProperty) {
    if (taskProperty.getId() != null && taskPropertyRepository.existsById(taskProperty.getId()))
      throw new IllegalStateException("Task property " + taskProperty.getId() + " already exists");
    
    return taskPropertyRepository.save(taskProperty);
  }
  
  /**
   * Gets a task property by id.
   * @param id the id of the task property to get
   * @return the task property with the given id
   */
  @Transactional(readOnly = true)
  public Optional<TaskProperty> getTaskPropertyById(Long id) {
    return taskPropertyRepository.findById(id);
  }
  
  /**
   * Gets all task properties by task id.
   * @return all task properties with the given task id
   */
  @Transactional(readOnly = true)
  public List<TaskProperty> getTaskPropertyByTaskId(Long taskId) {
    return taskPropertyRepository.findByTaskId(taskId, null).getContent();
  }
  
  /**
   * Gets all task properties by property value id.
   * @return all task properties with the given property value id
   */
  @Transactional(readOnly = true)
  public List<TaskProperty> getTaskPropertyByPropertyValueId(Long propertyValueId) {
    return taskPropertyRepository.findByPropertyValueId(propertyValueId, null).getContent();
  }
  
  /**
   * Gets all task properties by task id and property value id.
   * @return all task properties with the given task id and property value id
   */
  @Transactional(readOnly = true)
  public List<TaskProperty> getTaskPropertyByTaskIdAndPropertyValueId(Long taskId, Long propertyValueId) {
    return taskPropertyRepository.findByTaskIdAndPropertyValueId(taskId, propertyValueId, null).getContent();
  }
  
  /**
   * Deletes a task property by id.
   * @param id the id of the task property to delete
   */
  @Transactional
  public void deleteTaskPropertyById(Long id) {
    taskPropertyRepository.deleteById(id);
  }

  /**
   * Updates a task's TaskProperty from a task property dto.
   * @param taskPropertyDto the task property dto
   * @param task the task owning the task property
   */
  @Transactional
  public TaskProperty updateTaskPropertyFrom(TaskPropertyDto taskPropertyDto, Task task) {
    TaskProperty taskProperty = null;
    if (taskPropertyDto.getId() != null) {
      taskProperty = taskPropertyRepository.findById(taskPropertyDto.getId()).orElse(null);
    }

    if (taskProperty != null) {
      PropertyValue updatedPropertyValue = propertyValueService.updatePropertyValueFrom(taskPropertyDto.getPropertyValue());
      taskProperty.setPropertyValue(updatedPropertyValue);
    } else {
      taskProperty = new TaskProperty();
      taskProperty = updateTaskPropertysPropValFrom(taskPropertyDto, taskProperty);
      task.addTaskProperty(taskProperty);
    }
    return taskPropertyRepository.save(taskProperty);
  }

  private TaskProperty updateTaskPropertysPropValFrom(TaskPropertyDto taskPropertyDto, TaskProperty taskProperty) {
    PropertyValue propertyValue = propertyValueService.createPropertyValueFrom(taskPropertyDto.getPropertyValue());
    taskProperty.setPropertyValue(propertyValue);
    return taskPropertyRepository.save(taskProperty);
  }

}
