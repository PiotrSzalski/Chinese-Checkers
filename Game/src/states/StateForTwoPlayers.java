package states;

import gamepackage.Board;

public class StateForTwoPlayers implements State{

	public void setAreasForPlayers(Board board) {
		// Board for two players is created by default
	}

	public int[] setDestinationForPlayer(int playerId) {
		if(playerId == 0) {
			return player5;
		} else {
			return player;
		}
	}
}
