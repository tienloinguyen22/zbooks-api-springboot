package com.neoflies.zbooks.domains.books;

import com.neoflies.zbooks.domains.authors.Author;
import com.neoflies.zbooks.domains.authors.AuthorRepository;
import com.neoflies.zbooks.domains.categories.Category;
import com.neoflies.zbooks.domains.categories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/books")
public class BookController {
  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  BookRepository bookRepository;

  @GetMapping
  public Page<Book> findBooks(
    @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
    @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
    @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder
  ) {
    Sort sort = Sort.by(sortBy);
    if (sortOrder.equals("desc")) {
      sort = sort.descending();
    }
    Pageable pageRequest = PageRequest.of(pageIndex, pageSize, sort);
    return this.bookRepository.findAll(pageRequest);
  }

  @PostMapping
  public Book createBook(@Valid @RequestBody CreateBookPayload createBookPayload) {
    // Populate categories
    Set<Category> categories = new HashSet<>();
    for (Integer categoryId : createBookPayload.getCategories()) {
      Optional<Category> category = this.categoryRepository.findById(categoryId);
      category.ifPresent(categories::add);
    }


    // Populate authors
    Set<Author> authors = new HashSet<>();
    for (Integer authorId : createBookPayload.getAuthors()) {
      Optional<Author> author = this.authorRepository.findById(authorId);
      author.ifPresent(authors::add);
    }

    // Create new book record
    Book newBook = new Book();
    newBook.setTitle(createBookPayload.getTitle());
    newBook.setPublishYear(createBookPayload.getPublishYear());
    newBook.setPublisher(createBookPayload.getPublisher());
    newBook.setLanguage(createBookPayload.getLanguage());
    newBook.setNumberOfPages(createBookPayload.getNumberOfPages());
    newBook.setAvatarUrl(createBookPayload.getAvatarUrl());

    // Create book author record
    for (Author author : authors) {
      newBook.getAuthors().add(author);
    }

    // Create book category record
    for (Category category : categories) {
      newBook.getCategories().add(category);
    }

    return this.bookRepository.save(newBook);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String message = error.getDefaultMessage();
      errors.put(fieldName, message);
    });
    return errors;
  }
}
