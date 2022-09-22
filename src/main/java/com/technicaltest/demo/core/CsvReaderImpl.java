package com.technicaltest.demo.core;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;
import com.technicaltest.demo.api.CsvReaderService;
import com.technicaltest.demo.util.Helpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.StringReader;
import java.util.List;

@Slf4j
@Service
public class CsvReaderImpl implements CsvReaderService {


    @PostConstruct
    private void init() {
        log.info(getClass().getSimpleName() + " is started!");
    }

    @PreDestroy
    private void destroy() {
        log.info(getClass().getSimpleName() + " is stopped!");
    }

    @Override
    public void readCsv(String path, List list, Class obj) {
        try {
            new CsvToBeanBuilder(new StringReader(Helpers.readFile(path)))
                    .withType(obj)
                    .withSkipLines(1) //for getting rid of header
                    .withFilter(nullCheckFilter())
                    .build()
                    .parse()
                    .forEach(list::add);
        } catch (Exception e) {
            log.error("exception occurred while parsing csv");
            log.error("check the cvs files under resources folder");

        }
    }

    /**
     * to avoid getting empty lines//to avoid getting empty lines
     *
     * @return CsvToBeanFilter
     */
    private CsvToBeanFilter nullCheckFilter() {
        return strings -> {
            for (String s : strings) {
                if (s != null && !s.isEmpty()) {
                    return true;
                }
            }
            return false;
        };
    }


}
