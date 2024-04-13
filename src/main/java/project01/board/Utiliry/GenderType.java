package project01.board.Utiliry;

public enum GenderType {

    MALE("남성"), FEMALE("여성");

    private final String description;

    GenderType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
