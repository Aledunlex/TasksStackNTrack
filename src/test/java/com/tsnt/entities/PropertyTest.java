package com.tsnt.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PropertyTest {
  
  @Test
  void createPropertyPlaceholderName() {
    Property property = new Property();
    assertEquals("New Property", property.getName());
  }
  
  @Test
  void createPropertyWithName() {
    String testProperty = "Test Property";
    Property property = new Property(testProperty);
    assertEquals(testProperty, property.getName());
  }
  
  @Test
  void addPropertyValue() {
    Property property = new Property();
    PropertyValue propertyValue = new PropertyValue();
    property.addPropertyValue(propertyValue);
    assertEquals(propertyValue, property.getPropertyValues().stream().findFirst().get());
  }
  
  @Test
  void equalsWithSameIdAndName() {
    Property property1 = new Property();
    property1.setId(1L);
    property1.setName("Test Property");
    Property property2 = new Property();
    property2.setId(1L);
    property2.setName("Test Property");
    assertEquals(property1, property2);
  }
  
  @Test
  void equalsWithDifferentIdAndSameName() {
    Property property1 = new Property();
    property1.setId(1L);
    property1.setName("Test Property");
    Property property2 = new Property();
    property2.setId(2L);
    property2.setName("Test Property");
    assertNotEquals(property1, property2);
  }
  
  @Test
  void equalsWithSameIdAndDifferentName() {
    Property property1 = new Property();
    property1.setId(1L);
    property1.setName("Test Property");
    Property property2 = new Property();
    property2.setId(1L);
    property2.setName("Test Property 2");
    assertNotEquals(property1, property2);
  }
  
}
