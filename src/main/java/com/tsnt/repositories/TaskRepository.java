package com.tsnt.repositories;

import com.tsnt.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  Page<Task> findAllByOrderByCreationDateDesc(Pageable pageable);
  
  // Finds all tasks with a title containing the given string
  Page<Task> findAllByTitleContainingIgnoreCaseOrderByCreationDateDesc(String title, Pageable pageable);
  
  // Finds all tasks with a description containing the given string
  Page<Task> findAllByDescriptionContainingIgnoreCaseOrderByCreationDateDesc(String description, Pageable pageable);
  
  // Finds all tasks who have a property with at least one TaskProperty whose Property has a name equals to the given string
  Page<Task> findAllByTaskPropertiesPropertyValuePropertyNameContainingIgnoreCaseOrderByCreationDateDesc(String name, Pageable pageable);

}
