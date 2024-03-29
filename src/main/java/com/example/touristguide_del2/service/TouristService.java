package com.example.touristguide_del2.service;

import com.example.touristguide_del2.model.TouristAttraction;
import com.example.touristguide_del2.repository.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    @Autowired
    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getAllAttractions() {
        return touristRepository.getAllAttractions();
    }

    public TouristAttraction getAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    }

    public TouristAttraction addAttraction(TouristAttraction attraction) {
        return touristRepository.addAttraction(attraction);
    }

    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
        return touristRepository.updateAttraction(name, updatedAttraction);
    }

    public boolean deleteAttraction(String name) {
        return touristRepository.deleteAttraction(name);
    }

    public List<String> getTagsForAttraction(String attractionName) {
        return touristRepository.getTagsForAttraction(attractionName);
    }

    public List<String> getCities() {
        return touristRepository.getCities();
    }

    public List<String> getTags() {
        return touristRepository.getTags();
    }
}
