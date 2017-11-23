/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Mar 28, 2005
 */
package com.fgl.cwp.persistence.services;

import java.io.File;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author bting
 */
public class FileSystemService {
    
    private static Log log = LogFactory.getLog(FileSystemService.class);
    private static FileSystemService instance;
    
    private FileSystemService() {
        // empty
    }
    
    /**
     * 
     * @return
     */
    public static FileSystemService getInstance() {
        if (instance == null) {
            instance = new FileSystemService();
        }
        return instance;
    }
    

    /**
     * Delete files older than given date
     * @param dirPath
     * @param date
     */
    public void deleteFilesOlderThanDate(String dirPath, Date date) {
        
        File dir = new File(dirPath);
        
        File[] files = dir.listFiles();
        
        for (int i=0; i<files.length; ++i) {
            File currFile = files[i];
            if (currFile.isFile()
                    && currFile.lastModified() < date.getTime()) {
                log.debug("Deleting file: " + files[i].getName());
                files[i].delete();
            }
        }
        
    }

}
