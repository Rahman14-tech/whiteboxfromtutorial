package ordering;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

import pizza.*;
import printing.Printer;


public class OrderingApp {
	public Printer out;
	public Scanner in;

	public ArrayList<Pizza> cart;
	public Coupon coupon;

	
	public OrderingApp(){
		out = new Printer();
		in = new Scanner(System.in);
		cart = new ArrayList<>();
		coupon = null;
	}
	
	public int readIntInRange(int min, int max) throws InputMismatchException{
		int result = in.nextInt();
		
		if (result < min || max < result) {
			throw new InputMismatchException("Error: int not in specified range");
		}
		
		return result;
	}

	public int getOptionInt(String question, Object ...options) {
		// list options
		out.println(question);
		out.printEnumeration(1, " %2d: %s\n", options);
		
		// get option from user
		out.printf("Your choice: ");
		int choice;
		do {
			try {
				choice = readIntInRange(1, options.length);
				break;
			} catch (java.util.InputMismatchException e) {
				out.printf("Invalid option, choose again: ");
				in.nextLine(); // clear scanner
			}
		} while (true);

		in.nextLine();
		return choice;
	}

	public void emptyCart(ArrayList<Pizza> cart) {
		while (cart.size() > 0) {
			cart.remove(0);
		}
		coupon = null;
	}

	public void checkout(ArrayList<Pizza> cart) {
		if (cart.size() == 0) {
			out.printf("\nYour cart is currenty empty and therefore can not be checked out.\n\n");
			return;
		}

		out.printf("\nChecking out your cart. ");
		out.printf("Please enter your adress, or type 'c' to cancel:");
		String adress = in.nextLine().strip();

		if (adress.equals("c")) {
			out.printf("\nCancelling checkout.\n\n");
			return;
		}

		out.printf("\nPlease make sure the following details are correct.\n");

		printCart(cart);
		out.printf("This will be delivered to %s.\n", adress);

		int option = getOptionInt("\nConfirm order?", "Yes", "Cancel");

		if (option == 2) {
			out.printf("\nCancelling checkout.\n\n");
		} else {
			out.printf("\nThank you very much for ordering by us. Please enjoy your pizzas!\n\n");
			emptyCart(cart);
		}
	}

	public void printCart(ArrayList<Pizza> cart) {
		out.printf("\nYour cart currently contains the following pizzas:\n");
		for (Pizza p : cart) {
			out.printf("- A %s pizza ($%.2f) with %s sauce ($%.2f) and toppings:\n", p.getSize().toString().toLowerCase(),
					p.getSize().price(),
					p.getSauce().toString().toLowerCase(),
					p.getSauce().price());

			if (p.getToppingsAmount() == 0) {
				out.printf("  * no toppings\n");
				continue;
			}
			for (int i = 0; i < p.getToppingsAmount(); i++) {
				Topping topping = p.getTopping(i);
				out.printf("  * %-10s\t+$%.2f\n", topping.toString().toLowerCase(), topping.price());
			}
		}

		if (coupon != null) {
			out.printf("- Coupon %s for %s:\n", coupon, coupon.description);
			out.printf("  * discount\t-$%.2f\n", calculateDiscount(coupon, cart));
		} else {
			out.printf("- No coupons added\n");
		}
		out.printf("The total cost is $%.2f\n", calculateTotal(cart));
		out.println();
	}

	public void view(ArrayList<Pizza> cart) {
		if (cart.size() == 0) {
			out.printf("\nYour cart is currenty empty.\n\n");
			return;
		}
		printCart(cart);
	}
	
	public double calculateTotal(ArrayList<Pizza> cart) {
		double total = 0;
		for (Pizza p : cart) {
			total += p.price();
		}
		return total - calculateDiscount(coupon, cart);
	}

	public boolean eligibleForCoupon(Coupon c, ArrayList<Pizza> cart) {	
		if (c == null) return false;
		
		boolean eligible = false;
		switch(c) {

		case SWT2026:
			double price = 0;
			for (Pizza p : cart) {
				price += p.price();
			}
			eligible = price >= 26;
			break;

		case MLM:
			int countMedium = 0;
			int countLarge = 0;

			for (Pizza p : cart) {
				if (p.getSize() == Size.Medium) countMedium++;
				if (p.getSize() == Size.Large) countLarge++;
			}

			eligible = countMedium >= 2 && countLarge >= 1;
			break;

		case MEATLOVER:
			eligible = true;
			break;
			
		case YUCK:
			int count = 0;
			Pizza yucky = new Pizza(Size.Large, Sauce.Honey, Topping.Anchovies, Topping.Pineapple);
			
			for (Pizza p : cart) {
				if (p.equals(yucky)) {
					count++;
				}
			}
			eligible = count >= 2;
			break;
		}
		
		return eligible;
	}


