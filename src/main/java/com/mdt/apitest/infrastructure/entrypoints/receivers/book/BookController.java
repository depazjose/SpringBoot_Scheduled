package com.mdt.apitest.infrastructure.entrypoints.receivers.book;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mdt.apitest.domain.model.Book;
import com.mdt.apitest.domain.usescase.BookUseCase;
import com.mdt.apitest.infrastructure.entrypoints.receivers.book.dto.BookRequest.CreationBookRequest;
import com.mdt.apitest.infrastructure.entrypoints.receivers.book.dto.BookResponse.BookDetailResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController { 

   Logger logger = LogManager.getLogger(BookController.class);

   private final BookUseCase bookUseCase;

   public BookController(final BookUseCase bookUseCase) {
    this.bookUseCase = bookUseCase;
   }

   @GetMapping(value="/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value ="get book by code")
   public ResponseEntity<BookDetailResponse> getBook(@PathVariable Long isbn) {

     Optional<Book> book = bookUseCase.findByIsbn(isbn);

     if (book.isPresent()) {
       return new ResponseEntity<>(BookDetailResponse.fromModel(book.get()), HttpStatus.FOUND);
     } else {
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);   
     }
   }
   
   @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value ="get all books")
   public ResponseEntity<List<BookDetailResponse>> getAllBook() {

     return new ResponseEntity<List<BookDetailResponse>>(
       BookDetailResponse.fromModel(bookUseCase.findAll()), HttpStatus.ACCEPTED); 
   }
   
   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value ="create new book")
   public BookDetailResponse createBook(
        @RequestPart(name="book", required = true)  @Valid CreationBookRequest creationBookRequest ,
        @ApiParam(value="Portada file", required = false) 
        @RequestPart(name="image", required = false) MultipartFile file) {

     Book result = bookUseCase.createBook(CreationBookRequest.toModel(creationBookRequest));       

     return BookDetailResponse.fromModel(result);
   }

}

