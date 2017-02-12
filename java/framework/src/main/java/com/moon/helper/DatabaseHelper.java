package com.moon.helper;

import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static com.moon.ClassUtil.LOGGER;

/**
 * Created by Paul on 2017/2/12.
 */
public final class DatabaseHelper {
    private static final Logger LOGGER= org.slf4j.LoggerFactory.getLogger(DatabaseHelper.class);
    public static Connection getConnection(){
        return null;
    }

    /**
     * 开启事务
     */
    public static void beginTransaction(){
        Connection conn=getConnection();
        if(Objects.nonNull(conn)){
            try{
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure",e);
                throw new RuntimeException(e);
            }finally {

            }
        }
    }

    public static void commitTransaction(){
        Connection conn=getConnection();
        if(Objects.nonNull(conn)){
            try{
                conn.commit();
                conn.close();
            }catch (SQLException e){
                LOGGER.error("commit transaction failure",e);
                throw new RuntimeException(e);
            }finally {

            }
        }
    }

    public static void rollbackTransaction(){
        Connection conn=getConnection();
        if(Objects.nonNull(conn)){
            try{
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction failure",e);
                throw new RuntimeException(e);
            }finally{

            }
        }
    }
}
