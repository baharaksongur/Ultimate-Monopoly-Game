package game;
import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;


public class ColorRenderer extends JLabel implements TableCellRenderer {
	
	
	private static final long serialVersionUID = 1L;

	public ColorRenderer(int p){
		setOpaque(true); //Must do this for background to show up.
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Color color = (Color) value;
        setBackground(color);	
		return this;

	}

}
