package jinwoong.comprehensive.ui;

import java.util.Arrays;

public enum Feature {
    SHOW_ALL_MEMBERS(1, "모든 회원 조회"),
    FIND_MEMBER(2, "특정 회원 찾기"),
    REGISTER_MEMBER(3, "회원 등록"),
    MODIFY_MEMBER_INFO(4, "회원 정보 수정"),
    MODIFY_MEMBER_STATUS(5, "회원 상태 수정"),
    EXIT_PROGRAM(9, "프로그램 종료");
    
    private final int no;
    private final String description;
    
    Feature(int no, String description) {
        this.no = no;
        this.description = description;
    }

    public int getNo() {
        return no;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return no + ". " + description;
    }

    public static Feature fromInt(int no) {
        return Arrays.stream(Feature.values())
                .filter(feature -> feature.getNo() == no)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다. 다시 선택해주세요."));
    }
}
