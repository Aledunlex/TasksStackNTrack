package com.tsnt.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
  
  private Long id;
  private String title;
  private String description;
  private Date creationDate;
  private Set<TaskPropertyDto> taskProperties;
  
}
