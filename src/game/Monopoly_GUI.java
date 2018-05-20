package game;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.util.*;

import javax.swing.*;

public class Monopoly_GUI extends JFrame{

	private static final long serialVersionUID = 1L;

	//Current Player, player whose turn it is.
	private static Player currentPlayer; 

	//Dices.
	private RegularDice dice = new RegularDice();
	private SpeedDie speedDie= new SpeedDie();

	//Players array to be able to follow turns.
	List<Player> players = new LinkedList<Player>();
	Stock [] stockArray=new Stock[6];

	//Current index of players represented with p. Starts from player1, as its index is zero.
	private int p = 0;

	//Creating panels so we can add buttons to appropriate panel.
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();		
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel();


	//Buttons
	private JButton rollButton = new JButton("Roll Dice");
	private JButton endButton = new JButton("End Turn");
	private JButton moveButton = new JButton("Move");
	private JButton sellLands =new JButton("Sell a Land");
	private JButton putBuilding=new JButton("Put a building");
	private JButton sellBuilding=new JButton("Sell a building");
	private JButton sellStock=new JButton("Sell a stock");
	private JButton mortgage=new JButton("Mortgage a Land");
	private JButton MrMonopolyMove=new JButton("Mr.Monopoly move");
	private JButton unmortgage= new JButton("Unmorgage a Land");
	private JButton useCardButton= new JButton("Use Card");
	private JButton loadButton = new JButton("Load Game");
	private JButton saveButton = new JButton("Save Game");

	//Sum of dices and their facing values
	private int sumOfDices = 0;
	private int facingValue1 = 0; //First dice.
	private int facingValue2 = 0; //Second dice.
	private String speedDieFacedValue;

	//Tricky dices or not ? 
	private boolean trickyRegular = false;
	private boolean trickySpeed = false;

	//Board
	private Board board = new Board();

	//Labels
	private JLabel label;
	private JLabel currentPlayerLabel;
	private JLabel poolLabel;

	//Keeps how many times player doubled in case of going jail. Initially zero.
	private int doubleCount = 0; 

	//Table of players.
	private JTable dataTable;

	//Table of players assets.
	private JTable player1AssestTable;
	private JTable player2AssestTable;
	private JTable player3AssestTable;
	private JTable player4AssestTable;
	private JTable player5AssestTable;
	private JTable player6AssestTable;
	private JTable player7AssestTable;
	private JTable player8AssestTable;


	//Hole board represented as a map.
	private static HashMap<Integer, LinkedList<String[]>> squareMap = new HashMap<Integer, LinkedList<String[]>>();

	//Map of land objects. Holds the information for lands.
	private HashMap<Integer, LinkedList<LandCards>> landMap = new HashMap<Integer, LinkedList<LandCards>>();


	//Locations of special squares on the board as their indices in the list.
	private final static int[] GO_LOC =new int[] {2, 0}; 
	private final static int[] ROLL_ONCE_LOC= new int[] {2, 30}; 
	private final static int[] FREE_PARKING_LOC = new int[] {2,20}; //for taxi ride
	private final static int[] SQUEEZE_PLAY_LOC =  new int[] {3,0};

	private final static int[] BUS_TICKET_1 = new int[] {1, 5}; 
	private final static int[] BUS_TICKET_2 = new int[] {1, 48}; 

	private final static int[] AUCTION_LOC = new int[] {1, 15};
	private final static int[] SUBWAY_LOC = new int[] {1, 0};
	private final static int[] REVERSE_DIRECTION_LOC = new int[] {3, 22};

	private final static int[] BIRTHDAY_GIFT_LOC = new int[] {1, 51};
	private final static int[] STOCK_EXCHANGE_LOC = new int[] {3, 12};

	private final static int[] JAIL = new int[] {2, 10}; 
	private final static int[] GOTOJAIL = new int[] {1, 42}; 

	private final static int[] HOLLAND_TUNNEL_1_LOC = new int[] {1, 14}; 
	private final static int[] HOLLAND_TUNNEL_2_LOC = new int[] {3, 18}; 

	private final static int[] COMMUNITY_CHEST_1_LOC = new int[] {1, 2}; 
	private final static int[] COMMUNITY_CHEST_2_LOC = new int[] {1, 24}; 
	private final static int[] COMMUNITY_CHEST_3_LOC = new int[] {1, 36}; 
	private final static int[] COMMUNITY_CHEST_4_LOC = new int[] {1, 46}; 
	private final static int[] COMMUNITY_CHEST_5_LOC = new int[] {2, 2}; 
	private final static int[] COMMUNITY_CHEST_6_LOC = new int[] {2, 17}; 
	private final static int[] COMMUNITY_CHEST_7_LOC = new int[] {2, 33}; 
	private final static int[] COMMUNITY_CHEST_8_LOC = new int[] {3, 4}; 

	private final static int[] CHANCE_1_LOC = new int[] {1, 10}; 
	private final static int[] CHANCE_2_LOC = new int[] {1, 21}; 
	private final static int[] CHANCE_3_LOC = new int[] {1, 30}; 
	private final static int[] CHANCE_4_LOC = new int[] {1, 54}; 
	private final static int[] CHANCE_5_LOC = new int[] {2, 7}; 
	private final static int[] CHANCE_6_LOC = new int[] {2, 22};
	private final static int[] CHANCE_7_LOC = new int[] {2, 36};
	private final static int[] CHANCE_8_LOC = new int[] {3, 16}; 

	private final static int[] UTILITY_1_LOC = new int[] {1, 11}; 
	private final static int[] UTILITY_2_LOC = new int[] {1, 18}; 
	private final static int[] UTILITY_3_LOC = new int[] {1, 39}; 
	private final static int[] UTILITY_4_LOC = new int[] {1, 49}; 
	private final static int[] UTILITY_5_LOC = new int[] {2, 12}; 
	private final static int[] UTILITY_6_LOC = new int[] {2, 28};
	private final static int[] UTILITY_7_LOC = new int[] {3, 3};
	private final static int[] UTILITY_8_LOC = new int[] {3, 15};

	private final static int[] LUXURY_TAX_LOC = new int[] {2, 38}; 
	private final static int[] INCOME_TAX_LOC = new int[] {2, 4}; 
	private final static int[] TAX_REFUND_LOC = new int[] {3, 14}; 
	private final static int[] BONUS_LOC = new int[] {3,6}; 
	private final static int[] PAY_DAY= new int[] {1,28}; 

	private final static int[] TRANSIT_STATION1_ON_TRACK2=new int[] {2,5};//For taxi ride
	private final static int[] TRANSIT_STATION2_ON_TRACK2=new int[] {2,25};
	private final static int[] TRANSIT_STATION1_ON_TRACK3=new int[] {3,9};
	private final static int[] TRANSIT_STATION2_ON_TRACK3=new int[] {3,21};

	private final static int[] CHECKER_CAP_COMPANY =new int[] {1,6};
	private final static int[] YELLOW_CAP_COMPANY =new int[] {1,34};
	private final static int[] UTE_CAP_COMPANY =new int[] {1,50};
	private final static int[] BLACK_AND_WHITE_CAP_COMPANY =new int[] {1,22};

	private List<int[]> taxiRideTargetPlace = new LinkedList<int[]>();

	private final Container container;
	private JScrollPane player5ScrollPane;
	private JScrollPane player6ScrollPane;
	private JScrollPane player7ScrollPane;
	private JScrollPane player8ScrollPane;

	private String[] choices = new String[]{"TransitStation1","TransitStation2","TransitStation3","TransitStation4",
			"FreeParking","CheckerCabCompany","YellowCabCompany","UteCabCompany","BlackAndWhiteCabCompany"};


	//Community Cards List
	private List<CommunityCards> communityCardsDeck;

	//Chance Cards List
	private List<ChanceCards> chanceCardsDeck;

	//Roll Once Cards List
	private List<Cards> rollOnceCardsDeck;

	ArrayList<Integer> bids ;
	ArrayList<LandCards> lstForRent;

	private static LinkedList<LandCards> lst1;
	private static LinkedList<LandCards> lst2;
	private static LinkedList<LandCards> lst3;
	private static LinkedList<LandCards> cardsInTheBank;



	private Random randomGenerator;


	public Monopoly_GUI(){
		GameFlow gameflow = new GameFlow();
		//Making all the pre-game preparations here.
		landMap = GameFlow.getLandMap();
		squareMap = GameFlow.getSquareMap();

		bids =new ArrayList<Integer>();
		lstForRent=new ArrayList<LandCards>();

		randomGenerator=new Random();

		cardsInTheBank=new LinkedList<LandCards>();
		lst1=new LinkedList<LandCards>();
		lst2=new LinkedList<LandCards>();
		lst3=new LinkedList<LandCards>();

		//Instruction Set
		List<String[]> instructionSet = GameFlow.getInstructionSet();

		players = GameFlow.getPlayerList();
		stockArray =GameFlow.getStockArray();
		int playerCount = Integer.parseInt(instructionSet.get(0)[1]);
		for (int i = 0; i < playerCount; i++) {
			String[] playerLoc = instructionSet.get(i+1);
			int playerTrack = Integer.parseInt(playerLoc[3]);
			int playerLocation = Integer.parseInt(playerLoc[4]);
			int[] playerInf = new int[] {playerTrack, playerLocation}; 

			int playerX = Integer.parseInt(getSquareInf(playerInf)[1]);
			int playerY = Integer.parseInt(getSquareInf(playerInf)[2]);
			Player player = players.get(i);
			changeItemLoc(player, playerX, playerY);
		}		

		//Further instructions index is:
		int index = playerCount + 1;
		//Next instructions to GUI is setting dices.
		String[] instructionInf = instructionSet.get(index);
		//Checks if the dice is tricky or regular
		if(instructionInf.length > 1){
			if(!(instructionInf[1] == null || instructionInf[2] == null)){
				//Firstly the regular dice.
				facingValue1 = Integer.parseInt(instructionInf[1]);
				dice.setDice1(facingValue1);
				facingValue2 = Integer.parseInt(instructionInf[2]);
				dice.setDice2(facingValue2);
				trickyRegular = true;
			}
		}
		index++;

		instructionInf = instructionSet.get(index);
		if(instructionInf.length > 1){
			if(instructionInf[1] != null){
				speedDie.setFacedValue(instructionInf[1]);
				speedDieFacedValue = instructionInf[1];
				trickySpeed = true;
			}
		}

		//Adding taxi rides places. 
		taxiRideTargetPlace.add(CHECKER_CAP_COMPANY);
		taxiRideTargetPlace.add(YELLOW_CAP_COMPANY);
		taxiRideTargetPlace.add(UTE_CAP_COMPANY );
		taxiRideTargetPlace.add(FREE_PARKING_LOC);
		taxiRideTargetPlace.add(BLACK_AND_WHITE_CAP_COMPANY);
		taxiRideTargetPlace.add(TRANSIT_STATION1_ON_TRACK2);
		taxiRideTargetPlace.add(TRANSIT_STATION1_ON_TRACK3);
		taxiRideTargetPlace.add(TRANSIT_STATION2_ON_TRACK2);
		taxiRideTargetPlace.add(TRANSIT_STATION2_ON_TRACK3);

		communityCardsDeck = GameFlow.getCommunityCardsDeck();

		chanceCardsDeck = gameflow.getChanceCardsDeck();

		rollOnceCardsDeck = gameflow.getRollOnceCardsDeck();

		//Shuffles decks.
		Collections.shuffle(chanceCardsDeck);
		Collections.shuffle(communityCardsDeck);
		Collections.shuffle(rollOnceCardsDeck);

		//Getting the container to paint on, first.
		container = getContentPane();



		//Setting panels.
		//First Panel
		panel1.add(rollButton);
		label=new JLabel("PRESS ROLL DICE BUTTON");
		panel1.add(label);
		panel1.add(moveButton);
		panel1.add(MrMonopolyMove);
		panel1.setBounds(0,10,200,120);

		//Second Panel
		panel2.add(endButton);
		panel2.add(sellLands);
		panel2.add(putBuilding);
		panel2.add(sellBuilding);
		panel2.add(sellStock);
		panel2.add(mortgage);
		panel2.add(unmortgage);
		panel2.add(useCardButton);
		panel2.setBounds(0,130,200,300);

		//Third panel, which has the current player indicator in it.
		currentPlayerLabel = new JLabel();
		poolLabel = new JLabel("Currently the Pool: 0");
		panel3.add(currentPlayerLabel);
		panel3.add(poolLabel);
		panel3.setBounds(50, 550, 150, 50);

		panel4.add(loadButton);
		panel4.add(saveButton);
		panel4.setBounds(50, 600, 150, 100);

		//Creating tables to be able to see informations about the game.
		//dataTable for representing which player is associated with which color with how much money.
		dataTable = new JTable();
		dataTable.setModel(new MonopolyTableModel());
		dataTable.setDefaultRenderer(Color.class, new ColorRenderer(p));
		JScrollPane scrollPane = new JScrollPane( dataTable );
		scrollPane.setBounds(20, 430, 290, 90 );

		//Player Assets Tables are for showing separately showing each player's assets.
		//Player 1
		player1AssestTable = new JTable();
		player1AssestTable.setModel(new PlayerAssetsTableModel(0));
		JScrollPane player1ScrollPane = new JScrollPane( player1AssestTable );
		player1ScrollPane.setBounds(1000, 50, 200, 100);
		container.add(player1ScrollPane);

		//Player 2
		player2AssestTable = new JTable();
		player2AssestTable.setModel(new PlayerAssetsTableModel(1));
		JScrollPane player2ScrollPane = new JScrollPane( player2AssestTable );
		player2ScrollPane.setBounds(1000, 150, 200, 100);
		container.add(player2ScrollPane);

		//Player 3
		player3AssestTable = new JTable();
		player3AssestTable.setModel(new PlayerAssetsTableModel(2));
		JScrollPane player3ScrollPane = new JScrollPane( player3AssestTable );
		player3ScrollPane.setBounds(1000, 250, 200, 100);
		container.add(player3ScrollPane);

		//Player 4
		player4AssestTable = new JTable();
		player4AssestTable.setModel(new PlayerAssetsTableModel(3));
		JScrollPane player4ScrollPane = new JScrollPane( player4AssestTable );
		player4ScrollPane.setBounds(1000, 350, 200, 100);
		container.add(player4ScrollPane);

		//Player 5
		player5AssestTable = new JTable();
		player5AssestTable.setModel(new PlayerAssetsTableModel(3));
		player5ScrollPane = new JScrollPane(player5AssestTable );
		player5ScrollPane.setBounds(1000, 450, 200, 100);
		container.add(player5ScrollPane);
		if(playerCount <= 4)
			player5ScrollPane.setVisible(false);
		else
			player5AssestTable.setModel(new PlayerAssetsTableModel(4));

		//Player 6
		player6AssestTable = new JTable();
		player6AssestTable.setModel(new PlayerAssetsTableModel(3));
		player6ScrollPane = new JScrollPane( player6AssestTable );
		player6ScrollPane.setBounds(1000, 550, 200, 100);
		container.add(player6ScrollPane);
		if(playerCount <= 5)
			player6ScrollPane.setVisible(false);
		else
			player6AssestTable.setModel(new PlayerAssetsTableModel(5));

		//Player 7
		player7AssestTable = new JTable();
		player7AssestTable.setModel(new PlayerAssetsTableModel(3));
		player7ScrollPane = new JScrollPane( player7AssestTable );
		player7ScrollPane.setBounds(1200, 450, 200, 100);
		container.add(player7ScrollPane);
		if(playerCount <= 6)
			player7ScrollPane.setVisible(false);
		else
			player7AssestTable.setModel(new PlayerAssetsTableModel(6));

		//Player 8
		player8AssestTable = new JTable();
		player8AssestTable.setModel(new PlayerAssetsTableModel(3));
		player8ScrollPane = new JScrollPane( player8AssestTable );
		player8ScrollPane.setBounds(1200, 550, 200, 100);
		container.add(player8ScrollPane);
		if(playerCount <= 7)
			player8ScrollPane.setVisible(false);
		else
			player8AssestTable.setModel(new PlayerAssetsTableModel(7));

		//First player is the current.
		currentPlayer = players.get(0);
		currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());

