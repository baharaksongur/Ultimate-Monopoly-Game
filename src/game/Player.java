package game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The abstract player class for representing the players playing the game and their attributes.
 *
 */
public class Player {


	private String name;
	private int money;
	private int[] loc; //0th element is Track, 1st element is location on that track.
	private LinkedList<Cards> cardsList; // List because we don't know how many card they are going to have.
	private boolean renovationSuccess;
	private boolean specialOnlinePricing;
	private boolean reserveRent;
	private boolean inJail;
	private int jailRound;
	private boolean hasCompedRoom;
	private boolean drawedAdvancingUtilityCard= false; 
	private String color;
	private boolean loseTurn;
	private int bid=0;

	private HashMap<String, int[]> cardsWithColor; //It is created for checking the number of lands with same color, for taking extra rent.
	private HashMap<LandCards, int[]> buildings;
	private HashMap<Stock, Integer> numberofSharedStocks; 

	public boolean repOk(){
		if(this==null || this.name==null || this.cardsList==null || this.cardsWithColor==null || this.buildings==null || color==null)
			return false;
		else if(this.money<0)
			return false;
		else if(this.loc[0]<0 || this.loc[0]>3 || this.loc[1]<0)
			return false;
		else if(this.loc[0]==1 && this.loc[1]>56)
			return false;
		else if(this.loc[0]==2 && this.loc[1]>40)
			return false;
		else if(this.loc[0]==3 && this.loc[1]>24)
			return false;
		else if(jailRound>3 || jailRound<0)
			return false;

		return true;
	}
	/**
	 * The default constructor of the player class. 
	 * sets the location of player to integer array containing 2 and 0 by default.
	 * sets the inJail of player  to false by default. 
	 * sets the hasCompedRoom of the player to false by default
	 * sets the renovationSuccess of the player to false by default
	 * sets the specialOnlinePricing of the player to false by default
	 * sets the ReserveRent of the player to false by default
	 * sets the buildings of the player to HashMap with LandCard object as key and integer array as a value by default
	 * sets the cardsWithColor of the player to HashMap with String as a key and integer array as a value which are coming from reading color.txt
	 * sets the jailRound of the player to 0 by default 

	 * 

	 * 
	 * @param color	     The color of the player's item on the board
	 * @param name  	 The name of the player
	 * @param money  	 The money of the player that he initially has
	 */
	public Player(String name, int money, String color) {

		loc = new int[] {2, 0}; //Players starts from GO, which is on Track_1 and location of GO is in Track_1 is 0.
		this.color = color;
		inJail=false;
		hasCompedRoom=false;
		this.name=name;
		this.money=money;
		this.cardsList= new LinkedList<Cards>();
		setRenovationSuccess(false); //By default. True when player draws Renovation Success Community Card.
		setSpecialOnlinePricing(false);
		setReserveRent(false);
		this.buildings = new HashMap<LandCards, int[]>(); //Player has no building at the beginning.
		this.cardsWithColor = new HashMap<String, int[]>();
		this.numberofSharedStocks = new HashMap<Stock, Integer>();
		jailRound=0;
		BufferedReader reader = null;
		try {
			File file = new File("color.txt");
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] lineStr = line.split(" ");

				int[] numberOfLandsWithThisColor = new int[2];
				numberOfLandsWithThisColor[0]=0;	
				numberOfLandsWithThisColor[1]= Integer.parseInt(lineStr[1]);

				cardsWithColor.put(lineStr[0], numberOfLandsWithThisColor);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		Bank bank = new Bank();
	}


	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public void buyWithAuction(LandCards land,int bid){
		giveMoney(bid);

		addCard(land);

		land.setSold(true);
		land.setLandOwner(this);
		Monopoly_GUI.getCardsInTheBank().remove(land);
		land.setAuction(false);

		String color = land.getLandColor();
		getCardsWithColor().get(color)[0]++;				
		int[] building = new int[] {0,0,0,0,0};
		getBuildings().put(land, building);
	}
	public static void goToSpecificLoc(Player p,LandCards land){
		int[] locationOfPlayer =p.getLoc();
		int[] locationToGo=LandCards.getLoc(land);
		while(!(locationOfPlayer[0]==locationToGo[0] && locationOfPlayer[1]==locationToGo[1])){
			if(GameFlow.isTransit(locationOfPlayer)){
				GameFlow.transitToOppositeTrack(p);
				locationOfPlayer[1]++;
				p.setLoc(locationOfPlayer);
			}else{
				//Move once forward.
				locationOfPlayer[1]++;
				//Set the new location.
				p.setLoc(locationOfPlayer);
			}
		}
	}
	
	
	public int getJailRound() {
		return jailRound;
	}

