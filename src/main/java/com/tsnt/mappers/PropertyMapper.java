package com.tsnt.mappers;

import com.tsnt.dtos.PropertyDto;
import com.tsnt.entities.Property;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
  
  PropertyDto propertyToPropertyDto(Property property);
  
  Property propertyDtoToProperty(PropertyDto propertyDto);
  
}

