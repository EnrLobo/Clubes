package model.dao;
import java.util.HashMap;
import java.util.Map;

public class DAOFactory {
	
	private static Map<Class<?>, Object> listDAOsInterfaces = new HashMap<Class<?>, Object>();
	
	static {
		listDAOsInterfaces.put(ClubeDAO.class, new MySQLClubeDAO());
		listDAOsInterfaces.put(CompeticaoDAO.class, new MySQLCompeticaoDAO());
		listDAOsInterfaces.put(UserDAO.class, new MySQLUserDAO());
	}

	@SuppressWarnings("unchecked")
	public static <DAOInterface> DAOInterface createDAO(Class<?> entity){
		return (DAOInterface) listDAOsInterfaces.get(entity);
	} 
}
