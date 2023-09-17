<%@ page contentType="text/html; charset=utf-8" %>

<html>
<body>
<form action="update.jsp" method="post">
    <table border="1">
        <h2>정보 수정</h2>
        수정할 계정 닉네임을 입력하세요:<input type="text" name="name"></td>
        <tr>
            <td>회원정보를 수정하세요</td>
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