package org.zerock.ex4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zerock.ex4.dto.GuestbookDTO;
import org.zerock.ex4.entity.GuestBook;
import org.zerock.ex4.repository.GuestbookRepository;

@Service
@Slf4j
@RequiredArgsConstructor // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository; // 반드시 final로 선언

    /**
     * 서비스 계층에서는 파라미터를 DTO 타입으로 받는다.
     * 이를 JPA로 처리하기 위해서는 엔터티 타입으 객체로 변환하는 작업이 반드시 필요하다.
     * 변환 기능을 DTO 클래스에 적용하거나,
     * ModelMapper라이브러리나 MapStruct 등을 이용한다.
     * @param dto
     * @return
     */
    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO----------------------------");
        log.info(String.valueOf(dto));

        GuestBook entity = dtoToEntity(dto);

        log.info(String.valueOf(entity));

        // JPA
        repository.save(entity);

        return entity.getGno();

    }
}
