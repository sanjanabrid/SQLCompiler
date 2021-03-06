package edu.stevens.cs562.queryprocessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class DatabaseOperations {

	private Connection conn;
	
	public DatabaseOperations(){
		this.conn = ConnectDatabase.getDBInstance();
	}
	
	/**
	 * function that executes SQL query and return ResultSet object
	 * @param query
	 * @return ResultSet
	 */
	public ResultSet executeSaleQuery(String query){
		ResultSet rs = null;
		Statement st=null;
		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(query);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Function to get the data type of column
	 * If the column is not found then it thorws an error.
	 * @param columnname
	 * @return
	 */
	public String getColumnDataType(String columnname){
		System.out.println("getColumnDataType, column name = "+columnname);
		Statement st = null;
		String result = null;
		try {
			st = conn.createStatement();
			
			String query =  "select data_type from information_schema.columns where table_name='sales' and column_name = '"+columnname+"'";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				result = rs.getString(1);
				System.out.println("getColumnDataType, result="+result);
				break;
			}
			rs.close();
			return result;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally{
			if(st!=null){
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
}
