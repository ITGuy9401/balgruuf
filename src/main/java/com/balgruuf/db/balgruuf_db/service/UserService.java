package com.balgruuf.db.balgruuf_db.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.balgruuf.db.balgruuf_db.entity.User;

@Service
public class UserService{

	private static NamedParameterJdbcTemplate jdbcTemplate;
	private static RolesService rolesService = new RolesService();
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

    	
	public User login(String username, String password) {
		String sql = "select * from `user` where `username` = :username AND  `password` = :password";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("username", username)
		.addValue("password", password);
	    
	    try {
		    User user = (User) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(User.class));
		    user = this.addRoles(user);
		    return user;
	    } catch(EmptyResultDataAccessException e){
	    	return null;
	    }
	}
	
	//CRUD - CREATE
	public User insert(User obj) {
		Long id = jdbcTemplate.queryForObject("select max(_id) from `user`", new MapSqlParameterSource(), Long.class);
		obj.set_id(id == null ? 1 : id + 1);
		String sql = "INSERT INTO `user` (`_id`, `username`, `password`, `name`, `surname` , `mail` )	VALUES (:id, :username , :password, :name, :surname, :mail )";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", obj.get_id())
			.addValue("username", obj.getUsername())
			.addValue("password", obj.getPassword())
			.addValue("name", obj.getName())
			.addValue("surname", obj.getSurname())
			.addValue("mail", obj.getMail());

		jdbcTemplate.update(sql, parameters);

		// Insert Roles
		this.updateRoles(obj.get_id(), obj.getRoles());
		
	    return obj;
	}
	
    
    //CRUD - GET ONE
	public User get(Long id) {
		String sql = "select * from `user` where `_id` = :id";
	    SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
	    User user = (User) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(User.class));
	    user = this.addRoles(user);
	    return user;
	}
   
    
    //CRUD - GET LIST
	public List<User> getList() {
		String sql = "select * from `user`";
	    SqlParameterSource parameters = new MapSqlParameterSource();
	    List<User> list = jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper(User.class));
	    
	    for (User user : list) {
			user = this.addRoles(user);
		}
	    
	    return list;
	}
        	
        
    //CRUD - EDIT
	public User update(User obj, Long id) {
		String sql = "UPDATE `user` SET `name` = :name, `surname` = :surname , `mail` = :mail WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("name", obj.getName())
			.addValue("surname", obj.getSurname())
			.addValue("mail", obj.getMail());
		jdbcTemplate.update(sql, parameters);
		
		// Update Roles
		this.updateRoles(id, obj.getRoles());
		
	    return obj;
	}
	
	
    //CRUD - REMOVE
	public void delete(Long id) {
		
		// Remove roles
	    rolesService.deleteNotInArray(id, new ArrayList<String>());
	    
	    // Remove User
		String sql = "DELETE FROM Users WHERE \"id\"=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		jdbcTemplate.update(sql, parameters);
	    
	}


	public boolean changePassword(Long id_user, Map<String, String> params) throws Exception {
		
		//AuthenticationService auth =(AuthenticationService) SecurityContextHolder.getContext().getAuthentication();

		String usernameAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
		String passwordAdmin = params.get("passwordAdmin");
		String passwordNew= params.get("passwordNew");

		// Check admin user
		User admin = this.login(usernameAdmin, passwordAdmin);
		
		if(admin == null)
			throw new Exception("Admin password not valid");
		if (!admin.hasRole("ADMIN"))
			throw new Exception("User is not admin");
		
		return this.updatePassword(id_user, passwordNew);
	}
	

	// UTILS FUNCTION

	public boolean updatePassword(Long id_user, String password) {
		String sql = "UPDATE `user` SET `password` = :password WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id_user)
			.addValue("password", password);
		Integer rowNum = jdbcTemplate.update(sql, parameters);
		return rowNum != null && rowNum > 0;
	}
	
    public User addRoles(User user) {
	    ArrayList<String> roles = rolesService.getRolesAsStringArray(user.get_id());
	    user.setRoles(roles);
	    return user;
	}

	public void updateRoles(Long id_user, ArrayList<String> roles) {

		// Delete not in array
	    rolesService.deleteNotInArray(id_user, roles);
		
		// Get actual    		
	    List<String> actual = rolesService.getRolesAsStringArray(id_user);
	    
		// Insert new
		for (String role : roles) {
			if (actual.indexOf(role) == -1){
				rolesService.insert(id_user, role);
			}
		}
		
	}
	
}