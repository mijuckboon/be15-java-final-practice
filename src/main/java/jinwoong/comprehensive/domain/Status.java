package jinwoong.comprehensive.domain;

import java.util.Arrays;

public enum Status {
    IS_ACTIVE(1, "활성화"),
    IS_INACTIVE(2,"비활성화"),
    IS_DELETED(3, "탈퇴");

    static final String INVALID_STATUS = "Status does not exist.";

    private final int no;
    private final String description;

    Status(int no, String description) {
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

    public static Status fromInt(int no) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getNo() == no)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_STATUS));
    }
}


