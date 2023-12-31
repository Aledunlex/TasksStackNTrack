package com.tsnt.repositories;

import com.tsnt.entities.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PropertyRepositoryTest {
  
  @Autowired
  private PropertyRepository propertyRepository;
  
  @Test
  void findAllByOrderByNameAsc() {
    Property propertyOne = new Property("Property One");
    Property propertyTwo = new Property("Property Two");
    
    propertyRepository.save(propertyTwo);
    propertyRepository.save(propertyOne);
    
    Page<Property> properties = propertyRepository.findAllByOrderByNameAsc(null);
    assertEquals(2, properties.getContent().size());
    assertEquals(propertyOne, properties.getContent().get(0));
    assertEquals(propertyTwo, properties.getContent().get(1));
  }
  
  @Test
  void findAllByNameContainingIgnoreCaseOrderByNameAsc() {
    String propertyNameOne = "Property One";
    String propertyNameTwo = "Property Two";
    String propertyNameThree = "Property Three";
    
    Property propertyOne = new Property(propertyNameOne);
    Property propertyTwo = new Property(propertyNameTwo);
    Property propertyThree = new Property(propertyNameThree);
    
    propertyRepository.save(propertyOne);
    propertyRepository.save(propertyTwo);
    propertyRepository.save(propertyThree);
    
    Page<Property> properties = propertyRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc("two", null);
    assertEquals(1, properties.getContent().size());
    assertEquals(propertyTwo, properties.getContent().get(0));
    
    properties = propertyRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc("property", null);
    assertEquals(3, properties.getContent().size());
  }
  
}
