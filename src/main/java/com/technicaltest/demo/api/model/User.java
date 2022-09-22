package com.technicaltest.demo.api.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class User {

    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private String firstName;
    @CsvBindByPosition(position = 2)
    private String memberSince;
    @CsvBindByPosition(position = 3)
    private String memberTill;
    @CsvBindByPosition(position = 4)
    private String gender;

}
