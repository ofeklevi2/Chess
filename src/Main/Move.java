package Main;

import Pieces.Piece;

public class Move {
    int olcCol;
    int olcRow;
    int newCol;
    int newRow;

    Piece piece;
    Piece capture;

    /**
     * Move class constructor
     * @param board the chess board
     * @param piece the piece to move
     * @param newCol piece's col after it moved
     * @param newRow piece's row after it moved
     */
    public Move(Board board, Piece piece, int newCol, int newRow){
        this.olcCol = piece.col;
        this.olcRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;

        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);
    }
}

