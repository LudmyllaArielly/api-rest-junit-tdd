package com.ludmylla.libraryapi.service;

import com.ludmylla.libraryapi.model.entity.Book;
import com.ludmylla.libraryapi.model.repositories.BookRepository;
import com.ludmylla.libraryapi.services.BookService;
import com.ludmylla.libraryapi.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

     @MockBean
     BookRepository bookRepository;

     BookService service;

    @BeforeEach
    public void setUp(){
        this.service = new BookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Should save a book")
    public void saveBookTest(){
        // Cenário
        Book book = Book.builder()
                .author("Maria")
                .title("Secreto")
                .isbn("123").build();

        // Execução
        Mockito.when(bookRepository.save(book)).thenReturn(Book.builder()
                .id(1l).title("Secreto").author("Maria").isbn("123").build());


        Book savedBook = bookRepository.save(book);
       // Book savedBook = service.save(book);

        // Verificação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getTitle()).isEqualTo("Secreto");
        assertThat(savedBook.getAuthor()).isEqualTo("Maria");

    }

    @Test
    @DisplayName("Should initiate a validation error, when there is not enough data to create the book")
    public void createInvalidBookTest(){


    }

}
