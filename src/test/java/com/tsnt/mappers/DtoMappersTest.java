package com.tsnt.mappers;

import com.tsnt.dtos.PropertyDto;
import com.tsnt.dtos.PropertyValueDto;
import com.tsnt.dtos.TaskDto;
import com.tsnt.dtos.TaskPropertyDto;
import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import com.tsnt.entities.Task;
import com.tsnt.entities.TaskProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({TaskMapperImpl.class, TaskPropertyMapperImpl.class, TsntPropertyMapperImpl.class, PropertyValueMapperImpl.class})
class DtoMappersTest {
  
  @Autowired
  private TaskMapper taskMapper;
  
  @Autowired
  private TaskPropertyMapper taskPropertyMapper;
  
  @Autowired
  private TsntPropertyMapper tsntPropertyMapper;
  
  @Autowired
  private PropertyValueMapper propertyValueMapper;
  
  @Test
  public void taskToTaskDtoMapper() {
    Task task = new Task("New Task");
    task.setId(1L);
    task.setDescription("description");
    task.addTaskProperty(new TaskProperty(new PropertyValue("New PropertyValue", new Property("New Property"))));
    task.addTaskProperty(new TaskProperty(new PropertyValue("New PropertyValue 2", new Property("New Property 2"))));
    
    TaskDto taskDto = taskMapper.taskToTaskDto(task);
    
    assertEquals(task.getId(), taskDto.getId());
    assertEquals(task.getTitle(), taskDto.getTitle());
    assertEquals(task.getDescription(), taskDto.getDescription());
    assertEquals(task.getTaskProperties().size(), taskDto.getTaskProperties().size());
    assertEquals(task.getCreationDate(), taskDto.getCreationDate());
  }
  
  @Test
  public void taskDtoToTaskMapper() {
    TaskDto taskDto = new TaskDto();
    taskDto.setId(1L);
    taskDto.setTitle("New Task");
    taskDto.setDescription("description");
    taskDto.setTaskProperties(Set.of(
        new TaskPropertyDto(0L, new PropertyValueDto(0L, "New PropertyValue", new PropertyDto())),
        new TaskPropertyDto(1L, new PropertyValueDto(1L, "New PropertyValue 2", new PropertyDto()))
    ));
    
    Task task = taskMapper.taskDtoToTask(taskDto);
    
    assertEquals(taskDto.getId(), task.getId());
    assertEquals(taskDto.getTitle(), task.getTitle());
    assertEquals(taskDto.getDescription(), task.getDescription());
    assertEquals(taskDto.getTaskProperties().size(), task.getTaskProperties().size());
    assertNotNull(task.getCreationDate());
    assertNull(taskDto.getCreationDate());
  }
  
  @Test
  public void taskPropertyToTaskPropertyDtoMapper() {
    TaskProperty taskProperty = new TaskProperty();
    taskProperty.setId(1L);
    taskProperty.setPropertyValue(new PropertyValue("New PropertyValue", new Property("New Property")));
    
    TaskPropertyDto taskPropertyDto = taskPropertyMapper.taskPropertyToTaskPropertyDto(taskProperty);
    PropertyValue retrievedPropertyValue = propertyValueMapper.propertyValueDtoToPropertyValue(taskPropertyDto.getPropertyValue());
    
    assertEquals(taskProperty.getId(), taskPropertyDto.getId());
    assertEquals(taskProperty.getPropertyValue(), retrievedPropertyValue);
  }
  
  @Test
  public void taskPropertyDtoToTaskPropertyMapper() {
    TaskPropertyDto taskPropertyDto = new TaskPropertyDto();
    taskPropertyDto.setId(1L);
    
    taskPropertyDto.setPropertyValue(new PropertyValueDto(0L, "New PropertyValue", new PropertyDto()));
    
    TaskProperty taskProperty = taskPropertyMapper.taskPropertyDtoToTaskProperty(taskPropertyDto);
    PropertyValueDto retrievedPropertyValueDto = propertyValueMapper.propertyValueToPropertyValueDto(taskProperty.getPropertyValue());
    
    assertEquals(taskPropertyDto.getId(), taskProperty.getId());
    assertEquals(taskPropertyDto.getPropertyValue(), retrievedPropertyValueDto);
  }
  
  @Test
  public void propertyToPropertyDtoMapper() {
    Property property = new Property("New Property");
    property.setId(1L);
    property.setPropertyValues(Set.of(new PropertyValue("New PropertyValue", property)));
    
    PropertyDto propertyDto = tsntPropertyMapper.propertyToPropertyDto(property);
    Set<PropertyValue> retrievedPropertyValues = property.getPropertyValues()
        .stream()
        .map(propertyValue -> propertyValueMapper.propertyValueDtoToPropertyValue(
            propertyValueMapper.propertyValueToPropertyValueDto(propertyValue))
        )
        .collect(Collectors.toSet());
    
    assertEquals(property.getId(), propertyDto.getId());
    assertEquals(property.getName(), propertyDto.getName());
    assertEquals(property.getPropertyValues(), retrievedPropertyValues);
  }
  
  @Test
  public void propertyDtoToPropertyMapper() {
    PropertyDto propertyDto = new PropertyDto();
    propertyDto.setId(1L);
    propertyDto.setName("New Property");
    
    Property property = tsntPropertyMapper.propertyDtoToProperty(propertyDto);
    
    assertEquals(propertyDto.getId(), property.getId());
    assertEquals(propertyDto.getName(), property.getName());
  }
  
  @Test
  public void propertyValueToPropertyValueDtoMapper() {
    PropertyValue propertyValue = new PropertyValue("New PropertyValue", new Property("New Property"));
    propertyValue.setId(1L);
    
    PropertyValueDto propertyValueDto = propertyValueMapper.propertyValueToPropertyValueDto(propertyValue);
    Property retrievedProperty = tsntPropertyMapper.propertyDtoToProperty(propertyValueDto.getProperty());
    
    assertEquals(propertyValue.getId(), propertyValueDto.getId());
    assertEquals(propertyValue.getValue(), propertyValueDto.getValue());
    assertEquals(propertyValue.getProperty(), retrievedProperty);
  }
  
  @Test
  public void propertyValueDtoToPropertyValueMapper() {
    PropertyValueDto propertyValueDto = new PropertyValueDto();
    propertyValueDto.setId(1L);
    propertyValueDto.setValue("New PropertyValue");
    propertyValueDto.setProperty(new PropertyDto(0L, "New Property"));
    
    PropertyValue propertyValue = propertyValueMapper.propertyValueDtoToPropertyValue(propertyValueDto);
    PropertyDto retrievedPropertyDto = tsntPropertyMapper.propertyToPropertyDto(propertyValue.getProperty());
    
    assertEquals(propertyValueDto.getId(), propertyValue.getId());
    assertEquals(propertyValueDto.getValue(), propertyValue.getValue());
    assertEquals(propertyValueDto.getProperty(), retrievedPropertyDto);
  }
  
}
