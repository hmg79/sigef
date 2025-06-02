<%-- 
    Document   : homeDepartamento
    Created on : 26 may. 2025, 02:38:43
    Author     : USUARIO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="logica.Usuario" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !"Departamento".equalsIgnoreCase(u.getRol())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Rol Departamento</title>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <div class="container">
        <h2>Bienvenido, <%= u.getUsuario() %> (Rol: <%= u.getRol() %>)</h2>
        <p>Este es el panel de control para usuarios del tipo <strong>Departamento</strong>.</p>

        <p><a href="logoutServlet">Cerrar sesión</a></p>
    </div>
    </body>
</html>
