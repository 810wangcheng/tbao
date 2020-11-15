package com.jt.service;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Override
	public ItemDesc findDescById(Long id) {
		ItemDesc desc = itemDescMapper.selectById(id);
		
		return desc;
		
	}

}
