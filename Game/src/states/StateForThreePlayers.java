package states;

import java.awt.Color;

import gamepackage.Board;

public class StateForThreePlayers implements State{

	public void setAreasForPlayers(Board board) {
		board.createPlayer(player3,Color.BLUE);
		board.createPlayer(player4,Color.GREEN);
		board.createPlayer(player5,Color.WHITE);
	}

	public int[] setDestinationForPlayer(int playerId) {
		if(playerId == 0) {
			return player5;
		} else if(playerId == 1) {
			return player1;
		} else {
			return player2;
		}
	}
}
