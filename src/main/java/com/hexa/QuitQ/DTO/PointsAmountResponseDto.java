package com.hexa.QuitQ.DTO;


public class PointsAmountResponseDto {

	private Double amountToBePaid;
	private Double spentPoints;
	private Double receivedPoints;
	public PointsAmountResponseDto(Double amountToBePaid, Double spentPoints, Double receivedPoints) {
		super();
		this.amountToBePaid = amountToBePaid;
		this.spentPoints = spentPoints;
		this.receivedPoints = receivedPoints;
	}
	public PointsAmountResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Double getAmountToBePaid() {
		return amountToBePaid;
	}
	public void setAmountToBePaid(Double amountToBePaid) {
		this.amountToBePaid = amountToBePaid;
	}
	public Double getSpentPoints() {
		return spentPoints;
	}
	public void setSpentPoints(Double spentPoints) {
		this.spentPoints = spentPoints;
	}
	public Double getReceivedPoints() {
		return receivedPoints;
	}
	public void setReceivedPoints(Double receivedPoints) {
		this.receivedPoints = receivedPoints;
	}

}