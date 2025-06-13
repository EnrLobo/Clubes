package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Competicao;

public class MySQLCompeticaoDAO implements CompeticaoDAO{

	@Override
	public boolean save(Competicao comp) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO comps VALUES "
				+ " (DEFAULT, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, comp.getNome());
		db.setString(2, comp.getNomeClube());
		db.setString(3, comp.getCidade());
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Competicao comp) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE comps "
						 + " SET nome = ?,"
						 + " clubeRealizacao = ?,"
						 + " cidadeRealizacao = ?"
						 + " WHERE id = ?";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, comp.getNome());
		db.setString(2, comp.getNomeClube());
		db.setString(3, comp.getCidade());
		db.setInt(4, comp.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Competicao comp) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM comps "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, comp.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Competicao> listAll() throws ModelException {
	    DBHandler db = new DBHandler();
	    
	    List<Competicao> comps = new ArrayList<>();
	    
	    String sqlQuery = "SELECT * FROM comps ORDER BY nome;";
	    
	    db.createStatement();
	    db.executeQuery(sqlQuery);

	    while (db.next()) {
	        Competicao c = createComp(db);
	        comps.add(c);
	    }

	    return comps;
	}


	@Override
	public Competicao findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM comps WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Competicao c = null;
		while (db.next()) {
			c = createComp(db);
			break;
		}
		
		return c;
	}
	
	private Competicao createComp(DBHandler db) throws ModelException {
		Competicao c = new Competicao(db.getInt("id"));
		c.setNome(db.getString("nome"));
		c.setNomeClube(db.getString("clubeRealizacao"));
		c.setCidade(db.getString("cidadeRealizacao"));
		
		
		return c;
	
}
}

