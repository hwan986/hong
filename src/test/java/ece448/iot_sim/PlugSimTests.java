package ece448.iot_sim;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlugSimTests {

	@Test
	public void testInit() {
		PlugSim plug = new PlugSim("a");

		assertFalse(plug.isOn());
	}

	@Test
	public void testSwitchOn() {
		PlugSim plug = new PlugSim("a");

		plug.switchOn();

		assertTrue(plug.isOn());
	}
	@Test
	public void testName1() {
		PlugSim plug = new PlugSim("a");

		assertEquals("a",plug.getName());
	}

	@Test
	public void testName2() {
		PlugSim plug = new PlugSim("a.100");

		assertEquals("a.100",plug.getName());
	}


	@Test
	public void testSwitchOff() {
		PlugSim plug = new PlugSim("a");

		plug.switchOff();
		
		assertFalse(plug.isOn());
	}
	@Test
	public void testSwitchOnOff1() {
		PlugSim plug = new PlugSim("a");

		plug.switchOn();
		plug.switchOff();

		assertFalse(plug.isOn());
	}
	@Test
	public void testSwitchOnOff2() {
		PlugSim plug = new PlugSim("a");

		plug.switchOn();
		plug.switchOff();
		plug.switchOn();

		assertTrue(plug.isOn());
	}

	@Test
	public void testToggle1() {
		PlugSim plug = new PlugSim("a");
		plug.switchOn();
		plug.toggle();

		assertFalse(plug.isOn());
	}
	@Test
	public void testToggle2() {
		PlugSim plug = new PlugSim("a");

		plug.switchOn();
		plug.switchOff();
		plug.toggle();
		plug.toggle();

		assertFalse(plug.isOn());
	}

	@Test
	
	public void testPower1(){
		PlugSim plug = new PlugSim("b.230");
		plug.switchOn();
		plug.measurePower();
	
		assertTrue(plug.getPower()==230 && plug.isOn());
	}
	/**
	@Test	
	public void testPower2(){
		PlugSim plug = new PlugSim("b.50");
		plug.switchOn();
		plug.measurePower();
	
		assertTrue(plug.getPower()==50);
	}
	**/
	
	/**
	@Test
	public void testPower3(){
		PlugSim plug = new PlugSim("b.500");
		plug.switchOn();
		plug.measurePower();
	
		assertTrue(plug.getPower()==500);
	}
	**/

	@Test
	public void testPower2(){
		PlugSim plug = new PlugSim("b.230");
		plug.switchOn();
		plug.measurePower();
		plug.switchOff();
		plug.measurePower();
	
		assertTrue(plug.getPower()==0 && !plug.isOn());
	}


	
	
	@Test
	public void testPower3(){
		PlugSim plug = new PlugSim("b");
		plug.switchOn();
		plug.updatePower(100);
		plug.measurePower();
	
		assertTrue(100-20 <=plug.getPower() && plug.getPower()<= 100+20 );
	
	}
	@Test
	public void testPower4(){
		PlugSim plug = new PlugSim("b");
		plug.switchOn();
		plug.updatePower(50);
		plug.measurePower();
	
		assertTrue(50 <=plug.getPower() && plug.getPower()<= 50+100 );
	
	}

	@Test
	public void testPower5(){
		PlugSim plug = new PlugSim("b");
		plug.switchOn();
		plug.updatePower(400);
		plug.measurePower();
	
		assertTrue(400-100 <=plug.getPower() && plug.getPower()<= 400 );
	
	}


}
