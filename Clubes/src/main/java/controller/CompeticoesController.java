package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Competicao;
import model.ModelException;


import model.dao.CompeticaoDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/comps", "/comp/form", 
		"/comp/insert", "/comp/delete", "/comp/update"})
public class CompeticoesController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI().substring(req.getContextPath().length());
		
		switch (action) {
		case "/comp/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-comp.jsp");			
			break;
		}
		case "/comp/update": {
			String idStr = req.getParameter("compId");
			int idComp = Integer.parseInt(idStr); 
			
			CompeticaoDAO dao = DAOFactory.createDAO(CompeticaoDAO.class);
			
			Competicao comp = null;
			
			try {
				comp = dao.findById(idComp);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			CommonsController.listUsers(req);
			req.setAttribute("action", "update");
			req.setAttribute("competicao", comp);
			ControllerUtil.forward(req, resp, "/form-comp.jsp");
			break;
		}
		default:
			listComps(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/competicao.jsp");
		}
	}
	
	private void listComps(HttpServletRequest req) {
		CompeticaoDAO dao = DAOFactory.createDAO(CompeticaoDAO.class);
		
		List<Competicao> comp = null;
		try {
			comp = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (comp != null)
			req.setAttribute("competicoes", comp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI().substring(req.getContextPath().length());

		
		switch (action) {
		case "/comp/insert": {
			insertComp(req, resp);			
			break;
		}
		case "/comp/delete" :{
			
			deleteCompeticao(req, resp);
			
			break;
		}
		
		case "/comp/update" :{
			updateCompeticao(req, resp);
			break;
		}
		
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/comps");
	}

	private void updateCompeticao(HttpServletRequest req, HttpServletResponse resp) {
		String compIdStr = req.getParameter("compId");
		String compName = req.getParameter("nome");
		String clubeR = req.getParameter("clubeRealizacao");
		String cidadeR = req.getParameter("cidadeRealizacao");
		
		Competicao comp = new Competicao(Integer.parseInt(compIdStr));
		comp.setNome(compName);
		comp.setNomeClube(clubeR);
		comp.setCidade(cidadeR);
		
		
		CompeticaoDAO dao = DAOFactory.createDAO(CompeticaoDAO.class);
		
		try {
			if (dao.update(comp)) {
				ControllerUtil.sucessMessage(req, "Competição '" + 
						comp.getNome() + "' atualizada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Competição '" + 
						comp.getNome() + "' não pode ser atualizada.");
			}				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}		
	}

	private void deleteCompeticao(HttpServletRequest req, HttpServletResponse resp) {
		String compIdParameter = req.getParameter("id");
		
		int compId = Integer.parseInt(compIdParameter);
		
		CompeticaoDAO dao = DAOFactory.createDAO(CompeticaoDAO.class);
		
		try {
			Competicao comp = dao.findById(compId);
			
			if (comp == null)
				throw new ModelException("Competição não encontrada para deleção.");
			
			if (dao.delete(comp)) {
				ControllerUtil.sucessMessage(req, "Competição '" + 
						comp.getNome() + "' deletada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Competição '" + 
						comp.getNome() + "' não pode ser deletado. "
								+ "Há dados relacionados à empresa.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof 
					SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertComp(HttpServletRequest req, HttpServletResponse resp) {
		String compNome = req.getParameter("nome");
		String cidadeR = req.getParameter("cidadeRealizacao");
		String clubeR = req.getParameter("clubeRealizacao");

		
		Competicao comp = new Competicao();
		comp.setNome(compNome);
		comp.setCidade(cidadeR);
		comp.setNomeClube(clubeR);
		
		
		CompeticaoDAO dao = DAOFactory.createDAO(CompeticaoDAO.class);
	
		try {
			if (dao.save(comp)) {
				ControllerUtil.sucessMessage(req, "Competição '" + comp.getNome() 
				+ "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Competição '" + comp.getNome()
				+ "' não pode ser salva.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
}
