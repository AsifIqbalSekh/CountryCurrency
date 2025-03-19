package com.asifiqbalsekh.demo.CountryCurrencyAPI.service;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.CountryRequest;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.CountryResponse;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.DuplicateEntryException;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.PatchRequest;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.model.Country;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepo;

    public List<CountryResponse> listOfAllCountry(){

        return countryRepo
                .findAll()
                .stream()
                .map(item->
                        new CountryResponse(item.getCountryName(),item.getCurrencyCode(),
                                item.getCurrencyName())
                )
                .collect(Collectors.toList());

    }
    public CountryResponse addCountry(CountryRequest given_country,String req_method){
        if (countryRepo.existsById(given_country.country_name()) && req_method.equals("POST")) {
            throw new DuplicateEntryException("Given Country-ID Already Existed", HttpStatus.CONFLICT);
        }
        var save_country=countryRepo.save(

                Country.builder()
                    .countryName(given_country.country_name())
                    .currencyCode(given_country.currency_code())
                    .currencyName(given_country.currency_name())
                .build()

        );

        return new CountryResponse(save_country.getCountryName(),save_country.getCurrencyCode(),
                save_country.getCurrencyName()
        );
    }


    public String deleteCountry(String countryName) {
        if (!countryRepo.existsById(countryName)) {
            throw new RuntimeException("Given Country-ID Not found");
        }
        countryRepo.deleteById(countryName);
        return countryName+" deleted successfully";
    }

    public CountryResponse getCountry(String countryName) {
       var foundCountry= countryRepo.findByCountryNameIgnoreCase(countryName)
               .orElseThrow(()->new RuntimeException("Given Country-ID Not found"));
       return new CountryResponse(foundCountry.getCountryName(),foundCountry.getCurrencyCode(),
               foundCountry.getCurrencyName());

    }
    public CountryResponse updateCountry(PatchRequest patchData){
        var existingCountry=countryRepo.findById(patchData.country_name())
                .orElseThrow(()->new RuntimeException("Given Country-ID Not found"));

        if (patchData.currency_code() != null) {
            existingCountry.setCurrencyCode(patchData.currency_code());
        }

        if (patchData.currency_name() != null) {
            existingCountry.setCurrencyName(patchData.currency_name());
        }

        var updatedCountry = countryRepo.save(existingCountry);

        return new CountryResponse(
                updatedCountry.getCountryName(),
                updatedCountry.getCurrencyCode(),
                updatedCountry.getCurrencyName()
        );
    }
}
