/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package igu;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author HÉCTOR M GALLI
 */
@WebFilter("/*") //Se aplicara a todo el sistema
public class filtroAcceso implements Filter{
 @Override
 public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI();

        if (session == null || session.getAttribute("usuario") == null) {
            if (!uri.endsWith("login.jsp") && !uri.contains("loginServlet")) {
                response.sendRedirect("login.jsp");
                return;
            }
        } else {
            String rol = (String) session.getAttribute("rol");

            // Ejemplo: restringir acceso a página exclusiva para "departamento"
            if (uri.contains("homeDepartamento.jsp") && !"Departamento".equals(rol)) {
                response.sendRedirect("accesoDenegado.jsp");
                return;
            }

            if (uri.contains("homeInstitucion.jsp") && !"Institucion".equals(rol)) {
                response.sendRedirect("accesoDenegado.jsp");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}
