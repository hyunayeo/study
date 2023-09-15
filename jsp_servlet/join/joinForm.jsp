<%@ page contentType="text/html; charset=utf-8" %>

<html>
<body>
<form action="join.jsp" method="post">
    <table border="1">
        <h2>회원가입</h2>
        <tr>
            <td>닉네임:<input type="text" name="name"></td>
        </tr>
        <tr>
            <td>이메일:<input type="text" name="email"></td>
        </tr>
        <tr>
            <td>주소1:<input type="text" name="address1"></td>
        </tr>
        <tr>
            <td>주소2:<input type="text" name="address2"></td>
        </tr>
        <tr>
            <td>취미:
                <input type="checkbox" value="exercise" name="hobby">운동
                <input type="checkbox" value="music" name="hobby">음악
                <input type="checkbox" value="trip" name="hobby">여행
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="저장">
            </td>
        </tr>
    </table>
</form>
</body>
</html>