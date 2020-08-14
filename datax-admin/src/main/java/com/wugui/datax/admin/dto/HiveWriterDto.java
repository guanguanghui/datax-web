package com.wugui.datax.admin.dto;

import com.wugui.datax.admin.tool.datax.reader.HdfsColumn;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 构建hive write dto
 *
 * @author jingwk
 * @ClassName hive write dto
 * @Version 2.0
 * @since 2020/01/11 17:15
 */
@Data
public class HiveWriterDto implements Serializable {
    /**
     * Hadoop hdfs文件系统namenode节点地址
     */
    private String writerDefaultFS;
    /**
     * 文件的类型，目前只支持用户配置为"text"、"orc"
     */
    private String writerFileType;

    /**
     * 要写入的文件路径
     */
    private String writerPath;
    /**
     * HdfsWriter写入时的文件名
     */
    private String writerFileName;
    /**
     * 写入数据的字段
     */
    @NotEmpty
    private List<HdfsColumn> column;
    /**
     * hdfswriter写入前数据清理处理模式：
     */
    private String writeMode;
    /**
     * hdfswriter写入时的字段分隔符
     */
    private String writeFieldDelimiter;
    /**
     * text类型文件支持压缩类型有gzip、bzip2;orc类型文件支持的压缩类型有NONE、SNAPPY（需要用户安装SnappyCodec）
     */
    private String compress;
    /**
     * 读取文件的编码配置
     * 默认值：utf-8
     */
    private String encoding;
    /**
     * 配置与Hadoop相关的一些高级参数，比如HA的配置
     */
    private Map<String, Object> hadoopConfig;
    /**
     * 是否有Kerberos认证，默认false
     * 若配置true，则配置项kerberosKeytabFilePath，kerberosPrincipal为必填
     */
    private Boolean haveKerberos;
    /**
     * Kerberos认证 keytab文件路径，绝对路径
     */
    private String kerberosKeytabFilePath;
    /**
     * Kerberos认证Principal名，如xxxx/hadoopclient@xxx.xxx
     */
    private String kerberosPrincipal;
}
