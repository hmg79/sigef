/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package igu;

/**
 *
 * @author HÉCTOR M GALLI
 */


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import logica.Usuario;
import persistencia.UsuarioDAO;

@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String contraseña = request.getParameter("contraseña");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.validar(usuario, contraseña);
        
        if (u != null) {
            // Inicio de sesión exitoso
            HttpSession session = request.getSession();
            session.setAttribute("usuario", u);
            session.setAttribute("rol",u.getRol());
            // Redirigir según el rol
            String rol = u.getRol();
            if ("Institucion".equalsIgnoreCase(rol)) {
                response.sendRedirect("homeInstitucion.jsp");
            } else if ("Departamento".equalsIgnoreCase(rol)) {
                response.sendRedirect("homeDepartamento.jsp");
            } else {
                response.sendRedirect("home.jsp"); // por si hay otro tipo de rol
            }
        } else {
            // Error: usuario inválido o bloqueado
            request.setAttribute("error", "Usuario o contraseña incorrectos o cuenta bloqueada.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Si alguien entra por GET, lo redirigimos al login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}

