package game;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The abstract ChanceCards class ,which is a subclass of Cards class, for representing the Chance Cards' attributes when players draw them.
 *
 */

public class ChanceCards extends Cards {

    private String cardType;
    private boolean keepable;
    /**
     * The default constructor of the ChanceCards class. 

     * @param CardName       The card name of the Chance Cards which is same as its super class Cards
     * @param cardType       The card type of the Chance Cards

     */

    public ChanceCards (String CardName,String cardType, boolean keepable) {
        super(CardName);
        this.cardType=cardType;  
        this.keepable = keepable;
    }

    public String getCardType(){    
        return this.cardType;    
    }
    
    public boolean isKeepable(){
    	return this.keepable;
    }

    /**
     * Taking player to the St. Charles Place on the monopoly board
     * @requires player needs to be not null
     * @modifies the player's location 
     */

    public static void GoToStCharlesPlace(Player player){
        int[] newLoc = new int[] {2, 11};
        player.setLoc(newLoc);  // Advance to St. Charles Place    
    }

    /**
     * Taking player to the SqueezePlay location on the monopoly board and if the player passes 'GO' location, player takes $200 from the bank.
     * @requires player needs to be not null
     * @modifies the player's location and player's money according to the condition.
     */

    public static void GoToSqueezePlay(Player player){
        int[] firstLoc = player.getLoc();
        int[] newLoc = new int[] {3, 0};
        player.setLoc(newLoc); // Advance to Squeeze Play
        int[] finalLoc = player.getLoc(); //It is 15.        


        if(firstLoc[1] > finalLoc[1]) // If you pass Go, 
            player.takeMoney(200);    // Collect $200 from the bank.

    }

    /**
     * Player takes $50 from the other players
     * @requires player needs to be not null
     * @modifies all players' money 
     */

    public static void SelectedChairPerson(Player cardOwnerPlayer,List<Player> players){   //You are selected as the Chairperson.
        for (int i=0; i<players.size(); i++){
            if ( players.get(i) != cardOwnerPlayer){                    //Pay each player $50.
                Player.trade(cardOwnerPlayer,  players.get(i), 50);
            }
        }
    }
    /**
     * Taking player to the Go location on the monopoly board and player takes $200 from the bank
     * @requires player needs to be not null
     * @modifies the player's location and player's money 
     */

    public static void GoToGo(Player player){   //Advance to Go, collect $200.
        int[] newLoc = new int[] {2,0};
        player.setLoc(newLoc);
        player.takeMoney(200);
    }

    /**
     * Taking player to the nearest Utility place on the monopoly board
     * @requires player needs to be not null
     * @modifies the player's location 
     */
    
    public static void advanceToNearestUtility(Player player){
        int[] loc = player.getLoc();

        loc[1]++;
        LandCards land=GameFlow.getLandObject(loc);

        while(!(land.getLandColor().equals("utility"))){
            loc[1]++;
            land=GameFlow.getLandObject(loc);
        }
        player.setLoc(loc);
        player.setDrawedAdvancingUtilityCard(true); 
    }
    
    /**
     * Taking player to the In Jail location on the monopoly board
     * @requires player needs to be not null
     * @modifies the player's location 
     */

    public static void goToJail(Player player){ 

        int[] loc = new int[] {2,10};
        player.setLoc(loc);
        player.setInJail(true);
    }
    
    /**
     * Taking player to the nearest Railroad place on the monopoly board
     * @requires player needs to be not null
     * @modifies the player's location 
     */
    public static void advanceToNearestRailRoad(Player player){ 

        int[] loc = player.getLoc();

        loc[1]++;
        LandCards land=GameFlow.getLandObject(loc);

        while(!(land.getLandColor().equals("railroad"))){
            loc[1]++;
            land=GameFlow.getLandObject(loc);
        }
        player.setLoc(loc);
    }
    /**
     * Player gives money to pool by amount of money depends on how many building that it has
     * @requires player needs to be not null
     * @modifies the player's money according to the conditions
     */
    public static void makeGeneralRepairs(Player player){
        int houseNum=0;
        int hotelNum= 0;
        int skyscrapperNum=0;
        int CabStandNum = 0;
        int railroadNum = 0;
        for(Cards lcard: player.getCardList()){
            if (lcard instanceof LandCards){

                houseNum += player.getBuildings().get((LandCards) lcard)[0];
                hotelNum += player.getBuildings().get((LandCards) lcard)[1];
                skyscrapperNum += player.getBuildings().get((LandCards) lcard)[2];
                CabStandNum += player.getBuildings().get((LandCards) lcard)[3];
                if(((LandCards) lcard).getLandColor()=="railroad"){
                    railroadNum = railroadNum+1;
                }

                //we need to repair transit station as well but do we own them??
            }

        }
        GameFlow.payToPool((houseNum + CabStandNum)*25 + (hotelNum + skyscrapperNum)* 100 + railroadNum*25 ,player );

    }
    /**
     * Player adds this card on its card list to use, sold or trade this card later
     * @requires player needs to be not null
     * @modifies the card list of the player
     */

