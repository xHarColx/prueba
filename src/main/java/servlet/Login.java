package servlet;

import dao.UsuarioJpaController;
import dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    UsuarioJpaController usuDAO = new UsuarioJpaController();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String user = request.getParameter("user");
            String pass = request.getParameter("pass");
            Usuario usu = new Usuario(user, pass);
            Usuario b = usuDAO.validarUsuario(usu);
            System.out.println(b);
            if (b != null) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", user);
                String token = utils.JwtUtil.generarToken(user);
                // Respuesta en formato JSON
                out.println("{\"resultado\":\"ok\",\"token\":\"" + token + "\"}");
            } else {
                out.println("{\"resultado\": \"errorrrrr\"}");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
