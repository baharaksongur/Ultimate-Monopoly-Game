package game;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;


public class PlayerAssetsTableModel extends AbstractTableModel  {

	private static final long serialVersionUID = 1L;

	//List containing the Player Objects.
	private LinkedList<Player> representativePlayerList;

	//List of Cards of the Player;
	private LinkedList<Cards> cardsList;
	private HashMap<Stock, Integer> sharedStocks;
	
	//Player's indices in the table, in order to be able to get the right player, constructor takes it.
	private int playerIndex;

	private String[] columnNames ;

	private Object[][] rowData;


	public PlayerAssetsTableModel(int playerIndex) {
		this.playerIndex = playerIndex;
		representativePlayerList = (LinkedList<Player>) GameFlow.getPlayerList();
		Player currentPlayer = representativePlayerList.get(playerIndex);
		this.cardsList = currentPlayer.getCardList();
		this.sharedStocks = currentPlayer.getNumberofSharedStocks();
		
		columnNames = new String[] {currentPlayer.getName(), "# Hous.", "# Hot.", "# Skyscr.", "# Sto."};
		
		int rowLength = cardsList.size() + sharedStocks.size();
		rowData = new Object[rowLength][5];
		int i = 0;
		while(i < cardsList.size()){
			Cards card = cardsList.get(i);
			if(!(card instanceof CommunityCards || card instanceof ChanceCards)){
				String cardName = card.getName();
				rowData[i][0] = cardName;
				rowData[i][1] = currentPlayer.getBuildings().get(card)[0];
				rowData[i][2] = currentPlayer.getBuildings().get(card)[1];
				rowData[i][3] = currentPlayer.getBuildings().get(card)[2];
				rowData[i][4] = currentPlayer.getBuildings().get(card)[3];
			}else{
				if(card instanceof CommunityCards)
					rowData[i][0] = ((CommunityCards) card).getCardType();
				else if(card instanceof ChanceCards)
					rowData[i][0] = ((ChanceCards) card).getCardType();
				rowData[i][1] = "-";
				rowData[i][2] = "-";
				rowData[i][3] = "-";
				rowData[i][4] = "-";
			}
			i++;
		}
		
		for (Stock stock : sharedStocks.keySet()) {
			int number = sharedStocks.get(stock);
			rowData[i][0] = stock.getName();
			rowData[i][1] = "-";
			rowData[i][2] = "-";
			rowData[i][3] = "-";
			rowData[i][4] = number;
		}
		
	}
	
	@Override
	public int getRowCount() {
		return rowData.length; 
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rowData[rowIndex][columnIndex];
	}

	public void setValueAt(Object value, int row, int col) {
		rowData[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	public void add(){
		Player currentPlayer = representativePlayerList.get(playerIndex);
		this.cardsList = currentPlayer.getCardList();
		this.sharedStocks = currentPlayer.getNumberofSharedStocks();
				
		int rowLength = cardsList.size() + sharedStocks.size();
		rowData = new Object[rowLength][5];
		int i = 0;
		while(i < cardsList.size()){
			Cards card = cardsList.get(i);
			if(!(card instanceof CommunityCards || card instanceof ChanceCards)){
				String cardName = card.getName();
				rowData[i][0] = cardName;
				rowData[i][1] = currentPlayer.getBuildings().get(card)[0];
				rowData[i][2] = currentPlayer.getBuildings().get(card)[1];
				rowData[i][3] = currentPlayer.getBuildings().get(card)[2];
				rowData[i][4] = currentPlayer.getBuildings().get(card)[3];
			}else{
				if(card instanceof CommunityCards)
					rowData[i][0] = ((CommunityCards) card).getCardType();
				else if(card instanceof ChanceCards)
					rowData[i][0] = ((ChanceCards) card).getCardType();
				rowData[i][1] = "-";
				rowData[i][2] = "-";
				rowData[i][3] = "-";
				rowData[i][4] = "-";
			}
			i++;
		}
		
		for (Stock stock : sharedStocks.keySet()) {
			int number = sharedStocks.get(stock);
			rowData[i][0] = stock.getName();
			rowData[i][1] = "-";
			rowData[i][2] = "-";
			rowData[i][3] = "-";
			rowData[i][4] = number;
		}
		fireTableDataChanged();
	}

	public void remove(){
		Player currentPlayer = representativePlayerList.get(playerIndex);
		this.cardsList = currentPlayer.getCardList();
		this.sharedStocks = currentPlayer.getNumberofSharedStocks();
				
		int rowLength = cardsList.size() + sharedStocks.size();
		rowData = new Object[rowLength][5];
		int i = 0;
		while(i < cardsList.size()){
			Cards card = cardsList.get(i);
			if(!(card instanceof CommunityCards || card instanceof ChanceCards)){
				String cardName = card.getName();
				rowData[i][0] = cardName;
				rowData[i][1] = currentPlayer.getBuildings().get(card)[0];
				rowData[i][2] = currentPlayer.getBuildings().get(card)[1];
				rowData[i][3] = currentPlayer.getBuildings().get(card)[2];
				rowData[i][4] = currentPlayer.getBuildings().get(card)[3];
			}else{
				if(card instanceof CommunityCards)
					rowData[i][0] = ((CommunityCards) card).getCardType();
				else if(card instanceof ChanceCards)
					rowData[i][0] = ((ChanceCards) card).getCardType();
				rowData[i][1] = "-";
				rowData[i][2] = "-";
				rowData[i][3] = "-";
				rowData[i][4] = "-";
			}
			i++;
		}
		
		for (Stock stock : sharedStocks.keySet()) {
			int number = sharedStocks.get(stock);
			rowData[i][0] = stock.getName();
			rowData[i][1] = "-";
			rowData[i][2] = "-";
			rowData[i][3] = "-";
			rowData[i][4] = number;
		}
		
		fireTableRowsDeleted(0, rowData.length);
	}
}
