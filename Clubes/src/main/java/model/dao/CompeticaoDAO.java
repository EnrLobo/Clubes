package model.dao;

import java.util.List;

import model.Competicao;
import model.ModelException;

public interface CompeticaoDAO {

	boolean save(Competicao comp) throws ModelException ;
	boolean update(Competicao comp) throws ModelException;
	boolean delete(Competicao comp) throws ModelException;
	List<Competicao> listAll() throws ModelException;
	Competicao findById(int id) throws ModelException;
	
}
