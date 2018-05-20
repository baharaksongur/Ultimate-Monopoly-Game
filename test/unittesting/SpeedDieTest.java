package unittesting;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


import game.SpeedDie;;



public class SpeedDieTest {
	private  static SpeedDie sd;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sd=new SpeedDie();
		
	}
	@Test(expected = NullPointerException.class)
	public void testNullSpeedDie() {
		SpeedDie s = null;
		s.repOk();
	}
	@Test
	public void testSpeedDieRoll(){
		sd.rollSpeedDie();
		String s=sd.getFacedValue();
		assertTrue("Faced value is one of these values:{1,2,3,bus,MrMonopoly", s.equals("1")|| s.equals("2")|| s.equals("3")|| s.equals("MrMonopoly")||s.equals("Bus"));
		sd.repOk();
	}
	@Test
	public void testIsMrMonopoly(){
		sd.setFacedValue("MrMonopoly");
		String s=sd.getFacedValue();
		assertEquals(true, sd.isMrMonopoly());
		sd.repOk();
	}

}
