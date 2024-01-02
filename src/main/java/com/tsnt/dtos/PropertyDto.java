package com.tsnt.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {
  
  private Long id;
  private String name;
  /* No backward reference to PropertyValueDto to avoid circular dependency */
  
}
