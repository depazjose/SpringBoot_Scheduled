package com.mdt.apitest.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdt.apitest.infrastructure.adapters.h2repository.BookData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 2862668552922430100L;
  private Long id;
  private Long isbn;
  private String name;
  private Integer quantity;
  private Boolean available;
  private String autor;
  private Map<String, Object> properties;
  private LocalDateTime startSaleDate;
  private String status;

  public static Book fromModel(BookData bookData) {
    Book book = new Book();
    book.setId(bookData.getId());
    book.setIsbn(bookData.getIsbn());
    book.setName(bookData.getName());
    book.setQuantity(bookData.getQuantity());
    book.setAvailable(bookData.getAvailable());
    book.setAutor(bookData.getAutor());
    if (Objects.nonNull(bookData.getProperties())) {
      try {
        book.setProperties(new ObjectMapper()
          .readValue(bookData.getProperties(), new TypeReference<Map<String, Object>>(){}));
      } catch (JsonProcessingException e) {
        book.setProperties(new HashMap<>());
      }
    }

    if (Objects.nonNull(bookData.getStartSaleDate())) {
      book.setStartSaleDate(bookData.getStartSaleDate().toLocalDateTime());
    }
    book.setStatus(bookData.getStatus());
    return book;
  }

  public static List<Book> fromModel(List<BookData> bookDatas) {
    return bookDatas.stream()
      .filter(bookData -> Objects.nonNull(bookData))
      .map(book -> Book.fromModel(book))
      .filter(newBook -> Objects.nonNull(newBook))
      .collect(Collectors.toList());
  }
}