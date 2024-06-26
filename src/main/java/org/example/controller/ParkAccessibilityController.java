package org.example.controller;

import org.example.exception.ResourceNotFoundException;
import org.example.model.ParkAccessibility;
import org.example.service.ParkAccessibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Optional;


@RestController
@RequestMapping("/api") // http://localhost:8080/api/
public class ParkAccessibilityController {
    private ParkAccessibilityService parkAccessibilityService;
    static HashMap<String, Object> message = new HashMap<>();


    @Autowired
    public void setParkAccessibilityService(ParkAccessibilityService parkAccessibilityService) {
        this.parkAccessibilityService = parkAccessibilityService;
    }


    /**
     * Retrieves the accessibility details for a specific theme park by its park ID and accessibility ID.
     *
     * @param parkId The ID of the theme park whose accessibility details the user is trying to retrieve.
     * @param parkAccessibilityId The ID of the relevant accessibility details to retrieve.
     * @return ResponseEntity with the theme park's accessibility details and a success message if found (HTTP 200), or an error message and HTTP 404 if not found.
     */
    @GetMapping(path = "/parks/{parkId}/parkaccessibility/{parkAccessibilityId}/")
    public ResponseEntity<?> getParkAccessibilityByParkIdAndId(@PathVariable(value = "parkId") Long parkId, @PathVariable(value = "parkAccessibilityId") Long parkAccessibilityId) {

        try {
            Optional<ParkAccessibility> parkAccessibilityOptional = parkAccessibilityService.getParkAccessibilityByParkIdAndId(parkId, parkAccessibilityId);
            message.put("message", "success");
            message.put("data", parkAccessibilityOptional);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch(ResourceNotFoundException resourceNotFoundException) {
            message.put("message", "Park accessibility details with id " + parkAccessibilityId + " not found");
            return new ResponseEntity<>(message.get("message"), HttpStatus.NOT_FOUND);
        }
    }
}
