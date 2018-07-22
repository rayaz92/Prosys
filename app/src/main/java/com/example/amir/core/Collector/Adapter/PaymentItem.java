package com.example.amir.core.Collector.Adapter;

public class PaymentItem {
	private int BankNo;
	private String DueDate;
	private String ChequeNo;
	private Double CheuqeAmount;
	private String TransType = "";

	public PaymentItem() {
		// TODO Auto-generated constructor stub
	}

	public PaymentItem(int BankNo, String DueDate, String ChequeNo,
			Double CheuqeAmount, String TransType) {
		this.BankNo = BankNo;
		this.DueDate = DueDate;
		this.ChequeNo = ChequeNo;
		this.CheuqeAmount = CheuqeAmount;
		this.TransType = TransType;

	}

	public int getBankNo() {
		return this.BankNo;
	}

	public String getDueDate() {
		return this.DueDate;
	}

	public String getChequeNo() {
		return this.ChequeNo;
	}

	public double getCheuqeAmount() {
		return this.CheuqeAmount;
	}

	public String getTransType() {
		return this.TransType;
	}
}
