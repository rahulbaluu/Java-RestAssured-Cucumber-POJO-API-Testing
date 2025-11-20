package pojo;

import java.util.List;

public class BrandResponse {
	private int responseCode;
	private List<Brand> brands;

	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public List<Brand> getBrands() {
		return brands;
	}
	public void setBrand(List<Brand> brand) {
		this.brands = brand;
	}
}
