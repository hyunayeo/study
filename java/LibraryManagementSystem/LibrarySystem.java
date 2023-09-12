package library;

public class LibrarySystem {
    public static void start(){
        boolean run = true;
        TestCode.main(null);
        while (run){
            // 시작 화면
            PrinterScanner.startHome();
            int userInput = PrinterScanner.inputLine();
            switch (userInput){
                case 0:
                    // 종료
                    run = false;
                    break;

                case 1:
                    // 관리자 화면
                    PrinterScanner.mainHome();
                    int option = PrinterScanner.inputLine();
                    switch (option){
                        case 0:
                            run = false;
                            break;
                        case 1:
                            PrinterScanner.showBookHome();
                            break;
                        case 2:
                            PrinterScanner.registerBookHome();
                            break;
                        case 3:
                            PrinterScanner.updateBookHome();
                            break;
                        case 4:
                            PrinterScanner.deleteBookHome();
                            break;
                        case 5:
                            PrinterScanner.printAllMemberHome();
                            break;
                        case 6:
                            PrinterScanner.addMemberHome();
                            break;
                        case 7:
                            PrinterScanner.updateMemberHome();
                            break;
                        case 8:
                            PrinterScanner.deleteMemberHome();
                            break;
                        default:
                            PrinterScanner.printInvalidAccess();
                            break;
                    }
                    break;
                case 2:
                    PrinterScanner.memberHome();
                    int userLoginOpt = PrinterScanner.inputLine();
                    switch(userLoginOpt){
                        case 1:
                            // 회원 로그인 화면
                            Boolean loginRun=true;
                            while (loginRun){

                                Member member = PrinterScanner.loginHome();
                                if (member==null) continue;
                                boolean userRun = true;
                                while (userRun) {
                                    int borrowOpt = PrinterScanner.MemberLoanHome(member);
                                    switch (borrowOpt){
                                        case 1:
                                            PrinterScanner.borrowBookHome(member);
                                            break;
                                        case 2:
                                            PrinterScanner.returnBookHome(member);
                                            break;
                                        case 3:
                                            PrinterScanner.extendBookHome(member);
                                            break;
                                        default:
                                            userRun=false;
                                            loginRun =false;
                                            break;
                                    }
                                }
                            }
                            break;
                        case 2:
                            // 회원가입 화면
                            PrinterScanner.registerMemberHome();
                            break;
                    }

                    break;
                case 3:
                    // 도서 조회 화면
                    break;

            }



        }
    }
}
