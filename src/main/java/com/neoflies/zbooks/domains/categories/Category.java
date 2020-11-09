package com.neoflies.zbooks.domains.categories;

import com.neoflies.zbooks.domains.books.Book;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  @NotBlank(message = "Please input category name")
  private String name;

  @ManyToMany(mappedBy = "categories")
  private Set<Book> books;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
