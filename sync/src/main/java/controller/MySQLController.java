/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db_connections.DataSourceWrapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import model.RBagDetails;
import model.Route;
import model.Supplier;
import model.TGreenLeavesWeigh;
import model.TGreenLeavesWeighDetail;
import org.apache.log4j.Logger;

/**
 *
 * @author 'Kasun Chamara'
 */
public class MySQLController {

    private final DataSourceWrapper mysqlDataSourceWrapper;
    private static final Logger LOGGER = Logger.getLogger(MySQLController.class);

     private static MySQLController instance;

    public static MySQLController getInstance() throws SQLException {
        if (instance == null) {
            instance = new MySQLController();
        }
        return instance;
    }
    public MySQLController() throws SQLException {
        Properties prop = new Properties();
        InputStream input = null;
        String dbName = null;
        String user = null;
        String pswd = null;
        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            // set value into variable
            dbName = prop.getProperty("setDataSourceName");
            user = prop.getProperty("setDataSourceUser");
            pswd = prop.getProperty("setDataSourcePswd");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.mysqlDataSourceWrapper = new DataSourceWrapper(dbName, user, pswd);
    }

    public DataSourceWrapper getConnection() {
        return mysqlDataSourceWrapper;
    }

    public Integer saveRoute(ArrayList<Route> list) throws SQLException {
        try (Connection connection = mysqlDataSourceWrapper.getConnection()) {
            String insertSql = "replace into m_route (rno,rname,branch) values(?,?,?)";
            PreparedStatement preparedStatementInsert = connection.prepareStatement(insertSql);
            int value = 0;
            for (int i = 0; i < list.size(); i++) {
                preparedStatementInsert.setString(1, String.valueOf(list.get(i).getRNO()));
                preparedStatementInsert.setString(2, String.valueOf(list.get(i).getRNAME()));
                preparedStatementInsert.setInt(3, 7);

                //finger print save
                int execute = preparedStatementInsert.executeUpdate();

                if (execute > 0) {
                    value++;
                }

            }
            if (value == list.size()) {
                return value;

            }
            return -1;
        }
    }

    public Integer saveSupplier(ArrayList<Supplier> list) throws SQLException {
        try (Connection connection = mysqlDataSourceWrapper.getConnection()) {
            String insertSql = "replace into m_suplier (RNO,SupNo,SupName,branch,route) values(?,?,?,?,?)";
            PreparedStatement preparedStatementInsert = connection.prepareStatement(insertSql);
            int value = 0;
            for (int i = 0; i < list.size(); i++) {
                Integer routeIndex = getRouteIndex(list.get(i).getRNO());
                preparedStatementInsert.setString(1, list.get(i).getRNO());
                preparedStatementInsert.setString(2, list.get(i).getSupNo());
                preparedStatementInsert.setString(3, list.get(i).getSupName());
                preparedStatementInsert.setInt(4, 7);
                preparedStatementInsert.setInt(5, routeIndex);

                //finger print save
                int execute = preparedStatementInsert.executeUpdate();

                if (execute > 0) {
                    value++;
                }
            }
            if (value == list.size()) {
                return value;
            }
            return -1;
        }
    }

    private Integer getRouteIndex(String rno) throws SQLException {
        try (Connection connection = mysqlDataSourceWrapper.getConnection()) {
            String insertSql = "select m_route.index_no from m_route where m_route.rno=? limit 1";
            PreparedStatement preparedStatementInsert = connection.prepareStatement(insertSql);

            preparedStatementInsert.setString(1, rno);

            //finger print save
            ResultSet rst = preparedStatementInsert.executeQuery();
            if (rst.next()) {
                return rst.getInt(1);
            }
            return null;
        }
    }

