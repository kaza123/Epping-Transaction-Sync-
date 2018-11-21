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
public class MsSQLController {

    private final DataSourceWrapper mssqlDataSourceWrapper;
    private static final Logger LOGGER = Logger.getLogger(MsSQLController.class);

    public MsSQLController() throws SQLException {
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
            dbName = prop.getProperty("getDataSourceName");
            user = prop.getProperty("getDataSourceUser");
            pswd = prop.getProperty("getDataSourcePswd");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.mssqlDataSourceWrapper = new DataSourceWrapper(dbName, user, pswd);
    }

    public DataSourceWrapper getConnection() {
        return mssqlDataSourceWrapper;
    }

    public ArrayList<Route> getRouteList() throws SQLException {
        LOGGER.debug("reading route data");
        try (Connection connection = mssqlDataSourceWrapper.getConnection()) {
            String sql = "select RNO,RNAME \n"
                    + "from ROOTS where isSync != 1 ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<Route> list = new ArrayList<>();
            while (rst.next()) {
                Route route = new Route();
                route.setRNO(rst.getString(1));
                route.setRNAME(rst.getString(2));
                list.add(route);
            }
            LOGGER.debug(list.size() + " route data found");
            return list;
        }
    }

    public static Integer saveWeight(TGreenLeavesWeigh detail, Connection connection) throws SQLException {
        String sql = "INSERT INTO t_green_leaves_weigh (index_no,boiled_leaves, branch, coarse_leaves, rdate, \n"
                + "general_deduction, general_deduction_percent, green_leaves_type, col_no,\n"
                + " net_weight, route, r_serial, r_status, supplier, tare_calculated, tare_deduction, \n"
                + "temp_supplier_remark, total_bag_count, total_weight, water_deduction) \n"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, detail.getIndexNo());
        preparedStatement.setBigDecimal(2, detail.getBoiledLeaves());
        preparedStatement.setInt(3, detail.getBranch());
        preparedStatement.setBigDecimal(4, detail.getCoarseLeaves());
        preparedStatement.setString(5, detail.getDate());
        preparedStatement.setBigDecimal(6, detail.getGeneralDeduction());
        preparedStatement.setBigDecimal(7, detail.getGeneralDeductionPercent());
        preparedStatement.setString(8, detail.getGreenLeavesType());
        preparedStatement.setInt(9, Integer.valueOf(detail.getColNo()));
        preparedStatement.setBigDecimal(10, detail.getNetWeight());
        preparedStatement.setString(11, detail.getRouteNo());
        preparedStatement.setString(12, "-");
        preparedStatement.setString(13, detail.getStatus());
        preparedStatement.setInt(14, Integer.valueOf(detail.getSupNo()));
        preparedStatement.setBigDecimal(15, detail.getTareCalculated());
        preparedStatement.setBigDecimal(16, detail.getTareDeduction());
        preparedStatement.setString(17, detail.getTempSupplierRemark());
        preparedStatement.setInt(18, detail.getTotalBagCount());
        preparedStatement.setBigDecimal(19, detail.getTotalWeight());
        preparedStatement.setBigDecimal(20, detail.getWaterDeduction());

        return preparedStatement.executeUpdate();
    }

    public Integer update(ArrayList<Route> routeList) throws SQLException {
        int val = 0;
        LOGGER.debug("starting update !");
        for (Route route : routeList) {
            try (Connection connection = mssqlDataSourceWrapper.getConnection()) {
                String sql = "update ROOTS set isSync=1 where RNO=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, route.getRNO());
                Integer re = preparedStatement.executeUpdate();
                if (re > 0) {
                    val++;
                }

            }
        }
        if (routeList.size() == val) {
            LOGGER.debug("update success !");
            return val;
        }
        return 0;

    }

    public ArrayList<Supplier> getSupplierList() throws SQLException {
        LOGGER.debug("reading route data");
        try (Connection connection = mssqlDataSourceWrapper.getConnection()) {
            String sql = "select RNO,SupNo,supName \n"
                    + "from GLSupList where isSync != 1 ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<Supplier> list = new ArrayList<>();
            while (rst.next()) {
                Supplier supplier = new Supplier();
                supplier.setRNO(rst.getString(1));
                supplier.setSupNo(rst.getString(2));
                supplier.setSupName(rst.getString(3));
                list.add(supplier);
            }
            LOGGER.debug(list.size() + " route data found");
            return list;
        }
    }

    public Integer updateSupplier(ArrayList<Supplier> supList) throws SQLException {
        int val = 0;
        LOGGER.debug("starting update !");
        for (Supplier supp : supList) {
            try (Connection connection = mssqlDataSourceWrapper.getConnection()) {
                String sql = "update GLSupList set isSync=1 where SupNo=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, supp.getSupNo());
                Integer re = preparedStatement.executeUpdate();
                if (re > 0) {
                    val++;
                }

            }
        }
        if (supList.size() == val) {
            LOGGER.debug("update success !");
            return val;
        }
        return 0;
    }

    public static Integer saveWeightDetail(TGreenLeavesWeighDetail detail, Connection sql) throws SQLException {
        String query = "INSERT INTO t_green_leaves_weigh_detail (index_no,bag_count,quantity,t_green_leaves_weigh) \n"
                + "VALUES (?,?,?,?);";
        PreparedStatement preparedStatement = sql.prepareStatement(query);
        preparedStatement.setInt(1, detail.getIndexNo());
        preparedStatement.setInt(2, detail.getBagCount());
        preparedStatement.setBigDecimal(3, detail.getQuantity());
        preparedStatement.setInt(4, detail.getTGreenLeavesWeigh());

        int executeUpdate = preparedStatement.executeUpdate();
        if (executeUpdate > 0) {
            return 1;
        }
        return 0;

    }
    
    public static Integer saveBagDetail(RBagDetails detail, Connection sql) throws SQLException {
        System.out.println("a");
        String query = "INSERT INTO r_bag_details (index_no,bag_no, \n"
                + "quantity, t_green_leaves_weigh_detail )\n"
                + "VALUES (?,?,?,?);";
        PreparedStatement preparedStatement = sql.prepareStatement(query);
        preparedStatement.setInt(1, detail.getIndexNo());
       
        preparedStatement.setString(2, detail.getBagNo());
        preparedStatement.setBigDecimal(3, detail.getQuantity());
        preparedStatement.setInt(4, detail.getTGreenLeavesWeighDetail());
        System.out.println("saveBagDetail func");
        return preparedStatement.executeUpdate();
    }

}
