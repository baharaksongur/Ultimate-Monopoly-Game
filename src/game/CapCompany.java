package game;

/**
 * An abstract class for holding the information for cap companies.
 */
public class CapCompany extends LandCards {

/**
 * Construct a card with necessary informations for cab company.
 * 
 * @param CardName			Name of the cab company.
 * @param landPrice			Cost for the player to buy.
 * @param rent				Rent to be paid to this cap company's owner.
 * @param buildingPrice		Cost for the owner to put a cab stand.
 * @param mortgagedPrice	Amount of money losed when mortgaged or gained when unmortgaged.
 */
	public CapCompany(String CardName, int landPrice, int rent, int buildingPrice,int mortgagedPrice) {
		super( CardName, "cabCo",  landPrice , rent,  0,  0,
				 0,  0,  0,  0,  buildingPrice, mortgagedPrice);
	}

/**
 * @return returns an String array that holds the choices 
 * to offer the player, when player lands on a cap company.
 */
	public static String[] getTaxiRideChoices(){
		String[] taxiRideChoices= new String[2];
		taxiRideChoices[0]="Pay normal rent to owner";
		taxiRideChoices[1]="Pay $50 to owner";
		return taxiRideChoices;
	}

}
