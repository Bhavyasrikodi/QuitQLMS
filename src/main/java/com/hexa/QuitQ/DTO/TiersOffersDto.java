package com.hexa.QuitQ.DTO;

import java.util.UUID;

public class TiersOffersDto {
	
	private String currentTier;
	private String nextTier;
	private Double totalPoints;
	private Double leftPointsToReachNextTier;
    private UUID tierId;
    private String colour;
	
	public TiersOffersDto(String currentTier, String nextTier, Double totalPoints, Double leftPointsToReachNextTier, UUID tierId, String colour) {
		super();
		this.currentTier = currentTier;
		this.nextTier = nextTier;
		this.totalPoints = totalPoints;
		this.leftPointsToReachNextTier = leftPointsToReachNextTier;
        this.tierId = tierId;
        this.colour = colour;
	}
	public TiersOffersDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCurrentTier() {
		return currentTier;
	}
	public void setCurrentTier(String currentTier) {
		this.currentTier = currentTier;
	}
	public String getNextTier() {
		return nextTier;
	}
	public void setNextTier(String nextTier) {
		this.nextTier = nextTier;
	}
	public Double getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Double totalPoints) {
		this.totalPoints = totalPoints;
	}
	public Double getLeftPointsToReachNextTier() {
		return leftPointsToReachNextTier;
	}
	public void setLeftPointsToReachNextTier(Double leftPointsToReachNextTier) {
		this.leftPointsToReachNextTier = leftPointsToReachNextTier;
	}

    public UUID getTierId() {
        return tierId;
    }

    public void setTierId(UUID tierId) {
        this.tierId = tierId;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
	
	

}
