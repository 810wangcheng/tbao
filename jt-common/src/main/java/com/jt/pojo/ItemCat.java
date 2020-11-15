package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors
@TableName("tb_item_cat")
public class ItemCat {

    private Long id;
    private Long parentId;
    private String name;
    private Integer status;    //状态
    private Integer sortOrder; //商品分类排序号
    private Boolean isParent;  //是否为父级
}
