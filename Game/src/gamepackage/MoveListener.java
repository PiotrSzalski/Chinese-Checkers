package gamepackage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MoveListener implements MouseListener {

	private ArrayList<ColorCircle> circles;
	private BoardImage boardImage;
	private int clickedCircle;
	private int releasedCircle;
	
	public MoveListener(BoardImage boardImage, ArrayList<ColorCircle> circles) {
		this.circles = circles;
		this.boardImage = boardImage;
		this.clickedCircle = -1;
		this.releasedCircle = -1;
	}

	public void mouseClicked(MouseEvent arg0) {}
	
	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {
		releasedCircle = -1;
		for(int i=0;i<circles.size();i++) {
			if(circles.get(i).contains(arg0.getX(), arg0.getY())) {
				clickedCircle = i;
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) {
		for(int i=0;i<circles.size();i++) {
			if(circles.get(i).contains(arg0.getX(), arg0.getY())) {			
				if(clickedCircle != -1 && clickedCircle != i) {
					releasedCircle = i;
					boardImage.getBoard().move(clickedCircle, i, circles.get(clickedCircle).getColor());
					break;
				}	
			}
		}
		clickedCircle = -1;
	}
	 //For tests
	public int getClickedCircle() {
		return clickedCircle;
	}
	
	//For tests
	public void setClickedCircle(int circle) {
		this.clickedCircle = circle;
	}
	
	public int getReleasedCircle() {
		return releasedCircle;
	}
	
	//For tests
	public void setReleasedCircle(int circle) {
		this.releasedCircle = circle;
	}
		
}
