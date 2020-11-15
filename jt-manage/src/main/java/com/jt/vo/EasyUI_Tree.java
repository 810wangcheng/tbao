package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EasyUI_Tree {  //vo：是服务器数据与页面交互的对象，一般都需要转化为json

	private Long id;       //分类id号
	private String text;   //分类名称
	private String state;  //open、closed
}
