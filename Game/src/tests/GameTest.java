package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.Color;
import org.junit.Test;
import gameServer.Game;
import states.State;

public class GameTest {


	@Test
	public void singeleMoveTest() {
		//Proper single move
		Game game = new Game(2);
		String line = "6 14 255 0 0";
		assertTrue(game.checkMoveProperiety(line, Color.RED, State.player5));
		//Try to move to occupied field
		game.reset();
		line = "8 9 255 0 0";
		assertFalse(game.checkMoveProperiety(line, Color.RED, State.player5));
		//Try to move other player's pawn
		game = new Game(3);
		line = "74 64 0 255 0";
		assertFalse(game.checkMoveProperiety(line, Color.RED, State.player5));
		//End of moves
		game = new Game(2);
		line = "6 14 255 0 0";
		assertTrue(game.checkMoveProperiety(line, Color.RED, State.player5));
		line = "14 15 255 0 0";
		assertFalse(game.checkMoveProperiety(line, Color.RED, State.player5));
	}
	
	@Test
	public void tooFarMove() {
		Game game = new Game(2);
		String line = "5 40 255 0 0";
		assertFalse(game.checkMoveProperiety(line, Color.RED, State.player5));
	}
	
	@Test
	public void jumpTest() {
		Game game = new Game(2);
		//First jump
		String line = "5 18 255 0 0";
		assertTrue(game.checkMoveProperiety(line, Color.RED, State.player5));
		//Try to jump another checker
		line = "4 17 255 0 0";
		assertFalse(game.checkMoveProperiety(line, Color.RED, State.player5));
		
		game.reset();
		//First jump
		line = "3 14 255 0 0";
		assertTrue(game.checkMoveProperiety(line, Color.RED, State.player5));
		//Try to move one field
		line = "14 13 255 0 0";
		assertFalse(game.checkMoveProperiety(line, Color.RED, State.player5));
		
		game.reset();
		//Try to jump above white
		line = "6 28 255 0 0 0";
		assertFalse(game.checkMoveProperiety(line, Color.RED, State.player5));
		
		game = new Game(2);
		//Double jump
		game.getBoard().getCircles().get(9).setColor(Color.WHITE);
		game.getBoard().getCircles().get(18).setColor(Color.RED);
		line = "2 9 255 0 0 0";
		assertTrue(game.checkMoveProperiety(line, Color.RED, State.player5));
		line = "9 31 255 0 0 0";
		assertTrue(game.checkMoveProperiety(line, Color.RED, State.player5));
	}
	
	
	@Test
	public void movesInDestinationArea() {
		Game game = new Game(3);
		game.getBoard().getCircles().get(111).setColor(Color.RED);
		//Move in destination area.
		String line = "111 112 255 0 0 0";
		assertTrue(game.checkMoveProperiety(line, Color.RED, State.player5));
		//Move beyond the destination area
		line = "112 103 255 0 0 0";
		assertFalse(game.checkMoveProperiety(line, Color.RED, State.player5));
	}
	
	@Test
	public void checkWinTest() {
		Game game = new Game(3);
		for(int i=0;i<9;i++) {
			game.getBoard().getCircles().get(i).setColor(Color.WHITE);
			game.getBoard().getCircles().get(i+111).setColor(Color.RED);
		}
		assertFalse(game.checkWin(State.player5, Color.RED));
		game.getBoard().getCircles().get(9).setColor(Color.WHITE);
		game.getBoard().getCircles().get(120).setColor(Color.RED);
		assertTrue(game.checkWin(State.player5, Color.RED));
	}
}
