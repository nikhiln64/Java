package GenerateData;

public class WriteDataToCSV {
	public static void main(String[] args) {
		String fileName = System.getProperty("user.home")+"/Import_Export_Data.csv";
		System.out.println("Write CSV file:");
		CreateCsvData.writeCsvFile(fileName,20);
	}
}
