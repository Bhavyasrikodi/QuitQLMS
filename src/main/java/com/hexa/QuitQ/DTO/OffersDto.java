package com.hexa.QuitQ.DTO;

import java.util.UUID;

public class OffersDto {

	private UUID offerId;
	private UUID tierId;
	private UUID programId;
	private String offerTitle;
	private String offerDescription;
	private String imageUrl;
	private int benefit;
	private boolean status;

	public OffersDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OffersDto(UUID offerId, UUID tierId, UUID programId, String offerTitle, String offerDescription,
			String imageUrl, int benefit, boolean status) {
		super();
		this.offerId = offerId;
		this.tierId = tierId;
		this.programId = programId;
		this.offerTitle = offerTitle;
		this.offerDescription = offerDescription;
		this.imageUrl = imageUrl;
		this.benefit = benefit;
		this.status = status;
	}

	public UUID getOfferId() {
		return offerId;
	}

	public void setOfferId(UUID offerId) {
		this.offerId = offerId;
	}

	public UUID getTierId() {
		return tierId;
	}

	public void setTierId(UUID tierId) {
		this.tierId = tierId;
	}

	public String getOfferTitle() {
		return offerTitle;
	}

	public void setOfferTitle(String offerTitle) {
		this.offerTitle = offerTitle;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getBenefit() {
		return benefit;
	}

	public void setBenefit(int benefit) {
		this.benefit = benefit;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public UUID getProgramId() {
		return programId;
	}

	public void setProgramId(UUID programId) {
		this.programId = programId;
	}

	@Override
	public String toString() {
		return "OffersDto [offerId=" + offerId + ", tierId=" + tierId + ", programId=" + programId + ", offerTitle="
				+ offerTitle + ", offerDescription=" + offerDescription + ", imageUrl=" + imageUrl + ", benefit="
				+ benefit + ", status=" + status + "]";
	}

}
