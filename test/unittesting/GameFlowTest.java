package unittesting;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import game.GameFlow;
import game.LandCards;
import game.Player;

public class GameFlowTest {

	private static Player p1;
	private static int[] HOLLAND_TUNNEL_1_LOC;
	private static int[] HOLLAND_TUNNEL_2_LOC; 
	private static LandCards AndrewYoungIntlBoulevard;
	private static LandCards DecaturStreet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p1=new Player("Engin", 1500, "GREEN");
		HOLLAND_TUNNEL_1_LOC = new int[] {1, 14};
		HOLLAND_TUNNEL_2_LOC=new int[] {3, 18};
		AndrewYoungIntlBoulevard=new LandCards ("AndrewYoungIntlBoulevard", "darkGreen", 210, 17, 85, 240, 670, 840, 1025, 1525, 100, 90);
		DecaturStreet=new LandCards ("DecaturStreet", "darkGreen", 240, 20, 100, 300, 750, 925, 1100, 1600, 100, 100);
	}

	@Test
	public void testMoveforEven(){
		int currentTrack = p1.getLoc()[0];
		GameFlow.move(p1, 10); // Here, we are expected player should change the track.
		int newTrack= p1.getLoc()[0];
		assertTrue("p1 changed its track.", currentTrack!=newTrack);
		p1.repOk();
	}

	@Test
	public void testMoveforOdd(){
		int currentTrack = p1.getLoc()[0];
		GameFlow.move(p1, 15); // Here, we are expected player should not change the track.
		int newTrack= p1.getLoc()[0];
		assertTrue("p1 did not change its track.",currentTrack==newTrack);
		p1.repOk();
	}

	@Test
	public void testBuyforLand(){
		int currentMoney = p1.getMoney();
		GameFlow.landJob(p1, AndrewYoungIntlBoulevard, false);
		assertTrue("p1 bought the land correctly.", currentMoney-AndrewYoungIntlBoulevard.getLandPrice()==p1.getMoney() &&
				AndrewYoungIntlBoulevard.isSold()==true && AndrewYoungIntlBoulevard.getLandOwner()==p1);
		p1.repOk();
	}

	@Test
	public void testBuyforLandWithBargain(){
		GameFlow.move(p1, 1);
		int currentMoney = p1.getMoney();
		GameFlow.landJob(p1, DecaturStreet, true);
		assertTrue("p1 bought the land correctly with $100.", currentMoney-100==p1.getMoney() &&
				DecaturStreet.isSold()==true && DecaturStreet.getLandOwner()==p1 && !(p1.getCardList().contains(GameFlow.getBargainCard())));
		p1.repOk();
	}

	@Test
	public void testHollandTunnelJob(){
		p1.setLoc(HOLLAND_TUNNEL_1_LOC);
		GameFlow.hollandTunnelJob(p1);
		int[] newLoc=p1.getLoc();
		assertTrue("p1 directly went to the other Holland Tunnel.", Arrays.equals(newLoc,HOLLAND_TUNNEL_2_LOC));
		p1.repOk();
	}

	@Test
    public void testPayToPool() {
        int currentPool= GameFlow.getPool();
        int currentMoney = p1.getMoney();
        GameFlow.payToPool(50, p1);
        assertTrue("p1 gave $50 to pool.", (currentMoney-50)==p1.getMoney() && (currentPool+50)==GameFlow.getPool());
        p1.repOk();
    }
	
}

