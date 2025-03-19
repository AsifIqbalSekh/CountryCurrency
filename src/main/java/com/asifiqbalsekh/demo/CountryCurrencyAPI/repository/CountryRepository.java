package com.asifiqbalsekh.demo.CountryCurrencyAPI.repository;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CountryRepository extends MongoRepository<Country, String> {

    Optional<Country> findByCountryNameIgnoreCase(String countryName);
}
