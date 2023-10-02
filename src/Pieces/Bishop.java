package Pieces;

import Main.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece {
    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Bishop";
        this.value = 3;

        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize,board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return Math.abs(col - this.col) == Math.abs(row - this.row);
    }

    @Override
    public boolean moveCollidesWithPiece(int col, int row){

        //Up Left diagonal
        if (this.col > col && this.row > row){
            for (int i = 1; this.col - i > col; i++){
                if (board.getPiece(this.col - i, this.row - i) != null){
                    return true;
                }
            }
        }
        //Down Left diagonal
        if (this.col > col && this.row < row){
           for (int i = 1; this.col - i > col; i++){
               if (board.getPiece(this.col - i, this.row + i) != null){
                   return true;
               }
           }
        }

        //Up Right diagonal
        if (this.col < col && this.row > row){
            for (int i = 1; this.col + i < col; i++){
                if (board.getPiece(this.col + i, this.row - i) != null){
                    return true;
                }
            }
        }
        //Down Right diagonal
        if (this.col < col && this.row < row){
            for (int i = 1; this.col + i < col; i++){
                if (board.getPiece(this.col + i, this.row + i) != null){
                    return true;
                }
            }
        }

        return false;
    }
}
