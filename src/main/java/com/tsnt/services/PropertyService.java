package com.tsnt.services;

import com.tsnt.entities.Property;
import com.tsnt.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Provides services for properties (CRUD operations).
 */
@Service
public class PropertyService {
  
  /**
   * The property repository.
   */
  private final PropertyRepository propertyRepository;
  
  /**
   * Creates a new property service.
   * @param propertyRepository the property repository
   */
  @Autowired
  public PropertyService(PropertyRepository propertyRepository) {
    this.propertyRepository = propertyRepository;
  }
  
  /**
   * Creates a property after checking that it does not already exist and formatting its name.
   * @param property the property to create
   * @return the created property
   */
  @Transactional
  public Property createProperty(Property property) {
    if (propertyRepository.findByNameIgnoreCase(property.getName()).isPresent())
      throw new IllegalStateException("Property " + property.getName() + " already exists");
    
    property.setName(
        property.getName().trim().substring(0, 1).toUpperCase() + property.getName().substring(1)
            .toLowerCase()
            .replaceAll("_", " ")
            .replaceAll("\\s+", " ")
            .replaceAll("([A-Z])", " $1")
            .trim());
    
    return propertyRepository.save(property);
  }
  
  /**
   * Gets a property by id.
   * @param id the id of the property to get
   * @return the property with the given id
   */
  @Transactional(readOnly = true)
  public Optional<Property> getPropertyById(Long id) {
    return propertyRepository.findById(id);
  }
  
  /**
   * Gets a property by name (case-insensitive).
   * @param name the name of the property to get
   * @return the property with the given name
   */
  @Transactional(readOnly = true)
  public Optional<Property> getPropertyByName(String name) {
    return propertyRepository.findByNameIgnoreCase(name);
  }
  
  /**
   * Gets all properties ordered by name.
   * @return a list of all properties ordered by name
   */
  @Transactional(readOnly = true)
  public List<Property> getAllPropertiesOrderedByName() {
    return propertyRepository.findAllByOrderByNameAsc(null).getContent();
  }
  
  /**
   * Gets all properties containing the given name ordered by name (case-insensitive).
   * @param name the name of the properties to get
   * @return a list of all properties containing the given name ordered by name
   */
  @Transactional(readOnly = true)
  public List<Property> getAllPropertiesByNameContainingOrderedByName(String name) {
    return propertyRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(name, null).getContent();
  }
  
  /**
   * Deletes a property and all of its property values.
   * @param id the id of the property to delete
   */
  @Transactional
  public void deleteProperty(Long id) {
    Property property = propertyRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("Property with id " + id + " does not exist"));
    propertyRepository.delete(property);
  }
  
}
