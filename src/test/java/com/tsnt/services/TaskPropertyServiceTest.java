package com.tsnt.services;

import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import com.tsnt.entities.TaskProperty;
import com.tsnt.repositories.TaskPropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskPropertyServiceTest {
  
  @Autowired
  private TaskPropertyRepository taskPropertyRepository;
  
  private TaskPropertyService taskPropertyService;
  
  @BeforeEach
  void setUp() {
    taskPropertyService = new TaskPropertyService(taskPropertyRepository);
  }
  
  @Test
  void getTaskPropertyById() {
    TaskProperty taskProperty = new TaskProperty(new PropertyValue("propertyValue1", new Property("property1")));
    taskPropertyRepository.save(taskProperty);
    
    TaskProperty retrievedTaskProperty = taskPropertyService.getTaskPropertyById(taskProperty.getId());
    assertEquals(taskProperty, retrievedTaskProperty);
  }
  
  @Test
  void createTaskProperty() {
    TaskProperty taskProperty = new TaskProperty(new PropertyValue("propertyValue1", new Property("property1")));
    taskPropertyService.createTaskProperty(taskProperty);
    
    TaskProperty retrievedTaskProperty = taskPropertyService.getTaskPropertyById(taskProperty.getId());
    assertEquals(taskProperty, retrievedTaskProperty);
  }
  
  @Test
  void updateTaskProperty() {
    TaskProperty taskProperty = new TaskProperty(new PropertyValue("propertyValue1", new Property("property1")));
    taskPropertyService.createTaskProperty(taskProperty);
    
    taskProperty.setPropertyValue(new PropertyValue("propertyValue2", new Property("property2")));
    
    TaskProperty retrievedTaskProperty = taskPropertyService.getTaskPropertyById(taskProperty.getId());
    assertEquals("propertyValue2", retrievedTaskProperty.getPropertyValue().getValue());
  }
  
  @Test
  void deleteTaskProperty() {
    TaskProperty taskProperty = new TaskProperty(new PropertyValue("propertyValue1", new Property("property1")));
    taskPropertyService.createTaskProperty(taskProperty);
    
    taskPropertyService.deleteTaskPropertyById(taskProperty.getId());
    assertNull(taskPropertyService.getTaskPropertyById(taskProperty.getId()));
  }
  
}
