package states;

public class StateController {

	private State currentState;
	
	public void setState(int numberOfPlayers)
	{
		if(numberOfPlayers == 2) {
			currentState = new StateForTwoPlayers();
		} else if (numberOfPlayers == 3) {
			currentState = new StateForThreePlayers();
		} else if (numberOfPlayers == 4) {
			currentState = new StateForFourPlayers();
		} else {
			currentState = new StateForSixPlayers();
		} 
	}
	
	public State getState() {
		return currentState;
	}
}
