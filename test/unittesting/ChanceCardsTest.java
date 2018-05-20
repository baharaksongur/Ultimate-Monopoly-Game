package unittesting;

import static org.junit.Assert.*;

import java.util.LinkedList;

import game.ChanceCards;
import game.GameFlow;
import game.LandCards;
import game.Player;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChanceCardsTest {

	private static Player p1;
	private static Player p2;
	private static Player p3;
	private static Player p4;
	private static LinkedList<Player> players;
	private static LandCards lcard;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p1=new Player("Kutay", 1500, "GREEN");
		p2=new Player("Engin", 1500, "BLUE");
		p3=new Player("Asya", 1500, "RED");
		p4=new Player("Bahar", 1500, "YELLOW");
		players=new LinkedList<Player>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);

		lcard= new LandCards("OrientalAvenue", "lightBlue", 100, 6, 30, 90, 270, 
				400, 550, 900, 50, 50);
	}
	
	@Test

	public void testChangeLanes1() {
		GameFlow.move(p1, 2);
		int currentTrack = p1.getLoc()[0];
		ChanceCards.changingLanes1(p1);
		assertTrue("p1 move to below square at Outer Track.", p1.getLoc()[0]==currentTrack-1);
	}

	@Test
	public void testChangeLanes2() {
		GameFlow.move(p2, 2);
		int currentTrack = p2.getLoc()[0];
		ChanceCards.changingLanes2(p2);
		assertTrue("p2 move to above square at Inner Track.", p2.getLoc()[0]==currentTrack+1);
	}
	
	@Test

	public void testsocialMediaFail(){

		int currentMoney = p1.getMoney();
		boolean checkMoneys = false;
		ChanceCards.socialMediaFail(p1, players);
		for (int i = 0; i < players.size(); i++) {
			if(players.get(i) != p1){
				if(players.get(i).getMoney()==1550){
					checkMoneys = true;
				}else{
					checkMoneys =false; 
					break;
				}
			}
		}
		assertTrue("p1 gave $50 to each player.", currentMoney-150==p1.getMoney() && checkMoneys==true);
	}
	
	@Test
	public void testGoBackThreeSpace() {
		int currentLoc= p1.getLoc()[1];
		ChanceCards.goBackThreeSpaces(p1);
		int finalLoc= p1.getLoc()[1];
		assertTrue("p1 went back 3 space.",finalLoc+3==currentLoc);


	}
	@Test
	public void testGoToJail() {
		int jailLocTrack = 2;
		int jailLocIndex=10;

		ChanceCards.goToJail(p1);
		int finlocTrack=p1.getLoc()[0];
		int finlocIndex=p1.getLoc()[1];
		assertTrue("p1 is in jail location now.",finlocTrack==jailLocTrack);
		assertTrue("p1 is in jail location now.",finlocIndex==jailLocIndex);
		assertTrue("p1 is in jail now.",p1.isInJail()==true);

	}


	@Test
	public void testSchoolFees() {
		int initMoney=p1.getMoney();
		int initPoolMoney=GameFlow.getPool();
		ChanceCards.schoolFees(p1);
		int finMoney=p1.getMoney();
		int finPoolMoney=GameFlow.getPool();
		assertTrue("p1 gave 50 to pool.", finMoney+50==initMoney);
		assertTrue("pool took 50 from player p1.", initPoolMoney+50==finPoolMoney);
	}

	@Test
	public void testPropertyTaxes() {
		p1.addCard(lcard); //because it is added 1 land card and set it unmortgaged.
		lcard.setMortgaged(false);
		int initMoney=p1.getMoney();
		int initPoolMoney=GameFlow.getPool();
		ChanceCards.propertyTaxes(p1);
		int finMoney=p1.getMoney();
		int finPoolMoney=GameFlow.getPool();
		assertTrue("p1 gave 25 to pool.", finMoney+25==initMoney);
		assertTrue("pool took 25 from player p1.", initPoolMoney+25==finPoolMoney);

	}

}
