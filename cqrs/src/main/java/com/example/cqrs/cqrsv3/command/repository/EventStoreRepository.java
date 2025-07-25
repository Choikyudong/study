package com.example.cqrs.cqrsv3.command.repository;

import com.example.cqrs.cqrsv3.common.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventStoreRepository extends JpaRepository<EventEntity, Long> {

	// 특정 aggregateId에 해당하는 모든 이벤트를 버전(version) 오름차순으로 조회합니다.
	// 이는 애그리거트의 상태를 정확하게 재구성하기 위해 이벤트가 발생한 순서대로 필요하기 때문입니다.
	List<EventEntity> findByAggregateIdOrderByVersionAsc(Long aggregateId);

	// (선택 사항) 낙관적 락을 위한 추가 메서드: 특정 aggregateId와 현재 버전보다 큰 버전의 이벤트가 있는지 확인
	// 이 메서드는 save 로직에서 동시성 충돌을 감지하는 데 사용될 수 있습니다.
	boolean existsByAggregateIdAndVersionGreaterThan(Long aggregateId, int version);

}
