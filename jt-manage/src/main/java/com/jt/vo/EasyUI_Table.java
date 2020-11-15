package com.jt.vo;

import com.jt.pojo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors
@NoArgsConstructor
@AllArgsConstructor
public class EasyUI_Table {

    private Integer total;
    private List<Item> rows;
}
