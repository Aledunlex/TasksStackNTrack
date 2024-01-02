package com.tsnt.mappers;

import com.tsnt.dtos.PropertyDto;
import com.tsnt.entities.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
  
  PropertyDto propertyToPropertyDto(Property property);
  
  @Mapping(target = "propertyValues", ignore = true)
  Property propertyDtoToProperty(PropertyDto propertyDto);
  
}

