package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.utils.PasswordEncryptor;


@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String userLogin = req.getParameter("user_login");
		String userPw = req.getParameter("user_pw");

		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		User user = null;

		try {
			user = dao.findByEmail(userLogin);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		if (user != null && PasswordEncryptor.checkPassword(userPw, user.getPassword())) {
			req.getSession().setAttribute("usuario_logado", user);
			resp.sendRedirect(req.getContextPath() + "/clubs");
		} else {
			resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=true");
		}
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
		resp.sendRedirect("/Clubes/login.jsp");
	}
	
}