    public ArrayList<TGreenLeavesWeigh> getWeigh() throws SQLException {
        try (Connection connection = mysqlDataSourceWrapper.getConnection()) {
            String sql = "select t_green_leaves_weigh.index_no,t_green_leaves_weigh.boiled_leaves,\n"
                    + "t_green_leaves_weigh.branch, t_green_leaves_weigh.coarse_leaves,\n"
                    + "DATE_FORMAT(t_green_leaves_weigh.date,'%Y%m%d') as date,\n"
                    + "t_green_leaves_weigh.general_deduction,t_green_leaves_weigh.general_deduction_percent,\n"
                    + "t_green_leaves_weigh.green_leaves_type,m_login_details.col_no as collecter_no,\n"
                    + "t_green_leaves_weigh.net_weight, m_route.rno as route,\n"
                    + "t_green_leaves_weigh.serial, t_green_leaves_weigh.`status`,\n"
                    + "m_suplier.supno as supplier, t_green_leaves_weigh.tare_calculated,\n"
                    + "t_green_leaves_weigh.tare_deduction,t_green_leaves_weigh.temp_supplier_remark,\n"
                    + "t_green_leaves_weigh.total_bag_count,t_green_leaves_weigh.total_weight,\n"
                    + "t_green_leaves_weigh.water_deduction\n"
                    + "from t_green_leaves_weigh\n"
                    + "left join m_login_details on m_login_details.index_no=t_green_leaves_weigh.login_user\n"
                    + "left join m_route on m_route.index_no=t_green_leaves_weigh.route\n"
                    + "left join m_suplier on m_suplier.index_no=t_green_leaves_weigh.supplier\n"
                    + "where t_green_leaves_weigh.is_sync <> 1 or t_green_leaves_weigh.is_sync is null\n"
                    + "and t_green_leaves_weigh.login_user <> 22";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<TGreenLeavesWeigh> list = new ArrayList<>();
            while (rst.next()) {
                TGreenLeavesWeigh detail = new TGreenLeavesWeigh();
                detail.setIndexNo(rst.getInt(1));
                detail.setBoiledLeaves(rst.getBigDecimal(2));
                detail.setBranch(rst.getInt(3));
                detail.setCoarseLeaves(rst.getBigDecimal(4));
                detail.setDate(rst.getString(5));
                detail.setGeneralDeduction(rst.getBigDecimal(5));
                detail.setGeneralDeductionPercent(rst.getBigDecimal(7));
                detail.setGreenLeavesType(rst.getString(8));
                detail.setColNo(rst.getString(9));
                detail.setNetWeight(rst.getBigDecimal(10));
                detail.setRouteNo(rst.getString(11));
                detail.setSerial(rst.getString(12));
                detail.setStatus(rst.getString(13));
                detail.setSupNo(rst.getString(14));
                detail.setTareCalculated(rst.getBigDecimal(15));
                detail.setTareDeduction(rst.getBigDecimal(16));
                detail.setTempSupplierRemark(rst.getString(17));
                detail.setTotalBagCount(rst.getInt(18));
                detail.setTotalWeight(rst.getBigDecimal(19));
                detail.setWaterDeduction(rst.getBigDecimal(20));
                list.add(detail);
            }
            return list;
        }
    }

    public ArrayList<TGreenLeavesWeighDetail> getWeighDetail(Integer indexNo, Connection mysql) throws SQLException {
        try (Connection connection = mysqlDataSourceWrapper.getConnection()) {
            String sql = "select t_green_leaves_weigh_detail.index_no,\n"
                    + "t_green_leaves_weigh_detail.bag_count,\n"
                    + "t_green_leaves_weigh_detail.quantity,\n"
                    + "t_green_leaves_weigh_detail.t_green_leaves_weigh\n"
                    + "from t_green_leaves_weigh_detail \n"
                    + "where t_green_leaves_weigh_detail.t_green_leaves_weigh=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, indexNo);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<TGreenLeavesWeighDetail> list = new ArrayList<>();
            while (rst.next()) {
                TGreenLeavesWeighDetail detail = new TGreenLeavesWeighDetail();
                detail.setIndexNo(rst.getInt(1));
                detail.setBagCount(rst.getInt(2));
                detail.setQuantity(rst.getBigDecimal(3));
                detail.setTGreenLeavesWeigh(rst.getInt(4));
                list.add(detail);
            }
            return list;
        }
    }

    public ArrayList<RBagDetails> getBagDetail(Integer indexNo, Connection mysql) throws SQLException {
        try (Connection connection = mysqlDataSourceWrapper.getConnection()) {
            String sql = "select r_bag_details.index_no,\n"
                    + "r_bag_details.bag_no,\n"
                    + "r_bag_details.quantity,\n"
                    + "r_bag_details.t_green_leaves_weigh_detail\n"
                    + "from r_bag_details \n"
                    + "where r_bag_details.t_green_leaves_weigh_detail=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, indexNo);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<RBagDetails> list = new ArrayList<>();
            while (rst.next()) {
                RBagDetails detail = new RBagDetails();
                detail.setIndexNo(rst.getInt(1));
                detail.setBagNo(rst.getString(2));
                detail.setQuantity(rst.getBigDecimal(3));
                detail.setTGreenLeavesWeighDetail(rst.getInt(4));
                list.add(detail);
            }
            return list;
        }
    }

    public static Integer updateWeigh(Integer indexNo, Connection mysql) throws SQLException {
        String qyery = "UPDATE `t_green_leaves_weigh` SET `is_sync`='1' WHERE  `index_no`=?";
        PreparedStatement preparedStatementInsert = mysql.prepareStatement(qyery);
        preparedStatementInsert.setInt(1, indexNo);

        //finger print save
        Integer rst = preparedStatementInsert.executeUpdate();
        if (rst>0) {
            return 1;
        }
        return 0;
    }

}
