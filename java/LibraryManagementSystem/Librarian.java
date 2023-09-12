package library;

import java.time.LocalDate;
import java.util.ArrayList;
class InvalidBookIDException extends Exception{}
class LoginException extends Exception{}
class RegisterException extends Exception{}
class InvalidMemberException extends Exception{}

public class Librarian implements IBookManager,IAdmin {
    // 싱글톤
    private static Librarian librarian = new Librarian();
    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private ArrayList<Member> memberArrayList = new ArrayList<>();
    Long id = 0L;

    private Librarian() {
    }

    public static Librarian getInstance(){
        return librarian;
    }

    public void printAllBook(){
        for (Book book : bookArrayList) {
            printBook(book);
        }
    }

    public Book findById(long id){
        for (Book book : bookArrayList) {
            if (book.getId()==id){
                return book;
            }
        }
        return null;
    }
    public void registerBook(Long isbn, String name, String author, String publishedDate) {
        Book book = new Book(id, isbn, name, author, LocalDate.parse(publishedDate));
        addBook(book);
        id++;
    }
    @Override
    public void addBook(Book book){
        bookArrayList.add(book);
    }

    @Override
    public void printBook(Book book) {
        System.out.println(book);
    }

    @Override
    public void updateBook(Book oldBook, Book newBook) {


    }
    public void updateBookById(Long id, Long isbn, String name, String author, LocalDate publishedDate){

        Book updateBook = findById(id);
        if (updateBook==null){
            updateBook.setIsbn(isbn);
            updateBook.setName(name);
            updateBook.setAuthor(author);
            updateBook.setPublishedDate(publishedDate);
        }else{
            System.out.println("해당 도서가 존재하지 않습니다.");
        }
    }

    @Override
    public void deleteBook(Book book) {
        bookArrayList.remove(book);
    }

    public void deleteBookById(long id){
            Book removeBook = null;
            for (Book book : bookArrayList) {
                if (book.getId()==id){
                    removeBook = book;
                    break;
                }
            }
            if (removeBook!=null){
                deleteBook(removeBook);
            }
            else {
                System.out.println("해당 도서가 존재하지 않습니다");
            }
        }

    public void printAllMember() {
        for (Member member : memberArrayList) {
            printMember(member);
        }
    }

    @Override
    public void printMember(Member member) {
        System.out.println(member);
    }

    @Override
    public void addMember(Member member) {
        memberArrayList.add(member);
    }

    @Override
    public void updateMember(Member member, String name, String pw) {
        member.setName(name);
        member.setPw(pw);
    }

    @Override
    public void deleteMember(Member member) {
        memberArrayList.remove(member);
    }
    public void registerMember(String name, String id, String pw) throws RegisterException{
        try{
            findMemberById(id);
            throw new RegisterException();
        }catch (InvalidMemberException e){
            Member member = new Member(name, id, pw);
            addMember(member);
        }
    }
    public Member findMemberById(String id) throws InvalidMemberException{
        for (Member member : memberArrayList) {
            if (member.getId().equals(id)){
                return member;
            }
        }
        throw new InvalidMemberException();
    }


    public Member loginMember(String id, String pw) throws LoginException {
        for (Member member : memberArrayList) {
            if ((member.getId().equals(id)) && (member.getPw().equals(pw))) {
                return member;
            }
        }
        throw new LoginException();
    }
}
