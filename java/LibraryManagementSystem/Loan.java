package library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Loan {
    private Book book;
    private Member member;
    private Calendar calendar = Calendar.getInstance();
    private int extensionCount = 0;

    public Loan(Book book, Member member) {
        this.book = book;
        this.member = member;
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,7);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Loan){
            Loan loan = (Loan) obj;
            if (member.equals(loan.member)){
                return true;
            }
        }
        return false;
    }

    public void extendLoan(){
        if (extensionCount<2) {
            extensionCount++;
            calendar.add(Calendar.DATE,7);
        }else System.out.println("연장 횟수가 초과되었습니다.");
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getExtensionCount() {
        return extensionCount;
    }

    public String getReturnDate() {
        DateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public String toString() {
        return "* 도서 ID: "+book.getId() +" | 도서 이름: "+ book.getName()+" | 반납예정일: "+getReturnDate()+ " | 연장 횟수: "+extensionCount;
    }
}
