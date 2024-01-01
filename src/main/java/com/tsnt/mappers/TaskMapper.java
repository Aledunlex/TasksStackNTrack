package com.tsnt.mappers;

import org.mapstruct.Mapper;
import com.tsnt.dtos.TaskDto;
import com.tsnt.entities.Task;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  
  TaskDto taskToTaskDto(Task task);
  
  Task taskDtoToTask(TaskDto taskDto);
  
}

