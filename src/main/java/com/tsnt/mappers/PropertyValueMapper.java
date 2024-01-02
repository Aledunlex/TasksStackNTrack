package com.tsnt.mappers;

import com.tsnt.dtos.PropertyValueDto;
import com.tsnt.entities.PropertyValue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyValueMapper {
  
  PropertyValueDto propertyValueToPropertyValueDto(PropertyValue propertyValue);
  
  PropertyValue propertyValueDtoToPropertyValue(PropertyValueDto propertyValueDto);
  
}

