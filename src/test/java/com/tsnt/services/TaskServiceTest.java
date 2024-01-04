package com.tsnt.services;

import com.tsnt.dtos.PropertyDto;
import com.tsnt.dtos.PropertyValueDto;
import com.tsnt.dtos.TaskDto;
import com.tsnt.dtos.TaskPropertyDto;
import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import com.tsnt.entities.Task;
import com.tsnt.entities.TaskProperty;
import com.tsnt.mappers.TaskMapper;
import com.tsnt.mappers.TaskMapperImpl;
import com.tsnt.mappers.TaskPropertyMapper;
import com.tsnt.mappers.TaskPropertyMapperImpl;
import com.tsnt.repositories.TaskPropertyRepository;
import com.tsnt.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({TaskMapperImpl.class, TaskPropertyMapperImpl.class})
class TaskServiceTest {
  
  @Autowired
  private TaskRepository taskRepository;
  
  @Autowired
  private TaskPropertyRepository taskPropertyRepository;
  
  @Autowired
  private TaskMapper taskMapper;
  
  @Autowired
  private TaskPropertyMapper taskPropertyMapper;
  
  private TaskService taskService;
  
  @BeforeEach
  void setUp() {
    taskService = new TaskService(taskRepository, taskMapper, taskPropertyMapper, taskPropertyRepository);
  }
  
  @Test
  void createTask() {
    Task task = new Task();
    
    TaskDto taskDto = taskMapper.taskToTaskDto(task);
    
    Long id = taskService.createTask(taskDto);
    
    Task retrievedTask = taskRepository.findById(id).get();
    assertEquals(task, retrievedTask);
  }
  
  @Test
  void testTaskAndPropertiesPersistence() {
    // Créer une Task avec des TaskProperty
    TaskDto taskDto = new TaskDto();
    taskDto.setTitle("Task with Properties");
    taskDto.setDescription("Task with Properties description");
    taskDto.setTaskProperties(Set.of(new TaskPropertyDto(0L, new PropertyValueDto(0L, "Property Value 1", new PropertyDto(0L, "Property 1")))));
    
    Long taskId = taskService.createTask(taskDto);
    
    TaskDto retrievedTask = taskService.getTaskById(taskId);
    assertNotNull(retrievedTask);
    assertFalse(retrievedTask.getTaskProperties().isEmpty());
  }
  
  
  @Test
  void getTaskById() {
    Task task = new Task();
    taskRepository.save(task);
    
    Task retrievedTask = taskMapper.taskDtoToTask(taskService.getTaskById(task.getId()));
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
    
    Iterable<Task> retrievedTasks = taskService.getTasksByRecency().stream()
        .map(taskMapper::taskDtoToTask)
        .toList();
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
    
    Iterable<Task> retrievedTasks = taskService.getTasksByTitleContaining("title1").stream()
        .map(taskMapper::taskDtoToTask)
        .toList();
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
    
    Iterable<Task> retrievedTasks = taskService.getTasksByDescriptionContaining("description1").stream()
        .map(taskMapper::taskDtoToTask)
        .toList();
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
    
    List<Task> retrievedTasks = taskService.getTasksHavingAPropertyNameContaining("property1").stream()
        .map(taskMapper::taskDtoToTask)
        .toList();
    assertEquals(task1, retrievedTasks.iterator().next());
  }
  
  @Test
  void updateTask() {
    Task task = new Task();
    taskRepository.save(task);
    
    TaskDto taskDto = taskMapper.taskToTaskDto(task);
    taskDto.setTitle("A new title");
    taskDto.setDescription("A new description");
    taskDto.setTaskProperties(Set.of(new TaskPropertyDto(2L, new PropertyValueDto(1L, "A new property value", new PropertyDto(0L, "A new property")))));
    
    Task updatedTask = taskMapper.taskDtoToTask(taskService.updateTask(taskDto));
    assertEquals(task.getId(), updatedTask.getId());
    assertEquals(taskDto.getTitle(), updatedTask.getTitle());
    assertEquals(taskDto.getDescription(), updatedTask.getDescription());
    assertEquals(taskDto.getTaskProperties().iterator().next().getPropertyValue().getValue(), updatedTask.getTaskProperties().iterator().next().getPropertyValue().getValue());
  }
  
  @Test
  void deleteTask() {
    Task task = new Task();
    taskRepository.save(task);
    
    System.out.println(taskRepository.count());
    taskService.deleteTask(task.getId());
    assertEquals(0, taskRepository.count());
  }
  
}