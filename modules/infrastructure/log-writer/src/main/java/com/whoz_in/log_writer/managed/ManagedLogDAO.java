package com.whoz_in.log_writer.managed;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManagedLogDAO {

    private final JdbcTemplate jdbcTemplate;

    public void insertAll(Collection<ManagedLog> logs) {
        if(logs.size() > 0) {
            List<ManagedLog> logList = logs.stream().toList();

            String sql = new StringBuilder()
                    .append("INSERT INTO " + "managed_log")
                    .append("(managed_log_mac, created_date, updated_date, managed_log_device_name, managed_log_ip, managed_log_wifi_ssid)")
                    .append("values (?, now(), now(), ?, ?, ?)")
                    .append(" ON DUPLICATE KEY UPDATE updated_date = CURRENT_TIMESTAMP")
                    .toString();

            try{
                batchExecute(sql, logList);
            } catch(DuplicateKeyException e){
                System.err.println("[ERROR] Duplicate key: "+ e.getMessage());
            } catch (Exception e){
                System.err.println("[ERROR] Unexpected error: "+ e.getMessage());
            }
        }

    }

    private void batchExecute(String sql, List<ManagedLog> logList){
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    ManagedLog networkLog = logList.get(i);
                    preparedStatement.setString(1, networkLog.getMac());
                    preparedStatement.setString(2, networkLog.getDeviceName());
                    preparedStatement.setString(3, networkLog.getIp());
                    preparedStatement.setString(4, networkLog.getSsid());
            }

            @Override
            public int getBatchSize() {
                return logList.size();
            }
        });
    }

}