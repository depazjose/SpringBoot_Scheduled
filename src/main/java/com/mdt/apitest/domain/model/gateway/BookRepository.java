package com.mdt.apitest.domain.model.gateway;

import java.util.List;
import java.util.Optional;

import com.mdt.apitest.domain.model.Book;

public interface BookRepository {
 
  Book saveBook(Book book);
  
  Optional<Book> findByIsbn(Long isbn);
  
  List<Book> findAllBooks();
}