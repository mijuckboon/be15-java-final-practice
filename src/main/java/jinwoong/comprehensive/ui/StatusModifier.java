package jinwoong.comprehensive.ui;

import jinwoong.comprehensive.domain.Status;
import jinwoong.comprehensive.service.MemberService;

public class StatusModifier {
    private final InputManager inputManager = new InputManager();
    private final MemberService memberService;

    public StatusModifier(MemberService memberService) {
        this.memberService = memberService;
    }

    void modifyMemberStatus() {
        String message = "===== %s =====%n%s ".formatted(
                Feature.MODIFY_MEMBER_STATUS.toString()
                , inputManager.showElements(Status.values())
        );
        String inputMessage = "메뉴 선택 (번호 입력): ";
        int choice = inputManager.getInputByInt(message, inputMessage);
        Status status = Status.fromInt(choice);

        switch (status) {
            case IS_ACTIVE -> activateMember();
            case IS_INACTIVE -> inactivateMember();
            case IS_DELETED -> removeMember();
            default -> System.out.println("회원 상태 수정에 실패했습니다. (해당 번호의 상태 없음)");
        }
    }

    void modifyStatusAs(Status status) {
        String description = status.getDescription();
        try {
            String inputMessage = "%s할 회원 번호 입력: ".formatted(description);
            int no = inputManager.getInputByInt(inputMessage);

            switch (status) { // NullPointerException으로 인해 Enum 정보 노출되는 부분 수정 필요
                case IS_ACTIVE -> memberService.activateMember(no);
                case IS_INACTIVE -> memberService.inactivateMember(no);
                case IS_DELETED -> memberService.removeMember(no);
            }
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            System.out.println(message);
        }
    }

    void activateMember() {
        modifyStatusAs(Status.IS_ACTIVE);
    }

    void inactivateMember() {
        modifyStatusAs(Status.IS_INACTIVE);
    }

    void removeMember() {
        modifyStatusAs(Status.IS_DELETED);
    }

}
