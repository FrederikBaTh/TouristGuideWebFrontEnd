package com.example.touristguide_del2.repository;

import com.example.touristguide_del2.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();

    private static final List<String> cities = Arrays.asList("København", "Helsingør", "Roskilde","Paris","Barcelona","Skagen");
    private static final List<String> tags = Arrays.asList("Bygning", "Børnevenlig", "Kirke", "Gratis","Dyrt","Natur","Billigt");


    //constructor
    public TouristRepository() {

        attractions.add(new TouristAttraction("Runde Tårn", "Observatorie fra det 17. århundrede.","København", Arrays.asList("Bygning", "Børnevenlig")));
        attractions.add(new TouristAttraction("Kronborg slot", "Stort og smukt slot.","Helsingør", List.of("Børnevenlig")));
        attractions.add(new TouristAttraction("Roskilde domkirke", "Gammelt og flot kirke.","Roskilde", Arrays.asList("Kirke", "Gratis")));
    }

    public List<TouristAttraction> getAllAttractions() {
        return attractions;
    }
    public List<String> getCities() {
        return cities;
    }

    public List<String> getTags() {
        return tags;
    }
    public TouristAttraction getAttractionByName(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equals(name)) {
                return attraction;
            }
        }
        return null;
    }

    public TouristAttraction addAttraction(TouristAttraction attraction) {
        attractions.add(attraction);
        return attraction;
    }

    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
        for (int i = 0; i < attractions.size(); i++) {
            if (attractions.get(i).getName().equals(name)) {
                attractions.set(i, updatedAttraction);
                return updatedAttraction;
            }
        }
        return null;
    }

    public boolean deleteAttraction(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equals(name)) {
                attractions.remove(attraction);
                return true;
            }
        }
        return false;
    }


    public TouristRepository(TouristRepository repository) {
    }

    public List<String> getTagsForAttraction(String attractionName) {
        TouristAttraction attraction = getAttractionByName(attractionName);

        if (attraction != null) {
            return attraction.getTags();
        } else {
            return Collections.emptyList();
        }

    }






}
