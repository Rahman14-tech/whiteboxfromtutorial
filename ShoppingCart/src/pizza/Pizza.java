package pizza;

public class Pizza {
	private Size size;
	private Sauce sauce;
	private Topping[] toppings;
	private int toppingsAmount;
	
	public Pizza(Size size, Sauce sauce, Topping...toppings){
		this.setSize(size);
		this.setSauce(sauce);
		this.toppings = new Topping[Topping.values().length];
		this.toppingsAmount = 0;
		
		for (int i = 0; i < toppings.length; i++) {
			addTopping(toppings[i]);
		}
	}
	
	public void addTopping(Topping topping) {
		if (!hasTopping(topping)) {
			toppings[toppingsAmount++] = topping;
		}
	}
	
	public boolean hasTopping(Topping topping) {
		for (int i = 0; i < toppingsAmount; i++) {
			if (toppings[i] == topping) {
				return true;
			}
		}
		return false;
	}
	
	public double price() {
		double totalPrice = size.price() + sauce.price();
		
		for (int i = 0; i < toppingsAmount; i++) {
			totalPrice += toppings[i].price();
		}
		return totalPrice;
	}
	
	
	public boolean equals(Pizza other) {
		if (size != other.size || sauce != other.sauce || toppingsAmount != other.toppingsAmount) {
			return false;
		}
		
		for (int i = 0; i < toppingsAmount; i++) {
			if (!other.hasTopping(toppings[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Sauce getSauce() {
		return sauce;
	}

	public void setSauce(Sauce sauce) {
		this.sauce = sauce;
	}

	public Topping getTopping(int index) {
		if (index < 0 || index >= toppingsAmount) {
			return null;
		}
		return toppings[index];
	}
	
	public int getToppingsAmount() {
		return toppingsAmount;
	}
}
