package com.hexa.QuitQ.DTO;

import java.util.UUID;

public class LMSUserRequestDto {

    private long userId;
    private UUID partnerId;

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


    
}
