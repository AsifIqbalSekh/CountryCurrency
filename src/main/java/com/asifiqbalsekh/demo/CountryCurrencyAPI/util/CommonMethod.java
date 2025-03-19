package com.asifiqbalsekh.demo.CountryCurrencyAPI.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonMethod {

    public static String getTimestamp(){
        LocalDateTime systemTime = LocalDateTime.now();
        return systemTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy 'Time' HH:mm:ss"));
    }
}
