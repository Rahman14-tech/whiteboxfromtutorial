package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import hiringPolicy.HiringPolicy;

@Timeout(1)
public class HiringPolicyTests {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
	@Test
	void testPolicy() {
		HiringPolicy program = new HiringPolicy();
		
		assertEquals(program.policy(10), "do not hire");
		assertEquals(program.policy(25), "hire full-time");
		assertEquals(program.policy(16), "hire full-time");
		
	}
	
	@Test
	void testIsInRange() {
		HiringPolicy program = new HiringPolicy();
		assertEquals(program.isInRange(2,0,100),true);
		assertEquals(program.isInRange(-1,0,100),false);
	}
//	
	@Test
	void testStart() {
		HiringPolicy program = new HiringPolicy();
		program.start(30);
		assertEquals("The policy is: hire full-time", outputStream.toString());
	}
	
//	@Test
//	void testStartFail() {
//		HiringPolicy program = new HiringPolicy();
//		program.start(101);
//		assertEquals("Error. The age is not in range [0,100]", outputStream.toString());
//	}
//	
}


