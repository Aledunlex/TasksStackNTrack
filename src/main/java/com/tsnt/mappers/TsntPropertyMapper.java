package com.tsnt.mappers;

import com.tsnt.dtos.PropertyDto;
import com.tsnt.entities.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Maps Property entities to PropertyDto and reciprocally
 * Renamed from PropertyMapper to avoid name collision with PropertyMapper from OpenAPI generated code
 */
@Mapper(componentModel = "spring")
public interface TsntPropertyMapper {
  
  PropertyDto propertyToPropertyDto(Property property);
  
  @Mapping(target = "propertyValues", ignore = true)
  Property propertyDtoToProperty(PropertyDto propertyDto);
  
}

