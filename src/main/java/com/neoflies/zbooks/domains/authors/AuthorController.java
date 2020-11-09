package com.neoflies.zbooks.domains.authors;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authors")
public class AuthorController {
  @Autowired
  AuthorRepository authorRepository;

  @PostMapping
  public Author createAuthor(@Valid @RequestBody Author author) {
    return this.authorRepository.save(author);
  }

  @GetMapping
  public Page<Author> findAuthors(
    @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
    @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
    @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder
  ) {
    Sort sort = Sort.by(sortBy);
    if (sortOrder.equals("desc")) {
      sort = sort.descending();
    }
    Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
    return this.authorRepository.findAll(pageable);
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
