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
   * Gets all properties.
   * @return a list of all properties
   */
  @Transactional(readOnly = true)
  public List<Property> getAllProperties() {
    return propertyRepository.findAll();
  }
  
  /**
   * Updates a property's name and property values.
   * @param id the id of the property to update
   * @param propertyDetails the property details to update
   * @return the updated property
   */
  @Transactional
  public Property updateProperty(Long id, Property propertyDetails) {
    Property property = propertyRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("Property with id " + id + " does not exist"));
    
    if (propertyDetails.getName() != null)
      property.setName(propertyDetails.getName());
    if (propertyDetails.getPropertyValues() != null)
      property.setPropertyValues(propertyDetails.getPropertyValues());
    
    return propertyRepository.save(property);
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
