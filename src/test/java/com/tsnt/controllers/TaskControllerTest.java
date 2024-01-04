package com.tsnt.controllers;

import com.tsnt.dtos.PropertyDto;
import com.tsnt.dtos.PropertyValueDto;
import com.tsnt.dtos.TaskDto;
import com.tsnt.dtos.TaskPropertyDto;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class TaskControllerTest {
  
  @Autowired
  private TaskController taskController;
  
  @Test
  void contextLoads() {
    assertThat(taskController).isNotNull();
  }
  
  @Test
  void testGetTasks() {
    assertThat(taskController.retrieveAllTasks()).isNotNull();
  }
  
  @Test
  void testCreateAndGetTask() {
    TaskDto task = new TaskDto();
    task.setTitle("Test Task");
    task.setDescription("Test Description");
    task.setTaskProperties(Set.of(new TaskPropertyDto(0L, new PropertyValueDto(0L, "Property Value 1", new PropertyDto(0L, "Property 1")))));
    assertThat(taskController.createTask(task)).isNotNull();
    
    assertThat(taskController.retrieveTaskById(task.getId())).isNotNull();
  }
  
  @Test
  void testUpdateTask() {
    TaskDto task = new TaskDto();
    task.setTitle("Test Task");
    task.setDescription("Test Description");
    assertThat(taskController.createTask(task)).isNotNull();
    
    TaskDto createdTask = taskController.retrieveTaskById(task.getId()).getBody();
    assertNotNull(createdTask);
    assertEquals(createdTask.getTitle().toLowerCase(), task.getTitle().toLowerCase());
    assertEquals(createdTask.getDescription().toLowerCase(), task.getDescription().toLowerCase());
    Long id = createdTask.getId();
    
    String updatedTaskTitle = "Updated Test Task";
    String updatedTaskDescription = "Updated Test Description";
    task.setTitle(updatedTaskTitle);
    task.setDescription(updatedTaskDescription);
    task.setTaskProperties(Set.of(new TaskPropertyDto(0L, new PropertyValueDto(0L, "Property Value 1", new PropertyDto(0L, "Property 1")))));
    assertThat(taskController.updateTask(task)).isNotNull();
    
    TaskDto updatedTask = taskController.retrieveTaskById(id).getBody();
    assertNotNull(updatedTask);
    assertEquals(updatedTask.getTitle().toLowerCase(), updatedTaskTitle.toLowerCase());
    assertEquals(updatedTask.getDescription().toLowerCase(), updatedTaskDescription.toLowerCase());
    assertEquals(updatedTask.getId(), id);
  }
  
  @Test
  void testDeleteTask() {
    TaskDto task = new TaskDto();
    task.setTitle("Test Task");
    task.setDescription("Test Description");
    task.setTaskProperties(Set.of(new TaskPropertyDto(0L, new PropertyValueDto(0L, "Property Value 1", new PropertyDto(0L, "Property 1")))));
    assertThat(taskController.createTask(task)).isNotNull();
    
    assertThat(taskController.deleteTask(task.getId())).isNotNull();
  }
  
}
