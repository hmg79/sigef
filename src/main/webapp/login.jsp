<%-- 
    Document   : login
    Created on : 25 may. 2025, 22:20:43
    Author     : USUARIO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="persistencia.UsuarioDAO" %>
<%@page import="logica.Usuario" %>
<%@page import="igu.loginServlet" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ingreso al Sistema</title>
         <link rel="stylesheet" type="text/css" href="css/estilos.css">
    </head>
    <body>
        <div class="login-container">
    <h2>Iniciar Sesión</h2>

    <form method="post" action="loginServlet">
        <label for="usuario">Usuario:</label>
        <input type="text" name="usuario" required>

        <label for="contraseña">Contraseña:</label>
        <input type="password" name="contraseña" required>

        <input type="submit" value="Ingresar">
    </form>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <p><a href="recuperar.jsp">¿Olvidaste tu contraseña?</a></p>
  
        </div>
    
    </body>
</html>
