package com.ludmylla.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ludmylla.libraryapi.api.dto.BookDTO;
import com.ludmylla.libraryapi.model.entity.Book;
import com.ludmylla.libraryapi.services.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mvc;

    /* MockBean = Implementações fake de uma interface, que pode
    *  ter seu comportamento modificado conforme a nessidade
    */
    @MockBean
    BookService service;

    @Test
    @DisplayName("Create a book successfully")
    public void createBookTest() throws Exception {

        BookDTO dto = BookDTO
                .builder()
                .author("Arthur")
                .title("As aventuras")
                .isbn("123456").build();

        //  Quando for salvar, não será salvo omo dto mas sim
        // com a entidade livro
        Book savedBook = Book.builder()
                .id(10L)
                .author("Arthur")
                .title("As aventuras")
                .isbn("123456").build();

        BDDMockito.given(service.save(Mockito.any(Book.class)))
                .willReturn(savedBook);

        /* Representa o json
         * O método writeValueAsString recebe um objetp
         * de qualquer tipo e transforma ele em json
        */
        String json = new ObjectMapper()
                .writeValueAsString(dto);

        // Cria requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // Fazer requisição
        mvc.perform(request)
                // Passando os verificadores no andExpect
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").value(dto.getTitle()))
                .andExpect(jsonPath("author").value(dto.getAuthor()))
               .andExpect(jsonPath("isbn").value(dto.getIsbn()));
    }

    @Test
    @DisplayName("Should throw a validation error, when there is not enough data to create the book")
    public void createInvalidBookTest(){

    }
}
