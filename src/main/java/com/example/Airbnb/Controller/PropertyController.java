package com.example.Airbnb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Airbnb.Model.Property;
import com.example.Airbnb.Service.PropertyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id)
                .map(property -> new ResponseEntity<>(property, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/properties")
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @PostMapping("properties")
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        Property savedProperty = propertyService.saveProperty(property);
        return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);
    }

    @DeleteMapping("properties/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
            propertyService.deleteProperty(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("properties/updateName")
    public ResponseEntity<Property> updatePropertyName(
                @RequestParam(name = "id") Long id,
                @RequestParam(name = "name") String name) {

            // Retrieve the existing property
            Optional<Property> optionalProperty = propertyService.getPropertyById(id);
            if (optionalProperty.isPresent()) {
                Property existingProperty = optionalProperty.get();

                // Update the name of the property
                existingProperty.setName(name);

                // Save the updated property
                Property updatedProperty = propertyService.saveProperty(existingProperty);
                return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
            } else {
                // Property not found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
}

