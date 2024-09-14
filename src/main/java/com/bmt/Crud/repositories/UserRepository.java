package com.bmt.Crud.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.bmt.Crud.models.User;
@Repository
public class UserRepository {

	
	
	

@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<User> getUsers(){
		var users = new ArrayList<User>();
		
		String sql = "SELECT * FROM users ORDER BY id DESC";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		
		
		while(rows.next()) {
			User user = new User();
			user.setId(rows.getInt("id"));
			user.setUsername(rows.getString("username"));
			user.setPassword(rows.getString("password"));
			user.setEmail(rows.getString("email"));
		/*	user.setPhone(rows.getString("phone"));
			user.setAddress(rows.getString("address"));
*/

			users.add(user);
		}
		
		return users;
		
		
	}
public User getUser(int id) {
	String sql = "SELECT * FROM users WHERE id=?";
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);
	
	if(rows.next()) {
		
		User user = new User();
		user.setId(rows.getInt("id"));
		user.setUsername(rows.getString("username"));
		user.setPassword(rows.getString("password"));
		user.setEmail(rows.getString("email"));
		/*user.setPhone(rows.getString("phone"));
		user.setAddress(rows.getString("address"));
		*/
		return user;
		
		
		
	}
	return null;
	
	
	
	
	
}
	public User getUser(String email) {
	String sql = "SELECT * FROM users WHERE email=?";
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, email);
	
	if(rows.next()) {
		
		User user = new User();
		user.setId(rows.getInt("id"));
		user.setUsername(rows.getString("username"));
		user.setPassword(rows.getString("password"));
		user.setEmail(rows.getString("email"));
		/*user.setPhone(rows.getString("phone"));
		user.setAddress(rows.getString("address"));
		*/
		return user;
		
		
		
	}
	return null;

	
}
	public User createUser(User user) {
	 
		String sql = "INSERT INTO users  (username, password, email) VALUES(?,?,?) ";
		
		int count = jdbcTemplate.update(sql, user.getUsername(),user.getPassword(),user.getEmail());
		 
		if (count > 0) {
			
	  int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	  return getUser(id);
		}
		
		return null;
		
	}
	
	public User updateUser(User user) {
	    String sql = "UPDATE users SET username = ?, password = ?, email = ? WHERE id = ?";

	    int rowsAffected = jdbcTemplate.update(sql, 
	        user.getUsername(), 
	        user.getPassword(), 
	        user.getEmail(), 
	       /* user.getPhone(), 
	        user.getAddress(), */
	        user.getId() // Make sure to pass the ID for the WHERE clause
	    );

	    // Check if the update was successful
	    if (rowsAffected > 0) {
	        return getUser(user.getId()); // Return the updated user
	    } else {
	        throw new RuntimeException("User update failed or no user found with id: " + user.getId());
	    }
	}

	
	public void deleteUser(int id) {
		
		String sql = "DELETE FROM users WHERE id=?";
		jdbcTemplate.update(sql, id);
	}

	
	

}

