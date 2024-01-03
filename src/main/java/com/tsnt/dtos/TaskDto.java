package com.tsnt.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
  
  private Long id;
  
  @NotBlank(message = "Task title is mandatory")
  private String title;
  private String description;
  private Date creationDate;
  
  
  private Set<TaskPropertyDto> taskProperties;
  
}
