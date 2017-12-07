package GenerateData;

import java.text.DateFormatSymbols;

public class Utilities {
	/* Trade Flow */
	protected static final String[] TRADE_FLOW = { "IMPORT", "EXPORT" };

	/* Month */
	protected static final String[] MONTHS = new DateFormatSymbols().getMonths();

	/* YEAR */
	protected static final int MINIMUM_YEAR = 2014;
	protected static final int MAXIMUM_YEAR = 2016;

	/* HS Code */
	protected static final int NUMBER_OF_DIGITS_FOR_HS_CODE = 10;

	/* Product Name */
	protected static final String[] PRODUCTS = { "WOOD", "IRON ROD", "PLASTIC", "METAL" };

	/* Total Weight */
	protected static final int MINIMUM_WEIGHT = 100;
	protected static final int MAXIMUM_WEIGHT = 10000;

	/* Weight Units */
	protected static final String[] WEIGHT_UNITS = { "POUNDS", "KG" };

	/* Price */
	protected static final int MINIMUM_PRICE = 100;
	protected static final int MAXIMUM_PRICE = 10000;

	/* Quantity */
	protected static final int MINIMUM_QUANTITY = 1;
	protected static final int MAXIMUM_QUANTITY = 10;
}