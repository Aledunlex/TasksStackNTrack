package com.tsnt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TaskProperty {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "property_value_id")
  private PropertyValue propertyValue;
  
  public TaskProperty() {}
  
  public TaskProperty(PropertyValue propertyValue) {
    this.propertyValue = propertyValue;
  }
  
  public TaskProperty(PropertyValue propertyValue, Task task) {
    this.propertyValue = propertyValue;
    this.task = task;
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
    if (this.id == null || otherProperty.getId() == null) {
      if (this.task == null || otherProperty.getTask() == null) {
        return this.task == otherProperty.getTask();
      }
      return this.task.equals(otherProperty.getTask());
    }
    if (this.task == null || otherProperty.getTask() == null) {
      return false;
    }
    return this.id.equals(otherProperty.getId()) && this.task.equals(otherProperty.getTask());
  }
  
  @Override
  public int hashCode() {
    return this.id == null ? 0 : this.id.hashCode();
  }
  
  @Override
  public String toString() {
    return String.format("TaskProperty[%s, %s]", this.id, this.propertyValue);
  }
  
}
