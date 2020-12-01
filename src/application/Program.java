package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbExceptions;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false);// toda transa��o estar� sujeita a confirma��o
			
			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			/*int x =  1;
			if (x < 2) {
				throw new SQLException("Fake Error!");
			}*/
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			conn.commit();//executando a confima��o da transa��o
			
			System.out.println("Rows 1: "+rows1);
			System.out.println("Rows 2: "+rows2);
		}
		catch(SQLException e) {
			try {
				conn.rollback();
				throw new DbExceptions("Transaction rolled back! Caused By: "+e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DbExceptions("Error trying to rollback! Caused by: "+ e1.getMessage() ); 
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

}
