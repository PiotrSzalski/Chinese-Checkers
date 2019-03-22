package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.awt.Color;
import org.junit.Test;
import gamepackage.Board;
import states.State;
import states.StateController;

public class StateTest {
	
	@Test
	public void testForTwoPlayers() {
		StateController controller = new StateController();
		controller.setState(2);
		assertArrayEquals(controller.getState().setDestinationForPlayer(0),State.player5);
		assertArrayEquals(controller.getState().setDestinationForPlayer(1),State.player);
		Board board = new Board(2,null);
		controller.getState().setAreasForPlayers(board);
		assertEquals(board.getCircles().get(0).getColor(),Color.RED);
		assertEquals(board.getCircles().get(10).getColor(),Color.WHITE);
		assertEquals(board.getCircles().get(22).getColor(),Color.WHITE);
		assertEquals(board.getCircles().get(98).getColor(),Color.WHITE);
		assertEquals(board.getCircles().get(110).getColor(),Color.WHITE);
		assertEquals(board.getCircles().get(120).getColor(),Color.GREEN);
	}
	@Test
	public void testForThreePlayers() {
		Board board = new Board(3,null);
		StateController controller = new StateController();
		controller.setState(3);
		assertArrayEquals(controller.getState().setDestinationForPlayer(0),State.player5);
		assertArrayEquals(controller.getState().setDestinationForPlayer(1),State.player1);
		assertArrayEquals(controller.getState().setDestinationForPlayer(2),State.player2);
		controller.getState().setAreasForPlayers(board);
		assertEquals(board.getCircles().get(0).getColor(),Color.RED);
		assertEquals(board.getCircles().get(10).getColor(),Color.WHITE);
		assertEquals(board.getCircles().get(22).getColor(),Color.WHITE);
		assertEquals(board.getCircles().get(98).getColor(),Color.BLUE);
		assertEquals(board.getCircles().get(110).getColor(),Color.GREEN);
		assertEquals(board.getCircles().get(120).getColor(),Color.WHITE);
	}
	@Test
	public void testForFourPlayers() {
		Board board = new Board(4,null);
		StateController controller = new StateController();
		controller.setState(4);
		assertArrayEquals(controller.getState().setDestinationForPlayer(0),State.player4);
		assertArrayEquals(controller.getState().setDestinationForPlayer(1),State.player3);
		assertArrayEquals(controller.getState().setDestinationForPlayer(2),State.player1);
		assertArrayEquals(controller.getState().setDestinationForPlayer(3),State.player2);
		controller.getState().setAreasForPlayers(board);
		assertEquals(board.getCircles().get(0).getColor(),Color.WHITE);
		assertEquals(board.getCircles().get(10).getColor(),Color.RED);
		assertEquals(board.getCircles().get(22).getColor(),Color.GREEN);
		assertEquals(board.getCircles().get(98).getColor(),Color.CYAN);
		assertEquals(board.getCircles().get(110).getColor(),Color.BLUE);
		assertEquals(board.getCircles().get(120).getColor(),Color.WHITE);
	}
	@Test
	public void testForSixPlayers() {
		Board board = new Board(6,null);
		StateController controller = new StateController();
		controller.setState(6);
		assertArrayEquals(controller.getState().setDestinationForPlayer(0),State.player5);
		assertArrayEquals(controller.getState().setDestinationForPlayer(1),State.player3);
		assertArrayEquals(controller.getState().setDestinationForPlayer(2),State.player1);
		assertArrayEquals(controller.getState().setDestinationForPlayer(3),State.player);
		assertArrayEquals(controller.getState().setDestinationForPlayer(4),State.player2);
		assertArrayEquals(controller.getState().setDestinationForPlayer(5),State.player4);
		controller.getState().setAreasForPlayers(board);
		assertEquals(board.getCircles().get(0).getColor(),Color.RED);
		assertEquals(board.getCircles().get(10).getColor(),Color.YELLOW);
		assertEquals(board.getCircles().get(22).getColor(),Color.GREEN);
		assertEquals(board.getCircles().get(98).getColor(),Color.PINK);
		assertEquals(board.getCircles().get(110).getColor(),Color.BLUE);
		assertEquals(board.getCircles().get(120).getColor(),Color.CYAN);
	}
}
