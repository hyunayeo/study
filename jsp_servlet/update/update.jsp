<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%
    request.setCharacterEncoding("utf-8");
    String name = request.getParameter("name");
    String email = request.getParameter("email");

    String address = request.getParameter("address1");
    address += request.getParameter("address2");

    String[] values = request.getParameterValues("hobby");
    ArrayList list = new ArrayList(Arrays.asList(values));
    String hobby = String.join(", ", list);

    Class.forName("com.mysql.jdbc.Driver");

    Connection conn = null;
    PreparedStatement pstmt = null;
    int updateCount = 0;

    try {
        String jdbcDriver = "jdbc:mysql://localhost:3306/jsptestdb1?" +
                "useUnicode=true&characterEncoding=utf8";
        String dbUser = "root";
        String dbPass = "1234";

        conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);

        String query = "update user_table set email=?, address=?, hobby=? where name=?";

        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, email);
        pstmt.setString(2, address);
        pstmt.setString(3, hobby);
        pstmt.setString(4, name);
        updateCount = pstmt.executeUpdate();
        if (updateCount == 1) {
%>
성공적으로 반영되었습니다
<%
} else {
%>
변경사항이 적용되지 않았습니다.
<%
    }
} catch (Exception e) {
    e.printStackTrace();
%>
에러가 발생하였습니다.
<%
    } finally {
        if (pstmt != null) try {
            pstmt.close();
        } catch (SQLException ex) {
        }
        if (conn != null) try {
            conn.close();
        } catch (SQLException ex) {
        }
    }

%>
