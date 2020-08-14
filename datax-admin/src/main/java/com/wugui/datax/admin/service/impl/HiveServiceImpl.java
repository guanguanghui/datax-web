package com.wugui.datax.admin.service.impl;

import com.wugui.datatx.core.biz.model.ReturnT;
import com.wugui.datatx.core.util.Constants;
import com.wugui.datax.admin.dto.HiveInfoDTO;
import com.wugui.datax.admin.entity.JobDatasource;
import com.wugui.datax.admin.service.HiveService;
import com.wugui.datax.admin.service.JobDatasourceService;
import com.wugui.datax.admin.tool.datax.HivePartition;
import com.wugui.datax.admin.tool.datax.reader.HdfsColumn;
import com.wugui.datax.admin.tool.query.HiveQueryTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


/**
 * <p>
 * Hive Service Impl
 * </p>
 *
 * @author isacc 2019/04/28 19:41
 */
@Service
@Slf4j
public class HiveServiceImpl implements HiveService {

    @Autowired
    private JobDatasourceService jobDatasourceService;

    protected JdbcTemplate getJdbcTemplate(Long datasourceId) throws SQLException {
        //获取数据源对象
        JobDatasource datasource = jobDatasourceService.getById(datasourceId);
        DataSource dataSource = new HiveQueryTool(datasource).getConnDataSource();
        JdbcTemplate hiveJdbcTemplate = new JdbcTemplate(dataSource);
        return hiveJdbcTemplate;
    }

    @Override
    public ReturnT<Object> createTable(Long datasourceId, HiveInfoDTO hiveInfoDTO) {

        final ReturnT<Object> successApiResult = ReturnT.initSuccess();
        List<HdfsColumn> columns = hiveInfoDTO.getColumns();
        StringBuilder sb = new StringBuilder();
        columns.forEach(column -> sb.append(column.getName()).append(Constants.SPACE).append(column.getType()).append(Constants.SPLIT_COMMA));
        final String columnSql = sb.toString().substring(0, sb.toString().length() - 1);
        final String sql;
        // 检验是否是分区表
        List<HivePartition> partitionList = hiveInfoDTO.getPartitionList();
        if (partitionList.isEmpty()) {
            // 无分区
            sql = String.format("CREATE TABLE IF NOT EXISTS `%s.%s`(%s) ROW FORMAT DELIMITED FIELDS TERMINATED BY \"%s\" STORED AS %s",
                    hiveInfoDTO.getDatabaseName(), hiveInfoDTO.getTableName(),
                    columnSql, hiveInfoDTO.getFieldDelimiter(),
                    hiveInfoDTO.getFileType());
        } else {
            // 有分区
            StringBuilder dtSb = new StringBuilder();
            partitionList.forEach(partition -> dtSb.append(partition.getName()).append(Constants.SPACE).append(partition.getType()).append(Constants.SPLIT_COMMA));
            final String partitionSql = dtSb.toString().substring(0, dtSb.toString().length() - 1);
            sql = String.format("CREATE TABLE IF NOT EXISTS `%s.%s`(%s) PARTITIONED BY (%s) ROW FORMAT DELIMITED FIELDS TERMINATED BY \"%s\" STORED AS %s",
                    hiveInfoDTO.getDatabaseName(), hiveInfoDTO.getTableName(),
                    columnSql, partitionSql, hiveInfoDTO.getFieldDelimiter(),
                    hiveInfoDTO.getFileType());
        }
        log.info("创表语句：{}", sql);
        try {

            getJdbcTemplate(datasourceId).execute(sql);
            successApiResult.setMsg(String.format("成功创建表%s!", hiveInfoDTO.getTableName()));
            log.info("create hive table: {}.{}", hiveInfoDTO.getDatabaseName(), hiveInfoDTO.getTableName());
            return successApiResult;
        } catch (Exception e) {
            log.error("execute create table error: ", e);
            final ReturnT<Object> failureApiResult = ReturnT.initFailure();
            failureApiResult.setMsg(String.format("创建表%s失败!", hiveInfoDTO.getTableName()));
            log.error("create hive table: {} error!", hiveInfoDTO.getTableName());
            failureApiResult.setContent(e.getMessage());
            return failureApiResult;
        }
    }

