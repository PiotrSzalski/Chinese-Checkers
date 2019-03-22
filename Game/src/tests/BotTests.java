package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

import gameServer.Bot;
import gameServer.Game;
import gameServer.Player;
import gamepackage.Board;

public class BotTests {

	@Test
	public void test() throws InterruptedException {
		Game game = new Game(2);
		Color colorBeforeMove=game.getBoard().getCircles().get(111).getColor();
		Player bot = new Bot(game,0,2);
		Player bot2 = new Bot(game,1,2);
		Player[] players = new Player[2];
		players[0] = bot;
		players[1] = bot2;
		game.setPlayers(players);
		bot2.sendTurn(1);
		bot2.start();
		TimeUnit.SECONDS.sleep(1);
		Color colorAfterMove= game.getBoard().getCircles().get(111).getColor();
		assertNotEquals(colorBeforeMove,colorAfterMove);
	}

}
