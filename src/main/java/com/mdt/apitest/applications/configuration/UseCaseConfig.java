package com.mdt.apitest.applications.configuration;

import com.mdt.apitest.domain.model.gateway.BookRepository;
import com.mdt.apitest.domain.usescase.BookUseCaseImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    
  @Bean
  public BookUseCaseImpl bookUseCaseImpl(final BookRepository bookRepository) {
    return new BookUseCaseImpl(bookRepository);
  }

}