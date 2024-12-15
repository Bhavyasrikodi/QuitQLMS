package com.hexa.QuitQ.DTO;


import java.util.UUID;

public class PointsAmountRequestDto {
	private long userId;
	private UUID partnerId;
	private float amount;
	private boolean isPointsUsed;
	public PointsAmountRequestDto(long userId, UUID partnerId, float amount) {
		super();
		this.userId = userId;
		this.partnerId = partnerId;
		this.amount = amount;
	}
	public PointsAmountRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public UUID getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(UUID partnerId) {
		this.partnerId = partnerId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

    public boolean isIsPointsUsed() {
        return isPointsUsed;
    }

    public void setIsPointsUsed(boolean isPointsUsed) {
        this.isPointsUsed = isPointsUsed;
    }

	
	
}
