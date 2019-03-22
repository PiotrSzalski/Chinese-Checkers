package gameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import gamepackage.Board;

public class Connector 
{
	private JTextArea movesInfo;
	private JTextArea currentPlayer;
	private JTextArea places;
	private JProgressBar progressBar;
	public BufferedReader in;
	public PrintWriter out;
	private Socket s;
	private Board board;
	
	public Connector() {
		try {
			s=new Socket("192.168.1.180",6666);
			out=new PrintWriter(s.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBoardImage(Board board, JTextArea movesInfo, JTextArea currentPlayer, JTextArea places, JProgressBar progressbar) {
		this.places = places;
		this.board = board;
		this.movesInfo = movesInfo;
		this.currentPlayer = currentPlayer;
		this.progressBar = progressbar;
	}
	public void play() {
		String line;
		try {
			while(true) {
				line=in.readLine();
				
				if(line.equals("INCORRECT")) {
					movesInfo.setText("Wrong move.");
				
				}
				else if(line.equals("CONNECTED")) {
					this.progressBar.setVisible(false);
				}
				else if(line.equals("TURN") ) {
						int id = Integer.parseInt(in.readLine());
						if(id == board.myId()) {
							currentPlayer.setText("It's your turn");
							board.myTurn();
						} else {
							if(id == 0) {
								currentPlayer.setText(("Now is moving:\nRED"));
							} else if (id == 1) {
								currentPlayer.setText(("Now is moving:\nGREEN"));
							} else if (id == 2) {
								currentPlayer.setText(("Now is moving:\nBLUE"));
							} else if (id == 3) {
								currentPlayer.setText(("Now is moving:\nCYAN"));
							} else if (id == 4) {
								currentPlayer.setText(("Now is moving:\nPINK"));
							} else if (id == 5) {
								currentPlayer.setText(("Now is moving:\nYELLOW"));
							} else {
								currentPlayer.setText("End Of Game");
							}
						}
						
				} else {
					board.opponentMove(line);
					String id = in.readLine();
					if (!id.equals("")) {
						if(id.equals("0")) {
							places.append(("\nRED"));
						} else if (id.equals("1")) {
							places.append(("\nGREEN"));
						} else if (id.equals("2")) {
							places.append(("\nBLUE"));
						} else if (id.equals("3")) {
							places.append(("\nCYAN"));
						} else if (id.equals("4")) {
							places.append(("\nPINK"));
						} else {
							places.append(("\nYELLOW"));
						}
					}
					movesInfo.setText("");
				}
			}
			
		} catch (IOException e) {
			System.out.println("Nie mozna przeczytac lini");
			System.exit(0);
		}
	}
	
	public void sendMessageToServer(String text) {
		out.println(text);
	}
	
}
