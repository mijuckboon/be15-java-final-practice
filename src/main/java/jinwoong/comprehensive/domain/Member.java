package jinwoong.comprehensive.domain;

import java.io.Serializable;

public class Member implements Serializable {
    private final int no; // 일련번호 (PK)
    private String name; // 회원 이름
    private Role role; // 역할
    private Status status; // 상태 (활성화, 비활성화, 탈퇴 등)

    public Member(int no, String name, Role role, Status status) {
        this.no = no;
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User(no: %d, name: %s, role: %s, status: %s)".formatted(no, name, role.getDescription(), status.getDescription());
    }

    public Member update(String name, Role role, Status status) {
        return new Member(this.no, name, role, status);
    }

}
