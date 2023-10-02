package Pieces;

import Main.Board;

import java.awt.image.BufferedImage;

public class Queen extends Piece {
    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Queen";
        this.value = 8;

        this.sprite = sheet.getSubimage(1 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return this.col == col || this.row == row || //rook condition
                Math.abs(col - this.col) == Math.abs(row - this.row); // bishop condition
    }

    @Override
    public boolean moveCollidesWithPiece(int col, int row){

        //------------- Rook condition ---------------
        if (this.col == col || this.row == row ){
            //Left
            if (this.col > col){
                for (int c = this.col - 1; c > col; c--){
                    if (board.getPiece(c, this.row) != null){
                        return true;
                    }
                }
            }

            //Right
            if (this.col < col){
                for (int c = this.col + 1; c < col; c++){
                    if (board.getPiece(c, this.row) != null){
                        return true;
                    }
                }
            }

            //Up
            if (this.row > row){
                for (int r = this.row - 1; r > row; r--){
                    if (board.getPiece(this.col, r) != null){
                        return true;
                    }
                }
            }

            //Down
            if (this.row < row){
                for (int r = this.row + 1; r < row; r++){
                    if (board.getPiece(this.col, r) != null){
                        return true;
                    }
                }
            }

        }

        //------------- Bishop condition -------------
        else {
            //Up Left diagonal
            if (this.col > col && this.row > row) {
                for (int i = 1; this.col - i > col; i++) {
                    if (board.getPiece(this.col - i, this.row - i) != null) {
                        return true;
                    }
                }
            }
            //Down Left diagonal
            if (this.col > col && this.row < row) {
                for (int i = 1; this.col - i > col; i++) {
                    if (board.getPiece(this.col - i, this.row + i) != null) {
                        return true;
                    }
                }
            }

            //Up Right diagonal
            if (this.col < col && this.row > row) {
                for (int i = 1; this.col + i < col; i++) {
                    if (board.getPiece(this.col + i, this.row - i) != null) {
                        return true;
                    }
                }
            }
            //Down Right diagonal
            if (this.col < col && this.row < row) {
                for (int i = 1; this.col + i < col; i++) {
                    if (board.getPiece(this.col + i, this.row + i) != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
