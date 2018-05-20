package game;
import java.util.Random;

/**

 * The abstract speed die class for creating a new die with some specific facing values

 */

/**

 * initializes different facing values such as numbers 1, 2, 3, two MR.Monopoly and one bus.

 */

public class SpeedDie {
	private String[] facingValues={"1","2","3","MrMonopoly","MrMonopoly","Bus"}; 
	private String facedValue;

	public SpeedDie(){

	}

	/**

	 * Rolls a speed die

	 * @requires facing values can not be null or empty

	 * @return a random facing value between elements of facingValues array.

	 */

	public void rollSpeedDie(){
		String random = (facingValues[new Random().nextInt(facingValues.length)]);
		facedValue=random;
	}

	public String getFacedValue(){
		return facedValue;
	}

	public void setFacedValue(String facedValue) {
		this.facedValue = facedValue;
	}

	public boolean isMrMonopoly(){
		if(facedValue.equalsIgnoreCase("MrMonopoly")){
			return true;
		}else{
			return false;
		}
	}

	public boolean isBus(){
		if(facedValue.equalsIgnoreCase("Bus")){
			return true;
		}else{
			return false;

		}
	}
	
	public boolean repOk(){
		for(int i=0; i<this.facingValues.length; i++){
			if(!this.facedValue.equals(this.facingValues[i])){
				return false;				
			}
		}
		return true;
	}
}


