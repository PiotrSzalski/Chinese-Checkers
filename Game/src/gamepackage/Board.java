package gamepackage;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextArea;

import gameServer.Connector;
import states.StateController;

public class Board {
	
	public static int numberOfCircles = 121;
	private ArrayList<ColorCircle> circles;
	private Connector connector;
	private BoardImage boardImage;
	private boolean myTurn; 
	private int playerId;
	
	public Board( int numberOfPlayers, BoardImage boardImage ) {
		this.circles = new ArrayList<ColorCircle> (numberOfCircles);
		this.boardImage = boardImage;
		myTurn = false;
		
		createBoard();
		if(numberOfPlayers != 2) {
			StateController stateControler = new StateController();
			stateControler.setState(numberOfPlayers);
			stateControler.getState().setAreasForPlayers(this);
		}
	}
	
	/*
	 * create by default Board for 2 players
	 */
	public void createBoard() { 
		
		float start = ColorCircle.width*10;
		for( int i = 0; i < 4; i++) {
			createRaw(start-(ColorCircle.width/2)*i,i,i+1,Color.RED);
		}
		
		start = start - ColorCircle.width*8;
		for( int i = 4; i < 9 ; i++) {
			createRaw(start+(ColorCircle.width/2)*i,i,17-i,Color.WHITE);
		}
		
		start = (float) (start + 3.5*ColorCircle.width);
		for( int i = 9; i < 13 ; i++) {
			createRaw(start-((ColorCircle.width/2)*(i-9)),i,i+1,Color.WHITE);
		}
		
		start = (float) (start + 3*ColorCircle.width);
		for( int i = 13; i < 17 ; i++) {
			createRaw(start+((ColorCircle.width/2)*(i-13)),i,17-i,Color.GREEN);
		}

	}
	
	/*
	 * create 1 raw of circles
	 */
	public void createRaw(float startX,float row,int circlesNumber,Color color) {
		
		for( int i = 0; i < circlesNumber; i++ ) {
			circles.add(new ColorCircle(startX+ColorCircle.width*i,row*ColorCircle.height,color));
		}
	}
	
	public void createPlayer(int players[],Color color) {
		for( int i=0; i < players.length; i++ ) {
			circles.get(players[i]).setColor(color);
		}
			
	}
	
	public void move(int c1, int c2, Color color) {
		if(myTurn) {
		connector.out.println(c1+" "+c2+" "+color.getRed()+" "+color.getGreen()+" "+color.getBlue());
		}
	}
	
	public void opponentMove(String move) {
		String[] words = move.split(" ");	
		Color color = new Color(Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]));
		getCircles().get(Integer.parseInt(words[0])).setColor(Color.WHITE);
		getCircles().get(Integer.parseInt(words[1])).setColor(color);
		boardImage.repaint();		
	}
	
	public void setConnector( Connector c ) {
		this.connector = c;
	}
	public void setPlayerId(int id) {
		this.playerId = id;
	}
	
	public void endTurn(JTextArea communicator) {
		if(myTurn) {
			communicator.setText("");
			this.myTurn = false;
			connector.sendMessageToServer("END TURN");
		}
	}
	
	public void myTurn() {
		this.myTurn = true;
	}
	
	public ArrayList<ColorCircle> getCircles() {
		return circles;
	}

	public int myId() {
		return playerId;
	}
}
