package com.hexa.QuitQ.DTO;

import java.util.UUID;

public class UserValidationDto {
    
    private String couponCode;
    private UUID uId;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public UUID getuId() {
        return uId;
    }

    public void setuId(UUID uId) {
        this.uId = uId;
    }
}
