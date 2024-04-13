package project01.board.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import project01.board.Utiliry.GenderType;

@Getter @Setter
public class Member {
    private Long memberId;
    private String name;
    private Integer age;

    private Boolean open; // 정회원여부
    private GenderType genderType; // 성별
}
