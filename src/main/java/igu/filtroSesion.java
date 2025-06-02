/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author HÉCTOR M GALLI
 */package igu;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import logica.Usuario;

@WebFilter(filterName = "filtroSesion", urlPatterns = {
    "/homeInstitucion.jsp",
    "/homeDepartamento.jsp"
})
public class filtroSesion implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Verificamos que haya sesión y usuario autenticado
        if (session != null && session.getAttribute("usuario") != null) {
            Usuario u = (Usuario) session.getAttribute("usuario");
            String rol = u.getRol();
            String path = req.getServletPath();

            // Control por rol y destino
            if (path.contains("homeInstitucion.jsp") && "Institucion".equalsIgnoreCase(rol)) {
                chain.doFilter(request, response);
            } else if (path.contains("homeDepartamento.jsp") && "Departamento".equalsIgnoreCase(rol)) {
                chain.doFilter(request, response);
            } else {
                // Usuario autenticado pero no autorizado
                res.sendRedirect("login.jsp?error=acceso");
            }
        } else {
            // No autenticado
            res.sendRedirect("login.jsp");
        }
    }
}
