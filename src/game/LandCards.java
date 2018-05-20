package game;

/**

 * The abstract landCards class for representing the cards of lands with some specific instances. 
 * Instances of card is getting from txt document only the current rent is changing during game.

 */

public class LandCards extends Cards {
	private int landPrice; 
	private Player landOwner;
	private boolean isSold;
	private int rent0;
	private int rent1;
	private int rent2;
	private int rent3;
	private int rent4;
	private int rent5;
	private int rent6;
	private int mortgagedPrice;
	private int buildingPrice;
	private boolean isMortgaged;
	private int currentRent;
	private String color;
	private boolean auction;

	/**

	 * The default constructor of the landCards class. 
	 * sets currentRent to rent0 which means the rent without any building or any majority.
	 * sets landPrice and color information taking from the txt document. 
	 * sets landOwner null for all lands initially.
	 * sets isSold false for all lands initially.
	 * sets all rents information taking from the txt document for each condition.
	 * sets mortgagedPrice and building price information taking from the txt document.
	 * sets isMortgaged false for all lands initially.
	 * 
	 * 
	 * @param CardName 			The name of the land
	 * @param color				The color of the land
	 * @param landPrice			The price of the land for buying
	 * @param rent0				The rent of the land without any buildings and majorities
	 * @param rent1				The rent of the land with 1 house
	 * @param rent2				The rent of the land with 2 houses
	 * @param rent3				The rent of the land with 3 houses
	 * @param rent4				The rent of the land with 4 houses
	 * @param rent5				The rent of the land with hotel
	 * @param rent6				The rent of the land with skyscraper
	 * @param buildingPrice		The price for putting a building on the land
	 * @param mortgagePrice     The price that you will take when mortgage the land

	 */

	public LandCards (String CardName, String color, int landPrice, int rent0, int rent1, int rent2,
			int rent3, int rent4, int rent5, int rent6, int buildingPrice,int mortgagedPrice) {
		super(CardName);
		currentRent = rent0;
		this.landPrice = landPrice;
		this.color=color;
		this.landOwner = null;
		this.isSold = false;
		this.rent0 = rent0;
		this.rent1 = rent1;
		this.rent2 = rent2;
		this.rent3 = rent3;
		this.rent4 = rent4;
		this.rent5 = rent5;
		this.rent6 = rent6;
		this.mortgagedPrice=mortgagedPrice;
		this.buildingPrice=buildingPrice;
		this.isMortgaged = false;
		this.auction=false;
	}
	public boolean getAuction(){
		return auction;
	}
	public void setAuction(boolean bool){
		auction=bool;
	}
	public static int[] getLoc(LandCards card){
		//Finding the location based on its name, searching through all the tracks.
		String searchedName = card.getName();
		for (int track = 1; track < 4; track++) {
			int trackLength = GameFlow.trackLength(track);
			for (int loc = 0; loc < trackLength; loc++) {
				int[] location = new int[] {track, loc};
				String name = GameFlow.getLandName(location);
				if(searchedName.equals(name))
					return location;
			}
		}
		//If it reaches here, there is no such a land with this name (searchedName).
		//Returns null in this case.
		return null;
	}

	public String getLandColor() {
		return color;
	}

	public void setLandColor(String color) {
		this.color = color;
	}


	public int getRent0() {
		return rent0;
	}

	public void setRent0(int rent0) {
		this.rent0 = rent0;
	}

	public int getRent1() {
		return rent1;
	}

	public void setRent1(int rent1) {
		this.rent1 = rent1;
	}

	public int getRent2() {
		return rent2;
	}

	public void setRent2(int rent2) {
		this.rent2 = rent2;
	}

	public int getRent3() {
		return rent3;
	}

	public void setRent3(int rent3) {
		this.rent3 = rent3;
	}

	public int getRent4() {
		return rent4;
	}

	public void setRent4(int rent4) {
		this.rent4 = rent4;
	}

	public int getRent5() {
		return rent5;
	}

	public void setRent5(int rent5) {
		this.rent5 = rent5;
	}

	public int getRent6() {
		return rent6;
	}

	public void setRent6(int rent6) {
		this.rent6 = rent6;
	}

	public int getMortgagedPrice() {
		return mortgagedPrice;
	}

	public void setMortgagedPrice(int mortgagedPrice) {
		this.mortgagedPrice = mortgagedPrice;
	}

	public void setBuildingPrice(int buildingPrice) {
		this.buildingPrice = buildingPrice;
	}

	public boolean isMortgaged() {
		return isMortgaged;
	}

	public void setMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}

	public int getBuildingPrice(){
		return buildingPrice;

	}

	public int getCurrentRent(){
		return currentRent;

	}


	public void setCurrentRent(int currentRent) {
		this.currentRent = currentRent;
	}

	public int getLandPrice() {
		return landPrice;
	}

	public void setLandPrice(int landPrice) {
		this.landPrice = landPrice;
	}

	public Player getLandOwner() {
		return landOwner;   
	}

	public void setLandOwner(Player landOwner) {
		this.landOwner = landOwner;
	}

	public boolean isSold() {
		return isSold;
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}

	public boolean IsSold(){
		return isSold;
	}

	public boolean repOk(){
		if(this.buildingPrice<0|| this.color==null ||this.currentRent<0||this.landPrice<0||this.rent1<0||this.rent2<0
				||this.rent3<0||this.rent4<0||this.rent5<0||this.rent6<0){
			return false;
		}
		return true;
	}
}
