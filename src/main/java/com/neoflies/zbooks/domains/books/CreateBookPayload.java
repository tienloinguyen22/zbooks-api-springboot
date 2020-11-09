package com.neoflies.zbooks.domains.books;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class CreateBookPayload {
  @NotBlank(message = "Please input book title")
  private String title;

  private Integer publishYear;

  private String publisher;

  private String language;

  private Integer numberOfPages;

  private String avatarUrl;

  private Set<Integer> categories;

  private Set<Integer> authors;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getPublishYear() {
    return publishYear;
  }

  public void setPublishYear(Integer publishYear) {
    this.publishYear = publishYear;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Integer getNumberOfPages() {
    return numberOfPages;
  }

  public void setNumberOfPages(Integer numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public Set<Integer> getCategories() {
    return categories;
  }

  public void setCategories(Set<Integer> categories) {
    this.categories = categories;
  }

  public Set<Integer> getAuthors() {
    return authors;
  }

  public void setAuthors(Set<Integer> authors) {
    this.authors = authors;
  }
}
