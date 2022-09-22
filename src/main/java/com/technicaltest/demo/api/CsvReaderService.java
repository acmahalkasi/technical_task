package com.technicaltest.demo.api;

import java.util.List;

public interface CsvReaderService {

    /**
     * @param path
     * @param list
     * @param obj  this method will fill all the data for DataProvider
     */
    void readCsv(String path, List list, Class obj);

}
