package library;

import javax.imageio.spi.ImageInputStreamSpi;
import java.time.LocalDate;
import java.util.Scanner;


public class PrinterScanner {
    static Librarian librarian = Librarian.getInstance();
    static Member member;
    static Scanner scanner = new Scanner(System.in);
    // console print
    static void startHome(){
        titleLine("kitri 도서관에 오신 것을 환영합니다");
        System.out.println("(1) 관리자");
        System.out.println("(2) 회원");
        System.out.println("(3) 도서 조회");
        System.out.println("(0) 프로그램 종료");
    }
    static void memberHome(){
        titleLine("회원 화면입니다");
        System.out.println("(1) 회원 로그인하기");
        System.out.println("(2) 회원 가입하기");
        System.out.println("(0) 프로그램 종료");
    }
    static Member loginHome(){
        titleLine("로그인 화면");
        System.out.print("ID >> ");
        String id = (String) nextLineTypeCheck(2);
        System.out.print("PW >> ");
        String pw = (String) nextLineTypeCheck(2);
        try{
            member = librarian.loginMember(id, pw);
            messageLine("로그인 성공");
            return member;
        }catch (LoginException e){
            messageLine("로그인 실패");
            messageLine("다시 시도해 주세요");
            return null;
        }
    }
    static void registerMemberHome(){
        titleLine("회원 가입");
        System.out.print("이름 >> ");
        String name = (String) nextLineTypeCheck(2);
        System.out.print("ID >> ");
        String id = (String) nextLineTypeCheck(2);
        System.out.print("PW >> ");
        String pw = (String) nextLineTypeCheck(2);
        try{
            librarian.registerMember(name, id, pw);
        }catch (Exception e){
            messageLine("이미 존재하는 ID 입니다.");
        }

    }
    static void mainHome(){
        titleLine("도서 관리 프로그램");
        System.out.println("(1) 도서 조회");
        System.out.println("(2) 도서 등록");
        System.out.println("(3) 도서 수정");
        System.out.println("(4) 도서 삭제");
        System.out.println("(5) 회원 조회");
        System.out.println("(6) 회원 등록");
        System.out.println("(7) 회원 수정");
        System.out.println("(8) 회원 삭제");
        System.out.println("(9) 이전 페이지로 돌아가기");
        System.out.println("(0) 프로그램 종료");
    }

    // util
    static void titleLine(String message){
        System.out.println();
        System.out.println("=".repeat(20));
        System.out.println(message);
        System.out.println("=".repeat(20));
    }
    static void messageLine(String message){
        System.out.print("*".repeat(2));
        System.out.print(message);
        System.out.println("*".repeat(2));
    }
    static int inputLine(){
        System.out.print("선택 >>");
        return (int)nextLineTypeCheck(1);
    }
    static Object nextLineTypeCheck(int i){
        /**
         * 사용자 입력이 의도에 맞는 값이 들어오는지 체크하는 메소드
         * @i 1>int 2>String 3>Date format 4> Long
         */
        String inputStr;
        do {
            try {
                inputStr = scanner.nextLine();
                switch (i){
                    case 1:
                        return Integer.parseInt(inputStr);
                    case 2:
                        return inputStr;
                    case 3:
                        return LocalDate.parse(inputStr);
                    case 4:
                        return Long.parseLong(inputStr);
                }
            }catch (Exception e){
                System.out.println("입력값이 옳지 않습니다. 다시 시도해주세요.");
            }
        }while (true);
    }
    static void printInvalidAccess(){
        System.out.println("******잘못된 접근입니다.******");
    }

