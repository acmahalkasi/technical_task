package com.technicaltest.demo.rest;

import com.google.gson.Gson;
import com.technicaltest.demo.api.DataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class Controller {

    Gson gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().setPrettyPrinting().create();

    @Autowired
    DataProvider dataProvider;

    @GetMapping(value = "/books", produces = "application/json")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/books", produces = "application/json")
    public ResponseEntity<String> listBooks() {
        log.info("list books requested");
        return ResponseEntity.ok(gson.toJson(dataProvider.getBooks()));
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<String> listUses() {
        log.info("list users requested");
        return ResponseEntity.ok(gson.toJson(dataProvider.getUsers()));
    }

    @GetMapping(value = "/borrowed", produces = "application/json")
    public ResponseEntity<String> listBorroweds() {
        log.info("list borrowed requested");
        return ResponseEntity.ok(gson.toJson(dataProvider.getBorroweds()));
    }

    //which is associated with the requirement "E"
    //fixme at what time?
    //Today date is added to solve the problem of which date it is.
    //if very far date is given all the books will be listed

    /**
     * @param todayDate
     * @return
     */
    @GetMapping(value = "/available-books", produces = "application/json")
    public ResponseEntity<String> listAvailableBooks(@RequestParam String todayDate) {
        log.info("listAvailableBooks requested");
        try {
            var availableBooks = dataProvider.availableBooks(todayDate);
            return ResponseEntity.ok(gson.toJson(availableBooks));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("error occurred while listAvailableBooks");
        }
    }

    //which is associated with the requirement "C"
    //I do not understand "borrowed a book on a given date" part. it meant to be in borrowing time
    //range or specific time which is given. I prefer to implement exact date

    /**
     * @param date
     * @return
     */
    @GetMapping(value = "/users-borrowed-books", produces = "application/json")
    public ResponseEntity<String> listUsersBorrowedBooks(@RequestParam String date) {
        log.info("listUsersBorrowedBooks requested");
        try {
            return ResponseEntity.ok(gson.toJson(dataProvider.listUsersBorrowedBooks(date)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("error occurred while listUsersBorrowedBooks");
        }
    }

    //which is associated with the requirement "A"
    @GetMapping(value = "/users-borrowed-books-all-time", produces = "application/json")
    public ResponseEntity<String> listUsersBorrowedBooksAnyTime() {
        log.info("listUsersBorrowedBooksAnyTime requested");
        try {
            return ResponseEntity.ok(gson.toJson(dataProvider.listUsersBorrowedBooksAnyTime()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("error occurred while listUsersBorrowedBooksAnyTime");
        }
    }


}
