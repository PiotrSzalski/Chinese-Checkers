package gamepackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

public class BoardImage extends JPanel {

	private Board board;
	
	public BoardImage() {
		super();
		setPreferredSize(new Dimension(400,400));
		setBackground(new Color(77,77,77));
	}
	
	public void setBoard(Board board) {
		this.board = board;
		addMouseListener(new MoveListener(this,board.getCircles()));
	}
	
	public Board getBoard() {
		return board;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D)g;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    g2.setRenderingHints(rh);
	    if(board != null)
	    {
	    	ArrayList<ColorCircle> circles = board.getCircles();
	    	for ( ColorCircle c : circles ) {
	    		g2.setColor(c.getColor());
	    		g2.fillOval((int)c.x,(int)c.y, (int)ColorCircle.width, (int)ColorCircle.height);
	    	}
	    }
	}
}