    // admin page
    static void showBookHome(){
        titleLine("도서 관리 프로그램");
        librarian.printAllBook();
    }
    static void registerBookHome(){
        titleLine("도서 등록");
        System.out.println("도서이름>>");
        String name = (String)nextLineTypeCheck(2);
        System.out.println("도서IDSN>>");
        Long isbn = (Long)nextLineTypeCheck(4);
        System.out.println("작가>>");
        String author = (String)nextLineTypeCheck(2);
        System.out.println("출판 날짜(yyyy-mm-dd)>>");
        String publishedDate = (String)nextLineTypeCheck(3);
        try{
            librarian.registerBook(isbn,name,author,publishedDate);
            System.out.printf("도서 [%s] 등록이 완료되었습니다",name);
            System.out.println();
        }catch (Exception e){
            printInvalidAccess();
        }
    }
    static void updateBookHome(){
        librarian.printAllBook();
        try{
            long updateBookId= PrinterScanner.inputLine();
            System.out.println("도서이름>>");
            String newName = scanner.nextLine();
            System.out.println("도서IDSN>>");
            Long newIsbn = Long.parseLong(scanner.nextLine());
            System.out.println("작가>>");
            String newAuthor = scanner.nextLine();
            System.out.println("출판 날짜(yyyy-mm-dd)>>");
            LocalDate newPublishedDate = LocalDate.parse(scanner.nextLine());
            librarian.updateBookById(updateBookId, newIsbn, newName, newAuthor, newPublishedDate);
            System.out.println("수정한 도서정보가 반영 되었습니다");
        }catch (Exception e){
            printInvalidAccess();
        }
    }
    static void deleteBookHome(){
        PrinterScanner.titleLine("도서 삭제");
        librarian.printAllBook();
        long deleteBookId = PrinterScanner.inputLine();
        librarian.deleteBookById(deleteBookId);
    }
    static void printAllMemberHome(){
        titleLine("모든 회원 목록입니다");
        librarian.printAllMember();
    }
    static void addMemberHome(){
        messageLine("회원가입은 회원화면에서 가능합니다");
    }
    static void updateMemberHome(){
        titleLine("회원 정보 수정");
        System.out.print("회원 ID >>");
        String id = scanner.nextLine();
        try {
            Member member = librarian.findMemberById(id);
            librarian.printMember(member);
            System.out.print("new name >>");
            String name = scanner.nextLine();
            System.out.print("new PW >>");
            String pw = scanner.nextLine();
            librarian.updateMember(member,name,pw);
            messageLine("수정사항이 반영되었습니다.");
        }catch (InvalidMemberException e){
            messageLine("존재하지 않는 회원입니다");
        }
    }
    static void deleteMemberHome(){
        titleLine("모든 회원 목록입니다");
        librarian.printAllMember();
        System.out.print("삭제할 회원 ID >>");
        String id = scanner.nextLine();
        Member member = null;
        try {
            member = librarian.findMemberById(id);
            librarian.deleteMember(member);
            messageLine("수정사항이 반영되었습니다.");
        } catch (InvalidMemberException e) {
            messageLine("해당 아이디가 존재하지 않습니다.");
        }
    }

    // member page
    static int MemberLoanHome(Member member){
        titleLine("대여 화면 입니다.");
        System.out.println("1. 도서 대여");
        System.out.println("2. 도서 반납");
        System.out.println("3. 대여 기간 연장");
        return inputLine();
    }
    static void borrowBookHome(Member member){
        titleLine("대여할 책을 선택하세요");
        librarian.printAllBook();
        System.out.print("책 ID 입력>>");
        long bookId = (long)nextLineTypeCheck(4);
        Book book = librarian.findById(bookId);
        member.borrowBook(book);
        messageLine("요청이 완료되었습니다.");
    }

    public static void returnBookHome(Member member) {
        titleLine("반납할 책을 선택하세요");
        member.printAllBorrowBook();
        System.out.print("책 ID 입력>>");
        long bookId = (long)nextLineTypeCheck(4);
        Book book = librarian.findById(bookId);
        member.returnBook(book);
        messageLine("요청이 완료되었습니다.");

    }

    public static void extendBookHome(Member member) {
        titleLine("연장할 책을 선택 하세요");
        member.printAllBorrowBook();
        System.out.print("책 ID 입력>>");
        long bookId = (long)nextLineTypeCheck(4);
        Book book = librarian.findById(bookId);
        member.extendBook(book);
        messageLine("요청이 완료되었습니다.");
    }
}
