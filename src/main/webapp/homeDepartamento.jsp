<%-- 
    Document   : homeDepartamento
    Created on : 26 may. 2025, 02:38:43
    Author     : Héctor M Galli
--%>

<%@page import="logica.Institucion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="logica.Usuario" %>
<%@ page import="persistencia.LocalidadDAO" %>
<%@ page import="persistencia.InstitucionDAO" %>
<%@ page import="logica.Localidad" %>
<%@ page import="java.util.ArrayList" %>


<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = (usuario!=null)? usuario.getRol():null;
    if (usuario == null || !"Departamento".equals(rol)) {
        response.sendRedirect("accesoDenegado.jsp");
        return;
    }
 %>
 <%
    LocalidadDAO datosLocalidad=new LocalidadDAO();
    ArrayList<Localidad>localidades=datosLocalidad.obtenerTodas();
   InstitucionDAO datosInstitucion=new InstitucionDAO();
    ArrayList<Institucion>instituciones=datosInstitucion.listadoInstituciones();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Rol Departamento</title>
        <link rel="stylesheet" type="text/css" href="css/estilos.css">
    </head>
    <body>
        <div class="departamento">
            <div class="header">
                <span class="bienvenido">
                    Bienvenido, <%= usuario.getUsuario() %> (Rol: <%= rol %>)
                </span>

                <p><a class="logout" href="logoutServlet">Cerrar sesión</a></p>
            </div>
        <p>Generar Informes Estadisticos</p>
            <h2>Estadísticas Generales</h2>
        <form method="post" action="ConsultaEstadisticaServlet">
            <label for="localidad">Localidad:</label>
            <!--CARGO Las localidades en forma dinamica ordenadas -->
            <select name="localidad" id="localidad" onchange="cargarInstitucionesPorLocalidad()">
                <option value="">Todas</option>
                <% for(Localidad l: localidades){
                %>
                <option value="<%=l.getId_localidad()%>"><%=l.getNombre()%> </option>
                <% 
                    } 
                %>
            </select>

            <label for="institucion">Institución:</label>
            <select name="institucion" id="institucion">
                <!-- Cargo las Instituciones -->
                <option value="">Todas</option>
                <% for(Institucion l: instituciones){
                %>
                <option value="<%=l.getId_institucion()  %>"><%=l.getNombre()%> </option>
                <% 
                    } 
                %>
            </select>

            <label for="tipo">Tipo de Capacitación:</label>
            <select name="tipo" id="tipo">
                <option value="">Todas</option>
                <option value="Formación Profesional">Formación Profesional</option>
                <option value="Capacitación Laboral">Capacitación Laboral</option>
                <option value="Terminalidad Primaria">Terminalidad Primaria</option>
            </select>

            <button type="submit">Consultar</button>
        </form>

        <div class="resultados">
            <!-- Aquí se mostrarán los resultados de la consulta -->
            <table>
                <thead>
                    <tr>
                        <th>Curso</th>
                        <th>Localidad</th>
                        <th>Institución</th>
                        <th>Inscriptos</th>
                        <th>Cursando</th>
                        <th>Egresados</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Datos inyectados por JSP tras la consulta -->
                </tbody>
            </table>
        </div>

        <form method="post" action="GenerarPDFServlet">
            <input type="hidden" name="filtrosAplicados" value="...">
            <button type="submit">Generar Informe PDF</button>
        </form>
        </div>
            <!-- Programación en ayax para cargar solo los datos necesarios -->
            <script>
                function cargarInstitucionesPorLocalidad(){
                    const idLocalidad=document.getElementById("localidad").value;
                    const nepSelect=document.getElementById("institucion").value;
                    //Si elegimos todos, debe mostrar todos los nep
                    if(idLocalidad=="0"){
                        window.location.reload(); //o cargar todas las instituciones nuevamente
                        return ;
                    }
                    fetch("obtenerInstituciones?idLocalidad="+idLocalidad)
                            .then(response=>response.json())
                            .then(data=>{
                                nepSelect.innerHTML=""; //Vaciamos las opciones
                                data.forEatch(function(inst){
                                    const option=document.createElement("option");
                                    option.value=inst.id;
                                    option.text=inst.nombre;
                                    nepSelect.appendChild(option);
                                });
                            })
                                    .catch(error=>{
                                        console.error("Error al cargar las Instituciones;",error);
                                    });
                }
            </script>
    </body>
</html>
