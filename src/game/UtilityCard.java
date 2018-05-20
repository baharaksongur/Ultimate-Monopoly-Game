package game;

/**

 * The abstract UtilityCard class for representing the cards of only utilities with some specific instances. 
 * Instances of utility card is getting from txt document. The class extends to LandCards class.

 */

/**

 * @param CardName 				The name of the Utility
 * @param landPrice				The price of the Utility for buying
 * @param rent					The rent of the Utility

 */

public class UtilityCard extends LandCards {	
	
	public UtilityCard(String CardName, int landPrice, int rent) {
		super(CardName, CardName, landPrice, rent, rent, rent, rent, rent, rent, rent, rent, rent);
	}

}
