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

    try {
        String jdbcDriver = "jdbc:mysql://localhost:3306/jsptestdb1?" +
                "useUnicode=true&characterEncoding=utf8";
        String dbUser = "root";
        String dbPass = "1234";

        conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);

        String query = "insert into user_table values (null, ?, ?, ?, ?)";

        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, email);
        pstmt.setString(3, address);
        pstmt.setString(4, hobby);
        pstmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
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
<%=name%>
<%=email%>
<%=address%>
<%=hobby%>