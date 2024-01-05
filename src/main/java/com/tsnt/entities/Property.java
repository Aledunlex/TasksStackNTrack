package com.tsnt.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Property {
  
  private final static String DEFAULT_PROPERTY = "New Property";
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
    return this.id.equals(otherProperty.getId());
  }
  
  @Override
  public int hashCode() {
    return this.id == null ? 0 : (this.id.hashCode() * this.getClass().hashCode());
  }
  
  @Override
  public String toString() {
    return String.format("Property[%s, %s]", this.id, this.name);
  }

}
