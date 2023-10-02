package Pieces;

import Main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {
//----------------- Fields -----------------
    public int col, row;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;
    public int value;

    Image sprite;
    Board board;

    public boolean isFirstMove = true; //for pawn first move and king and rook castling

    BufferedImage sheet; //resources/pieces.png image
    {
        try{
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("resources/pieces.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    protected int sheetScale = sheet.getWidth() / 6;

    //----------------- End Of Fields -----------------

    public Piece(Board board){
        this.board = board;
    }



    /**
     *This method is being called by Board.isValidMove(), and checks if move.piece (with respect to its type) can move to (col,row)
     * @param col
     * @param row
     * @return true if move.piece can move to (col,row), else false
     */
    public boolean isValidMovement(int col, int row){
        return true;
    }

    /**
     * This method is being called by Board.isValidMove(), and checks if there are any pieces that collides with move.piece valid paths it can move to (with respect to its type)
     * @param col
     * @param row
     * @return
     */
    public boolean moveCollidesWithPiece(int col, int row){
        return false;
    }

    /**
     * Put the piece on the board on (xPos, yPos)
     * @param g2d
     */
    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos, null);
    }


}
