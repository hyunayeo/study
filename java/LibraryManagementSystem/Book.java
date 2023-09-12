package library;

import java.time.LocalDate;

public class Book {
    private Long id;
    private Long isbn;
    private String name;
    private String author;
    private LocalDate publishedDate;
    private Boolean isLone = false;

    Book(Long id, Long isbn, String name, String author, LocalDate publishedDate){
        this.id = id;
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Boolean getLone() {
        return isLone;
    }

    public void setLone(Boolean lone) {
        isLone = lone;
    }

    @Override
    public String toString() {
        return "* ID:"+id+" | 제목: "+name+" | 저자: "+ author+" | 대여 중: "+ isLone;
    }
}
