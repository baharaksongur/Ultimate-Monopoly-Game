package unittesting;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


import game.LandCards;
import game.Player;


public class LandCardsTest {
	private static LandCards landCard;
	private static Player p1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		landCard=new LandCards("LakeStreet","lightPink",30, 1, 5, 15, 45, 80, 125, 625, 50, 15);
		p1=new Player("Kutay", 1500, "GREEN");
		
		p1.giveMoney(landCard.getLandPrice());
		p1.addCard(landCard);

		landCard.setSold(true);
		landCard.setLandOwner(p1);

		String color = landCard.getLandColor();
		p1.getCardsWithColor().get(color)[0]++;				
		int[] building = new int[] {0,0,0,0,0};
		p1.getBuildings().put(landCard, building);
	}
	@Test
	public void testGetLandOwner(){
		Player x= landCard.getLandOwner();
		assertEquals(p1, x);
		landCard.repOk();
	}
}
