package gamepackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import gameServer.Connector;


public class Client {
	
	private JFrame clientWindow;
	private BackgroundPanel backgroundPanel;
	private JTextArea movesInfo;
	private JTextArea currentPlayer;
	private JTextArea places;
	private BoardImage boardImage;
	private JButton endTurn;
	private Board board;
	private JProgressBar progressBar;
	
	public Client() {
		
		clientWindow = new JFrame("Chinese checkers");
		backgroundPanel = new BackgroundPanel();
		movesInfo = new JTextArea();
		currentPlayer = new JTextArea();
		places = new JTextArea();
		boardImage  = new BoardImage();	
		
		endTurn = new JButton("END YOUR TURN");
		endTurn.addActionListener(e -> {
			board.endTurn(movesInfo);
		});
		movesInfo.setFont(new Font("Segoe Script",Font.BOLD,15));
		movesInfo.setText("WELCOME!");
		movesInfo.setEditable(false);
		movesInfo.setPreferredSize(new Dimension(140,40));
		movesInfo.setOpaque(false); 
		
		currentPlayer.setFont(new Font("Segoe Script",Font.BOLD,15));
		currentPlayer.setEditable(false);
		currentPlayer.setPreferredSize(new Dimension(140,60));
		currentPlayer.setOpaque(false);
		
		places.setFont(new Font("Segoe Script",Font.BOLD,15));
		places.setEditable(false);
		places.setPreferredSize(new Dimension(140,300));
		places.setText("Places:");
		places.setOpaque(false);
		
		clientWindow.setLayout(new BorderLayout());
		clientWindow.setSize(800, 600);
		clientWindow.getContentPane().setBackground(new Color(255,153,51));		
		clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		progressBar=new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setPreferredSize(new Dimension(150,30));
		progressBar.setForeground(Color.BLACK);
		progressBar.setString("Waiting for all players");
		
		backgroundPanel.setPreferredSize(new Dimension(150,500));
		backgroundPanel.add(endTurn);	
		backgroundPanel.add(movesInfo);
		backgroundPanel.add(currentPlayer);
		backgroundPanel.add(progressBar);
		backgroundPanel.add(places);
		
		clientWindow.add(backgroundPanel,BorderLayout.EAST);
	    clientWindow.add(boardImage,BorderLayout.CENTER);
	    clientWindow.setVisible(true);
	}
	
	public class BackgroundPanel extends JPanel
	{
		private Color color = Color.WHITE;
		protected void paintComponent(Graphics g) 
		{
			  Graphics2D g2d = (Graphics2D) g;
			  g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			  int w = getWidth();
			  int h = getHeight();
			  Color color2 = Color.WHITE;
			  GradientPaint gp = new GradientPaint(0, 0, color, 0, h, color2);
			  g2d.setPaint(gp);
			  g2d.fillRect(0, 0, w, h);
		}

		public void setColor(Color color) {
			this.color = color;
		}
	}
	
	private void setMyColor(Color color) {
		backgroundPanel.setColor(color);
		backgroundPanel.repaint();
		
	}
	
	public static void main(String[] args)  {
				
				int id = 0;
				int numberOfPlayers = 0;
			    Color color = Color.WHITE;
				Connector connector = new Connector();
				
				connector.out.println("CONNECT");
				
				try {
					numberOfPlayers = Integer.parseInt(connector.in.readLine());
					color = new Color(Integer.parseInt(connector.in.readLine()));
					id = Integer.parseInt(connector.in.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Client client = new Client(); 
				client.board = new Board(numberOfPlayers,client.boardImage);		
				client.board.setConnector(connector);
				client.board.setPlayerId(id);
				
				connector.setBoardImage(client.board,client.movesInfo,client.currentPlayer, client.places, client.progressBar);
				client.boardImage.setBoard(client.board);
				client.setMyColor(color);			
				
				connector.play();

		}
		
	}
