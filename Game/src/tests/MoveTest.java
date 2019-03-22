package tests;

import static org.junit.Assert.assertEquals;

import java.awt.event.MouseEvent;
import org.junit.Test;
import gamepackage.Board;
import gamepackage.BoardImage;
import gamepackage.MoveListener;

public class MoveTest {

	@Test
	public void mousePressedTest() {
		BoardImage boardImage = new BoardImage();
		Board board = new Board(2,boardImage);
		boardImage.setBoard(board);
		MoveListener ml = new MoveListener(boardImage,board.getCircles());
		
		//Click beyond circle.
		MouseEvent me = new MouseEvent(boardImage,1,0,0,0,0,1,false);
		ml.mousePressed(me);
		assertEquals(ml.getClickedCircle(),-1);
		
		//Click on circle.
		me = new MouseEvent(boardImage,1,0,0,270,105,1,false);
		ml.mousePressed(me);
		assertEquals(ml.getClickedCircle(),6);
	}
	
	@Test
	public void mouseReleasedTest() {
		BoardImage boardImage = new BoardImage();
		Board board = new Board(2,boardImage);
		boardImage.setBoard(board);
		MoveListener ml = new MoveListener(boardImage,board.getCircles());
		
		//Click and release not on circles
		ml.setClickedCircle(-1);
		MouseEvent me = new MouseEvent(boardImage,1,0,0,10,10,1,false);
		ml.mouseReleased(me);
		assertEquals(ml.getReleasedCircle(),-1);
		
		//Click and release on different circles
		ml.setClickedCircle(6);
		me = new MouseEvent(boardImage,1,0,0,255,135,1,false);
		ml.mouseReleased(me);
		assertEquals(ml.getReleasedCircle(),14);
		
		//Click not on circle and release on circle
		ml.setClickedCircle(-1);
		ml.setReleasedCircle(-1);
		me = new MouseEvent(boardImage,1,0,0,255,135,1,false);
		ml.mouseReleased(me);
		assertEquals(ml.getReleasedCircle(),-1);
		
		//Release on the same circle
		ml.setClickedCircle(6);
		ml.setReleasedCircle(-1);
		me = new MouseEvent(boardImage,1,0,0,270,105,1,false);
		ml.mouseReleased(me);
		assertEquals(ml.getReleasedCircle(),-1);
	}
}
