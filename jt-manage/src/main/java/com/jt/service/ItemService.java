package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUI_Table;

public interface ItemService {

    EasyUI_Table findItemByPage(Integer page,Integer row);

    void saveItem(Item item, ItemDesc itemDesc);

    void updateItem(Item item,ItemDesc itemDesc);

    void deleteItems(Long[] ids);

    void updateItemStatus(Long[] ids, int status);

    Item findItemById(Long itemId);
}
