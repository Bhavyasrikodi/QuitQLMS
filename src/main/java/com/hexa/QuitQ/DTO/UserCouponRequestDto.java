package com.hexa.QuitQ.DTO;
import java.util.UUID;
public class UserCouponRequestDto {





	private String couponCode;
    private Double amount;
    private UUID uId;


    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public UUID getuId() {
        return uId;
    }

    public void setuId(UUID uId) {
        this.uId = uId;
    }

}
