package com.technicaltest.demo.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.technicaltest.demo.api.CsvReaderService;
import com.technicaltest.demo.api.DataProvider;
import com.technicaltest.demo.api.model.Book;
import com.technicaltest.demo.api.model.Borrowed;
import com.technicaltest.demo.api.model.User;
import com.technicaltest.demo.util.Helpers;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class DataProviderImpl implements DataProvider {


    List<Book> books;
    List<User> users;
    List<Borrowed> borroweds;

    @Autowired
    CsvReaderService csvReaderService;

    @PostConstruct
    private void init() {
        books = Lists.newArrayList();
        users = Lists.newArrayList();
        borroweds = Lists.newArrayList();

        csvReaderService.readCsv("books.csv", books, Book.class);
        csvReaderService.readCsv("user.csv", users, User.class);
        csvReaderService.readCsv("borrowed.csv", borroweds, Borrowed.class);

        log.info(getClass().getSimpleName() + " is started!");
    }

    @PreDestroy
    private void destroy() {
        log.info(getClass().getSimpleName() + " is stopped!");
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public Optional<Book> getBook(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findAny();
    }

    @Override
    public Set<Book> availableBooks(String todayDate) {
        Set<Book> bookSet = Sets.newHashSet();
        var convertedDate = Helpers.toDate(todayDate);
        for (Borrowed borrowed : getBorroweds()) { //travers all the borrowed record to identify which one is available
            var borrowedTo = Helpers.toDate(borrowed.getBorrowedTo());
            var borrowedFrom = Helpers.toDate(borrowed.getBorrowedFrom());

            //check the date which not in the borrowing range
            if (convertedDate.isAfter(borrowedTo) || convertedDate.isBefore(borrowedFrom)) {
                if (getBook(borrowed.getBook()).isPresent()) {
                    Book book = getBook(borrowed.getBook()).orElse(null);
                    bookSet.add(book);
                }
            }
        }
        return bookSet;

        //which returns all the books which are not borrowed
        //return books.stream()
        //        .filter(book -> borroweds.stream()
        //        .anyMatch(a -> !a.getBook().equals(book.getTitle())))
        //        .toList();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public Optional<User> getUser(String name) {
        return users.stream()
                .filter(user -> (user.getName() + "," + user.getFirstName()).equals(name))
                .findAny();
    }

    @SneakyThrows
    @Override
    public Set<User> listUsersBorrowedBooks(String date) {
        Set<User> userList = Sets.newHashSet();
        var convertedDate = Helpers.toDate(date);
        for (Borrowed borrowed : getBorroweds()) {
            var borrowedFrom = Helpers.toDate(borrowed.getBorrowedFrom());

            //check days are equal
            if (convertedDate.isEqual(borrowedFrom)) {
                if (getUser(borrowed.getBorrower()).isPresent()) {
                    User user = getUser(borrowed.getBorrower()).orElse(null);
                    userList.add(user);
                }
            }
        }
        return userList;
    }

    @Override
    public Set<User> listUsersBorrowedBooksAnyTime() {
        Set<User> userList = Sets.newHashSet();
        for (Borrowed borrowed : getBorroweds()) {
            if (getUser(borrowed.getBorrower()).isPresent()) {
                User user = getUser(borrowed.getBorrower()).orElse(null);
                userList.add(user);
            }
        }
        return userList;
    }

    @Override
    public List<Borrowed> getBorroweds() {
        return borroweds;
    }


}
