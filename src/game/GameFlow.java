package game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is for organizing the flow of the game. It is the class that
 * recieve the request from UI and distribute to domain classes according to
 * the type of the request. With this property, it works like a controller.
 */
public class GameFlow {

	//Players
	private static Player player1;
	private static Player player2;
	private static Player player3;
	private static Player player4;
	private static Player player5;
	private static Player player6;
	private static Player player7;
	private static Player player8;


	//The raw type of the player list. Includes all the player.
	private static LinkedList<Player> beginningPlayerList = new LinkedList<Player>();

	//The actual player list who plays the game according to given instructions.
	private static List<Player> playerList = new LinkedList<Player>();

	//Hold the saved games' file names.
	private static List<String> savedNames = new LinkedList<String>();

	//The amount of money in the middle.
	private static int pool;


	//Stocks
	private static Stock acmeMotors=new Stock ("AcmeMotors", 6, 150, 75, 15, 60, 135, 240, 375);
	private static Stock untdRailways=new Stock ("UntdRailways", 6, 140, 70, 14, 56, 126, 224, 350);
	private static Stock genRadio=new Stock ("GenRadio", 6, 130, 65, 13, 52, 117, 208, 325);
	private static Stock natUtilities=new Stock ("NatUtilities", 6, 120, 60, 12, 48, 108, 192, 300);
	private static Stock alldSteamship=new Stock ("AlldSteamship", 6, 110, 55, 11, 44, 99, 176, 275);
	private static Stock motionPictures=new Stock ("MotionPictures", 6, 100, 50, 10, 40, 90, 160, 250);

	//Chance Cards. 
	private ChanceCards i = new ChanceCards("chanceType1", "StCharlesPlace", false);
	private ChanceCards chanceType2 = new ChanceCards("chanceType2", "SqueezePlay", false);
	private ChanceCards chanceType3 = new ChanceCards("chanceType3", "ChairPerson", false);
	private ChanceCards chanceType4 = new ChanceCards("chanceType4", "Go", false);
	private ChanceCards chanceType5 = new ChanceCards("chanceType5", "advanceToNearestUtility", false);
	private ChanceCards chanceType6 = new ChanceCards("chanceType6", "goToJail", false);
	private ChanceCards chanceType7 = new ChanceCards("chanceType7", "advanceToNearestRailRoad", false);
	private ChanceCards chanceType8 = new ChanceCards("chanceType8", "makeGeneralRepairs", false);
	private ChanceCards chanceType9 = new ChanceCards("chanceType9", "GetOutofJailFree", true);
	private ChanceCards chanceType10 = new ChanceCards("chanceType10", "goBackThreeSpaces", false);
	private ChanceCards chanceType11 = new ChanceCards("chanceType11", "schoolFees", false);
	private ChanceCards chanceType12 = new ChanceCards("chanceType12", "advanceToTheStockExchange", false);
	private ChanceCards chanceType13= new ChanceCards("chanceType13", "loanMatures", false);
	private ChanceCards chanceType14 = new ChanceCards("chanceType14", "occupy", false);
	private ChanceCards chanceType15 = new ChanceCards("chanceType15", "foreclosedPropertySale", false);
	private ChanceCards chanceType16 = new ChanceCards("chanceType16", "excellentAccounting", false);
	private ChanceCards chanceType17 = new ChanceCards("chanceType17", "getRollin", false);
	private ChanceCards chanceType18 = new ChanceCards("chanceType18", "hurricaneMakesLandfall", false);
	private ChanceCards chanceType19 = new ChanceCards("chanceType19", "propertyTaxes", false);
	private ChanceCards chanceType20 = new ChanceCards("chanceType20", "whistle", false);
	private ChanceCards chanceType21 = new ChanceCards("chanceType21", "socialMediaFail", false);
	private ChanceCards chanceType22 = new ChanceCards("chanceType22", "assetsSeized", false);
	private ChanceCards chanceType23 = new ChanceCards("chanceType23", "winTheMarathon", false);
	private ChanceCards chanceType24 = new ChanceCards("chanceType24", "mardiGras", false);
	private ChanceCards chanceType25 = new ChanceCards("chanceType25", "taxiWarsAreNotFare", false);
	private static ChanceCards chanceType26 = new ChanceCards("chanceType26", "zeroDollarsDown", false);
	private static ChanceCards chanceType27 = new ChanceCards("chanceType27", "compedRoom", true);
	private ChanceCards chanceType28 = new ChanceCards("chanceType28", "changingLanes1", false);
	private ChanceCards chanceType29 = new ChanceCards("chanceType29", "changingLanes2", false);
	private ChanceCards chanceType30 = new ChanceCards("chanceType30", "takingBusTicket",true);
	private ChanceCards chanceType31 = new ChanceCards("chanceType31", "advanceToThePayCorner",false);
	private ChanceCards chanceType32 = new ChanceCards("chanceType32", "payingforTrafficTicket",false);
	private ChanceCards chanceType33 = new ChanceCards("chanceType33", "havingEntertainmentRocks",false);
	//Community Cards.
	private CommunityCards communityType1 = new CommunityCards("communityType1", "ConsultancyFee");
	private static CommunityCards communityType2 = new CommunityCards("communityType2", "BargainBusiness");
	private CommunityCards communityType3 = new CommunityCards("communityType3", "RenovationSuccess");
	private CommunityCards communityType4 = new CommunityCards("communityType4", "InheritedHundred");
	private CommunityCards communityType5 = new CommunityCards("communityType5", "Birthday");
	private CommunityCards communityType6 = new CommunityCards("communityType6", "DoctorFee");
	private CommunityCards communityType7 = new CommunityCards("communityType7", "AprilFifteen");
	private CommunityCards communityType8 = new CommunityCards("communityType8", "MovingExperience");
	private CommunityCards communityType9 = new CommunityCards("communityType9", "ChangingLanes1");
	private CommunityCards communityType10 = new CommunityCards("communityType10", "ChangingLanes2");
	private CommunityCards communityType11 = new CommunityCards("communityType11", "HouseCondemned");
	private CommunityCards communityType12 = new CommunityCards("communityType12", "SpecialOnlinePricing");
	private CommunityCards communityType13 = new CommunityCards("communityType13", "ReserveRent");
	private CommunityCards communityType14 = new CommunityCards("communityType14", "StreetRepair");
	private CommunityCards communityType15 = new CommunityCards("communityType15", "GetOutofJailFree");
	private CommunityCards communityType16 = new CommunityCards("communityType16", "GoToJail");
	private CommunityCards communityType17 = new CommunityCards("communityType17", "AdvanceToStockExchange");
	private CommunityCards communityType18 = new CommunityCards("communityType18", "TornadoHits");
	private CommunityCards communityType19 = new CommunityCards("communityType19", "GeneralRepair");
	private CommunityCards communityType20 = new CommunityCards("communityType20", "VehicleImpounded");
	private CommunityCards communityType21 = new CommunityCards("communityType21", "InheritStock");


	//Creating the Roll Once Cards.
	private Cards one = new Cards("1");
	private Cards two = new Cards("2");
	private Cards three = new Cards("3");
	private Cards four = new Cards("4");
	private Cards five = new Cards("5");
	private Cards six = new Cards("6");

	//Community Cards List
	private static List<CommunityCards> communityCardsDeck = new LinkedList<CommunityCards>();

	//Chance Cards List
	private static List<ChanceCards> chanceCardsDeck = new LinkedList<ChanceCards>();

	//Roll Once Cards List
	private static List<Cards> rollOnceCardsDeck = new LinkedList<Cards>();

	//Array consist of stocks.
	private static Stock[] stockArray = {acmeMotors,untdRailways,genRadio,natUtilities,alldSteamship,motionPictures};

	//List of squares, each track is seperated from one another.
	private static LinkedList<String[]> track1 = new LinkedList<String[]>();
	private static LinkedList<String[]> track2 = new LinkedList<String[]>();
	private static LinkedList<String[]> track3 = new LinkedList<String[]>();

	//Squares are in a Map so that every square can be reachable. By this way, any square information can be 
	//reached only by giving their track and index value as an two-length array to the map: e.g. [track, index]
	//Every track has its own list (it can be seen above). Every lists are constructed by String[]Â arrays. Those
	//arrays are for holding the information in it.
	//The informations about squares are kept in String[] arrays in the format of "name, x, y, width, height".
	//Players are moving in terms of index of the track lists, so players' current locations in terms of 
	//coordinate [e.g. (x,y)] on the window can get with their location as index numbers. Thus, map plays role 
	//especially in setting the player items' coordinates on board.
	private static HashMap<Integer, LinkedList<String[]>> squareMap = new HashMap<Integer, LinkedList<String[]>>();

