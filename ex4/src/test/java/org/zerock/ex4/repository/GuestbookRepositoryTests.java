package org.zerock.ex4.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.ex4.entity.GuestBook;
import org.zerock.ex4.entity.QGuestBook;

import java.awt.*;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1, 300).forEach(i -> {

            GuestBook guestBook = GuestBook.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer("user" + (i % 10))
                    .build();

            System.out.println(guestbookRepository.save(guestBook));
        });
    }

    @Test
    public void updateTest() {

        Optional<GuestBook> result = guestbookRepository.findById(300L); // 존재하는 번호로 테스트

        if (result.isPresent()) {

            GuestBook guestBook = result.get();

            guestBook.changeTitle("Changed Title...");
            guestBook.changeContent("Changed Content...");

            guestbookRepository.save(guestBook);
        }
    }

    @Test
    @DisplayName("단일 항목 검색 테스트")
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        // Q도메인 클래스를 이용하면
        // 엔터티 클래스에 선언된 필드들을 변수로 활용할 수 있다.
        QGuestBook qGuestBook = QGuestBook.guestBook; // 1

        String keyword = "1";

        // BooleanBuilder: WHERE 문에 들어가는 조건을 넣어주는 컨테이너 같은 역할.
        // 안에 들어가는 값은 com.querydsl.core.types.Predicate 타입(Java의 Predicate 타입이 아니므로 주의!)
        BooleanBuilder builder = new BooleanBuilder(); // 2

        BooleanExpression expression = qGuestBook.title.contains(keyword); // 3

        // 만들어진 조건문의 조합: and() 또는 or()
        builder.and(expression); // 4

        // 페이징 처리와 검색 처리를 동시에
        Page<GuestBook> result = guestbookRepository.findAll(builder, pageable); // 5

//        result.stream().forEach(guestBook -> {
//            System.out.println(guestBook);
//        });

        result.stream().forEach(System.out::println);

    }

    @Test
    @DisplayName("다중 항목 검색 테스트")
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);

        BooleanExpression exAll = exContent.or(exContent); // 1 -- BooleanExpression 클래스 끼리의 조건 결합
        builder.and(exAll); // 2 -- BooleanBuilder와  BooleanExpression 끼리의 조건 결합
        builder.and(qGuestBook.gno.gt(0L)); // 3 -- BooleanBuilder 에 조건 결합

        Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);

//        result.stream().forEach(guestBook -> {
//            System.out.println(guestBook);
//        });

        result.stream().forEach(System.out::println);

    }
}
