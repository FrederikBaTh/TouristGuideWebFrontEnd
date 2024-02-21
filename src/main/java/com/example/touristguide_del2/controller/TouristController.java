package com.example.touristguide_del2.controller;

import com.example.touristguide_del2.model.TouristAttraction;
import com.example.touristguide_del2.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class TouristController {
    private final TouristService touristService;
    private TouristAttraction attraction;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("/attractions")
    public String getAllAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("touristAttractions", attractions);
        return "attractionList";
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
        List<String> cities = touristService.getCities();
        List<String> tags = touristService.getTags();
        model.addAttribute("cities", cities);
        model.addAttribute("tags", tags);
        return "addAttraction";
    }


    @GetMapping("{name}/edit")
    public String showEditForm(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        List<String> cities = touristService.getCities();
        List<String> tags = touristService.getTags();

        model.addAttribute("touristAttraction", attraction);
        model.addAttribute("cities", cities);
        model.addAttribute("tags", tags);

        return "updateAttraction";
    }




    @PostMapping("/update")
    public String updateAttraction(@RequestParam String name, @ModelAttribute TouristAttraction updatedAttraction) {
        touristService.updateAttraction(name, updatedAttraction);
        return "redirect:/attractions";
    }



    @GetMapping("/{name}/delete")
    public String DeleteConfirmation(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        model.addAttribute("touristAttraction", attraction);
        return "deleteAttraction";
    }

    @PostMapping("/{name}/delete")
    public String deleteAttractionPost(@PathVariable String name) {
        touristService.deleteAttraction(name);
        return "redirect:/attractions";
    }

}


