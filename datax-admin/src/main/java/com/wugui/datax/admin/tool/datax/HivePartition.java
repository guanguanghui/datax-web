package com.wugui.datax.admin.tool.datax;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * <p>
 * Hive分区信息
 * </P>
 *
 * @author isacc 2019/05/09 13:46
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HivePartition {

    /**
     * 分区类型
     */
    @Builder.Default
    private String type = "STRING";
    /**
     * 分区名称
     */
    private String name;
    /**
     * 分区值
     */
    private String value;

}
