package com.balgruuf.db.balgruuf_db.service.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import com.balgruuf.db.balgruuf_db.entity.Url;
import com.balgruuf.db.balgruuf_db.service.UrlService;

//IMPORT RELATIONS

@Service
public class UrlBaseService {

	private static NamedParameterJdbcTemplate jdbcTemplate;
	
		@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}


    //CRUD METHODS
    
    //CRUD - CREATE
    	
	public Url insert(Url obj) {
		Long id = jdbcTemplate.queryForObject("select max(_id) from `url`", new MapSqlParameterSource(), Long.class);
		obj.set_id(id == null ? 1 : id + 1);
		String sql = "INSERT INTO `url` (`_id`, `longUrl`,`shortKey`) VALUES (:id,:longUrl,:shortKey)";
		
		SqlParameterSource parameters = new MapSqlParameterSource()
		    .addValue("id", obj.get_id())
			.addValue("longUrl", obj.getLongUrl())
			.addValue("shortKey", obj.getShortKey());
		
		jdbcTemplate.update(sql, parameters);
		return obj;
	}
	
    	
    //CRUD - REMOVE
    
	public void delete(Long id) {
		String sql = "DELETE FROM `url` WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		
		jdbcTemplate.update(sql, parameters);
	}

    	
    //CRUD - GET ONE
    	
	public Url get(Long id) {
	    
		String sql = "select * from `url` where `_id` = :id";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
	    
	    return (Url) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(Url.class));
	}


    	
        	
    //CRUD - GET LIST
    	
	public List<Url> getList() {
	    
		String sql = "select * from `url`";
		
	    SqlParameterSource parameters = new MapSqlParameterSource();
	    return jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper(Url.class));
	}

    	
    //CRUD - EDIT
    	
	public Url update(Url obj, Long id) {

		String sql = "UPDATE `url` SET `longUrl` = :longUrl,`shortKey` = :shortKey  WHERE `_id`=:id";

		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("longUrl", obj.getLongUrl())
			.addValue("shortKey", obj.getShortKey());

		jdbcTemplate.update(sql, parameters);
	    return obj;
	}
	
    	
    
    


    
    /*
     * CUSTOM SERVICES
     * 
     *	These services will be overwritten and implemented in urlService.java
     */
    


}
