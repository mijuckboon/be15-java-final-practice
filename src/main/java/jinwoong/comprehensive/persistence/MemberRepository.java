package jinwoong.comprehensive.persistence;

import jinwoong.comprehensive.domain.Role;
import jinwoong.comprehensive.domain.Status;
import jinwoong.comprehensive.domain.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private final MemberStorage memberStorage;
    private final List<Member> memberList;

    public MemberRepository(MemberStorage memberStorage) {
        this.memberStorage = memberStorage;
        this.memberList = memberStorage.loadMembers();
    }

    public List<Member> selectAllMembers() {
        return new ArrayList<>(memberList);
    }

    public Member selectMemberByNo(int no) {
        return memberList.stream().filter(member -> member.getNo() == no).findFirst().orElse(null);
    }

    public List<Member> selectMembersByName(String name) {
        return memberList.stream().filter(member -> member.getName().equals(name)).toList();
    }

    public List<Member> selectMembersByRole(Role role) {
        return memberList.stream().filter(member -> member.getRole().equals(role)).toList();
    }

    public List<Member> selectMembersByStatus(Status status) {
        return memberList.stream().filter(member -> member.getStatus().equals(status)).toList();
    }

    public void insertMember(Member member) {
        memberList.add(member);
        memberStorage.saveMembers(memberList);
    }

    public void updateMemberInfo(Member updatedMember) {
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getNo() == updatedMember.getNo()) {
                memberList.set(i, updatedMember);
                memberStorage.saveMembers(memberList);
                break;
            }
        }
    }

    public void updateMemberStatus(int no, Status status) {
        Member member = selectMemberByNo(no);
        updateMemberInfo(member.update(member.getName(), member.getRole(), status));
    }

    public void activateMember(int no) {
        updateMemberStatus(no, Status.IS_ACTIVE);
    }

    public void inactivateMember(int no) {
        updateMemberStatus(no, Status.IS_INACTIVE);
    }

    public void deleteMember(int no) {
        updateMemberStatus(no, Status.IS_DELETED);
    }

}
