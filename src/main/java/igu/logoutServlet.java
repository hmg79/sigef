/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package igu;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import logica.Usuario;
import persistencia.UsuarioDAO;

/**
 *
 * @author HÉCTOR M GALLI
 */

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logoutServlet"})
public class logoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener la sesión actual, si existe
        HttpSession session = request.getSession(false);

        // Invalidar solo si hay una sesión activa
        if (session != null) {
            session.invalidate();
        }

        // Redirigir al login
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
