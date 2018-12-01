package com.paramount.shopping.controller;

import java.util.Map;

import com.paramount.shopping.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/itemsearch")
public class ItemSearchController {
	
	@Autowired
	private ItemSearchService itemSearchService;
	
	@RequestMapping("/search")
	public Map search(@RequestBody Map searchMap){
		
		return itemSearchService.search(searchMap);
		
	}

}
