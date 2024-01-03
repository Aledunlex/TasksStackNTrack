package com.tsnt.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyValueDto {
  
  private Long id;
  
  @NotBlank(message = "Value is required")
  private String value;
  private PropertyDto property;
    
}
