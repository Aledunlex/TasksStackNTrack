package com.tsnt.repositories;

import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import com.tsnt.entities.Task;
import com.tsnt.entities.TaskProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskPropertyRepositoryTest {
  
  @Autowired
  private TaskPropertyRepository taskPropertyRepository;
  
  @Autowired
  private TaskRepository taskRepository;
  
  @Autowired
  private PropertyValueRepository propertyValueRepository;
  
  @Test
  void findByTaskId() {
    Task task = new Task("Test Task");
    
    TaskProperty taskProperty = new TaskProperty();
    task.addTaskProperty(taskProperty);
    
    taskPropertyRepository.save(taskProperty);
    
    assertThrows(InvalidDataAccessApiUsageException.class, () -> taskPropertyRepository.findByTaskId(task.getId(), null));
    taskRepository.save(task);
    assertDoesNotThrow(() -> taskPropertyRepository.findByTaskId(task.getId(), null));
    
    Page<TaskProperty> taskProperties = taskPropertyRepository.findByTaskId(task.getId(), null);
    assertEquals(1, taskProperties.getTotalElements());
    assertTrue(taskProperties.getContent().contains(taskProperty));
  }
  
  @Test
  void findById() {
    TaskProperty taskProperty = new TaskProperty();
    taskPropertyRepository.save(taskProperty);
    
    assertTrue(taskPropertyRepository.findById(taskProperty.getId()).isPresent());
  }
  
  @Test
  void findByPropertyValueId() {
    Property property = new Property("Test Property");
    PropertyValue propertyValue = new PropertyValue("Test Value");
    property.addPropertyValue(propertyValue);
    
    TaskProperty taskProperty = new TaskProperty();
    taskProperty.setPropertyValue(propertyValue);
    
    taskPropertyRepository.save(taskProperty);
    
    assertDoesNotThrow(() -> taskPropertyRepository.findByPropertyValueId(taskProperty.getPropertyValue().getId(), null));
    
    Page<TaskProperty> taskProperties = taskPropertyRepository.findByPropertyValueId(taskProperty.getPropertyValue().getId(), null);
    assertEquals(1, taskProperties.getTotalElements());
    assertTrue(taskProperties.getContent().contains(taskProperty));
  }
  
  @Test
  void findByTaskIdAndPropertyValueId() {
    Task task = new Task("Test Task");
    Property property = new Property("Test Property");
    PropertyValue propertyValue = new PropertyValue("Test Value");
    property.addPropertyValue(propertyValue);
    
    TaskProperty taskProperty = new TaskProperty();
    task.addTaskProperty(taskProperty);
    taskProperty.setPropertyValue(propertyValue);
    
    taskPropertyRepository.save(taskProperty);
    
    assertThrows(InvalidDataAccessApiUsageException.class, () -> taskPropertyRepository.findByTaskIdAndPropertyValueId(task.getId(), propertyValue.getId(), null));
    taskRepository.save(task);
    
    Page<TaskProperty> taskProperties = taskPropertyRepository.findByTaskIdAndPropertyValueId(task.getId(), propertyValue.getId(), null);
    assertEquals(1, taskProperties.getTotalElements());
    assertTrue(taskProperties.getContent().contains(taskProperty));
  }
  
}
