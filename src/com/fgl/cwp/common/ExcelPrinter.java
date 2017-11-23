package com.fgl.cwp.common;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.io.File;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import com.fgl.cwp.common.util.ExcelFormatter;
import com.fgl.cwp.model.SumTotalCourse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fgl.cwp.model.QueryParameters;
import com.fgl.cwp.common.util.SumMultiMap;
import com.fgl.cwp.model.sumtotal.ReportData;
import com.fgl.cwp.persistence.services.ElearningReportService.DepartmentInStoreData;

/**
 *  @author jmbarga
 *  Main class to generate the excel report
 *
 */

public class ExcelPrinter extends ReportPrinter {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(ExcelPrinter.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");		
	private WritableFont times12font = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, true);
	private WritableCellFormat times12format = new WritableCellFormat (times12font); 
	DecimalFormat df = new DecimalFormat("##0.0");
	private ExcelFormatter formatter= null;

	public ExcelPrinter(SumMultiMap data,QueryParameters parameters) {
		super(data,parameters);
		formatter = new ExcelFormatter();
	}

	/**
	 * 
	 * @param output_stream
	 * @throws IOException
	 * @throws ArrayIndexOutOfBoundsException
	 * Entry point for the generation of the excel report
	 */
	public void generateReport(File output_stream)throws IOException,ArrayIndexOutOfBoundsException {
		SumMultiMap data = this.getReportMultObject();
		WritableWorkbook  workbook = Workbook.createWorkbook(output_stream);

		Map map = data.getMap();
		Iterator it = map.keySet().iterator();
		List<Integer> keys = new ArrayList<Integer>();
		while(it.hasNext()){
			keys.add((Integer)it.next());
		}
		Collections.sort(keys);
		
		try {
			int sheetCount = 0;
			ArrayList<Collection> globalArr = new ArrayList<Collection>();
			Iterator iterValues = keys.iterator();
			while (iterValues.hasNext()) {
				Integer multiKey = (Integer) iterValues.next();
				WritableSheet sheet = workbook.createSheet("Store #"
						+ multiKey.intValue(), sheetCount++);
				Collection values = (Collection) map.get(multiKey);
				doStoreReport(sheet, multiKey, values);
				globalArr.add(values);
			}
			//output the summary tab only if we have more than one store
			if(globalArr.size() > 1 ) generateGlobalSummary(globalArr,workbook);			
			workbook.write();
			workbook.close();
		} catch(ParseException pe){
			log.error(pe);
		} catch (WriteException wre) {
			log.error(wre);
		}finally{
			

		}
	}

	/**
	 * 
	 * @param sheet
	 * @param storeId
	 * @param values
	 * @throws WriteException
	 * This method will handle one store at a time
	 */
	private void doStoreReport(WritableSheet sheet, Integer storeId,
			Collection values) throws WriteException {
		Object[] objects = values.toArray();		
		//ArrayList storeInfos = (ArrayList) objects[0];
		//List storeData = (List) storeInfos.get(1);		
		//if (storeData.isEmpty()) {
			//sheet.addCell(new Label(0, 0,"No Data on chosen course(s) in this store"));
		//} else {
			try {
				doPrint(objects, sheet, storeId);
			} catch (WriteException wre) {
				log.error(wre);
			} catch(ParseException pe){
				log.error(pe);
			}
		//}		
	}

	/**
	 * 
	 * @param collect
	 * @param sheet
	 * @param id
	 * @throws WriteException
	 * @throws ParseException
	 * The real work of formatting is done here
	 */
	private void doPrint(Object[] collect, WritableSheet sheet, Integer id)
			throws WriteException,ParseException {
		ArrayList storeInfos = (ArrayList)collect[0];
		SortedMap depData = (TreeMap) storeInfos.get(0);
		List storeList = (List)storeInfos.get(1);		
		
		SortedMap e_data = (TreeMap)storeInfos.get(2);
		QueryParameters params = getReportParameters();
		SumMultiMap combined_map = formatter.consolidateMap(depData,e_data);
		Map c_map = combined_map.getMap();		
		int total_employees=get_numberOfEmployees(c_map,storeList);	
		
		Collection store_collection = c_map.values();
		Object[] store_data = store_collection.toArray();
		
		Object[] entries = (Object[])c_map.entrySet().toArray();
		Arrays.sort(entries, new Comparator() {
		    private int compareTo(Object lhs, Object rhs) {
		        Map.Entry le = (Map.Entry)lhs;
		        Map.Entry re = (Map.Entry)rhs;
		        return ((Comparable)le.getKey()).compareTo((Comparable)re.getKey());
		    }		    
		    public int compare(Object lhs, Object rhs) {
		    	Map.Entry le = (Map.Entry)lhs;
		    	Map.Entry re = (Map.Entry)rhs;
		    	return ((Comparable)le.getKey()).compareTo((Comparable)re.getKey());
		    }
		});
				
		int row = formatter.generateHeader(sheet,total_employees,params,id);

		Map.Entry other_entry = null;
		for(int i=0;i<entries.length;i++){	
			String key = (String)((Map.Entry)entries[i]).getKey();
			if( key.equalsIgnoreCase("Other")){
				other_entry = (Map.Entry)entries[i];
			}else{
				row = formatter.depInStoreInfos(sheet,(Map.Entry)entries[i], row,params);
			}
		}		
		if( other_entry != null )
			row = formatter.depInStoreInfos(sheet,other_entry,row,params);
		
		row+=2;
		sheet.addCell(new Label(0,row,"Store Saturation : ",times12format));
		List courses = params.getCourses();
		for(int c=0;c<courses.size();c++){
			SumTotalCourse course = (SumTotalCourse)courses.get(c);
			ArrayList<DepartmentInStoreData> tmp_arr = new ArrayList<DepartmentInStoreData>();
			for(int n=0;n<store_data.length;n++){
				Collection dep_collection = (Collection)store_data[n];
				Object[] d_objs = dep_collection.toArray();
				for(int x=0;x<d_objs.length;x++){
					DepartmentInStoreData o_data = (DepartmentInStoreData)d_objs[x];
					if( (""+course.getCourseId()).equalsIgnoreCase(o_data.getCourseId())){
						if( o_data.getScore() != "" && o_data.getScore()!=null && Double.valueOf(o_data.getScore()) >= 80 )
							tmp_arr.add(o_data);
					}
				}
			}
			sheet.addCell(new Label(c+1,row,"" + df.format((Double.valueOf(tmp_arr.size()) / Double.valueOf(total_employees))* 100) +" %,  Completed : "+tmp_arr.size(),times12format));
		}		
	}	
	
