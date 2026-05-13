package pizza;

public enum Topping {
	Anchovies(1.50),
	Bacon(1.80),
	Bellpepper(1.00),
	Chicken(2.50),
	Ham(1.50),
	Mushrooms(1.20),
	Olives(1.00),
	Pineapple(1.50), 
	Salami(3.00);
	
	private double price;
	
	private Topping(double price) {
		this.price = price;
	}
	
	public double price() {
		return price;
	}
}