    public static void getOutoJailFree(Player player, ChanceCards GetOutoJail ){

        player.addCard(GetOutoJail);
        //CAN BE TRADED OR SOLD
    }
    /**
     * Taking player three squares back on the monopoly board
     * @requires player needs to be not null
     * @modifies the player's location 
     */

    public static void goBackThreeSpaces(Player player){
        int[] x = player.getLoc();
        x[1] = x[1] - 3;
        player.setLoc(x);

    }
    /**
     * Player pays $50 to pool
     * @requires player needs to be not null
     * @modifies the player's money
     */

    public static void schoolFees(Player player){
        GameFlow.payToPool(50, player);
    }
    /**
     * Taking player to the Stock Exchange place on the monopoly board and player takes $300 from the bank if it passes from Pay Day location on the monopoly board
     * @requires player needs to be not null
     * @modifies the player's location and player's money according to the conditions
     */

    public static void advanceToTheStockExchange(Player player){
        //go to stock exchange loc. if u pass pay day u will collect 300 dolar!

        int[] firstLoc = player.getLoc();
        int[] stockExchangeLoc = new int[] {3, 12}; // stock exchange loc 

        player.setLoc(stockExchangeLoc); // Advance to Pay Day loc    


        if(firstLoc[0]==1 && (10 == firstLoc[1] || firstLoc[1] == 21) ) // If you pass Pay day, 
            player.takeMoney(300);    // Collect $300 from the bank.

    }
    /**
     * Player takes $150 from the bank
     * @requires player needs to be not null
     * @modifies the player's money
     */

    public static void loanMatures(Player player){
        player.takeMoney(150);

    }
    /**
     * Taking player to the nearest unoccupied property location on the monopoly board
     * @requires player needs to be not null
     * @modifies the player's location 
     */

    public static void occupy(Player player){
        int[] loc=player.getLoc();
        int location = loc[1];
        location++;

        LandCards land=GameFlow.getLandObject(loc);
        while(land instanceof PropertyCard){

            location++;
            land=GameFlow.getLandObject(loc);
        }
        player.setLoc(loc);


    }
    
    /**
     * Player adds this card on its card list to use this card later
     * @requires player needs to be not null
     * @modifies the card list of the player
     */
    public static void foreclosedPropertySale(Player player, ChanceCards foreclosedPropertySale){

        player.addCard(foreclosedPropertySale);

    }
    /**
     * Player adds this card on its card list to use this card later
     * @requires player needs to be not null
     * @modifies the card list of the player
     */
    public static void  excellentAccounting(Player player, ChanceCards excellentAccounting){
    
        player.addCard(excellentAccounting);
        //usecard button
    }
    /**
     * Taking player to the Roll Once location on the monopoly board
     * @modifies the player's location 
     */

    public static void getRollin(Player player){

        int[] loc =new int[]{2,30}; 
        player.setLoc(loc);
    }

    /**
     * Player pays the amount of money changing how many morgaged property it has to pool
     * @requires player needs to be not null
     * @modifies the player's money according to the condition 
     */


    public static void propertyTaxes(Player player){
        int x=0;

        for(Cards lcards: player.getCardList()){
            if (lcards instanceof LandCards){
                if(!((LandCards) lcards).isMortgaged()){
                    x++;
                }
            }
        }
        GameFlow.payToPool(x*25, player);
    }
    /**
     * Taking player to the Black&White Cap Company location on the monopoly board
     * @requires player needs to be not null
     * @modifies the player's location 
     */

    public static void whistle(Player player){
        int[] loc =new int[]{1,22}; 
        player.setLoc(loc);
    }
    /**
     * Player gives $50 to other players
     * @requires player needs to be not null
     * @modifies all players' money 
     */
    public static void socialMediaFail(Player cardOwnerPlayer,LinkedList<Player> players){  
        for (int i=0; i<players.size(); i++){
            if ( players.get(i) != cardOwnerPlayer){                    //Pay each player $50.
                Player.trade(cardOwnerPlayer,  players.get(i), 50);
            }
        }
    }
    /**
     * Player takes amount of money changing which Pay Corner it passes from the bank
     * @requires player needs to be not null
     * @modifies players money according to the conditions
     */

