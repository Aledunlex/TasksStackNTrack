package com.tsnt.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PropertyValue {
  
  private final static String DEFAULT_VALUE = "New Value";
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Column(nullable = false)
  private String value;
  
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "property_id", nullable = false)
  private Property property;
  
  public PropertyValue() {
    this.value = DEFAULT_VALUE;
  }
  
  public PropertyValue(String value) {
    this.value = value;
  }
  
  public PropertyValue(String value, Property property) {
    this.value = value;
    this.property = property;
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
    PropertyValue otherPropertyValue = (PropertyValue) other;
    if (this.id == null || otherPropertyValue.getId() == null) {
      return this.value.equalsIgnoreCase(otherPropertyValue.getValue());
    }
    return this.id.equals(otherPropertyValue.getId());
  }
  
  @Override
  public int hashCode() {
    return this.id == null ? 0 : (this.id.hashCode() * this.getClass().hashCode());
  }
  
  @Override
  public String toString() {
    return String.format("PropertyValue[%s, %s]", this.id, this.value);
  }
  
}
