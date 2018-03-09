package com.onlineMIS.unit_testing;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class testSQLServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; 
		String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=QXBB2015"; 
		String userName = "program"; 
		String userPwd = "vj7683c6"; 
		Connection dbConn ;

		try { 
		    Class.forName(driverName); 
		    dbConn = DriverManager.getConnection(dbURL, userName, userPwd); 
		    System.out.println("Connection Successful!"); 
		
		    PreparedStatement productStatement = dbConn.prepareStatement("select unit1_id, Product_ID, convert(bigint,ModifyDate) date  from dbo.products");
			Map<Integer, Integer> product_unit = new HashMap<Integer, Integer>();
			
		    ResultSet   rs1   =   productStatement.executeQuery();
		    while (rs1.next()){
		    	int unit1_id = rs1.getInt("unit1_id");
		    	int productID = rs1.getInt("Product_ID");
		    	long date = rs1.getLong("date");
		    	System.out.println(date);
		    	if (product_unit.get(productID) != null)
		    		System.out.println("---" + productID);
		    	product_unit.put(productID, unit1_id);
		    }
		    
//		    PreparedStatement pStatement = dbConn.prepareStatement("select * from dbo.salepricehis where ModifyDate >= '2012/5/7'");
//		
//		    Statement statement = dbConn.createStatement();
//			
//		    
//		    ResultSet   rs   =   pStatement.executeQuery();
//
//		    while (rs.next()){
//		    	int customerID = rs.getInt("c_id");
//		    	int productID = rs.getInt("p_id");
//		    	int unitID = rs.getInt("unit_id");
//                try{
//		    	   int unitID_map = product_unit.get(productID);
// 
//			    	if (unitID != unitID_map){
//			    		System.out.println("----");
//			    		String sql = "update dbo.salepricehis set unit_id = " + unitID_map + " where c_id = " + customerID + " and p_id = " + productID + " and unit_id = " + unitID;
//			    		boolean success = statement.execute(sql);
//			    	}
//                } catch (Exception e) {
// 					System.out.println(productID);
// 				}
//		    }
		} 
			catch (Exception e) { 
			e.printStackTrace(); 
		} 



	}

}
