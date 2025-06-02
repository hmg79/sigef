/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package igu;

/**
 *
 * @author HÉCTOR M GALLI
 */
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import logica.Usuario;
import persistencia.UsuarioDAO;
import logica.EmailSender;

@WebServlet(name = "RecuperarServlet", urlPatterns = {"/recuperarServlet"})
public class recuperarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.buscarPorEmail(email);

        if (u != null) {
            String asunto = "Recuperación de acceso SIGEF";
            String cuerpo = "Hola " + u.getUsuario() + ",\n\n"
                          + "Tu contraseña actual es: " + u.getContraseña() + "\n\n"
                          + "Recomendamos que la cambies al iniciar sesión.";

            boolean enviado = EmailSender.enviar(email, asunto, cuerpo);
            if (enviado) {
                request.setAttribute("mensaje", "Se ha enviado un correo con la información de acceso.");
            } else {
                request.setAttribute("error", "Error al enviar el correo.");
            }
        } else {
            request.setAttribute("error", "No se encontró una cuenta asociada a ese correo.");
        }

        request.getRequestDispatcher("recuperar.jsp").forward(request, response);
    }
}