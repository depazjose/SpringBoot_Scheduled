package com.mdt.apitest.domain.usescase;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdt.apitest.domain.model.Book;
import com.mdt.apitest.domain.model.gateway.BookRepository;


public class BookUseCaseImpl implements BookUseCase {

  private final BookRepository bookRepository;

  public BookUseCaseImpl(final BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Book createBook(Book book) {
    return bookRepository.saveBook(book);
  }

  @Override
  public Optional<Book> findByIsbn(Long isbn) {
    return bookRepository.findByIsbn(isbn);
  }

  @Override
  public List<Book> findAll() {
    ObjectMapper objectMapper = new ObjectMapper();
    List<Book> result = objectMapper.convertValue(bookRepository.findAllBooks(), new TypeReference<List<Book>>() { });
    return result;
  }
    
}