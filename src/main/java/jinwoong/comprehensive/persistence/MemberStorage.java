package jinwoong.comprehensive.persistence;

import jinwoong.comprehensive.domain.Member;

import java.util.List;

public interface MemberStorage {
    void saveMembers(List<Member> members);
    List<Member> loadMembers();
}
