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
		plug.switchOff();
		plug.switchOn();
		plug.toggle();

		assertFalse(plug.isOn());
	}
	@Test
	public void testToggle2() {
		PlugSim plug = new PlugSim("a");

		plug.switchOn();
		plug.switchOff();
		plug.switchOn();
		plug.toggle();
		plug.toggle();

		assertTrue(plug.isOn());
	}

	@Test
	
	public void testPower1(){
		PlugSim plug = new PlugSim("b.230");
		plug.switchOn();
		plug.measurePower();
	
		assertTrue(plug.getPower()==230);
	}
	@Test
	
	public void testPower2(){
		PlugSim plug = new PlugSim("b.50");
		plug.switchOn();
		plug.measurePower();
	
		assertTrue(plug.getPower()==50);
	}
	@Test
	public void testPower3(){
		PlugSim plug = new PlugSim("b.500");
		plug.switchOn();
		plug.measurePower();
	
		assertTrue(plug.getPower()==500);
	}
	@Test
	public void testPower4(){
		PlugSim plug = new PlugSim("b.230");
		plug.switchOn();
		plug.switchOff();
		plug.measurePower();
	
		assertTrue(plug.getPower()==0);
	}

	@Test
	public void testPower5(){
		PlugSim plug = new PlugSim("b.230");
		plug.switchOff();
		plug.measurePower();
	
		assertFalse(plug.isOn() );
	}
	@Test
	public void testPower6(){
		PlugSim plug = new PlugSim("b.230");
		plug.switchOn();
		plug.measurePower();
	
		assertTrue(plug.isOn() && plug.getPower()==230);
	}



}
