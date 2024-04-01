package project01.board.board;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException(String message){
        super(message);
    }
}
