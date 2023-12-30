package com.tsnt.repositories;

import com.tsnt.entities.Property;
import com.tsnt.entities.PropertyValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyValueRepository extends JpaRepository<PropertyValue, Long> {
  
  Page<PropertyValue> findAllByProperty(Property property, Pageable pageable);
  
}
