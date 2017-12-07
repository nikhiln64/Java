package GenerateData;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateCsvData {
	// Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	// CSV file header
	private static final String FILE_HEADER = "TRADE_FLOW,MONTH,YEAR,HS_CODE,PRODUCT_NAME,TOTAL_WEIGHT,WEIGHT_UNIT,PRICE_PER_UNIT($),QUANTITY";

	public static void writeCsvFile(String fileName, int numberOfRecords) {
		GenerateData generateData = new GenerateData();
		List<TradeDataVO> tradeDataVOList = new ArrayList<TradeDataVO>();
		for (int i = 0; i < numberOfRecords; i++) {
			TradeDataVO tradeDataVO = new TradeDataVO();
			tradeDataVO.setTradeFlow(generateData.generateTradeFlow());
			tradeDataVO.setMonth(generateData.generateMonth());
			tradeDataVO.setYear(generateData.generateYear());
			tradeDataVO.setHsCode(generateData.generateHSCode());
			tradeDataVO.setProductName(generateData.generateProductName());
			tradeDataVO.setTotalWeight(generateData.generateTotalWeight());
			tradeDataVO.setWeightUnits(generateData.generateWeightUnits());
			tradeDataVO.setPrice(new BigDecimal(generateData.generatePrice()));
			tradeDataVO.setQuantity(generateData.generateQuantity());
			tradeDataVOList.add(tradeDataVO);
		}

		FileWriter fileWriter = null;

		try {
			System.out.println("**** writeCsvFile started ****");
			fileWriter = new FileWriter(fileName);
			SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
			fileWriter.append("HDR," +dt1.format(new Date()));

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);

			for (TradeDataVO tradeDataVO : tradeDataVOList) {
				fileWriter.append(tradeDataVO.getTradeFlow());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(tradeDataVO.getMonth()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(tradeDataVO.getYear()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(tradeDataVO.getHsCode()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(tradeDataVO.getProductName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(tradeDataVO.getTotalWeight()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(tradeDataVO.getWeightUnits());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(tradeDataVO.getPrice()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(tradeDataVO.getQuantity()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			fileWriter.append("Triler_Count," + tradeDataVOList.size());
			fileWriter.append(NEW_LINE_SEPARATOR);
			System.out.println("CSV file was created successfully !!!");
		} catch (Exception ex) {
			System.out.println("writeCsvFile failed => " + ex);
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}

	}

}
