package db.jdbcspring.repository;

import db.jdbcspring.domain.Member;
import java.sql.SQLException;

public interface MemberRepositoryEX {
    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}