	public double calculateDiscount(Coupon c, ArrayList<Pizza> cart) {
		if (c == null) return 0;
		
		double discount = 0;
		double price = 0;
		switch(c) {

		case SWT2026:
			discount = 20.0;
			break;

		case MLM:
			price = 0;
			for (Pizza p : cart) {
				price += p.price();
			}

			discount =  price * 0.15;
			break;

		case MEATLOVER:
			price = 0;
			for (Pizza p : cart) {
				for (int i = 0; i < p.getToppingsAmount(); i++) {
					if (p.getTopping(i).equals(Topping.Anchovies) || 
						p.getTopping(i).equals(Topping.Bacon) || 
						p.getTopping(i).equals(Topping.Chicken) || 
						p.getTopping(i).equals(Topping.Ham) || 
						p.getTopping(i).equals(Topping.Salami)) {
							price += p.getTopping(i).price();
					}
				}
			}
			discount =  price / 2;
			break;
			
			
		case YUCK:
			Pizza p = new Pizza(Size.Large, Sauce.Honey);
			p.addTopping(Topping.Anchovies);
			p.addTopping(Topping.Pineapple);
			discount =  p.price();
			break;
		}
		
		return discount;
	}

	public void addCoupon(ArrayList<Pizza> cart) {
		out.printf("\nPlease enter your coupon, or type 'c' to cancel: ");

		do {
			String coupon = in.nextLine().strip();
			if (coupon.equals("c")) {
				out.printf("\nCancelling coupon.\n\n");
				return;
			}

			for (Coupon c : Coupon.values()) {
				if (c.toString().toLowerCase().equals(coupon.toLowerCase())) {
					if (eligibleForCoupon(c, cart)) {
						if (this.coupon == null) {
							out.printf("\nAdded coupon '%s' to your cart.\n", c);
						} else {
							out.printf("\nReplaced coupon '%s' with %s.\n", this.coupon, c);
						}
						this.coupon = c;

					} else {
						out.printf("\nYour cart does not meet the requirements for this coupon. Try again later.\n");
					}
					return;
				}
			}
			out.printf("\nThe coupon '%s' is not recognized. Try again, or type 'c' to cancel:", coupon);

		} while (true);		
	}

	public void removePizza(ArrayList<Pizza> cart) {
		// ask the user what to do
		if (cart.size() == 0) {
			out.printf("\nYour cart is currently empty.\n");
			return;
		}

		Object[] options = out.formatOptionsPizzas(cart, "Cancel");
		int option = getOptionInt("\nPlease select which pizza to remove:", options);

		// process the user input
		if (option == cart.size()+1) {
			out.printf("Cancelling removal.\n\n");
			return;
		}

		out.printf("\nRemoved pizza %d from your cart.\n", option);
		cart.remove(option-1);

		if (coupon != null && !eligibleForCoupon(coupon, cart)) {
			out.printf("You are now no longer eligible for coupon '%s'\n", coupon);
			coupon = null;
		}
	}

	public int askTopping() {
		Object[] options = out.formatOptionsEnum(Topping.values(), "Done", "Cancel");
		return getOptionInt("Please select if you want to add (more) toppings to your pizza:", options);
	}

	public int askSauce() {
		Object[] options = out.formatOptionsEnum(Sauce.values(), "Cancel");
		return getOptionInt("Please select the sauce you want:", options);
	}

	public int askSize() {		
		Object[] options = out.formatOptionsEnum(Size.values(), "Cancel");
		return getOptionInt("Please select the size you want:", options);
	}

	public void addPizza(ArrayList<Pizza> cart) {
		int sizeOption = askSize();
		if (sizeOption == Size.values().length + 1) {
			out.printf("\nCancelling new pizza.\n");
			return;
		}

		int sauceOption = askSauce();
		if (sauceOption == Sauce.values().length + 1) {
			out.printf("\nCancelling new pizza.\n");
			return;
		}

		Pizza pizza = new Pizza(Size.values()[sizeOption-1], Sauce.values()[sauceOption-1]);
		do {
			int toppingOption = askTopping();
			if (toppingOption == Topping.values().length + 1) {
				cart.add(pizza);
				out.printf("\nAdded pizza to your cart.\n");
				return;
			}
			if (toppingOption == Topping.values().length + 2) {
				out.printf("\nCancelling new pizza.\n");
				return;
			}

			Topping topping = Topping.values()[toppingOption-1];
			if (pizza.hasTopping(topping)) {
				out.printf("\nYour pizza already has %s on it.\n", topping.toString().toLowerCase());
			} else {
				pizza.addTopping(topping);
				out.printf("\nAdded %s to your pizza.\n", topping.toString().toLowerCase());
			}


		} while (true);
	}

	void orderingHub() {

		while (true) {
			int option = getOptionInt("\nPlease choose one of the following:",
					"Add a pizza to your cart", 
					"Remove a pizza from your cart", 
					"View your current cart",
					"Add a coupon",
					"Go to checkout",
					"Exit");

			switch(option) {
			case 1:
				addPizza(cart);
				break;
			case 2:
				removePizza(cart);
				break;
			case 3:
				view(cart);
				break;
			case 4:
				addCoupon(cart);
				break;
			case 5:
				checkout(cart);
				break;
			default:
				out.printf("\nExiting the app.\n");
				return;
			}
		}
	}

	public void start() {
		orderingHub();
	}
	

	public static void main(String[] args) {
		new OrderingApp().start();
	}
}
