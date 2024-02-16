package com.example.touristguide_del2.Controller;

import com.example.touristguide_del2.Model.TouristAttraction;
import com.example.touristguide_del2.Service.TouristService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TouristController {
    private final TouristService touristService;
    private TouristAttraction attraction;;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping(("/attractions"))
    public ResponseEntity<List<TouristAttraction>> getAttractions() {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        return ResponseEntity.ok(attractions);
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getDescriptionForAttraction(@PathVariable String name) {
        TouristAttraction attraction = touristService.getAttractionByName(name);

        if (attraction != null) {
            String description = attraction.getDescription();
            return ResponseEntity.ok(description);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction attraction) {
        TouristAttraction addedAttraction = touristService.addAttraction(attraction);
        return ResponseEntity.status(201).body(addedAttraction);
    }

    @PostMapping("/update")
    public ResponseEntity<TouristAttraction> updateAttraction(@RequestBody TouristAttraction updatedAttraction) {
        TouristAttraction attraction = touristService.updateAttraction(updatedAttraction.getName(), updatedAttraction);
        if (attraction != null) {
            return ResponseEntity.ok(attraction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/attractions/delete/{name}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable String name) {
        boolean deleted = touristService.deleteAttraction(name);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}


