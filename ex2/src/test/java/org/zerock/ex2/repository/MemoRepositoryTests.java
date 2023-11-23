package org.zerock.ex2.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {

        System.out.println(memoRepository.getClass().getName());

    }


    // CRUD 테스트

    @Test
    public void testInsertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });

    }

    @Test
    @DisplayName("findByid() -  데이터베이스를 먼저 이용하는 방식")
    public void testSelect() {

        // 데이터베이스에 존재하는 mno
        Long mno = 100L;

        // findById()의 경우 Optional 타입으로 반환된다.
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("==================================");

        // 그렇기 때문에 한번 더 조회 결과가 존재하는지 체크하는 방식으로 작성된다.
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }

        /** 실행결과
         * Hibernate:
         *     select
         *         m1_0.mno,
         *         m1_0.memo_text
         *     from
         *         tbl_memo m1_0
         *     where
         *         m1_0.mno=?
         * ==================================
         * Memo(mno=100, memoText=Sample...100)
         */
    }

    @Transactional
    @Test
    @DisplayName("getOne() - 데이터베이스 이용을 나중에 필요한 순간까지 미루는 방식. @Transactional 어노테이션이 추가로 필요하다.")
    public void testSelect2() {

        // 데이터베이스에 존재하는 mno
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("==================================");

        System.out.println(memo);

        /** 실행결과
         * ==================================
         * Hibernate:
         *     select
         *         m1_0.mno,
         *         m1_0.memo_text
         *     from
         *         tbl_memo m1_0
         *     where
         *         m1_0.mno=?
         * Memo(mno=100, memoText=Sample...100)
         */
    }

    @Test
    @DisplayName("업데이트 테스트 - save()")
    public void testUpdate() {
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("Update Test")
                .build();

        System.out.println(memoRepository.save(memo));

        // 내부적으로 select 쿼리로 해당 ID의 메모가 있는지 먼저 확인하고,
        // 이를 update하는 방식을 취하고 있다.
        /**
         * Hibernate:
         *     select
         *         m1_0.mno,
         *         m1_0.memo_text
         *     from
         *         tbl_memo m1_0
         *     where
         *         m1_0.mno=?
         * Hibernate:
         *     update
         *         tbl_memo
         *     set
         *         memo_text=?
         *     where
         *         mno=?
         * Memo(mno=100, memoText=Update Test)
         */
    }

    @Test
    @DisplayName("삭제 테스트 - deleteById()")
    public void testDelete() {

        Long mno = 100L;

        memoRepository.deleteById(mno); // 반환 타입 void

        // deleteById()는 해당 데이터가 존재하지 않으면 예외 발생.
        // org.springframework.dao.EmptyResultDataAccessException
        /**
         * Hibernate:
         *     select
         *         m1_0.mno,
         *         m1_0.memo_text
         *     from
         *         tbl_memo m1_0
         *     where
         *         m1_0.mno=?
         * Hibernate:
         *     delete
         *     from
         *         tbl_memo
         *     where
         *         mno=?
         */

    }


    // 페이징 처리 테스트
    @Test
    public void testPageDefault() {

        // 1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("-------------------------------------------");

        System.out.println("Total Pages: " + result.getTotalPages()); // 총 페이지 수
        System.out.println("Total Number: " + result.getTotalElements()); // 전체 개수
        System.out.println("Page Number: " + result.getNumber()); // 현재 페이지 번호
        System.out.println("Page Size: " + result.getSize()); // 페이지당 데이터 개수
        System.out.println("has next page?: "+ result.hasNext()); // 다음 페이지 존재 여부
        System.out.println("first page?: " + result.isFirst()); // 시작 패이지(0) 여부

        /**
         * -------------------------------------------
         * Total Pages: 10
         * Total Number: 99
         * Page Number: 0
         * Page Size: 10
         * has next page?: true
         * first page?: true
         */

        System.out.println("-------------------------------------------");

//        for (Memo memo : result.getContent()) {
//            System.out.println(memo);
//        }

        // 이렇게도 가능. get() 메서드를 쓰면 Stream<엔터티 타입>을 반환한다.
//        result.get().forEach(memo -> {
//            System.out.println(memo);
//        });

        /**
         * -------------------------------------------
         * Memo(mno=1, memoText=Sample...1)
         * Memo(mno=2, memoText=Sample...2)
         * Memo(mno=3, memoText=Sample...3)
         * Memo(mno=4, memoText=Sample...4)
         * Memo(mno=5, memoText=Sample...5)
         * Memo(mno=6, memoText=Sample...6)
         * Memo(mno=7, memoText=Sample...7)
         * Memo(mno=8, memoText=Sample...8)
         * Memo(mno=9, memoText=Sample...9)
         * Memo(mno=10, memoText=Sample...10)
         */

    }


    // 정렬 테스트
    @Test
    @DisplayName("정렬 테스트 - Sort.by()")
    public void testSort() {

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); // and를 이용한 조건식 연결

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        // 람다식으로 변환하면 이렇게 간결하게 표현할 수 있다.
        result.get().forEach(System.out::println);

        // 정렬이 먼저 적용되고, 그 후 페이지네이션을 했다는 것을 알 수 있다.
        /**
         * Memo(mno=99, memoText=Sample...99)
         * Memo(mno=98, memoText=Sample...98)
         * Memo(mno=97, memoText=Sample...97)
         * Memo(mno=96, memoText=Sample...96)
         * Memo(mno=95, memoText=Sample...95)
         * Memo(mno=94, memoText=Sample...94)
         * Memo(mno=93, memoText=Sample...93)
         * Memo(mno=92, memoText=Sample...92)
         * Memo(mno=91, memoText=Sample...91)
         * Memo(mno=90, memoText=Sample...90)
         */

    }


    @Test
    @DisplayName("쿼리 메서드 테스트")
    public void testQueryMethods() {

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }

        /**
         * Memo(mno=80, memoText=Sample...80)
         * Memo(mno=79, memoText=Sample...79)
         * Memo(mno=78, memoText=Sample...78)
         * Memo(mno=77, memoText=Sample...77)
         * Memo(mno=76, memoText=Sample...76)
         * Memo(mno=75, memoText=Sample...75)
         * Memo(mno=74, memoText=Sample...74)
         * Memo(mno=73, memoText=Sample...73)
         * Memo(mno=72, memoText=Sample...72)
         * Memo(mno=71, memoText=Sample...71)
         * Memo(mno=70, memoText=Sample...70)
         */

    }

    @Test
    @DisplayName("Pageable을 사용하여 더 간결해진 Query Method의 명")
    public void testQueryMethodWithPageable() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(70L, 80L, pageable);

        result.get().forEach(System.out::println);

        /**
         * Memo(mno=80, memoText=Sample...80)
         * Memo(mno=79, memoText=Sample...79)
         * Memo(mno=78, memoText=Sample...78)
         * Memo(mno=77, memoText=Sample...77)
         * Memo(mno=76, memoText=Sample...76)
         * Memo(mno=75, memoText=Sample...75)
         * Memo(mno=74, memoText=Sample...74)
         * Memo(mno=73, memoText=Sample...73)
         * Memo(mno=72, memoText=Sample...72)
         * Memo(mno=71, memoText=Sample...71)
         */

    }

    // deleteBy... 의 경우 select문으로 엔터티 객체를 가져오는 작업과
    // 각 엔터티를 삭제하는 작업이 같이 이루어지기 때문에 @Transactional을 걸어주었다.
    // 만약 @Transactional 없다면 -> TransactionRequiredException 발생
    // deleteBy... 의 경우 엔터티 객체를 하나씩 삭제하기 때문에 기본적으로 롤백이 적용된다.
    // 그래서 최종 결과 반영을 위해 @Commit이 필요하다.
    // 실제로 deleteBy는 실무에서 많이 사용되지 않는다고 한다.
    @Test
    @Commit
    @Transactional
    public void testDeleteQueryMethods() {

        memoRepository.deleteMemoByMnoLessThan(10L);

        /**
         * Hibernate:
         *     select
         *         m1_0.mno,
         *         m1_0.memo_text
         *     from
         *         tbl_memo m1_0
         *     where
         *         m1_0.mno<?
         * Hibernate:
         *     delete
         *     from
         *         tbl_memo
         *     where
         *         mno=?
         * Hibernate:
         *     delete
         *     from
         *         tbl_memo
         *     where
         *         mno=?
         * ...
         */

        // 개발 시에는 @Query 를 사용하여 위와 같은 비효율적이 부분을 개선한다.

    }

}
