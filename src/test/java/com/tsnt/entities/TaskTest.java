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
    TaskProperty property = new TaskProperty(new PropertyValue(propertyValue, new Property(propertyName)));
    task.addTaskProperty(property);
    
    assertEquals(propertyValue, task.getTaskProperties().stream()
        .filter(p -> p.getPropertyValue().getValue().equals(propertyValue))
        .findFirst()
        .get()
        .getPropertyValue()
        .getValue());
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