		//Game moves.
		//Before anything, dice should be rolled, so others are disabled. 
		moveButton.setEnabled(false);
		endButton.setEnabled(false);
		MrMonopolyMove.setEnabled(false);
		endButton.setEnabled(false);
		sellLands.setEnabled(false);
		sellBuilding.setEnabled(false);
		putBuilding.setEnabled(false);
		sellStock.setEnabled(false);
		mortgage.setEnabled(false);
		unmortgage.setEnabled(false);
		useCardButton.setEnabled(false);
		saveButton.setEnabled(false);
		loadButton.setEnabled(true);
		rollButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){	
				//Regular dice.
				if(trickyRegular){
					facingValue1 = dice.getDice1();
					facingValue2 = dice.getDice2();
				}else{
					dice.roll();
					facingValue1 = dice.getDice1();
					facingValue2 = dice.getDice2();
				}

				if(facingValue1 == facingValue2){
					doubleCount++;
					if(doubleCount == 3){
						Player currentPlayer = players.get(p);
						//Send to jail.
						currentPlayer.setLoc(JAIL);
						currentPlayer.setInJail(true);
						//Changes the item's location.
						String[] squareInf = getSquareInf(currentPlayer.getLoc()); //The information of the square where the player is in.
						changeItemLoc(currentPlayer, Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); //Moves the player item on board.

						moveButton.setEnabled(false);
						endButton.setEnabled(false);
						MrMonopolyMove.setEnabled(false);
						endButton.setEnabled(true);
						sellLands.setEnabled(false);
						sellBuilding.setEnabled(false);
						sellStock.setEnabled(false);
						putBuilding.setEnabled(false);
						mortgage.setEnabled(false);
						unmortgage.setEnabled(false);
						useCardButton.setEnabled(false);
						rollButton.setEnabled(false);
						JOptionPane.showMessageDialog(null,"You doubled 3 times. Time to go Jail. Bye bye!");
						doubleCount =0;
						return;
					}
				}
				sumOfDices = facingValue1 + facingValue2;

				//Speed die.
				if(trickySpeed){
					speedDieFacedValue = speedDie.getFacedValue();	
				}else{
					speedDie.rollSpeedDie();
					speedDieFacedValue = speedDie.getFacedValue();	
				}

				if(speedDie.isMrMonopoly()){
					rollButton.setEnabled(false);
					moveButton.setEnabled(true);
					label.setText("Rolled: " + sumOfDices + " + Mr Monopoly" );
					JOptionPane.showMessageDialog(null,"You rolled  "+facingValue1+" + "+facingValue2+" = "+sumOfDices +" and Mr Monopoly.First do regular move and then the MR MONOPOLY move." );
				}else if(speedDie.isBus()){
					JOptionPane.showMessageDialog(null,"You rolled  "+facingValue1+" + "+facingValue2+" = "+sumOfDices +" and BUS." );
					label.setText("Rolled: " + sumOfDices + " + Bus   " );
					String str = (String)JOptionPane.showInputDialog(
							null,
							"Select number",
							"Customized Dialog",
							JOptionPane.PLAIN_MESSAGE,
							null,
							dice.getRolledValues(),
							""
							);
					sumOfDices=Integer.parseInt(str);
					rollButton.setEnabled(false);
					moveButton.setEnabled(true);
				}else {
					int speedDieIntValue=Integer.parseInt(speedDieFacedValue);
					sumOfDices+=speedDieIntValue;
					JOptionPane.showMessageDialog(null,"You rolled  "+facingValue1+" + "+facingValue2+ " + "+speedDieIntValue+" = " + sumOfDices );
					label.setText("Rolled (Three Pips): " + sumOfDices );
					rollButton.setEnabled(false);
					moveButton.setEnabled(true);	
				}

				sellLands.setEnabled(true);
				sellBuilding.setEnabled(true);
				sellStock.setEnabled(true);
				putBuilding.setEnabled(true);
				mortgage.setEnabled(true);
				unmortgage.setEnabled(true);
				useCardButton.setEnabled(true);
				saveButton.setEnabled(false);
				loadButton.setEnabled(false);
			}});

		MrMonopolyMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player currentPlayer = players.get(p); //Player whose turn it is.
				if(currentPlayer.isInJail()){
					JOptionPane.showMessageDialog(null, "You are in jail, you cannot do Mr Monopoly Move. End your turn.");
					endButton.setEnabled(true);
				}else{
					GameFlow.MrMonopolyMove(currentPlayer, sumOfDices); 
					int[] currentPlayerLoc = currentPlayer.getLoc(); 
					//Changes the item's location.
					String[] squareInf = getSquareInf(currentPlayerLoc); //The information of the square where the player is in.
					changeItemLoc( players.get(p),Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); //Moves the player item on board.

					//After the move, obey to the land.
					playTheTurn(currentPlayerLoc,currentPlayer);

					if(facingValue1 == facingValue2){ 
						JOptionPane.showMessageDialog(null, "You rolled doubles, you should roll again.");//If facing values are equal, dice must be rolled again.
						rollButton.setEnabled(true);
						MrMonopolyMove.setEnabled(false);
					}else{ //If facing values are not equal, turn must be ended.
						moveButton.setEnabled(false);
						endButton.setEnabled(true);
						MrMonopolyMove.setEnabled(false);
					}

				}
			}});

		moveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player currentPlayer = players.get(p); //Player whose turn it is.

				GameFlow.move(currentPlayer, sumOfDices); //It doesn't move the item on board, but change the player loc.

				//Player may pass one of the pay corners.
				MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
				monoModel.refresh(); 	

				int[] currentPlayerLoc = currentPlayer.getLoc(); 
				int track = currentPlayerLoc[0];
				int locationOnTrack = currentPlayerLoc[1];

				//Changes the item's location.
				String[] squareInf = getSquareInf(currentPlayerLoc); //The information of the square where the player is in.
				changeItemLoc(currentPlayer,Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); //Moves the player item on board.

				//After the move, obey to the land.
				playTheTurn(currentPlayerLoc,currentPlayer);

				if(!(Arrays.equals(currentPlayer.getLoc(), HOLLAND_TUNNEL_1_LOC) || 
						Arrays.equals(currentPlayer.getLoc(), HOLLAND_TUNNEL_2_LOC) ||
						Arrays.equals(currentPlayer.getLoc(), JAIL) || 
						Arrays.equals(currentPlayer.getLoc(), BLACK_AND_WHITE_CAP_COMPANY) ||
						Arrays.equals(currentPlayer.getLoc(), YELLOW_CAP_COMPANY) ||
						Arrays.equals(currentPlayer.getLoc(), CHECKER_CAP_COMPANY) ||
						Arrays.equals(currentPlayer.getLoc(), UTE_CAP_COMPANY)) ){
					//It may or not equal to currentPlayerLoc. Depends on the square. To guarantee, taking the loc again as the final loc.
					if(!(track == currentPlayer.getLoc()[0] && locationOnTrack == currentPlayer.getLoc()[1])){ //If player moved, somehow, follow the newly visited square's instructions.
						squareInf = getSquareInf(currentPlayerLoc); //The information of the square where the player is in.
						changeItemLoc(currentPlayer,Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); //Moves the player item on board.
						playTheTurn(currentPlayerLoc,currentPlayer);
					}
				}

				if(facingValue1 == facingValue2 && facingValue1+facingValue2==sumOfDices && speedDieFacedValue.equals("MrMonopoly")){ 
					//meaning player rolled doubles and the Mr.Monopoly
					JOptionPane.showMessageDialog(null, "You rolled doubles,and MR Monopoly. First do the MrMonopolyMove then you should roll the dice again.");//If facing values are equal, dice must be rolled again.
					MrMonopolyMove.setEnabled(true);
					moveButton.setEnabled(false);
				}else if(facingValue1 != facingValue2 && facingValue1+facingValue2==sumOfDices && speedDieFacedValue.equals("MrMonopoly")){
					JOptionPane.showMessageDialog(null, "You rolled MR Monopoly. Do Mr Monopoly");//If facing values are equal, dice must be rolled again.
					MrMonopolyMove.setEnabled(true);
					moveButton.setEnabled(false);
				}else if (facingValue1 == facingValue2 && facingValue1+facingValue2!=sumOfDices){ 
					//meaning player rolled doubles and pipe on the speedDie. should roll the dice again after move.
					JOptionPane.showMessageDialog(null, "You rolled doubles,you should roll again.");
					rollButton.setEnabled(true);
					moveButton.setEnabled(false);
					endButton.setEnabled(false);
				}else if(facingValue1 == facingValue2 && facingValue1+facingValue2==sumOfDices && speedDieFacedValue.equals("Bus")){	
					//meaning player rolled doubles and the bus on the speedDie.shoul roll the dice again.
					JOptionPane.showMessageDialog(null, "You rolled doubles,you should roll again.");
					rollButton.setEnabled(true);
					moveButton.setEnabled(false);
					endButton.setEnabled(false);
				}else{
					rollButton.setEnabled(false);
					moveButton.setEnabled(false);
					MrMonopolyMove.setEnabled(false);
					endButton.setEnabled(true);
				}

			}
		});

		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Next player's turn.
				Player currentPlayer =  players.get(p);

				if(findAuctionCard()!=null ){
					if(currentPlayer.getBid()==0){ 
						JOptionPane.showMessageDialog(null, currentPlayer.getName()+" Your turn to offer a bid for "+findAuctionCard().getName());
						int x=Integer.parseInt(JOptionPane.showInputDialog(null, "", "Enter your bid",0 ));
						if(currentPlayer.getMoney()<x){
							JOptionPane.showMessageDialog(null, "Oppps! you gave an bid more than your balance.Your balance is "+currentPlayer.getMoney()+" Please give a valid bid." );
							int y=Integer.parseInt(JOptionPane.showInputDialog(null, "", "Enter your bid again",0 ));
							currentPlayer.setBid(y); 
							bids.add(y);
						}else{
							currentPlayer.setBid(x); 
							bids.add(x);
						}


					}
				}
				if(findAuctionedStock()!=null ){
					if(currentPlayer.getBid()==0){ 
						JOptionPane.showMessageDialog(null, currentPlayer.getName()+" Your turn to offer a bid for "+findAuctionedStock().getName());
						int x=Integer.parseInt(JOptionPane.showInputDialog(null, "", "Enter your bid",0 )); 
						currentPlayer.setBid(x); 
						bids.add(x);
					}
				}

				if(bids.size()==players.size()){//GameFlow.getPlayerList().size()){

					if(findAuctionedStock()!=null){		
						Stock auctionedStock = findAuctionedStock();
						int maxBid=Bank.getMax(bids);
						int price=auctionedStock.getPriceOfShare() * ((Integer)auctionedStock.getAuction()[1]);
						if(maxBid < price/2){ 
							JOptionPane.showMessageDialog(null, "Everbody gave a bid for "+auctionedStock.getName()+" but all of bids the are below the its half of price.So no winner!");
						}else{ 
							Player winner= findTheWinner(GameFlow.getPlayerList(), maxBid);
							winner.buyStockWithAuction(auctionedStock, maxBid, ((Integer)auctionedStock.getAuction()[1]));
							updateAssets(winner);
							MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
							monoModel.refresh(); 	
							Object[] array = new Object[]{false, 0};
							auctionedStock.setAuction(array);
							JOptionPane.showMessageDialog(null, "Congratulations! "+winner.getName()+" You win the auction for stock "+auctionedStock.getName());
							for(int i=0; i<bids.size(); i++){
								bids.remove(i); 
								GameFlow.getPlayerList().get(i).setBid(0);
							}
						}
					}else if(findAuctionCard()!=null){
						LandCards auctionLand=findAuctionCard();
						int price=auctionLand.getLandPrice(); 
						int maxBid=Bank.getMax(bids);
						if(maxBid <price/2){ 		
							JOptionPane.showMessageDialog(null, "Everbody gave a bid for "+auctionLand.getName()+" but all of bids the are below the its half of price.So no winner!");
						}else{ 
							Player winner= findTheWinner(GameFlow.getPlayerList(), maxBid);
							winner.buyWithAuction(auctionLand, maxBid); 
							auctionLand.setAuction(false);
							updateAssets(winner);
							MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
							monoModel.refresh(); 	
							JOptionPane.showMessageDialog(null, "Congratulations! "+winner.getName()+" You win the auction for "+auctionLand.getName());
							for(int i=0; i<bids.size(); i++){
								bids.remove(i); GameFlow.getPlayerList().get(i).setBid(0);	
								GameFlow.getPlayerList().get(i).setBid(0);
							}
						}

					}
				}


				if (currentPlayer.hasMoney()){ //Has money.
					getNextPlayer();
					Player nextPlayer = players.get(p);
					if(nextPlayer.isInJail() && Arrays.equals(nextPlayer.getLoc(), JAIL)){
						int round = nextPlayer.getJailRound();
						round++;
						nextPlayer.setJailRound(round);
						if(round == 3){
							JOptionPane.showMessageDialog(null, "3 turn passed. You can get out of jail," + nextPlayer.getName());
							nextPlayer.setJailRound(0);
							nextPlayer.setInJail(false);
						}

						if(nextPlayer.isInJail()) //Round may became 3 and player may need to play normally.
							playTheTurn(JAIL, nextPlayer);

					}
				}else{ //Hasn't got money. Kick the player out of the game, game is over for player.
					JOptionPane.showMessageDialog(null, "Sorry! Game is over for you, " + currentPlayer.getName());
					currentPlayer.bankrupcy();
					changeItemLoc(currentPlayer, Integer.MAX_VALUE, Integer.MAX_VALUE);
					removePlayerAssets(currentPlayer);
					players.remove(currentPlayer); //Bye bye ! 
					if(p == players.size()){
						p = 0; //We are at the end of the list, so we can't call next players since it is index.
						//But setting to zero means returning to the beginning of list, equivalently the next player. 
					}else{
						//getNextPlayer() shouldn't be used here, since the index has decrease with removing the player.
						//Turn became to the next player already.
					}	

					Player nextPlayer = players.get(p);
					if(nextPlayer.isInJail() && Arrays.equals(nextPlayer.getLoc(), JAIL)){
						int round = nextPlayer.getJailRound();
						round++;
						nextPlayer.setJailRound(round);
						if(round == 3){
							JOptionPane.showMessageDialog(null, "3 turn passed. You can get out of jail," + nextPlayer.getName());
							nextPlayer.setJailRound(0);
							nextPlayer.setInJail(false);
						}

						if(nextPlayer.isInJail()) //Round may became 3 and player may need to play normally.
							playTheTurn(JAIL, nextPlayer);
					}
				}
				if(players.size() == 1){ //Only one player left, means game is over. Oscar goes to...
					Player winnerPlayer = players.get(p);
					JOptionPane.showMessageDialog(null, "AAAWEEESOME!!!!!!!");
					JOptionPane.showMessageDialog(null, "You are the only one remaining after all..." );
					JOptionPane.showMessageDialog(null, "You Beat Them All!!!");
					JOptionPane.showMessageDialog(null, "Well played, " + winnerPlayer.getName() + "!\nYou are the SHAMPION OF MONOPOLY!!!!");
					rollButton.setEnabled(false);
					moveButton.setEnabled(false);
					endButton.setEnabled(false);
					dispose(); 		//Close the window. Game is over.
				}else{		//Game continues...
					//Setting the buttons to default, so next player can play accordingly.
					Player nextPlayer = players.get(p);	
					if((nextPlayer.isInJail() && Arrays.equals(nextPlayer.getLoc(), JAIL)) || nextPlayer.isLoseTurn()){
						rollButton.setEnabled(false);
						endButton.setEnabled(true);
					}else{
						rollButton.setEnabled(true);
						endButton.setEnabled(false);						
					}
					moveButton.setEnabled(false);
					sellLands.setEnabled(false);
					sellBuilding.setEnabled(false);
					putBuilding.setEnabled(false);
					mortgage.setEnabled(false);
					unmortgage.setEnabled(false);
					useCardButton.setEnabled(false);
					saveButton.setEnabled(true);
					loadButton.setEnabled(true);
					sellStock.setEnabled(false);
					nextPlayer.setLoseTurn(false);
					doubleCount=0;
				}
			}
		});

		mortgage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				Player currentPlayer =  players.get(p);

				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to mortgage",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						currentPlayer.getPossibleMorgagedLandWithNamesAsArray(),
						""
						);

				currentPlayer.mortgageLand(str);
				MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
				monoModel.refresh(); 			

			}
		});

		unmortgage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				Player currentPlayer =  players.get(p);
				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to set your land unmortgaged",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						currentPlayer.getPossibleunMorgagedLandWithNamesAsArray(),
						""
						);
				currentPlayer.unmortgageLand(str);
				MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
				monoModel.refresh(); 			
			}
		});

		//Sell Lands action listener
		sellLands.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				Player currentPlayer =  players.get(p);

				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to sell",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						currentPlayer.getOwnedLandsWithNamesAsArray(),
						""
						);
				//String str can be null if cancel is pressed. Don't forget to look for that case.
				if(str != null){
					if(currentPlayer.sellLand(str)){
						JOptionPane.showMessageDialog(null, "You have sold" + str);
					}else{
						JOptionPane.showMessageDialog(null, "You cannot sell this property.\nIt is either mortgaged"
								+ " or have building on it.");
					}
					//Need to refresh, now. Money and assets may have changed
					MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
					monoModel.refresh(); 
					removeOneAsset(currentPlayer);
				}

			}
		});
		sellStock.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				Player currentPlayer =  players.get(p);
				String[] sellableStocks = GameFlow.getSellingStocksWithNamesAsArray(currentPlayer);
				if(sellableStocks.length!=0 ){
					String str = (String)JOptionPane.showInputDialog(
							null,
							"Select to sell",
							"Customized Dialog",
							JOptionPane.PLAIN_MESSAGE,
							null,
							GameFlow.getSellingStocksWithNamesAsArray(currentPlayer),
							""
							);

					Stock stock1 =GameFlow.findStockFromName(str);

					String str2 = (String)JOptionPane.showInputDialog(
							null,
							"Select to number of share to sell",
							"Customized Dialog",
							JOptionPane.PLAIN_MESSAGE,
							null,
							GameFlow.getSellableStockNumbersAsArray(currentPlayer,stock1),
							""
							);
					currentPlayer.sellStock(stock1,Integer.parseInt(str2));
					JOptionPane.showMessageDialog(null, "You have sold " + str2 +" share from "+ stock1.getName() );

					//Need to refresh, now. Money and assets may have changed
					MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
					monoModel.refresh(); 
					removeOneAsset(currentPlayer);
				}else{
					JOptionPane.showMessageDialog(null, "You have no stock for selling.");
				}
			}
		});

		putBuilding.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				Player currentPlayer =  players.get(p);

				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to put building",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						currentPlayer.getPossiblePutBuildingWithNamesAsArray(),
						""
						);

				currentPlayer.PutBuildingToLand(str);
				MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
				monoModel.refresh(); 	
				updateAssets(currentPlayer);
			}
		});

		sellBuilding.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){

				Player currentPlayer =  players.get(p);

				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to sell building",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						currentPlayer.getPossibleSellBuildingWithNamesAsArray(),
						""
						);
				currentPlayer.SellBuildingFromLand(str);
				MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
				monoModel.refresh(); 
				updateAssets(currentPlayer);
			}
		});
		useCardButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				Player currentPlayer =  players.get(p);

				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to use",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						currentPlayer.getUseCardWithNamesAsArray(),
						""
						);

				if(str.equals("takingBusTicket")){

					int track = currentPlayer.getLoc()[0];
					int loc=currentPlayer.getLoc()[1];
					Random randomGenerator = new Random();
					int randomInt=0;

					if(track==1){
						if(loc>=0 && loc<=14){
							randomInt = randomGenerator.nextInt(14);
						}else if (loc >= 14 && loc<=28 ){
							randomInt = randomGenerator.nextInt(14) + 14;
						}else if (loc >= 28 && loc<=42 ){
							randomInt = randomGenerator.nextInt(14) + 28;
						}else if (loc >= 42 && loc<=56 ){
							randomInt = randomGenerator.nextInt(14) + 42;
						}
						loc=randomInt;
						int[] newloc={1,loc};
						currentPlayer.setLoc(newloc);


					}else if(track==2){
						if(loc>=0 && loc<=10){
							randomInt = randomGenerator.nextInt(10);
						}else if (loc >= 10 && loc<=20 ){
							randomInt = randomGenerator.nextInt(10) + 10;
						}else if (loc >= 20 && loc<=30 ){
							randomInt = randomGenerator.nextInt(10) + 20;
						}else if (loc >= 30 && loc<=40 ){
							randomInt = randomGenerator.nextInt(10) + 30;
						}
						loc=randomInt;
						int[] newloc={2,loc};
						currentPlayer.setLoc(newloc);

					}else if (track ==3){
						if(loc>=0 && loc<=6){
							randomInt = randomGenerator.nextInt(6);
						}else if (loc >= 6 && loc<=12){
							randomInt = randomGenerator.nextInt(6) + 6;
						}else if (loc >= 12 && loc<=18 ){
							randomInt = randomGenerator.nextInt(6) + 12;
						}else if (loc >= 18 && loc<=24){
							randomInt = randomGenerator.nextInt(6) + 18;
						}
						loc=randomInt;
						int[] newloc={3,loc};
						currentPlayer.setLoc(newloc);
					}
					
					LinkedList<Cards> list = currentPlayer.getCardList();
					int size = list.size();
					int count = 0;
					for (int i = 0; i < size; i++) {
						Cards card =list.get(i-count);
						if(card instanceof ChanceCards && card !=null){
							list.remove(card); 
							count++;
						}
					}
					currentPlayer.setCardsList(list);
				}
				updateAssets(currentPlayer); 
				MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
				monoModel.refresh(); 	
				String[] squareInf = getSquareInf(currentPlayer.getLoc()); //The information of the square where the player is in.
				changeItemLoc(currentPlayer,Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); 
				playTheTurn(currentPlayer.getLoc(), currentPlayer);

			}
		});

		//Load's the most recent game.
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] savedNames = GameFlow.getSavedNames();
				if(savedNames != null){
					String saveName = (String)JOptionPane.showInputDialog(
							null,
							"Select to load a game",
							"Customized Dialog",
							JOptionPane.PLAIN_MESSAGE,
							null,
							GameFlow.getSavedNames(),
							""
							);
					if(saveName != null){
						GameFlow.load(saveName);
						setUp();
					}
				}else{
					JOptionPane.showMessageDialog(null, "There is not any saved game.");
				}	
			}
		});

		//Save the initial game.
		saveButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {	
				String saveName = JOptionPane.showInputDialog("Save Game As:");
				GameFlow.save(p, saveName);
				JOptionPane.showMessageDialog(null, "Game is saved. You can quit safetly, now.");

			}
		});

		container.add(panel1);
		container.add(panel2);
		container.add(panel3);
		container.add(panel4);
		container.add(scrollPane);
		container.add(board);
	}


	public void setUp(){
		//Instruction Set
		List<String[]> instructionSet = GameFlow.getInstructionSet();

		players = GameFlow.getPlayerList();
		int playerCount = Integer.parseInt(instructionSet.get(0)[1]);
		p = playerCount;

		for (int i = 0; i < playerCount; i++) {
			String[] playerLoc = instructionSet.get(i+1);
			int playerTrack = Integer.parseInt(playerLoc[3]);
			int playerLocation = Integer.parseInt(playerLoc[4]);
			int[] playerInf = new int[] {playerTrack, playerLocation}; 

			int playerX = Integer.parseInt(getSquareInf(playerInf)[1]);
			int playerY = Integer.parseInt(getSquareInf(playerInf)[2]);
			Player player = players.get(i);

			//Changing location.
			changeItemLoc(player, playerX, playerY);
			//Updating the having assets of player.
			updateAssetsAtStart(player);
		}	

		removeRemainingPlayersFromBoard(playerCount);

		removeRemainingPlayersFromList(playerCount);

		//Updates data table
		MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
		monoModel.refresh(); 

		//Further instructions index is:
		int index = playerCount + 1;

		//Whose turn is it.
		String[] turnStr = instructionSet.get(index);
		int turn = Integer.parseInt(turnStr[0]);
		this.p = turn;
		currentPlayer = players.get(p);
		currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());

		//Pool
		poolLabel.setText("Pool: " + GameFlow.getPool());

		//Next instructions index is:
		index++;
		//Next instructions to GUI is setting dices.
		String[] instructionInf = instructionSet.get(index);
		//Checks if the dice is tricky or regular
		if(instructionInf.length > 1){
			if(!(instructionInf[1] == null || instructionInf[2] == null)){
				//Firstly the regular dice.
				facingValue1 = Integer.parseInt(instructionInf[1]);
				dice.setDice1(facingValue1);
				facingValue2 = Integer.parseInt(instructionInf[2]);
				dice.setDice2(facingValue2);
				trickyRegular = true;
			}
		}
		index++;

		instructionInf = instructionSet.get(index);
		if(instructionInf.length > 1){
			if(instructionInf[1] != null){
				speedDie.setFacedValue(instructionInf[1]);
				speedDieFacedValue = instructionInf[1];
				trickySpeed = true;
			}
		}
	}


	public void removeRemainingPlayersFromBoard(int playerCount){
		if(players.size() > playerCount){
			int size = players.size();
			int diff = size - playerCount;

			//Further of player counts in list, doesn't needed to be showed
			//on board. Send them to their inital state, which is maximum.
			for (int i = 0; i < diff; i++){
				Player playerToBeRemoved = players.get(playerCount + i);
				changeItemLoc(playerToBeRemoved, Integer.MAX_VALUE, Integer.MAX_VALUE);
			}		
		}
	}

	//If the newly loaded play has lesser number of player than the current 
	//play, playerList should have to remove those unneccessary players 
	//from list.
	public void removeRemainingPlayersFromList(int playerCount){
		if(players.size() > playerCount){
			int size = players.size();
			int diff = size - playerCount;

			//Whenever we remove an element from list, it shrinks.
			//So, in every iteration remove the (last player's index + 1) index 
			//which equals to player count and do it for number of remaning
			//elements times (diff).
			for (int i = 0; i < diff; i++)
				players.remove(playerCount);
		}
	}


	//To buy a capCampony
	public void buyingCapCo(Player currentPlayer, CapCompany capCo){
		int n = JOptionPane.showConfirmDialog(  
				null,
				"Price of "+ capCo +" is $"+ capCo.getLandPrice() + "\n" + 
						"Do you want to buy? ",
						capCo.getName(),
						JOptionPane.YES_NO_OPTION);

		if(n == JOptionPane.YES_OPTION){
			LinkedList<Cards> playerCardsList = currentPlayer.getCardsList();
			boolean hasBargain = false; //Assumes player hasn't Bargain Business Card.
			for (int i = 0; i < playerCardsList.size(); i++) { //Looks if the player has Bargain Business Card.

				if(playerCardsList.get(i).equals(GameFlow.getBargainCard())){
					hasBargain = true; //We are in. So player has Bargain Business Card.
					int p = JOptionPane.showConfirmDialog(  
							null,
							"Do you want to use your bargain card?!" ,
							"Bargain",
							JOptionPane.YES_NO_OPTION);

					if(p == JOptionPane.YES_OPTION){ //But the land for $100 via Bargain Business Card.
						if(currentPlayer.getMoney() >= capCo.getLandPrice()){
							GameFlow.landJob(currentPlayer,capCo, true);
							//Updating the assets table.
							updateAssets(currentPlayer); 
						}else{
							JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + capCo.getName());
						}
						break;
					}else{ //Player chose to buy at normal price.
						if(currentPlayer.getMoney() >= capCo.getLandPrice()){
							GameFlow.landJob(currentPlayer,capCo, false);
							//Updating the assets table.
							updateAssets(currentPlayer); 
						}else{
							JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + capCo.getName());
						}
						break;
					}

				}


			}  // for loop ends here, means player has no Bargain Business Card.

			if(!hasBargain){ //If hasBargain is still false, that means player doesn't have Bargain Business Card.
				if(currentPlayer.getMoney() >= capCo.getLandPrice()){
					GameFlow.buy(currentPlayer,capCo, false);
					//Updating the assets table.
					updateAssets(currentPlayer); 
				}else{
					JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + capCo.getName());
				}
			}

		}
	}

	public void playTheTurn(int[] currentPlayerLoc, final Player currentPlayer){
		//If player comes a special square, move should follow their instructions. If not, it is just a land.
		if(Arrays.equals(currentPlayerLoc,GO_LOC)){
			//Move method give player the money, so nothing need to be done here.
			JOptionPane.showMessageDialog(null, "Welcome! You are in GO Location. You earned $200 "); 
		}else if(Arrays.equals(currentPlayerLoc, COMMUNITY_CHEST_1_LOC) || Arrays.equals(currentPlayerLoc, COMMUNITY_CHEST_2_LOC) || Arrays.equals(currentPlayerLoc, COMMUNITY_CHEST_3_LOC) 
				|| Arrays.equals(currentPlayerLoc, COMMUNITY_CHEST_4_LOC) || Arrays.equals(currentPlayerLoc, COMMUNITY_CHEST_5_LOC) || Arrays.equals(currentPlayerLoc, COMMUNITY_CHEST_6_LOC) 
				||Arrays.equals(currentPlayerLoc, COMMUNITY_CHEST_7_LOC) || Arrays.equals(currentPlayerLoc, COMMUNITY_CHEST_8_LOC)){
			CommunityCards communityCard = currentPlayer.drawCommunityCard(communityCardsDeck);
			if(communityCard.getCardType()=="AprilFifteen"){
				JOptionPane.showMessageDialog(null, "April 15, Taxes Due!\nMove directly to Income Tax, pay the fine or go directly to Jail.");
				currentPlayer.setLoc(INCOME_TAX_LOC);
				int n = JOptionPane.showConfirmDialog(  
						null,
						"Will you pay the tax? Otherwise, you will go to Jail.", null,
						JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION){
					GameFlow.incomeTaxJob(currentPlayer);
				}else{
					JOptionPane.showMessageDialog(null, "You directed to Jail. :(");
					currentPlayer.setLoc(JAIL);
					currentPlayer.setInJail(true);
				}
			}else if(communityCard.getCardType()=="MovingExperience"){
				JOptionPane.showMessageDialog(null, "A Moving Experince!\nMove to any Transportation Property. If unowned, you may purchase it else pay rent.");
				String[] transportationProperties = new String[] {"Reading RailRoad","Pennsylvania RailRoad", "B&O RailRoad", "Short Line","Checker Cab Co.", "Black&White Cab Co.", "Yellow Cab Co.", "Ute Cab Co." };
				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to move",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						transportationProperties,
						""
						);
				//Find the land from String str and sell. Write a method for it.
				//String str can be null if cancel is pressed. Don't forget to look for that case.
				if(str=="Reading RailRoad"){
					int[] loc = new int[]{1,7};
					currentPlayer.setLoc(loc);
				}else if(str=="Pennsylvania RailRoad"){
					int[] loc = new int[]{2,15};
					currentPlayer.setLoc(loc);
				}else if(str=="B&O RailRoad"){
					int[] loc = new int[]{1,35};
					currentPlayer.setLoc(loc);
				}else if(str=="Short Line"){
					int[] loc = new int[]{2,35};
					currentPlayer.setLoc(loc);
				}else if(str=="Checker Cab Co."){
					int[] loc = new int[]{1,6};
					currentPlayer.setLoc(loc);
				}else if(str=="Black&White Cab Co."){
					int[] loc = new int[]{1,22};
					currentPlayer.setLoc(loc);
				}else if(str=="Yellow Cab Co."){
					int[] loc = new int[]{1,34};
					currentPlayer.setLoc(loc);
				}else if(str=="Ute Cab Co."){
					int[] loc = new int[]{1,50};
					currentPlayer.setLoc(loc);
				}
			}else if(communityCard.getCardType()=="InheritStock"){
				JOptionPane.showMessageDialog(null, "Inherit Stock! You may chose any 1 share of any unpurchased stock to add to your portfolio.");

				String str = (String)JOptionPane.showInputDialog(
						null,
						"You will choose one company for buying a share",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						GameFlow.getStocksWithNamesAsArray(stockArray),
						""
						);

				Stock stock1=GameFlow.findStockFromName(str);

				currentPlayer.buyStock(stock1,1);
				currentPlayer.takeMoney(stock1.getPriceOfShare());

				updateAssets(currentPlayer);	

			}else if(communityCard.getCardType()=="HouseCondemned"){
				JOptionPane.showMessageDialog(null, "House Condemned!\nThe city condemned one of your houses.\nSell one house back to Bank at 1/2 the price you paid for it.");

				int houseNum = 0;
				for (Cards lcards : currentPlayer.getCardsList()) {
					if(lcards instanceof LandCards)
						houseNum = houseNum + currentPlayer.getBuildings().get((LandCards) lcards)[0];
				}

				if(houseNum!=0){
					String str = (String)JOptionPane.showInputDialog(
							null,
							"Select to buy a building",
							"Customized Dialog",
							JOptionPane.PLAIN_MESSAGE,
							null,
							currentPlayer.getLandwithHouseBuildingAsArray(),
							""
							);
					currentPlayer.SellBuildingFromLand(str);
				}

			}else if(communityCard.getCardType()=="TornadoHits"){
				JOptionPane.showMessageDialog(null, "Tornado Hits!\nRemove one House from each property in any 1 of your color groups.");


			}else{
				String message = GameFlow.communityJob(currentPlayer, communityCard); //Handles the community cards' work by following their instructions.
				JOptionPane.showMessageDialog(null, "Welcome! You are in Community Chest Location");
				JOptionPane.showMessageDialog(null, message);
				displayWarningMessage(currentPlayer);
			}

			poolLabel.setText("Pool: " + GameFlow.getPool());
			//Changes the item's location.
			String[] squareInf = getSquareInf(currentPlayerLoc); //The information of the square where the player is in.
			changeItemLoc(currentPlayer,Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); //Moves the player item on board.
			updateAssets(currentPlayer);
			removeOneAsset(currentPlayer);
		}else if(Arrays.equals(currentPlayerLoc, ROLL_ONCE_LOC)){
			JOptionPane.showMessageDialog(null, "Welcome! You are in Roll Once Location");

			final JFrame rollFrame = new JFrame("Roll once");
			Container container = rollFrame.getContentPane();

			rollFrame.setVisible(true);
			rollFrame.setSize(500, 200);

			JPanel rollPanel = new JPanel();
			JPanel labelPanel = new JPanel();

			final JButton rollOnceButton = new JButton("Roll Dice & Draw Card");
			rollPanel.add(rollOnceButton);

			rollPanel.setBounds(100, 0, 300, 50);

			final JLabel rollLabel = new JLabel("Click to Roll Dice & Draw Card button.");
			labelPanel.add(rollLabel);
			labelPanel.setBounds(125, 100, 300, 100);

			container.add(labelPanel);
			container.add(rollPanel);
			rollOnceButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int rollDice;
					rollDice = RegularDice.rollOneDice(); //Roll dice
					Cards rollOnceCard = currentPlayer.drawRollOnceCard(rollOnceCardsDeck); //Draw a card.
					rollLabel.setText("Rolled: " + rollDice + "\nDrawed: " + rollOnceCard.getName());
					int beforeGameMoney = currentPlayer.getMoney();
					GameFlow.rollOnceJob(currentPlayer, rollDice, rollOnceCard); //Follow the instructions of roll once square.
					int afterGameMoney = currentPlayer.getMoney();
					rollOnceButton.setEnabled(false);
					int winningMoney = afterGameMoney - beforeGameMoney;
					if(winningMoney == 0)
						JOptionPane.showMessageDialog(null, "Sorry, you couldn't earn money!");
					else
						JOptionPane.showMessageDialog(null, "Well Done! You earned $" + winningMoney + "!");
					rollFrame.dispose();
					MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
					monoModel.refresh(); 
				}
			});

		}else if(Arrays.equals(currentPlayerLoc, BLACK_AND_WHITE_CAP_COMPANY) || Arrays.equals(currentPlayerLoc, YELLOW_CAP_COMPANY)
				||Arrays.equals(currentPlayerLoc, CHECKER_CAP_COMPANY)||Arrays.equals(currentPlayerLoc, UTE_CAP_COMPANY)){

			LandCards currentLand = GameFlow.getLandObject(currentPlayerLoc);
			boolean isSold= currentLand.isSold();
			if(isSold){
				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to act",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						CapCompany.getTaxiRideChoices(),
						""
						);

				if(str.equals("Pay normal rent to owner")){
					int rent=currentLand.getCurrentRent();
					Player.trade(currentPlayer,currentLand.getLandOwner(),rent);

				}else if(str.equals("Pay $50 to owner")){
					if(!currentLand.getLandOwner().getName().equals(currentPlayer.getName())){
						Player.trade(currentPlayer, currentLand.getLandOwner(), 50);
						String goPlace = (String)JOptionPane.showInputDialog(
								null,
								"Select to act",
								"Customized Dialog",
								JOptionPane.PLAIN_MESSAGE,
								null,
								choices,
								""
								);
						if(goPlace.equals("TransitStation1")){
							currentPlayer.setLoc(TRANSIT_STATION1_ON_TRACK2);
						}else if(goPlace.equals("TransitStation2")){
							currentPlayer.setLoc(TRANSIT_STATION2_ON_TRACK2);
						}else if(goPlace.equals("TransitStation3")){
							currentPlayer.setLoc(TRANSIT_STATION1_ON_TRACK3);
						}else if(goPlace.equals("TransitStation4")){
							currentPlayer.setLoc(TRANSIT_STATION2_ON_TRACK3);
						}else if(goPlace.equals("FreeParking")){
							currentPlayer.setLoc(FREE_PARKING_LOC);
						}else if(goPlace.equals("CheckerCabCompany")){
							currentPlayer.setLoc(CHECKER_CAP_COMPANY);
						}else if(goPlace.equals("BlackAndWhiteCabCompany")){
							currentPlayer.setLoc(BLACK_AND_WHITE_CAP_COMPANY);
						}else if(goPlace.equals("UteCabCompany")){
							currentPlayer.setLoc(UTE_CAP_COMPANY);
						}else if(goPlace.equals("YellowCabCompany")){
							currentPlayer.setLoc(YELLOW_CAP_COMPANY);
						}
						int[] loc = currentPlayer.getLoc();
						LandCards land = GameFlow.getLandObject(loc);
						if(!land.isSold()){
							playTheTurn(loc, currentPlayer);
						}


						//					taxiRideTargetPlace.remove(currentPlayerLoc);
						//					int x=(int)(Math.random()*taxiRideTargetPlace.size());
						//					int[] loc=taxiRideTargetPlace.get(x);
						//					currentPlayer.setLoc(loc);
						//					playTheTurn(loc, currentPlayer);

					}else{
						GameFlow.payToPool(20, currentPlayer);

						String goPlace = (String)JOptionPane.showInputDialog(
								null,
								"Select to act",
								"Customized Dialog",
								JOptionPane.PLAIN_MESSAGE,
								null,
								choices,
								""
								);
						if(goPlace.equals("TransitStation1")){
							currentPlayer.setLoc(TRANSIT_STATION1_ON_TRACK2);
						}else if(goPlace.equals("TransitStation2")){
							currentPlayer.setLoc(TRANSIT_STATION2_ON_TRACK2);
						}else if(goPlace.equals("TransitStation3")){
							currentPlayer.setLoc(TRANSIT_STATION1_ON_TRACK3);
						}else if(goPlace.equals("TransitStation4")){
							currentPlayer.setLoc(TRANSIT_STATION2_ON_TRACK3);
						}else if(goPlace.equals("FreeParking")){
							currentPlayer.setLoc(FREE_PARKING_LOC);
						}else if(goPlace.equals("CheckerCabCompany")){
							currentPlayer.setLoc(CHECKER_CAP_COMPANY);
						}else if(goPlace.equals("BlackAndWhiteCabCompany")){
							currentPlayer.setLoc(BLACK_AND_WHITE_CAP_COMPANY);
						}else if(goPlace.equals("UteCabCompany")){
							currentPlayer.setLoc(UTE_CAP_COMPANY);
						}else if(goPlace.equals("YellowCabCompany")){
							currentPlayer.setLoc(YELLOW_CAP_COMPANY);
						}
						int[] loc = currentPlayer.getLoc();
						LandCards land = GameFlow.getLandObject(loc);
						if(!land.isSold()){
							playTheTurn(loc, currentPlayer);
						}
					}
				}

				//				}else if(currentLand.getLandOwner().equals(currentPlayer)){
				//					GameFlow.payToPool(20, currentPlayer);
				//					taxiRideTargetPlace.remove(currentPlayerLoc);
				//					int x=(int)(Math.random()*taxiRideTargetPlace.size());
				//					int[] loc=taxiRideTargetPlace.get(x);
				//					currentPlayer.setLoc(loc);
				//					playTheTurn(loc, currentPlayer);
				//				}
			}
			poolLabel.setText("Pool: " + GameFlow.getPool());

		}else if(Arrays.equals(currentPlayerLoc, CHANCE_1_LOC) || Arrays.equals(currentPlayerLoc, CHANCE_2_LOC) || Arrays.equals(currentPlayerLoc, CHANCE_3_LOC) 
				|| Arrays.equals(currentPlayerLoc, CHANCE_4_LOC) || Arrays.equals(currentPlayerLoc, CHANCE_5_LOC) || Arrays.equals(currentPlayerLoc, CHANCE_6_LOC) 
				||Arrays.equals(currentPlayerLoc, CHANCE_7_LOC) || Arrays.equals(currentPlayerLoc, CHANCE_8_LOC)){

			JOptionPane.showMessageDialog(null, "Welcome! You are in Chance Location");

			ChanceCards chanceCard = currentPlayer.drawChanceCard(chanceCardsDeck);

			if(chanceCard.getCardType()=="assetsSeized"){
				JOptionPane.showMessageDialog(null, "Assets Seized!\nSurrender any one undeveloped, unmortgaged property - or any one building to the Bank. "
						+ "/nIf you do not own property - go directly to Jail.");
				if(currentPlayer.getSeizeLandsWithNamesAsArray() != null){

					String str = (String)JOptionPane.showInputDialog(
							null,
							"Select to sell",
							"Customized Dialog",
							JOptionPane.PLAIN_MESSAGE,
							null,
							currentPlayer.getSeizeLandsWithNamesAsArray(),
							""
							);
					currentPlayer.seizeLand(str);
				}else{
					currentPlayer.setLoc(JAIL);
					currentPlayer.setInJail(true);
				}


			}else if(chanceCard.getCardType()=="changingLanes2"){
				JOptionPane.showMessageDialog(null, "Changing Lanes!\nMove directly  to the space that is 1 Track above this one. "
						+ "\nIf you are on the Inner Track, do nothing.");
				ChanceCards.changingLanes2(currentPlayer);
				String[] squareInf = getSquareInf(currentPlayer.getLoc()); //The information of the square where the player is in.
				changeItemLoc(currentPlayer,Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); //Moves the player item on board.
				//Updates assets.
				//updateAssets(currentPlayer);



			}else if(chanceCard.getCardType()=="hurricaneMakesLandfall"){
				JOptionPane.showMessageDialog(null, "Hurricane Makes Landfall!\nRemove 1 House from each property in any player's 1 color group."
						+ "\n(Downgrade Skyscrapers to Hotels; Hotels to 4 houses.");

				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select player",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						currentPlayer.getPlayersWithNamesAsArray((LinkedList<Player>) players),
						""
						);

				Player player = currentPlayer;
				for (int i = 0; i < players.size(); i++){
					if(players.get(i).getName().equals(str)){
						player = players.get(i);
						break;
					}
				}

				if(player.getOwnedLandsColorWithNamesAsArray().length == 0){
					JOptionPane.showMessageDialog(null, "There is no color groups to select.");
				}else{
					String color = (String)JOptionPane.showInputDialog(
							null,
							"Select color",
							"Customized Dialog",
							JOptionPane.PLAIN_MESSAGE,
							null,
							player.getOwnedLandsColorWithNamesAsArray(),
							""
							);
					if(color!=null){
						//Take the color group.
						List<LandCards> pCards = player.findLandsOfColorGroup(color);

						for (LandCards pCard : pCards) {
							player.SellBuildingFromLand(pCard.getName());
						}
					}
				}
			}else{
				String message = GameFlow.chanceJob(currentPlayer, chanceCard); //Handles the chance cards' work by following their instructions
				JOptionPane.showMessageDialog(null, message);
				displayWarningMessage(currentPlayer); 
			}
			poolLabel.setText("Pool: " + GameFlow.getPool());
			for (int i = 0; i < players.size(); i++) {
				//Changes the items locations.
				Player player = players.get(i);
				String[] squareInf = getSquareInf(player.getLoc()); //The information of the square where the player is in.
				changeItemLoc(player,Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); //Moves the player item on board.
				//Updates assets.
				updateAssets(player);
				removeOneAsset(player);
			}

		}else if(Arrays.equals(currentPlayerLoc, FREE_PARKING_LOC)){
			//No instructions on free parking lot. 
			JOptionPane.showMessageDialog(null, "Welcome! You are in Free Parking Location");

		}else if(Arrays.equals(currentPlayerLoc, STOCK_EXCHANGE_LOC)){
			//No instructions on free parking lot. 
			JOptionPane.showMessageDialog(null, "Welcome! You are in Stock Exhange Location, now.");
			if(currentPlayer.getNumberofSharedStocks().keySet().size()!=0){
				JOptionPane.showMessageDialog(null, "You will get dividends for your all stocks that you have!");

				int money=0;

				for (Stock stock : currentPlayer.getNumberofSharedStocks().keySet()) {
					int x = currentPlayer.getNumberofSharedStocks().get(stock);
					int[] array = stock.getDividends();
					money +=array[x-1];

				}

				currentPlayer.takeMoney(money);

				JOptionPane.showMessageDialog(null, "You earned $" + money + " from your stocks!");

			}

			int n = JOptionPane.showConfirmDialog(  
					null,
					"Do you want to buy a stock?", null,
					JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){

				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select to buy a stock",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						GameFlow.getStocksWithNamesAsArray(stockArray),
						""
						);
				Stock stock1=GameFlow.findStockFromName(str);

				String numberOfWanted = (String)JOptionPane.showInputDialog(
						null,
						"Select to number of share of Stock",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						GameFlow.getStockNumbersAsArray(stock1),
						""
						);
				currentPlayer.buyStock(stock1,Integer.parseInt(numberOfWanted));

			}else{
				Object[] array = new Object[]{true,1};
				int i=(int)(Math.random()*6);
				stockArray[i].setAuction(array);
				JOptionPane.showMessageDialog(null, "OK. now its Auction time for " + stockArray[i].getName() + ".");	
			}
			updateAssets(currentPlayer);

		}else if(Arrays.equals(currentPlayerLoc, PAY_DAY)){		
			JOptionPane.showMessageDialog(null, "You are in Pay Day location. When you pass, you will collect money.");

		}else if(currentPlayerLoc[0]==GOTOJAIL[0] && currentPlayerLoc[1]==GOTOJAIL[1]){
			currentPlayer.setLoc(JAIL);
			currentPlayer.setInJail(true);
			JOptionPane.showMessageDialog(null, "You will directly go to Jail. :(");

		}else if(currentPlayerLoc[0]==JAIL[0] && currentPlayerLoc[1]==JAIL[1]){
			if(currentPlayer.isInJail()==true){
				LinkedList<Cards> playerCardsList = currentPlayer.getCardsList();
				for (int i = 0; i < playerCardsList.size(); i++) { //Looks if the player has getoutofjail card.
					if(playerCardsList.get(i) instanceof CommunityCards){
						if(playerCardsList.get(i).getName()=="communityType15"){
							int n = JOptionPane.showConfirmDialog(  
									null,
									"Do you want to use your Get Out of Jail Card?", null,
									JOptionPane.YES_NO_OPTION);
							if(n == JOptionPane.YES_OPTION){
								playerCardsList.remove(i);
								currentPlayer.setInJail(false);
								currentPlayer.setJailRound(0);
								return;
							}else{
								JOptionPane.showMessageDialog(null, "OK.It is your choice.");
								break;
							}
						}
					}
				}
				int n = JOptionPane.showConfirmDialog(  
						null,
						"Will you pay $200 for getting out of the Jail?", null,
						JOptionPane.YES_NO_OPTION);

				if(n == JOptionPane.YES_OPTION){
					GameFlow.payToPool(200, currentPlayer);
					poolLabel.setText("Pool: " + GameFlow.getPool());
					currentPlayer.setInJail(false);
					currentPlayer.setJailRound(0);
					return;
				}else{
					JOptionPane.showMessageDialog(null, "OK.It is your choice. You will roll the dice, now.");
					final JFrame jailFrame = new JFrame("For Getting Out Of The Jail");
					Container container = jailFrame.getContentPane();
					jailFrame.setVisible(true);
					jailFrame.setSize(500, 200);
					JPanel rollPanel = new JPanel();
					JPanel labelPanel = new JPanel();
					final JButton rollDiceButton = new JButton("Roll Dice");
					rollPanel.add(rollDiceButton);
					rollPanel.setBounds(100, 0, 300, 50);
					final JLabel rollLabel = new JLabel("Click Roll Dice button.");
					labelPanel.add(rollLabel);
					labelPanel.setBounds(125, 100, 300, 100);
					jailFrame.add(rollPanel);
					container.add(labelPanel);
					container.add(rollPanel);
					rollDiceButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							RegularDice dice = new RegularDice();
							dice.roll();
							int first = dice.getDice1();
							int second = dice.getDice2();
							rollLabel.setText("Rolled: " + first + " and " + second);
							if (first==second){
								currentPlayer.setInJail(false);
								JOptionPane.showMessageDialog(null, "You are lucky. You can get out of the jail on the next turn.");
								currentPlayer.setJailRound(0);
								jailFrame.dispose();
								return;
							}else{
								JOptionPane.showMessageDialog(null, "You will wait one more round.");
								jailFrame.dispose();
							}
						}
					});			
				}
			}
			poolLabel.setText("Pool: " + GameFlow.getPool());
		}else if(currentPlayerLoc[0]==HOLLAND_TUNNEL_1_LOC[0] && currentPlayerLoc[1]==HOLLAND_TUNNEL_1_LOC[1] || currentPlayerLoc[0]==HOLLAND_TUNNEL_2_LOC[0] && currentPlayerLoc[1]==HOLLAND_TUNNEL_2_LOC[1]){
			JOptionPane.showMessageDialog(null, "You will directly go to the other Holland Tunnel.");
			GameFlow.hollandTunnelJob(currentPlayer);
			//Changes the item's location.
			String[] squareInf = getSquareInf(currentPlayerLoc); //The information of the square where the player is in.
			changeItemLoc(currentPlayer,Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); //Moves the player item on board.
		}else if(currentPlayerLoc[0] == INCOME_TAX_LOC[0] && currentPlayerLoc[1] == INCOME_TAX_LOC[1]){
			GameFlow.incomeTaxJob(currentPlayer);
			poolLabel.setText("Pool: " + GameFlow.getPool());
			JOptionPane.showMessageDialog(null, "You have to pay %10 of your income or $200 to the pool.");
		}else if(currentPlayerLoc[0] == LUXURY_TAX_LOC[0] && currentPlayerLoc[1] == LUXURY_TAX_LOC[1]){
			GameFlow.luxuryTaxJob(currentPlayer);
			poolLabel.setText("Pool: " + GameFlow.getPool());
			JOptionPane.showMessageDialog(null, "You have to pay $200 to Pool. If your money is greatest than $2000,\n otherwise you will pay %10 amount of your money.");
		}else if(currentPlayerLoc[0] == BONUS_LOC[0] && currentPlayerLoc[1] == BONUS_LOC[1]){
			GameFlow.bonusJob(currentPlayer);
			JOptionPane.showMessageDialog(null, "You earned $300.");
		}else if(currentPlayerLoc[0] == TAX_REFUND_LOC[0] && currentPlayerLoc[1] == TAX_REFUND_LOC[1]){
			GameFlow.taxRefundJob(currentPlayer);
			poolLabel.setText("Pool: " + GameFlow.getPool());
			JOptionPane.showMessageDialog(null, "You will collect half of the money from Pool.");
		}else if(Arrays.equals(currentPlayerLoc, BUS_TICKET_1) || Arrays.equals(currentPlayerLoc, BUS_TICKET_2)){
			JOptionPane.showMessageDialog(null, "You are in Bus Ticket, now.");
		}else if(Arrays.equals(currentPlayerLoc,TRANSIT_STATION1_ON_TRACK2) || Arrays.equals(currentPlayerLoc,TRANSIT_STATION2_ON_TRACK2) 
				|| Arrays.equals(currentPlayerLoc,TRANSIT_STATION1_ON_TRACK3) || Arrays.equals(currentPlayerLoc,TRANSIT_STATION2_ON_TRACK3)){
			JOptionPane.showMessageDialog(null, "You are in Transit Station, now.");
		}else if(Arrays.equals(currentPlayerLoc, AUCTION_LOC)){
			JOptionPane.showMessageDialog(null, "You are in Auction, now.");
			if(!(getCardsInTheBank()==null)){
				String str = (String)JOptionPane.showInputDialog(
						null,
						"Select a land to put on auction",
						"Customized Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						getCardsNameInTheBank(),
						""
						);
				LandCards auctionLand=findLandFromName(str);
				auctionLand.setAuction(true);

				JOptionPane.showMessageDialog(null, currentPlayer.getName()+"Your turn to offer a bid for "+auctionLand.getName());
				int x=Integer.parseInt(JOptionPane.showInputDialog(null, "", "Enter your bid",0 ));

				currentPlayer.setBid(x);
				bids.add(x);

			}else{//if sum of dices is even, 
				if(getSumOfDice()%2==0){
					for(int i=0; i<GameFlow.getPlayerList().size(); i++){
						for(int j=0; j<GameFlow.getPlayerList().get(i).getOwnedLands().size(); j++){
							if((!GameFlow.getPlayerList().get(i).getOwnedLands().get(j).getLandOwner().equals(currentPlayer))){
								lstForRent.add(GameFlow.getPlayerList().get(i).getOwnedLands().get(j));		
							}
						}
					}
					LandCards landToGo=getLandWithHighestRent(lstForRent);
					int[] loc=LandCards.getLoc(landToGo);
					Player.goToSpecificLoc(currentPlayer,landToGo);
					currentPlayer.setLoc(loc);
					String[] coordinates=getSquareInf(loc);
					int x=Integer.parseInt(coordinates[0]);
					int y=Integer.parseInt(coordinates[1]);
					currentPlayer.setLoc(loc);
					changeItemLoc(currentPlayer,x,y);
					Player.trade(currentPlayer,landToGo.getLandOwner(), landToGo.getCurrentRent());

				}else{//if some of dices is odd, go the square with highest rent within the outer most track
					for(int i=0; i<GameFlow.getPlayerList().size(); i++){
						for(int j=0; j<GameFlow.getPlayerList().get(i).getOwnedLands().size(); j++){
							if((!GameFlow.getPlayerList().get(i).getOwnedLands().get(j).getLandOwner().equals(currentPlayer))&&LandCards.getLoc(GameFlow.getPlayerList().get(i).getOwnedLands().get(j))[1]==1){
								lstForRent.add(GameFlow.getPlayerList().get(i).getOwnedLands().get(j));		
							}
						}
					}
					LandCards landToGo=getLandWithHighestRent(lstForRent);
					int[] loc=LandCards.getLoc(landToGo);
					currentPlayer.setLoc(loc);
					String[] coordinates=getSquareInf(loc);
					int x=Integer.parseInt(coordinates[0]);
					int y=Integer.parseInt(coordinates[1]);
					currentPlayer.setLoc(loc);
					changeItemLoc(currentPlayer,x,y);
					Player.trade(currentPlayer,landToGo.getLandOwner(), landToGo.getCurrentRent());
				}


			}
		}else if(Arrays.equals(currentPlayerLoc, REVERSE_DIRECTION_LOC)){
			JOptionPane.showMessageDialog(null, "You are in Reverse Direction, now.");
		}else if(Arrays.equals(currentPlayerLoc, SUBWAY_LOC)){
			JOptionPane.showMessageDialog(null, "You are in Subway, now.");
		}else if(Arrays.equals(currentPlayerLoc, BIRTHDAY_GIFT_LOC)){
			JOptionPane.showMessageDialog(null, "You are in Birthday Gift. You will take $100 or go to nearest Cap Company.");
			int n = JOptionPane.showConfirmDialog(  
					null,
					"Do you want to take $100? (Otherwise, you can go to nearest Cap Company.)",
					null,
					JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){
				currentPlayer.takeMoney(100);
			}else{
				GameFlow.birthdayGiftJob(currentPlayer);
				String[] squareInf = getSquareInf(currentPlayer.getLoc());
				changeItemLoc(currentPlayer, Integer.parseInt(squareInf[1]), Integer.parseInt(squareInf[2])); 
				JOptionPane.showMessageDialog(null, "Now, you directed to " + GameFlow.getLandName(currentPlayer.getLoc()));

				LandCards currentCabCo = GameFlow.getLandObject(currentPlayer.getLoc());
				if(!currentCabCo.IsSold()){
					int b = JOptionPane.showConfirmDialog(  
							null,
							"Do you want to buy?",
							null,
							JOptionPane.YES_NO_OPTION);
					if(b == JOptionPane.YES_OPTION){

						LinkedList<Cards> playerCardsList = currentPlayer.getCardsList();
						boolean hasBargain = false;
						for (int i = 0; i < playerCardsList.size(); i++) {
							if(playerCardsList.get(i).equals(GameFlow.getBargainCard())){

								hasBargain = true;
								int p = JOptionPane.showConfirmDialog(  
										null,
										"Do you want to use your bargain card?!" ,
										"Bargain",
										JOptionPane.YES_NO_OPTION);

								if(p == JOptionPane.YES_OPTION){
									if(currentPlayer.getMoney() >= currentCabCo.getLandPrice()){
										GameFlow.landJob(currentPlayer,currentCabCo, true);
										updateAssets(currentPlayer); 
									}else{
										JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentCabCo);
									}
									break;
								}else{
									if(currentPlayer.getMoney() >= currentCabCo.getLandPrice()){
										GameFlow.landJob(currentPlayer,currentCabCo, false);
										updateAssets(currentPlayer); 
									}else{
										JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentCabCo);
									}
									break;
								}

							}
						}  

						if(!hasBargain){ 
							if(currentPlayer.getMoney() >= currentCabCo.getLandPrice()){
								GameFlow.landJob(currentPlayer,currentCabCo, false);
								updateAssets(currentPlayer); 
							}else{
								JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentCabCo);
							}
						}
					}else{
						JOptionPane.showMessageDialog(null, "OK. It is your choice.");
					}
				}
			}

		}else if(Arrays.equals(currentPlayerLoc, UTILITY_1_LOC)||Arrays.equals(currentPlayerLoc, UTILITY_2_LOC)||Arrays.equals(currentPlayerLoc, UTILITY_3_LOC)||
				Arrays.equals(currentPlayerLoc, UTILITY_4_LOC)||Arrays.equals(currentPlayerLoc, UTILITY_5_LOC)||Arrays.equals(currentPlayerLoc, UTILITY_6_LOC)||
				Arrays.equals(currentPlayerLoc, UTILITY_7_LOC)||Arrays.equals(currentPlayerLoc, UTILITY_8_LOC)){	
			LandCards currentLand = GameFlow.getLandObject(currentPlayerLoc); 
			String currentLandName = currentLand.getName();
			JOptionPane.showMessageDialog(null, "You are in " + currentLandName + " Utility Location.");
			if(currentLand.isSold()){
				utilityRentJob(currentPlayer, currentLand.getLandOwner());
			}else{
				int n = JOptionPane.showConfirmDialog(  
						null,
						"Price of "+ currentLandName +" is $"+ currentLand.getLandPrice() + "\n" + 
								"Do you want to buy? ",
								currentLandName,
								JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION){
					LinkedList<Cards> playerCardsList = currentPlayer.getCardsList();
					boolean hasBargain = false; //Assumes player hasn't Bargain Business Card.
					for (int i = 0; i < playerCardsList.size(); i++) { //Looks if the player has Bargain Business Card.
						if(playerCardsList.get(i).equals(GameFlow.getBargainCard())){
							hasBargain = true; //We are in. So player has Bargain Business Card.
							int p = JOptionPane.showConfirmDialog(  
									null,
									"Do you want to use your bargain card?!" ,
									"Bargain",
									JOptionPane.YES_NO_OPTION);
							if(p == JOptionPane.YES_OPTION){ //But the land for $100 via Bargain Business Card.
								if(currentPlayer.getMoney() >= 100){
									GameFlow.landJob(currentPlayer,currentLand, true);
									//Updating the assets table.
									updateAssets(currentPlayer); 
								}else{
									JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentLandName);
								}
								break;
							}else{ //Player chose to buy at normal price.
								if(currentPlayer.getMoney() >= currentLand.getLandPrice()){
									GameFlow.landJob(currentPlayer,currentLand, false);
									//Updating the assets table.
									updateAssets(currentPlayer); 
								}else{
									JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentLandName);
								}
								break;
							}
						}
					}  // for loop ends here, means player has no Bargain Business Card.
					if(!hasBargain){ //If hasBargain is still false, that means player doesn't have Bargain Business Card.
						if(currentPlayer.getMoney() >= currentLand.getLandPrice()){
							GameFlow.landJob(currentPlayer,currentLand, false);
							//Updating the assets table.
							updateAssets(currentPlayer); 
						}else{
							JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentLandName);
						}
					}
				}
				currentLand.setAuction(true);

				JOptionPane.showMessageDialog(null, currentPlayer.getName()+"Your turn to offer a bid for "+currentLand.getName());
				int x=Integer.parseInt(JOptionPane.showInputDialog(null, "", "Enter your bid",0 ));

				currentPlayer.setBid(x);
				bids.add(x);

			}	

		}else if(Arrays.equals(currentPlayerLoc,SQUEEZE_PLAY_LOC)){
			JOptionPane.showMessageDialog(null, "Welcome! You are in Squeeze Location");
			final JFrame squeezeFrame = new JFrame("Squueze Play");
			Container container = squeezeFrame.getContentPane();
			squeezeFrame.setVisible(true);
			squeezeFrame.setSize(500, 200);
			JPanel rollPanel = new JPanel();
			JPanel labelPanel = new JPanel();
			final JButton rollDiceButton = new JButton("Roll Dice");
			rollPanel.add(rollDiceButton);
			rollPanel.setBounds(100, 0, 300, 50);
			final JLabel rollLabel = new JLabel("Click Role Dice button.");
			labelPanel.add(rollLabel);
			labelPanel.setBounds(125, 100, 300, 100);
			squeezeFrame.add(rollPanel);
			container.add(labelPanel);
			container.add(rollPanel);
			rollDiceButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RegularDice dice = new RegularDice();
					dice.roll();
					int first = dice.getDice1();
					int second = dice.getDice2();
					int sum = first + second;
					rollLabel.setText("Rolled: " + sum);
					int beforeGameMoney = currentPlayer.getMoney();
					GameFlow.squeezePlayJob(currentPlayer, sum);
					int afterGameMoney = currentPlayer.getMoney();
					rollDiceButton.setEnabled(false);
					int winningMoney = afterGameMoney - beforeGameMoney;
					if(winningMoney == 0)
						JOptionPane.showMessageDialog(null, "Sorry, you couldn't earn money!");
					else
						JOptionPane.showMessageDialog(null, "Well Done! You earned $" + winningMoney + "");
					squeezeFrame.dispose();
					MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
					monoModel.refresh(); 
				}
			});
		}else{ //Means, square is property.
			LandCards currentLand = GameFlow.getLandObject(currentPlayerLoc);
			String currentLandName = currentLand.getName();
			JOptionPane.showMessageDialog(null, "Welcome! You are in " + currentLandName);
			boolean sold= currentLand.isSold();

			if(sold){ //Land is already sold.

				if(!currentLand.getLandOwner().equals(currentPlayer)){//Means, the player visits his/her place. Nothing to do if it is the case.

					JOptionPane.showMessageDialog(null, "This place has been already sold.Its owner is: "+ currentLand.getLandOwner().getName());
					if(currentPlayer.isHasCompedRoom()){
						int p = JOptionPane.showConfirmDialog(  
								null,
								"Do you want to use your Comped Room card?" ,
								"",
								JOptionPane.YES_NO_OPTION);
						if(p == JOptionPane.YES_OPTION){ 
							currentPlayer.removeCard(GameFlow.getcompedRoomCard());
							currentPlayer.setHasCompedRoom(false);
						} else{
							JOptionPane.showMessageDialog(null, "You have to pay $" + currentLand.getCurrentRent() + " rent.");
						}

					}else if(currentLand.getLandOwner().getRenovationSuccess()){
						Player.trade(currentPlayer, currentLand.getLandOwner(),currentLand.getCurrentRent()  + 50);    //Paying the rent with extra $50.
						JOptionPane.showMessageDialog(null, "You have to pay $"+ (currentLand.getCurrentRent()+ 50) +" rent.");
						displayWarningMessage(currentPlayer);
						currentLand.getLandOwner().setRenovationSuccess(false);

					}else if(currentPlayer.isSpecialOnlinePricing()){
						if(((currentPlayer.getLoc()[0]==1) && (currentPlayer.getLoc()[1]==7))||
								((currentPlayer.getLoc()[0]==1) && (currentPlayer.getLoc()[1]==35))||
								((currentPlayer.getLoc()[0]==2) && (currentPlayer.getLoc()[1]==15))||
								((currentPlayer.getLoc()[0]==2) && (currentPlayer.getLoc()[1]==35))){
							Player.trade(currentPlayer, currentLand.getLandOwner(),currentLand.getCurrentRent() / 2);    //Paying the rent with extra $50.
							JOptionPane.showMessageDialog(null, "You have to pay $"+ (currentLand.getCurrentRent() / 2) +" rent.");
							displayWarningMessage(currentPlayer);
							currentLand.getLandOwner().setSpecialOnlinePricing(false);
						}else{
							Player.trade(currentPlayer, currentLand.getLandOwner(),currentLand.getCurrentRent()); //Paying the rent.
							JOptionPane.showMessageDialog(null, "You have to pay $"+ (currentLand.getCurrentRent()) +" rent.");
							displayWarningMessage(currentPlayer);
						}

						//					}else if(currentLand.getLandOwner().hasMajorityOwnerShip(currentLand.getLandColor())&&
						//							currentLand.getLandOwner().NumberofHouse(currentLand)==0 && currentLand.getLandOwner().NumberofHotel(currentLand)==0 &&
						//							currentLand.getLandOwner().NumberofSkyscrapper(currentLand)==0){
						//						Player.trade(currentPlayer, currentLand.getLandOwner(),currentLand.getRent0()*2);    //Paying the rent with extra $50.
						//						JOptionPane.showMessageDialog(null, "You have to pay $"+ currentLand.getRent0()*2 +" rent.");
						//						displayWarningMessage(currentPlayer);
						//
						//					}else if(currentLand.getLandOwner().hasMonopoly(currentLand.getLandColor()) &&
						//							currentLand.getLandOwner().NumberofHouse(currentLand)==0 && currentLand.getLandOwner().NumberofHotel(currentLand)==0 &&
						//							currentLand.getLandOwner().NumberofSkyscrapper(currentLand)==0){
						//						Player.trade(currentPlayer, currentLand.getLandOwner(),currentLand.getRent0()*3);    //Paying the rent with extra $50.
						//						JOptionPane.showMessageDialog(null, "You have to pay $"+ currentLand.getRent0()*3 +" rent.");
						//						displayWarningMessage(currentPlayer);

					}else{
						Player.trade(currentPlayer, currentLand.getLandOwner(),currentLand.getCurrentRent()); //Paying the rent.
						JOptionPane.showMessageDialog(null, "You have to pay $"+ (currentLand.getCurrentRent()) +" rent.");
						displayWarningMessage(currentPlayer);
					}
				}
			}else{ //Land is not sold. 
				int n = JOptionPane.showConfirmDialog(  
						null,
						"Price of "+ currentLandName +" is $"+ currentLand.getLandPrice() + "\n" + 
								"Do you want to buy? ",
								currentLandName,
								JOptionPane.YES_NO_OPTION);

				if(n == JOptionPane.YES_OPTION){
					LinkedList<Cards> playerCardsList = currentPlayer.getCardsList();
					boolean hasBargain = false; //Assumes player hasn't Bargain Business Card.
					for (int i = 0; i < playerCardsList.size(); i++) { //Looks if the player has Bargain Business Card.

						if(playerCardsList.get(i).equals(GameFlow.getBargainCard())){
							hasBargain = true; //We are in. So player has Bargain Business Card.
							int p = JOptionPane.showConfirmDialog(  
									null,
									"Do you want to use your bargain card?!" ,
									"Bargain",
									JOptionPane.YES_NO_OPTION);

							if(p == JOptionPane.YES_OPTION){ //But the land for $100 via Bargain Business Card.
								if(currentPlayer.getMoney() >= currentLand.getLandPrice()){
									GameFlow.landJob(currentPlayer,currentLand, true);
									//Updating the assets table.
									updateAssets(currentPlayer); 
								}else{
									JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentLandName);
								}
								break;
							}else{ //Player chose to buy at normal price.
								if(currentPlayer.getMoney() >= currentLand.getLandPrice()){
									GameFlow.landJob(currentPlayer,currentLand, false);
									//Updating the assets table.
									updateAssets(currentPlayer); 
								}else{
									JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentLandName);
								}
								break;
							}

						}


					}  // for loop ends here, means player has no Bargain Business Card.

					if(!hasBargain){ //If hasBargain is still false, that means player doesn't have Bargain Business Card.
						if(currentPlayer.getMoney() >= currentLand.getLandPrice()){
							GameFlow.landJob(currentPlayer,currentLand, false);
							//Updating the assets table.
							updateAssets(currentPlayer); 
						}else{
							JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentLandName);
						}
					}

				}else{ //Player didn't want to buy.
					JOptionPane.showMessageDialog(null,"Well, you did not buy "+currentLand.getName()+"So it is on auction!");
					currentLand.setAuction(true);

					JOptionPane.showMessageDialog(null, currentPlayer.getName()+"Your turn to offer a bid for "+currentLand.getName());
					int x=Integer.parseInt(JOptionPane.showInputDialog(null, "", "Enter your bid",0 ));

					currentPlayer.setBid(x);
					bids.add(x);
				}
			}

		}
		//Need to refresh, now. Money may have changed
		MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
		monoModel.refresh(); 
	}


	public void getNextPlayer(){
		p = (p + 1) % players.size();
		currentPlayer = players.get(p);
		currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());
	}

	public void changeItemLoc(Player player, int x, int y){
		if(player.equals(players.get(0)))
			board.setPlayer1Loc(x, y);
		else if(player.equals(players.get(1)))
			board.setPlayer2Loc(x+10, y);
		else if(player.equals(players.get(2)))
			board.setPlayer3Loc(x, y+10);
		else if(player.equals(players.get(3)))
			board.setPlayer4Loc(x+10, y+10);
		else if(player.equals(players.get(4)))
			board.setPlayer5Loc(x, y+20);
		else if(player.equals(players.get(5)))
			board.setPlayer6Loc(x+10, y+20);
		else if(player.equals(players.get(6)))
			board.setPlayer7Loc(x, y+30);
		else if(player.equals(players.get(7)))
			board.setPlayer8Loc(x+10, y+30);
		repaint();
	}

	public void removePlayerAssets(Player player){
		if(player.equals(players.get(0)))
			player1AssestTable.setVisible(false);
		else if(player.equals(players.get(1)))
			player2AssestTable.setVisible(false);
		else if(player.equals(players.get(2)))
			player3AssestTable.setVisible(false);
		else if(player.equals(players.get(3)))
			player4AssestTable.setVisible(false);
		else if(player.equals(players.get(4)))
			player5AssestTable.setVisible(false);
		else if(player.equals(players.get(5)))
			player6AssestTable.setVisible(false);
		else if(player.equals(players.get(6)))
			player7AssestTable.setVisible(false);
		else if(player.equals(players.get(7)))
			player8AssestTable.setVisible(false);
		repaint();
	}

	public void removeOneAsset(Player player){
		if(player.equals(players.get(0))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player1AssestTable.getModel();
			assetsModel.remove();
		}else if(player.equals(players.get(1))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player2AssestTable.getModel();
			assetsModel.remove();
		}else if(player.equals(players.get(2))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player3AssestTable.getModel();
			assetsModel.remove();
		}else if(player.equals(players.get(3))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player4AssestTable.getModel();
			assetsModel.remove();
		}else if(player.equals(players.get(4))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player5AssestTable.getModel();
			assetsModel.remove();
		}else if(player.equals(players.get(5))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player6AssestTable.getModel();
			assetsModel.remove();
		}else if(player.equals(players.get(6))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player7AssestTable.getModel();
			assetsModel.remove();
		}else if(player.equals(players.get(7))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player8AssestTable.getModel();
			assetsModel.remove();
		}
	}

	public void updateAssetsAtStart(Player player){
		if(player.equals(players.get(0))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player1AssestTable.getModel();
			assetsModel.add();				
		}else if(player.equals(players.get(1))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player2AssestTable.getModel();
			assetsModel.add();
		}else if(player.equals(players.get(2))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player3AssestTable.getModel();
			assetsModel.add();
		}else if(player.equals(players.get(3))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player4AssestTable.getModel();
			assetsModel.add();
			doRemaningUnvisible(4);
		}else if(player.equals(players.get(4)) || players.size() == 5 ){
			if(!player5ScrollPane.isVisible()){
				player5ScrollPane.setVisible(true);
			}
			player5AssestTable.setModel(new PlayerAssetsTableModel(4));
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player5AssestTable.getModel();
			assetsModel.add();
			doRemaningUnvisible(5);
		}else if(player.equals(players.get(5)) || players.size() == 6){
			if(!player6ScrollPane.isVisible()){
				player6ScrollPane.setVisible(true);
			}
			player6AssestTable.setModel(new PlayerAssetsTableModel(5));
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player6AssestTable.getModel();
			assetsModel.add();
			doRemaningUnvisible(6);
		}else if(player.equals(players.get(6)) || players.size() == 7){
			if(!player7ScrollPane.isVisible()){
				player7ScrollPane.setVisible(true);
			}
			player7AssestTable.setModel(new PlayerAssetsTableModel(6));
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player7AssestTable.getModel();
			assetsModel.add();
			doRemaningUnvisible(7);
		}else if(player.equals(players.get(7)) || players.size() == 8){
			if(!player8ScrollPane.isVisible()){
				player8ScrollPane.setVisible(true);
			}
			player8AssestTable.setModel(new PlayerAssetsTableModel(7));
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player8AssestTable.getModel();
			assetsModel.add();
		}
	}

	public void doRemaningUnvisible(int begin){
		if (begin == 4) {
			player5ScrollPane.setVisible(false);
			player6ScrollPane.setVisible(false);
			player7ScrollPane.setVisible(false);
			player8ScrollPane.setVisible(false);
		}

		if(begin == 5){
			player6ScrollPane.setVisible(false);
			player7ScrollPane.setVisible(false);
			player8ScrollPane.setVisible(false);
		}

		if(begin == 6){
			player7ScrollPane.setVisible(false);
			player8ScrollPane.setVisible(false);
		}

		if(begin == 7){
			player8ScrollPane.setVisible(false);
		}
	}

	public void updateAssets(Player player){
		if(player.equals(players.get(0))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player1AssestTable.getModel();
			assetsModel.add();				
		}else if(player.equals(players.get(1))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player2AssestTable.getModel();
			assetsModel.add();
		}else if(player.equals(players.get(2))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player3AssestTable.getModel();
			assetsModel.add();
		}else if(player.equals(players.get(3))){
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player4AssestTable.getModel();
			assetsModel.add();
		}else if(player.equals(players.get(4))){
			if(!player5ScrollPane.isVisible()){
				player5ScrollPane.setVisible(true);
			}
			player5AssestTable.setModel(new PlayerAssetsTableModel(4));
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player5AssestTable.getModel();
			assetsModel.add();
		}else if(player.equals(players.get(5))){
			if(!player6ScrollPane.isVisible()){
				player6ScrollPane.setVisible(true);
			}
			player6AssestTable.setModel(new PlayerAssetsTableModel(5));
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player6AssestTable.getModel();
			assetsModel.add();
		}else if(player.equals(players.get(6))){
			if(!player7ScrollPane.isVisible()){
				player7ScrollPane.setVisible(true);
			}
			player7AssestTable.setModel(new PlayerAssetsTableModel(6));
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player7AssestTable.getModel();
			assetsModel.add();
		}else if(player.equals(players.get(7))){
			if(!player8ScrollPane.isVisible()){
				player8ScrollPane.setVisible(true);
			}
			player8AssestTable.setModel(new PlayerAssetsTableModel(7));
			PlayerAssetsTableModel assetsModel = (PlayerAssetsTableModel) player8AssestTable.getModel();
			assetsModel.add();
		}
	}

	public String[] getSquareInf(int[] currentPlayerLoc){
		LinkedList<String[]> squareList = squareMap.get(currentPlayerLoc[0]);
		String[] squareInf = squareList.get(currentPlayerLoc[1]);
		return squareInf;
	}

	public void displayWarningMessage(Player player){
		if(player.getMoney() < 0)
			JOptionPane.showMessageDialog(null, "If you push End Turn button before increasing your money,\ngame is over for you.", null, JOptionPane.WARNING_MESSAGE);
	}

	public void utilityRentJob(final Player currentPlayer,final Player owner){
		int x=owner.getUtilityCount();
		if(!currentPlayer.isDrawedAdvancingUtilityCard()){
			int rent=0;
			if(speedDieFacedValue.equals("MrMonopoly")||speedDieFacedValue.equals("Bus")){
				int sum=facingValue1 +facingValue2;
				switch (x) {
				case 1:  rent=sum*4;
				break;
				case 2:  rent=sum*10;
				break;
				case 3:  rent=sum*20;
				break;
				case 4: rent=sum*40;
				break;
				case 5:  rent=sum*80;
				break;
				case 6:  rent=sum*100;
				break;
				case 7:  rent=sum*120;
				break;
				case 8:  rent=sum*150;
				break;
				}
				JOptionPane.showMessageDialog(null, "You have to pay $"+rent+" rent to "+ owner.getName());
				Player.trade(currentPlayer,owner,rent);
			}else{
				int sum=facingValue1+facingValue2+Integer.parseInt(speedDieFacedValue);
				switch (x) {
				case 1:  rent=sum*4;
				break;
				case 2:  rent=sum*10;
				break;
				case 3:  rent=sum*20;
				break;
				case 4: rent=sum*40;
				break;
				case 5:  rent=sum*80;
				break;
				case 6:  rent=sum*100;
				break;
				case 7:  rent=sum*120;
				break;
				case 8:  rent=sum*150;
				break;
				}
				JOptionPane.showMessageDialog(null, "You have to pay $"+rent+" rent to "+ owner.getName());
				Player.trade(currentPlayer,owner,rent);
			}
		}else{
			final JFrame rollFrame = new JFrame("Roll dice");
			Container container = rollFrame.getContentPane();
			rollFrame.setVisible(true);
			rollFrame.setSize(500, 200);
			JPanel rollPanel = new JPanel();
			JPanel labelPanel = new JPanel();
			final JButton rollOnceButton = new JButton("Roll Dice");
			rollPanel.add(rollOnceButton);
			rollPanel.setBounds(100, 0, 300, 50);
			final JLabel rollLabel = new JLabel("Roll Dice");
			labelPanel.add(rollLabel);
			labelPanel.setBounds(125, 100, 300, 100);
			container.add(labelPanel);
			container.add(rollPanel);
			rollOnceButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int rent=0;
					int sum=0;
					sum = RegularDice.rollOneDice(); //Roll dice
					rollLabel.setText("Rolled: " + sum);
					rent=10*sum;
					JOptionPane.showMessageDialog(null, "Dice rolled " +sum+ ".You have to pay "+rent+" to "+owner);
					Player.trade(currentPlayer,owner,rent);
					currentPlayer.setDrawedAdvancingUtilityCard(false);
					rollOnceButton.setEnabled(false);
					rollFrame.dispose();
					MonopolyTableModel monoModel = (MonopolyTableModel) dataTable.getModel();
					monoModel.refresh(); 
				}
			});
		}
	}
	public Player findTheWinner(List<Player> lst,int max ){
		if(GameFlow.getPlayerList().get(0).getBid()==GameFlow.getPlayerList().get(1).getBid()&&GameFlow.getPlayerList().get(1).getBid()==GameFlow.getPlayerList().get(2).getBid()&&GameFlow.getPlayerList().get(2).getBid()==GameFlow.getPlayerList().get(3).getBid()&&GameFlow.getPlayerList().get(2).getBid()==GameFlow.getPlayerList().get(1).getBid()){
			int index = randomGenerator.nextInt(GameFlow.getPlayerList().size());
			return GameFlow.getPlayerList().get(index);
		}else{ 
			for(int i=0; i<GameFlow.getPlayerList().size(); i++){
				if(GameFlow.getPlayerList().get(i).getBid()==max) 
					return GameFlow.getPlayerList().get(i); 
			}
		}
		return null; 
	}
	public Stock findAuctionedStock(){

		Stock stock=null;
		for (int i = 0; i < stockArray.length; i++) {

			if(stockArray[i].getAuction()[0].equals(true)){
				stock=stockArray[i];
				break;
			}
		}
		return stock;
	}
	public static LinkedList<LandCards> getCardsInTheBank(){

		for(int j=0; j<GameFlow.getLandMap().get(1).size(); j++){
			if(GameFlow.getLandMap().get(1).get(j)!=null &&!(GameFlow.getLandMap().get(1).get(j).getName().equalsIgnoreCase("NoLand")))
				lst1.add(GameFlow.getLandMap().get(1).get(j));
		}
		for(int j=0; j<GameFlow.getLandMap().get(2).size(); j++){
			if(GameFlow.getLandMap().get(2).get(j)!=null &&!(GameFlow.getLandMap().get(2).get(j).getName().equalsIgnoreCase("NoLand")))
				lst2.add(GameFlow.getLandMap().get(2).get(j));
		}
		for(int j=0; j<GameFlow.getLandMap().get(3).size(); j++){
			if(GameFlow.getLandMap().get(3).get(j)!=null &&!(GameFlow.getLandMap().get(3).get(j).getName().equalsIgnoreCase("NoLand")))
				lst3.add(GameFlow.getLandMap().get(3).get(j));
		}

		for(int i=0; i< lst1.size(); i++){
			if(!(lst1.get(i).isSold())) cardsInTheBank.add(lst1.get(i));
		}
		for(int i=0; i< lst2.size(); i++){
			if(!(lst2.get(i).isSold())) cardsInTheBank.add(lst2.get(i));
		}
		for(int i=0; i< lst3.size(); i++){
			if(!(lst3.get(i).isSold())) cardsInTheBank.add(lst3.get(i));
		}
		return cardsInTheBank;

	}
	public  String[] getCardsNameInTheBank(){


		List<String> newlist=new ArrayList<String>();

		for (int i = 0; i < cardsInTheBank.size(); i++) {
			newlist.add( cardsInTheBank.get(i).getName());
		}

		int x=newlist.size();
		String[] getCardsInTheBank = new String[x]; 
		for (int i = 0; i < newlist.size(); i++) {
			getCardsInTheBank[i] = newlist.get(i);
		}

		return getCardsInTheBank;
	}
	public static LandCards findLandFromName(String str){

		LandCards correspondingCardToName = null;
		LinkedList<LandCards> lands = getCardsInTheBank();
		for (LandCards landCard : lands) {
			if(landCard.getName().equals(str)){ //Object is found.
				correspondingCardToName = landCard;
				break; //Don't have to look for other objects, since there is no copy of cards.
			}
		}
		return correspondingCardToName;
	}
	public LandCards findAuctionCard(){
		LandCards auctionCard=null;
		LinkedList<LandCards> cardsInBank = getCardsInTheBank();
		for(int j=0; j<cardsInBank.size(); j++){
			if(cardsInBank.get(j).getAuction()){
				auctionCard=getCardsInTheBank().get(j);
				break;
			}
		}
		return auctionCard;

	}
	public int getSumOfDice(){
		return sumOfDices;
	}
	public LandCards getLandWithHighestRent(ArrayList<LandCards> lst){
		LandCards landWithHighestRent=lst.get(0);
		for(int i=1; i<lst.size(); i++){
			if(lst.get(i).getCurrentRent()>landWithHighestRent.getCurrentRent())
				landWithHighestRent=lst.get(i);
		}
		return landWithHighestRent;
	}


	public static void main(String[] args) {
		Monopoly_GUI land = new Monopoly_GUI();
		land.setTitle("MONOPOLY");	
		land.setSize(1275 , 750);
		land.setVisible(true);
		land.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