	public void setJailRound(int jailRound) {
		this.jailRound = jailRound;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isDrawedAdvancingUtilityCard() {
		return drawedAdvancingUtilityCard;
	}

	public void setDrawedAdvancingUtilityCard(boolean drawedAdvancingUtilityCard) {
		this.drawedAdvancingUtilityCard = drawedAdvancingUtilityCard;
	}

	public int[] getLoc() {
		return loc;
	}

	public int getUtilityCount(){

		return cardsWithColor.get("utility")[0];

	}
	
	public boolean isLoseTurn() {
		return loseTurn;
	}
	
	public void setLoseTurn(boolean loseTurn) {
		this.loseTurn = loseTurn;
	}
	
	public HashMap<Stock, Integer> getNumberofSharedStocks() {
		return numberofSharedStocks;
	}
	
	public void setNumberofSharedStocks(HashMap<Stock, Integer> numberofSharedStocks) {
		this.numberofSharedStocks = numberofSharedStocks;
	}
	
	public void setLoc(int[] loc) {
		this.loc[0] = loc[0];
		this.loc[1] = loc[1];
	}

	public LinkedList<Cards> getCardsList() {
		return cardsList;
	}
	public void setCardsList(LinkedList<Cards> cardsList) {
		this.cardsList = cardsList;
	}
	public int NumberofHouse(LandCards land){
		return buildings.get(land)[0];

	}
	public int NumberofHotel(LandCards land){
		return buildings.get(land)[1];
	}
	public int NumberofSkyscrapper(LandCards land){
		return buildings.get(land)[2];
	}
	public int NumberofCapstand(LandCards land){
		return buildings.get(land)[3];
	}
	public int NumberofTraindepot(LandCards land){
		return buildings.get(land)[4];
	}

	/**
	 * Put a house on a given land of player
	 * @requires player has the given land input and has monopoly on given land's color group
	 * @modifies the money of the player by price of the building and buildings hash map of the player by increasing the first element of integer array of given key(land)
	 */
	public void putHouse(LandCards land){

		if(Bank.getNumberOfHouses()>0){
			buildings.get(land)[0]++;
			int houseNum = Bank.getNumberOfHouses();
			houseNum = houseNum - 1;
			Bank.setNumberOfHouses(houseNum);
			giveMoney(land.getBuildingPrice());
			setCurrentRentforProperties(land);
		}
	}
	/**
	 * Construct a new list of land which are either cap company,railroad or utility
	 * @returns a list consisting of cap company,railroad and utility if player has any
	 */
	public List<LandCards> findLandwithSpecificColor(String Color){

		List<LandCards> newlist=new LinkedList<LandCards>();
		for(Cards p : cardsList ){
			if(p instanceof LandCards){
				if(!(((LandCards) p).getLandColor().equals("cabCo") || ((LandCards) p).getLandColor().equals("railroad")
						|| ((LandCards) p).getLandColor().equals("utility") )){
					if(((LandCards) p).getLandColor()==Color){
						newlist.add((LandCards) p);
					}
				}
			}

		}
		return newlist;
	}

	public void buyStockWithAuction(Stock stock,int bid, int numberofWanted){
		giveMoney(bid);
		stock.setCurrentNumberOfShare(stock.getCurrentNumberOfShare()-numberofWanted);
		if (!numberofSharedStocks.containsKey(stock)){
			numberofSharedStocks.put(stock, numberofWanted);
		}else{
			int x= numberofSharedStocks.get(stock);
			numberofSharedStocks.put(stock, x+numberofWanted);
		}
	}

	/**
	 * Removes a house from a given land of player
	 * @requires player has the given land input and has house on it
	 * @modifies the money of the player by half of the price of the building and buildings hash map of the player by decreasing the first element of integer array of the given key(land)
	 */
	public void sellHouse(LandCards land){

		buildings.get(land)[0]--;
		takeMoney(land.getBuildingPrice()/2);
		int houseNum = Bank.getNumberOfHouses()+1;
		houseNum = houseNum + 1;
		Bank.setNumberOfHouses(houseNum);
		setCurrentRentforProperties(land);
	}
	/**
	 * Put a hotel on a given land of player
	 * @requires player has the given land input and has four house on it
	 * @modifies the money of the player by five times price of the building and buildings hash map of the player by increasing the second  element of integer array of given key(land) and decreasing by four the first  element of integer array of given key(land)
	 */
	public void putHotel(LandCards land){

		if(Bank.getNumberOfHotels()>0){
			buildings.get(land)[1]++;
			buildings.get(land)[0]=0;
			Bank.setNumberOfHouses(Bank.getNumberOfHouses()+4);
			Bank.setNumberOfHotels(Bank.getNumberOfHotels()-1);
			giveMoney(land.getBuildingPrice()*5);
			setCurrentRentforProperties(land);
		}
	}
	/**
	 * remove a hotel from a given land of player
	 * @requires player has the given land input and  hotel on it
	 * @modifies the money of the player by two and a half of the price of the building and buildings hash map of the player by increasing by four the first  element of integer array of given key(land) and decreasing by one the second   element of integer array of given key(land)
	 */
	public void sellHotel(LandCards land){

		buildings.get(land)[1]--;
		buildings.get(land)[0]=4;
		Bank.setNumberOfHouses(Bank.getNumberOfHouses()-4);
		Bank.setNumberOfHotels(Bank.getNumberOfHotels()+1);
		int money = (int) (land.getBuildingPrice()*2.5);
		takeMoney(money);
		setCurrentRentforProperties(land);

	}
	/**
	 * put a sky scraper on a given land of player
	 * @requires player has the given land input and hotel on it
	 * @modifies the money of the player by six times price of the building and buildings hash map of the player by increasing the third  element of integer array of given key(land) and decreasing  the second element of integer array of given key(land)
	 */
	public void putSkyscrapper(LandCards land){

		if(Bank.getNumberOfSkyscraper()>0){
			buildings.get(land)[2]++;
			buildings.get(land)[1]=0;
			Bank.setNumberOfHotels(Bank.getNumberOfHotels()+1);
			Bank.setNumberOfSkyscraper(Bank.getNumberOfSkyscraper()-1);
			giveMoney(land.getBuildingPrice()*6);
			setCurrentRentforProperties(land);
		}

	}
	/**
	 * Remove a sky scraper from a given land of player
	 * @requires player has the given land input and sky scraper on it
	 * @modifies the money of the player by three times price of the building and buildings hash map of the player by increasing the second  element of integer array of given key(land) and decreasing  the third  element of integer array of given key(land)
	 */
	public void sellSkyscrapper(LandCards land){

		buildings.get(land)[2]--;
		buildings.get(land)[1]=1;
		Bank.setNumberOfHotels(Bank.getNumberOfHotels()-1);
		Bank.setNumberOfSkyscraper(Bank.getNumberOfSkyscraper()+1);
		takeMoney(land.getBuildingPrice()*3);
		setCurrentRentforProperties(land);

	}
	/**
	 * Put a Cap stand on a given land of player
	 * @requires player has the given land input
	 * @modifies the money of the player by price of the building and buildings hash map of the player by increasing the fourth  element of integer array of given key(land) 
	 */
	public void putCapstand(LandCards land){

		if(Bank.getNumberOfCapStand()>0){
			buildings.get(land)[3]++;
			Bank.setNumberOfCapStand(Bank.getNumberOfCapStand()-1);
			giveMoney(land.getBuildingPrice());
			setCurrentRentforCapStands(land);
		}
	}
	/**
	 * Remove a Cap stand  from a given land of player
	 * @requires player has the given land input
	 * @modifies the money of the player by half of the price of the building and buildings hash map of the player by decreasing the fourth  element of integer array of given key(land) 
	 */
	public void sellCapstand(LandCards land){

		buildings.get(land)[3]--;
		Bank.setNumberOfCapStand(Bank.getNumberOfCapStand()+1);
		takeMoney(land.getBuildingPrice()/2);
		setCurrentRentforCapStands(land);

	}
	/**
	 * Put a train depot on a given land of player
	 * @requires player has the given land input
	 * @modifies the money of the player by price of the building and buildings hash map of the player by increasing the fifth  element of integer array of given key(land) 
	 */
	public void putTraindepot(LandCards land){

		if(Bank.getNumberOfTraindepots()>=0){
			buildings.get(land)[4]++;
			Bank.setNumberOfTraindepots(Bank.getNumberOfTraindepots()-1);
			giveMoney(land.getBuildingPrice());
			setCurrentRentforRailroads(land);
		}

	}
	/**
	 * Remove a train depot from a given land of player
	 * @requires player has the given land input
	 * @modifies the money of the player by price of the building and buildings hash map of the player by decreasing the fifth  element of integer array of given key(land) 
	 */
	public void sellTraindepot(LandCards land){

		buildings.get(land)[4]--;
		Bank.setNumberOfTraindepots(Bank.getNumberOfTraindepots()+1);
		takeMoney(land.getBuildingPrice()/2);
		setCurrentRentforRailroads(land);

	}
	public HashMap<LandCards, int[]> getBuildings() {
		return buildings;
	}
	public void setBuildings(HashMap<LandCards, int[]> buildings) {
		this.buildings = buildings;
	}
	public boolean isSpecialOnlinePricing() {
		return specialOnlinePricing;
	}
	public void setSpecialOnlinePricing(boolean specialOnlinePricing) {
		this.specialOnlinePricing = specialOnlinePricing;
	}
	public boolean isReserveRent() {
		return reserveRent;
	}
	public void setReserveRent(boolean reserveRent) {
		this.reserveRent = reserveRent;
	}
	/**
	 * Check whether the player has the one less land of given color than the given color group has
	 * @returns boolean value true if player's hash map cardsWithColor's integer array's 1st element associated with the given key is one less than the integer array's 2nd element ,otherwise returns false
	 */
	public boolean hasMajorityOwnerShip(String color){

		if((cardsWithColor.get(color)[0]+1)==cardsWithColor.get(color)[1])
			return true;
		return false;
	}
	/**
	 * Check whether the player has all the lands of given color group
	 * @returns boolean value true if player's hash map cardsWithColor's integer array's 1st element associated with the given key is equal to the integer array's 2nd element ,otherwise returns false
	 */
	public boolean hasMonopoly(String color){

		if(cardsWithColor.get(color)[0]==cardsWithColor.get(color)[1])
			return true;
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean hasMoney(){
		return (getMoney()>=0);
	}

	public LinkedList<Cards> getCardList() {
		return cardsList;
	}

	public boolean getRenovationSuccess(){
		return renovationSuccess;
	}
	/**
	 * Player gives money by amount given as an argument
	 * @requires player has money more than the amount given
	 * @modifies the money of the player by amount
	 */
	public void giveMoney (int amount){

		setMoney(money-amount);
		System.out.println(getName() + ", your remaining money is: " +getMoney());
	}
	/**
	 * Player takes money by amount given as an argument
	 * @modifies the money of the player by amount
	 */
	public void takeMoney (int amount){

		setMoney(money+amount);
		System.out.println(getName() + ", your money is: " +getMoney());
	}
	/**
	 * Player gives money by amount given as an argument to the other player
	 * @requires this has money more than the amount given
	 * @modifies the money of the players by amount
	 */
	public static void trade (Player player1, Player player2, int amount){

		player1.giveMoney(amount);
		player2.takeMoney(amount);
	}
	/**
	 * Player moves accordance with the sum of dices and if he passes through any payment squares,he gets payment
	 * @modifies the location of the player and the money of the player
	 */
	public void move (int sumOfDices, int moved, int startTrack, int startLocation){

		int track = loc[0];
		int location = loc[1];
		int trackLength = GameFlow.trackLength(startTrack);

		if(startTrack==1 && startLocation <= 28 && startLocation+sumOfDices+moved > 28 ){
			if(sumOfDices % 2 != 0){
				takeMoney(300);
			}else if(sumOfDices % 2 == 0){
				takeMoney(400);
			}

		}else if(startTrack==2 && (startLocation+sumOfDices+moved) % trackLength  < startLocation % trackLength ){
			takeMoney(200);

		}else if(startTrack==3 && startLocation % trackLength < 6 && (startLocation+sumOfDices+moved) % trackLength > 6 ){
			takeMoney(250);

		}
		int[] newLoc = {track, (location+sumOfDices) %  GameFlow.trackLength(track)};
		setLoc(newLoc);
	}

	/**
	 * Player draws a chance card
	 * @requires given cardList is not null
	 * @returns chance card that player drew
	 */
	public ChanceCards drawChanceCard(List<ChanceCards> cardsList){

		ChanceCards x = cardsList.get(0);
		cardsList.remove(x);
		cardsList.add(cardsList.size(), x);
		return x;
	}
	/**
	 * Player draws a community card
	 * @requires given cardList is not null
	 * @returns community card that player drew
	 */
	public CommunityCards drawCommunityCard(List<CommunityCards> cardsList){

		CommunityCards x = cardsList.get(0);
		cardsList.remove(x);
		cardsList.add(cardsList.size(), x);
		return x;
	}
	/**
	 * Player draws a roll once card
	 * @requires given cardList is not null
	 * @returns roll once card that player drew
	 */
	public Cards drawRollOnceCard(List<Cards> cardsList){

		Cards x = cardsList.get(0);
		cardsList.remove(x);
		cardsList.add(cardsList.size(), x);
		return x;
	}
	/**
	 * Add given card to player's card list
	 * @modifies cardList of the player
	 */
	public void addCard (Cards card){
		if(card instanceof ChanceCards){
			if(((ChanceCards) card).isKeepable())
				cardsList.add(card);
		}else{
			cardsList.add(card);
		}
	}

	/**
	 * remove given card from player's card list
	 * @requires given card input is exist in player's cardList
	 * @modifies cardList of the player
	 */
	public void removeCard (Cards card){

		cardsList.remove(card);
	}

	public boolean isInJail() {
		return inJail;
	}


	public boolean isHasCompedRoom() {
		return hasCompedRoom;
	}

	public void setHasCompedRoom(boolean hasCompedRoom) {
		this.hasCompedRoom = hasCompedRoom;
	}

	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	public boolean isRenovationSuccess() {
		return renovationSuccess;
	}

	public void setRenovationSuccess(boolean renovationSuccess) {
		this.renovationSuccess = renovationSuccess;
	}

	public HashMap<String, int[]> getCardsWithColor() {
		return cardsWithColor;
	}
	/**
	 * Sets given input land's current rent according to its owner's  other possessions
	 * @modifies input land's currentRent instance
	 */
	public void setCurrentRentforProperties(LandCards lCard){

		if(hasMajorityOwnerShip(lCard.getLandColor()) && !hasBuilding(lCard)){
			lCard.setCurrentRent(lCard.getRent0()*2);
		}else if(hasMonopoly(lCard.getLandColor())  && !hasBuilding(lCard)){
			lCard.setCurrentRent(lCard.getRent0()*3);
		}else if(NumberofHouse(lCard)!=0 && NumberofHotel(lCard) == 0 && NumberofSkyscrapper(lCard) == 0){
			if(NumberofHouse(lCard)==1){
				lCard.setCurrentRent(lCard.getRent1());
			}else if(NumberofHouse(lCard)==2){
				lCard.setCurrentRent(lCard.getRent2());
			}else if(NumberofHouse(lCard)==3){
				lCard.setCurrentRent(lCard.getRent3());
			}else if(NumberofHouse(lCard)==4){
				lCard.setCurrentRent(lCard.getRent4());
			}			
		}else if(NumberofHouse(lCard) == 0 && NumberofHotel(lCard) != 0 && NumberofSkyscrapper(lCard) == 0){
			lCard.setCurrentRent(lCard.getRent5());return;
		}else if(NumberofHouse(lCard) == 0 && NumberofHotel(lCard) == 0 && NumberofSkyscrapper(lCard) != 0){
			lCard.setCurrentRent(lCard.getRent6());return;
		}else{
			lCard.setCurrentRent(lCard.getRent0());return;
		}
	}
	/**
	 * Checks whether player has any building on the given land input
	 * @returns true if the given land has any kind of building, otherwise returns false
	 */
	public boolean hasBuilding(LandCards lCard){

		return !(NumberofHouse(lCard) == 0 && NumberofHotel(lCard) == 0 && NumberofSkyscrapper(lCard) == 0);
	}
	/**
	 * Set current rent for the given land according to whether it has a cap stands
	 * @modifies LandCards lCard's current rent 
	 */
	public void setCurrentRentforCapStands(LandCards lCard){

		if(NumberofCapstand(lCard)==0){
			lCard.setCurrentRent(lCard.getRent0());
		}else if((NumberofCapstand(lCard)==1)){
			lCard.setCurrentRent(lCard.getRent0()*2);
		}
	}
	/**
	 * Set current rent for the given land according to whether it has a train depot
	 * @modifies LandCards lCard's current rent 
	 */
	public void setCurrentRentforRailroads(LandCards lCard){

		if(NumberofTraindepot(lCard)==0){
			lCard.setCurrentRent(lCard.getRent0());
		}else if((NumberofTraindepot(lCard)==1)){
			lCard.setCurrentRent(lCard.getRent0()*2);
		}
	}
	/**
	 * Set current rent for the given land 
	 * @modifies LandCards lCard's current rent 
	 */
	public void buyLand(LandCards land){

		if(land.getLandColor().equals("railroad")){
			setCurrentRentforRailroads(land);
		}else if(land.getLandColor().equals("cabCo")){
			setCurrentRentforCapStands( land);
		}else{
			setCurrentRentforProperties(land);
		}
	}
	
	public void buyStock(Stock stock, int numberofWanted){

		if(numberofSharedStocks.get(stock) == null || 
				(stock.getCurrentNumberOfShare() >= numberofWanted
				&& numberofSharedStocks.get(stock)<6-numberofWanted 
						)){
			giveMoney(stock.getPriceOfShare()*numberofWanted);
			stock.setCurrentNumberOfShare(stock.getCurrentNumberOfShare()-numberofWanted);

			if (!numberofSharedStocks.containsKey(stock)){
				numberofSharedStocks.put(stock, numberofWanted);
			}else{
				int x= numberofSharedStocks.get(stock);
				numberofSharedStocks.put(stock, x+numberofWanted);
			}
		}
	}
	
	public void sellStock(Stock stock, int numberofWanted){
		
		takeMoney(stock.getPriceOfShare()* numberofWanted);
		stock.setCurrentNumberOfShare(stock.getCurrentNumberOfShare()+ numberofWanted);

		if (numberofSharedStocks.get(stock)==numberofWanted){
			numberofSharedStocks.remove(stock);
		}else{
			int x= numberofSharedStocks.get(stock);
			numberofSharedStocks.put(stock, x-numberofWanted);
		}

	}

	public void addStock(Stock stock, int wanted){
		if (!numberofSharedStocks.containsKey(stock)){
			numberofSharedStocks.put(stock, wanted);
		}else{
			int x= numberofSharedStocks.get(stock);
			numberofSharedStocks.put(stock, x-wanted);
		}
	}
	
	/**
	 * Determine the lands that player has in the his cardList and returns them as a list
	 * @returns a list which is consisting of lands owned by player 
	 */
	public LinkedList<LandCards> getOwnedLands(){

		LinkedList<LandCards> ownedLands = new LinkedList<LandCards>();
		for (Cards card : cardsList) {
			if(card instanceof LandCards)
				ownedLands.add((LandCards) card);
		}
		return ownedLands;
	}
	/**
	 * Determine the names the lands that player has and return them as a string array
	 * @returns a string array which is consisting of lands' name owned by player 
	 */
	public String[] getOwnedLandsWithNamesAsArray(){

		String[] ownedLandsWithNames = new String[cardsList.size()]; //Can't bigger than card list.
		for (int i = 0; i < cardsList.size(); i++) {
			Cards card = cardsList.get(i);
			if(card instanceof LandCards)
				ownedLandsWithNames[i] = card.getName();
		}
		return ownedLandsWithNames;
	}
	/**
	 * Determine the lands player owned other than cap company,railroad and utility and which has building on it
	 * @returns a string array which consists of lands' name except cap company, utility and railroad and which has building on it owned by player 
	 */
	public String[] getOwnedLandsColorWithNamesAsArray(){

		List<String> newlist=new ArrayList<String>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards land = cardsList.get(i);
			if(land instanceof LandCards){
				if(!(((LandCards) land).getLandColor().equals("cabCo") || ((LandCards) land).getLandColor().equals("railroad")
						|| ((LandCards) land).getLandColor().equals("utility") )){
					if(hasMonopoly(((LandCards) land).getLandColor())){
						if(hasBuilding((LandCards) land)){
							newlist.add(((LandCards) land).getLandColor());
						}
					}
				}
			}
		}

		int x=newlist.size();
		String[] getOwnedLandsColorWithNames = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getOwnedLandsColorWithNames[i] = newlist.get(i);
		}

		return getOwnedLandsColorWithNames;
	}
	/**
	 * Determine the players' names who has cap company and returns them as a string array
	 * @returns a string array which consists of player's name who has cap company
	 */
	public String[] getCompanyCapOwnerWithNamesAsArray(List <Player> players){

		List<String> newlist=new ArrayList<String>();

		for (int i = 0; i < players.size(); i++) {

			for (int m = 0; m < players.get(i).cardsList.size(); m++) {
				Cards land = cardsList.get(i);
				if(land instanceof LandCards){
					if(((LandCards) land).getLandColor().equals("cabCo")){
						newlist.add(players.get(i).getName());
						break;
					}
				}
			}
		}

		int x=newlist.size();
		String[] getCompanyCapOwnerWithName = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getCompanyCapOwnerWithName[i] = newlist.get(i);
		}

