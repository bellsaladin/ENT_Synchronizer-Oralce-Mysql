package uit.ent.synchronizer.table.generic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import uit.ent.synchronizer.LoggerFactory;
import uit.ent.synchronizer.Statics;

public class Synchronizable {
	
	protected Connection _connection_mysql;
	protected Connection _connection_oracle;

	
	public Synchronizable(){
		_connection_oracle = null;
		_connection_mysql = null;
		// initialize the logger
	}
	
	protected Logger getLogger(){
		return LoggerFactory.getLogger(this.getClass().getName());
	}
	
	
	public Connection getConnection(String type){
		if(type.toLowerCase().equals("oracle")){
			return getOracleConnection();
		}else{
			return getMysqlConnection();
		}
	}
	
	public void closeConnection(String type){
		try {
			if(type.toLowerCase().equals("oracle")){
				if(_connection_oracle != null)
					_connection_oracle.close();
			}else{
				if(_connection_mysql != null)
				_connection_mysql.close();
			}
		} catch (SQLException e) {
			System.err.println("Error closing '" + type + "' connection");
			System.err.println(e);
		}
	}
	
	private Connection getMysqlConnection(){
		if(_connection_mysql != null)
			return _connection_mysql;
		
		try {
			Class.forName(Statics.DB_MYSQL_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			_connection_mysql = DriverManager.getConnection(Statics.DB_MYSQL_URL, Statics.DB_MYSQL_USER, Statics.DB_MYSQL_PASSWORD);
			return _connection_mysql;
		} catch (SQLException e) {
			getLogger().error("Mysql Connection Error !", e);
			e.printStackTrace();
		}
		return null;
	}
	
	private Connection getOracleConnection() {
		if(_connection_oracle != null)
			return _connection_oracle;
		
		try {
			Class.forName(Statics.DB_ORACLE_DRIVER);
		} catch (ClassNotFoundException e) {
			getLogger().error(e.getMessage());
			e.printStackTrace();
		}
		try {
			_connection_oracle = DriverManager.getConnection(Statics.DB_ORACLE_CONNECTION, Statics.DB_ORACLE_USER,
					Statics.DB_ORACLE_PASSWORD);
			return _connection_oracle;
		} catch (SQLException e) {
			getLogger().error("Oracle Connection Error !", e);
			e.printStackTrace();
		}
		return null;
	}
	
	
}
