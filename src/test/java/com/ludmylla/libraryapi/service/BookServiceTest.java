package com.ludmylla.libraryapi.service;

import com.ludmylla.libraryapi.exceptions.BusinessException;
import com.ludmylla.libraryapi.model.entity.Book;
import com.ludmylla.libraryapi.model.repositories.BookRepository;
import com.ludmylla.libraryapi.services.BookService;
import com.ludmylla.libraryapi.services.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        Book book = createNewBook();

        // Execução
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(false);
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

    private Book createNewBook () {
        return Book.builder()
                .author("Maria")
                .title("Secreto")
                .isbn("123").build();
    }

    @Test
    @DisplayName("Should generate a exception error when trying to register a book with duplicate isbn")
    public void shouldNotSaveABookWithDuplicatedIsbn(){
        // Cenário
        Book book = createNewBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(true);
        
        // Execução
        Throwable exception = Assertions.catchThrowable(() -> service.save(book));

        // Verificações
        assertThat(exception).isInstanceOf(BusinessException.class)
                .hasMessage("Isbn already exists");

        // Verificando que nunca pode chamar o método salvar
        Mockito.verify(bookRepository, Mockito.never()).save(book);

    }

}
