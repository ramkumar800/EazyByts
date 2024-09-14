package com.bmt.Crud.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.bmt.Crud.models.Client;

@Repository
public class ClientsRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Client> getClients(){
		var clients = new ArrayList<Client>();
		
		String sql = "SELECT * FROM clients ORDER BY id DESC";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		
		
		while(rows.next()) {
			Client client = new Client();
			client.setId(rows.getInt("id"));
			client.setFirstname(rows.getString("firstname"));
			client.setLastname(rows.getString("lastname"));
			client.setEmail(rows.getString("email"));
			client.setPhone(rows.getString("phone"));
			client.setAddress(rows.getString("address"));


			clients.add(client);
		}
		
		return clients;
		
		
	}
public Client getClient(int id) {
	String sql = "SELECT * FROM clients WHERE id=?";
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);
	
	if(rows.next()) {
		
		Client client = new Client();
		client.setId(rows.getInt("id"));
		client.setFirstname(rows.getString("firstname"));
		client.setLastname(rows.getString("lastname"));
		client.setEmail(rows.getString("email"));
		client.setPhone(rows.getString("phone"));
		client.setAddress(rows.getString("address"));
		
		return client;
		
		
		
	}
	return null;
	
	
	
	
	
}
	public Client getClient(String email) {
	String sql = "SELECT * FROM clients WHERE email=?";
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, email);
	
	if(rows.next()) {
		
		Client client = new Client();
		client.setId(rows.getInt("id"));
		client.setFirstname(rows.getString("firstname"));
		client.setLastname(rows.getString("lastname"));
		client.setEmail(rows.getString("email"));
		client.setPhone(rows.getString("phone"));
		client.setAddress(rows.getString("address"));
		
		return client;
		
		
		
	}
	return null;

	
}
	public Client createClient(Client client) {
	 
		String sql = "INSERT INTO clients  (firstname, lastname, email, phone, address) VALUES(?,?,?,?,?) ";
		
		int count = jdbcTemplate.update(sql, client.getFirstname(),client.getLastname(),client.getEmail(),client.getPhone(),client.getAddress());
		 
		if (count > 0) {
			
	  int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	  return getClient(id);
		}
		
		return null;
		
	}
	
	public Client updateClient(Client client) {
	    String sql = "UPDATE clients SET firstname = ?, lastname = ?, email = ?, phone = ?, address = ? WHERE id = ?";

	    int rowsAffected = jdbcTemplate.update(sql, 
	        client.getFirstname(), 
	        client.getLastname(), 
	        client.getEmail(), 
	        client.getPhone(), 
	        client.getAddress(), 
	        client.getId() // Make sure to pass the ID for the WHERE clause
	    );

	    // Check if the update was successful
	    if (rowsAffected > 0) {
	        return getClient(client.getId()); // Return the updated client
	    } else {
	        throw new RuntimeException("Client update failed or no client found with id: " + client.getId());
	    }
	}

	
	public void deleteClient(int id) {
		
		String sql = "DELETE FROM clients WHERE id=?";
		jdbcTemplate.update(sql, id);
	}

	
	

}
