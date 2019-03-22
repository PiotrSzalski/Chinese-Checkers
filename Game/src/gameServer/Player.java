package gameServer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import gamepackage.Board;
import states.StateController;

public class Player extends Thread
{
	private BufferedReader in;
	protected Socket client;
	protected PrintWriter out;
	protected Game game;
	protected int playerId;
	protected Color playerColor;
	protected int destination[] = new int[10];
	protected boolean hasWon;
	protected boolean myTurn;
	
	public Player(Socket socket, Game game, int playerId, int numberOfPlayers)
	{
		this.game = game;
		if(socket != null) {
		client=socket;
		try {
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			System.out.println("Nie mozna nawiazac komunikacji");
			System.exit(0);
		}
		}
		StateController s = new StateController();
		s.setState(numberOfPlayers);
		destination = s.getState().setDestinationForPlayer(playerId);
		if(playerId == 0) playerColor = Color.RED;
		else if(playerId == 1) playerColor = Color.GREEN;
		else if(playerId == 2) playerColor = Color.BLUE;
		else if(playerId == 3) playerColor = Color.CYAN;
		else if(playerId == 4) playerColor = Color.PINK;
		else if(playerId == 5) playerColor = Color.YELLOW;
		
		this.playerId = playerId;
		playerId++;
	}
	
	public void turn(int player) {
		for(int i=0;i<game.getNumberOfPlayers();i++) {
			game.getPlayers()[i].sendTurn(player);
		}
	}
	public boolean hasWon() {
		return hasWon;
	}
	
	public void run() {
		String line;
		try {
			while(true) {
				
				line=in.readLine();
				if(line.equals("CONNECT")) {
					
					out.println(game.getNumberOfPlayers());
					out.println(Integer.toString(playerColor.getRGB()));
					out.println(Integer.toString(playerId));
					
				} else if  ( line.equals("END TURN") ) {
					game.reset();
					int nextPlayer = (playerId+1)%game.getNumberOfPlayers();
					while(game.getPlayers()[nextPlayer].hasWon) {
						nextPlayer = (nextPlayer+1)%game.getNumberOfPlayers();
						if(nextPlayer == playerId) {
							for(int i=0;i<game.getPlayers().length;i++) {
								nextPlayer = -1;
							}
							break;
						}
					}
					for(int i=0;i<game.getPlayers().length;i++) {
						game.getPlayers()[i].sendTurn(nextPlayer);
						this.myTurn = false;
					}
				}
				
				else {	
					if ( game.checkMoveProperiety(line,playerColor,destination) ) {
						if (!hasWon && game.checkWin(destination,playerColor)) {
							hasWon = true;
						}
						for(int i=0;i<game.getPlayers().length;i++) {
							game.getPlayers()[i].send(line);
							if(hasWon) {
								game.getPlayers()[i].send(Integer.toString(playerId));
							} else {
								game.getPlayers()[i].send("");
							}
						}
							
					}
					else {
							this.out.println("INCORRECT");
						}
				}
			}
		} catch (IOException e) {
			this.hasWon = true;
			for(int i=0;i<Board.numberOfCircles;i++) {
				if(game.getBoard().getCircles().get(i).getColor().equals(this.playerColor)) {
					String l = i+" "+i+" "+"255 255 255";
					game.getBoard().getCircles().get(i).setColor(Color.WHITE);
					try {
						for(int j=0;j<game.getNumberOfPlayers();j++) {
							if(!game.getPlayers()[j].isBot()) {
								game.getPlayers()[j].out.println(l);
								game.getPlayers()[j].out.println("");
							}
						}
					} catch(NullPointerException err) {
						System.exit(0);
					}
					
				}
			}
			if(myTurn)
			{
				int nextPlayer = (playerId+1)%game.getNumberOfPlayers();
				while(game.getPlayers()[nextPlayer].hasWon) {
					nextPlayer = (nextPlayer+1)%game.getNumberOfPlayers();
					if(nextPlayer == playerId) {
						for(int i=0;i<game.getPlayers().length;i++) {
							nextPlayer = -1;
						}
						break;
					}
				}
				for(int i=0;i<game.getPlayers().length;i++) {
					game.getPlayers()[i].sendTurn(nextPlayer);
				}
			}
		}
	}
	public boolean isBot() {
		return false;
	}
	public void sendTurn(int player) {
		out.println("TURN");
		out.println(player);
		if(player == playerId) {
			myTurn = true;
		}
	}
	
	public boolean myTurn() {
		return myTurn;
	}
	
	public void send(String text) {
		out.println(text);
	}
}
