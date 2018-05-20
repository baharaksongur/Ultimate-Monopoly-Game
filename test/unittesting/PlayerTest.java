package unittesting;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import game.ChanceCards;
import game.LandCards;
import game.Player;

public class PlayerTest {

	private static Player p1;
	private static Player p2;

	private static LandCards landT2L1;
	private static String landNameT2L1 = "LakeStreet";

	private static LandCards landT2L2;
	private static String landNameT2L2 = "BalticAvenute";

	private static LandCards landT3L13;
	private static String landNameT3L13 = "WallStreet";

	private static ChanceCards goToJailCard;
	private static ChanceCards getOutOfFreeJailCard;
	private static List<ChanceCards> chanceCardsDeck = new LinkedList<ChanceCards>();

	private final static int[] JAIL = new int[] {2, 10}; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p1=new Player("Kutay", 1500, "GREEN");
		p2=new Player("Engin", 1500, "BLUE");

		landT2L1 = new LandCards(landNameT2L1, "purple", 60, 2, 10, 30, 90, 160, 250, 500, 50, 30);
		landT2L2 = new LandCards(landNameT2L2, "purple", 60, 4, 20, 60, 180, 320, 450, 750, 50, 30);
		landT3L13 = new LandCards(landNameT3L13,"gray", 500, 80, 300, 800, 1800, 2200, 2700, 4200, 300, 250);

		//Add the first land to the player 1. 
		p1.addCard(landT2L1);
		landT2L1.setSold(true);
		landT2L1.setLandOwner(p1);

		//Add the second land to the player 1. 
		p1.addCard(landT2L2);
		landT2L2.setSold(true);
		landT2L2.setLandOwner(p1);

		//Add the third land to the player 2.
		p2.addCard(landT3L13);
		landT3L13.setSold(true);
		landT3L13.setLandOwner(p2);
		String color = landT3L13.getLandColor();
		p2.getCardsWithColor().get(color)[0]++;				
		int[] building = new int[] {0,0,0,0,0};
		p2.getBuildings().put(landT3L13, building);

		//Creating a chance deck with one card in it so that every time it can be pulled from deck.
		goToJailCard= new ChanceCards("chanceType6", "goToJail", false);
		chanceCardsDeck.add(goToJailCard);
		
