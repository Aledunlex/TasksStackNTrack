package com.tsnt.mappers;

import com.tsnt.dtos.TaskPropertyDto;
import com.tsnt.entities.TaskProperty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskPropertyMapper {
  
  TaskPropertyDto taskPropertyToTaskPropertyDto(TaskProperty taskProperty);
  
  TaskProperty taskPropertyDtoToTaskProperty(TaskPropertyDto taskPropertyDto);
  
}

