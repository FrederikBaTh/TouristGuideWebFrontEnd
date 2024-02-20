package com.example.touristguide_del2.Controller;

import com.example.touristguide_del2.Model.TouristAttraction;
import com.example.touristguide_del2.Service.TouristService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TouristController {
    private final TouristService touristService;
    private TouristAttraction attraction;;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("/attractions")
    public String getAllAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("touristAttractions", attractions);
        return "attractionList"; // Assuming there is a view named "attractionList"
    }


    @GetMapping("/tags")
    public String showTagsPage(@RequestParam String attractionName, Model model) {
        List<String> tags = touristService.getTagsForAttraction(attractionName);
        model.addAttribute("attractionName", attractionName);
        model.addAttribute("tags", tags);
        return "tags";
    }



    @PostMapping("/saveAttraction")
    public String saveAttraction(@RequestParam String name, @RequestParam String description, @RequestParam String city, @RequestParam List<String> tags) {
        TouristAttraction newAttraction = new TouristAttraction(name, description, city, tags);
        touristService.addAttraction(newAttraction);
        return "redirect:/attractions";
    }


    @GetMapping("/addAttraction")
    public String someEndpoint(Model model) {
        // Fetch the list of cities and tags from the service
        List<String> cities = touristService.getCities();
        List<String> tags = touristService.getTags();

        // Add them to the model to be used in your Thymeleaf template
        model.addAttribute("cities", cities);
        model.addAttribute("tags", tags);

        // Return the name of your Thymeleaf template
        return "addAttraction";
    }


    @GetMapping("/{name}/edit")
    public String showEditForm(@PathVariable String name, Model model) {
        // Retrieve the tourist attraction by name from the service
        TouristAttraction attraction = touristService.getAttractionByName(name);

        // Add the attraction to the model for Thymeleaf to use in the form
        model.addAttribute("attraction", attraction);

        return "updateAttraction"; // Assuming there is a view named "editAttraction"
    }
    @PostMapping("/update")
    public String updateAttraction(@RequestParam String name, @ModelAttribute TouristAttraction updatedAttraction) {
        touristService.updateAttraction(name, updatedAttraction);
        return "redirect:/attractions";
    }


}


