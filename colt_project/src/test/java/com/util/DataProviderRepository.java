package com.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;

public class DataProviderRepository {

	@DataProvider(name = "getData")
	public static Object[][] setData() throws IOException, IllegalArgumentException, IllegalAccessException {
		// Rows - Number of times your test has to be repeated.
		// Columns - Number of parameters in test data.
		ExcelReader ex = new ExcelReader("TestData_WithContractTerm.xlsx", "sheet2");
		Object[][] data = new Object[ex.getRowCount()][ex.getColCount()];
		for (int i = 0; i < ex.getRowCount(); i++) {
			for (int j = 0; j < ex.getColCount(); j++) {
				data[i][j] = ex.readExcel(i + 1, j);
				System.out.println(i + " " + j);
			}
		}
		return data;
	}

	@DataProvider(name = "testDataProvider")
	public static Iterator<Object[]> ethernetDataProvider() throws IOException {

		Collection<Object[]> modelData = new ArrayList<Object[]>() {
			{
				ExcelReader ex = new ExcelReader("TestData_WithContractTerm.xlsx", "Ethernet");
				for (int i = 0; i < ex.getRowCount(); i++) {
					DataModelCPQ cpqModel = new DataModelCPQ();
					cpqModel.setQuoteName(ex.readExcel(i + 1, 0));
					cpqModel.setCustomer(ex.readExcel(i + 1, 1));
					cpqModel.setCountry(ex.readExcel(i + 1, 2));
					cpqModel.setSegment(ex.readExcel(i + 1, 3));
					cpqModel.setContract_Term(ex.readExcel(i + 1, 4));
					cpqModel.setCoverage(ex.readExcel(i + 1, 5));
					cpqModel.setBuilding_Type(ex.readExcel(i + 1, 6));
					cpqModel.setSite_A_Add(ex.readExcel(i + 1, 7));
					cpqModel.setSite_B_Add(ex.readExcel(i + 1, 8));
					cpqModel.setLongLining_A(ex.readExcel(i + 1, 9));
					cpqModel.setIc_Site_A(ex.readExcel(i + 1, 10));
					cpqModel.setDual_Entry_Site_A(ex.readExcel(i + 1, 11));
					cpqModel.setOutsideBHI_Site_A(ex.readExcel(i + 1, 12));
					cpqModel.setLongLining_B(ex.readExcel(i + 1, 13));
					cpqModel.setIc_Site_B(ex.readExcel(i + 1, 14));
					cpqModel.setDual_Entry_Site_B(ex.readExcel(i + 1, 15));
					cpqModel.setOutsideBHI_Site_B(ex.readExcel(i + 1, 16));
					cpqModel.setSync(ex.readExcel(i + 1, 17));
					cpqModel.setResiliency(ex.readExcel(i + 1, 18));
					cpqModel.setBandWidth(ex.readExcel(i + 1, 19));
					cpqModel.setLag_Site_A(ex.readExcel(i + 1, 20));
					cpqModel.setDiversity(ex.readExcel(i + 1, 21));
					cpqModel.setPam(ex.readExcel(i + 1, 22));
					cpqModel.setPr(ex.readExcel(i + 1, 23));
					cpqModel.setFastTrack(ex.readExcel(i + 1, 24));
					cpqModel.setCos(ex.readExcel(i + 1, 25));

					add(new Object[] { cpqModel });

				}
			}

		};
		return modelData.iterator();
	}

