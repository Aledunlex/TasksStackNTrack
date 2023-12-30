package com.tsnt.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskPropertyTest {
  
  @Test
  void createTaskPropertyWithoutPropertyValue() {
    String value = "Property Value";
    TaskProperty taskProperty = new TaskProperty();
    taskProperty.setPropertyValue(new PropertyValue(value));
    
    assertEquals(value, taskProperty.getPropertyValue().getValue());
  }
  
  @Test
  void createTaskPropertyWithPropertyValue() {
    String value = "Property Value";
    PropertyValue propertyValue = new PropertyValue(value);
    TaskProperty taskProperty = new TaskProperty(propertyValue);
    
    assertEquals(propertyValue, taskProperty.getPropertyValue());
  }
  
  @Test
  void addTaskToTaskProperty() {
    Task task = new Task();
    TaskProperty property = new TaskProperty();
    property.setTask(task);
    
    assertEquals(task, property.getTask());
  }
  
  @Test
  void addTaskPropertyToTask() {
    Task task = new Task();
    TaskProperty property = new TaskProperty();
    task.addTaskProperty(property);
    
    assertEquals(property, task.getTaskProperties().stream().findFirst().get());
  }
  
}
