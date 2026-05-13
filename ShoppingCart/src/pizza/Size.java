package pizza;

public enum Size {
	Small(3.00),
	Medium(4.00),
	Large(5.00);
	
	private double price;
	
	private Size(double price) {
		this.price = price;
	}
	
	public double price() {
		return price;
	}
}
