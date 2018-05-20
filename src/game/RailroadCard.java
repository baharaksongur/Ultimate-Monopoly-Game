package game;

/**

 * The abstract RailroadCard class for representing the cards of only railroads with some specific instances. 
 * Instances of railroad card is getting from txt document. The class extends to LandCards class.

 */

/**

 * @param CardName 			The name of the Railroad
 * @param landPrice			The price of the Railroad for buying
 * @param rent				The rent of the Railroad

 */


public class RailroadCard extends LandCards {

	public RailroadCard(String CardName, int landPrice, int rent) {
		super(CardName, CardName, landPrice, rent, rent, rent, rent, rent, rent, rent, rent, rent);
	}

}
