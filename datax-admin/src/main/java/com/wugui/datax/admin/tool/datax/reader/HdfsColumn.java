package com.wugui.datax.admin.tool.datax.reader;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * <p>
 * 指定Column信息，type必须填写，index/value必须选择其一
 * </p>
 *
 * @author isacc 2019/04/28 15:05
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HdfsColumn {

	/**
	 * index指定当前列来自于文本第几列(以0开始)
	 */
	private Integer index;
	/**
	 * type指定源数据的类型
	 */
	private String type;
	/**
	 * value指定当前类型为常量，不从源头文件读取数据，而是根据value值自动生成对应的列
	 */
	private String value;
	/**
	 * 列名
	 */
	private String name;
}
