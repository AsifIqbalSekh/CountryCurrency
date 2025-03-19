package com.asifiqbalsekh.demo.CountryCurrencyAPI.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data //Includes Getter/Setters/ToString etc
@Document(value = "country-currency")
public class Country {

    @Id
    private String countryName;
    @Indexed(unique = true)
    private String currencyCode;
    private String currencyName;
}
