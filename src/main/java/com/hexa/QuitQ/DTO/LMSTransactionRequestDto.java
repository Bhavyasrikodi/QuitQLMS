package com.hexa.QuitQ.DTO;

public class LMSTransactionRequestDto {

    private long payment_id;
    private float amount;

    public long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(long payment_id) {
        this.payment_id = payment_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    
}
