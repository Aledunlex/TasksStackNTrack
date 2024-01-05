package com.tsnt.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Task {
  
  private static final String NEW_TASK = "New Task";
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private final Date creationDate = new Date();
  
  @Column(nullable = false)
  private String title;
  
  private String description;
  
  @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<TaskProperty> taskProperties = new HashSet<>();
  
  public Task() {
    this.title = NEW_TASK;
  }
  
  public Task(String title) {
    this.title = title;
  }
  
  public Task(String title, String description) {
    this.title = title;
    this.description = description;
  }
  
  public Task(String title, String description, Set<TaskProperty> taskProperties) {
    this.title = title;
    this.description = description;
    this.taskProperties = taskProperties;
    taskProperties.forEach(taskProperty -> taskProperty.setTask(this));
  }
  
  public void addTaskProperty(TaskProperty property) {
    taskProperties.add(property);
    property.setTask(this);
  }
  
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }
    if (other.getClass() != this.getClass()) {
      return false;
    }
    Task otherTask = (Task) other;
    if (this.id == null || otherTask.getId() == null) {
      return this.title.equalsIgnoreCase(otherTask.getTitle());
    }
    return this.id.equals(otherTask.getId());
  }
  
  @Override
  public int hashCode() {
    return this.id == null ? 0 : (this.id.hashCode() * this.getClass().hashCode());
  }
  
  @Override
  public String toString() {
    return String.format("Task[%s, %s, %s, %s]", this.id, this.title, this.description, this.taskProperties);
  }
  
}