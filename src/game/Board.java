package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel {
	private static final long serialVersionUID = 1L;

	private int player1X = Integer.MAX_VALUE, player2X = Integer.MAX_VALUE, player3X  = Integer.MAX_VALUE, player4X = Integer.MAX_VALUE;
	private int player5X = Integer.MAX_VALUE, player6X = Integer.MAX_VALUE, player7X  = Integer.MAX_VALUE, player8X = Integer.MAX_VALUE;

	private int player1Y = Integer.MAX_VALUE, player2Y = Integer.MAX_VALUE, player3Y = Integer.MAX_VALUE, player4Y = Integer.MAX_VALUE;
	private int player5Y = Integer.MAX_VALUE, player6Y = Integer.MAX_VALUE, player7Y = Integer.MAX_VALUE, player8Y = Integer.MAX_VALUE;

	public Board(){

	}

	//Setting items location, separately.
	public void setPlayer1Loc(int player1X, int player1Y){
		this.player1X = player1X;
		this.player1Y = player1Y;
	}

	public void setPlayer2Loc(int player2X, int player2Y){
		this.player2X = player2X;
		this.player2Y = player2Y;
	}

	public void setPlayer3Loc(int player3X, int player3Y){
		this.player3X = player3X;
		this.player3Y = player3Y;
	}

	public void setPlayer4Loc(int player4X, int player4Y){
		this.player4X = player4X;
		this.player4Y = player4Y;
	}
	
	public void setPlayer5Loc(int player5X, int player5Y){
		this.player5X = player5X;
		this.player5Y = player5Y;
	}
	
	public void setPlayer6Loc(int player6X, int player6Y){
		this.player6X = player6X;
		this.player6Y = player6Y;
	}
	
	public void setPlayer7Loc(int player7X, int player7Y){
		this.player7X = player7X;
		this.player7Y = player7Y;
	}
	
	public void setPlayer8Loc(int player8X, int player8Y){
		this.player8X = player8X;
		this.player8Y = player8Y;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Board
		ImageIcon icon20 = new ImageIcon("board.png");
		Image img20 = icon20.getImage();
		BufferedImage bi20 = new BufferedImage(img20.getWidth(this), img20.getHeight(this), BufferedImage.TYPE_INT_ARGB);
		//g.drawImage(img20, 350,75,425,425,this);
		g.drawImage(img20, 350,35,642,642,this);
		ImageIcon newIcon20 = new ImageIcon(bi20);
		newIcon20.paintIcon(this, g, 0, 0);



		//		//The corners.
		g.setColor(Color.BLACK);
		g.drawRect(508,445,71,71); //left below
		g.drawRect(431,522,73,73);
		g.drawRect(352,601,74,74); // outmost
		//		
		//		
		g.drawRect(760,445,71,71); //right below
		g.drawRect(837,522,73,73); 
		g.drawRect(916,601,74,74);  // outmost

		//		
		//		 
		g.drawRect(509,192,71,71);//left top
		g.drawRect(431,114,73,73);
		g.drawRect(350,35,74,74); // outmost

		//		
		//		
		//		
		g.drawRect(760,192,71,71); //right top
		g.drawRect(837,114,73,73);
		g.drawRect(916,35,74,74); // outmost




		for(int i=1; i<6; i++){  //Below row
			g.drawRect(543+i*36,445,36,71);
		}
		for(int i=1; i<10; i++){  
			g.drawRect(467+i*37,522,37,73);
		}
		for(int i=1; i<14; i++){  
			g.drawRect(386+i*38,601,38,74);
		}

		for(int i=1; i<6; i++){ //Left row
			g.drawRect(509,227+i*36,71 , 36);
		}
		for(int i=1; i<10; i++){ 
			g.drawRect(431,150+i*37,73,37);
		}
		for(int i=1; i<14; i++){ 
			g.drawRect(350,71+i*38,74 , 38);
		}

		for(int i=1; i<6; i++){  //Top row
			g.drawRect(544+i*36,192, 36, 71);
		}
		for(int i=1; i<10; i++){  
			g.drawRect(467+i*37,114, 37, 73);
		}
		for(int i=1; i<14; i++){ 
			g.drawRect(385+i*38,35, 38, 74);
		}


		for(int i=1; i<6; i++){ //Right row
			g.drawRect(760,227+i*36,71,36);
		}
		for(int i=1; i<10; i++){ 
			g.drawRect(837,150+i*37,73,37);
		}
		for(int i=1; i<14; i++){ 
			g.drawRect(916,71+i*38,74,38);
		}

		// Players items and items' places are described here.

		ImageIcon player1Icon = new ImageIcon("1445196362_Luma - Blue.png");
		Image img = player1Icon.getImage();
		g.drawImage(img, player1X, player1Y, 
				25, 25, this);

		ImageIcon player2Icon = new ImageIcon("1445196360_Luma - Yellow.png");
		Image img2 = player2Icon.getImage();
		g.drawImage(img2, player2X, player2Y, 
				25,25 , this);

		ImageIcon player3Icon = new ImageIcon("1445196358_Luma - Red.png");
		Image img3 = player3Icon.getImage();
		g.drawImage(img3, player3X, player3Y, 
				25,25 , this);

		ImageIcon player4Icon = new ImageIcon("1445194496_Luma - Green.png");
		Image img4 = player4Icon.getImage();
		g.drawImage(img4, player4X, player4Y, 
				25,25 , this);
		
		ImageIcon player5Icon = new ImageIcon("1445194496_Luma - Green.png");
		img4 = player5Icon.getImage();
		g.drawImage(img4, player5X, player5Y, 
				25,25 , this);
		
		ImageIcon player6Icon = new ImageIcon("1445194496_Luma - Green.png");
		img4 = player6Icon.getImage();
		g.drawImage(img4, player6X, player6Y, 
				25,25 , this);
		
		ImageIcon player7Icon = new ImageIcon("1445194496_Luma - Green.png");
		img4 = player7Icon.getImage();
		g.drawImage(img4, player7X, player7Y, 
				25,25 , this);
		
		ImageIcon player8Icon = new ImageIcon("1445194496_Luma - Green.png");
		img4 = player8Icon.getImage();
		g.drawImage(img4, player8X, player8Y, 
				25,25 , this);
		

	}
}
