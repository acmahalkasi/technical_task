package com.technicaltest.demo.api.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Borrowed {

    @CsvBindByPosition(position = 0)
    private String borrower;
    @CsvBindByPosition(position = 1)
    private String book;
    @CsvBindByPosition(position = 2)
    private String borrowedFrom;
    @CsvBindByPosition(position = 3)
    private String borrowedTo;

}
