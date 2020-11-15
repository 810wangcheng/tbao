package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUI_Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;


	@Override
	public EasyUI_Table findItemByPage(Integer page, Integer rows) {
		//1.获取总记录数
		Integer count = itemMapper.selectCount(null);
		//2.根据当前页获取起始值
		int start = (page - 1) * rows;
		List<Item> itemList = itemMapper.findItemByPage(start, rows);
		return new EasyUI_Table(count,itemList);
	}

	//需要事务控制
	@Transactional //添加事务控制
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//因为商品详情与商品id一致，但是item数据是主键
		//自增只有入库之后才能获取主键信息，底层封装 select last_insert_id()
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	/**
	 * 1.手写sql
	 * delete from tb_item where id in(1,2,3,4)
	 * 2.使用mybatisplus
	 */
	@Override
	@Transactional
	public void deleteItems(Long[] ids) {
		/*
		 * QueryWrapper<Item> queryWrapper = new QueryWrapper<>(); for(Long id : ids) {
		 * queryWrapper.eq("id", id); itemMapper.delete(queryWrapper); }
		 */
		//itemMapper.deleteItems(ids);
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}

	/**
	 * @Transactional(rollbackFor = IOException.class)
	 * rollbackfor 指定异常类型回滚
	 * noRollbackFor 指定异常类型不回滚
	 */
	@Override
	@Transactional
	public void updateItem(Item item,ItemDesc itemDesc) {
		/*
		 * UpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>();
		 * updateWrapper.eq("id", item.getId()); itemMapper.update(item, updateWrapper);
		 */
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

	@Override
	@Transactional
	public void updateItemStatus(Long[] ids, int status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		List<Long> asList = Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>();
		updateWrapper.in("id", asList);
		itemMapper.update(item, updateWrapper);
		//itemMapper.updateItemStatus(ids,status);

	}

	@Override
	public Item findItemById(Long itemId) {
		System.out.println("查询数据库");
		return itemMapper.selectById(itemId);
	}
}
