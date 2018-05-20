package game;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;


public class MonopolyTableModel extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	private String[] columnNames = {
			"Name",
			"Color",
			"Money",
	};
	private Object[][] rowData;

	private LinkedList<Player> representativePlayerList;

	public MonopolyTableModel() {
		Color color;
		try {
		representativePlayerList = (LinkedList<Player>) GameFlow.getPlayerList();
		rowData = new Object[representativePlayerList.size()][3];
		for (int i = 0; i < representativePlayerList.size(); i++) {
			Player player = representativePlayerList.get(i);
			rowData[i][0] = player.getName();
			Field field = Class.forName("java.awt.Color").getField(player.getColor().toUpperCase());
			color = (Color)field.get(null);
			rowData[i][1] = color;
			rowData[i][2] = player.getMoney();
		}
		
		}catch(Exception e) {
			color = null;
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

	@Override
	public void setValueAt(Object value, int row, int col) {
		rowData[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	public void refresh(){
		representativePlayerList = (LinkedList<Player>) GameFlow.getPlayerList();
		if (rowData.length != representativePlayerList.size())
			recreateTable();
		else
			for (int i = 0; i <representativePlayerList.size() ; i++) 
				setValueAt(representativePlayerList.get(i).getMoney(), i, 2);
	}
	
	public void recreateTable(){
		Color color;
		try {			
			rowData = new Object[representativePlayerList.size()][3];
			for (int i = 0; i < representativePlayerList.size(); i++) {
				Player player = representativePlayerList.get(i);
				rowData[i][0] = player.getName();
				Field field = Class.forName("java.awt.Color").getField(player.getColor().toUpperCase());
				color = (Color)field.get(null);
				rowData[i][1] = color;
				rowData[i][2] = player.getMoney();
			}

		}catch(Exception e) {
			color = null;
		}
		fireTableDataChanged();
	}

	@Override
	public Class getColumnClass(int columnIndex){
		switch(columnIndex){
		case 1:
			return Color.class;
		default:
			return Object.class;
		}
	}
}
