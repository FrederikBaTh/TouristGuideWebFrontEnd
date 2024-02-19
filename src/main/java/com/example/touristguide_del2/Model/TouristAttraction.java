package com.example.touristguide_del2.Model;


import java.util.List;

public class TouristAttraction {
    private String name;

    private String description;

    private String by;

    private List<String> tags;



    public TouristAttraction() {
    }

    public TouristAttraction(String name, String description,String by, List<String> tags) {
        this.name = name;
        this.description = description;
        this.by = by;
        this.tags = tags;
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
    public List<String> getTags() {
        return tags;
    }
    @Override
    public String toString() {
        return "TouristAttraction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }
}

