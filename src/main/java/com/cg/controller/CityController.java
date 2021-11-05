package com.cg.controller;

import com.cg.model.City;
import com.cg.service.city.ICityService;
import com.cg.service.country.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private ICityService cityService;

    @Autowired
    private ICountryService countryService;

    @GetMapping("")
    public ModelAndView getList() {
        ModelAndView modelAndView = new ModelAndView("city/index");
        modelAndView.addObject("cities", cityService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("city/create");
        modelAndView.addObject("city", new City());
        modelAndView.addObject("countries", countryService.findAll());
        modelAndView.addObject("success", null);
        return modelAndView;
    }

    @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
    public ModelAndView save (@Validated @ModelAttribute("city") City city,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasFieldErrors()) {
            modelAndView.setViewName("city/create");
            modelAndView.addObject("countries", countryService.findAll());
            modelAndView.addObject("script", true);

        } else {
            if (cityService.existsByName(city.getName())) {
                modelAndView.setViewName("city/create");
                modelAndView.addObject("countries", countryService.findAll());
                modelAndView.addObject("error", "City already exists");

            } else {
                cityService.save(city);
                redirectAttributes.addFlashAttribute("success", "Successfully added new city");
                modelAndView.setViewName("redirect:/city");
            }
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id) {
        Optional<City> city = cityService.findById(id);

        if(city.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("city/edit");
            modelAndView.addObject("city", city.get());
            modelAndView.addObject("countries", countryService.findAll());
            modelAndView.addObject("success", null);
            return modelAndView;
        } else {
            return new ModelAndView("error.404");
        }
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateCity(@PathVariable Long id, @Validated @ModelAttribute("city") City city,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasFieldErrors()) {
            modelAndView.setViewName("city/edit");
            modelAndView.addObject("countries", countryService.findAll());
            modelAndView.addObject("script", true);

        } else {
            cityService.save(city);

            redirectAttributes.addFlashAttribute("success", "Cập nhật thành phố thành công");
            modelAndView.setViewName("redirect:/city");
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showFormDelete(@PathVariable Long id) {
        Optional<City> city = cityService.findById(id);

        if(city.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("city/delete");
            modelAndView.addObject("city", city.get());
            modelAndView.addObject("countries", countryService.findAll());
            modelAndView.addObject("success", null);
            return modelAndView;
        } else {
            return new ModelAndView("error.404");
        }
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteCity(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<City> city = cityService.findById(id);

        if (city.isPresent()) {
            cityService.remove(id);
            redirectAttributes.addFlashAttribute("success", "Xóa thành phố thành công");
            modelAndView.setViewName("redirect:/city");

            return modelAndView;
        } else {
            return new ModelAndView("error.404");
        }
    }

    @GetMapping("/info/{id}")
    public ModelAndView showFormInfo(@PathVariable Long id) {
        Optional<City> city = cityService.findById(id);

        if(city.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("city/info");
            modelAndView.addObject("city", city.get());
            modelAndView.addObject("success", null);
            return modelAndView;
        } else {
            return new ModelAndView("error.404");
        }
    }
}
