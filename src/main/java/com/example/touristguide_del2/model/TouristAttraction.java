package com.example.touristguide_del2.model;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

public class TouristAttraction {

    private int id;
    private String name;
    private String description;
    private String city;
    private String cityName;
    private List<String> tags;
    private Connection connection;

    public TouristAttraction() {
    }

    public TouristAttraction(String name, String description, String city, List<String> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public TouristAttraction(int id, String name, String description, String cityName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = cityName;

    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id= id;
    }

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName){
        this.cityName= cityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TouristAttraction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", cityName='" + cityName + '\'' +
                ", tags=" + tags +
                '}';
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TouristAttraction that = (TouristAttraction) object;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(city, that.city) &&
                Objects.equals(cityName, that.cityName) &&
                Objects.equals(tags, that.tags);
    }


    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
