package gameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Server 
{
	public static void main(String[] args) throws InterruptedException
	{
		int numberOfPlayers = 0;
		int numberOfBots = 0;
		int sum = 0;
		ServerSocket server;
		Scanner in = new Scanner(System.in);
		System.out.println("Podaj liczbe graczy:");
		while(true)
		{
			numberOfPlayers = in.nextInt();
			if(numberOfPlayers > 0 && numberOfPlayers <= 6) {
				break;
			}
			else {
				System.out.println("Bledny wybor");
			}	
		}	
		if(numberOfPlayers != 6) {
			System.out.println("Podaj liczbe botow:");
			while(true)
			{
				numberOfBots = in.nextInt();
				if(numberOfBots < 0 || numberOfBots > 6) {
					System.out.println("Bledny wybor");
				}
				else {
					break;
				}
			}
			sum = numberOfPlayers+numberOfBots;
			if(!(sum==2 ||sum==3 ||sum==4 ||sum==6)) {
				System.out.println("Bledny wybor3");
				in.close();
				return;
			}
				
		}
		sum = numberOfPlayers+numberOfBots;
		Game game = new Game(sum);
		Player[] players = new Player[sum];
		try {
			
			server=new ServerSocket(6666);
			System.out.println("Serwer utworzony");
			for(int i=0;i<numberOfPlayers;i++) { 
				players[i] = new Player(server.accept(),game,i,sum);
				players[i].start();
			}	
			for(int i=numberOfPlayers;i<sum;i++) { 
				players[i] = new Bot(game,i,sum);
				players[i].start();
			}
		} catch (IOException e) {
			System.out.println("Nie mozna utworzyc serwera");
			System.exit(0);
		}
		game.setPlayers(players);
		TimeUnit.SECONDS.sleep(1);
		game.whoBeginsGame();
		in.close();
	}
}