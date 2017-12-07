package GenerateData;

import java.util.Arrays;
import java.util.Random;

public class GenerateData {

	/* Column 1 Random Generator */
	protected String generateTradeFlow() {
		String tradeFlow = null;
		try {
			System.out.println("**** generateTradeFlow Started ****");
			Random random = new Random();
			int index = random.nextInt(Utilities.TRADE_FLOW.length);
			tradeFlow = Utilities.TRADE_FLOW[index];
			return tradeFlow;
		} catch (Exception e) {
			System.out.println("generateTradeFlow Failed ==> " + e);
			return tradeFlow;
		}
	}

	/* Column 2 Random Generator */
	protected String generateMonth() {
		String month = null;
		try {
			System.out.println("**** generateMonth Started ****");
			Random random = new Random();
			int index = random.nextInt(Utilities.MONTHS.length - 1);
			month = Utilities.MONTHS[index];
			return month;
		} catch (Exception e) {
			System.out.println("generateMonth Failed ==> " + e);
			return null;
		}
	}

	/* Column 3 Random Generator */
	protected int generateYear() {
		int year;
		try {
			System.out.println("**** generateYear Started ****");
			Random random = new Random();
			year = random.nextInt(Utilities.MINIMUM_YEAR - Utilities.MINIMUM_YEAR + 1) + Utilities.MINIMUM_YEAR;
			return year;
		} catch (Exception e) {
			System.out.println("generateYear Failed ==> " + e);
			return 0;
		}
	}

	/* Column 4 Random Generator */
	protected long generateHSCode() {
		long hsCode;
		try {
			System.out.println("**** generateHSCode Started ****");
			hsCode = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
			return hsCode;
		} catch (Exception e) {
			System.out.println("generateHSCode Failed ==> " + e);
			return 0;
		}
	}

	/* Column 5 Random Generator */
	protected String generateProductName() {
		String productName = null;
		try {
			System.out.println("**** generateProductName Started ****");
			Random random = new Random();
			int index = random.nextInt(Utilities.PRODUCTS.length);
			productName = Utilities.PRODUCTS[index];
			return productName;
		} catch (Exception e) {
			System.out.println("generateProductName Failed ==> " + e);
			return productName;
		}
	}

	/* Column 6 Random Generator */
	protected int generateTotalWeight() {
		int totalWeight = 0;
		try {
			System.out.println("**** generateTotalWeight Started ****");
			Random random = new Random();
			totalWeight = random.nextInt(Utilities.MAXIMUM_WEIGHT - Utilities.MINIMUM_WEIGHT + 1)
					+ Utilities.MINIMUM_WEIGHT;
			return totalWeight;
		} catch (Exception e) {
			System.out.println("generateTotalWeight Failed ==> " + e);
			return 0;
		}
	}

	/* Column 7 Random Generator */
	protected String generateWeightUnits() {
		String weightUnit = null;
		try {
			System.out.println("**** generateWeightUnits Started ****");
			Random random = new Random();
			int index = random.nextInt(Utilities.WEIGHT_UNITS.length);
			weightUnit = Utilities.WEIGHT_UNITS[index];
			return weightUnit;
		} catch (Exception e) {
			System.out.println("generateWeightUnits Failed ==> " + e);
			return weightUnit;
		}
	}

	/* Column 8 Random Generator */
	protected int generatePrice() {
		int price = 0;
		try {
			System.out.println("**** generatePrice Started ****");
			Random random = new Random();
			price = random.nextInt(Utilities.MAXIMUM_PRICE - Utilities.MINIMUM_PRICE + 1) + Utilities.MINIMUM_PRICE;
			return price;
		} catch (Exception e) {
			System.out.println("generatePrice Failed ==> " + e);
			return 0;
		}
	}

	/* Column 9 Random Generator */
	protected int generateQuantity() {
		int quantity = 0;
		try {
			System.out.println("**** generateQuantity Started ****");
			Random random = new Random();
			quantity = random.nextInt(Utilities.MAXIMUM_QUANTITY - Utilities.MINIMUM_QUANTITY + 1)
					+ Utilities.MINIMUM_QUANTITY;
			return quantity;
		} catch (Exception e) {
			System.out.println("generateQuantity Failed ==> " + e);
			return 0;
		}
	}

}
