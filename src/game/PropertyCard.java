package game;

/**

 * The abstract PropertyCard class for representing the cards of only properties with some specific instances. 
 * Instances of property card is getting from txt document. The class extends to LandCards class.

 */

/**

 * @param CardName 				The name of the Property
 * @param landPrice				The price of the Property for buying
 * @param landColor				The color of the Property
 * @param amountOfSameCards		The number of Properties with same color   
 * @param rent					The rent of the Property

 */

public class PropertyCard extends LandCards {
	private String landColor;
	
	public PropertyCard(String CardName, int landPrice,
			String landColor, int amountOfSameCards, int rent) {
		super(CardName, landColor, landPrice, rent, rent, rent, rent, rent, rent, rent, rent, rent);
		setLandColor(landColor);
	}

	public String getLandColor() {
		return landColor;
	}

	public void setLandColor(String landColor) {
		this.landColor = landColor;
	}
}