	//List of lands.
	private static LinkedList<LandCards> land1 = new LinkedList<LandCards>();
	private static LinkedList<LandCards> land2 = new LinkedList<LandCards>();
	private static LinkedList<LandCards> land3 = new LinkedList<LandCards>();

	//Maps in terms of land objects. Work flow and the logic behind it is similar to squareMap.
	//Every track has a confronting list of Land Cards. From outside to inside, track numbers are
	//1, 2 and 3. Map holds the number of track and the confronting list for them. 
	private static HashMap<Integer, LinkedList<LandCards>> landMap = new HashMap<Integer, LinkedList<LandCards>>();

	//Instructions are kept in this list.
	private static List<String[]> instructionSet = new LinkedList<String[]>();

	//Locations of Transit Stations and RailRoads.
	//Track 1
	private final static int[] TRANSIT_STATION_1_LOC = new int[] {2, 5}; 
	private final static int[] RAILROAD_1_LOC = new int[] {1, 7}; 
	//Track 2
	private final static int[] TRANSIT_STATION_2_LOC = new int[] {3, 9}; 
	private final static int[] RAILROAD_2_LOC = new int[] {2, 15}; 
	//Track 3
	private final static int[] TRANSIT_STATION_3_LOC = new int[] {2, 25}; 
	private final static int[] RAILROAD_3_LOC = new int[] {1, 35}; 
	//Track 4
	private final static int[] TRANSIT_STATION_4_LOC = new int[] {3, 21}; 
	private final static int[] RAILROAD_4_LOC = new int[] {2, 35}; 
	//Tunel loc's.
	private final static int[] HOLLAND_TUNNEL_1_LOC = new int[] {1, 14}; 
	private final static int[] HOLLAND_TUNNEL_2_LOC = new int[] {3, 18}; 
	//Pay Corners.
	private final static int[] GO_LOC= new int[] {2, 0};
	private final static int[] PAYDAY_LOC = new int[] {1, 28}; 
	private final static int[] BONUS_LOC = new int[] {3, 6}; 


