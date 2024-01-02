package com.tsnt.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyValueDto {
  
  private Long id;
  private String value;
  private PropertyDto property;
    
}
