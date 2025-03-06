package jinwoong.comprehensive.domain;

import java.util.Arrays;

public enum Role {
    STUDENT(1, "수강생"),
    TEACHING_ASSISTANT(2, "조교"),
    INSTRUCTOR(3, "강사");

    static final String INVALID_ROLE = "Role does not exist.";

    private final int no;
    private final String description;

    Role(int no, String description) {
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

    public static Role fromInt(int no) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getNo() == no)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ROLE));
    }
}
