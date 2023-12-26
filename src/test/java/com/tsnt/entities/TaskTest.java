package com.tsnt.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
  
  @Test
  void createTaskWithTitle() {
    String title = "Task Title";
    Task task = new Task();
    task.setTitle(title);
    
    assertEquals(title, task.getTitle());
  }
  
  @Test
  void createTaskWithDescription() {
    String title = "Task Title";
    String description = "Task Description";
    Task task = new Task(title, description);
    
    assertEquals(title, task.getTitle());
    assertEquals(description, task.getDescription());
  }
  
  @Test
  void createTaskWithCreationDate() {
    // Make sure the creation date is set to the current date by comparing it to a new Date object
    // created after the task is created (the dates should be within at most a few milliseconds of each other).
    Task task = new Task();
    Date creationDate = task.getCreationDate();
    Date now = new Date();
    int msDeltaMax = 3000;
    assertTrue(now.getTime() - creationDate.getTime() < msDeltaMax);
  }
  
  @Test
  void createTaskWithProperties() {
    Task task = new Task();
    String propertyValue = "Property Value";
    String propertyName = "Property Name";
    TaskProperty property = new TaskProperty(propertyName, propertyValue);
    task.addProperty(property);
    
    assertEquals(propertyValue, task.getProperties().stream()
        .filter(p -> p.getName().equals(propertyName))
        .findFirst()
        .get()
        .getPropertyValue());
  }
  
  @Test
  void createTaskWithPropertiesWithSameName() {
    Task task = new Task();
    String propertyValue1 = "Property Value 1";
    String propertyValue2 = "Property Value 2";
    String propertyName = "Property Name";
    TaskProperty property1 = new TaskProperty(propertyName, propertyValue1);
    TaskProperty property2 = new TaskProperty(propertyName, propertyValue2);
    task.addProperty(property1);
    task.addProperty(property2);
    
    assertEquals(propertyValue1 + propertyValue2, task.getProperties().stream()
        .filter(p -> p.getName().equals(propertyName))
        .findFirst()
        .get()
        .getPropertyValue());
  }
  
  @Test
void createTaskWithPropertiesWithSameNameAndDifferentCase() {
    Task task = new Task();
    String propertyValue1 = "Property Value 1";
    String propertyValue2 = "Property Value 2";
    String propertyName = "Property Name";
    TaskProperty property1 = new TaskProperty(propertyName, propertyValue1);
    TaskProperty property2 = new TaskProperty(propertyName.toUpperCase(), propertyValue2);
    task.addProperty(property1);
    task.addProperty(property2);
    
    assertEquals(propertyValue1 + propertyValue2, task.getProperties().stream()
        .filter(p -> p.getName().equals(propertyName))
        .findFirst()
        .get()
        .getPropertyValue());
  }
  
  @Test
  void createTaskWithPropertiesWithSameNameAndDifferentCaseAndDifferentValue() {
    Task task = new Task();
    String propertyValue1 = "Property Value 1";
    String propertyValue2 = "Property Value 2";
    String propertyName = "Property Name";
    TaskProperty property1 = new TaskProperty(propertyName, propertyValue1);
    TaskProperty property2 = new TaskProperty(propertyName.toUpperCase(), propertyValue2);
    task.addProperty(property1);
    task.addProperty(property2);
    
    assertNotEquals(propertyValue1, task.getProperties().stream()
        .filter(p -> p.getName().equals(propertyName))
        .findFirst()
        .get()
        .getPropertyValue());
  }
  
  @Test
  void createTaskWithPropertiesWithSameNameAndDifferentCaseAndSameValue() {
    Task task = new Task();
    String propertyValue1 = "Property Value 1";
    String propertyName = "Property Name";
    TaskProperty property1 = new TaskProperty(propertyName, propertyValue1);
    TaskProperty property2 = new TaskProperty(propertyName.toUpperCase(), propertyValue1);
    task.addProperty(property1);
    task.addProperty(property2);
    
    assertNotEquals(propertyValue1, task.getProperties().stream()
        .filter(p -> p.getName().equals(propertyName))
        .findFirst()
        .get()
        .getPropertyValue());
  }
  
  @Test
  void cantSetNewId() {
    Task task = new Task();
    
    assertThrows(UnsupportedOperationException.class, () -> task.setId(1L));
  }
  
}