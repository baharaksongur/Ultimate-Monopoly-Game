package game;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;



//The CommunityCards class extends (is a subclass of) Cards class.



public class CommunityCards extends Cards {

    private String cardType;


    public CommunityCards (String CardName, String cardType) {

        super(CardName);

        this.cardType=cardType;

    }



    public String getCardType(){

        return this.cardType;

    }



    public static void RecieveConsultancyFee(Player player){ //Collect $25 from the bank.
        player.takeMoney(25);
    }



    public static void BargainBusiness(Player player, CommunityCards bargainCard){ //When you land on an unowned propert you want, 

        //buy it for only $100. Keep until needed.

        player.addCard(bargainCard);

    }



    public static void RenovationSuccess(Player player){ //Collect $50 extra rent from the next player
        //who lands on any of your properties.
        player.setRenovationSuccess(true);

    }



    public static void TakeInheritedHundred(Player player){ //Collect $100 from the bank.
        player.takeMoney(100);
    }



    public static void Birthday(Player player, List<Player> players){ //Collect $10 from each player.

        for (int i=0; i<players.size(); i++){
            if ( players.get(i) != player){
                Player.trade(players.get(i), player, 10);
            }
        }
        player.setLoc(new int[] {1,51});
    }



    public static void DoctorFee(Player player){ //pay $50 to pool for doctor
        GameFlow.payToPool(50,player);
        JOptionPane.showMessageDialog(null, "pool has"+ GameFlow.getPool() +" rent.");
    }

    //
    //    public static void MovingExperience(Player player){ 
    //        //Go to any transportation property what player wants
    //        //move to any transportation, ask for it
    //     // tanımlamam için gereken seyler var onlar tanımlanıca halledicem
    //    }


    public static void ChangingLanes1(Player player1){ //Move directly to the space is 1 Track below this
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


    public static void ChangingLanes2(Player player1){ //Move directly to the space is 1 Track above this
        int[] loc1 =new int[]{1,2}; 
        int[] loc2 =new int[]{1,24}; 
        int[] loc3 =new int[]{1,36}; 
        int[] loc4 =new int[]{1,48};
        int[] loc5 =new int[]{2,2}; 
        int[] loc6 =new int[]{2,17}; 
        int[] loc7 =new int[]{2,33}; 

        if(Arrays.equals(player1.getLoc(), loc1)){
            player1.setLoc(new int[] {2,0});
        }else if(Arrays.equals(player1.getLoc(), loc2)){
            player1.setLoc(new int[] {2,18});
        }else if(Arrays.equals(player1.getLoc(), loc3)){
            player1.setLoc(new int[] {2,26});
        }else if(Arrays.equals(player1.getLoc(), loc4)){
            player1.setLoc(new int[] {2,32});
        }else if(Arrays.equals(player1.getLoc(), loc5)){
            player1.setLoc(new int[] {3,0});
        }else if(Arrays.equals(player1.getLoc(), loc6)){
            player1.setLoc(new int[] {3,11});
        }else if(Arrays.equals(player1.getLoc(), loc7)){
            player1.setLoc(new int[] {3,19});
        }

    }


    public static void SpecialOnlinePricing(Player player){ 
        player.setSpecialOnlinePricing(true);
    }


    public static void ReserveRent(Player player){ 
        player.setReserveRent(true);
    }


    public static void StreetRepair(Player player){ 

        int houseNum=0;
        int hotelNum=0;
        int skyscraperNum=0;
        int cabStandNum=0;
        int railroadNum = 0;

        for(Cards lcards: player.getCardList()){
            if (lcards instanceof LandCards){
                houseNum += player.getBuildings().get((LandCards) lcards)[0];
                hotelNum += player.getBuildings().get((LandCards) lcards)[1];
                skyscraperNum += player.getBuildings().get((LandCards) lcards)[2];
                cabStandNum += player.getBuildings().get((LandCards) lcards)[3];
                if(((LandCards) lcards).getLandColor()=="railroad"){
                    railroadNum = railroadNum+1;
                }

                //we need to repair transit station as well but do we own them??
            }
        }
        GameFlow.payToPool(cabStandNum*25 + houseNum*40 + hotelNum*115 + skyscraperNum*100 + railroadNum*25 ,player);
        JOptionPane.showMessageDialog(null, "pool has"+ GameFlow.getPool() +" rent.");
    }
    
    public static void GenerealRepair(Player player){ 

        int houseNum=0;
        int hotelNum=0;
        int skyscraperNum=0;
        int cabStandNum=0;

        for(Cards lcards: player.getCardList()){
            if (lcards instanceof LandCards){
                houseNum += player.getBuildings().get((LandCards) lcards)[0];
                hotelNum += player.getBuildings().get((LandCards) lcards)[1];
                skyscraperNum += player.getBuildings().get((LandCards) lcards)[2];
                cabStandNum += player.getBuildings().get((LandCards) lcards)[3];

            }
        }
        GameFlow.payToPool((cabStandNum + houseNum) *25 + hotelNum*100 + skyscraperNum*100 + 100 ,player);
        JOptionPane.showMessageDialog(null, "pool has"+ GameFlow.getPool() +" rent.");
    }




    public static void GetOutofJailFree(Player player, CommunityCards jailCard){ 
        player.addCard(jailCard);
    }


    public static void GoToJail(Player player){ 
        int[] loc = new int[] {2,10};
        player.setLoc(loc);
        player.setInJail(true);
    }


    public static void AdvanceStockExchange(Player player){ 
        //go to stock exchange loc. if u pass pay day u will collect 300 dolar!

        int[] firstLoc = player.getLoc();
        int[] payDayLoc = new int[] {1, 28}; // pay day loc
        int[] stockExchangeLoc = new int[] {3, 12}; // stock exchange loc 

        player.setLoc(stockExchangeLoc); // Advance to Pay Day loc    
        if(firstLoc[0]==1 && firstLoc[1] < payDayLoc[1] ) // If you pass Pay day, 
            player.takeMoney(300);    // Collect $300 from the bank.
    }

    public static void VehicleImpounded(Player player){ 
    	GameFlow.payToPool(50, player);
     	int[] loc = new int[] {2,10};
        player.setLoc(loc);
        player.setLoseTurn(true);
    }
    

}