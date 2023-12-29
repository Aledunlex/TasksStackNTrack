package com.tsnt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TaskProperty {
  
  private static final String NEW_PROPERTY = "New Property";
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;
  
  private String propertyValue;
  
  @Column(nullable = false)
  private String name;
  
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
  
  public TaskProperty(String name, String propertyValue, Task task) {
    this.name = name;
    this.propertyValue = propertyValue;
    this.task = task;
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
  
}
