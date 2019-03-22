package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import org.junit.Test;
import gamepackage.Board;
import gamepackage.BoardImage;
import gamepackage.ColorCircle;
import states.State;
import states.StateForTwoPlayers;

public class BoardTests {
	
	@Test
	public void opponentMoveTest() {
		BoardImage boardImage = new BoardImage();
		Board board = new Board(2,boardImage);
		boardImage.setBoard(board);
		State state = new StateForTwoPlayers();
		ArrayList<ColorCircle> circles = board.getCircles();
		circles.get(5).setColor(Color.WHITE);
		circles.get(9).setColor(Color.RED);
		state.setAreasForPlayers(board);
		board.opponentMove("5 9 255 0 0 0");
		assertEquals(board.getCircles(),circles);
	}
	@Test
	public void idTest() {
		Board board = new Board(2,null);
		board.setPlayerId(1);
		assertEquals(board.myId(),1);	
	}
	
	
}
