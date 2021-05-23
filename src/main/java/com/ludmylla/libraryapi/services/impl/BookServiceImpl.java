package com.ludmylla.libraryapi.services.impl;

import com.ludmylla.libraryapi.exceptions.BusinessException;
import com.ludmylla.libraryapi.model.entity.Book;
import com.ludmylla.libraryapi.model.repositories.BookRepository;
import com.ludmylla.libraryapi.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        if(bookRepository.existsByIsbn(book.getIsbn())){
            throw new BusinessException("Isbn already exists");
        }
        return bookRepository.save(book);
    }
}
