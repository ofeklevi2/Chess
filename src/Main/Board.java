package Main;

import Pieces.*;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Board extends JPanel {

    public final int tileSize = 85;
    final int rows = 8;
    final int cols = 8;

    ArrayList<Piece> pieceList = new ArrayList<Piece>();

    public Piece selectedPiece; // The piece that you are moving

    Input input = new Input(this);

    public int enPassantTile = -1;

    public CheckScanner checkScanner = new CheckScanner(this);

    public Board(){
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPieces();
    }

    public Piece getPiece(int col, int row){
        for (Piece piece : pieceList){
            if (piece.col == col && piece.row == row){
                return piece;
            }
        }
        return null;
    }

    /**
     * The makeMove(move) is being called by Input.mouseReleased(), after we check if move is valid
     * Moves the piece stored in move.piece field to the position represented by move
     * @param move the move to perform
     */
    public void makeMove(Move move){
        if (move.piece.name.equals("Pawn")){
            movePawn(move);
        }
        else if (move.piece.name.equals("King")){
            moveKing(move);
        }

            move.piece.col = move.newCol;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newCol * tileSize;
            move.piece.yPos = move.newRow * tileSize;

            move.piece.isFirstMove = false;

            capture(move.capture);
    }

    private void moveKing(Move move){

        // if you are trying to castle
        if (Math.abs(move.piece.col - move.newCol) == 2){
            Piece rook;
            //if you are trying to castle to the king side
            if (move.piece.col < move.newCol){
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            }
            //else you are trying to castle to the opposite king side
            else{
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPos = rook.col * tileSize;
        }
    }

    private void movePawn(Move move) {
        //en passant
        int colorIndex = move.piece.isWhite ? 1 : -1;

        //if we want to perform en passant, capture the corresponding pawn
        if (getTileNum(move.newCol, move.newRow) == enPassantTile){
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }

        //if we pushed a pawn by 2, there is a potential that it will be captured by en passant,
        // so the enPassantTile is the tile behind the pawn
        if (Math.abs(move.newRow - move.piece.row) == 2){
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        }

        else{
            enPassantTile = -1;
        }

        //promotion
        colorIndex = move.piece.isWhite ? 0 : 7;
        if (move.newRow == colorIndex){
            promotePawn(move);
        }

    }

    private void promotePawn(Move move) {
        String[] options = {"Queen", "Rook", "Bishop", "knight"};
        int choice = JOptionPane.showOptionDialog(
                null,
               " Choose to which piece to promote",
                "promote the pawn",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                0
        );

        switch (choice){
            case 0 : pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
                break;
            case 1 : pieceList.add(new Rook(this, move.newCol, move.newRow, move.piece.isWhite));
                break;
            case 2 : pieceList.add(new Bishop(this, move.newCol, move.newRow, move.piece.isWhite));
                break;
            case 3 : pieceList.add(new Knight(this, move.newCol, move.newRow, move.piece.isWhite));
                break;
        }
        capture(move.piece);
    }

    /**
     * capture the piece stored in move.capture field
     * @param piece
     */
    public void capture(Piece piece){
        pieceList.remove(piece);
    }

    /**
     * checks whether move is valid
     * @param move - the move (which is being created under Input class when releasing the mouse) we like to make
     * @return true if move is valid, else false
     */
    public boolean isValidMove(Move move){
        if (sameTeam(move.piece, move.capture)){
            return false;
        }
        if (!move.piece.isValidMovement(move.newCol, move.newRow)){
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)){
            return false;
        }
        if (checkScanner.isKingChecked(move)){
            return false;
        }

        return true;
    }

    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    /**
     * Return the tile number of (col,row) , starting to count from left to right;
     * @param col
     * @param row
     * @return
     */
    public int getTileNum(int col, int row){
        return row * cols + col;
    }

    /**
     * This method is being called by CheckScanner.isKingChecked()
     * @param isWhite
     * @return
     */
    Piece findKing(boolean isWhite){
        for (Piece piece : pieceList){
            if (piece.isWhite == isWhite && piece.name.equals("King")){
                return piece;
            }
        }
        return null;
    }

    /**
     * adds all pieces to pieceList
     */
    public void addPieces(){
        // ------------- Black pieces -----------------
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));

        pieceList.add(new Pawn(this, 0, 1, false));
        pieceList.add(new Pawn(this, 1, 1, false));
        pieceList.add(new Pawn(this, 2, 1, false));
        pieceList.add(new Pawn(this, 3, 1, false));
        pieceList.add(new Pawn(this, 4, 1, false));
        pieceList.add(new Pawn(this, 5, 1, false));
        pieceList.add(new Pawn(this, 6, 1, false));
        pieceList.add(new Pawn(this, 7, 1, false));

        // ------------- White pieces -----------------
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));

        pieceList.add(new Pawn(this, 0, 6, true));
        pieceList.add(new Pawn(this, 1, 6, true));
        pieceList.add(new Pawn(this, 2, 6, true));
        pieceList.add(new Pawn(this, 3, 6, true));
        pieceList.add(new Pawn(this, 4, 6, true));
        pieceList.add(new Pawn(this, 5, 6, true));
        pieceList.add(new Pawn(this, 6, 6, true));
        pieceList.add(new Pawn(this, 7, 6, true));
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //paints the board
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                g2d.setColor((c + r) % 2 == 0 ? new Color(225, 196, 157) : new Color(189, 133, 42));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

        //highlights all valid moves for selectedPiece
        if (this.selectedPiece != null) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (isValidMove(new Move(this, this.selectedPiece, c, r))) {
                        g2d.setColor(new Color(19, 155, 6, 171));
                        g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                    }
                }
            }
        }

        //put each piece at the right place
        for (Piece piece : pieceList){
            piece.paint(g2d);
        }
    }

}
