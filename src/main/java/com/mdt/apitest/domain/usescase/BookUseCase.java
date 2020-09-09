package com.mdt.apitest.domain.usescase;

import java.util.List;
import java.util.Optional;

import com.mdt.apitest.domain.model.Book;

public interface BookUseCase {
 
  Book createBook(Book book);
 
  Optional<Book> findByIsbn(Long isbn);

  List<Book> findAll();

}