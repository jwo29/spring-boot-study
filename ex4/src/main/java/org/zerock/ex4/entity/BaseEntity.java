package org.zerock.ex4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 해당 엔터티로 테이블이 생성되지 않게 하는 어노테이션. 실제 테이블은 BaseEntity 클래스를 상속한 엔터티의 클래스로 테이블이 생성된다.
@EntityListeners(value = { AuditingEntityListener.class }) // 엔터티의 생성/변화를 감지하는 리스너. 사용하기 위해서는 메인 클래스에 @EnableJpaAuditing 을 추가해야 한다.
@Getter
abstract class BaseEntity {

    @CreatedDate // 엔터티의 생성시간을 자동으로 처리
    @Column(name = "regdate", updatable = false) // 엔터티 객체를 디비에 반영할 때 regdate 컬럼값은 변경되지 않는다.
    private LocalDateTime regDate;

    @LastModifiedDate // 엔터티의 최종 수정시간을 자동으로 처리
    @Column(name = "moddate")
    private LocalDateTime modDate;

}
