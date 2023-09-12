package library;

public class TestCode {
    public static void main(String[] args) {
        Librarian librarian = Librarian.getInstance();
        librarian.registerBook(9791168473690L,"세이노의 가르침", "세이노","2023-03-02");
        librarian.registerBook(9791192836188L, "문과 남자의 과학 공부","유시민","2023-06-23");
        librarian.registerBook(9791167740984L, "도둑맞은 집중력","요한 하리","2023-04-28");
        librarian.registerBook(9788901272580L, "역행자","자청","2023-05-29");
        librarian.registerBook(9791191891287L, "메리골드 마음 세탁소","윤정은","2023-03-06");

        try{
            librarian.registerMember("test","testid", "testpw");
            Member member = librarian.loginMember("testid","testpw");
            member.borrowBook(librarian.findById(1));

            librarian.registerMember("test1","test", "test");
            Member member1 = librarian.loginMember("test","test");
            member1.borrowBook(librarian.findById(4));
            member1.borrowBook(librarian.findById(3));


        }catch (Exception e){

        }

    }
}
