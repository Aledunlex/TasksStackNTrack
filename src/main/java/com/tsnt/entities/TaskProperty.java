package com.tsnt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class TaskProperty {
  
  private static final String NEW_PROPERTY = "New Property";
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToMany(mappedBy = "properties")
  private Set<Task> tasks = new HashSet<>();
  
  private String propertyValue;
  
  @Column(nullable = false)
  private final String name;
  
  public TaskProperty() {
    this.name = NEW_PROPERTY;
  }
  
  public TaskProperty(String name) {
    this.name = name;
  }
  
  public TaskProperty(String name, String propertyValue) {
    this.name = name;
    this.propertyValue = propertyValue;
  }
  
  public TaskProperty(String name, String propertyValue, Set<Task> tasks) {
    this.name = name;
    this.tasks = tasks;
  }
  
  public void addTask(Task task) {
    tasks.add(task);
  }
  
  public void addToOtherProperty(TaskProperty property) {
    this.propertyValue += property.getPropertyValue();
  }
  
  public void subtractFromOtherProperty(TaskProperty property) {
    this.propertyValue = this.propertyValue.replace(property.getPropertyValue(), "");
  }
  
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }
    if (!(other instanceof TaskProperty)) {
      return false;
    }
    TaskProperty otherProperty = (TaskProperty) other;
    return this.name.equalsIgnoreCase(otherProperty.getName());
  }
  
  @Override
  public int hashCode() {
    return this.name.toLowerCase().hashCode();
  }
  
  @Override
  public String toString() {
    return String.format("TaskProperty[%s, %s]", this.name, this.propertyValue);
  }
  
  public void setId(Long id) {
    throw new UnsupportedOperationException("Cannot set id on TaskProperty");
  }
  
}