		getOutOfFreeJailCard = new ChanceCards("chanceType9", "GetOutofJailFree", true);

	}


	@Test(expected = NullPointerException.class)
	public void testNullPlayer() {
		Player p = null;
		p.repOk();
	}

	@Test
	public void testTakeMoney(){
		int initMoney = p1.getMoney();
		p1.takeMoney(200);
		int finMoney = p1.getMoney();
		assertTrue("p1 took 200 money.", (initMoney+200)==finMoney);
		p1.repOk();
	}

	@Test
	public void testTrade(){
		int p1initMoney = p1.getMoney();
		int p2initMoney = p2.getMoney();
		Player.trade(p1, p2, 300); //p1 gave p2 $300.
		int p1finMoney = p1.getMoney();
		int p2finMoney = p2.getMoney();
		assertTrue("p1 money decreased by $300", (p1initMoney-300)==p1finMoney);
		assertTrue("p2 money inrecased $300", (p2initMoney+300)==p2finMoney);
		p1.repOk();
		p2.repOk();
	}

	@Test
	public void testTradeResultWithNegativeMoney(){
		int p1initMoney = p1.getMoney();
		int p2initMoney = p2.getMoney();
		Player.trade(p1, p2, 1600); //p1 gave p2 $300.
		int p1finMoney = p1.getMoney();
		int p2finMoney = p2.getMoney();
		assertTrue("p1 money decreased by $300", (p1initMoney-1600)==p1finMoney);
		assertTrue("p2 money inrecased $300", (p2initMoney+1600)==p2finMoney);
		assertFalse(p1.repOk());
		assertTrue(p2.repOk());
	}

	@Test
	public void testPutHouseToLand(){
		//Build Construction.
		int numhouse = 2;
		//buildings = [2,0,0,0,0]
		int[] buildings = new int[] {numhouse, 0, 0, 0, 0};
		p1.getBuildings().put(landT2L1, buildings);
		p1.getBuildings().put(landT2L2, buildings);

		int initHouseNum = p1.getBuildings().get(landT2L1)[0];
		p1.PutBuildingToLand(landNameT2L1);
		int finHouseNum = p1.getBuildings().get(landT2L1)[0];

		assertTrue("p1 put an house", (initHouseNum+1)==finHouseNum);
		assertTrue("p1 put an house", finHouseNum == 3);
	}

	@Test
	public void testPutAndImproveToHotel(){
		//Build Construction.
		int numhouse = 4;
		//buildings = [4,0,0,0,0]
		int[] buildings = new int[] {numhouse, 0, 0, 0, 0};
		p1.getBuildings().put(landT2L1, buildings);
		p1.getBuildings().put(landT2L2, buildings);

		int initHotelNum = p1.getBuildings().get(landT2L1)[1];
		p1.PutBuildingToLand(landNameT2L1);
		int finHotelNum = p1.getBuildings().get(landT2L1)[1];
		int finHouseNum = p1.getBuildings().get(landT2L1)[0];

		assertTrue("p1 decreased # of houses to 0 for hotel", finHouseNum==0);
		assertTrue("p1 improved to hotel", (initHotelNum+1)==finHotelNum);
		assertTrue("p1 increased # of hotels to 1", finHotelNum==1);
	}

	@Test
	public void testPutAndImproveToSkyscraper(){
		//Build Construction.
		int numHotel = 1;
		//buildings = [0,1,0,0,0]
		int[] buildings = new int[] {0, numHotel, 0, 0, 0};
		p1.getBuildings().put(landT2L1, buildings);
		p1.getBuildings().put(landT2L2, buildings);

		int initSkyscraperNum = p1.getBuildings().get(landT2L1)[2];
		p1.PutBuildingToLand(landNameT2L1);
		int finHotelNum = p1.getBuildings().get(landT2L1)[1];
		int finSkyscraperNum = p1.getBuildings().get(landT2L1)[2];

		assertTrue("p1 decreased # of hotels to 0 for skyscraper", finHotelNum==0);
		assertTrue("p1 improved to skyscraper", (initSkyscraperNum+1)==finSkyscraperNum);
		assertTrue("p1 increased # of hotels to 1", finSkyscraperNum==1);
	}

	@Test
	public void testSellHouseFromLand(){
		//Build Construction.
		int numHouse = 2;
		//buildings = [2,0,0,0,0]
		int[] buildings = new int[] {numHouse, 0, 0, 0, 0};
		p1.getBuildings().put(landT2L1, buildings);
		p1.getBuildings().put(landT2L2, buildings);

		int initHouseNum = p1.getBuildings().get(landT2L1)[0];
		p1.SellBuildingFromLand(landNameT2L1);
		int finHouseNum = p1.getBuildings().get(landT2L1)[0];

		assertTrue("p1 decreased # of houses to 1", finHouseNum==1);
		assertTrue("p1 decreased # of houses by one", finHouseNum==(initHouseNum-1));		
	}

	@Test
	public void testSellHotelFromLand(){
		//Build Construction.
		int numHotel = 1;
		//buildings = [0,1,0,0,0]
		int[] buildings = new int[] {0, numHotel, 0, 0, 0};
		p1.getBuildings().put(landT2L1, buildings);
		p1.getBuildings().put(landT2L2, buildings);

		int initHotelNum = p1.getBuildings().get(landT2L1)[1];
		p1.SellBuildingFromLand(landNameT2L1);
		int finHouseNum = p1.getBuildings().get(landT2L1)[0];
		int finHotelNum = p1.getBuildings().get(landT2L1)[1];

		assertTrue("p1 decreased to house and # of houses is 4", finHouseNum==4);
		assertTrue("p1 decreased to house and # of hotel is 0", finHotelNum==0);
		assertTrue("p1 sold an hotel ", finHotelNum==(initHotelNum-1));	
	}

	@Test
	public void testSellSkyscrapperFromLand(){
		//Build Construction.
		int numSkyscrapper = 1;
		//buildings = [0,0,1,0,0]
		int[] buildings = new int[] {0, 0, numSkyscrapper, 0, 0};
		p1.getBuildings().put(landT2L1, buildings);
		p1.getBuildings().put(landT2L2, buildings);

		int initSkyscraperNum = p1.getBuildings().get(landT2L1)[2];
		p1.SellBuildingFromLand(landNameT2L1);
		int finHotelNum = p1.getBuildings().get(landT2L1)[1];
		int finSkyscraperNum = p1.getBuildings().get(landT2L1)[2];

		assertTrue("p1 decreased to hotel and # of hotel is 1", finHotelNum==1);
		assertTrue("p1 decreased to hotel and # of skyscraper is 0", finSkyscraperNum==0);
		assertTrue("p1 sold a skyscraper", finSkyscraperNum==(initSkyscraperNum-1));		
	}

	@Test
	public void testSellLand(){
		int initMoney=p2.getMoney();
		boolean bool=p2.sellLand("WallStreet");
		int finalMoney=p2.getMoney();
		assertTrue("p2 money increases by the half of the price of the land",initMoney+(landT3L13.getLandPrice()/2)==finalMoney);
		assertTrue("p2 could  sell the land, successively",bool);
		assertFalse("p2 sold the land", p2.getCardsList().contains(landT3L13));
	}

	@Test
	public void testSellLandWithBuildings(){
		//Build Construction.
		int numHouse = 2;
		//buildings = [2,0,0,0,0]
		int[] buildings = new int[] {numHouse, 0, 0, 0, 0};
		p2.getBuildings().put(landT2L1, buildings);
		p2.getBuildings().put(landT2L2, buildings);

		int initMoney=p2.getMoney();
		int initHouseNum = p1.getBuildings().get(landT2L1)[0];
		boolean bool=p2.sellLand(landNameT2L1);
		int finalMoney=p2.getMoney();
		int finHouseNum = p1.getBuildings().get(landT2L1)[0];
		assertTrue("p2 money did not change", initMoney==finalMoney);
		assertFalse("p2 could not sell the land", bool);
		assertFalse("p2 cardlist did not change", p2.getCardsList().contains(landT2L1));
		assertTrue("p1 # of houses did not change", finHouseNum==initHouseNum);
	}

	@Test
	public void testIsInJail(){
		ChanceCards cc=p2.drawChanceCard(chanceCardsDeck);
		if(cc.equals(goToJailCard))
			ChanceCards.goToJail(p2);
		assertTrue("p2 jail set to true", p2.isInJail());	
		assertTrue("p2 loc changed to jail loc", Arrays.equals(JAIL, p2.getLoc()));	
	}

	@Test
	public void testAddCard(){
		p1.addCard(landT2L1);
		assertTrue("p1's cardsList contains lcard", p1.getCardsList().contains(landT2L1));
	}
	
	@Test
	public void testKeepableChangeCard(){
		p1.addCard(getOutOfFreeJailCard);
		assertTrue("p1's cardsList contains chance card ", p1.getCardsList().contains(getOutOfFreeJailCard));
	}
	
	@Test
	public void testUnkeepableChangeCard(){
		p1.addCard(goToJailCard);
		assertFalse("p1's cardsList does not contain chance card ", p1.getCardsList().contains(goToJailCard));
	}

	@Test
	public void testmortgageLand(){
		int p1initMoney = p1.getMoney();
		p1.addCard(landT2L1);
		p1.mortgageLand(landT2L1.getName());
		int p1finMoney = p1.getMoney();
		int mortgagePrice= landT2L1.getMortgagedPrice();

		assertTrue("p1 took half of the mortgagePrice", p1initMoney+mortgagePrice==p1finMoney);
		assertTrue("lcard is mortgaged",landT2L1.isMortgaged()==true);
	}

	@Test
	public void testunmortgageLand(){
		int p1initMoney = p1.getMoney();
		p1.addCard(landT2L1);
		p1.unmortgageLand(landT2L1.getName());
		int p1finMoney = p1.getMoney();
		int mortgagePrice= landT2L1.getMortgagedPrice();

		assertTrue("p1 gave half of the mortgagePrice", p1finMoney+mortgagePrice==p1initMoney);
		assertTrue("lcard is mortgaged",landT2L1.isMortgaged()==false);
	}

}
