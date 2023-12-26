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
  
  @ManyToMany
  @JoinTable(
      name = "task_property_link",
      joinColumns = @JoinColumn(name = "task_id"),
      inverseJoinColumns = @JoinColumn(name = "property_id")
  )
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
    // If the property is already in the set, add the properties values together
    if (properties.contains(property)) {
      properties.stream()
          .filter(p -> p.equals(property))
          .findFirst()  // Only be one property with the same name
          .get()
          .addToOtherProperty(property);
    } else {
      properties.add(property);
    }
    property.addTask(this);
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
    return this.id.equals(otherTask.getId()) && this.title.equalsIgnoreCase(otherTask.getTitle());
  }
  
  @Override
  public int hashCode() {
    return this.title.toLowerCase().hashCode() + this.properties.stream().mapToInt(TaskProperty::hashCode).sum();
  }
  
  @Override
  public String toString() {
    return String.format("Task[%s, %s, %s]", this.title, this.description, this.properties);
  }
  
  public void setId(Long id) {
    throw new UnsupportedOperationException("Cannot set id on Task");
  }
  
}