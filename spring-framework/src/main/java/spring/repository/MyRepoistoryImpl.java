package spring.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MyRepoistoryImpl implements MyRepoistory {
	@Override
	public void save(String saveSomething) {
		System.out.println("저장됨 : " + saveSomething);
	}
}
