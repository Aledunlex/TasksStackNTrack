package com.tsnt.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPropertyDto {
  
  private Long id;
  private TaskDto task;
  private PropertyValueDto propertyValue;
  
}
