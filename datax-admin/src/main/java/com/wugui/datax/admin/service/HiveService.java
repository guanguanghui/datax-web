package com.wugui.datax.admin.service;


import com.wugui.datatx.core.biz.model.ReturnT;
import com.wugui.datax.admin.dto.HiveInfoDTO;

/**
 * <p>
 * Hive CURD
 * </p>
 *
 * @author isacc 2019/04/28 19:38
 */
public interface HiveService {

    /**
     * 创建Hive表
     *
     * @param hiveInfoDTO HiveInfoDTO
     * @return com.isacc.datax.api.dto.ApiResult<com.isacc.datax.api.dto.hive.HiveInfoDTO>
     * @author isacc 2019-04-28 19:44
     */
    ReturnT<Object> createTable(Long datasourceId, HiveInfoDTO hiveInfoDTO);

    /**
     * 新建Hive数据库
     *
     * @param databaseName 数据库名称
     * @return com.isacc.datax.api.dto.ApiResult<java.lang.String>
     * @author isacc 2019-04-29 9:58
     */
    ReturnT<Object> createDatabase(Long datasourceId, String databaseName);

    /**
     * 为hive分区表增加分区
     *
     * @param hiveInfoDTO HiveInfoDTO
     * @return com.isacc.datax.api.dto.ApiResult<java.lang.Object>
     * @author isacc 2019/5/9 14:24
     */
    ReturnT<Object> addPartition(Long datasourceId, HiveInfoDTO hiveInfoDTO);

    /**
     * 删除Hive表
     *
     * @param hiveInfoDTO HiveInfoDTO
     * @return com.isacc.datax.api.dto.ApiResult<java.lang.String>
     * @author isacc 2019-04-29 11:28
     */
    ReturnT<Object> deleteTable(Long datasourceId, HiveInfoDTO hiveInfoDTO);

    /**
     * 删除Hive数据库
     *
     * @param databaseName 数据库名称
     * @return com.isacc.datax.api.dto.ApiResult<java.lang.String>
     * @author isacc 2019-04-29 9:58
     */
    ReturnT<Object> deleteDatabase(Long datasourceId, String databaseName);
}
