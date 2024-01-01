package com.tsnt.services;

import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import com.tsnt.repositories.PropertyValueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PropertyValueServiceTest {
  
  @Autowired
  private PropertyValueRepository propertyValueRepository;
  
  private PropertyValueService propertyValueService;
  
  @BeforeEach
  void setUp() {
    propertyValueService = new PropertyValueService(propertyValueRepository);
  }
  
  @Test
  void getPropertyValueByProperty() {
    Property property1 = new Property("property1");
    Property property2 = new Property("property2");
    propertyValueRepository.saveAll(List.of(
        new PropertyValue("value1", property1),
        new PropertyValue("value2", property1),
        new PropertyValue("value3", property2)
    ));
    
    List<PropertyValue> propertyValues = propertyValueService.getPropertyValueByProperty(property1);
    assertEquals(2, propertyValues.size());
  }
  
  @Test
  void createPropertyValue() {
    PropertyValue propertyValue = new PropertyValue("value1", new Property("property1"));
    propertyValueService.createPropertyValue(propertyValue);
    
    Optional<PropertyValue> retrievedPropertyValue = propertyValueService.getPropertyValueById(propertyValue.getId());
    assertTrue(retrievedPropertyValue.isPresent());
    assertEquals("value1", retrievedPropertyValue.get().getValue());
    assertEquals("property1", retrievedPropertyValue.get().getProperty().getName());
  }
  
  @Test
  void updatePropertyValue() {
    Property property = new Property();
    
    PropertyValue propertyValue = new PropertyValue("value1", property);
    propertyValueService.createPropertyValue(propertyValue);
    
    String value2 = "value2";
    propertyValue.setValue(value2);
    
    Optional<PropertyValue> retrievedPropertyValue = propertyValueService.getPropertyValueById(propertyValue.getId());
    System.out.println(retrievedPropertyValue);
    assertEquals(value2, retrievedPropertyValue.get().getValue());
  }
  
  @Test
  void deletePropertyValue() {
    PropertyValue propertyValue = new PropertyValue("value1", new Property("property1"));
    propertyValueService.createPropertyValue(propertyValue);
    
    propertyValueService.deletePropertyValue(propertyValue.getId());
    
    Optional<PropertyValue> retrievedPropertyValue = propertyValueService.getPropertyValueById(propertyValue.getId());
    assertTrue(retrievedPropertyValue.isEmpty());
  }
  
}
