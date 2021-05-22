package com.ludmylla.libraryapi.api.resources;

import com.ludmylla.libraryapi.api.dto.BookDTO;
import com.ludmylla.libraryapi.model.entity.Book;
import com.ludmylla.libraryapi.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public BookDTO create(@RequestBody BookDTO bookDTO){
        Book entity = modelMapper.map(bookDTO, Book.class);
        entity = service.save(entity);
        return modelMapper.map(entity, BookDTO.class);
    }
}
