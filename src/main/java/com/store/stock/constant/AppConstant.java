package com.store.stock.constant;

public class AppConstant {

	private AppConstant() {

	}

	public static final String LOGIN_TYPE = "CreditCard";

	public static final Integer LOGIN_SUCCESS_CODE = 200;

	public static final String LOGIN_SUCCESS_MESSAGE = "Login Successful";

	public static final String INALID_CREDENTIALS = "Invalid email or password";

	public static final String PRODUCT_NOT_FOUND = "Product Not Found";
	public static final String ORDER_NOT_FOUND = "Order Not Placed";
	public static final String USER_NOT_FOUND = "User Not Found";
	
	// Order
	public static final String OTP_WRONG_VALUE = "Entered Wrong Otp.";
	
	// Buy Product
	public static final String BUY_SUCCESS_OTP_SEND = "OTP sent to your email address.";
	public static final String ORDER_SUCCESS_MESSAGE = "Ordered Successfully.";
	public static final String CREDIT_NUMBER_REQUIRED = "Card Number is Mandatory.";
	public static final String HOLDER_NAME_REQUIRED = "Holder Name is Mandatory.";
	public static final String CVV_REQUIRED = "Cvv is Mandatory.";

}
