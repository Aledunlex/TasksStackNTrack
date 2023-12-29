package com.tsnt.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskPropertyTest {
  
  @Test
  void createTaskPropertyWithoutName() {
    String name = "New Property";
    TaskProperty property = new TaskProperty();
    
    assertEquals(name, property.getName());
  }
  
  @Test
  void createTaskPropertyWithName() {
    String name = "Property Name";
    TaskProperty property = new TaskProperty(name);
    
    assertEquals(name, property.getName());
  }
  
  @Test
  void createTaskPropertyWithNameAndValue() {
    String name = "Property Name";
    String value = "Property Value";
    TaskProperty property = new TaskProperty(name, value);
    
    assertEquals(name, property.getName());
    assertEquals(value, property.getPropertyValue());
  }
  
  @Test
  void createTaskPropertyWithNameAndValueAndTasks() {
    String name = "Property Name";
    String value = "Property Value";
    Task task = new Task();
    TaskProperty property = new TaskProperty(name, value);
    property.setTask(task);
    
    assertEquals(name, property.getName());
    assertEquals(value, property.getPropertyValue());
    assertEquals(task, property.getTask());
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
    task.addProperty(property);
    
    assertEquals(property, task.getProperties().stream().findFirst().get());
  }
  
  @Test
  void subtractFromOtherPropertyBothSameValue() {
    TaskProperty property1 = new TaskProperty("Property Name", "Property Value");
    TaskProperty property2 = new TaskProperty("Property Name", "Property Value");
    property1.subtractFromOtherProperty(property2);
    
    assertEquals("", property1.getPropertyValue());
  }
  
  @Test
  void subtractFromOtherPropertyBothDifferentValue() {
    TaskProperty property1 = new TaskProperty("Property Name", "Property Value 1");
    TaskProperty property2 = new TaskProperty("Property Name", "Property Value 2");
    property1.subtractFromOtherProperty(property2);
    
    assertEquals("Property Value 1", property1.getPropertyValue());
  }
  
}