	/**
	 * A huge constructor which involves in creating nearly all the
	 * objects in the game, players list (the list to be 
	 * able to follow the next player), card decks (for community, chance
	 * and roll once cards), the squareMap (an abstract board, a map
	 * holding the location information of the squares) and the landMap
	 * (similar to square, but only have lands in it).
	 */
	public GameFlow () {		
		beginningPlayerList.add(player1);
		beginningPlayerList.add(player2);
		beginningPlayerList.add(player3);
		beginningPlayerList.add(player4);
		beginningPlayerList.add(player5);
		beginningPlayerList.add(player6);
		beginningPlayerList.add(player7);
		beginningPlayerList.add(player8);


		//Creating square map.
		BufferedReader reader = null;
		try {
			File file = new File("track1.txt");
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {    	
				String[] squareInf = line.split(" ");				        
				track1.add(squareInf);
			}

			file = new File("track2.txt");
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {    	
				String[] squareInf = line.split(" ");				        
				track2.add(squareInf);
			}

			file = new File("track3.txt");
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {    	
				String[] squareInf = line.split(" ");				        
				track3.add(squareInf);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 

		squareMap.put(1, track1);
		squareMap.put(2, track2);
		squareMap.put(3, track3);

		//Creating land map.
		reader = null;
		try {
			File file = new File("land1.txt");
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {    	
				String[] landInf = line.split(" ");
				LandCards land = new LandCards(landInf[0], landInf[1], Integer.parseInt(landInf[2]), Integer.parseInt(landInf[3]), Integer.parseInt(landInf[4]), Integer.parseInt(landInf[5]),Integer.parseInt(landInf[6]), Integer.parseInt(landInf[7]), Integer.parseInt(landInf[8]),Integer.parseInt(landInf[9]), Integer.parseInt(landInf[10]),Integer.parseInt(landInf[11]));
				land1.add(land);
			}

			file = new File("land2.txt");
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {    	
				String[] landInf = line.split(" ");
				LandCards land = new LandCards(landInf[0], landInf[1], Integer.parseInt(landInf[2]), Integer.parseInt(landInf[3]), Integer.parseInt(landInf[4]), Integer.parseInt(landInf[5]),Integer.parseInt(landInf[6]), Integer.parseInt(landInf[7]), Integer.parseInt(landInf[8]),Integer.parseInt(landInf[9]), Integer.parseInt(landInf[10]),Integer.parseInt(landInf[11]));
				land2.add(land);
			}

			file = new File("land3.txt");
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {    	
				String[] landInf = line.split(" ");
				LandCards land = new LandCards(landInf[0], landInf[1], Integer.parseInt(landInf[2]), Integer.parseInt(landInf[3]), Integer.parseInt(landInf[4]), Integer.parseInt(landInf[5]),Integer.parseInt(landInf[6]), Integer.parseInt(landInf[7]), Integer.parseInt(landInf[8]),Integer.parseInt(landInf[9]), Integer.parseInt(landInf[10]),Integer.parseInt(landInf[11]));
				land3.add(land);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 

		landMap.put(1, land1);
		landMap.put(2, land2);
		landMap.put(3, land3);






		//Adding Chance Cards to Chance Card Deck.
		//		chanceCardsDeck.add(chanceType1);
		//chanceCardsDeck.add(chanceType2);
		//		chanceCardsDeck.add(chanceType3);
		//		chanceCardsDeck.add(chanceType4);
		//		chanceCardsDeck.add(chanceType5);
		//		chanceCardsDeck.add(chanceType6);
		//		chanceCardsDeck.add(chanceType7);
		//		chanceCardsDeck.add(chanceType8);
		//chanceCardsDeck.add(chanceType9);// get out of jail
		//		chanceCardsDeck.add(chanceType10);
		//		chanceCardsDeck.add(chanceType11);
		//		chanceCardsDeck.add(chanceType12);
		//		chanceCardsDeck.add(chanceType13);
		//		chanceCardsDeck.add(chanceType14);
		//		chanceCardsDeck.add(chanceType15);
		//		chanceCardsDeck.add(chanceType16);
		//		chanceCardsDeck.add(chanceType17);
		//chanceCardsDeck.add(chanceType18);
		//chanceCardsDeck.add(chanceType19);
		//		chanceCardsDeck.add(chanceType20);
		//		chanceCardsDeck.add(chanceType21);
		//		chanceCardsDeck.add(chanceType22);
		//		chanceCardsDeck.add(chanceType23);
		//chanceCardsDeck.add(chanceType24);
		//		chanceCardsDeck.add(chanceType25);
		//	chanceCardsDeck.add(chanceType26);
		// chanceCardsDeck.add(chanceType27);// this comped room
		//		chanceCardsDeck.add(chanceType28);
		//chanceCardsDeck.add(chanceType29);
		// chanceCardsDeck.add(chanceType30);
		//chanceCardsDeck.add(chanceType31);
		chanceCardsDeck.add(chanceType33);


		//Adding Chance Cards to Chance Card Deck.
		//		communityCardsDeck.add(communityType1);
		//		communityCardsDeck.add(communityType2);
		//		communityCardsDeck.add(communityType3);
		//		communityCardsDeck.add(communityType4);
		//		communityCardsDeck.add(communityType5);
		//		communityCardsDeck.add(communityType6);
		//		communityCardsDeck.add(communityType7);
		//		communityCardsDeck.add(communityType8);
		//		communityCardsDeck.add(communityType9);
		//		communityCardsDeck.add(communityType10);
		//		communityCardsDeck.add(communityType11);
		//		communityCardsDeck.add(communityType12);
		//		communityCardsDeck.add(communityType13);
		//		communityCardsDeck.add(communityType14);
		//		communityCardsDeck.add(communityType15);//Get out of jail.
		//		communityCardsDeck.add(communityType16);
		//		communityCardsDeck.add(communityType17);
		//		communityCardsDeck.add(communityType18);
		// 		communityCardsDeck.add(communityType19);
		communityCardsDeck.add(communityType20);
		communityCardsDeck.add(communityType21);

		//Adding Roll Once Cards to Roll Once Card Deck.
		rollOnceCardsDeck.add(one);
		rollOnceCardsDeck.add(two);
		rollOnceCardsDeck.add(three);
		rollOnceCardsDeck.add(four);
		rollOnceCardsDeck.add(five);
		rollOnceCardsDeck.add(six);

		//Load the game.
		startingload();
	}

	public static HashMap<Integer, LinkedList<LandCards>> getLandMap() {
		return landMap;
	}

	public static List<String[]> getInstructionSet() {
		return instructionSet;
	}
	public static Stock[] getStockArray() {
		return stockArray;
	}

	public static void setInstructionSet(List<String[]> instructionSet) {
		GameFlow.instructionSet = instructionSet;
	}

	public static HashMap<Integer, LinkedList<String[]>> getSquareMap() {
		return squareMap;
	}

	public static void setSquareMap(HashMap<Integer, LinkedList<String[]>> squareMap) {
		GameFlow.squareMap = squareMap;
	}

	public static Player getPlayer1() {
		return player1;
	}

	public static void setPlayer1(Player player1) {
		GameFlow.player1 = player1;
	}

	public static Player getPlayer2() {
		return player2;
	}

	public static void setPlayer2(Player player2) {
		GameFlow.player2 = player2;
	}

	public static Player getPlayer3() {
		return player3;
	}

	public static void setPlayer3(Player player3) {
		GameFlow.player3 = player3;
	}

	public static Player getPlayer4() {
		return player4;
	}

	public static void setPlayer4(Player player4) {
		GameFlow.player4 = player4;
	}

	public static List<Player> getPlayerList(){		
		return playerList;
	}

	public static void setPlayerList(List<Player> playerList) {
		GameFlow.playerList = playerList;
	}

	public static List<CommunityCards> getCommunityCardsDeck() {
		return communityCardsDeck;
	}

	public static void setCommunityCardsDeck(List<CommunityCards> communityCardsDeck) {
		GameFlow.communityCardsDeck = communityCardsDeck;
	}

	public List<ChanceCards> getChanceCardsDeck() {
		return chanceCardsDeck;
	}

	public void setChanceCardsDeck(List<ChanceCards> chanceCardsDeck) {
		GameFlow.chanceCardsDeck = chanceCardsDeck;
	}

	public List<Cards> getRollOnceCardsDeck() {
		return rollOnceCardsDeck;
	}

	public static CommunityCards getBargainCard() {
		return communityType2;
	}
	public static ChanceCards getcompedRoomCard() {
		return chanceType27;
	}
	public static ChanceCards getzeroDollarsDownCard() {
		return chanceType26;
	}

	public void setRollOnceCardsDeck(List<Cards> rollOnceCardsDeck) {
		GameFlow.rollOnceCardsDeck = rollOnceCardsDeck;
	}

	public static void setPool(int pool) {
		GameFlow.pool = pool;
	}

	public static int getPool(){
		return pool;
	}

	public static LinkedList<Player> getBeginningPlayerList() {
		return beginningPlayerList;
	}

	public static void setBeginningPlayerList(LinkedList<Player> beginningPlayerList) {
		GameFlow.beginningPlayerList = beginningPlayerList;
	}

	public static String[] getSavedNames(){
		File dir = new File("saved");

		File[] savedFilesArray = dir.listFiles();
		if(savedFilesArray.length == 0)
			return null;

		String[] savedNamesArray = new String[savedFilesArray.length];
		if(savedNamesArray.length == 0)
			return null;

		for (int i = 0; i < savedFilesArray.length; i++) {
			savedNamesArray[i] = savedFilesArray[i].getName().substring(0, savedFilesArray[i].getName().length()-4);
		}

		if(savedNamesArray.length != 0)
			return savedNamesArray;
		else
			return null;
	}

	/**
	 * Load the game to be started from a new game.
	 * Reading the set of instructions here, distributed among 
	 * the game in proper classes. Reading from "Starting Instructions.txt"
	 * 
	 * @modifies playerList, player's assets and cards, dice and speedDie 
	 */
	public static void startingload(){
		//Reading the set of instructions here, distributed among the game in proper classes.
		//Reading from "Starting Instructions.txt"
		BufferedReader reader = null;
		try {
			File file = new File("Starting Instructions.txt");
			reader = new BufferedReader(new FileReader(file));
			String line;

			//First line indicates the player count.
			line = reader.readLine(); 
			String[] instructionInf = line.split(" ");	
			int playerCount = Integer.parseInt(instructionInf[1]);
			instructionSet.add(instructionInf);

			//Further lines, which indicates the instructions for players.
			for (int i = 0; i < playerCount; i++) {
				line = reader.readLine();
				instructionInf = line.split(" ");	
				Player player = beginningPlayerList.get(i); //Returns the appropriate player.
				player = new Player(instructionInf[0], Integer.parseInt(instructionInf[1]), instructionInf[2]);
				int[] loc = new int[] {Integer.parseInt(instructionInf[3]), Integer.parseInt(instructionInf[4])};
				player.setLoc(loc);
				playerList.add(player);
				instructionSet.add(instructionInf);
			}

			//After players, assets are assigning.
			for (int i = 0; i < playerCount; i++) {
				line = reader.readLine();
				instructionInf = line.split(" ");
				for (int j = 1; j < instructionInf.length; j += 7) {
					//First, take the land depending on its coordinates.
					int x = Integer.parseInt(instructionInf[j]);
					int y = Integer.parseInt(instructionInf[j+1]);
					int[] landLoc = new int[] {x, y}; 
					LandCards land = getLandObject(landLoc);

					//Build Construction.
					int house = Integer.parseInt(instructionInf[j+2]);
					int hotel = Integer.parseInt(instructionInf[j+3]);
					int skyscrapper = Integer.parseInt(instructionInf[j+4]);
					int cap = Integer.parseInt(instructionInf[j+5]);
					int trainDepot = Integer.parseInt(instructionInf[j+6]);
					int[] buildings = new int[] {house, hotel, skyscrapper, cap, trainDepot};

					//Returns the appropriate player.
					Player player = playerList.get(i); 

					//Add the land to the player. 
					player.addCard(land);
					land.setSold(true);
					land.setLandOwner(player);
					//Lands with amounts of buildings:
					player.getBuildings().put(land, buildings);

					//Necessary final works about land.
					String color = land.getLandColor();
					player.getCardsWithColor().get(color)[0]++;
					LinkedList<LandCards> list = (LinkedList<LandCards>) player.findLandwithSpecificColor(land.getLandColor());
					for (int k = 0; k < list.size(); k++) {
						player.buyLand(list.get(k));
					}
				}		
			}


			//After assets, the option for giving players cards (Chance or community) became.
			//Please note that, adding cards to players separately by instructions, doesn't affect the card desks.
			for (int i = 0; i < playerCount; i++) {
				line = reader.readLine();
				instructionInf = line.split(" ");
				for (int j = 1; j < instructionInf.length; j++) {
					//Find the card.
					String str = instructionInf[j];
					Cards card = findCardFromName(str);

					//Returns the appropriate player.
					Player player = playerList.get(i); 

					//Checks whether the card is found or not.
					if(card!=null)
						player.addCard(card);
				}
			}


			//Next, time is for assigning stocks for the players.
			for (int i = 0; i < playerCount; i++) {
				line = reader.readLine();
				instructionInf = line.split(" ");
				for (int j = 1; j < instructionInf.length; j += 2) {
					//Find the stock.
					String str = instructionInf[j];
					Stock stock = findStockFromName(str);

					//Returns the appropriate player.
					Player player = playerList.get(i); 

					//Adding player into stock share.
					String wantedStr = instructionInf[j+1];
					int wanted = Integer.parseInt(wantedStr);
					stock.setCurrentNumberOfShare(stock.getCurrentNumberOfShare()-wanted);
					player.addStock(stock, wanted);
				}
			}

			//Time is now for checking whether dices are normal or tricky.
			line = reader.readLine();
			instructionInf = line.split(" ");
			//Firstly the regular dice.
			instructionSet.add(instructionInf);
			//Secondly, the speed die.
			line = reader.readLine();
			instructionInf = line.split(" ");
			instructionSet.add(instructionInf);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}



	/**
	 * Load the game to be started from the latest save. [If it is 
	 * not saved before, then it loads the starting instructions]
	 * Reading the set of instructions here, distributed among 
	 * the game in proper classes. Reading from the specified file name.
	 * 
	 * @modifies playerList, player's assets and cards, dice and speedDie 
	 */
	public static void load(String fileName){
		//
		//Reading the set of instructions here, distributed among the game in proper classes.
		//Reading from the specified file name.
		BufferedReader reader = null;
		try {
			String path = "./saved/" + fileName + ".txt";
			File file = new File(path);
			reader = new BufferedReader(new FileReader(file));
			String line;

			//First line indicates the player count.
			line = reader.readLine(); 
			String[] instructionInf = line.split(" ");	
			int playerCount = Integer.parseInt(instructionInf[1]);
			instructionSet.remove(0);
			instructionSet.add(0,instructionInf);

			//Further lines, which indicates the instructions for players.
			for (int i = 0; i < playerCount; i++) {
				line = reader.readLine();
				instructionInf = line.split(" ");	
				Player player = beginningPlayerList.get(i); //Returns the appropriate player.
				player = new Player(instructionInf[0], Integer.parseInt(instructionInf[1]), instructionInf[2]);
				int[] loc = new int[] {Integer.parseInt(instructionInf[3]), Integer.parseInt(instructionInf[4])};
				player.setLoc(loc);
				if(playerList.size() > i)
					playerList.remove(i);
				playerList.add(i,player);
				if(instructionSet.size() > i+1)
					instructionSet.remove(i+1);
				instructionSet.add(i+1,instructionInf);
			}




			//After players, assets are assigning.
			for (int i = 0; i < playerCount; i++) {
				line = reader.readLine();
				instructionInf = line.split(" ");
				for (int j = 1; j < instructionInf.length; j += 7) {
					//First, take the land depending on its coordinates.
					int x = Integer.parseInt(instructionInf[j]);
					int y = Integer.parseInt(instructionInf[j+1]);
					int[] landLoc = new int[] {x, y}; 
					LandCards land = getLandObject(landLoc);

					//Build Construction.
					int house = Integer.parseInt(instructionInf[j+2]);
					int hotel = Integer.parseInt(instructionInf[j+3]);
					int skyscrapper = Integer.parseInt(instructionInf[j+4]);
					int cap = Integer.parseInt(instructionInf[j+5]);
					int trainDepot = Integer.parseInt(instructionInf[j+6]);
					int[] buildings = new int[] {house, hotel, skyscrapper, cap, trainDepot};

					//Returns the appropriate player.
					Player player = playerList.get(i); 

					//Add the land to the player. 
					player.addCard(land);
					land.setSold(true);
					land.setLandOwner(player);
					//Lands with amounts of buildings:
					player.getBuildings().put(land, buildings);

					//Necessary final works about land.
					String color = land.getLandColor();
					player.getCardsWithColor().get(color)[0]++;
					LinkedList<LandCards> list = (LinkedList<LandCards>) player.findLandwithSpecificColor(land.getLandColor());
					for (int k = 0; k < list.size(); k++) {
						player.buyLand(list.get(k));
					}
				}		
			}




			//After assets, the option for giving players cards (Chance or community) became.
			//Please note that, adding cards to players separately by instructions, doesn't affect the card desks.
			for (int i = 0; i < playerCount; i++) {
				line = reader.readLine();
				instructionInf = line.split(" ");
				for (int j = 1; j < instructionInf.length; j++) {
					//Find the card.
					String str = instructionInf[j];
					Cards card = findCardFromName(str);

					//Returns the appropriate player.
					Player player = playerList.get(i); 

					//Checks whether the card is found or not.
					if(card!=null)
						player.addCard(card);
				}
			}


			//Next, time is for assigning stocks for the players.
			for (int i = 0; i < playerCount; i++) {
				line = reader.readLine();
				instructionInf = line.split(" ");
				for (int j = 1; j < instructionInf.length; j += 2) {
					//Find the stock.
					String str = instructionInf[j];
					Stock stock = findStockFromName(str);

					//Returns the appropriate player.
					Player player = playerList.get(i); 

					//Adding player into stock share.
					String wantedStr = instructionInf[j+1];
					int wanted = Integer.parseInt(wantedStr);
					stock.setCurrentNumberOfShare(stock.getCurrentNumberOfShare()-wanted);
					player.addStock(stock, wanted);
				}
			}


			//Loads whose turn is it. 
			int index = playerCount + 1;
			line = reader.readLine();
			instructionInf = line.split(" ");
			if(index >= instructionSet.size()){
				instructionSet.add(instructionInf);
			}else{
				instructionSet.remove(index);
				instructionSet.add(index,instructionInf);
			}


			//Loads the amount of pool.
			line = reader.readLine();
			instructionInf = line.split(" ");
			int pool = Integer.parseInt(instructionInf[0]);
			setPool(pool);


			//Further instructions index is:
			index++;

			//Time is now for checking whether dices are normal or tricky.
			line = reader.readLine();
			instructionInf = line.split(" ");
			//Firstly the regular dice.
			if(instructionSet.size() > index){
				instructionSet.remove(index);
				instructionSet.add(index,instructionInf);
			}else {
				instructionSet.add(instructionInf);
			}

			//Secondly, the speed die.
			index++;
			line = reader.readLine();
			instructionInf = line.split(" ");
			if(instructionSet.size() > index){
				instructionSet.remove(index);
				instructionSet.add(index,instructionInf);
			}else {
				instructionSet.add(instructionInf);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}


	/**
	 * Fins the card from its name. 
	 * 
	 * @param str	name of the card to be found.
	 * @return the card found from its name. It is not found, returns null.
	 */
	private static Cards findCardFromName(String str) {
		//First iterate over community cards.
		for (int i = 0; i < communityCardsDeck.size(); i++) {
			CommunityCards card = communityCardsDeck.get(i);
			if(card.getCardType().equalsIgnoreCase(str))
				return card;
		}

		//Secondly, iterate over chance cards.
		for (int i = 0; i < chanceCardsDeck.size(); i++) {
			ChanceCards card = chanceCardsDeck.get(i);
			if(card.getCardType().equalsIgnoreCase(str))
				return card;
		}

		//If the card is not in both of the the decks, there is no such a card with this type(str).
		System.out.println("You may write invalid card type. Check it again.");
		//Returns null if the card with given name couldn't find.
		return null;
	}

	/**
	 * Saves the initial state of game. It writes to the .txt with specified name  
	 * so that the next time when the game is load, it can stay consistent and not be 
	 * disappeared. 
	 */
	public static void save(int p, String fileName){
		//Save the initial state of game.
		//It writes to the Starting Instructions so that the 
		//next time when the game is load, it can stay consistent and 
		//not be disappeared.
		try {
			String path = "./saved/" + fileName + ".txt";
			PrintWriter writer = new PrintWriter(path);

			//Text begins with specifying player count.
			int playerCount = playerList.size();
			writer.println("PlayerCount " + playerCount);

			//Continues with the player's initial balance, location and also their color.
			//They are in the order of [Player Name, Balance, Color, Track, Location on that Track].
			for (int i = 0; i < playerCount; i++) {
				Player player = playerList.get(i);
				writer.println(player.getName() + " " + player.getMoney() + " " + 
						player.getColor().toUpperCase() + " " + player.getLoc()[0] + " " + 
						player.getLoc()[1]);
			}

			//Time is writing the owned lands(as their location) with the amounts of buildings on the lands.
			for (int i = 0; i < playerCount; i++) {
				//Starts with the name of the player.
				Player player = playerList.get(i);
				writer.print(player.getName() + " ");
				List<Cards> cardList = player.getCardsList();

				//Writing every land card of the player.
				for (int j = 0; j < cardList.size(); j++) {
					Cards card = cardList.get(j);
					if(card instanceof LandCards){
						//Takes the location.
						int[] location = getLoc(card);
						//getLoc returns null if the land cannot be found. 
						//Just to be safe, controlling before writing the informations about the land.
						if(location != null){
							//Location is found. First, writing the locations of the land.
							writer.print(location[0] + " " + location[1] + " ");
							//Consecutively, writing the amounts of building on that land.
							int[] amountsOfBuildings = player.getBuildings().get(card);
							writer.print(amountsOfBuildings[0] + " " + amountsOfBuildings[1] + 
									" " + amountsOfBuildings[2] + " " + amountsOfBuildings[3] + 
									" " + amountsOfBuildings[4] + " ");
						}
					}
				} //Pass to next owned land card.

				//Pass to the next player with new line.
				writer.println();
			}//Pass to the next player.

			//Time is for writing the cards(Chance or Community) that the players carries on with them.
			for (int i = 0; i < playerCount; i++) {
				//Starts with the name of the player.
				Player player = playerList.get(i);
				writer.print(player.getName() + " ");
				List<Cards> cardList = player.getCardsList();

				//Writing every card of the player.
				for (int j = 0; j < cardList.size(); j++) {
					Cards card = cardList.get(j);
					if(card instanceof ChanceCards)
						writer.print(((ChanceCards) card).getCardType());
					else if(card instanceof CommunityCards)
						writer.print(((CommunityCards) card).getCardType());
				}
				writer.println();
			}


			//Next, stocks and the number of sharing them are being saved.
			for (int i = 0; i < playerCount; i++) {
				//Starts with the name of the player.
				Player player = playerList.get(i);
				writer.print(player.getName() + " ");

				HashMap<Stock, Integer> sharedStocks = player.getNumberofSharedStocks();
				for (Stock stock : sharedStocks.keySet()) {
					writer.print(stock.getName() + " ");
					int value = sharedStocks.get(stock);
					writer.print(value + " ");
				}
				writer.println();
			}

			//Writing whose turn was it last time when it saved.
			writer.println(p);

			//Writes the pool.
			writer.println(pool);

			//Lastly, dices are needed to be checked.
			//Dices are written at the last two of the instructions, so the size of set will lead to right lines.
			//If one of the dice (or both) is tricky, then there will be a number written.
			//These following two instructions are simply re-writing from the instruction set, what we already had before.

			//Instructions' positions on the set. 
			int speedDieInstPos = instructionSet.size() - 1 ;
			int regularDiceInstPos= instructionSet.size() - 2;

			//Taking the instructions out from the set.
			String[] regularDiceInst = instructionSet.get(regularDiceInstPos);
			String[] speedDieInst = instructionSet.get(speedDieInstPos);

			//Writing them back again.
			for (int i = 0; i < regularDiceInst.length; i++) {
				writer.print(regularDiceInst[i] + " ");
			}
			writer.println();
			for (int i = 0; i < speedDieInst.length; i++) {
				writer.print(speedDieInst[i] + " ");
			}

			//All the necessary informations has now updated to the text file. Time for exiting.
			//Closing PrintWriter writer before exiting.
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Finding the location based on its name, searching through all the tracks.
	 * @requires card to be a land card.
	 * @param card card to be found the location of.
	 * @return the location of the land or null if it is not found.
	 */
	public static int[] getLoc(Cards card){
		//Finding the location based on its name, searching through all the tracks.
		String searchedName = card.getName();
		for (int track = 1; track < 4; track++) {
			int trackLength = GameFlow.trackLength(track);
			for (int loc = 0; loc < trackLength; loc++) {
				int[] location = new int[] {track, loc};
				String name = getLandName(location);
				if(searchedName.equals(name))
					return location;
			}
		}
		//If it reaches here, there is no such a land with this name (searchedName).
		//Returns null in this case.
		return null;
	}

	/**
	 * Returns the land name from the location.
	 * @requires location to be inside of the board's bound.
	 * @param location card's location to be wanted to get.
	 * @return name of the card if it is found, otherwise null.
	 */
	public static String getLandName(int[] location){
		//Returns the land name from the location.
		int track = location[0];
		int loc = location[1];
		LinkedList<LandCards> landList = landMap.get(track);
		LandCards landCard =  landList.get(loc);
		String landName = landCard.getName();
		return landName;
	}

	/**
	 * Jobs to each specific property. Method for buying.
	 * 
	 * @requires player to be one of the player in game and land to be one of the property in game
	 * 
	 * @modifies player's money, cards list, building map, cards with color map and land's sold, owner
	 * 
	 * @param currentPlayer player that is landed on the property.
	 * @param currentLand land card that is the landed property.
	 * @param hasBargain if player has bargain or not.
	 */
	public static void landJob(Player currentPlayer, LandCards currentLand, boolean hasBargain){ 
		//Jobs to each specific property.
		if(hasBargain){
			currentPlayer.giveMoney(100);

			currentPlayer.addCard(currentLand);

			currentPlayer.removeCard(GameFlow.getBargainCard());
			communityCardsDeck.add(GameFlow.getBargainCard());
			currentLand.setSold(true);
			currentLand.setLandOwner(currentPlayer);

			String color = currentLand.getLandColor();
			currentPlayer.getCardsWithColor().get(color)[0]++;
			int[] building = new int[] {0,0,0,0,0};
			currentPlayer.getBuildings().put(currentLand, building);

		}else{
			currentPlayer.giveMoney(currentLand.getLandPrice());

			currentPlayer.addCard(currentLand);

			currentLand.setSold(true);
			currentLand.setLandOwner(currentPlayer);

			String color = currentLand.getLandColor();
			currentPlayer.getCardsWithColor().get(color)[0]++;				
			int[] building = new int[] {0,0,0,0,0};
			currentPlayer.getBuildings().put(currentLand, building);
		}
	}


	/**
	 * Method for chance card's jobs. Looks for the type and calls the right
	 * method of chance card. 
	 * 
	 * @requires player to be one of the player in game and x to be one of the chance cards in game.
	 * 
	 * @param player1 the player drawed the card.
	 * @param x drawed card
	 * @return string message to be displayed in GUI.
	 */
	public static String chanceJob(Player player1, ChanceCards x){	
		String message = "";
		if (x.getCardType()=="StCharlesPlace"){
			message = "You are being redirected to St. Charles Place";
			ChanceCards.GoToStCharlesPlace(player1);
		}else if(x.getCardType()=="SqueezePlay"){
			message = "You are being redirected to Squeeze Play.\nIf you pass GO, you will collect $200 from the bank.";
			ChanceCards.GoToSqueezePlay(player1);
		}else if(x.getCardType()=="ChairPerson"){
			message = "You are selected as the Chair Person!\nYou will pay each player $50.";
			ChanceCards.SelectedChairPerson(player1,playerList);
		}else if(x.getCardType()=="Go"){
			message = "You are being redirected to GO.\nYou will collect $200 from the bank.";
			ChanceCards.GoToGo(player1);
		}else if(x.getCardType()=="advanceToNearestUtility"){
			message = "Advance To Nearest Utility!.\nIf Go to nearest utility. If it is unowned, you may buy it from the Bank.";
			ChanceCards.advanceToNearestUtility(player1);
		}else if(x.getCardType()=="goToJail"){
			message = "Go to Jail!.\n Go directly to Jail.\nDo not pass any Pay Corner.\n Do not collect any money.";
			ChanceCards.goToJail(player1);
		}else if(x.getCardType()=="advanceToNearestRailRoad"){
			message = "Advance To Nearest RailRoad!\nGo to nearest RailRoad. If it is unowned, you may buy it from the Bank."
					+ "/nIf Owned, pay the owner twice the rent otherwise due";
			ChanceCards.advanceToNearestRailRoad(player1);
		}else if(x.getCardType()=="makeGeneralRepairs"){
			message = "Make General Repairs to all your properties!\n$25 per House, Cab Stand, and Transit Station.\n$100 per Hotel and Skyscraper.";
			ChanceCards.makeGeneralRepairs(player1);
		}else if(x.getCardType()=="GetOutofJailFree"){
			message = "Get Out of Jail Free!/nYou can keep until needed and play at any time on your turn.This card may be sold.";
			ChanceCards.getOutoJailFree(player1, x);
			chanceCardsDeck.remove(x);
		}else if(x.getCardType()=="goBackThreeSpaces"){
			message = "Go Back Three Spaces!";
			ChanceCards.goBackThreeSpaces(player1);
		}else if(x.getCardType()=="schoolFees"){
			message = "School Fees!\nPay the Pool $150.";
			ChanceCards.schoolFees(player1);
		}else if(x.getCardType()=="advanceToTheStockExchange"){
			message = "Advance to the Stock Exchange!\nIf you pass â€œPay Dayâ€� collect $300 from the Bank.";
			ChanceCards.advanceToTheStockExchange(player1);
		}else if(x.getCardType()=="loanMatures"){
			message = "Loan Matures! \nCollect $150 from the Bank.";
			ChanceCards.loanMatures(player1);
		}else if(x.getCardType()=="occupy"){
			message = "Occupy! \nGo to next unoccupied property,\nif all the properties are owned,"
					+ " move to the closest property that is not owned by you.\n Pay the normal rent due.";
			ChanceCards.occupy(player1);
		}else if(x.getCardType()=="foreclosedPropertySale"){
			message = "Foreclosed Property Sale!\nForeclose on any opponent's mortgaged property."
					+ "\nPay the mortgage value to the bank to claim the property.You can keep until needed and play at any time on your turn.";
			ChanceCards.foreclosedPropertySale(player1, x);
			chanceCardsDeck.remove(x);
		}else if(x.getCardType()=="excellentAccounting"){
			message = "Excellent Accounting!.\nAdvance to Tax Refund.Collect all of the Pool. "
					+ "/nYou can keep until needed and play at any time on your turn.This card may be traded or sold.";
			ChanceCards.excellentAccounting(player1, x);
			chanceCardsDeck.remove(x);
		}else if(x.getCardType()=="getRollin"){
			message = "Get Rollinâ€™!.\nAdvance to Roll Once. Roll the dice.";
			ChanceCards.getRollin(player1);
		}else if(x.getCardType()=="propertyTaxes"){
			message = "Property Taxes!\nPay $25 to the Pool for each unmortgaged property you own.";
			ChanceCards.propertyTaxes(player1);
		}else if(x.getCardType()=="whistle"){
			message = "HEY! TAXI!!! *whistle*.\nAdvance to Black & White Cab Co. If Unowned, you may buy it from the Bank."
					+ "\nf Owned, pay the owner the normal rent due.";
			ChanceCards.whistle(player1);
		}else if(x.getCardType()=="socialMediaFail"){
			message = "Social Media Fail!.\nSomeone posting to your company's official online presence made you look bad. "
					+ "\nPay each other player $50 to restore good PR.";
			ChanceCards.socialMediaFail(player1, (LinkedList<Player>) playerList);		
		}else if(x.getCardType()=="winTheMarathon"){
			message = "Win the Marathon!.\nTake a victory lap around the board (on your current Track)"
					+ " \nand collect the Pay Corner income from the Bank.";
			ChanceCards.winTheMarathon(player1);
		}else if(x.getCardType()=="mardiGras"){
			message = "MARDI GRAS!.\nEveryone has to see the parade of Rex, King of Carnival. \nAll players must move directly to Canal Street";
			ChanceCards.mardiGras((LinkedList<Player>) playerList);
		}else if(x.getCardType()=="compedRoom"){
			message = "Comped Room!\nThe next time you land on anyone else's property, you are excused from paying rent."
					+ "\nYou can keep this card.";
			ChanceCards.compedRoom(player1, x);
			chanceCardsDeck.remove(x);
		}else if(x.getCardType()=="changingLanes1"){
			message = "Changing Lanes!\nMove directly  to the space that is 1 Track below this one. "
					+ "\nIf you are on the Outer Track, do nothing";
			ChanceCards.changingLanes1(player1);
		}else if(x.getCardType()=="takingBusTicket"){  //if it is going to be used
			message = " Move forward to any space on this side of the Board " +
					"\n(Corner Spaces count as the first and last space on a side.) " +
					"\nKeep until needed (or expired). Play on your turn instead of rolling.";
			ChanceCards.takingBusTicket(player1, x);
			chanceCardsDeck.remove(x);
		}else if(x.getCardType()=="advanceToThePayCorner"){  //if it is going to be used
			message = " Collect your income for landing there from the Bank";	
			ChanceCards.advanceToThePayCorner(player1);
		}else if(x.getCardType()=="payingforTrafficTicket"){  //if it is going to be used
			message = " Pay the Pool $15";	
			ChanceCards.payingforTrafficTicket(player1);
		}else if(x.getCardType()=="havingEntertainmentRocks"){  //if it is going to be used
			message = "Stockholders in Motion Pictures and General Radio can immediately collect dividends";	
			ChanceCards.havingEntertainmentRocks((LinkedList<Player>) playerList);
		}
		return message;
	}

	/**
	 * Method for chance card's jobs. Looks for the type and calls the right
	 * method of chance card.
	 *  
	 * @requires player to be one of the player in game and x to be one of the chance cards in game.
	 * 
	 * @param player the player drawed the card.
	 * @param x drawed card
	 * @return string message to be displayed in GUI.
	 */
	public static String communityJob(Player player, CommunityCards x){	
		String message = "";
		if (x.getCardType()=="ConsultancyFee"){
			message = "Recieved consultancy fee!\nYou will collect $25 from the bank.";
			CommunityCards.RecieveConsultancyFee(player);
		}else if(x.getCardType()=="BargainBusiness"){
			message = "Bargain Business!\nYou can buy an unowned land for only $100, whenever you want! ";
			CommunityCards.BargainBusiness(player, x);
			communityCardsDeck.remove(x);
		}else if(x.getCardType()=="RenovationSuccess"){
			message = "Renovation Success!\nYou will collect $50 extra rent from the next player landing on your land";
			CommunityCards.RenovationSuccess(player);
		}else if(x.getCardType()=="InheritedHundred"){
			message = "You inherit $100!\nYou will collect $100 from the bank";
			CommunityCards.TakeInheritedHundred(player);
		}else if(x.getCardType()=="Birthday"){
			message = "Happy Birthday!\nYou will collect $10 from the each player, and move to the Birthday Gift\nspace and follow the instructions";
			CommunityCards.Birthday(player, playerList);
		}else if(x.getCardType()=="DoctorFee"){
			message = "Doctor's Fee!\nPay $50 to the Pool.";
			CommunityCards.DoctorFee(player);
		}else if(x.getCardType()=="ChangingLanes1"){
			message = "Changing Lanes!\nMove directly to the space that is 1 Track below this one. If you are on the inner Track, do nothing.";
			CommunityCards.ChangingLanes1(player);
		}else if(x.getCardType()=="ChangingLanes2"){
			message = "Changing Lanes!\nMove directly to the space that is 1 Track above this one. If you are on the inner Track, do nothing.";
			CommunityCards.ChangingLanes2(player);
		}else if(x.getCardType()=="SpecialOnlinePricing"){
			message = "Special Online Pricing!\nThe next time you land on anyone else's railroad, only pay 1/2 of the rent.";
			CommunityCards.SpecialOnlinePricing(player);
		}else if(x.getCardType()=="ReserveRent"){
			message = "Reserve Rent!\nCollect the rent due when you land on anotherplayer's property. ";
			CommunityCards.ReserveRent(player);
		}else if(x.getCardType()=="StreetRepair"){
			message = "Assesed Street Repair!\n$25 per Cab Stand & Transit Station, $40 per House, $115 per Hotel and $100 for Skyscraper.";
			CommunityCards.StreetRepair(player);
		}else if(x.getCardType()=="GetOutofJailFree"){
			message = "Get Out of Jail Free!\nYou can get out of jail free. Keep until needed.";
			CommunityCards.GetOutofJailFree(player,x);
			communityCardsDeck.remove(x);
		}else if(x.getCardType()=="GoToJail"){
			message = "Go to Jail!\nGo directly to Jail. Do not pass any Pay Corner. Do not collect any money.";
			CommunityCards.GoToJail(player);
		}else if(x.getCardType()=="AdvanceToStockExchange"){
			message = "Advance to the Stock Exchange!\nIf you pass 'Pay Day' collect $300.";
			CommunityCards.AdvanceStockExchange(player);
		}else if(x.getCardType()=="GeneralRepair"){
			message = "Make general repairs to all your properties - $25 per House, Cab Stand and Transit Station, $100 per Hotel and Skyscraper.";
			CommunityCards.GenerealRepair(player);
		}else if(x.getCardType()=="VehicleImpounded"){
			message = "Pay $50 to the pool, move directly to Just Visiting to pick up your car. Lose 1 turn";
			CommunityCards.VehicleImpounded(player);
		}else if(x.getCardType()=="InheritStock"){
			message = "You may chose any 1 share of any unpurchased stock to add to your portfolio.";
		}

		return message;
	}

	/**
	 * Method for the roll once
	 * 
	 * @requires player to be one of the player in game
	 * 
	 * @modifies player's money
	 * 
	 * @param player1 player landed on roll once
	 * @param facingValue the facing value on the die
	 * @param rollOnceCard the drawed roll once card
	 */
	public static void rollOnceJob(Player player1, int facingValue, Cards rollOnceCard){
		if (facingValue == Integer.parseInt(rollOnceCard.getName()))
			player1.takeMoney(100);
	}


	/**
	 * Method for squeeze play
	 * 
	 * @requires player to be one of the player in game
	 * 
	 * @modifies player's money
	 * 
	 * @param player1 player landed on squeeze play
	 * @param sum sum of the rolled dice
	 */
	public static void squeezePlayJob(Player player1, int sum){
		if(sum>=5 && sum<=9){
			player1.takeMoney(50);
		}else if(sum==3 || sum==4 || sum==10 || sum==11){
			player1.takeMoney(100);
		}else if(sum==2 || sum==12){
			player1.takeMoney(200);
		}
	}


	/**
	 * Method for the holland tunnel
	 * 
	 * @requires player to be one of the player in game
	 * 
	 * @modifies player's location 
	 * 
	 * @param player1 player landed on squeeze play
	 */
	public static void hollandTunnelJob(Player player1){
		if(Arrays.equals(player1.getLoc(), HOLLAND_TUNNEL_1_LOC)){
			player1.setLoc(HOLLAND_TUNNEL_2_LOC);
			return;
		}else if(Arrays.equals(player1.getLoc(), HOLLAND_TUNNEL_2_LOC)){
			player1.setLoc(HOLLAND_TUNNEL_1_LOC);
			return;
		}
	}

	/**
	 * Method for the luxury tax
	 * 
	 * @requires player to be one of the player in game
	 * 
	 * @modifies pool 	 
	 * 
	 * @param player1 player landed on luxury tax 
	 */
	public static void luxuryTaxJob(Player player1){
		payToPool(75,player1);
	}

	/**
	 * Method for the income tax
	 * 
	 * @requires player to be one of the player in game
	 * 
	 * @modifies pool 	 
	 * 
	 * @param player1 player landed on income tax 
	 */
	public static void incomeTaxJob(Player player1){
		if(player1.getMoney()>2000){
			payToPool(200,player1);
		}else{
			payToPool(player1.getMoney()/10,player1);
		}
	}

	/**
	 * Method for the tax refund
	 * 
	 * @requires player to be one of the player in game
	 * 
	 * @modifies pool 	 
	 * 
	 * @param player1 player landed on tax refund 
	 */
	public static void taxRefundJob(Player player1){
		player1.takeMoney(Math.round(pool/2));
		pool=pool-Math.round(pool/2);
	}

	/**
	 * Method for the bonus
	 * 
	 * @requires player to be one of the player in game
	 * 
	 * @modifies player's money 	 
	 * 
	 * @param player1 player landed on bonus
	 */
	public static void bonusJob(Player player1){
		player1.takeMoney(300);
	}

	/**
	 * Method for the paying to the pool.
	 * 
	 * @requires player to be one of the player in game and amount to be non-negative
	 * 
	 * @modifies pool and player's money
	 * 
	 * @param amount amount to be payed
	 * @param player1 player needed to pay pool
	 */
	public static void payToPool(int amount,Player player){
		player.setMoney(player.getMoney()-amount);
		GameFlow.setPool(getPool()+amount);
	}



	/**
	 * Method for transferring player to opposite track when player landed on transit.
	 * 
	 * @requires player to be one of the player in game
	 * 
	 * @modifies player's loc.
	 * 
	 * @param player player landed on square.
	 */
	public static void transitToOppositeTrack(Player player){
		int[] loc = player.getLoc();
		if(Arrays.equals(loc, TRANSIT_STATION_1_LOC)){
			player.setLoc(RAILROAD_1_LOC);
		}else if(Arrays.equals(loc, TRANSIT_STATION_2_LOC)){
			player.setLoc(RAILROAD_2_LOC);
		}else if(Arrays.equals(loc, TRANSIT_STATION_3_LOC)){
			player.setLoc(RAILROAD_3_LOC);
		}else if(Arrays.equals(loc, TRANSIT_STATION_4_LOC)){
			player.setLoc(RAILROAD_4_LOC);
		}else if(Arrays.equals(loc, RAILROAD_1_LOC)){
			player.setLoc(TRANSIT_STATION_1_LOC);
		}else if(Arrays.equals(loc, RAILROAD_2_LOC)){
			player.setLoc(TRANSIT_STATION_2_LOC);
		}else if(Arrays.equals(loc, RAILROAD_3_LOC)){
			player.setLoc(TRANSIT_STATION_3_LOC);
		}else if(Arrays.equals(loc, RAILROAD_4_LOC)){
			player.setLoc(TRANSIT_STATION_4_LOC);
		}
	}

	/**
	 * Identifies whether the location is transit or not.
	 * 
	 * @requires loc to be in bounds of board.
	 * 
	 * @param loc location
	 * 
	 * @return true if it is transit, otherwise false
	 */
	public static boolean isTransit(int[] loc){
		return Arrays.equals(loc, RAILROAD_1_LOC) || Arrays.equals(loc, RAILROAD_2_LOC) || 
				Arrays.equals(loc, RAILROAD_3_LOC) || Arrays.equals(loc, RAILROAD_4_LOC) || 
				Arrays.equals(loc, TRANSIT_STATION_1_LOC) || Arrays.equals(loc, TRANSIT_STATION_2_LOC) ||
				Arrays.equals(loc, TRANSIT_STATION_3_LOC) || Arrays.equals(loc, TRANSIT_STATION_4_LOC);
	}

	/**
	 * Handles the complex move structure in the Ultimate Monopoly.
	 * 
	 * @requires currPlayer to be one of the player in game
	 * 
	 * @modifies player's location
	 * 
	 * @param currPlayer player needed to move
	 * @param sumOfDices sum of the rolled dices (if speed die is number, it is added or just regular dice)
	 */
	public static void move(Player currPlayer, int sumOfDices){
		//Usage: Handles the complex move structure in the Ultimate Monopoly.
		int[] loc = currPlayer.getLoc();
		int moved = 0;
		int track = loc[0];
		int location = loc[1];
		if(sumOfDices % 2 == 1) { //Means odd.
			//Track will not change but location will increase as usual.
			currPlayer.move(sumOfDices, moved, track, location);
		}else if(sumOfDices % 2 == 0) { //Means even.
			//Track will be changed if player pass any transition state.
			if(isTransit(loc)){
				transitToOppositeTrack(currPlayer);
				currPlayer.move(sumOfDices, moved, track, location);
				return;
			}
			for (int i = 1; i < sumOfDices + 1; i++) { //Starts from 1, because loop has to start with checking the next location.
				moved++;
				loc[1] = (location + i) % trackLength(track);
				if(isTransit(loc)){
					transitToOppositeTrack(currPlayer);
					break;
				}
			}
			currPlayer.move(sumOfDices - moved, moved, track, location);
		}
	}

	/**
	 * Handles the complex Mr. Monopoly Move action when speed die is rolled as Mr. Monopoly.
	 * 
	 * @requires currPlayer to be one of the player in game
	 * 
	 * @modifies player's location and money if player passes one of the pay corners.
	 * 
	 * @param currPlayer player needed to move
	 * @param sumOfDices sum of the rolled dices (if speed die is number, it is added or just regular dice)
	 */
	public static void MrMonopolyMove(Player currPlayer, int sumOfDices){
		int[] loc = currPlayer.getLoc();
		int track = loc[0];
		int locationOnTrack = loc[1];

		if(sumOfDices % 2 == 1){ //Odd.
			for (int i = 0; i < trackLength(track)-1; i++) { //-1 because don't check current loc
				//Move once forward.
				locationOnTrack++;
				locationOnTrack = locationOnTrack % trackLength(track);
				int[] newLoc = new int[] {track, locationOnTrack};
				currPlayer.setLoc(newLoc);
				//Checks if player passes pay corner.
				if(isPayCorner(currPlayer.getLoc()))
					getHighestAmountFromPayCorner(currPlayer);
				LandCards property = getLandObject(currPlayer.getLoc());
				if(property.getLandOwner() == null && !(property.getName().equals("NoLand"))
						&& !(property.getLandColor().equals("utility")) && !(property.getLandColor().equals("railroad"))
						&& !(property.getLandColor().equals("cabCo"))){ //Means, property is unowned.
					return;
				}
			}

			//If we reaches here, there is no unowned property left on the track.
			//Return to starting point.
			locationOnTrack++;
			locationOnTrack = locationOnTrack % trackLength(track);
			int[] startingLoc = new int[] {track, locationOnTrack};
			currPlayer.setLoc(startingLoc);

			//There is no unowned property left, go to the nearest rentable property
			for (int i = 0; i < trackLength(track)-1; i++) { //-1 because don't check current loc
				//Currently the location.
				int[] currLoc = currPlayer.getLoc();
				//Checks if player passes pay corner.
				if(isPayCorner(currLoc))
					getHighestAmountFromPayCorner(currPlayer);
				locationOnTrack++;
				int[] newLoc = new int[] {track, locationOnTrack};
				currPlayer.setLoc(newLoc);
				LandCards property = getLandObject(currLoc);
				if(property.getLandOwner() != null  && !(property.getName().equals("NoLand"))
						&& !(property.getLandColor().equals("utility")) && !(property.getLandColor().equals("railroad"))
						&& !(property.getLandColor().equals("cabCo"))){ //Means, property is owned.
					return;
				}
			}

		}else if(sumOfDices % 2 == 0){ //Even.
			//Checks if player is already on transit.
			if(isTransit(loc)){
				//Use transit
				transitToOppositeTrack(currPlayer);
				//Move once forward.
				int[] nextLoc = currPlayer.getLoc();
				nextLoc[1]++;
				//Set the new location.
				currPlayer.setLoc(nextLoc);
			}else{
				//Move once forward.
				int[] nextLoc = currPlayer.getLoc();
				nextLoc[1]++;
				//Set the new location.
				currPlayer.setLoc(nextLoc);
			}

			while(!(track == currPlayer.getLoc()[0] && locationOnTrack == currPlayer.getLoc()[1])){
				//Currently the location.
				int[] currLoc = currPlayer.getLoc();
				//Checks if player passes pay corner.
				if(isPayCorner(currLoc))
					getHighestAmountFromPayCorner(currPlayer);
				//Take the land object at the current location.
				LandCards property = getLandObject(currLoc);
				//Go to opposite track, if loc is transit.
				if(isTransit(currLoc)){
					transitToOppositeTrack(currPlayer);
				}else if(property.getLandOwner() == null && !(property.getName().equals("NoLand"))
						&& !(property.getLandColor().equals("utility")) && !(property.getLandColor().equals("railroad"))
						&& !(property.getLandColor().equals("cabCo"))){ //Means, property is unowned.
					return;
				}
				int[] nextLoc = currLoc;
				nextLoc[1]++;
				currPlayer.setLoc(nextLoc);
			}

			//If we reaches here, there is no unowned property left on the track.
			//Return to starting point.
			locationOnTrack++;
			locationOnTrack = locationOnTrack % trackLength(track);
			int[] startingLoc = new int[] {track, locationOnTrack};
			currPlayer.setLoc(startingLoc);

			//There is no unowned property left, go to the nearest rentable property
			//Checks if player is already on transit.
			if(isTransit(loc)){
				//Use transit
				transitToOppositeTrack(currPlayer);
				//Move once forward.
				int[] nextLoc = currPlayer.getLoc();
				nextLoc[1]++;
				//Set the new location.
				currPlayer.setLoc(nextLoc);
			}else{
				//Move once forward.
				int[] nextLoc = currPlayer.getLoc();
				nextLoc[1]++;
				//Set the new location.
				currPlayer.setLoc(nextLoc);
			}

			while(!(track == currPlayer.getLoc()[0] && locationOnTrack == currPlayer.getLoc()[1])){
				//Currently the location.
				int[] currLoc = currPlayer.getLoc();
				//Checks if player passes pay corner.
				if(isPayCorner(currLoc))
					getHighestAmountFromPayCorner(currPlayer);
				//Take the land object at the current location.
				LandCards property = getLandObject(currLoc);
				//Go to opposite track, if loc is transit.
				if(isTransit(currLoc)){
					transitToOppositeTrack(currPlayer);
				}else if(property.getLandOwner() != null  && !(property.getName().equals("NoLand"))
						&& !(property.getLandColor().equals("utility")) && !(property.getLandColor().equals("railroad"))
						&& !(property.getLandColor().equals("cabCo"))){ //Means, property is owned.
					return;
				}
				int[] nextLoc = currLoc;
				nextLoc[1]++;
				currPlayer.setLoc(nextLoc);
			}
		}
	}

	/**
	 * Method for player to be able to take most income from pay corners. Its
	 * usage is needed durin the player moves Mr. Monopoly.
	 * 
	 * @requires currPlayer to be one of the player in game
	 * 
	 * @modifies player's location
	 * 
	 * @param currPlayer player needed to take most income from pay corner.
	 */
	private static void getHighestAmountFromPayCorner(Player currPlayer) {
		int[] loc = currPlayer.getLoc();
		if(Arrays.equals(loc, GO_LOC)){
			currPlayer.setMoney(currPlayer.getMoney()+200);
		}else if(Arrays.equals(loc, PAYDAY_LOC)){
			currPlayer.setMoney(currPlayer.getMoney()+400);
		}else if(Arrays.equals(loc, BONUS_LOC)){
			currPlayer.setMoney(currPlayer.getMoney()+300);
		}
	}

	/**
	 * Identifies whether the location is transit or not.
	 * 
	 * @requires loc to be in bounds of board.
	 * 
	 * @param loc location
	 * 
	 * @return true if it is pay corner, otherwise false
	 */
	private static boolean isPayCorner(int[] loc) {
		return (Arrays.equals(loc, GO_LOC) || Arrays.equals(loc, PAYDAY_LOC) || Arrays.equals(loc, BONUS_LOC));
	}

	/**
	 * Finds the land object from the location.
	 * 
	 * @requires currentPlayerLoc to be in bounds of board.
	 * 
	 * @param currentPlayerLoc
	 * 
	 * @return Land card found based on the location.
	 */
	public static LandCards getLandObject(int[] currentPlayerLoc){
		LinkedList<LandCards> landList = landMap.get(currentPlayerLoc[0]);
		return landList.get(currentPlayerLoc[1]);
	}

	/**
	 * Finds the track length of the given track
	 * 
	 * @requires track to be in bounds of board.
	 * 
	 * @param track track 
	 * 
	 * @return length of given track.
	 */
	public static int trackLength(int track){
		if(track == 1){
			return 56;
		}else if(track == 2){	
			return 40;
		}else{		
			return 24;
		}
	}

	/**
	 * This method is used for buying cabCo
	 * 
	 * @requires player to be one of the player in game and land to be one of the Cab Company in game
	 * 
	 * @modifies player's money, card list and cab company's owner and sold attributes.
	 * 
	 * @param currentPlayer player who is land on one of the capCo
	 * @param capCo Cab Company that is landed on.
	 * @param hasBargain if player has bargain or not.
	 */
	public static void buy(Player currentPlayer, CapCompany capCo,boolean hasBargain){
		//For buying cabCo,Train depot or utility
		if(hasBargain){
			currentPlayer.giveMoney(100);
			currentPlayer.addCard(capCo);
			currentPlayer.removeCard(GameFlow.getBargainCard());
			communityCardsDeck.add(GameFlow.getBargainCard());
			capCo.setSold(true);
			capCo.setLandOwner(currentPlayer);
		}else{
			currentPlayer.giveMoney(capCo.getLandPrice());
			currentPlayer.addCard(capCo);
			capCo.setSold(true);
			capCo.setLandOwner(currentPlayer);
		}
	}

	public static void birthdayGiftJob(Player currentPlayer){

		int[] loc = currentPlayer.getLoc();

		currentPlayer.move(1, 0, loc[0], loc[1]);
		LandCards land=GameFlow.getLandObject(loc);

		while(!(land.getLandColor().equals("cabCo"))){
			currentPlayer.move(1, 0, loc[0], loc[1]);
			land=GameFlow.getLandObject(loc);
		}
		currentPlayer.setLoc(loc);
	}

	public static String[] getStocksWithNamesAsArray(Stock[] stockArray){


		List<String> stocksArray=new ArrayList<String>();

		for (int i = 0; i < stockArray.length; i++) {
			if(stockArray[i].getCurrentNumberOfShare()!=0)
				stocksArray.add(stockArray[i].getName());
		}

		int x=stocksArray.size();
		String[] getStocksWithNames = new String[x]; 
		for (int i = 0; i < stocksArray.size(); i++) {
			getStocksWithNames[i] = stocksArray.get(i);
		}

		return getStocksWithNames;
	}
	public static String[] getSellingStocksWithNamesAsArray(Player player){


		List<String> stocksArray=new ArrayList<String>();

		for (Stock stock : player.getNumberofSharedStocks().keySet()) {
			stocksArray.add(stock.getName());
		}
		int x=stocksArray.size();
		String[] getSellingStocksWithNames = new String[x]; 
		for (int i = 0; i < stocksArray.size(); i++) {
			getSellingStocksWithNames[i] = stocksArray.get(i);
		}

		return getSellingStocksWithNames;
	}
	public static String[] getStockNumbersAsArray(Stock stock){

		LinkedList<Integer> numbers = new LinkedList<Integer>();
		int x= stock.getCurrentNumberOfShare();
		for (int i = 1; i <= x; i++) {
			if(i!=6)
				numbers.add(i);
		}

		int y=numbers.size();
		String[] getStocksNumbersWithNames = new String[y]; 
		for (int i = 0; i < numbers.size(); i++) {
			getStocksNumbersWithNames[i] = numbers.get(i).toString();
		}
		return getStocksNumbersWithNames;

	}
	public static String[] getSellableStockNumbersAsArray(Player player,Stock stock){

		LinkedList<Integer> numbers = new LinkedList<Integer>();
		int x= player.getNumberofSharedStocks().get(stock);
		for (int i = 1; i <= x; i++) {
			numbers.add(i);
		}

		int y=numbers.size();
		String[] getSellableStocksNumbersWithNames = new String[y]; 
		for (int i = 0; i < numbers.size(); i++) {
			getSellableStocksNumbersWithNames[i] = numbers.get(i).toString();
		}
		return getSellableStocksNumbersWithNames;

	}
	public static Stock findStockFromName(String str){

		Stock correspondingStockToName = null;


		for (int i = 0; i < stockArray.length; i++) {
			if(stockArray[i].getName().equals(str)){ 
				correspondingStockToName =stockArray[i];
				break; 
			}		
		}
		return correspondingStockToName; 
	} 



}