package com.tsnt.services;

import com.tsnt.entities.Task;
import com.tsnt.entities.TaskProperty;
import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import com.tsnt.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class TaskServiceTest {
  
  @Autowired
  private TaskRepository taskRepository;
  
  private TaskService taskService;
  
  @BeforeEach
  void setUp() {
    taskService = new TaskService(taskRepository);
  }
  
  @Test
  void createTask() {
    Task task = new Task();
    
    taskService.createTask(task);
    
    Task retrievedTask = taskRepository.findById(task.getId()).get();
    assertEquals(task, retrievedTask);
  }
  
  @Test
  void getTaskById() {
    Task task = new Task();
    taskRepository.save(task);
    
    Task retrievedTask = taskService.getTaskById(task.getId()).get();
    assertEquals(task, retrievedTask);
  }
  
  @Test
  void getAllTasksOrderedByCreationDateDesc() {
    Task task1 = new Task();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Task task2 = new Task();
    taskRepository.save(task1);
    taskRepository.save(task2);
    
    Iterable<Task> retrievedTasks = taskService.getTasksByRecency();
    assertEquals(task2, retrievedTasks.iterator().next());
  }
  
  @Test
  void getAllTasksByTitleContainingIgnoreCaseOrderedByCreationDateDesc() {
    Task task1 = new Task();
    task1.setTitle("title1");
    Task task2 = new Task();
    task2.setTitle("title2");
    taskRepository.save(task1);
    taskRepository.save(task2);
    
    Iterable<Task> retrievedTasks = taskService.getTasksByTitleContaining("title1");
    assertEquals(task1, retrievedTasks.iterator().next());
  }
  
  @Test
  void getAllTasksByDescriptionContainingIgnoreCaseOrderedByCreationDateDesc() {
    Task task1 = new Task();
    task1.setDescription("description1");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Task task2 = new Task();
    task2.setDescription("description2");
    taskRepository.save(task1);
    taskRepository.save(task2);
    
    Iterable<Task> retrievedTasks = taskService.getTasksByDescriptionContaining("description1");
    assertEquals(task1, retrievedTasks.iterator().next());
  }
  
  @Test
  void getAllTasksByTaskPropertiesPropertyValuePropertyNameContainingIgnoreCaseOrderedByCreationDateDesc() {
    Task task1 = new Task();
    task1.addTaskProperty(new TaskProperty(new PropertyValue("propertyValue1", new Property("property1"))));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Task task2 = new Task();
    task2.addTaskProperty(new TaskProperty(new PropertyValue("propertyValue2", new Property("property2"))));
    taskRepository.save(task1);
    taskRepository.save(task2);
    
    List<Task> retrievedTasks = taskService.getTasksHavingAPropertyNameContaining("property1");
    assertEquals(task1, retrievedTasks.iterator().next());
  }
  
  @Test
  void deleteTask() {
    Task task = new Task();
    taskRepository.save(task);
    
    taskService.deleteTask(task.getId());
    assertEquals(0, taskRepository.count());
  }
  
}
