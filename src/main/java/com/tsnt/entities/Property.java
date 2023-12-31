package com.tsnt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Property {
  
  private final static String DEFAULT_PROPERTY = "New Property";
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String name;
  
  @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PropertyValue> propertyValues = new HashSet<>();
  
  public Property() {
    this.name = DEFAULT_PROPERTY;
  }
  
  public Property(String name) {
    this.name = name;
  }
  
  public void addPropertyValue(PropertyValue propertyValue) {
    propertyValues.add(propertyValue);
    propertyValue.setProperty(this);
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
    Property otherProperty = (Property) other;
    if (this.id == null || otherProperty.getId() == null) {
      return this.name.equalsIgnoreCase(otherProperty.getName());
    }
    return this.id.equals(otherProperty.getId()) && this.name.equalsIgnoreCase(otherProperty.getName());
  }
  
  @Override
  public int hashCode() {
    if (this.id == null) {
      return this.name.hashCode();
    }
    return this.id.hashCode() + this.name.hashCode();
  }
  
  @Override
  public String toString() {
    return String.format("Property[%s, %s]", this.id, this.name);
  }

}
