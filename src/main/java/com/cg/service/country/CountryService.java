package com.cg.service.country;

import com.cg.model.Country;
import com.cg.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CountryService implements ICountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Iterable<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public void remove(Long id) {
        countryRepository.deleteById(id);
    }
}
