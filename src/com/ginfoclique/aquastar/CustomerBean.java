package com.ginfoclique.aquastar;

public class CustomerBean {
	private int id;
	private String name;
	private String city;
	private String gender;
	private int product_count;
	private String phone_number;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getProductCount() {
		return product_count;
	}

	public void setProductCount(int age) {
		this.product_count = age;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phone_number = phoneNumber;
	}
}
