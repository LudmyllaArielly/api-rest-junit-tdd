package com.ludmylla.libraryapi.service;

import com.ludmylla.libraryapi.model.entity.Book;
import com.ludmylla.libraryapi.services.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    @Autowired
    private BookService service;

    @Test
    @DisplayName("Should save a book")
    public void saveBookTest(){
        // cenário
        Book book = Book.builder()
                .author("Maria")
                .title("A lagoa")
                .isbn("45555").build();

        // execução
        Book savedBook = service.save(book);

        // verificação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("A lagoa");
        assertThat(savedBook.getAuthor()).isEqualTo("Maria");
        assertThat(savedBook.getIsbn()).isEqualTo("45555");

    }

}
