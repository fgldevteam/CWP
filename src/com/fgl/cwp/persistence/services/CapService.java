package com.fgl.cwp.persistence.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import com.fgl.cwp.model.Cap;
import com.fgl.cwp.persistence.SessionManager;

/**
 * 
 * @author gdelve
 *
 */

public class CapService {

	private static final CapService instance = new CapService();
	
	public static CapService getInstance() {
		return instance;
	}
	
	public List<Cap> getAllCaps() throws Exception {
		Session session = null;
		List<Cap> caps = new ArrayList<Cap>();
		try {
			session = SessionManager.getSessionFactory().openSession();
			caps = session.getNamedQuery("getAllCaps").list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return caps;
	}
	
	public List<Cap> getCapsById(Set<Integer> capIds) throws Exception{
		Session session = null;
		List<Cap> caps = new ArrayList<Cap>();
		try {
			session = SessionManager.getSessionFactory().openSession();
			Query query = session.getNamedQuery("getCapsById");
			query.setParameterList("capIds", capIds);
			caps = query.list();
			
		} finally {
			if (session != null) { 
				session.close();
			}
		}
		return caps;
	}

}
