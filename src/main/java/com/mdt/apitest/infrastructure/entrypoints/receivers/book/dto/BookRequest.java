package com.mdt.apitest.infrastructure.entrypoints.receivers.book.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.mdt.apitest.domain.model.Book;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

public interface BookRequest {

  @Getter
  @Setter
  public class CreationBookRequest implements Serializable {
 
    /**
     *
     */
    private static final long serialVersionUID = 2583412521973755486L;
    @Min(1)
    private Long isbn;
    @Size(max = 100)
    private String name;
    @NonNull
    @Min(0)
    private Integer quantity;
    @Size(max = 100)
    private String autor;
    private Map<String, Object> properties; 
    private Optional<Long> startSaleDate;
    private String status;

    public static Book toModel(CreationBookRequest request) {
      Book book = new Book();
      book.setIsbn(request.getIsbn());
      book.setName(request.getName());
      book.setQuantity(request.getQuantity());
      book.setAutor(request.getAutor());
      book.setProperties(request.getProperties());
      book.setAvailable(false);
      if (Objects.nonNull(request.getStartSaleDate())) {
        request.getStartSaleDate()
        .ifPresent(startDate -> 
          book.setStartSaleDate(Instant.ofEpochMilli(startDate).atZone(ZoneId.systemDefault()).toLocalDateTime()));
      }
      book.setStatus(request.getStatus());
      return book;
    }

  }

}