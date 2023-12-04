package org.zerock.ex4.service;

import org.zerock.ex4.dto.GuestbookDTO;
import org.zerock.ex4.entity.GuestBook;

public interface GuestbookService {

    Long register(GuestbookDTO dto);

    /**
     * Java8부터는 default 키워드를 통해,
     * 인터페이스의 실제 내용을 가지는 코드를 만들 수 있게 되었다.
     * 그 결과, [인터페이스 -> 추상 클래스 -> 구현 클래스] 형태로 구현되던 방식에서
     * [인터페이스 -> 구현 클래스] 형태로 구현 가능하게 되었다.
     *
     * 구현 클래스에서 동작할 수 있는 dtoToEntity()의 정의.
     * @param dto
     * @return
     */
    default GuestBook dtoToEntity(GuestbookDTO dto) {
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

}
