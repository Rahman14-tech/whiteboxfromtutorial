package pizza;

public enum Sauce {
	Alfredo(1.00),
	BBQ(1.50),
	Honey(2.50),
	Pesto(0.80),
	Tomato(0.50);
	
	private double price;
	
	private Sauce(double price) {
		this.price = price;
	}
	
	public double price() {
		return price;
	}
}
