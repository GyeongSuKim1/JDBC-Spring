package db.jdbcspring.repository;

import com.zaxxer.hikari.HikariDataSource;
import db.jdbcspring.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static db.jdbcspring.ConnectionConst.ConnectionConst.*;

@Slf4j
public class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() throws Exception {
        /**
         * 기본 DriverManager - 항상 새로운 커넥션 획득
         * DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
         */

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException, InterruptedException {

        log.info("start");

        // save
        Member member = new Member("memberV3", 1500);
        repository.save(member);

        // findById
        Member memberById = repository.findById(member.getMemberId());
        Assertions.assertThat(memberById).isNotNull();

        // update : money: 1500 -> 2500
        repository.update(member.getMemberId(), 5500);
        Member updateMember = repository.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(5500);

        // delete
        repository.delete(member.getMemberId());
        Assertions.assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
