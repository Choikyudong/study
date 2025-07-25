package com.example.cqrs.cqrsv2.query.listener;

import com.example.cqrs.cqrsv2.common.event.UserCreatedEvent;
import com.example.cqrs.cqrsv2.common.event.UserUpdatedEvent;
import com.example.cqrs.cqrsv2.query.domain.AddressReadEntity;
import com.example.cqrs.cqrsv2.query.domain.ContactReadEntity;
import com.example.cqrs.cqrsv2.query.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserEventListener {

	private final UserQueryRepository repository;

	@Transactional("readTransactionManager")
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
	public void handleUserCreatedEvent(UserCreatedEvent event) {
		repository.findById(event.userId()).ifPresent(userReadEntity -> {
			userReadEntity.updateFirstName(event.firstName());
			userReadEntity.updateLastName(event.lastName());
			repository.save(userReadEntity);
		});
	}

	@Transactional("readTransactionManager")
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
	public void handleUserUpdatedEvent(UserUpdatedEvent event) {
		repository.findById(event.userId()).ifPresent(userReadEntity -> {
			Set<ContactReadEntity> newContactReadEntities = event.contacts().stream()
					.map(contactVo -> {
						ContactReadEntity cre = new ContactReadEntity(contactVo.getType(), contactVo.getDetail());
						cre.setUser(userReadEntity);
						return cre;
					})
					.collect(Collectors.toSet());
			userReadEntity.changeContacts(newContactReadEntities);

			Set<AddressReadEntity> newAddressReadEntities = event.addresses().stream()
					.map(addressVo -> {
						AddressReadEntity are = new AddressReadEntity(addressVo.getCity(), addressVo.getState(), addressVo.getPostcode());
						are.setUser(userReadEntity); // 양방향 관계 설정
						return are;
					})
					.collect(Collectors.toSet());
			userReadEntity.changeAddress(newAddressReadEntities);
		});
	}

}
