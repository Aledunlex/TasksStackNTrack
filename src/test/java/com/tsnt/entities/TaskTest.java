package com.tsnt.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
    String addedValues = propertyValue1 + propertyValue2;
    String propertyName = "Property Name";
    TaskProperty property1 = new TaskProperty(propertyName, propertyValue1);
    TaskProperty property2 = new TaskProperty(propertyName, propertyValue2);
    task.addProperty(property1);
    task.addProperty(property2);
    
    assertEquals(addedValues, task.getProperties().stream()
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
    String addedValues = propertyValue1 + propertyValue2;
    String propertyName = "Property Name";
    TaskProperty property1 = new TaskProperty(propertyName, propertyValue1);
    TaskProperty property2 = new TaskProperty(propertyName.toUpperCase(), propertyValue2);
    task.addProperty(property1);
    task.addProperty(property2);
    
    assertEquals(addedValues, task.getProperties().stream()
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
    String addedValues = propertyValue1 + propertyValue2;
    String propertyName = "Property Name";
    TaskProperty property1 = new TaskProperty(propertyName, propertyValue1);
    TaskProperty property2 = new TaskProperty(propertyName.toUpperCase(), propertyValue2);
    task.addProperty(property1);
    task.addProperty(property2);
    
    assertEquals(addedValues, task.getProperties().stream()
        .filter(p -> p.getName().equals(propertyName))
        .findFirst()
        .get()
        .getPropertyValue());
  }
  
  @Test
  void twoNewTasksAreEqual() {
    Task task1 = new Task();
    Task task2 = new Task();
    assertEquals(task1, task2);
  }
  
  @Test
  void twoTasksWithDifferentDescriptionsCanBeEqual() {
    Task task1 = new Task();
    Task task2 = new Task();
    task1.setDescription("Some Description");
    assertEquals(task1, task2);
  }
  
  @Test
  void twoTasksWithDifferentPropertiesCanBeEqual() {
    Task task1 = new Task();
    Task task2 = new Task();
    task1.setProperties(new HashSet<>(
        List.of(new TaskProperty("Property Name", "Property Value"))
    ));
    assertEquals(task1, task2);
  }
  
  @Test
  void twoTasksWithDifferentTitlesAndNoIdAreNotEqual() {
    Task task1 = new Task();
    Task task2 = new Task();
    assertNull(task1.getId());
    task1.setTitle("Task 1");
    assertNotEquals(task1, task2);
  }
 
  @Test
  void twoTasksWithOneOfThemWithoutIdAreComparedByTitle() {
    Task task1 = new Task();
    Task task2 = new Task();
    task1.setId(1L);
    task1.setTitle("Task 1");
    task2.setTitle("Task 1");
    assertEquals(task1, task2);
    task2.setTitle("Task 2");
    assertNotEquals(task1, task2);
  }
  
  @Test
  void twoTasksWithDifferentIdsButSameTitleAreNotEqual() {
    Task task1 = new Task();
    Task task2 = new Task();
    task1.setId(1L);
    task2.setId(2L);
    task1.setTitle("Task 1");
    task2.setTitle("Task 1");
    assertNotEquals(task1, task2);
  }
  
}