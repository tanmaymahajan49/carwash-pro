package com.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DealerDto {

	private int dealer_id;
	private String first_name;
	private String last_name;
	private String email;
	private String address;
	private String district;
	private String pincode;
	private String phone_no;
	private BankDetailsDto bankDetails;

	
	

}
