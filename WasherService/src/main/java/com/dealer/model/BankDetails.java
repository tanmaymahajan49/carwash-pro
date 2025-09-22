package com.dealer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BankDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bank_details_id;
	private String bankName;
	private String accountNumber;
	private String ifscCode;
	private String upiId;
	private String upiNumber;

	public int getBank_details_id() {
		return bank_details_id;
	}

	public void setBank_details_id(int bank_details_id) {
		this.bank_details_id = bank_details_id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public String getUpiNumber() {
		return upiNumber;
	}

	public void setUpiNumber(String upiNumber) {
		this.upiNumber = upiNumber;
	}

	public BankDetails(int bank_details_id, String bankName, String accountNumber, String ifscCode, String upiId,
			String upiNumber) {
		super();
		this.bank_details_id = bank_details_id;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
		this.upiId = upiId;
		this.upiNumber = upiNumber;
	}

	public BankDetails() {

	}

}
