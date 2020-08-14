package com.wugui.datax.admin.tool.datax.reader;

import com.google.common.collect.Maps;
import com.wugui.datax.admin.tool.pojo.DataxHivePojo;

import java.util.Map;

/**
 * hive reader 构建类
 *
 * @author jingwk
 * @version 2.0
 * @since 2020/01/05
 */
public class HiveReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "hdfsreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }


    @Override
    public Map<String, Object> buildHive(DataxHivePojo plugin) {
        //构建
        Map<String, Object> readerObj = Maps.newLinkedHashMap();
        readerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();
        parameterObj.put("path", plugin.getReaderPath());
        parameterObj.put("defaultFS", plugin.getReaderDefaultFS());
        parameterObj.put("fileType", plugin.getReaderFileType());
        parameterObj.put("fieldDelimiter", plugin.getReaderFieldDelimiter());
        parameterObj.put("skipHeader", plugin.getSkipHeader());
        parameterObj.put("column", plugin.getColumns());

        if(plugin.getHaveKerberos()!=null && plugin.getHaveKerberos()){
            parameterObj.put("haveKerberos",true);
            parameterObj.put("kerberosPrincipal",plugin.getKerberosPrincipal());
            parameterObj.put("kerberosKeytabFilePath", plugin.getKerberosKeytabFilePath());
        }

        if(plugin.getHadoopConfig()!=null){
            parameterObj.put("hadoopConfig", plugin.getHadoopConfig());
        }

        parameterObj.put("compress", plugin.getCompress());
        parameterObj.put("nullFormat", plugin.getNullFormat());
        parameterObj.put("csvReaderConfig", plugin.getCsvReaderConfig());

        readerObj.put("parameter", parameterObj);
        return readerObj;
    }
}
