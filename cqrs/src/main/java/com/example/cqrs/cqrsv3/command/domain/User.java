package com.example.cqrs.cqrsv3.command.domain;

import com.example.cqrs.cqrsv3.common.event.DomainEvent;
import com.example.cqrs.cqrsv3.common.event.UserCreatedEvent;
import com.example.cqrs.cqrsv3.common.event.UserUpdatedEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	private Long userId;

	private String firstName;

	private String lastName;

	private Set<String> contacts;

	private Set<String> addresses;

	private int version;

	// JPA가 이 리스트를 DB에 매핑하지 않도록
	@Transient
	private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

	// 1. 사용자 생성: User를 생성하는 대신, UserCreatedEvent를 발생시킴
	public static User createUser(String firstName, String lastName) {
		User user = new User();
		// 실제 아이디는 랜덤성으로 줘야함, 버전은 0부터
		user.applyNewEvent(new UserCreatedEvent(1L, 0, firstName, lastName));
		return user;
	}

	// 2. 사용자 업데이트: 상태를 직접 바꾸는 대신, UserUpdatedEvent를 발생시킴
	public void updateUser(Set<String> newContacts, Set<String> newAddresses) {
		this.applyNewEvent(new UserUpdatedEvent(this.userId, this.version + 1, newContacts, newAddresses));
	}

	// 3. 내부 상태 변경 로직: 이벤트를 받아 User 객체의 필드를 변경
	// 이 메서드는 새로운 이벤트를 기록할 때도, 과거 이력을 재구성할 때도 사용됩니다.
	private void apply(DomainEvent event) {
		this.userId = event.getAggregateId();
		this.version = event.getVersion();

		if (event instanceof UserCreatedEvent createdEvent) {
			this.firstName = createdEvent.getFirstName();
			this.lastName = createdEvent.getLastName();
		} else if (event instanceof UserUpdatedEvent updatedEvent) {
			this.contacts = updatedEvent.getContacts();
			this.addresses = updatedEvent.getAddresses();
		}
	}

	// 4. 새로운 이벤트를 기록하고, apply 메서드를 호출하여 즉시 상태에 반영
	private void applyNewEvent(DomainEvent event) {
		apply(event); 				  // 이벤트를 적용하여 애그리거트 상태 변경
		uncommittedEvents.add(event); // 아직 저장되지 않은 이벤트 목록에 추가
	}

	// 5. 과거 이력으로부터 User 객체를 재구성하는 핵심 메서드 (UserRepository에서 호출)
	public static User reconstruct(List<DomainEvent> historyEvents) {
		User user = new User();
		for (DomainEvent event : historyEvents) {
			user.apply(event); // 모든 이벤트를 순서대로 적용하여 상태 재구성
		}
		return user;
	}

	// 6. 저장할 이벤트를 Repository에 넘겨주기 위한 메서드
	public List<DomainEvent> getUncommittedEvents() {
		return new ArrayList<>(uncommittedEvents);
	}

	// 7. 이벤트를 저장소에 커밋한 후, 임시 목록을 비우는 메서드
	public void clearUncommittedEvents() {
		uncommittedEvents.clear();
	}

}
