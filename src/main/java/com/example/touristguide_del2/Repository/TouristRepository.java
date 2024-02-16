package com.example.touristguide_del2.Repository;

import com.example.touristguide_del2.Model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

        private List<TouristAttraction> attractions = new ArrayList<>();

        //constructor
        public TouristRepository() {

            attractions.add(new TouristAttraction("Runde Tårn", "Runde Tårn er et observatorie fra det 17. århundrede."));
            attractions.add(new TouristAttraction("Nyhavn", "Nyhavn er en farverig kanal i København."));
            attractions.add(new TouristAttraction("Tivoli", "Tivoli er en forlystelsespark i København."));
        }

        public List<TouristAttraction> getAllAttractions() {
            return attractions;
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

        private TouristRepository repository;


        public TouristRepository(TouristRepository repository) {
            this.repository = repository;
        }





    }

