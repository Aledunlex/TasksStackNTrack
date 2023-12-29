package com.tsnt.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyValueTest {
  
  @Test
  void createPropertyValueWithoutValueAndSetIt() {
    String value = "New Value";
    PropertyValue property = new PropertyValue();
    property.setValue(value);
    
    assertEquals(value, property.getValue());
  }
  
  @Test
  void createPropertyValueWithValue() {
    String value = "New Value";
    PropertyValue property = new PropertyValue(value);
    
    assertEquals(value, property.getValue());
  }
  
  @Test
  void createPropertyValueWithoutValueOrPropertyAndSetThem() {
    String value = "New Value";
    Property property = new Property();
    PropertyValue propertyValue = new PropertyValue(value);
    propertyValue.setProperty(property);
    
    assertEquals(value, propertyValue.getValue());
    assertEquals(property, propertyValue.getProperty());
  }
  
  @Test
  void createPropertyValueWithValueAndProperty() {
    String value = "New Value";
    Property property = new Property();
    PropertyValue propertyValue = new PropertyValue(value, property);
    
    assertEquals(value, propertyValue.getValue());
    assertEquals(property, propertyValue.getProperty());
  }
  
}
