package com.tsnt.mappers;

import com.tsnt.dtos.TaskPropertyDto;
import com.tsnt.entities.TaskProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskPropertyMapper {
  
  TaskPropertyDto taskPropertyToTaskPropertyDto(TaskProperty taskProperty);
  
  @Mapping(target = "task", ignore = true)
  TaskProperty taskPropertyDtoToTaskProperty(TaskPropertyDto taskPropertyDto);
  
}

