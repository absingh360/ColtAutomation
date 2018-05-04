package com.util;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelDataBaseConnector {

	static String connectionUrl = "";
	static Connection connection;
	static String resultValue = "";
	static Recordset rs;

	// Should be defined as jdbc:mysql://host:port/database name
	private static String databaseURLQA = "/src/test/resources/TestData/";
	private static String databaseURLSTAGE = "jdbc:mysql://stagehost:2020/easyDB";
	private static String databaseURLPRODUCTION = "jdbc:mysql://prodhost:2020/easyDB";

	public static Connection createConnection(String excel) {
		Fillo fillo = new Fillo();
		try {
			System.out.println(Utilities.getPath() + databaseURLQA + excel + ".xlsx");
			   connection = fillo.getConnection(Utilities.getPath()+databaseURLQA + excel + ".xlsx");
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (connection != null) {
			System.out.println("Connected to the database...");
		} else {
			System.out.println("Database connection failed to connect with " + excel + "");
		}
		return connection;
	}

	public static void CloseTheConnection(Connection connection) throws FilloException {

		// Code to close each and all Object related to Database connection
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	public static String executeSQLQuery(Connection excelDb, DataModelCPQ model, String column) {

		String data = null;
		  System.out.println(model.getZone());
		  String strQuery = "SELECT  * FROM Sheet1 WHERE Country = '" + model.getCountry() + "' AND Coverage='"
		    + model.getCoverage() + "' " + "AND Bandwidth='" + model.getBandWidth() + "' AND Resiliency ='"
		    + model.getResiliency() + "'" + " AND Pricing_Segment ='" + model.getSegment() +"' AND Zone ='"+ model.getZone()+"'"+ " AND Building_Type='" 
		    + model.getBuilding_Type() + "'";
		
		
		System.out.println(strQuery);

		try {
			rs = excelDb.executeQuery(strQuery);
			while (rs.next()) {
				data = rs.getField(column);
			}
		} catch (FilloException e) {
			e.printStackTrace();
		} catch (NullPointerException err) {
			System.out.println("No Records obtained for this specific query");
			err.printStackTrace();
		}
		return data;
	}

	public static String executeSQLQuery(Connection excelDb, String query, String column) {

		String data = null;

		try {
			rs = excelDb.executeQuery(query);
			while (rs.next()) {
				data = rs.getField(column);
			}
		} catch (FilloException e) {
			e.printStackTrace();
		} catch (NullPointerException err) {
			System.out.println("No Records obtained for this specific query");
			err.printStackTrace();
		}
		return data;
	}

	public static String getSQLQuery(String type, DataModelCPQ model) {
		String strQuery = null;

		if (type.equals("Hub")) {
			strQuery = "SELECT  * FROM Sheet1 WHERE Country = '" + model.getCountry()
					+ "' AND Connection_Type='Hub' AND Pricing_Type='On-Net' AND Bandwidth='" + model.getBandWidth()
					+ "' AND Resiliency ='" + model.getResiliency() + "'" + "AND Pricing_Segment ='"
					+ model.getSegment() + "' AND Building_Type='" + model.getSiteABuildingType() + "'";
		}

		return strQuery;

	}

}
