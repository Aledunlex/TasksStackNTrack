package com.tsnt.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPropertyDto {
  
  private Long id;
  private PropertyValueDto propertyValue;
  /* No backward reference to TaskDto to avoid circular reference. */
  
}
