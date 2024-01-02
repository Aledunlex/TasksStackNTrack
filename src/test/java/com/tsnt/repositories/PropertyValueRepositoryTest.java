package com.tsnt.repositories;

import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PropertyValueRepositoryTest {
  
  @Autowired
  private PropertyValueRepository propertyValueRepository;
  
  @Test
  void findAllByProperty() {
    Property property = new Property("Test Property");
    PropertyValue propertyValue = new PropertyValue("Test Value", property);
    propertyValueRepository.save(propertyValue);
    
    Property property2 = new Property("Test Property 2");
    PropertyValue propertyValue2 = new PropertyValue("Test Value 2", property2);
    propertyValueRepository.save(propertyValue2);
    
    Page<PropertyValue> propertyValues = propertyValueRepository.findAllByProperty(property, null);
    assertEquals(1, propertyValues.getTotalElements());
    assertTrue(propertyValues.getContent().contains(propertyValue));
  }
  
  @Test
  void findAllByPropertyWithNullProperty() {
    Property property = new Property("Test Property");
    PropertyValue propertyValue = new PropertyValue("Test Value", property);
    propertyValueRepository.save(propertyValue);
    
    Property property2 = new Property("Test Property 2");
    PropertyValue propertyValue2 = new PropertyValue("Test Value 2", property2);
    propertyValueRepository.save(propertyValue2);
    
    Page<PropertyValue> propertyValues = propertyValueRepository.findAllByProperty(null, null);
    assertEquals(0, propertyValues.getTotalElements());
  }
  
}
