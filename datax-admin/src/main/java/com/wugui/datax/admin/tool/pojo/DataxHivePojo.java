package com.wugui.datax.admin.tool.pojo;

import com.wugui.datax.admin.entity.JobDatasource;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用于传参，构建json
 *
 * @author jingwk
 * @ClassName DataxHivePojo
 * @Version 2.0
 * @since 2020/01/11 17:15
 */
@Data
public class DataxHivePojo {

    /**
     * hive列名
     */
    private List<Map<String,Object>> columns;

    /**
     * 数据源信息
     */
    private JobDatasource jdbcDatasource;

    private String readerPath;

    private String readerDefaultFS;

    private String readerFileType;

    private String readerFieldDelimiter;

    private String writerDefaultFS;

    private String writerFileType;

    private String writerPath;

    private String writerFileName;

    private String writeMode;

    private String writeFieldDelimiter;

    private Boolean skipHeader;

    /**
     * 读取文件的编码配置
     * 默认值：utf-8
     */
    private String encoding;
    /**
     * 文本文件中无法使用标准字符串定义null(空指针)，DataX提供nullFormat定义哪些字符串可以表示为null
     * 例如配置，nullFormat:"\N"，那么如果源头数据是"\N"，DataX视作null字段
     */
    private String nullFormat;
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
    /**
     * 当fileType（文件类型）为csv下的文件压缩方式，目前仅支持 gzip、bz2、zip、lzo、lzo_deflate、hadoop-snappy、framing-snappy压缩
     * orc文件类型下无需填写
     */
    private String compress;
    /**
     * 配置与Hadoop相关的一些高级参数，比如HA的配置
     */
    private Map<String, Object> hadoopConfig;
    /**
     * 读取CSV类型文件参数配置
     */
    private Map<String, Object> csvReaderConfig;
}
