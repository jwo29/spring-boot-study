package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex2.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    // mno 범위 설정하고, mno 의 역순으로 정렬하는 쿼리 메서드
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // 위의 메서드명은 너무 길고 복잡하다. Pageable과의 결합을 통해 더 간결하게 만들 수 있다.
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);
}
