package com.tsnt.dtos;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {
  
  private Long id;
  private String name;
  private Set<PropertyValueDto> propertyValues;
  
}