	@DataProvider(name = "spokeData")
	public static Iterator<Object[]> spokeDataProvider() throws IOException {

		Collection<Object[]> modelData = new ArrayList<Object[]>() {
			{
				ExcelReader ex = new ExcelReader("TestData_WithContractTermHub.xlsx", "sheet1");
				for (int i = 0; i < ex.getRowCount(); i++) {
					DataModelCPQ cpqModel = new DataModelCPQ();
					cpqModel.setQuoteName(ex.readExcel(i + 1, 0));
					cpqModel.setCustomer(ex.readExcel(i + 1, 1));
					cpqModel.setCountry(ex.readExcel(i + 1, 2));
					cpqModel.setSegment(ex.readExcel(i + 1, 3));
					cpqModel.setContract_Term(ex.readExcel(i + 1, 4));
					cpqModel.setCoverage(ex.readExcel(i + 1, 5));
					cpqModel.setBuilding_Type(ex.readExcel(i + 1, 6));
					cpqModel.setSite_A_Add(ex.readExcel(i + 1, 7));
					cpqModel.setSite_B_Add(ex.readExcel(i + 1, 8));
					cpqModel.setLongLining_A(ex.readExcel(i + 1, 9));
					cpqModel.setIc_Site_A(ex.readExcel(i + 1, 10));
					cpqModel.setDual_Entry_Site_A(ex.readExcel(i + 1, 11));
					cpqModel.setOutsideBHI_Site_A(ex.readExcel(i + 1, 12));
					cpqModel.setLongLining_B(ex.readExcel(i + 1, 13));
					cpqModel.setIc_Site_B(ex.readExcel(i + 1, 14));
					cpqModel.setDual_Entry_Site_B(ex.readExcel(i + 1, 15));
					cpqModel.setOutsideBHI_Site_B(ex.readExcel(i + 1, 16));
					cpqModel.setSync(ex.readExcel(i + 1, 17));
					cpqModel.setResiliency(ex.readExcel(i + 1, 18));
					cpqModel.setBandWidth(ex.readExcel(i + 1, 19));
					cpqModel.setLag_Site_A(ex.readExcel(i + 1, 20));
					cpqModel.setDiversity(ex.readExcel(i + 1, 21));
					cpqModel.setPam(ex.readExcel(i + 1, 22));
					cpqModel.setPr(ex.readExcel(i + 1, 23));
					cpqModel.setFastTrack(ex.readExcel(i + 1, 24));
					cpqModel.setCos(ex.readExcel(i + 1, 25));
					cpqModel.setSpokeBandwidth(ex.readExcel(i + 1, 26));

					add(new Object[] { cpqModel });

				}
			}

		};
		return modelData.iterator();
	}

	/*@DataProvider(name = "javascriptInjection")
	public static Iterator<Object[]> javascriptDataProvider() throws IOException {

		Collection<Object[]> modelData = new ArrayList<Object[]>() {
			{
				ExcelReader ex = new ExcelReader("JavascriptData.xlsx", "Sheet6");
				for (int i = 0; i < ex.getRowCount(); i++) {
					DataModelCPQ cpqModel = new DataModelCPQ();
					cpqModel.setCountry(ex.readExcel(i + 1, 0));
					cpqModel.setCityName(ex.readExcel(i + 1, 1));
					cpqModel.setPricingCity(ex.readExcel(i + 1, 2));
					cpqModel.setPostCode(ex.readExcel(i + 1, 3));
					cpqModel.setStreetName(ex.readExcel(i + 1, 4));
					cpqModel.setBuildingNumber(ex.readExcel(i + 1, 5));
					cpqModel.setMasterId(ex.readExcel(i + 1, 6));
					cpqModel.setBuilding_Type(ex.readExcel(i + 1, 7));
					cpqModel.setCountry2(ex.readExcel(i + 1, 8));
					cpqModel.setCityName2(ex.readExcel(i + 1, 9));
					cpqModel.setPricingCity2(ex.readExcel(i + 1, 10));
					cpqModel.setPostCode2(ex.readExcel(i + 1, 11));
					cpqModel.setStreetName2(ex.readExcel(i + 1, 12));
					cpqModel.setBuildingNumber2(ex.readExcel(i + 1, 13));
					cpqModel.setMasterId2(ex.readExcel(i + 1, 14));
					cpqModel.setBuilding_Type2(ex.readExcel(i + 1, 15));
					cpqModel.setSite_A_Add(ex.readExcel(i + 1, 16));
					cpqModel.setSite_B_Add(ex.readExcel(i + 1, 17));
					cpqModel.setBandWidth(ex.readExcel(i + 1, 18));
					cpqModel.setSegment(ex.readExcel(i + 1, 19));
					cpqModel.setResiliency(ex.readExcel(i + 1, 20));
					cpqModel.setCurrency(ex.readExcel(i + 1, 21));
					cpqModel.setContract_Term(ex.readExcel(i + 1, 22));
					cpqModel.setCoverage(ex.readExcel(i + 1, 23));
					cpqModel.setZone(ex.readExcel(i + 1, 24));
					add(new Object[] { cpqModel });

				}
			}

		};
		return modelData.iterator();
	}*/
	
	
	@DataProvider(name = "javascriptInjection")
	public static Iterator<Object[]> javascriptDataProvider() throws IOException {

		Collection<Object[]> modelData = new ArrayList<Object[]>() {
			{
				ExcelReader ex = new ExcelReader("JavascriptData.xlsx", "Sheet6");
				for (int i = 0; i < ex.getRowCount(); i++) {
					DataModelCPQ cpqModel = new DataModelCPQ();
					cpqModel.setCountry(ex.readExcel(i + 1, 0));
					cpqModel.setCityName(ex.readExcel(i + 1, 1));
					cpqModel.setPricingCity(ex.readExcel(i + 1, 2));
					cpqModel.setPostCode(ex.readExcel(i + 1, 3));
					cpqModel.setStreetName(ex.readExcel(i + 1, 4));
					cpqModel.setBuildingNumber(ex.readExcel(i + 1, 5));
					cpqModel.setMasterId(ex.readExcel(i + 1, 6));
					cpqModel.setBuilding_Type(ex.readExcel(i + 1, 7));
					cpqModel.setCountry2(ex.readExcel(i + 1, 8));
					cpqModel.setCityName2(ex.readExcel(i + 1, 9));
					cpqModel.setPricingCity2(ex.readExcel(i + 1, 10));
					cpqModel.setPostCode2(ex.readExcel(i + 1, 11));
					cpqModel.setStreetName2(ex.readExcel(i + 1, 12));
					cpqModel.setBuildingNumber2(ex.readExcel(i + 1, 13));
					cpqModel.setMasterId2(ex.readExcel(i + 1, 14));
					cpqModel.setBuilding_Type2(ex.readExcel(i + 1, 15));
					cpqModel.setSite_A_Add(ex.readExcel(i + 1, 16));
					cpqModel.setSite_B_Add(ex.readExcel(i + 1, 17));
					cpqModel.setBandWidth(ex.readExcel(i + 1, 18));
					cpqModel.setSegment(ex.readExcel(i + 1, 19));
					cpqModel.setResiliency(ex.readExcel(i + 1, 20));
					cpqModel.setCurrency(ex.readExcel(i + 1, 21));
					cpqModel.setContract_Term(ex.readExcel(i + 1, 22));
					cpqModel.setCoverage(ex.readExcel(i + 1, 23));
					cpqModel.setZone(ex.readExcel(i + 1, 24));
					cpqModel.setInterfaceValue(ex.readExcel(i + 1, 25));
					add(new Object[] { cpqModel });

				}
			}

		};
		return modelData.iterator();
	}
	
