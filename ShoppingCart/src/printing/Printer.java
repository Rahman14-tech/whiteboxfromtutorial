package printing;

import java.io.PrintStream;
import java.util.ArrayList;

import pizza.*;

public class Printer {

	public PrintStream out;
	
	public Printer(){
		out = new PrintStream(System.out);
	}
	
	
	
	public String toFancyString(Enum<?> e) {
		if (e instanceof Topping t) {
			return String.format("%-10s\t($%.2f)", t.toString(), t.price());
		} else if (e instanceof Size s) {
			return String.format("%s\t($%.2f)", s.toString(), s.price());
		} else if (e instanceof Sauce s) {
			return String.format("%-6s\t($%.2f)", s.toString(), s.price());
		}
		return "";
	}
	
	public String toFancyString(Pizza p) {
		String description = String.format("%s pizza with %s sauce and ", p.getSize().toString(), p.getSauce().toString().toLowerCase()); 

		if (p.getToppingsAmount() == 0) {
			description += "no toppings";
		} else {
			description += "toppings: ";
			for (int j = 0; j < p.getToppingsAmount(); j++) {
				description += p.getTopping(j).toString().toLowerCase() + " ";
			}
		}
		
		return description;
	}
	
	public void println(String line) {
		out.println(line);
	}
	
	public void println() {
		out.println();
	}
	
	public void printf(String base, Object... args) {
		out.printf(base, args);
	}
	
	public void printEnumeration(int start, String base, Object... args) {
		for (int i = 0; i < args.length; i++) {
			out.printf(" %2d: %s\n", start+i, args[i]);
		}
	}
	
	public Object[] formatOptionsPizzas(ArrayList<Pizza> values, String... additional) {
		int amountOptions = values.size() + additional.length;
		Object[] options = new Object[amountOptions];
		for (int i = 0; i < amountOptions-additional.length; i++) {
			options[i] = toFancyString(values.get(i));
		}
		
		for (int i = 0; i < additional.length; i++) {
			options[amountOptions - additional.length + i] = additional[i];
		}
		
		return options;
	}
	
	public Object[] formatOptionsEnum(Enum<?>[] values, String... additional) {
		int amountOptions = values.length + additional.length;
		Object[] options = new Object[amountOptions];
		for (int i = 0; i < amountOptions-additional.length; i++) {
			options[i] = toFancyString(values[i]);
		}
		
		for (int i = 0; i < additional.length; i++) {
			options[amountOptions - additional.length + i] = additional[i];
		}
		
		return options;
	}
}
