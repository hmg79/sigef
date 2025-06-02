<%-- 
    Document   : recuperar
    Created on : 26 may. 2025, 03:34:17
    Author     : USUARIO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recuperar Acceso</title>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <div class="login-container">
        <h2>Recuperar acceso</h2>
        <form method="post" action="recuperarServlet">
            <label for="email">Correo electrónico:</label>
            <input type="email" name="email" required>
            <input type="submit" value="Enviar acceso">
        </form>
        <p><a href="login.jsp">Volver al login</a></p>
        <% if (request.getAttribute("mensaje") != null) { %>
            <p style="color: green;"><%= request.getAttribute("mensaje") %></p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>
    </div>
    </body>
</html>
