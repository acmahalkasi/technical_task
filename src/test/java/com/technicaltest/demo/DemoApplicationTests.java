package com.technicaltest.demo;

import com.google.gson.Gson;
import com.technicaltest.demo.api.DataProvider;
import com.technicaltest.demo.util.Helpers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
class DemoApplicationTests {

    public MockMvc mockMvc;
    public MockRestServiceServer mockServer;
    public Gson gson;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private DataProvider dataProvider;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().setPrettyPrinting().create();
    }

    @Test
    void testListingBooks() throws Exception {

        String exceptedBody = gson.toJson(dataProvider.getBooks());

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andDo(print())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    assertEquals(HttpStatus.OK.value(), response.getStatus());

                    assertEquals(exceptedBody, response.getContentAsString());
                });
    }

    @Test
    void testListingUsers() throws Exception {

        String exceptedBody = gson.toJson(dataProvider.getUsers());

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(print())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    assertEquals(HttpStatus.OK.value(), response.getStatus());

                    assertEquals(exceptedBody, response.getContentAsString());
                });
    }

    @Test
    void testListingBorrowed() throws Exception {

        String exceptedBody = gson.toJson(dataProvider.getBorroweds());

        mockMvc.perform(MockMvcRequestBuilders.get("/borrowed"))
                .andDo(print())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    assertEquals(HttpStatus.OK.value(), response.getStatus());

                    assertEquals(exceptedBody, response.getContentAsString());
                });
    }

    @Test
    void testUsersBorrowedBook() throws Exception {

        String exceptedBody = Helpers.readFile("usersBorrowedBooks.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/users-borrowed-books?date=06/28/2021"))
                .andDo(print())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    assertEquals(HttpStatus.OK.value(), response.getStatus());

                    assertEquals(Helpers.removeWhiteSpaces(exceptedBody),
                            Helpers.removeWhiteSpaces(response.getContentAsString()));
                });
    }

    @Test
    void testUsersBorrowedBookAnyTime() throws Exception {

        String exceptedBody = Helpers.readFile("usersBorrowedBooksAllTime.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/users-borrowed-books-all-time"))
                .andDo(print())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    assertEquals(HttpStatus.OK.value(), response.getStatus());

                    assertEquals(Helpers.removeWhiteSpaces(exceptedBody),
                            Helpers.removeWhiteSpaces(response.getContentAsString()));
                });
    }

    @Test
    void testAvailableBookAnyTime() throws Exception {

        String exceptedBody = Helpers.readFile("availableBooksAtTime.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/available-books?todayDate=05/15/2008"))
                .andDo(print())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    assertEquals(HttpStatus.OK.value(), response.getStatus());

                    assertEquals(Helpers.removeWhiteSpaces(exceptedBody),
                            Helpers.removeWhiteSpaces(response.getContentAsString()));
                });
    }

}
