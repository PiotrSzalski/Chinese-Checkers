package gameServer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import gamepackage.Board;
import gamepackage.ColorCircle;


public class Bot extends Player {

	private volatile boolean turn;
	private Random rand;
		
	public Bot(Game game, int playerId, int numberOfPlayers) {	
		super(null,game,playerId,numberOfPlayers);
		turn = false;
		rand = new Random();
	}
	
	public boolean isBot() {
		return true;
	}
		
	public void sendTurn(int id) {
		if (id == playerId) {
			turn = true;			
		}
	}

	public void run() {

		int start,end,aim,reachedNumber;	
		double distance,max,currentDistanceToAim,distanceToAim;
		String line;
		ColorCircle aimCircle;
		Board board = game.getBoard();
		Color aimColor;
		ArrayList<ColorCircle> circles = board.getCircles();
		ColorCircle middle = circles.get(60);
		
		reachedNumber=0;
		aim = 0;
		max = 0;
		line = "";
		int[] destination2 = destination.clone();
		int [] reached = new int[10];
		for (int x : reached) {
			x = 0;
		}
			
		for (int i : destination) {
			distance =((circles.get(i).getCenterX()-middle.getCenterX())*(circles.get(i).getCenterX()-middle.getCenterX())) +
				((circles.get(i).getCenterY()-middle.getCenterY())*(circles.get(i).getCenterY()-middle.getCenterY()));	
			if (distance > max) {
				max = distance;
				aim = i;
				}
			}
			
		while(true) {	
			if (turn) {
					
				circles = board.getCircles();
				aimCircle = board.getCircles().get(aim);
				aimColor = aimCircle.getColor();

				if (aimColor.equals(playerColor) ) {								
					max = 0;
					reached[reachedNumber] = aim; 
					reachedNumber++;
							
					for (int i = 0; i < destination2.length; i++)
						if( destination2[i] == aim)
							destination2[i] = -1;
							
					for (int i = 0; i < destination2.length; i++) {
						if(destination2[i] == -1) continue;
						distance = ((circles.get(i).getCenterX()-middle.getCenterX())*(circles.get(i).getCenterX()-middle.getCenterX())) +
										((circles.get(i).getCenterY()-middle.getCenterY())*(circles.get(i).getCenterY()-middle.getCenterY()));	
						if(distance > max) {
							max = distance;
							aim = destination2[i];
							}
						}
					}
						
				move:
				for (ColorCircle c:circles) {
					if(c.getColor().equals(playerColor)) {
						if(inArray(reached,circles.indexOf(c)))
							continue;
						for(ColorCircle c2:circles) {
							if(c2.getColor().equals(Color.WHITE)) {
								distance = Math.sqrt(((c2.getCenterX()-c.getCenterX())*(c2.getCenterX()-c.getCenterX())) +
										((c2.getCenterY()-c.getCenterY())*(c2.getCenterY()-c.getCenterY())));
								if(distance < 1.5*ColorCircle.height) {		
									currentDistanceToAim = Math.sqrt(((c.getCenterX()-aimCircle.getCenterX())*(c.getCenterX()-aimCircle.getCenterX())) +
												((c.getCenterY()-aimCircle.getCenterY())*(c.getCenterY()-aimCircle.getCenterY())));
									distanceToAim = Math.sqrt(((c2.getCenterX()-aimCircle.getCenterX())*(c2.getCenterX()-aimCircle.getCenterX())) +
												((c2.getCenterY()-aimCircle.getCenterY())*(c2.getCenterY()-aimCircle.getCenterY())));

									if(distance < 1.5*ColorCircle.height && distanceToAim < currentDistanceToAim ) {
										start = board.getCircles().indexOf(c);
										end = board.getCircles().indexOf(c2);
										line = start+" "+end+" "+playerColor.getRed()+" "+playerColor.getGreen()+" "+playerColor.getBlue();

										if(game.checkMoveProperiety(line,playerColor,destination)) {
											break move;
										}
									}
								}
							}
						}		
					}
				}			 			
			if (!hasWon && game.checkWin(destination,playerColor)) {
				hasWon = true;
				}
			for (int i = 0; i < game.getPlayers().length;i++) {
					game.getPlayers()[i].send(line);
					if(hasWon) {
						game.getPlayers()[i].send(Integer.toString(playerId));
					} else {
						game.getPlayers()[i].send("");
					}
						
				}		
			game.reset();
			int nextPlayer = (playerId+1)%game.getNumberOfPlayers();
			while (game.getPlayers()[nextPlayer].hasWon) {
				nextPlayer = (nextPlayer+1)%game.getNumberOfPlayers();
				if (nextPlayer == playerId) {
					for (int i = 0;i < game.getPlayers().length;i++) {
						nextPlayer = -1;
					}
					break;
				}
			}
			
			for (int i = 0; i < game.getPlayers().length; i++) {
				game.getPlayers()[i].sendTurn(nextPlayer);
			}

			turn = false;	
			}
		}	
	}
		
	public void send(String text) {
		//has to override
	}
		
	private Boolean inArray(int[] array, int x) {
		for (int i : array)
			if(x == i)
				return true;
		return false;
	}
}	

