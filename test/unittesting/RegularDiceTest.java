package unittesting;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import game.RegularDice;


public class RegularDiceTest {
private  static RegularDice rd;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rd=new RegularDice();
		
	}
	@Test(expected = NullPointerException.class)
	public void testNullRegularDie() {
		RegularDice r = null;
		r.repOk();
	}
	@Test
	public void testSpeedDieRoll(){
		rd.roll();
		int x=rd.getDice1();
		int y=rd.getDice2();
		assertTrue("Faced value", x>0 && x<=6 && y>0 && y<=6);
		rd.repOk();
	}

}
