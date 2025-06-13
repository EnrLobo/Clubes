package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Clube;
import model.ModelException;
import model.Competicao;

public class MySQLClubeDAO implements ClubeDAO{

	@Override
	public boolean save(Clube club) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO clube VALUES "
				+ " (DEFAULT, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, club.getNome());
		db.setString(2, club.getEstado());
		db.setInt(3, club.getCompeticao().getId());
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Clube club) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE clube "
						 + " SET nome = ?,"
						 + " estado = ?,"
						 + " comp_id = ?"
						 + " WHERE id = ?";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, club.getNome());
		db.setString(2, club.getEstado());
		db.setInt(3, club.getCompeticao().getId());
		db.setInt(4, club.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Clube club) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM clube "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, club.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Clube> listAll() throws ModelException {
	
		DBHandler db = new DBHandler();
		
		List<Clube> clubs = new ArrayList<Clube>();
			
		String sqlQuery = "SELECT cl.*, c.nome AS nome_competicao "
                + "FROM clube cl "
                + "LEFT JOIN comps c ON cl.comp_id = c.id "
                + "ORDER BY cl.nome;";

		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Clube cl = createClube(db);
			
			clubs.add(cl);
		}
		
		return clubs;
	}

	@Override
	public Clube findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM clube WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Clube cl = null;
		while (db.next()) {
			cl = createClube(db);
			break;
		}
		
		return cl;
	}
	
	private Clube createClube(DBHandler db) throws ModelException {
		Clube cl = new Clube(db.getInt("id"));
		cl.setNome(db.getString("nome"));
		cl.setEstado(db.getString("estado"));
		
		CompeticaoDAO compDAO = DAOFactory.createDAO(CompeticaoDAO.class); 
		
		Competicao comp = compDAO.findById(db.getInt("comp_id"));
		cl.setCompeticao(comp);
		
		return cl;
	
}

}
