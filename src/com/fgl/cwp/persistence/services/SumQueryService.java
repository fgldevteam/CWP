package com.fgl.cwp.persistence.services;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.fgl.cwp.persistence.services.SumNativeQuery;

/**
 * 
 * @author jmbarga
 *
 */

public class SumQueryService implements Serializable {
	private static final long serialVersionUID = 1L;
    //singleton
    private static SumQueryService instance;
    private Map<String, SumNativeQuery> queries = new HashMap<String, SumNativeQuery>();
    
    private SumQueryService(){
        //private constructor
    }
    
    /**
     * Singleton access method
     * 
     * @return the single instance of SumQueryService
     */
    public static final SumQueryService getInstance(){
        if (instance == null){
            instance = new SumQueryService();
            try {
                Document doc = instance.openDocument();
                instance.readQueries(doc);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    
    /**
     * Reads the XML file and creates a DOM4J Document object
     * 
     * @return DOM4J Document
     * @throws DocumentException
     */
    private Document openDocument() throws DocumentException{
        SAXReader xmlReader = new SAXReader();
        InputStream xmlStream = SumQueryService.class.getResourceAsStream("SumNativeQueries.xml");
        return xmlReader.read( xmlStream );
    }
    
    /**
     * Reads all the &lt;query&gt; elements from the XML document, substitutes
     * all #{blockName} with the matching block values and puts them in a instance map.
     * 
     * @param doc
     */
    private final void readQueries(Document doc){
        Map blocks = this.readBlocks(doc);
        List results = doc.selectNodes("//queries/query");
        for (Iterator iter = results.iterator(); iter.hasNext();) {
            Element element = (Element) iter.next();
            String queryName = element.valueOf("@name");
            String query = replaceBlocks(element.getText(), blocks);
            queries.put(queryName, new SumNativeQuery(queryName, query) );
        }
    }
    
    /**
     * Reads all the &lt;block&gt; elements from the XML document and
     * puts them in a map keyed by name
     * 
     * @param doc
     * @return Map
     */
    private final Map readBlocks(Document doc){
        List results = doc.selectNodes("//queries/block");
        Map<String, String> blocks = new HashMap<String, String>();
        for (Iterator iter = results.iterator(); iter.hasNext();) {
            Element element = (Element) iter.next();
            String name = element.valueOf("@name");
            String text = element.getText();
            blocks.put(name, text.trim());
        }
        return blocks;
    }
    
    /**
     * Substitutes all text values that take the form #{blockName} with the
     * contents of the block element defined elsewhere in the block with a matching
     * name.  Any values of the form #{blockName} where the matching block cannot 
     * be found are left alone and are assumed to be replaced at runtime.
     * 
     * @param query
     * @param blocks
     * @return the final assembled query syntax
     */
    private String replaceBlocks(String query, Map blocks) {
        Pattern pattern = Pattern.compile("#\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(query);
        StringBuffer buffer = new StringBuffer();
        while(matcher.find()){
            String blockName = matcher.group(1);
            if (blocks.containsKey(blockName)){
                //replace the block
                String replacement = (String) replaceBlocks((String)blocks.get(blockName), blocks);
                matcher.appendReplacement(buffer, replacement);
            } else {
                //leave it alone
                matcher.appendReplacement(buffer, "#{" + blockName + "}");
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString().trim();
    }
    
    /**
     * @return Returns the queries.
     */
    public Map getQueries() {
        return queries;
    }
    
    /**
     * @param queries The queries to set.
     */
    public void setQueries(Map<String, SumNativeQuery> queries) {
        this.queries = queries;
    }
    
    /**
     * A convenience method for getting a named query from the service.
     * 
     * @param name
     * @return SumNativeQuery
     */
    public static SumNativeQuery getQuery(String name){
        return (SumNativeQuery) SumQueryService.getInstance().getQueries().get(name);
    }
}
