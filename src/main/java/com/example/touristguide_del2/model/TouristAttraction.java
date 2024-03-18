package com.example.touristguide_del2.model;

import java.util.List;

public class TouristAttraction {

    private int id;
    private String name;
    private String description;
    private String city;
    private String cityName;
    private List<String> tags;

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
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", by='" + city + '\'' +
                ", tags=" + tags +
                '}';
    }
}
