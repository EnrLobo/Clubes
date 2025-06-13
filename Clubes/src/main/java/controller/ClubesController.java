package controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Clube;
import model.Competicao;
import model.ModelException;
import model.dao.ClubeDAO;
import model.dao.CompeticaoDAO;
import model.dao.DAOFactory;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {
    "/clubs",
    "/clube/form",
    "/clube/delete",
    "/clube/insert",
    "/clube/update"
})
public class ClubesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI().substring(req.getContextPath().length());

        switch (action) {
            case "/clube/form": {
                carregarCompeticoes(req);
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-clube.jsp");
                break;
            }
            case "/clube/update": {
                carregarCompeticoes(req);
                Clube c = loadClube(req);
                req.setAttribute("clube", c);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-clube.jsp");
                break;
            }
            default: {
                listClubes(req);
                ControllerUtil.transferSessionMessagesToRequest(req);
                ControllerUtil.forward(req, resp, "/index.jsp");
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI().substring(req.getContextPath().length());

        if (action == null || action.isEmpty()) {
            ControllerUtil.forward(req, resp, "/index.jsp");
            return;
        }

        switch (action) {
            case "/clube/delete":
                deleteClube(req, resp);
                ControllerUtil.redirect(resp, req.getContextPath() + "/clubs");
                break;
            case "/clube/insert":
                insertClube(req, resp);
                ControllerUtil.redirect(resp, req.getContextPath() + "/clubs");
                break;
            case "/clube/update":
                updateClube(req, resp);
                break;
            default:
                System.out.println("URL inválida " + action);
                ControllerUtil.redirect(resp, req.getContextPath() + "/clubs");
                break;
        }
    }


    private void carregarCompeticoes(HttpServletRequest req) {
        CompeticaoDAO compDAO = DAOFactory.createDAO(CompeticaoDAO.class);
        List<Competicao> competicoes = new ArrayList<>();

        try {
            competicoes = compDAO.listAll();
        } catch (ModelException e) {
            e.printStackTrace();
            // Pode colocar mensagem de erro na requisição se quiser
        }

        req.setAttribute("competicoes", competicoes);
    }

    private Clube loadClube(HttpServletRequest req) {
        String clubeIdParameter = req.getParameter("id");
        if (clubeIdParameter == null) {
            ControllerUtil.errorMessage(req, "ID do clube não fornecido.");
            return null;
        }

        int clubeId;
        try {
            clubeId = Integer.parseInt(clubeIdParameter);
        } catch (NumberFormatException e) {
            ControllerUtil.errorMessage(req, "ID do clube inválido.");
            return null;
        }

        ClubeDAO dao = DAOFactory.createDAO(ClubeDAO.class);

        try {
            Clube c = dao.findById(clubeId);
            if (c == null)
                throw new ModelException("Clube não encontrado para alteração");
            return c;
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }

        return null;
    }

    private void updateClube(HttpServletRequest req, HttpServletResponse resp) {
        String clubeNome = req.getParameter("nome");
        String clubeEstado = req.getParameter("estado");
        String compIdStr = req.getParameter("competicaoId");

        if (clubeNome == null || clubeEstado == null || compIdStr == null) {
            ControllerUtil.errorMessage(req, "Parâmetros inválidos para atualização.");
            return;
        }

        int compId;
        try {
            compId = Integer.parseInt(compIdStr);
        } catch (NumberFormatException e) {
            ControllerUtil.errorMessage(req, "Competição inválida.");
            return;
        }

        Clube clube = loadClube(req);
        if (clube == null) {
            ControllerUtil.errorMessage(req, "Clube não encontrado para atualização.");
            return;
        }

        clube.setNome(clubeNome);
        clube.setEstado(clubeEstado);
        clube.setCompeticao(new Competicao(compId));

        ClubeDAO dao = DAOFactory.createDAO(ClubeDAO.class);

        try {
            if (dao.update(clube)) {
                ControllerUtil.sucessMessage(req, "Clube '" + clube.getNome() + "' atualizado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Clube '" + clube.getNome() + "' não pode ser atualizado.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void insertClube(HttpServletRequest req, HttpServletResponse resp) {
        String clubeNome = req.getParameter("nome");
        String clubeEstado = req.getParameter("estado");
        String compIdStr = req.getParameter("competicaoId");

        if (clubeNome == null || clubeEstado == null || compIdStr == null) {
            ControllerUtil.errorMessage(req, "Parâmetros inválidos para inserção.");
            return;
        }

        int compId;
        try {
            compId = Integer.parseInt(compIdStr);
        } catch (NumberFormatException e) {
            ControllerUtil.errorMessage(req, "Competição inválida.");
            return;
        }

        Clube clube = new Clube();
        clube.setNome(clubeNome);
        clube.setEstado(clubeEstado);
        clube.setCompeticao(new Competicao(compId));

        ClubeDAO dao = DAOFactory.createDAO(ClubeDAO.class);

        try {
            if (dao.save(clube)) {
                ControllerUtil.sucessMessage(req, "Clube '" + clube.getNome() + "' salvo com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Clube '" + clube.getNome() + "' não pode ser salvo.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void deleteClube(HttpServletRequest req, HttpServletResponse resp) {
        String clubeIdParameter = req.getParameter("id");

        if (clubeIdParameter == null) {
            ControllerUtil.errorMessage(req, "ID do clube não fornecido.");
            return;
        }

        int clubeId;
        try {
            clubeId = Integer.parseInt(clubeIdParameter);
        } catch (NumberFormatException e) {
            ControllerUtil.errorMessage(req, "ID do clube inválido.");
            return;
        }

        ClubeDAO dao = DAOFactory.createDAO(ClubeDAO.class);

        try {
            Clube c = dao.findById(clubeId);
            if (c == null)
                throw new ModelException("Clube não encontrado para deleção");

            if (dao.delete(c)) {
                ControllerUtil.sucessMessage(req, "Clube '" + c.getNome() + "' deletado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Clube '" + c.getNome() + "' não pode ser deletado.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void listClubes(HttpServletRequest req) {
        ClubeDAO dao = DAOFactory.createDAO(ClubeDAO.class);

        List<Clube> clubes = null;
        try {
            clubes = dao.listAll();
        } catch (ModelException e) {
            e.printStackTrace();
        }

        if (clubes != null)
            req.setAttribute("clubes", clubes); // mudei de "posts" para "clubes" para ser coerente
    }
}
