package library;

public interface IBookManager {
    void addBook(Book book);
    void printBook(Book book);
    void updateBook(Book oldBook, Book newBook);
    void deleteBook(Book book);
}
