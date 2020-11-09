package com.neoflies.zbooks.domains.authors;

import com.neoflies.zbooks.domains.books.Book;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotBlank(message = "Please input author name")
  private String name;

  @ManyToMany(mappedBy = "authors")
  private Set<Book> books;

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
