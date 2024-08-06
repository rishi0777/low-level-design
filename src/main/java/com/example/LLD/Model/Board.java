package com.example.LLD.Model;

import com.example.LLD.Util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private PlayingPiece[][] board;

    public Board(int size){
        this.size = size;
        board = new PlayingPiece[size][size];
    }

    public int getSize() {
        return size;
    }

    public PlayingPiece[][] getBoard() {
        return board;
    }

    public List<Pair<Integer,Integer>> getFreeCells(){
        List<Pair<Integer,Integer>> freeCells = new ArrayList<Pair<Integer,Integer>>();
        for(int i = 0; i < size; i++){
            for(int j = 0;j < size; j++) {
                if(board[i][j] == null)
                    freeCells.add(new Pair<>(i,j));
            }
        }

        return freeCells;
    }

    public boolean addCell(int row, int col, PlayingPiece playingPiece){
        if(row < 0 || col < 0 || col >= size || row >= size || board[row][col] != null) {
            return false;
        }
        board[row][col] = playingPiece;
        return true;
    }

   public void printBoard(){
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j] != null)
                    System.out.print(board[i][j].getPieceType());
                else
                    System.out.print(" ");
                System.out.print("\t|");
            }
            System.out.println();
        }
    }
}
