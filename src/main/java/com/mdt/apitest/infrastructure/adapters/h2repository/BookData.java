package com.mdt.apitest.infrastructure.adapters.h2repository;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="book_data")
@Getter
@Setter
public class BookData {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private Long isbn;
  @NotBlank(message = "Name is mandatory")
  private String name;
  private Integer quantity;
  private Boolean available;
  @NotBlank(message = "Autor is mandatory")
  private String autor;
  private String properties;
  @Column(name = "start_sale_date")
  private Timestamp startSaleDate;
  private String status;
  
}