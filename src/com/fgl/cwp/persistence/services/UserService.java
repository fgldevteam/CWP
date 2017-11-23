package com.fgl.cwp.persistence.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;
import com.fgl.cwp.model.UserRole;
import com.fgl.cwp.persistence.SessionManager;

/**
 * @author Jessica Wong
 */
public class UserService {
    
    private static final UserService instance = new UserService();

    /**
     * @return
     */
    public static UserService getInstance() {
        return instance;
    }

    /**
     * Count the number of users with the same username.
     * @param username
     * @return
     * @throws Exception
     */
    public int countUserByUsername(String username) throws Exception {
        int count = 0;
        Session session = null;
        try {
            session = SessionManager.getSessionFactory().openSession();
            Integer result = (Integer)session.getNamedQuery("countUserByUsername")
                    .setParameter("username", username)
                    .uniqueResult();
            if (result != null) {
                count = result.intValue();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return count;
    }
    
    /**
     * @param username
     * @return
     * @throws Exception
     */
    public User getUserByUsername(String username) throws Exception {
        User user;
        Session session = SessionManager.getSessionFactory().openSession();
        try {
            user = (User) session.getNamedQuery("getUserByUsername")
                .setParameter("username", username)
                .uniqueResult();
            
            if( user != null){
	            Set roles = user.getRoles();
	            if (roles != null) {
	                for (Iterator iter = roles.iterator(); iter.hasNext();) {
	                    UserRole userRole = (UserRole)iter.next();
	                    Hibernate.initialize(userRole.getFunctionality());
	                }
	            }
            }
        } finally {
            session.close();
        }
        return user;
    }

    
    /**
     * Get all application users.
     * @return
     * @throws Exception
     */
    public List getUsers() throws Exception {
        List users = null;
        Session session = null;
        try {
            session = SessionManager.getSessionFactory().openSession();
            users = session.getNamedQuery("getUsers").list();
            
            LinkedHashSet<List> distinctUsers = new LinkedHashSet<List>(users);
            users = new ArrayList(distinctUsers);
            
        } finally {
            if (session != null) {
                session.close();
            }
        }
        if (users == null) {
            users = new ArrayList();
        }
        return users;
    }
    
    /**
     * Save a user to the database.
     * @param user
     * @return
     * @throws Exception
     */
    public boolean saveUser(User user) throws Exception {
        boolean success = false;
        Session session = null;
        Transaction tx = null;
        try {
            session = SessionManager.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(user);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return success;
    }
    
    /**
     * Delete a user from the database.
     * @param user
     * @throws Exception
     */
    public void deleteUser(User user) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = SessionManager.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Return a set of stores not managed by a given user.
     * @param user
     * @return
     * @throws Exception
     */
    public Set getUsersStoresNotManaged(User user) throws Exception {
        List stores = null;
        Session session = null;
        try {
            
            List<Integer> storeNumbers = new ArrayList<Integer>();
            for (Iterator iter = user.getStores().iterator(); iter.hasNext();) {
                storeNumbers.add(new Integer(((Store)iter.next()).getNumber()));
            }
            
            StringBuffer query = new StringBuffer();
            query.append("select store from Store as store");
            if (!storeNumbers.isEmpty()) {
                query.append(" where store.number not in (:storeList)");
            }
            
            session = SessionManager.getSessionFactory().openSession();
            Query q = session.createQuery(query.toString());
            if (!storeNumbers.isEmpty()) {
                q = q.setParameterList("storeList", storeNumbers);
            }
                
            stores = q.list();
            
        } finally {
            if (session != null) {
                session.close();
            }
        }
        if (stores == null) {
            stores = new ArrayList();
        }
        return new TreeSet(stores);
    }
    
    /**
     * Load all the users managed stores into session.
     * @param user
     * @throws Exception
     */
    public void loadUsersStores(User user) throws Exception {
        Session session = null;
        try {
            session = SessionManager.getSessionFactory().openSession();
            
            session.lock(user,LockMode.NONE);
            
            // Read all the stores into session
            session.getNamedQuery("getAllStores").list();            
            Hibernate.initialize(user.getStores());

        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}