    @Override
    public ReturnT<Object> createDatabase(Long datasourceId, String databaseName) {
        final String sql = String.format("CREATE DATABASE IF NOT EXISTS %s", databaseName);
        final ReturnT<Object> successApiResult = ReturnT.initSuccess();
        try {
            getJdbcTemplate(datasourceId).execute(sql);
            successApiResult.setMsg(String.format("成功创建数据库%s!", databaseName));
            log.info("create hive database: {}!", databaseName);
            return successApiResult;
        } catch (Exception e) {
            log.error("execute create hive database error", e);
            final ReturnT<Object> failureApiResult = ReturnT.initFailure();
            failureApiResult.setMsg(String.format("创建数据库%s失败!", databaseName));
            failureApiResult.setContent(e.getMessage());
            return failureApiResult;
        }
    }

    @Override
    public ReturnT<Object> addPartition(Long datasourceId, HiveInfoDTO hiveInfoDTO) {
        List<HivePartition> partitionList = hiveInfoDTO.getPartitionList();
        StringBuilder sb = new StringBuilder();
        partitionList.forEach(partition -> sb.append(partition.getName()).
                append(Constants.EQUAL).
                append(Constants.SINGLE_QUOTE).append(partition.getValue()).append(Constants.SINGLE_QUOTE).
                append(Constants.SPLIT_COMMA));
        final String partitionInfoSql = sb.toString().substring(0, sb.toString().length() - 1);
        final String sql = String.format("ALTER TABLE `%s.%s` ADD PARTITION(%s)",
                hiveInfoDTO.getDatabaseName(), hiveInfoDTO.getTableName(), partitionInfoSql
        );
        final ReturnT<Object> successApiResult = ReturnT.initSuccess();
        try {
            getJdbcTemplate(datasourceId).execute(sql);
            successApiResult.setMsg(String.format("成功创建分区%s!", partitionInfoSql));
            log.info("create hive table partition: {}!", partitionInfoSql);
            return successApiResult;
        } catch (Exception e) {
            log.error("execute create hive table partition error", e);
            final ReturnT<Object> failureApiResult = ReturnT.initFailure();
            failureApiResult.setMsg(String.format("创建分区%s失败!", partitionInfoSql));
            failureApiResult.setContent(e.getMessage());
            return failureApiResult;
        }
    }

    @Override
    public ReturnT<Object> deleteTable(Long datasourceId, HiveInfoDTO hiveInfoDTO) {
        final String sql = String.format("DROP TABLE IF EXISTS %s.%s", hiveInfoDTO.getDatabaseName(), hiveInfoDTO.getTableName());
        try {
            getJdbcTemplate(datasourceId).execute(sql);
            log.info("delete hive table: {}.{}!", hiveInfoDTO.getDatabaseName(), hiveInfoDTO.getTableName());
            final ReturnT<Object> successApiResult = ReturnT.initSuccess();
            successApiResult.setMsg(String.format("成功删除表%s.%s!", hiveInfoDTO.getDatabaseName(), hiveInfoDTO.getTableName()));
            return successApiResult;
        } catch (Exception e) {
            log.error("execute delete hive table error", e);
            final ReturnT<Object> failureApiResult = ReturnT.initFailure();
            failureApiResult.setMsg(String.format("删除表%s%s%s失败!", hiveInfoDTO.getDatabaseName(), Constants.SPLIT_POINT, hiveInfoDTO.getTableName()));
            failureApiResult.setContent(e.getMessage());
            return failureApiResult;
        }
    }

    @Override
    public ReturnT<Object> deleteDatabase(Long datasourceId, String databaseName) {
        final String sql = String.format("DROP DATABASE IF EXISTS %s", databaseName);
        try {
            getJdbcTemplate(datasourceId).execute(sql);
            final ReturnT<Object> successApiResult = ReturnT.initSuccess();
            successApiResult.setMsg(String.format("成功删除数据库%s!", databaseName));
            log.info("delete hive database: {} !", databaseName);
            return successApiResult;
        } catch (Exception e) {
            log.error("execute delete hive database error", e);
            final ReturnT<Object> failureApiResult = ReturnT.initFailure();
            failureApiResult.setMsg(String.format("删除数据库%s失败!", databaseName));
            failureApiResult.setContent(e.getMessage());
            return failureApiResult;
        }
    }


}
