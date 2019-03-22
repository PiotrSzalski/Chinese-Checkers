package states;

import java.awt.Color;

import gamepackage.Board;

public class StateForFourPlayers implements State{


	public void setAreasForPlayers(Board board) {
		board.createPlayer(player,Color.WHITE);
		board.createPlayer(player1,Color.RED);
		board.createPlayer(player2,Color.GREEN);
		board.createPlayer(player4,Color.BLUE);
		board.createPlayer(player3,Color.CYAN);
		board.createPlayer(player5,Color.WHITE);
	}

	public int[] setDestinationForPlayer(int playerId) {
		if(playerId == 0) {
			return player4;
		} else if (playerId == 1) {
			return player3;
		} else if (playerId == 2) {
			return player1;
		} else {
			return player2;
		}
	}
}
