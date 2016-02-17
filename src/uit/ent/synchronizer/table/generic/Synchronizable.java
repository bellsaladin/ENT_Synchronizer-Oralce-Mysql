package uit.ent.synchronizer.table.generic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import uit.ent.synchronizer.LoggerFactory;
import uit.ent.synchronizer.Statics;

public class Synchronizable {
	
	protected static Connection _connection_mysql;
	protected static Connection _connection_oracle;

	
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
			getLogger().error("Error while closing '" + type + "' connection %n", e);
		}
	}
	
	private Connection getMysqlConnection(){
		try {
			if(_connection_mysql != null && !_connection_mysql.isClosed())
				return _connection_mysql;
			Class.forName(Statics.DB_MYSQL_DRIVER);
			_connection_mysql = DriverManager.getConnection(Statics.DB_MYSQL_URL, Statics.DB_MYSQL_USER, Statics.DB_MYSQL_PASSWORD);
			return _connection_mysql;
		}
		catch (Exception e) {
			getLogger().error("Error while connecting to mysql %n", e);
		}
		return null;
	}
	
	private Connection getOracleConnection() {
		
		try {
			if(_connection_oracle != null && !_connection_oracle.isClosed())
				return _connection_oracle;
			Class.forName(Statics.DB_ORACLE_DRIVER);
			_connection_oracle = DriverManager.getConnection(Statics.DB_ORACLE_CONNECTION, Statics.DB_ORACLE_USER,
					Statics.DB_ORACLE_PASSWORD);
			return _connection_oracle;
		} catch (Exception e) {
			getLogger().error("Error while connecting to oracle %n", e);
		}
		return null;
	}
	
	
}
