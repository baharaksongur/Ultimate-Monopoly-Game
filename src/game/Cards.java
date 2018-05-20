package game;

/**
 * Main class for all the cards. All type of cards are 
 * inherited from this class.
 */
public class Cards {
	
	private String CardName;
	
	/**
	 * @param name	Name of the card.
	 */
	public Cards(String name) {		
		CardName = name;		
	}	

	public String getName() {		
		return CardName;		
	}
	
	
}