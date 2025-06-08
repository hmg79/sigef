<%-- 
    Document   : textLocalidad
    Created on : 5 jun. 2025, 11:00:55
    Author     : USUARIO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prueba conexión a Localidad</title>
    <style>
        table {
            border-collapse: collapse;
            width: 60%;
            margin: auto;
        }
        th, td {
            border: 1px solid #666;
            padding: 8px;
            text-align: left;
        }
        h1 {
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>Listado de Localidades</h1>
    <%
        String url = "jdbc:mysql://localhost:3306/Sigef"; // Cambiar por tu base
        String user = "root"; // Cambiar por tu usuario
        String password = ""; // Cambiar por tu clave

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT n.nombre AS tipocapacitacion,"+
                  "COUNT(*) AS total,"+
                  "SUM(CASE WHEN i.estado = 'inscripto' THEN 1 ELSE 0 END) AS inscripto,"+
                  "SUM(CASE WHEN i.estado = 'egresado' THEN 1 ELSE 0 END) AS egresado,"+
                  "SUM(CASE WHEN i.estado = 'cursando' THEN 1 ELSE 0 END) AS cursando "+
                  "FROM inscripción i"+
                  "JOIN tipocapacitacion t ON i.id_curso = t.id_tipocapacitacion"+
                  "JOIN nivel n ON t.id_nivel = n.id_nivel"+
                  "WHERE i.id_institucion = 19"+
                  "GROUP BY n.nombre");

            out.println("<table>");
            out.println("<tr><th>ID</th><th>Nombre</th></tr>");
            while (rs.next()) {
                int id = rs.getInt("id_nivel");
                String nombre = rs.getString("nombre");
                out.println("<tr><td>" + id + "</td><td>" + nombre + "</td></tr>");
            }
            out.println("</table>");
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(new java.io.PrintWriter(out)); // Muestra detalles para depuración
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    %>
</body>
</html>
