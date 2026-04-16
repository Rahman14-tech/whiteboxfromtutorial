package hiringPolicy;

public class HiringPolicy {
	public HiringPolicy() {
		
	}
	public String policy(int age) {
		if(age < 16 || age > 65) {
			return "do not hire";	
		}else if(age < 18) {
			return "hire part-time";
		}
		return "hire full-time";
	}
	public boolean isInRange(int value, int min, int max) {
		if(value < min || max < value) {
			return false;
		}
		return true;
	}
	public void start(int age) {
		if(isInRange(age,0,100)) {
			String policy = policy(age);
			System.out.printf("The policy is: %s",policy);
		}else {
			System.out.println("Error. The age is not in range [0,100]");
			System.exit(3);
		}
	}
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Invalid number of arguments");
			System.exit(1);
		}
		int age = 0;
		try {
			age = Integer.valueOf(args[0]);
		}catch(Exception e) {
			System.out.println("Error. Please give an integer");
			System.exit(2);
		}
		
		new HiringPolicy().start(age);
	}
}
