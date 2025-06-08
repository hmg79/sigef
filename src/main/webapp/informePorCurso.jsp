<%-- 
    Document   : informePorCurso
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
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = (usuario!=null)? usuario.getRol():null;
    int IdInstitucion=usuario.getId_institución();
    if (usuario == null || !"Institucion".equals(rol)) {
        response.sendRedirect("accesoDenegado.jsp");
        return;
    } 
    //Cargo datos de Institucion
    InstitucionDAO institucionDAO=new InstitucionDAO();
    Institucion institucion=institucionDAO.obtenerInstitucionporID(IdInstitucion);
    //Cargo datos de Informes
    InformesDAO informesDAO=new InformesDAO();
    List<EstadisticaCurso> cursos=informesDAO.obtenerEstadisticasPorCurso(IdInstitucion);
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
                <p>Informe Total por Curso</p>
                  <%
        if (cursos != null && !cursos.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                    <th>Nombre del Curso</th>
                    <th>Total </th>
                    <th>Total Inscriptos</th>
                    <th>Total Egresados</th>
                    <th>Total Cursando</th>
                    
                </tr>
            </thead>
            <tbody>
                <%
                    for (EstadisticaCurso curso : cursos) {
                %>
                <tr>
                    <td><%= curso.getNombreCurso() %></td>
                    <td><%= curso.getTotal() %></td>
                    <td><%= curso.getInscripto() %></td>
                    <td><%= curso.getEgresado() %></td>
                    <td><%= curso.getCursando() %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <p style="text-align:center;">No se encontraron cursos registrados para esta institución.</p>
    <%
        }
    %>
             </div>
    </body>
</html>