	/**
	 * 
	 * @param summary
	 * @param book
	 * @throws WriteException
	 * @throws ParseException
	 * 
	 * Method for the summary tab of the report
	 */
	private void generateGlobalSummary(ArrayList summary,WritableWorkbook book)throws WriteException,ParseException{
		WritableSheet sheet = book.createSheet("Summary",summary.size());int row=0;
		WritableCellFormat times12format = new WritableCellFormat (times12font);
		int num_employees=0;
		QueryParameters params = getReportParameters();
		SumMultiMap allStores_map = new SumMultiMap(new TreeMap());		
		row = formatter.generateSummaryHeader(sheet,params);
		for(int n=0;n<summary.size();n++){
			Collection collection = (Collection)summary.get(n);
			Object[] objects = collection.toArray();				
			ArrayList storeInfos = (ArrayList)objects[0];
			SortedMap depData = (TreeMap) storeInfos.get(0);
			List store_data = (List)storeInfos.get(1);
			SortedMap e_data = (TreeMap)storeInfos.get(2);
			SumMultiMap combined_map = formatter.consolidateMap(depData,e_data);
			Map c_map = combined_map.getMap();	
			num_employees += get_numberOfEmployees(c_map, store_data);			
			Collection store_collection = c_map.values();
			Object[] store_objs = store_collection.toArray();		
			for(int d=0;d<store_objs.length;d++){
				Collection l_collection = (Collection)store_objs[d];
				Object[] l_objs = l_collection.toArray();
				for(int l=0;l<l_objs.length;l++){
					DepartmentInStoreData data = (DepartmentInStoreData)l_objs[l];
					if( data.getCourseId() != "")
						allStores_map.add(data.getCourseId(), data);
				}				
			}
		}
		Map all_sum_map = allStores_map.getMap();		
		List course_list =params.getCourses();
		Iterator all_iter = all_sum_map.entrySet().iterator();
		row+=2;
		sheet.addCell(new Label(0,row," Total Saturation : ",times12format));
		for(int s=0;s<course_list.size();s++){
			SumTotalCourse course = (SumTotalCourse)course_list.get(s);
			int auto_size = (""+df.format(0.0)  +" %, Completed : "+0).length() > course.getDisplayName().length() ? (""+df.format(0.0)  +" %, Completed : "+0).length() + 5 : course.getDisplayName().length() + 5;
			sheet.addCell(new Label(s+1,row,""+df.format(0.0)  +" %, Completed : "+0));	
			sheet.setColumnView(s+1, auto_size);
		}
		while(all_iter.hasNext()){
			Map.Entry allCourse_Entry = (Map.Entry)all_iter.next();
			String store_key = (String)allCourse_Entry.getKey();			
			Object[] coll_arr = ((Collection)allCourse_Entry.getValue()).toArray();
			for(int s=0;s<course_list.size();s++){
				SumTotalCourse course = (SumTotalCourse)course_list.get(s);				
				if( (""+course.getCourseId()).equalsIgnoreCase(store_key)){
					int auto_size = (df.format( (Double.valueOf(coll_arr.length) / Double.valueOf(num_employees)) * 100)+" %, Completed : "+coll_arr.length).length() + 5;
					sheet.addCell(new Label(s+1,row,""+df.format( num_employees == 0 ? 0.0 : (Double.valueOf(coll_arr.length) / Double.valueOf(num_employees)) * 100)+" %, Completed : "+coll_arr.length));
					if(course.getDisplayName().length() <= auto_size )
						sheet.setColumnView(s+1, auto_size);
				}								
			}
		}		
	}
	
	private int get_numberOfEmployees(Map c_map, List store_data){
		int num_employees=0;
		if ( !store_data.isEmpty() ) {
			ReportData sample = (ReportData)store_data.get(0);
			num_employees+=sample.getNumberEmp();
		}else{
			Iterator l_iter = c_map.entrySet().iterator();
			while(l_iter.hasNext()){			
				Map.Entry entry  = (Map.Entry)l_iter.next();
				Collection  l_col = ((Collection)entry.getValue());
				Object[] l_objs = l_col.toArray();
				num_employees += l_objs.length;
			}
		}
		return num_employees;
	}	
}
