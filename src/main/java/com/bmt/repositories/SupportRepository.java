package com.bmt.Crud.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.bmt.Crud.models.Support;

@Repository
public class SupportRepository {
@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Support> getSupports(){
		var supports = new ArrayList<Support>();
		
		String sql = "SELECT * FROM supports ORDER BY id DESC";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		
		
		while(rows.next()) {
			Support support = new Support();
			support.setId(rows.getInt("id"));
			support.setFirstname(rows.getString("firstname"));
			support.setLastname(rows.getString("lastname"));
			support.setEmail(rows.getString("email"));
			support.setPhone(rows.getString("phone"));
			support.setAddress(rows.getString("address"));


			supports.add(support);
		}
		
		return supports;
		
		
	}
public Support getSupport(int id) {
	String sql = "SELECT * FROM supports WHERE id=?";
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);
	
	if(rows.next()) {
		
		Support support = new Support();
		support.setId(rows.getInt("id"));
		support.setFirstname(rows.getString("firstname"));
		support.setLastname(rows.getString("lastname"));
		support.setEmail(rows.getString("email"));
		support.setPhone(rows.getString("phone"));
		support.setAddress(rows.getString("address"));
		
		return support;
		
		
		
	}
	return null;
	
	
	
	
	
}
	public Support getSupport(String email) {
	String sql = "SELECT * FROM supports WHERE email=?";
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, email);
	
	if(rows.next()) {
		
		Support support = new Support();
		support.setId(rows.getInt("id"));
		support.setFirstname(rows.getString("firstname"));
		support.setLastname(rows.getString("lastname"));
		support.setEmail(rows.getString("email"));
		support.setPhone(rows.getString("phone"));
		support.setAddress(rows.getString("address"));
		
		return support;
		
		
		
	}
	return null;

	
}
	public Support createSupport(Support support) {
	 
		String sql = "INSERT INTO supports  (firstname, lastname, email, phone, address) VALUES(?,?,?,?,?) ";
		
		int count = jdbcTemplate.update(sql, support.getFirstname(),support.getLastname(),support.getEmail(),support.getPhone(),support.getAddress());
		 
		if (count > 0) {
			
	  int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	  return getSupport(id);
		}
		
		return null;
		
	}
	


	
	

}
