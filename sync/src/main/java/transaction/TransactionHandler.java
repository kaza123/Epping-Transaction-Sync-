/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

import controller.MsSQLController;
import controller.MySQLController;
import db_connections.DataSourceWrapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import model.RBagDetails;
import model.TGreenLeavesWeigh;
import model.TGreenLeavesWeighDetail;

/**
 *
 * @author kasun
 */
public class TransactionHandler {

    private final DataSourceWrapper mssqlDataSourceWrapper;
    private final DataSourceWrapper mysqlDataSourceWrapper;

    public TransactionHandler() throws SQLException {
        this.mssqlDataSourceWrapper = new MsSQLController().getConnection();
        this.mysqlDataSourceWrapper = new MySQLController().getConnection();
    }

    public void saveaaa() {
//        try (java.sql.Connection con = createConnection())
//{
//    con.setAutoCommit(false);
//    try (Statement stm = con.createStatement())
//    {
//        stm.execute(someQuery); // causes SQLException
//    }
//    catch(SQLException ex)
//    {
//        con.rollback();
//        con.setAutoCommit(true);
//        throw ex;
//    }
//    con.commit();
//    con.setAutoCommit(true);
//}

    }

    public Integer save(TGreenLeavesWeigh detail) throws SQLException {
        Integer commit = 0;
        System.out.println("1");
        System.out.println("Weighting "+detail.getIndexNo()+" saving..");
        try (Connection sql = mssqlDataSourceWrapper.getConnection()) {
            try (Connection mysql = mysqlDataSourceWrapper.getConnection()) {
                sql.setAutoCommit(false);
                mysql.setAutoCommit(false);
                Integer saveWeight = MsSQLController.saveWeight(detail, sql);
                if (saveWeight > 0) {
                    System.out.println("2");
                    System.out.println("Weighting save! - "+detail.getIndexNo());
                    ArrayList<TGreenLeavesWeighDetail> detailList = MySQLController.getInstance().getWeighDetail(detail.getIndexNo(), mysql);
                    if (detailList.size() > 0) {
                        for (TGreenLeavesWeighDetail tGreenLeavesWeighDetail : detailList) {
                        System.out.println("3");
                            commit = 0;
                            Integer saveWeightDetail = MsSQLController.saveWeightDetail(tGreenLeavesWeighDetail, sql);
                            if (saveWeightDetail > 0) {
                                System.out.println("4");
                                ArrayList<RBagDetails> bagList = MySQLController.getInstance().getBagDetail(tGreenLeavesWeighDetail.getIndexNo(), mysql);
                                if (bagList.size() <= 0) {
                                    System.out.println("EMPTY BAG DETAIL !" + detail.getIndexNo());
                                    sql.rollback();
                                    sql.setAutoCommit(true);
                                    mysql.rollback();
                                    mysql.setAutoCommit(true);
                                    return -1;
                                } else {
                                System.out.println("5");
                                    System.out.println("Weight detail save "+tGreenLeavesWeighDetail.getIndexNo());
                                    for (RBagDetails rBagDetail : bagList) {
                                    System.out.println("6");
                                        Integer saveBag = MsSQLController.saveBagDetail(rBagDetail, sql);
                                    System.out.println("6.1");
                                        if (saveBag > 0) {
                                            //update
                                            System.out.println("Bag detail save "+rBagDetail.getIndexNo());
                                            Integer update = MySQLController.updateWeigh(detail.getIndexNo(), mysql);
                                            if (update <= 0) {
                                                System.out.println("7");
                                                System.out.println("STATUS UPDATE FAIL FOR " + detail.getIndexNo());
                                                sql.rollback();
                                                sql.setAutoCommit(true);
                                                mysql.rollback();
                                                mysql.setAutoCommit(true);
                                                return -1;
                                            } else {
                                                System.out.println("8");
                                                commit = 1;
                                            }

                                        } else {
                                            System.out.println("BAG SAVE FAIL ! "+detail.getIndexNo());
                                            sql.rollback();
                                            sql.setAutoCommit(true);
                                            mysql.rollback();
                                            mysql.setAutoCommit(true);
                                            return -1;
                                        }
                                    }
                                    System.out.println("Bag details Save !");
                                }

                            } else {
                                System.out.println("WEIGHT DETAIL SAVE FAIL !" + detail.getIndexNo());
                                sql.rollback();
                                sql.setAutoCommit(true);
                                mysql.rollback();
                                mysql.setAutoCommit(true);
                                return -1;
                            }

                        }
                        System.out.println("weighting details save ! ");
                        if (commit > 0) {
                            // commit transaction
                            mysql.commit();
                            sql.commit();
                            mysql.setAutoCommit(true);
                            sql.setAutoCommit(true);
                            return 1;
                        }
                    } else {
                        System.out.println("EMPTY DETAIL FOR " + detail.getIndexNo());
                        sql.rollback();
                        sql.setAutoCommit(true);
                        mysql.rollback();
                        mysql.setAutoCommit(true);
                        return -1;
                    }
                } else {
                    System.out.println("WEIGH SAVE FAIL ! " + detail.getIndexNo());
                    sql.rollback();
                    sql.setAutoCommit(true);
                    mysql.rollback();
                    mysql.setAutoCommit(true);
                    return -1;
                }

                return 0;
            }
        }
    }

}
