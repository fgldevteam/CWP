/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */
package com.fgl.cwp.model;

import net.sf.hibernate.HibernateException;

import com.fgl.cwp.common.HibernateTestCase;

/**
 * TODO Add Class Commments
 * @author dschultz
 */
public class ItemPriceChangeTest extends HibernateTestCase {
    
    /**
     * Test the fecth of a single ItemPriceChange record
     * @throws HibernateException
     */
    public void testFetch() throws HibernateException{
        ItemPriceChange ipc = (ItemPriceChange)session.createQuery("select ipc " +
                "from ItemPriceChange as ipc " +
                "where ipc.key.style.id = 986628")
            .setMaxResults(1)
            .uniqueResult();
        assertNotNull(ipc);
    }

}
