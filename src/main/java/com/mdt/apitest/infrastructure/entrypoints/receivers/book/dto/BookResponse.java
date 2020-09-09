package com.mdt.apitest.infrastructure.entrypoints.receivers.book.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mdt.apitest.domain.model.Book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public interface BookResponse {

  @Getter
  @Setter
  @ToString
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public class BookDetailResponse implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 4076190693760201431L;
    private Long id;
    private Long isbn;
    private String name;
    private Integer quantity;
    private Boolean available;
    private String autor;
    private Map<String, Object> properties;
    private LocalDateTime startSaleDate;
    private String status;

    public static BookDetailResponse fromModel(Book book) {
      BookDetailResponse bookDetailResponse = new BookDetailResponse();
      bookDetailResponse.setId(book.getId());
      bookDetailResponse.setIsbn(book.getIsbn());
      bookDetailResponse.setName(book.getName());
      bookDetailResponse.setQuantity(book.getQuantity());
      bookDetailResponse.setAvailable(book.getAvailable());
      bookDetailResponse.setAutor(book.getAutor());
      bookDetailResponse.setProperties(book.getProperties());
      bookDetailResponse.setStartSaleDate(book.getStartSaleDate());
      bookDetailResponse.setStatus(book.getStatus());
        
      return bookDetailResponse;
    }

    public static List<BookDetailResponse> fromModel(List<Book> books) {
      if (Objects.nonNull(books)) {
        return books.stream()
          .filter(book -> Objects.nonNull(book))
          .map(item -> BookDetailResponse.fromModel(item))
          .filter(book -> Objects.nonNull(book))
          .collect(Collectors.toList());
      }
      return new ArrayList<>();
    }
  }
}