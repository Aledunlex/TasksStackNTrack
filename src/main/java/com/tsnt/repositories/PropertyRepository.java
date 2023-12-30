package com.tsnt.repositories;

import com.tsnt.entities.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
  
  Page<Property> findAllByOrderByNameAsc(Pageable pageable);
  
  Page<Property> findAllByNameContainingIgnoreCaseOrderByNameAsc(String name, Pageable pageable);
  
}
