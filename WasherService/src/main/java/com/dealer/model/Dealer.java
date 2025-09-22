package com.dealer.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dealer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dealer_id;
	private String first_name;
	private String last_name;
	private String email;
	private String address;
	private String district;
	private String pincode;
	private String phone_no;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bank_details_id", referencedColumnName = "bank_details_id")
	private BankDetails bankDetails;


}
