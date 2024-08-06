package com.example.LLD;

import com.example.LLD.Model.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class TicTacToeApplication {
	Deque<Player> players;
	Board gamesBoard;

	TicTacToeApplication(){
		initializeGame();
	}

	public void initializeGame(){
		gamesBoard = new Board(3);
		players = new LinkedList<>();

		PlayingPieceX crossPiece = new PlayingPieceX();
		Player player1 = new Player("Ram", crossPiece);

		PlayingPieceO naughtPiece = new PlayingPieceO();
		Player player2 = new Player("Shyam", naughtPiece);
		players.add(player1);
		players.add(player2);
	}

	public String runGame(){
		boolean winner = false;
		String result = "";
		while(!winner) {
			if(gamesBoard.getFreeCells().isEmpty()){
				winner = true;
				result = "Draw: No Player Won";
				continue;
			}

			gamesBoard.printBoard();
			Player activePlayer = players.removeFirst();

			System.out.println(activePlayer.getName() + "'s turn: Enter cell(row,col) ");
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			String[] inputValues = input.split(",");
			int row = Integer.parseInt(inputValues[0]);
			int col = Integer.parseInt(inputValues[1]);

			boolean cellAdded = gamesBoard.addCell(row,col,activePlayer.getAllottedPiece());
			if(!cellAdded){
				System.out.println("Error: Input not valid, please give other input");
				players.addFirst(activePlayer);
				continue;
			}

			players.addLast(activePlayer);
			if(checkForWinner(row, col, activePlayer)){
				result = activePlayer.getName() + " won";
				winner = true;
				gamesBoard.printBoard();
			}
		}
		return result;
	}

	boolean checkForWinner(int row, int col, Player activePlayer){
		int size = gamesBoard.getSize();
		PlayingPiece[][] board = gamesBoard.getBoard();

		boolean colMatch = true;
		boolean rowMatch = true;
		boolean diagonalMatch = true;
		boolean antiDiagMatch = true;

		for(int i = 0; i < size; i++){
			if(board[i][col] == null || board[i][col] != activePlayer.getAllottedPiece())
				colMatch = false;
		}

		for(int j = 0; j < size; j++){
			if(board[row][j] == null || board[row][j] != activePlayer.getAllottedPiece())
				rowMatch = false;
		}

		for(int i = 0, j = 0; i < size; i++, j++){
			if(board[i][j] == null || board[i][j] != activePlayer.getAllottedPiece())
				diagonalMatch = false;
		}

		for(int i = 0, j = size - 1; i < size; i++, j--){
			if(board[i][j] == null || board[i][j] != activePlayer.getAllottedPiece())
				antiDiagMatch = false;
		}

		return colMatch || rowMatch || diagonalMatch || antiDiagMatch;
	}
}
