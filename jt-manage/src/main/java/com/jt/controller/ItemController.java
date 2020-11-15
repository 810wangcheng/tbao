package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;
import com.jt.vo.EasyUI_Table;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping("/query")
	@ResponseBody
	public EasyUI_Table findItemByPage(Integer page,Integer rows){
		return itemService.findItemByPage(page,rows);
	}

	/**
	 * 实现商品新增
	 * 统一异常处理，一般需要制定特定异常类型
	 * 说明：需要在controller端进行异常管理
	 */
	@RequestMapping("save")
	@ResponseBody
	public SysResult saveItem(Item item, ItemDesc itemDesc) {
		/*
		 * try { itemService.saveItem(item); return SysResult.success(); } catch
		 * (Exception e) { e.printStackTrace(); return SysResult.fail(); }
		 */
		itemService.saveItem(item,itemDesc);
		return SysResult.success();
	}

	/**
	 * 实现商品修改
	 */
	@RequestMapping("update")
	@ResponseBody
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}
	/**
	 * 删除商品信息
	 * var params = {"ids":1,2,3,4,5};
	 * 如果参数是通过“，”号分割，则接收时可以使用数组接收
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public SysResult deleteItemByIds(Long[] ids) {
		itemService.deleteItems(ids);
		return SysResult.success();
	}
	/**
	 * 商品的下架
	 */
	@RequestMapping("instock")
	@ResponseBody
	public SysResult itemInstock(Long[] ids) {
		int status = 2;
		itemService.updateItemStatus(ids,status);
		return SysResult.success();
	}
	@RequestMapping("reshelf")
	@ResponseBody
	public SysResult itemReshelf(Long[] ids) {
		int status = 1;
		itemService.updateItemStatus(ids,status);
		return SysResult.success();
	}
	/**
	 * localhost:8091/item/query/item/desc/1474391970
	 * 查询商品详情
	 */
	@RequestMapping("query/item/desc/{data.id}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable("data.id")Long id) {
		//需要将数据返回页面
		ItemDesc desc = itemDescService.findDescById(id);
		return SysResult.success(desc);
	}

	/**
	 * http://localhost:8091/item/param/item/query/1474391962
	 * 没有处理只是返回SysResult对象
	 */
	@RequestMapping("param/item/query/{data.id}")
	@ResponseBody
	public SysResult findItemParamById(@PathVariable("data.id")Long id){
		//itemService.findParamById(id);
		return SysResult.success();
	}
}
