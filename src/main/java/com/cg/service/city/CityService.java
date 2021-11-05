package com.cg.service.city;

import com.cg.model.City;
import com.cg.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CityService implements ICityService{

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Iterable<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void remove(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public Boolean existsByName(String name) {
        return cityRepository.existsByName(name);
    }
}
