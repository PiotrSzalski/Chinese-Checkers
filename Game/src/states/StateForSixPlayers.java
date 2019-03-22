package states;

import java.awt.Color;

import gamepackage.Board;

public class StateForSixPlayers implements State{
	
	public void setAreasForPlayers(Board board) {
		board.createPlayer(player,Color.RED);
		board.createPlayer(player2,Color.GREEN);
		board.createPlayer(player4,Color.BLUE);
		board.createPlayer(player5,Color.CYAN);
		board.createPlayer(player3,Color.PINK);
		board.createPlayer(player1,Color.YELLOW);
	}

	public int[] setDestinationForPlayer(int playerId) {
		if(playerId == 0) {
			return player5;
		} else if(playerId == 1) {
			return player3;
		} else if(playerId == 2) {
			return player1;
		} else if(playerId == 3) {
			return player;
		} else if(playerId == 4) {
			return player2;
		} else {
			return player4;
		}
		
	}
}
