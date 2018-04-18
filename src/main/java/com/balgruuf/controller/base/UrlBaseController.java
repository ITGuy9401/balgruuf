package com.balgruuf.controller.base;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.ArrayList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import com.balgruuf.db.balgruuf_db.service.UrlService;
import com.balgruuf.db.balgruuf_db.entity.Url;

//IMPORT RELATIONS

public class UrlBaseController {
    
    @Autowired
	UrlService urlService;



//CRUD METHODS


    //CRUD - CREATE
    @Secured({ "ROLE_PRIVATE_USER" })
		@RequestMapping(value = "/urls", method = RequestMethod.POST, headers = "Accept=application/json")
	public Url insert(@RequestBody Url obj) {
		Url result = urlService.insert(obj);

	    
		
		return result;
	}

	
    //CRUD - REMOVE
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/urls/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void delete(@PathVariable("id") Long id) {
		urlService.delete(id);
	}
	
	
    //CRUD - GET ONE
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/urls/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Url get(@PathVariable Long id) {
		Url obj = urlService.get(id);
		
		
		
		return obj;
	}
	
	
    //CRUD - GET LIST
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/urls", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Url> getList() {
		return urlService.getList();
	}
	
	

    //CRUD - EDIT
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/urls/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public Url update(@RequestBody Url obj, @PathVariable("id") Long id) {
		Url result = urlService.update(obj, id);

	    
		
		return result;
	}
	


/*
 * CUSTOM SERVICES
 * 
 *	These services will be overwritten and implemented in  Custom.js
 */


	
}
