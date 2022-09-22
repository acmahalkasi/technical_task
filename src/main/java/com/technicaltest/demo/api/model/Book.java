package com.technicaltest.demo.api.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Book {

    @CsvBindByPosition(position = 0)
    private String title;
    @CsvBindByPosition(position = 1)
    private String author;
    @CsvBindByPosition(position = 2)
    private String genre;
    @CsvBindByPosition(position = 3)
    private String publisher;

}
