package model.dao;

import java.util.List;

import model.ModelException;
import model.Clube;

public interface ClubeDAO {
	boolean save(Clube clube) throws ModelException; 
	boolean update(Clube clube) throws ModelException;
	boolean delete(Clube clube) throws ModelException;
	List<Clube> listAll() throws ModelException;
	Clube findById(int id) throws ModelException;
}
