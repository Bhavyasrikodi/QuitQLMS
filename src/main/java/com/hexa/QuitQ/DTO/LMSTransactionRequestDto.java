package com.hexa.QuitQ.DTO;

import java.util.UUID;


public class LMSTransactionRequestDto {

    private long userId;
	private UUID partnerId;
	private long paymentId;
	private UUID couponId;
	private double pointsGained;
	private double pointsSpent;
	private double amount;


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

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getCouponId() {
        return couponId;
    }

    public void setCouponId(UUID couponId) {
        this.couponId = couponId;
    }

    public double getPointsGained() {
        return pointsGained;
    }

    public void setPointsGained(double pointsGained) {
        this.pointsGained = pointsGained;
    }

    public double getPointsSpent() {
        return pointsSpent;
    }

    public void setPointsSpent(double pointsSpent) {
        this.pointsSpent = pointsSpent;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
