package com.tsnt.repositories;

import com.tsnt.entities.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
  
  Optional<Property> findByNameIgnoreCase(String name);
  
  Page<Property> findAllByOrderByNameAsc(Pageable pageable);
  
  Page<Property> findAllByNameContainingIgnoreCaseOrderByNameAsc(String name, Pageable pageable);
  
}
