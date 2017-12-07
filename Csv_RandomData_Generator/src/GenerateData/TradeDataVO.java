package GenerateData;

import java.math.BigDecimal;

public class TradeDataVO {
	private String tradeFlow;
	private int month;
	private int year;
	private long hsCode;
	private String productName;
	private int totalWeight;
	private String weightUnits;
	private BigDecimal price;
	private int quantity;

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getHsCode() {
		return hsCode;
	}

	public void setHsCode(long hsCode) {
		this.hsCode = hsCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getWeightUnits() {
		return weightUnits;
	}

	public void setWeightUnits(String weightUnits) {
		this.weightUnits = weightUnits;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
