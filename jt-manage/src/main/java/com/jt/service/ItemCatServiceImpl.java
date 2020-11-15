package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.utils.ObjectMapperUtils;
import com.jt.vo.EasyUI_Tree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private Jedis jedis;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public String findItemCatById(Long id) {
        ItemCat itemCat = itemCatMapper.selectById(id);
        return itemCat.getName();
    }

    @Override
    public List<EasyUI_Tree> findItemCatByParentId(Long parentId) {
        ArrayList<EasyUI_Tree> treeList = new ArrayList<EasyUI_Tree>();
        //1.获取数据库数据
        List<ItemCat> itemCatList = findItemCatList(parentId);
        for(ItemCat ic : itemCatList) {
            Long id = ic.getId();
            String text = ic.getName();
            String state = ic.getIsParent()?"closed":"open";
            EasyUI_Tree tree = new EasyUI_Tree(id,text,state);
            treeList.add(tree);
        }
        return treeList;
    }

    @Override
    public List<EasyUI_Tree> findItemCatByCache(Long parentId) {
        List<EasyUI_Tree> treeList = new ArrayList<>();
        String key = "ITEM_CAT"+parentId;
        String result = jedis.get(key);
        if (StringUtils.isEmpty(result)){
            System.out.println("查询数据库");
            //缓存中没有，查询数据库
            treeList = findItemCatByParentId(parentId);
            //将查询数据转化为json
            String json = ObjectMapperUtils.toJSON(treeList);
            //存入到redis中
            jedis.set(key,json);
        }else {
            System.out.println("从缓存中获取数据");
            treeList = ObjectMapperUtils.toObject(result,treeList.getClass());
        }
        return treeList;
    }

    public List<ItemCat> findItemCatList(Long parentId){
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<ItemCat>();
        queryWrapper.eq("parent_id", parentId);
        return itemCatMapper.selectList(queryWrapper);
    }
}
