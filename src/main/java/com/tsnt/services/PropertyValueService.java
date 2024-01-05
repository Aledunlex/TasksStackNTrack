package com.tsnt.services;

import com.tsnt.dtos.PropertyValueDto;
import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import com.tsnt.repositories.PropertyValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Provides services for property values (CRUD operations).
 */
@Service
public class PropertyValueService {
  
  /**
   * The property value repository.
   */
  private final PropertyValueRepository propertyValueRepository;
  
  @Autowired
  private PropertyService propertyService;
  
  /**
   * Creates a new property value service.
   * @param propertyValueRepository the property value repository
   */
  @Autowired
  public PropertyValueService(PropertyValueRepository propertyValueRepository) {
    this.propertyValueRepository = propertyValueRepository;
  }
  
  /**
   * Creates a property value after checking that it does not already exist.
   * @param propertyValue the property value to create
   * @return the created property value
   */
  @Transactional
  public PropertyValue createPropertyValue(PropertyValue propertyValue) {
    if (propertyValue.getId() != null && propertyValueRepository.existsById(propertyValue.getId()))
      throw new IllegalStateException("Property value " + propertyValue.getId() + " already exists");
    
    return propertyValueRepository.save(propertyValue);
  }
  
  /**
   * Gets a property value by id.
   * @param id the id of the property value to get
   * @return the property value with the given id
   */
  @Transactional(readOnly = true)
  public Optional<PropertyValue> getPropertyValueById(Long id) {
    return propertyValueRepository.findById(id);
  }
  
  /**
   * Gets all property values by property.
   * @param property the property of the property value to get
   * @return the property values with the given property
   */
  @Transactional(readOnly = true)
  public List<PropertyValue> getPropertyValueByProperty(Property property) {
    Page<PropertyValue> propertyValuesPaged =  propertyValueRepository.findAllByProperty(property, null);
    return propertyValuesPaged.getContent();
  }
  
  /**
   * Deletes a property value by id.
   * @param id the id of the property value to delete
   */
  @Transactional
  public void deletePropertyValue(Long id) {
    propertyValueRepository.deleteById(id);
  }
  
  public PropertyValue updatePropertyValueFrom(PropertyValueDto propertyValueDto) {
    PropertyValue propertyValue = propertyValueRepository.findById(propertyValueDto.getId())
        .orElse(null);
    
    if (propertyValue != null) {
      Property property = propertyService.updateOrCreatePropertyFrom(propertyValueDto.getProperty());
      propertyValue.setValue(propertyValueDto.getValue());
      propertyValue.setProperty(property);
    } else {
      propertyValue = createPropertyValueFrom(propertyValueDto);
    }
    
    return propertyValueRepository.save(propertyValue);
  }
  
  public PropertyValue createPropertyValueFrom(PropertyValueDto propertyValueDto) {
    Property property = propertyService.updateOrCreatePropertyFrom(propertyValueDto.getProperty());
    return new PropertyValue(propertyValueDto.getValue(), property);
  }
}
