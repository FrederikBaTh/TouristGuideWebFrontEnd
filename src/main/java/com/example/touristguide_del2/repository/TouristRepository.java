package com.example.touristguide_del2.repository;

import com.example.touristguide_del2.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class TouristRepository {



    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;






    private final List<TouristAttraction> attractions = new ArrayList<>();

    private static final List<String> cities = Arrays.asList("København", "Helsingør", "Roskilde","Paris","Barcelona","Skagen");
    private static final List<String> tags = Arrays.asList("Bygning", "Børnevenlig", "Kirke", "Gratis","Dyrt","Natur","Billigt");


    //constructor
    public TouristRepository() {

        attractions.add(new TouristAttraction("Runde Tårn", "Observatorie fra det 17. århundrede.","København", Arrays.asList("Bygning", "Børnevenlig")));
        attractions.add(new TouristAttraction("Kronborg slot", "Stort og smukt slot.","Helsingør", List.of("Børnevenlig")));
        attractions.add(new TouristAttraction("Roskilde domkirke", "Gammelt og flot kirke.","Roskilde", Arrays.asList("Kirke", "Gratis")));
    }

    /*public List<TouristAttraction> getAllAttractions() {
        return attractions;
    }
    */

    public List<TouristAttraction> getAllAttractions() {
        List<TouristAttraction> attractions = new ArrayList<>();
        String query = "SELECT id, name, description, city_name FROM attractions";

        try (Connection connection = DriverManager.getConnection(db_url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String cityName = resultSet.getString("city_name");

                // Create TouristAttraction object and add it to the list
                TouristAttraction attraction = new TouristAttraction(id, name, description, cityName);
                attractions.add(attraction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }





    public List<String> getCities() {
        return cities;
    }

    public List<String> getTags() {
        return tags;
    }
    /*public TouristAttraction getAttractionByName(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equals(name)) {
                return attraction;
            }
        }
        return null;
    }
    */

    public TouristAttraction getAttractionByName(String name) {
        String query = "SELECT id, name, description, city_name FROM attractions WHERE name = ?";
        try (Connection connection =  DriverManager.getConnection(db_url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    String cityName = resultSet.getString("city_name");
                    return new TouristAttraction(id, name, description, cityName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   /* public TouristAttraction addAttraction(TouristAttraction attraction) {
        attractions.add(attraction);
        return attraction;
    }
*/
   public TouristAttraction addAttraction(TouristAttraction attraction) {
       String attractionQuery = "INSERT INTO attractions (name, description, city_name) VALUES (?, ?, ?)";
       String tagQuery = "INSERT INTO attraction_tags (attraction_id, tag) VALUES (?, ?)";

       try (Connection connection = DriverManager.getConnection(db_url, username, password);
            PreparedStatement attractionStatement = connection.prepareStatement(attractionQuery, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement tagStatement = connection.prepareStatement(tagQuery)) {

           // Insert attraction data
           attractionStatement.setString(1, attraction.getName());
           attractionStatement.setString(2, attraction.getDescription());
           attractionStatement.setString(3, attraction.getCity());

           // Execute the insertion query for attraction
           int affectedRows = attractionStatement.executeUpdate();

           // Check if any rows were affected
           if (affectedRows == 0) {
               throw new SQLException("Adding attraction failed, no rows affected.");
           }

           // Retrieve the generated attraction ID
           try (ResultSet generatedKeys = attractionStatement.getGeneratedKeys()) {
               if (generatedKeys.next()) {
                   // Retrieve the generated ID and set it in the attraction object
                   int attractionId = generatedKeys.getInt(1);
                   attraction.setId(attractionId);

                   // Insert tags associated with the attraction
                   for (String tag : attraction.getTags()) {
                       tagStatement.setInt(1, attractionId);
                       tagStatement.setString(2, tag);
                       tagStatement.addBatch(); // Add to batch for batch insertion
                   }

                   // Execute batch insertion for tags
                   tagStatement.executeBatch();
               } else {
                   throw new SQLException("Adding attraction failed, no ID obtained.");
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }

       return attraction;
   }


   /* public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equals(attraction.getName())) {
                attraction.setDescription(updatedAttraction.getDescription());
                attraction.setCity(updatedAttraction.getCity());
                attraction.setTags(updatedAttraction.getTags());
                return attraction;
            }
        }
        return updatedAttraction;
    }
*/

    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
        String updateAttractionQuery = "UPDATE attractions SET description = ?, city_name = ? WHERE name = ?";
        String deleteTagsQuery = "DELETE FROM attraction_tags WHERE attraction_id = ?";
        String insertTagsQuery = "INSERT INTO attraction_tags (attraction_id, tag) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(db_url, username, password);
             PreparedStatement updateAttractionStatement = connection.prepareStatement(updateAttractionQuery);
             PreparedStatement deleteTagsStatement = connection.prepareStatement(deleteTagsQuery);
             PreparedStatement insertTagsStatement = connection.prepareStatement(insertTagsQuery)) {

            // Set parameters for the update attraction PreparedStatement
            updateAttractionStatement.setString(1, updatedAttraction.getDescription());
            updateAttractionStatement.setString(2, updatedAttraction.getCity());
            updateAttractionStatement.setString(3, name); // Use the provided name

            // Execute the update attraction query
            int rowsAffected = updateAttractionStatement.executeUpdate();

            // Check if any rows were affected
            if (rowsAffected > 0) {
                // Delete existing tags for the attraction
                deleteTagsStatement.setInt(1, updatedAttraction.getId());
                deleteTagsStatement.executeUpdate();

                // Insert new tags for the attraction using batch insert
                List<String> tags = updatedAttraction.getTags();
                for (String tag : tags) {
                    insertTagsStatement.setInt(1, updatedAttraction.getId());
                    insertTagsStatement.setString(2, tag);
                    insertTagsStatement.addBatch(); // Add the insert statement to the batch
                }
                // Execute the batch insert
                insertTagsStatement.executeBatch();

                // Return the updated attraction
                return updatedAttraction;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the error instead
        }

        return null;
    }


/*
    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
        // Query to check if an attraction with the provided name exists
        String attractionExistsQuery = "SELECT * FROM attractions WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(db_url, username, password);
             PreparedStatement existsStatement = connection.prepareStatement(attractionExistsQuery)) {

            // Set the parameter for the existsStatement
            existsStatement.setString(1, name);

            // Execute the query to check if the attraction exists
            try (ResultSet existsResultSet = existsStatement.executeQuery()) {
                if (!existsResultSet.next()) {
                    System.out.println("Attraction with name '" + name + "' does not exist.");
                    return null;
                }
            }

            // Compare existing data with updated data
            // Retrieve existing data from the database based on the provided name
            // Compare existing data with the data provided in the updatedAttraction object

            // Your code to perform the update operation...

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
*/

    public boolean deleteAttraction(String name) {
        String deleteTagsQuery = "DELETE FROM attraction_tags WHERE attraction_id IN (SELECT id FROM attractions WHERE name = ?)";
        String deleteAttractionQuery = "DELETE FROM attractions WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(db_url, username, password);
             PreparedStatement deleteTagsStatement = connection.prepareStatement(deleteTagsQuery);
             PreparedStatement deleteAttractionStatement = connection.prepareStatement(deleteAttractionQuery)) {

            // Set parameter for the delete tags PreparedStatement
            deleteTagsStatement.setString(1, name);

            // Execute the delete tags query
            int tagsDeleted = deleteTagsStatement.executeUpdate();

            // Set parameter for the delete attraction PreparedStatement
            deleteAttractionStatement.setString(1, name);

            // Execute the delete attraction query
            int attractionDeleted = deleteAttractionStatement.executeUpdate();

            // Check if both the attraction and its associated tags were deleted
            if (attractionDeleted > 0 && tagsDeleted >= 0) {
                return true; // Attraction and associated tags successfully deleted
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the error instead
        }

        return false; // Attraction not found or deletion failed
    }




    public TouristRepository(TouristRepository repository) {
    }

    public List<String> getTagsForAttraction(String attractionName) {
        String query = "SELECT tag FROM attraction_tags INNER JOIN attractions " +
                "ON attraction_tags.attraction_id = attractions.id " +
                "WHERE attractions.name = ?";
        List<String> tags = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(db_url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, attractionName);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tags.add(resultSet.getString("tag"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }





}
