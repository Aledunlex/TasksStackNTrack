package com.tsnt.repositories;

import com.tsnt.entities.TaskProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskPropertyRepository extends JpaRepository<TaskProperty, Long> {
  
  Page<TaskProperty> findByTaskId(Long taskId, Pageable pageable);
  
  Page<TaskProperty> findByPropertyValueId(Long propertyValueId, Pageable pageable);
  
  Page<TaskProperty> findByTaskIdAndPropertyValueId(Long taskId, Long propertyValueId, Pageable pageable);
  
  Optional<TaskProperty> findById(Long id);
  
}
