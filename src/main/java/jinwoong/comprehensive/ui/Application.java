package jinwoong.comprehensive.ui;

import jinwoong.comprehensive.persistence.FileMemberStorage;
import jinwoong.comprehensive.persistence.MemberRepository;
import jinwoong.comprehensive.service.MemberService;

public class Application {
    private final MemberService memberService;
    private final InputManager inputManager = new InputManager();
    private final MemberFinder memberFinder;
    private final StatusModifier statusModifier;
    private final MemberInfoModifier memberInfoModifier;
    private final MemberRegister memberRegister;

    public Application() {
        MemberRepository memberRepository = new MemberRepository(new FileMemberStorage());
        this.memberService = new MemberService(memberRepository);
        this.memberFinder = new MemberFinder(memberService);
        this.statusModifier = new StatusModifier(memberService);
        this.memberInfoModifier = new MemberInfoModifier(memberService);
        this.memberRegister = new MemberRegister(memberService);
    }

    public void run() {
        while (true) {
            String message = "%n===== 회원 관리 프로그램 =====%n%s".formatted(inputManager.showElements(Feature.values()));
            String inputMessage = "메뉴 선택 (번호 입력): ";
            int choice = inputManager.getInputByInt(message, inputMessage);

            try {
                switch (Feature.fromInt(choice)) {
                    case SHOW_ALL_MEMBERS -> memberFinder.showAllMembers();
                    case FIND_MEMBER -> memberFinder.findMember();
                    case REGISTER_MEMBER -> memberRegister.registerMember();
                    case MODIFY_MEMBER_INFO -> memberInfoModifier.modifyMemberInfo();
                    case MODIFY_MEMBER_STATUS -> statusModifier.modifyMemberStatus();
                    case EXIT_PROGRAM -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("오류: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
