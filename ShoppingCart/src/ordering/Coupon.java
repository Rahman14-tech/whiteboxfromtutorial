package ordering;

public enum Coupon {

	SWT2026("$20 off any purchase exceeding $26"),
	MLM("15% off any order containing 2 medium and 1 large pizza"),
	MEATLOVER("50% off all meats"),
	YUCK("a free large pizzas with honey, anchovies and pineapple when orderings two or more");
	
	public String description;
	
	private Coupon(String description) {
		this.description = description;
	}
}
