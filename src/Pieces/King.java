package Pieces;

import Main.Board;
import Main.Move;

import java.awt.image.BufferedImage;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "King";
        this.value = 0;

        this.sprite = sheet.getSubimage(0 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean isValidMovement(int col, int row){
        return  Math.abs((this.col - col) * (this.row - row)) == 1 || //diagonal move
                Math.abs(this.col - col) +  Math.abs(this.row - row) == 1 || //vertical / horizontal move
                canCastle(col, row);
    }

    //implementing moveCollidesWithPiece(int col, int row) for king is redundant

    private boolean canCastle(int col, int row){
        if (this.row == row){
            if (col == 6){
                Piece rook = board.getPiece(7, row);
                if (rook != null && rook.isFirstMove && this.isFirstMove){
                    return  board.getPiece(5, row) == null &&
                            board.getPiece(6, row) == null &&
                            !board.checkScanner.isKingChecked(new Move(board, this, 5, row));
                }
            }
            else if (col == 2){
                Piece rook = board.getPiece(0, row);
                if (rook != null && rook.isFirstMove && this.isFirstMove){
                    return  board.getPiece(3, row) == null &&
                            board.getPiece(2, row) == null &&
                            board.getPiece(1, row) == null &&
                            !board.checkScanner.isKingChecked(new Move(board, this, 3, row));
                }
            }
        }

        return false;
    }

}

