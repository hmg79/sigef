<%-- 
    Document   : testLog
    Created on : 7 jun. 2025, 08:55:31
    Author     : USUARIO
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="persistencia.InformesDAO"%>
<%@page import="java.util.List"%>
<%@page import="logica.EstadisticaCurso"%>
<!DOCTYPE html>
<html>
<head>
    <title>Log de Prueba</title>
</head>
<body>
    <h2>Test de conexión y consulta a base de datos</h2>

    <%
        try {
            int idInstitucion = 19; 
            out.println("<p>Buscando estadísticas para institución ID: " + idInstitucion + "</p>");

            InformesDAO dao = new InformesDAO();
            List<EstadisticaCurso> lista = dao.obtenerEstadisticaPorTipoCurso(idInstitucion);

            if (lista == null || lista.isEmpty()) {
                out.println("<p style='color:red;'>No se obtuvieron resultados.</p>");
            } else {
                out.println("<table border='1'>");
                out.println("<tr><th>Tipo</th><th>Total</th><th>Inscriptos</th><th>Egresados</th><th>Cursando</th></tr>");
                for (EstadisticaCurso ec : lista) {
                    out.println("<tr>");
                    out.println("<td>" + ec.getTipo() + "</td>");
                    out.println("<td>" + ec.getTotal() + "</td>");
                    out.println("<td>" + ec.getInscripto() + "</td>");
                    out.println("<td>" + ec.getEgresado() + "</td>");
                    out.println("<td>" + ec.getCursando() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
        } catch (Exception e) {
            out.println("<p>Error al consultar: " + e.getMessage() + "</p>");
            e.printStackTrace(new java.io.PrintWriter(out));
        }
    %>
</body>
</html>
