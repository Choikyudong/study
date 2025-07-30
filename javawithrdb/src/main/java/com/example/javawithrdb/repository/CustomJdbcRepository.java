package com.example.javawithrdb.repository;

import com.example.javawithrdb.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class CustomJdbcRepository {

	private final DataSource dataSource;

	public void insert() {
		String sql = "insert into users (name) values(?)";
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			for (int i = 0; i < 100_000; i++) {
				String name = "user" + i;
				ps.setString(1, name);
			}
		} catch (SQLException s) {
			throw new RuntimeException(s);
		}
	}

	public User findById(long findId) {
		String sql = "select * from user where id = " + findId;
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			long id = rs.getLong("id");
			String name = rs.getString("name");
			LocalDateTime createdAt = LocalDateTime.parse(rs.getString("createdAt"));
			return new User(id, new ArrayList<>(), name, createdAt);
		} catch (SQLException s) {
			throw new RuntimeException(s);
		}
	}


}
