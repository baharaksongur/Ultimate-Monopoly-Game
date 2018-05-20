package game;

/**

 * The abstract regular dice class for generating two random values between 1 and 6.

 */

/**

 * initializes dice two elements array for facing values of dice.

 */

public class RegularDice { 

	public int[] dices; 


	public RegularDice(){ 
		dices = new int[2];
	} 

	public int getDice1(){
		return dices[0];
	} 

	public int getDice2(){
		return dices[1]; 
	} 

	/**

	 * Adds two facing values of a dice

	 * @requires dice can not be null

	 * @return sum of facing values of the dice 

	 */

	public int getSum(){
		int sum=0;
		for(int i=0; i<dices.length; i++){
			sum+=dices[i];
		}
		return sum;
	}

	public void setDice1(int dice1) {
		dices[0] = dice1;
	}

	public void setDice2(int dice2) {
		dices[1] = dice2;
	}

	/**

	 * Rolls dice

	 * @requires dice can not be null

	 * @modifies the dices array with random two values between 1 and 6

	 */

	public void roll(){ 		
		for(int i=0; i<2; i++){ 
			dices[i]=(int)(Math.random()*6)+1; 
		} 
	}

	/**

	 * Rolls a die

	 * @return a facing value of a die between 1 and 6

	 */

	public static int rollOneDice(){
		int facingValue = (int)(Math.random()*6) + 1; 
		return facingValue;
	}

	//For the case that Speed die comes with bus face.
	public String[] getRolledValues(){
		String[] rolledValues = new String[3]; //Can't bigger.
		rolledValues[0]= Integer.toString(getDice1());
		rolledValues[1]=Integer.toString(getDice2());
		rolledValues[2]=Integer.toString(getSum());
		return rolledValues;
	}

	public boolean repOk(){

		if(this.getDice1()>6 || this.getDice1()<1 ||this.getDice2()>6 || this.getDice2()<1){
			return false;
		}else if(this.getSum()<2 || this.getSum()>12){
			return false;
		}
		return true;
	}

}

