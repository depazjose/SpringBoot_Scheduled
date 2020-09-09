package com.mdt.apitest.infrastructure.adapters.h2repository;

import java.sql.Timestamp;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdt.apitest.domain.model.Book;
import com.mdt.apitest.domain.model.gateway.BookRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryAdapter {

  Logger logger = LogManager.getLogger(BookRepositoryAdapter.class);

  private final BookDataRepository jpaBookRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  public BookRepositoryAdapter(final BookDataRepository jpaBookRepository, 
      final RedisTemplate<String, Object> redisTemplate) {
    this.jpaBookRepository = jpaBookRepository;
    this.redisTemplate = redisTemplate;
  }

  public Book saveBook(Book book) {

    BookData bookData = new BookData();
    bookData.setName(book.getName());
    bookData.setIsbn(book.getIsbn());
    bookData.setAutor(book.getAutor());
    bookData.setAvailable(book.getAvailable());
    bookData.setQuantity(book.getQuantity());
    if (Objects.nonNull(book.getProperties())) {
      try {
        bookData.setProperties(new ObjectMapper().writeValueAsString(book.getProperties()));
      } catch (JsonProcessingException e) {
        bookData.setProperties("");
      } 
    }
    if (Objects.nonNull(book.getStartSaleDate())) {
      bookData.setStartSaleDate(Timestamp.valueOf(book.getStartSaleDate()));
    }
    bookData.setStatus(book.getStatus());

    bookData = jpaBookRepository.save(bookData);

    book.setId(bookData.getId());
    ListOperations<String, Object> val = redisTemplate.opsForList();
    logger.info("Size cache = " + val.size("*"));
    

    String keyToFind = "hola::key";
    Set<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().keys(keyToFind.getBytes());
    
    Set<String> otras = redisTemplate.keys(keyToFind);

    otras.stream().forEach(item -> logger.info(new String("otras: " + item)));
    keys.stream().forEach(item -> logger.info(new String("llaves: " + new String(item))));

    if (keys.contains(keyToFind.getBytes())) {
      logger.info("llave encontrada");
    }
    
    if (otras.contains(keyToFind)) {
      logger.info("llave encontrada otras");
     
    } 

    if(redisTemplate.hasKey(keyToFind)) {
      logger.info("has key");
    
      Set<Object> aks = redisTemplate.boundSetOps("key").members();
      logger.info(aks);

      Jackson2JsonRedisSerializer<Object> serializer;
      serializer = new Jackson2JsonRedisSerializer<>(Object.class);
      serializer.setObjectMapper(new ObjectMapper());

      

      ListOperations<String,Object> operations = redisTemplate.opsForList();
      operations.getOperations();



      //new ObjectMapper().convertValue(operations.get(keyToFind), Map.class);
      //new ObjectMapper().readValue(operations.get(keyToFind), Map.class);

      //Object ako = operations.getOperations()keyToFind);
      
      //serializer.deserialize((byte[]) operations.get(keyToFind));

      logger.info("ako");
    }

    return book;
  }

  public Optional<Book> findByIsbn(Long isbn) {
    BookData bookData = jpaBookRepository.findByIsbn(isbn);
    if (Objects.isNull(bookData)) {
      return Optional.empty();
    }
    Book book = new Book();
    book.setId(bookData.getId());
    book.setAutor(bookData.getAutor());
    book.setName(bookData.getName());
    book.setIsbn(bookData.getIsbn());
    book.setQuantity(bookData.getQuantity());
    book.setStartSaleDate(bookData.getStartSaleDate().toLocalDateTime());
    book.setStatus(bookData.getStatus());
    return Optional.of(book);
  }

  public List<Book> findAllBooks() {
    List<BookData> bookDataList = jpaBookRepository.findAll();
    return Book.fromModel(bookDataList);
  }
}