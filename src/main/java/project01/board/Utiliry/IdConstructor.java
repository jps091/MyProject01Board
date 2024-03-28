package project01.board.Utiliry;

public class IdConstructor {

    private static final IdConstructor idConstructor = new IdConstructor();
    private Long memberId = 0L;
    private Long boardId = 0L;

    private IdConstructor(){};

    public IdConstructor getIdConstructor(){
        return idConstructor;
    }

    public Long getMemberId(){
        return memberId++;
    }

    public Long getBoardId(){
        return boardId++;
    }
}
