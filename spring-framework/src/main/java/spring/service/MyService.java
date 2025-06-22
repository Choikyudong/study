package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.repository.MyRepoistory;

@Service
public class MyService {

	private final MyRepoistory myRepoistory;

	@Autowired
	public MyService(MyRepoistory myRepoistory) {
		this.myRepoistory = myRepoistory;
	}

	public void save(String save) {
		myRepoistory.save(save);
	}

}
