package com.technicaltest.demo.api;

import com.technicaltest.demo.api.model.Book;
import com.technicaltest.demo.api.model.Borrowed;
import com.technicaltest.demo.api.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DataProvider {

    List<Book> getBooks();

    /**
     * @param title
     * @return Optional<Book>
     */
    Optional<Book> getBook(String title);

    /**
     * @param todayDate
     * @return Set<Book>
     */
    Set<Book> availableBooks(String todayDate) throws Exception;

    List<User> getUsers();

    /**
     * @param name
     * @return Optional<User>
     */
    Optional<User> getUser(String name);

    List<Borrowed> getBorroweds();

    /**
     * @param date
     * @return List<User>
     */
    Set<User> listUsersBorrowedBooks(String date) throws Exception;

    Set<User> listUsersBorrowedBooksAnyTime() throws Exception;
}
