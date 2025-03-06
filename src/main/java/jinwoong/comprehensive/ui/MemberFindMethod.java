package jinwoong.comprehensive.ui;

import java.util.Arrays;

public enum MemberFindMethod {
    BY_NUMBER(1, "번호"),
    BY_NAME(2, "이름"),
    BY_ROLE(3, "역할"),
    BY_STATUS(4, "상태");

    private final int no;
    private final String description;

    MemberFindMethod(int no, String description) {
        this.no = no;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getNo() {
        return no;
    }

    @Override
    public String toString() {
        return no + ". " + description;
    }

    public static MemberFindMethod fromInt(int no) {
        return Arrays.stream(MemberFindMethod.values())
                .filter(method -> method.getNo() == no)
                .findFirst()
                .orElse(null);
    }
}