	 @DataProvider(name = "BulkHubPrices")
	 public static Iterator<Object[]> HubPricesBulk() throws IOException {
	  
	  Collection<Object[]> modelData = new ArrayList<Object[]>() {
	   {
	    ExcelReader ex = new ExcelReader("JavascriptData.xlsx", "Hub");
	    for (int i = 0; i < ex.getRowCount(); i++) {
	     DataModelCPQ cpqModel = new DataModelCPQ();
	     cpqModel.setCountry(ex.readExcel(i + 1, 0));
	     cpqModel.setCityName(ex.readExcel(i + 1, 1));
	     cpqModel.setPricingCity(ex.readExcel(i + 1, 2));
	     cpqModel.setPostCode(ex.readExcel(i + 1, 3));
	     cpqModel.setStreetName(ex.readExcel(i + 1, 4));
	     cpqModel.setBuildingNumber(ex.readExcel(i + 1, 5));
	     cpqModel.setMasterId(ex.readExcel(i + 1, 6));
	     cpqModel.setBuilding_Type(ex.readExcel(i + 1, 7));
	     cpqModel.setSite_A_Add(ex.readExcel(i + 1, 8));
	     cpqModel.setBandWidth(ex.readExcel(i + 1, 9));
	     cpqModel.setSegment(ex.readExcel(i + 1, 10));
	     cpqModel.setResiliency(ex.readExcel(i + 1, 11));
	     cpqModel.setCurrency(ex.readExcel(i + 1, 12));
	     cpqModel.setContract_Term(ex.readExcel(i + 1, 13));
	     cpqModel.setCoverage(ex.readExcel(i + 1, 14));
	     cpqModel.setZone(ex.readExcel(i+1, 15));
	     add(new Object[] { cpqModel });    
	     

	    }
	   }

	  };
	  return modelData.iterator();
	 }

}
