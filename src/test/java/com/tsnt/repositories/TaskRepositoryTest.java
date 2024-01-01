package com.tsnt.repositories;

import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import com.tsnt.entities.Task;

import com.tsnt.entities.TaskProperty;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {
  
  @Autowired
  private TaskRepository taskRepository;
  
  @Test
  void simpleTaskSavingTest() {
    Task task = new Task();
    taskRepository.save(task);
    
    assertEquals(1, taskRepository.findAll().size());
    assertEquals(task, taskRepository.findAll().get(0));
    assertTrue(taskRepository.findById(task.getId()).isPresent());
  }
  
  @Test
  void taskWithPropertiesSavingTest() {
    String taskName = "New Task";
    String newDescription = "New Description";
    String valueOne = "Value 1";
    String valueTwo = "Value 2";
    String propertyNameOne = "Property 1";
    String propertyNameTwo = "Property 2";
    
    Task task = new Task(taskName, newDescription);
    task.addTaskProperty(new TaskProperty(new PropertyValue(valueOne, new Property(propertyNameOne))));
    task.addTaskProperty(new TaskProperty(new PropertyValue(valueTwo, new Property(propertyNameTwo))));
    taskRepository.save(task);
    
    Page<Task> byTitle = taskRepository.findAllByTitleContainingIgnoreCaseOrderByCreationDateDesc(taskName, null);
    assertTrue(byTitle.getContent().contains(task));
    
    Page<Task> byDescription = taskRepository.findAllByDescriptionContainingIgnoreCaseOrderByCreationDateDesc(newDescription, null);
    assertTrue(byDescription.getContent().contains(task));
    
    Page<Task> byPropertyName = taskRepository.findAllByTaskPropertiesPropertyValuePropertyNameContainingIgnoreCaseOrderByCreationDateDesc(propertyNameOne, null);
    assertTrue(byPropertyName.getContent().contains(task));
  }
  
  @Test
  void taskWithPropertiesDeletingTest() {
    String taskName = "New Task";
    String newDescription = "New Description";
    String valueOne = "Value 1";
    String valueTwo = "Value 2";
    String propertyNameOne = "Property 1";
    String propertyNameTwo = "Property 2";
    
    Task task = new Task(taskName, newDescription);
    task.addTaskProperty(new TaskProperty(new PropertyValue(valueOne, new Property(propertyNameOne))));
    task.addTaskProperty(new TaskProperty(new PropertyValue(valueTwo, new Property(propertyNameTwo))));
    taskRepository.save(task);
    
    assertEquals(1, taskRepository.findAll().size());
    assertEquals(task, taskRepository.findAll().get(0));
    assertTrue(taskRepository.findById(task.getId()).isPresent());
    
    taskRepository.delete(task);
    
    assertEquals(0, taskRepository.findAll().size());
    assertFalse(taskRepository.findById(task.getId()).isPresent());
  }
  
  @Test
  void taskWithPropertiesUpdatingTest() {
    String taskName = "New Task";
    String newDescription = "New Description";
    String valueOne = "Value 1";
    String valueTwo = "Value 2";
    String propertyNameOne = "Property 1";
    String propertyNameTwo = "Property 2";
    
    Task task = new Task(taskName, newDescription);
    task.addTaskProperty(new TaskProperty(new PropertyValue(valueOne, new Property(propertyNameOne))));
    task.addTaskProperty(new TaskProperty(new PropertyValue(valueTwo, new Property(propertyNameTwo))));
    taskRepository.save(task);
    
    assertEquals(1, taskRepository.findAll().size());
    assertEquals(task, taskRepository.findAll().get(0));
    assertTrue(taskRepository.findById(task.getId()).isPresent());
    
    String newTaskName = "New Task Name";
    String newDescriptionName = "New Description Name";
    String newValueOne = "New Value 1";
    String newValueTwo = "New Value 2";
    String newPropertyNameOne = "New Property 1";
    String newPropertyNameTwo = "New Property 2";
    
    task.setTitle(newTaskName);
    task.setDescription(newDescriptionName);
    task.getTaskProperties().forEach(taskProperty -> {
      taskProperty.getPropertyValue().setValue(newValueOne);
      taskProperty.getPropertyValue().getProperty().setName(newPropertyNameOne);
    });
    task.addTaskProperty(new TaskProperty(new PropertyValue(newValueTwo, new Property(newPropertyNameTwo))));
    taskRepository.save(task);
    
    assertEquals(1, taskRepository.findAll().size());
    assertEquals(task, taskRepository.findAll().get(0));
    assertTrue(taskRepository.findById(task.getId()).isPresent());
    
    Page<Task> byTitle = taskRepository.findAllByTitleContainingIgnoreCaseOrderByCreationDateDesc(newTaskName, null);
    assertTrue(byTitle.getContent().contains(task));
    
    Page<Task> byDescription = taskRepository.findAllByDescriptionContainingIgnoreCaseOrderByCreationDateDesc(newDescriptionName, null);
    assertTrue(byDescription.getContent().contains(task));
    
    Page<Task> byPropertyName = taskRepository.findAllByTaskPropertiesPropertyValuePropertyNameContainingIgnoreCaseOrderByCreationDateDesc(newPropertyNameOne, null);
    assertTrue(byPropertyName.getContent().contains(task));
  }
  
  @Test
  void taskWithPropertiesEqualityTest() {
    String taskName = "New Task";
    String newDescription = "New Description";
    String valueOne = "Value 1";
    String valueTwo = "Value 2";
    String propertyNameOne = "Property 1";
    String propertyNameTwo = "Property 2";
    
    Task task = new Task(taskName, newDescription);
    task.addTaskProperty(new TaskProperty(new PropertyValue(valueOne, new Property(propertyNameOne))));
    task.addTaskProperty(new TaskProperty(new PropertyValue(valueTwo, new Property(propertyNameTwo))));
    taskRepository.save(task);
    
    Task taskTwo = new Task(taskName, newDescription);
    taskTwo.addTaskProperty(new TaskProperty(new PropertyValue(valueOne, new Property(propertyNameOne))));
    taskTwo.addTaskProperty(new TaskProperty(new PropertyValue(valueTwo, new Property(propertyNameTwo))));
    taskRepository.save(taskTwo);
    
    assertEquals(2, taskRepository.findAll().size());
    assertEquals(task, taskRepository.findAll().get(0));
    assertEquals(taskTwo, taskRepository.findAll().get(1));
    assertTrue(taskRepository.findById(task.getId()).isPresent());
    assertTrue(taskRepository.findById(taskTwo.getId()).isPresent());
    
    taskTwo.setTitle("New Task Name");
    taskRepository.save(taskTwo);
    
    assertEquals(2, taskRepository.findAll().size());
    assertEquals(task, taskRepository.findAll().get(0));
    assertEquals(taskTwo, taskRepository.findAll().get(1));
    assertTrue(taskRepository.findById(task.getId()).isPresent());
    assertTrue(taskRepository.findById(taskTwo.getId()).isPresent());
    
    taskTwo.setDescription("New Description Name");
    taskRepository.save(taskTwo);
    
    assertEquals(2, taskRepository.findAll().size());
    assertEquals(task, taskRepository.findAll().get(0));
    assertEquals(taskTwo, taskRepository.findAll().get(1));
    assertTrue(taskRepository.findById(task.getId()).isPresent());
    assertTrue(taskRepository.findById(taskTwo.getId()).isPresent());
    
    taskTwo.getTaskProperties().forEach(taskProperty -> {
      taskProperty.getPropertyValue().setValue("New Value 1");
      taskProperty.getPropertyValue().getProperty().setName("New Property 1");
    });
    taskRepository.save(taskTwo);
    
    assertEquals(2, taskRepository.findAll().size());
    assertEquals(task, taskRepository.findAll().get(0));
    assertEquals(taskTwo, taskRepository.findAll().get(1));
    assertTrue(taskRepository.findById(task.getId()).isPresent());
    assertTrue(taskRepository.findById(taskTwo.getId()).isPresent());
  }
  
  @Test
  void findAllByOrderByCreationDateDescTest() {
    for (int i = 0; i < 3; i++) {
      Task task = new Task();
      taskRepository.save(task);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
    Page<Task> allByOrderByCreationDateDesc = taskRepository.findAllByOrderByCreationDateDesc(null);
    List<Task> tasks = new ArrayList<>(allByOrderByCreationDateDesc.getContent());
    for (int i = 0; i < tasks.size() - 1; i++) {
      assertTrue(tasks.get(i).getCreationDate().after(tasks.get(i + 1).getCreationDate()));
    }
  }
  
}
