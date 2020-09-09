package com.mdt.apitest.infrastructure.adapters;

import com.mdt.apitest.domain.model.Book;
import com.mdt.apitest.domain.model.gateway.BookRepository;
import com.mdt.apitest.infrastructure.adapters.h2repository.BookRepositoryAdapter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookRepositoryImpl implements BookRepository {

  private final BookRepositoryAdapter bookRepositoryAdapter;

  public BookRepositoryImpl(BookRepositoryAdapter bookRepositoryAdapter) {
    this.bookRepositoryAdapter = bookRepositoryAdapter;
  }

  @Override
  public Book saveBook(Book book) {
    return bookRepositoryAdapter.saveBook(book);
  }

  @Override
  public Optional<Book> findByIsbn(Long isbn) {
    return bookRepositoryAdapter.findByIsbn(isbn);
  }

  @Override
  @Cacheable(value= "hola", key = "'key'", unless = "#result == null or #result.size() == 0")
  public List<Book> findAllBooks() {
    List<Book> result = bookRepositoryAdapter.findAllBooks();
    return result;
  }
}