		return getCompanyCapOwnerWithName;


	}
	/**
	 * Determine lands of player other than cap company,railroad,utility which are not mortgaged or does not have building on it and returns them as a string array
	 * @returns a string array which consists of name of lands owned by player which are not mortgaged, doesn't have any building except railroads, utilities and cap companies
	 */
	public String[] getSeizeLandsWithNamesAsArray(){

		List<LandCards> newlist=new ArrayList<LandCards>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards land = cardsList.get(i);
			if(land instanceof LandCards){
				if(!(((LandCards) land).getLandColor().equals("cabCo") || ((LandCards) land).getLandColor().equals("railroad")
						|| ((LandCards) land).getLandColor().equals("utility") )){
					if(!((LandCards) land).isMortgaged() && NumberofHouse( (LandCards) land)==0 && NumberofHotel((LandCards) land)==0
							&& NumberofSkyscrapper((LandCards) land)==0 ){
						newlist.add((LandCards) land);
					}
				}
			}
		}
		int x=newlist.size();
		String[] getSeizeLandsWithNames = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getSeizeLandsWithNames[i] = newlist.get(i).getName();
		}

		return getSeizeLandsWithNames;
	}
	/**
	 * Determine the names of the cap company that player has owned and returns them as a string array
	 * @returns a string array which consists of cap companies' names owned by player
	 */
	public String[] getcabCompanyWithNamesAsArray(){

		List<LandCards> newlist=new ArrayList<LandCards>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards cabCompany = cardsList.get(i);
			if(cabCompany instanceof LandCards){
				if(((LandCards) cabCompany).getLandColor().equals("cabCo")){
					newlist.add((LandCards) cabCompany);
				}
			}
		}
		int x=newlist.size();
		String[] getcabCompanyWithNames = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getcabCompanyWithNames[i] = newlist.get(i).getName();
		}

		return getcabCompanyWithNames;
	}
	
	public String[] getUseCardWithNamesAsArray(){

		List<ChanceCards> newlist=new ArrayList<ChanceCards>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards usableCard = cardsList.get(i);
			if(!(usableCard instanceof LandCards)){
				newlist.add((ChanceCards) usableCard);
			}
		}

		int x=newlist.size();
		String[] getUseCardWithNames = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getUseCardWithNames[i] = ((ChanceCards) newlist.get(i)).getCardType();
		}

		return getUseCardWithNames;
	}
	
	/**
	 * Determine the lands other than cap company, railroad,utility that player has owned which has at least one building on it
	 * @returns a string array which consists of land's name owned by player which has at least one building on it except cap companies, railroads and utilities 
	 */
	public String[] getLandwithHouseBuildingAsArray(){

		List<LandCards> newlist=new ArrayList<LandCards>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards cards = cardsList.get(i);
			if(cards instanceof LandCards){
				if(!(((LandCards) cards).getLandColor().equals("cabCo") || ((LandCards) cards).getLandColor().equals("railroad")
						|| ((LandCards) cards).getLandColor().equals("utility") )){
					if(NumberofHouse((LandCards) cards)!=0 ){
						newlist.add((LandCards) cards);
					}

				}
			}
		}
		int x=newlist.size();
		String[] getLandwithHouseBuilding = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getLandwithHouseBuilding[i] = newlist.get(i).getName();
		}

		return getLandwithHouseBuilding;
	}


	/**
	 * Determine the lands that player has owned that can be mortgaged
	 * @returns a string array which consists of land's name owned by player which is not mortgaged or does not have any building on it
	 */
	public String[] getPossibleMorgagedLandWithNamesAsArray(){

		List<LandCards> newlist=new ArrayList<LandCards>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards card = cardsList.get(i);
			if(card instanceof LandCards){
				if(!((LandCards) card).isMortgaged() ||
						(NumberofHouse((LandCards) card)==0 && NumberofHotel((LandCards) card)==0 
						&& NumberofSkyscrapper((LandCards) card)==0 && NumberofTraindepot((LandCards) card)==0
						&& NumberofCapstand((LandCards) card)==0 )){
					newlist.add((LandCards) card);
				}
			}
		}
		int x=newlist.size();
		String[] getPossibleMorgagedLandWithNames = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getPossibleMorgagedLandWithNames[i] = newlist.get(i).getName();
		}

		return getPossibleMorgagedLandWithNames;
	}
	/**
	 * Find the lands of player which are mortgaged
	 * @returns a string array which consists of land's name owned by player which are mortgaged
	 */
	public String[] getPossibleunMorgagedLandWithNamesAsArray(){

		List<LandCards> newlist=new ArrayList<LandCards>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards card = cardsList.get(i);
			if(card instanceof LandCards){
				if(((LandCards) card).isMortgaged()) {
					newlist.add((LandCards) card);
				}
			}
		}

		int x=newlist.size();
		String[] getPossibleunMorgagedLandWithNames = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getPossibleunMorgagedLandWithNames[i] = newlist.get(i).getName();
		}

		return getPossibleunMorgagedLandWithNames;
	}


	/**
	 * Find the lands of player where player can build on it
	 * @returns a string array which consists of land's name owned by player where player can put a building
	 */
	public String[] getPossiblePutBuildingWithNamesAsArray(){

		List<LandCards> newlist=new ArrayList<LandCards>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards card = cardsList.get(i);
			if(card instanceof LandCards)  {
				//card = (LandCards) card;
				if(((LandCards) card).getLandColor().equals("cabCo")){
					newlist.add((LandCards) card);
				}else if(((LandCards) card).getLandColor().equals("railroad")){
					newlist.add((LandCards) card);
				}else{
					if(hasMonopoly(((LandCards) card).getLandColor())){
						newlist.add((LandCards) card);
					}
				}
			}
		}

		int x=newlist.size();
		String[] getPossiblePutBuildingWithNames = new String[x]; 
		for (int i = 0; i < x; i++) {
			getPossiblePutBuildingWithNames[i] = newlist.get(i).getName();
		}

		return getPossiblePutBuildingWithNames;
	}


	/**
	 * Find the lands of player which have building(s) on it.
	 * @returns a string array which consists of land's name owned by player which has at least one building on it containing cap company, railroad and utility
	 */
	public String[] getPossibleSellBuildingWithNamesAsArray(){


		List<LandCards> newlist=new ArrayList<LandCards>();
		for (int i = 0; i < cardsList.size(); i++) {
			Cards cards = cardsList.get(i);
			LandCards lcards=null;
			if(cards instanceof LandCards){
				if(((LandCards) cards).getLandColor().equals("cabCo") && NumberofCapstand((LandCards) cards)==1)  {
					lcards=(LandCards) cards;

				}else if(((LandCards) cards).getLandColor().equals("railroad") && NumberofTraindepot((LandCards) cards)==1)  {
					lcards=(LandCards) cards;

				}else{ 
					if(NumberofHouse((LandCards) cards) !=0 || NumberofHotel((LandCards) cards)==1 || 
							NumberofSkyscrapper((LandCards) cards)==1){
						lcards=(LandCards) cards;
					}
				}
				if(lcards !=null)
					newlist.add((LandCards) lcards);
			}

		}

		int x=newlist.size();
		String[] getPossibleSellBuildingWithNames = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getPossibleSellBuildingWithNames[i] = newlist.get(i).getName();
		}

		return getPossibleSellBuildingWithNames;
	}


	/**
	 * Disposes player of land with given name as an argument and if player has nothing, send him to jail
	 * @modifies player's cardList by removing land with given name or if player does not have any land,modifies player's location and inJail instance 
	 */
	public void seizeLand(String str) {

		LandCards landCard = findLandFromName(str);
		if(landCard != null){ //If the object has found.
			//Now the card is seized; so has no longer an owner, it is not sold and it is removed from the card list of player.
			landCard.setLandOwner(null);
			landCard.setSold(false);
			cardsList.remove(landCard);
		}else{
			int[] loc= new int[] {2, 10}; 
			setLoc(loc);
			setInJail(true);
		}
	}
	/**
	 * Sets land with given name as an argument as mortgaged
	 * @requires player has the land named as given input
	 * @modifies player's money by mortgaged price and land's mortgaged instance
	 */
	public void mortgageLand(String str){

		LandCards landCard = findLandFromName(str);
		if(landCard != null){ //If the object has found.
			//Now the card is seized; so has no longer an owner, it is not sold and it is removed from the card list of player.
			landCard.setMortgaged(true);
			takeMoney(landCard.getMortgagedPrice());

		}

		//    }else{ jail a giidp gitmediÄŸini bilmiyorum
		//            int[] loc= new int[] {2, 10}; 
		//            setLoc(loc);
		//            setInJail(true);
		//        }


	}

	/**
	 * Sets land with given name as an argument as not mortgaged
	 * @requires player has the land named as given input
	 * @modifies player's money by mortgaged price and land's mortgaged instance
	 */
	public void unmortgageLand(String str){

		LandCards landCard = findLandFromName(str);
		if(landCard != null){ //If the object has found.
			//Now the card is seized; so has no longer an owner, it is not sold and it is removed from the card list of player.
			landCard.setMortgaged(false);
			giveMoney(landCard.getMortgagedPrice());

		}

		//    }else{ jail a giidp gitmediÄŸini bilmiyorum
		//            int[] loc= new int[] {2, 10}; 
		//            setLoc(loc);
		//            setInJail(true);
		//        }


	}

	/**
	 * Determine other players rather than this  and returns their names as a string array
	 * @returns a string array which consists of players name other than this
	 */
	public String[] getPlayersWithNamesAsArray(LinkedList<Player> players){


		List<String> newlist=new ArrayList<String>();

		for (int i = 0; i < players.size(); i++) {
			if(!players.get(i).equals(this))
				newlist.add( players.get(i).getName());
		}

		int x=newlist.size();
		String[] getLandsWithNames = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getLandsWithNames[i] = newlist.get(i);
		}

		return getLandsWithNames;
	}


	/**
	 * @returns true after removing land named as given input  from players cardList if input is found as name of a land and this land has no building on it or is not mortgaged.Otherwise returns false
	 * @modifies player's cardList by removing the land and player's money by half of the land price and land's landOwner instance as null and isSold instance as false
	 */
	public boolean sellLand(String str) {

		LandCards landCard = findLandFromName(str);
		if(landCard != null){ //if the object has found.
			if(!hasBuilding(landCard) && !landCard.isMortgaged()){
				takeMoney(landCard.getLandPrice() / 2); //When an land is sold back to bank, player returns half of the price.
				//Now the card is sold; so has no longer an owner. Now, it is not sold and it is removed from the card list of player.
				landCard.setLandOwner(null);
				landCard.setSold(false);
				cardsList.remove(landCard);
				return true;
			}
		}
		return false;
	}

	/**
	 *Put a building on a land named as given input
	 * @requires Player has Monopoly on land's color group that land named as given input string
	 * @modifies money of the player and the buildings hash map of the player
	 */
	public void PutBuildingToLand(String str) {

		Cards card = findLandFromName(str);
		int sum=0,sum1=0,sum2=0,sum3=0;

		List<LandCards> plist=new LinkedList<LandCards>();
		if(card instanceof LandCards){
			if(!(((LandCards) card).getLandColor().equals("cabCo") || ((LandCards) card).getLandColor().equals("railroad")
					|| ((LandCards) card).getLandColor().equals("utility") )){

				plist = findLandsOfColorGroup(((LandCards) card).getLandColor());

				for (int i = 0; i < plist.size(); i++) {
					sum1 += NumberofHouse(plist.get(i));
					sum2 += NumberofHotel(plist.get(i));
					sum3 += NumberofSkyscrapper(plist.get(i));
				}
				sum = sum1 + sum2*5 + sum3*6;
				sum = (int)(sum / plist.size());


				if(sum<4 && NumberofHouse((LandCards) card) == sum){
					putHouse((LandCards) card);
				}else if(sum == 4 && NumberofHouse((LandCards) card) == 4){
					putHotel((LandCards) card);
				}else if(sum == 5 && NumberofHotel((LandCards) card) == 1){
					putSkyscrapper((LandCards) card);
				}	
			}else if(((LandCards) card).getLandColor().equals("cabCo")){
				if(NumberofCapstand((LandCards) card)==0){
					putCapstand((LandCards) card);
				}
			}else if(((LandCards) card).getLandColor().equals("railroad")){
				if(NumberofTraindepot((LandCards) card)==0){
					putTraindepot((LandCards) card);
				}
			}
		}
	}
	/**
	 * Removes a building from land named as given input
	 * @modifies money of the player and buildings hash map of the player
	 */
	public void SellBuildingFromLand(String str) {


		Cards card = findLandFromName(str);
		int sum=0,sum1=0,sum2=0,sum3=0;
		List<LandCards> plist = new ArrayList<LandCards>();
		if(card instanceof LandCards){
			if(!(((LandCards) card).getLandColor().equals("cabCo") || ((LandCards) card).getLandColor().equals("railroad")
					|| ((LandCards) card).getLandColor().equals("utility") )){

				plist = findLandsOfColorGroup(((LandCards) card).getLandColor());

				for (int i = 0; i < plist.size(); i++) {
					sum1 += NumberofHouse(plist.get(i));
					sum2 += NumberofHotel(plist.get(i));
					sum3 += NumberofSkyscrapper(plist.get(i));
				}
				sum = sum1 + sum2*5 + sum3*6;
				if(sum%plist.size()==0){
					sum = (int)(sum / plist.size());
				}else{
					sum = (int)(sum / plist.size()) +1;
				}

				if(sum<=4 && NumberofHouse((LandCards) card) == sum){
					sellHouse((LandCards) card);
				}else if(sum == 5 && NumberofHotel((LandCards) card) == 1){
					sellHotel((LandCards) card);
				}else if(sum == 6 && NumberofSkyscrapper((LandCards) card) == 1){
					sellSkyscrapper((LandCards) card);
				}	
			}else if(((LandCards) card).getLandColor().equals("cabCo")){
				if(NumberofCapstand((LandCards) card)==1){
					sellCapstand((LandCards) card);
				}
			}else if(((LandCards) card).getLandColor().equals("railroad")){
				if(NumberofTraindepot((LandCards) card)==1){
					sellTraindepot((LandCards) card);
				}
			}
		}
	}
	/**
	 *Remove a building from a land named as given input string
	 *@modifies Bank's numberOfHouses, numberOfHotels,numberOfSkyscraper instances and Player's buildings instance by number of houses destroyed or sky scraper and hotel
	 */
	public void destroyBuildingFromLands(String str) {


		Cards card = findLandFromName(str);
		List<LandCards> plist = new ArrayList<LandCards>();
		if(card instanceof LandCards){
			if(!(((LandCards) card).getLandColor().equals("cabCo") || ((LandCards) card).getLandColor().equals("railroad")
					|| ((LandCards) card).getLandColor().equals("utility") )){
				plist = findLandwithSpecificColor(((LandCards) card).getLandColor());

				for (int i = 0; i < plist.size(); i++) {
					if(NumberofHouse(plist.get(i)) < 4 && NumberofHouse(plist.get(i)) > 0){
						buildings.get(plist.get(i))[0]--;
						Bank.setNumberOfHouses(Bank.getNumberOfHouses()+1);
					}else if(NumberofHotel(plist.get(i)) > 0){
						buildings.get(plist.get(i))[1]--;
						buildings.get(plist.get(i))[0] += 4;
						Bank.setNumberOfHouses(Bank.getNumberOfHouses()-4);
						Bank.setNumberOfHotels(Bank.getNumberOfHotels()+1);
					}else if(NumberofSkyscrapper(plist.get(i)) > 0){
						buildings.get(plist.get(i))[2]--;
						buildings.get(plist.get(i))[1]++;
						Bank.setNumberOfSkyscraper(Bank.getNumberOfSkyscraper()+1);
						Bank.setNumberOfHotels(Bank.getNumberOfHotels()-1);
					}
				}
			}
		}
	}
	/**
	 * Find the specific color group lands from the player's owned lands and returns them as a list.
	 * @returns a list consisting landCards object in given input color from player's owned lands, returns null if player does not have land on given color
	 */
	public List<LandCards> findLandsOfColorGroup(String Color){

		List<LandCards> correspondingCardToColor = new ArrayList<LandCards>();
		LinkedList<LandCards> ownedLands = getOwnedLands();
		for (LandCards landCard : ownedLands) {
			if(landCard instanceof LandCards){
				if(!(((LandCards) landCard).getLandColor().equals("cabCo") || ((LandCards) landCard).getLandColor().equals("railroad")
						|| ((LandCards) landCard).getLandColor().equals("utility") )){
					if(((LandCards) landCard).getLandColor().equals(Color)){ //Object is found.
						correspondingCardToColor.add(landCard);
					}
				}
			}
		}
		return correspondingCardToColor;
		//Returns null if object couldn't find, o.w/ returns object.
	}



	/**
	 *Find the land named as given input string from the Player's owned lands and returns it as LandCards object
	 *@returns LandCards object named as given input if it is owned by player, otherwise returns null
	 */
	public LandCards findLandFromName(String str){

		LandCards correspondingCardToName = null;
		LinkedList<LandCards> ownedLands = getOwnedLands();
		for (LandCards landCard : ownedLands) {
			if(landCard.getName().equals(str)){ //Object is found.
				correspondingCardToName = landCard;
				break; //Don't have to look for other objects, since there is no copy of cards.
			}
		}
		return correspondingCardToName; //Returns null if object couldn't find, o.w/ returns object.
	}
	
	public Cards findCardFromName(String str){

		Cards correspondingCardToName = null;
		LinkedList<Cards> cards = getCardList();
		for (Cards cardo : cards) {
			if(cardo.getName().equals(str)){ //Object is found.
				correspondingCardToName = (Cards) cardo;
				break; //Don't have to look for other objects, since there is no copy of cards.
			}
		}
		return correspondingCardToName; //Returns null if object couldn't find, o.w/ returns object.
	} 
	
	/**
	 *Removes a player from the game
	 *@modifies GameFlow's community cards deck,player's cardList instance,land cards' isSold instance as false and landOwner instance as null which player has before removal
	 */
	public void bankrupcy(){

		LinkedList<Cards> playerCardList = getCardList();
		for (int i=0;i< playerCardList.size();i++){
			Cards card = playerCardList.get(i);
			if(card instanceof LandCards){
				((LandCards) card).setSold(false);
				((LandCards) card).setLandOwner(null);
				playerCardList.remove(card);
			}else if(card instanceof CommunityCards){
				GameFlow.getCommunityCardsDeck().add((CommunityCards) card);
				playerCardList.remove(card);
			}
		}
		setName(null);
	}
	/**
	 *Removes one house from land named as given input
	 *@requires land named as given input string has at least one house on it.
	 *@modifies player's buildings hash map by decreasing given key's integer array's first element by one and Bank's numberOfHouses instance by one
	 */
	public void decreaseHouseNumber(String str){


		LandCards land=findLandFromName(str);
		buildings.get(land)[0]--;
		Bank.setNumberOfHouses(Bank.getNumberOfHouses()+1);

	}

	/**
	 *Find cards from the player's cardList which can be kept and are instance of either ChanceCards or CommunityCards returns them
	 *@returns a string array which consists of specific community and chance cards from player's cardList
	 */
	public String[] getUsableCards(){


		List<String> usableCards = new LinkedList<String>(); //Can't bigger than card list.
		for (int i = 0; i < cardsList.size(); i++) {
			Cards card = cardsList.get(i);
			if(card instanceof ChanceCards){
				if(((ChanceCards) card).getCardType().equals("zeroDollarsDown")){
					usableCards.add("zeroDollarsDown");
				}else if(((ChanceCards) card).getCardType().equals("foreclosedPropertySale")){
					usableCards.add("foreclosedPropertySale");
				}else if(((ChanceCards) card).getCardType().equals("excellentAccounting")){
					usableCards.add("excellentAccounting");
				}
			}else if(card instanceof CommunityCards){
				if(((CommunityCards) card).getCardType().equals("ReserveRent")){
					usableCards.add("ReserveRent");
				}
			}

		}
		String[] usableCardsOptions=new String[usableCards.size()];
		for(int i=0; i<usableCards.size(); i++){
			usableCardsOptions[i]=usableCards.get(i);
		}

		return usableCardsOptions;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", money=" + money + ", loc=" + Arrays.toString(loc) + ", cardsList="
				+ cardsList + ", renovationSuccess=" + renovationSuccess + ", specialOnlinePricing="
				+ specialOnlinePricing + ", reserveRent=" + reserveRent + ", inJail=" + inJail + ", jailRound="
				+ jailRound + ", hasCompedRoom=" + hasCompedRoom + ", drawedAdvancingUtilityCard="
				+ drawedAdvancingUtilityCard + ", color=" + color + ", cardsWithColor=" + cardsWithColor
				+ ", buildings=" + buildings + "]";
	}
}
