package library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Member {
    private String name;
    private String id;
    private String pw;
    private Calendar calendar = Calendar.getInstance();
    private int extensionCount = 0;
    private List<Loan> loanList = new ArrayList<>();
    private List<Book> applyBookList = new ArrayList<>();

    public Member(String name,String id, String pw) {
        this.name = name;
        this.id = id;
        this.pw = pw;
    }
    void printAllBorrowBook(){
        for (Loan loan:loanList){
            printBorrowBook(loan);
        }
    }
    void printBorrowBook(Loan loan){
        System.out.println(loan);
    }
    void borrowBook(Book book){
        if (!book.getLone()){
            loanList.add(new Loan(book, this));
            book.setLone(true);
        }else System.out.println("해당 도서는 대여중입니다.");
    }
    void returnBook(Book book){
        Loan removeLoan = null;
        for (Loan loan : loanList) {
            if (loan.getBook()==book){
                removeLoan = loan;
                break;
            }
        }
        if (removeLoan!=null){
            loanList.remove(removeLoan);
            book.setLone(false);
        }else {
            System.out.println("해당 대출이 존재하지 않습니다");
        }
    }
    void extendBook(Book book){
        Loan extendLoan = null;
        for (Loan loan : loanList) {
            if (loan.getBook()==book){
                extendLoan = loan;
                break;
            }
        }
        if (extendLoan!=null){
            extendLoan.extendLoan();
            System.out.println(extendLoan);
        }else {
            System.out.println("해당 대출이 존재하지 않습니다");
        }
    }
    void applyBook(Book book){
        applyBookList.add(book);
    }

    public String getReturnDate() {
        DateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(calendar.getTime());
    }
    boolean findByTitle(String title){
        return loanList.contains(title);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "회원 ID: "+id+"\n회원 이름: "+name;
    }
}
