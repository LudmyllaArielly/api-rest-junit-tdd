package com.ludmylla.libraryapi.model.repository;

import com.ludmylla.libraryapi.model.repositories.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    // Teste de integração

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("Should return true when there is a book in the database with an ISBN")
    public void  returnTrueWhenIsnbExits(){
        // Cenário
        String isbn = "123";

        // Execução
        boolean exists = bookRepository.existsByIsbn(isbn);

        // Verificação
        assertThat(exists).isTrue();
    }

}
