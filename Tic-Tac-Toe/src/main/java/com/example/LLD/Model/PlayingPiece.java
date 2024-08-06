package com.example.LLD.Model;

import com.example.LLD.Util.PieceType;

public class PlayingPiece {
    private PieceType pieceType;

    public PlayingPiece(PieceType pieceType){
        this.pieceType=pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }
}
