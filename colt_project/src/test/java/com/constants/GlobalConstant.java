package com.constants;


public class GlobalConstant {
	
	public enum FileNames {
		TestDataRelativePath("src/test/resources/testdata/"),
		C4C_TEST_DATA("C4C_Data"),
		CONFIG("application"),
		CPQ_TEST_DATA("CPQ_Data"),
		CSV_SHEET("Deal Pricing Upload");
		
		
		private String value;
		private FileNames(String value) {
			this.value = value;
		}
		
		public String toString() {
			return value;
		}
	}
	
	public final static String BUILDING_RETAIL = "Retail Building";
	public final static String BUILDING_KEY = "DC - Key";
	public final static String BUILDING_STANDARD = "DC- Standard";
	public final static String saveFileEXE = "C:\\Users\\abhishekbs\\Desktop\\newtest.exe";
	public final static String DEAL_PRICE_UPLOAD_SHEET = System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\DealPricingCSVupload.csv";
	public final static String DEAL_PRICE_UPLOAD_SHEET1 = System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\New_folder\\DealPricingCSVuploadNew.csv";
	//public final static String DEAL_PRICE_UPLOAD_SHEET = "C://Users//abhishekbs//eclipse-workspace//colt_project_swapnil//src//test//resources//TestData//DealPricingUpload.csv";
	public final static String proposalDocument = "D:\\ColtRepo\\ColtAutomation\\colt_project\\src\\test\\resources\\TestData\\QT-20180312-014836-01.pdf";
	
	

}
