<%-- 
    Document   : homeInstitucion
    Created on : 26 may. 2025, 02:38:17
    Author     : Héctor M Galli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="logica.Usuario"%> 
<%@page import="logica.Institucion" %>
<%@page import="persistencia.InstitucionDAO" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = (usuario!=null)? usuario.getRol():null;
    if (usuario == null || !"Institucion".equals(rol)) {
        response.sendRedirect("accesoDenegado.jsp");
        return;
    } 
    InstitucionDAO institucionDAO=new InstitucionDAO();
    Institucion institucion=institucionDAO.obtenerInstitucionporID(usuario.getId_institución());
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
                        Institución:<%=institucion.getNombre()%>
                        
                    </span>
           
                     <a class="logout" href="logoutServlet">Cerrar sesión</a>
                 </div>
                <p>Generar Informes Estadisticos</p>
                <p><a href="informePorTipoCurso.jsp">Informe por Tipo de Curso</a>
                <p><a href="informePorCurso.jsp">Informe por Curso</a></p>

             </div>

    </body>
</html>
