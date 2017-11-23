package com.fgl.cwp.persistence.services;

import java.util.Collections;
import java.util.List;

import net.sf.hibernate.Session;

import com.fgl.cwp.model.Store;
import com.fgl.cwp.persistence.SessionManager;

/**
 * @author Jessica Wong
 */
public class StoreService {
    private static final StoreService instance = new StoreService();

    /**
     * @return
     */
    public static StoreService getInstance() {
        return instance;
    }

    /**
     * @param number
     * @return
     * @throws Exception
     */
    public Store getStoreById(Integer number) throws Exception {
        Store store = null;

        Session session = SessionManager.getSessionFactory().openSession();
        try {
            store = (Store) session.getNamedQuery("getStoreByID")
                .setParameter("number", number)
                .uniqueResult();
        } finally {
            session.close();
        }
        return store;
    }
    
    /**
     * Returns all the stores ordered by default ordering.
     * @return
     * @throws Exception
     */
    public List getAllStores() throws Exception {
        List stores = null;
        
        Session session = null;
        
        try {
            session = SessionManager.getSessionFactory().openSession();
            stores = session.getNamedQuery("getAllStores").list();
            if (stores != null) {
                Collections.sort(stores);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return stores;
    }
}