package com.tsnt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PropertyValue {
  
  private final static String DEFAULT_VALUE = "New Value";
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String value;
  
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "property_id")
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
    boolean equalValues = this.value.equalsIgnoreCase(otherPropertyValue.getValue());
    if (this.id == null || otherPropertyValue.getId() == null) {
      return equalValues;
    }
    return this.id.equals(otherPropertyValue.getId()) && equalValues;
  }
  
  @Override
  public int hashCode() {
    if (this.id == null) {
      return this.value.toLowerCase().hashCode();
    }
    return this.id.hashCode() + this.value.toLowerCase().hashCode();
  }
  
  @Override
  public String toString() {
    return String.format("PropertyValue[%s, %s]", this.id, this.value);
  }
  
}
