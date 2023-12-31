package com.tsnt.services;

import com.tsnt.entities.Property;
import com.tsnt.repositories.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PropertyServiceTest {
  
  @Autowired
  private PropertyRepository propertyRepository;
  
  private PropertyService propertyService;
  
  @BeforeEach
  void setUp() {
    propertyService = new PropertyService(propertyRepository);
  }
  
  @Test
  void getAllProperties() {
    propertyRepository.saveAll(List.of(
        new Property("property1"),
        new Property("property2"),
        new Property("property3")
    ));
    
    List<Property> properties = propertyService.getAllProperties();
    assertEquals(3, properties.size());
  }
  
  @Test
  void createProperty() {
    Property property = new Property("property1");
    propertyService.createProperty(property);
    
    List<Property> properties = propertyService.getAllProperties();
    assertEquals(1, properties.size());
  }
  
  @Test
  void updateProperty() {
    Property property = new Property("property1");
    propertyService.createProperty(property);
    
    property.setName("property2");
    propertyService.updateProperty(property.getId(), property);
    
    List<Property> properties = propertyService.getAllProperties();
    assertEquals(1, properties.size());
    assertEquals("property2", properties.get(0).getName());
  }
  
  @Test
  void deleteProperty() {
    Property property = new Property("property1");
    propertyService.createProperty(property);
    
    propertyService.deleteProperty(property.getId());
    
    List<Property> properties = propertyService.getAllProperties();
    assertTrue(properties.isEmpty());
  }
  
}