    public static void winTheMarathon(Player player){
        int[] loc=player.getLoc();
        int track = loc[0];
        if(track==1){
            player.takeMoney(300);
        }else if(track==2){
            player.takeMoney(200);
        }else if (track ==3){
            player.takeMoney(250);
        }

    }
    /**
     * Taking all players to Mardi Gras location on the monopoly board if they are not in jail
     * @requires player needs to be not null
     * @modifies all players' location
     */

    public static void mardiGras(LinkedList<Player> players){
        int[] loc =new int[] {1,9}; 
        for (int i=0; i<players.size(); i++){
            Player player = players.get(i);
            if(!player.isInJail())
                player.setLoc(loc);
        }
    }

    public static void taxiWarsAreNotFare(){
        //will do in Monopoly: going any Cap company
    }
    /**
     * Player adds this card on its card list to use this card later
     * @requires player needs to be not null
     * @modifies the card list of the player
     */

    public static void compedRoom(Player player, ChanceCards CompedRoom){
        player.getCardList().add(CompedRoom);
        player.setHasCompedRoom(true);
    }
    /**
     * Taking player directly to the space that is one track below current one
     * @requires player needs to be not null
     * @modifies player's location according to the condition
     */
    public static void changingLanes1(Player player1){
        //move directly to the space that is 1 Track below this one. If you are on the Outer Track, do nothing
        int[] loc1 =new int[]{3,4}; 
        int[] loc2 =new int[]{2,2}; 
        int[] loc3 =new int[]{2,17};  
        int[] loc4 =new int[]{2,33}; 

        if(Arrays.equals(player1.getLoc(), loc1)){
            player1.setLoc(new int[] {2,6});
        }else if(Arrays.equals(player1.getLoc(), loc2)){
            player1.setLoc(new int[] {1,4});
        }else if(Arrays.equals(player1.getLoc(), loc3)){
            player1.setLoc(new int[] {1,23});
        }else if(Arrays.equals(player1.getLoc(), loc4)){
            player1.setLoc(new int[] {1,47});
        }
    }
    /**
     * Taking player directly to the space that is one track above current one
     * @requires player needs to be not null
     * @modifies player's location according to the condition
     */

    public static void changingLanes2(Player player1){
        //move directly to the space that is 1 Track above this one. If you are on the Outer Track, do nothing
        int[] loc1 =new int[]{1,10}; 
        int[] loc2 =new int[]{1,21}; 
        int[] loc3 =new int[]{1,30}; 
        int[] loc4 =new int[]{1,54};
        int[] loc5 =new int[]{2,7}; 
        int[] loc6 =new int[]{2,22}; 
        int[] loc7 =new int[]{2,36}; 

        if(Arrays.equals(player1.getLoc(), loc1)){
            player1.setLoc(new int[] {2,8});
        }else if(Arrays.equals(player1.getLoc(), loc2)){
            player1.setLoc(new int[] {2,15});
        }else if(Arrays.equals(player1.getLoc(), loc3)){
            player1.setLoc(new int[] {2,20});
        }else if(Arrays.equals(player1.getLoc(), loc4)){
            player1.setLoc(new int[] {2,0});
        }else if(Arrays.equals(player1.getLoc(), loc5)){
            player1.setLoc(new int[] {3,5});
        }else if(Arrays.equals(player1.getLoc(), loc6)){
            player1.setLoc(new int[] {3,12});
        }else if(Arrays.equals(player1.getLoc(), loc7)){
            player1.setLoc(new int[] {3,22});
        }
    }
    

    public static void takingBusTicket(Player player, ChanceCards busTicket){

		player.addCard(busTicket);
		//if u use this card, all other chance cards expire.
		//move forward to any space


	}
	public static void advanceToThePayCorner(Player player){
		int[] loc=player.getLoc();
		int[] bonusLoc=new int[] {3,6};
		int[] goLoc=new int[] {2,0};
		int[] payDayLoc=new int[] {1,28};

		int track = loc[0];
		if(track==1){
			player.takeMoney(400);
			player.setLoc(payDayLoc);
		}else if(track==2){
			player.takeMoney(200);
			player.setLoc(goLoc);
		}else if (track ==3){
			player.takeMoney(300);
			player.setLoc(bonusLoc);
		}
	}
	
	public static void payingforTrafficTicket(Player player){
		GameFlow.payToPool(15, player);
	}
	public static void havingEntertainmentRocks(LinkedList<Player> players){
		for (int i=0; i<players.size(); i++){
			Player player = players.get(i);
			for (Stock stock : player.getNumberofSharedStocks().keySet()) {
				if(stock.getName().equals("MotionPictures") || stock.getName().equals("GenRadio")){
					int x = player.getNumberofSharedStocks().get(stock);
					int[] array = stock.getDividends();
					player.takeMoney(array[x-1]);
				}
			}
		}		
	}
}