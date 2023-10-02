package Main;

import Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {

    Board board;

    public Input(Board board){
        this.board = board;
    }

    /**
     * Gets the piece that you clicked on, and make it the new board.selectedPiece
     * @param e a mouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e){
        //Gets the col and row of the clicked tile
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null){
            board.selectedPiece = pieceXY;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if (board.selectedPiece != null){
            board.selectedPiece.xPos = e.getX() - board.tileSize / 2; // The 'board.tileSize / 2' is to center the piece
            board.selectedPiece.yPos = e.getY() - board.tileSize / 2; // The 'board.tileSize / 2' is to center the piece

            board.repaint();
        }
    }

    /**
     * Defines the action to perform when the mouse is being released (depends whether or not the move is valid)
     * @param e MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e){
        //Gets the col and row of the released tile
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        if (board.selectedPiece != null){
            Move move = new Move(board, board.selectedPiece, col , row);

            if (board.isValidMove(move)){
                board.makeMove(move);
            }

            //if move is not valid but u still dragged the piece, it will return it to its last position
            else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }
        }

        board.selectedPiece = null;
        board.repaint();
    }
}
