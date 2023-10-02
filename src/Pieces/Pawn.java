package Pieces;

import Main.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Pawn";
        this.value = 1;

        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize,board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean isValidMovement(int col, int row){
        int colorIndex = this.isWhite ? 1 : -1; //white == 1, black == -1

        //push pawn 1
        if (this.col == col && this.row - colorIndex == row && board.getPiece(col, row) == null){
            return true;
        }

        //push pawn 2
        if (isFirstMove && this.col == col && this.row - 2 * colorIndex == row && board.getPiece(col, row) == null && board.getPiece(col, row + colorIndex) == null){
            return true;
        }

        //because in Board.isValidMove(Move move) we first check if ((sameTeam(move.piece, move.capture) == false), we cant capture our own piece anyway, so we don't need to check it here

        //capture left
        if (col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row) != null){
            return true;
        }

        //capture right
        if (col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row) != null){
            return true;
        }

        //en passant left
        if (board.getTileNum(col, row) == board.enPassantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null){
            return true;
        }

        //en passant right
        if (board.getTileNum(col, row) == board.enPassantTile && col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null){
            return true;
        }

        return false;
    }
}

