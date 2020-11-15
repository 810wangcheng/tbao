package com.jt.controller;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("queryItemName")
    @ResponseBody
    public String findItemCatNameById(Long itemCatId){
        return itemCatService.findItemCatById(itemCatId);
    }

    /**
     * 实现商品分类树形结构查询
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public List<EasyUI_Tree> findEasyUITree(@RequestParam(name="id",defaultValue="0")Long parentId) {
        /*
         * //1.定义parentId Long parentId = id==null?0L:id; //return
         * itemCatService.findItemCat();
         */
        //return itemCatService.findItemCatByParentId(parentId);
        return itemCatService.findItemCatByCache(parentId);
    }
}
