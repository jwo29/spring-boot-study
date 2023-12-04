package org.zerock.ex4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.ex4.entity.GuestBook;

// Querydsl을 사용하기 위한 QuerydslPredicateExecutor 인터페이스 상속
public interface GuestbookRepository extends JpaRepository<GuestBook, Long>, QuerydslPredicateExecutor<GuestBook> {
}
