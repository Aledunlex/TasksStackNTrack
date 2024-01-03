package com.tsnt.controllers;

import com.tsnt.dtos.TaskDto;
import com.tsnt.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TaskController is a Spring REST Controller that handles HTTP requests for Task resources.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  
  
  /**
   * TaskService is a Spring Service that handles business logic for Task resources.
   */
  private final TaskService taskService;
  
  /**
   * TaskController constructor.
   * @param taskService TaskService
   */
  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }
  
  /**
   * createTask is a POST endpoint that creates a new Task resource, using the provided TaskDto.
   * @param taskDto TaskDto
   * @return ResponseEntity<TaskDto>
   */
  @PostMapping
  public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
    taskDto.setId(taskService.createTask(taskDto));
    return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
  }
  
  /**
   * retrieveAllTasks is a GET endpoint that retrieves all TaskDto resources.
   * @return ResponseEntity<List<TaskDto>>
   */
  @GetMapping
  public ResponseEntity<List<TaskDto>> retrieveAllTasks() {
    return ResponseEntity.ok(taskService.getTasksByRecency());
  }
  
  /**
   * retrieveTaskById is a GET endpoint that retrieves a TaskDto resource by its id.
   * @param id Long
   * @return ResponseEntity<TaskDto>
   */
  @GetMapping("/{id}")
  public ResponseEntity<TaskDto> retrieveTaskById(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.getTaskById(id));
  }
  
  /**
   * updateTask is a PUT endpoint that updates a Task resource, using the provided TaskDto.
   * @param taskDto TaskDto mapping to the Task resource to be updated
   * @return ResponseEntity<TaskDto> the updated TaskDto
   */
  @PutMapping("/{id}")
  public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
    return ResponseEntity.ok(taskService.updateTask(taskDto));
  }
  
  /**
   * deleteTask is a DELETE endpoint that deletes a Task resource by its id.
   * @param id Long mapping to the Task resource to be deleted
   * @return ResponseEntity<Void> an empty ResponseEntity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }
  
}