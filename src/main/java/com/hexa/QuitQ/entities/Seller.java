package com.hexa.QuitQ.entities;

import java.util.List;

import com.hexa.QuitQ.enums.ShippingMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Sellers")
public class Seller {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sellers_id;
    private String store_name;
    private Long account_number;
    private String IFSC_code;
    private String GST_number;
    
	@Enumerated(EnumType.STRING)
	private ShippingMode shippingMode;
	
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User users;

    @OneToMany(mappedBy = "sellers",cascade=CascadeType.REMOVE)
    private List<Product> products;

    
    //Costructors
    
	public Seller() {
		super();
	}

	public Seller(Long sellers_id, String store_name, Long account_number, String iFSC_code, String gST_number,ShippingMode shippingMode) {
		super();
		this.sellers_id = sellers_id;
		this.store_name = store_name;
		this.account_number = account_number;
		this.IFSC_code = iFSC_code;
		this.GST_number = gST_number;
		this.shippingMode = shippingMode;
	}

	public Long getSellers_id() {
		return sellers_id;
	}

	public void setSellers_id(Long sellers_id) {
		this.sellers_id = sellers_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public Long getAccount_number() {
		return account_number;
	}

	public void setAccount_number(Long account_number) {
		this.account_number = account_number;
	}

	public String getIFSC_code() {
		return IFSC_code;
	}

	public void setIFSC_code(String iFSC_code) {
		this.IFSC_code = iFSC_code;
	}

	public String getGST_number() {
		return GST_number;
	}

	public void setGST_number(String gST_number) {
		this.GST_number = gST_number;
	}

	public ShippingMode getShippingMode() {
		return shippingMode;
	}

	public void setShippingMode(ShippingMode shippingMode) {
		this.shippingMode = shippingMode;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

    
}
