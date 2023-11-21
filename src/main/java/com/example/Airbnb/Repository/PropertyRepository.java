package com.example.Airbnb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Airbnb.Model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // spring provides all the basic crud operations. No Coding!
}

