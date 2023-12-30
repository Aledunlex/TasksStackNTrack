package com.tsnt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Task {
  
  private static final String NEW_TASK = "New Task";
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private final Date creationDate = new Date();
  
  @Column(nullable = false)
  private String title;
  
  private String description;
  
  @OneToMany(mappedBy = "task")
  private Set<TaskProperty> properties = new HashSet<>();
  
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
  
  public Task(String title, String description, Set<TaskProperty> properties) {
    this.title = title;
    this.description = description;
    this.properties = properties;
  }
  
  public void addProperty(TaskProperty property) {
    properties.add(property);
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
    boolean equalTitles = this.title.equalsIgnoreCase(otherTask.getTitle());
    if (this.id == null || otherTask.getId() == null) {
      return equalTitles;
    }
    return this.id.equals(otherTask.getId()) && equalTitles;
  }
  
  @Override
  public int hashCode() {
    return this.title.toLowerCase().hashCode() + this.properties.stream().mapToInt(TaskProperty::hashCode).sum();
  }
  
  @Override
  public String toString() {
    return String.format("Task[%s, %s, %s]", this.title, this.description, this.properties);
  }
  
}