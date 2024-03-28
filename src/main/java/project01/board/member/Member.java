package project01.board.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String name;
    private int age;
}
