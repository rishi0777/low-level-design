package com.example.LLD.Model;

import com.example.LLD.Util.PieceType;

public class Player {
    String name;
    PlayingPiece allottedPiece;

    public Player(String name, PlayingPiece allottedPiece){
        this.name = name;
        this.allottedPiece = allottedPiece;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayingPiece getAllottedPiece() {
        return allottedPiece;
    }

    public void setAllottedPiece(PlayingPiece allottedPiece) {
        this.allottedPiece = allottedPiece;
    }
}
