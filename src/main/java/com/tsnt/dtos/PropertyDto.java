package com.tsnt.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {
  
  private Long id;
  
  @NotBlank(message = "Property name is required")
  private String name;
  /* No backward reference to PropertyValueDto to avoid circular dependency */
  
}
