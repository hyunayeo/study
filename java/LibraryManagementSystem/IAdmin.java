package library;

public interface IAdmin {
    void printMember(Member member);
    void addMember(Member member);
    void updateMember(Member member, String name, String pw);
    void deleteMember(Member member);
}
