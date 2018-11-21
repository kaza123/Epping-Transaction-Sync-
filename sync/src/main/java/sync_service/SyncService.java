/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync_service;

import controller.MsSQLController;
import controller.MySQLController;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import model.Route;
import model.Supplier;
import model.TGreenLeavesWeigh;
import org.apache.log4j.Logger;
import transaction.TransactionHandler;

/**
 *
 * @author 'Kasun Chamara'
 */
public class SyncService {

    private ScheduledExecutorService scheduledExecutorService;

    private final MySQLController mySQLController;
    private final MsSQLController msSQLController;

    private static SyncService instance;
    private static final Logger LOGGER = Logger.getLogger(SyncService.class);

    public static SyncService getInstance() throws SQLException {
        if (instance == null) {
            instance = new SyncService();
        }

        return instance;
    }

    private SyncService()throws SQLException{
        this.mySQLController = new MySQLController();
        this.msSQLController = new MsSQLController();
    }

    public void start() throws ConcurrentModificationException {
        if (scheduledExecutorService != null) {
            throw new ConcurrentModificationException("Sync process already running");
        }

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(getSyncRunnable(), 0, 2, TimeUnit.MINUTES);
        LOGGER.info("Synchronization service started successfully");
    }

    public void stop() throws ConcurrentModificationException {
        if (scheduledExecutorService == null) {
            throw new ConcurrentModificationException("Sync process not started");
        }

        scheduledExecutorService.shutdown();
        scheduledExecutorService = null;
        LOGGER.info("Synchronization service stopped successfully");
    }

    private Runnable getSyncRunnable() {
        Runnable syncRunnable = () -> {
            try {
                LOGGER.info("START SYNCHRONIZATION !");

                //route
                ArrayList<Route> routeList = msSQLController.getRouteList();
                if (routeList.size() > 0) {
                    Integer saveIndex = mySQLController.saveRoute(routeList);
                    if (saveIndex > 0) {
                        String dateTime = new SimpleDateFormat("yyyy-MM-dd  hh-mm-ss a").format(new Date());
                        Integer update= msSQLController.update(routeList);
                        if (update<=0) {
                            LOGGER.info("ROUTE SYNCHRONIZATION FAILED @" + dateTime);
                        }else{
                            LOGGER.info("ROUTE SUCCESSFULLY SYNCHRONIZED @" + dateTime);
                        }
                    }
                } else {
                }
                
                //supplier
                ArrayList<Supplier> supList = msSQLController.getSupplierList();
                if (supList.size() > 0) {
                    Integer saveIndex = mySQLController.saveSupplier(supList);
                    if (saveIndex > 0) {
                        String dateTime = new SimpleDateFormat("yyyy-MM-dd  hh-mm-ss a").format(new Date());
                        LOGGER.info(supList.size() + "  SUPPLIER SYNCING . . ." + dateTime);
                        Integer update= msSQLController.updateSupplier(supList);
                        if (update<=0) {
                            LOGGER.info("SUPPLIER SYNCHRONIZATION FAILED @" + dateTime);
                        }else{
                            LOGGER.info("SUPPLIER SUCCESSFULLY SYNCHRONIZED @" + dateTime);
                        }
                    }
                } else {
                }
//                t_green_leaves_weigh
                  syncGLweighing();
                
                
            } catch (SQLException ex) {
                LOGGER.fatal("Synchronization failed", ex);
            }
        };
        return syncRunnable;
    }

    public void syncGLweighing() throws SQLException {
        ArrayList<TGreenLeavesWeigh> list = mySQLController.getWeigh();
        if (list.size()<=0) {
            System.out.println("EMPTY WEIGHT TO INTEGRATE..");
        }
        for (TGreenLeavesWeigh detail : list) {
            Integer save=new TransactionHandler().save(detail);
            if (save<0) {
                LOGGER.info("SYNCHRONIZATION SUCCESS !");
            }else{
                LOGGER.info("SYNCHRONIZATION FAILED ! TRANSACTION ROLLBACK ! ");
            }
            
        }
    }
}
