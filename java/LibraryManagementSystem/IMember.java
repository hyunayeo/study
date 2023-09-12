package library;

public interface IMember {
    void printAllBorrowBook();
    void printBorrowBook(Book book);
    void borrowBook(Book book);
    void returnBook(Book book);
    void extendBook(Book book);
    void applyBook(Book book);
    public String getReturnDate();
    boolean findByTitle(String title);
}
