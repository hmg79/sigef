<%-- 
    Document   : informePorTipoCurso
    Created on : 7 jun. 2025, 07:52:14
    Author     : HÉCTOR M GALLI
--%>


<%@page import="java.util.List"%>
<%@page import="logica.EstadisticaCurso"%>
<%@page import="persistencia.InformesDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="logica.Usuario"%> 
<%@page import="logica.Institucion" %>
<%@page import="persistencia.InstitucionDAO" %>
<%
    //Verifico las credenciales del usuario
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = (usuario!=null)? usuario.getRol():null;
    int IdInstitucion= usuario.getId_institución();
    if (usuario == null || !"Institucion".equals(rol)) {
        response.sendRedirect("accesoDenegado.jsp");
        return;
    }
    //Obtengo datos de la institucion
    InstitucionDAO institucionDAO=new InstitucionDAO();
    Institucion institucion=institucionDAO.obtenerInstitucionporID(IdInstitucion);
    //obtengo los datos estadisticos desde un objeto dao
    InformesDAO informesDAO=new InformesDAO();
    List <EstadisticaCurso> estadisticas=informesDAO.obtenerEstadisticaPorTipoCurso(IdInstitucion);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Rol Instituciones</title>
        <link rel="stylesheet" type="text/css" href="css/estilos.css">
    </head>
    <body>
             <div class="departamento">
                 <div class="header">
                    <span class="bienvenido">
                        Bienvenido, <%= usuario.getUsuario() %> (Rol: <%= rol %>)<br>
                        ID Institucion:<%=usuario.getId_institución()   %>
                        Institución:<%=institucion.getNombre()%><br>
                    </span>
                    <span class="logout">
                        <a href="homeInstitucion.jsp"> Volver</a> <br>
                         <a href="logoutServlet">Cerrar sesión</a>
                    </span>
                 </div>
                        <p>Informe Total por Tipo de Curso Realizado</p>
 <table>
        <thead>
            <tr>
                <th>Tipo de Capacitación</th>
                <th>Inscriptos</th>
                <th>Cursando</th>
                <th>Egresados</th>
                <th>Total</th>
            </tr>   
        </thead>
        <tbody>
            <% for (EstadisticaCurso e : estadisticas) { %>
                <tr>
                    <td><%= e.getTipo()%></td>
                    <td><%= e.getInscripto() %></td>
                    <td><%= e.getCursando() %></td>
                    <td><%= e.getEgresado() %></td>
                    <td><%= e.getTotal() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
             </div>


    </body>
</html>
