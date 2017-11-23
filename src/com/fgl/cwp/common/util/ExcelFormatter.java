package com.fgl.cwp.common.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.fgl.cwp.common.util.SumMultiMap;
import com.fgl.cwp.model.QueryParameters;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.SumTotalCourse;
import com.fgl.cwp.persistence.services.ElearningReportService.DepartmentInStoreData;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

/**
 *  * @author jmbarga
 *  This is a helper class for the excel formatting with some helper functions
 *
 */

public class ExcelFormatter implements Serializable {
	private static final long serialVersionUID = 1L;	
	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");		
	private  WritableFont times12font = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, true);
	private  WritableCellFormat times12format = new WritableCellFormat (times12font); 
	DecimalFormat df = new DecimalFormat("##0.0");
	
	/**
	 * @param sheet
	 * @param storeInfos
	 * @param params
	 * @return
	 * @throws WriteException
	 * @throws ParseException
	 * Method to generate the header of each tab
	 */
	public int generateHeader(WritableSheet sheet,int numOfEmployees, QueryParameters params,Integer id)throws WriteException,ParseException{
		WritableCellFormat times12format = new WritableCellFormat (times12font);	
		String store_key = ""+id.intValue();
		GregorianCalendar start_date = params.getStartDate();
		GregorianCalendar end_date = params.getEndDate();
		List<SumTotalCourse> courses_list = params.getCourses();
		String now_date = sdf.format( new Date());
		String start_date_str = start_date != null ? sdf.format( start_date.getTime() ): now_date;
		String end_date_str = end_date !=null ? sdf.format( end_date.getTime() ) : now_date;
		
		int row = 2;	
		sheet.addCell(new Label(0,row, "Report Generated on : "+ now_date,times12format));
		if( start_date == null && end_date == null){
			sheet.addCell(new Label(0,++row,"E-learning Report till : ["+now_date+"]",times12format));
		}else if( start_date == null && end_date != null){
			sheet.addCell(new Label(0,++row,"E-learning Report till : ["+end_date_str+"]",times12format));
		}else if( start_date != null && end_date == null){
			sheet.addCell(new Label(0,++row,"E-learning Report from : ["+start_date_str+"] to ["+now_date+"]",times12format));
		}else {
			sheet.addCell(new Label(0,++row,"E-learninf Report from : ["+start_date_str+"] to ["+end_date_str+"]",times12format));
		}
		
		if( start_date == null &&  end_date == null )
			sheet.addCell(new Label(0,row,"No Dates specified",times12format));
		
		sheet.addCell(new Label(0, ++row, "Information for Store # "	+ store_key,times12format));
		row = row + 2;	
		sheet.addCell(new Label(0, ++row, "Total Number of Employees in Store ("+ store_key + ")  : " + numOfEmployees,times12format));
		sheet.setColumnView(0, ("Total Number of Employees in Store ("+ store_key + ")  : " +numOfEmployees).length());
		row++;
		sheet.addCell( new Label(0, ++row, "Course : ",times12format));
		sheet.addCell(new Label(0,row+1, "Employee Name",times12format));
		for(int n=0; n<courses_list.size();n++){		
			sheet.addCell(new Label(n+1, row, ((SumTotalCourse)courses_list.get(n)).getDisplayName(), times12format));
			sheet.addCell(new Label(n+1, row+1, " Score %       Date Taken", times12format));
			int auto_size = ((SumTotalCourse)courses_list.get(n)).getDisplayName().length() >= " Score %       Date Taken".length() ? ((SumTotalCourse)courses_list.get(n)).getDisplayName().length() + 5 : " Score %       Date Taken".length() + 5;
			sheet.setColumnView(n+1, auto_size);
		}
		return row + 3;
	}	
	
	/**
	 * @param sheet
	 * @param params
	 * @return
	 * @throws WriteException
	 * @throws ParseException
	 * Generates the header of the summary page
	 */
	public int generateSummaryHeader(WritableSheet sheet, QueryParameters params)throws WriteException,ParseException{
		List<SumTotalCourse> courses_list = params.getCourses();
		List stores = params.getStores();StringBuffer storeBuff = new StringBuffer();
		int row=2;
		for(int n=0;n<stores.size();n++){
			Store store = (Store)stores.get(n);
			storeBuff.append(store.getNumber())
			         .append(", ");
			
		}
		GregorianCalendar start_date = params.getStartDate();
		GregorianCalendar end_date = params.getEndDate();
		String now_date = sdf.format( new Date());
		String start_date_str = start_date != null ? sdf.format( start_date.getTime() ): now_date;
		String end_date_str = end_date !=null ? sdf.format( end_date.getTime() ) : now_date;		
		
		sheet.addCell(new Label(0,row++, "Report Generated on : "+ now_date,times12format));
		if( start_date == null && end_date == null){
			sheet.addCell(new Label(0,++row,"E-learning Report till : ["+now_date+"]",times12format));
		}else if( start_date == null && end_date != null){
			sheet.addCell(new Label(0,++row,"E-learning Report till : ["+end_date_str+"]",times12format));
		}else if( start_date != null && end_date == null){
			sheet.addCell(new Label(0,++row,"E-learning Report from : ["+start_date_str+"] to ["+now_date+"]",times12format));
		}else {
			sheet.addCell(new Label(0,++row,"E-learninf Report from : ["+start_date_str+"] to ["+end_date_str+"]",times12format));
		}	
		
		if( start_date == null &&  end_date == null )
			sheet.addCell(new Label(0,row,"No Dates specified",times12format));
		
		sheet.setColumnView(0, storeBuff.length());
		
		storeBuff.setLength(storeBuff.lastIndexOf(","));
		sheet.addCell(new Label(0,row++,"Summary for the selected stores ("+storeBuff+")",times12format));
		sheet.addCell(new Label(0,row,"Course:",times12format));
		sheet.setColumnView(0, ("Summary for the selected stores ("+storeBuff+")").length() >= 100 ? 100 : ("Summary for the selected stores ("+storeBuff+")").length());
		for(int n=0; n<courses_list.size();n++){
			sheet.addCell(new Label(n+1, row, ((SumTotalCourse)courses_list.get(n)).getDisplayName(), times12format));
			sheet.setColumnView(n+1,((SumTotalCourse)courses_list.get(n)).getDisplayName().length() + 5);
		}
		storeBuff = null;
		return row;
	}
	
	/**
	 * @param objs
	 * @return
	 * helper method to get the employees of a department in a store
	 */
	public static List<DepartmentInStoreData> getDepartEmployees(Object[] objs){			
		Map<String,DepartmentInStoreData> l_map = new TreeMap<String,DepartmentInStoreData>();
		for(int n=0;n<objs.length;n++){
			DepartmentInStoreData data = (DepartmentInStoreData)objs[n];		
			l_map.put(data.getEmpName(),data);
		}	
		List<DepartmentInStoreData> departList = new ArrayList<DepartmentInStoreData>();
		Iterator iter = l_map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();			
			DepartmentInStoreData data = (DepartmentInStoreData)l_map.get(entry.getKey());
			departList.add(data);
		}		
		return departList;
	}
	/**
	 * @param sheet
	 * @param dep_m
	 * @param row
	 * @param params
	 * @return
	 * @throws WriteException
	 * @throws ParseException
	 * Helper method to get the department infos in a store
	 */
	public int depInStoreInfos(WritableSheet sheet,Map.Entry dep_m, int row, QueryParameters params)throws WriteException,ParseException{
		Collection collection = (Collection)dep_m.getValue(); 	SumMultiMap course_map = new SumMultiMap(new TreeMap());	
		Object[] objs = collection.toArray();
		DepartmentInStoreData sample = ((DepartmentInStoreData)objs[0]);
		List<SumTotalCourse> courses_list = params.getCourses();
		List<DepartmentInStoreData> dep_employees = getDepartEmployees(objs);
		row+=2;		
			//print the employees alphabetically
		if( sample == null ) return row++;
		sheet.addCell(new Label(0, row++,"Department : "+sample.getDepartment()+" with : "+dep_employees.size(), times12format));row++;
		for(int n=0; n<dep_employees.size();n++){
			int e_row = row++;				
			DepartmentInStoreData empData = ((DepartmentInStoreData)dep_employees.get(n));				 
			sheet.addCell(new Label(0,e_row, empData.getEmpName()));
			for(int c=0;c<courses_list.size();c++){					
				SumTotalCourse course = (SumTotalCourse)courses_list.get(c);				
				for(int o=0;o<objs.length;o++){						
					DepartmentInStoreData o_data = (DepartmentInStoreData)objs[o];
					if(empData.getEmpId() != null && empData.getEmpId().equalsIgnoreCase(o_data.getEmpId()) ){
						if( o_data.getCourseId() != null && o_data.getCourseId().equalsIgnoreCase(""+course.getCourseId()) ){				
							//sheet.addCell(new Label(c+1, e_row, o_data.getScore() !=null && o_data.getScore() != "" ? df.format(Double.valueOf(o_data.getScore())) + (o_data.getScore().length() == 7 ? "          ":"        "): ""+ (o_data.getScore() !=null ? (o_data.getScore().length() == 7 ? "          ":"        ") +  o_data.getDateTaken() != null || o_data.getDateTaken() != "" ? sdf.format( sdf.parse(o_data.getDateTaken())) : "" : "        ")+ o_data.getDateTaken() != null || o_data.getDateTaken() != "" ? sdf.format( sdf.parse(o_data.getDateTaken())) : "" ));
							String date_string = "";
							if( o_data.getDateTaken() != null && o_data.getDateTaken() != "" ){
								date_string  = o_data.getScore().length() == 7 ? "            "+sdf.format( sdf.parse(o_data.getDateTaken()) ): "          "+sdf.format( sdf.parse(o_data.getDateTaken()) );
							}
							sheet.addCell(new Label(c+1, e_row, o_data.getScore() != null && o_data.getScore() != "" ? df.format(Double.valueOf(o_data.getScore())) + date_string : ""));
							course_map.add( o_data.getCourseName(), o_data);
						}							
					}	
				}
			}
		}
		sheet.addCell(new Label(0,row,"Dept Saturation : ",times12format));		
		for(int n=0;n<courses_list.size();n++){
			SumTotalCourse course = (SumTotalCourse)courses_list.get(n);
			ArrayList<DepartmentInStoreData> tmp_arr = new ArrayList<DepartmentInStoreData>();
			for(int m=0;m<objs.length;m++){
				DepartmentInStoreData o_data = (DepartmentInStoreData)objs[m];
				if( (""+course.getCourseId()).equalsIgnoreCase(o_data.getCourseId())){
					if( o_data.getScore() != "" && o_data.getScore() !=null && Double.valueOf(o_data.getScore()) >= 80 )
						tmp_arr.add(o_data);
				}
			}
			sheet.addCell(new Label(n+1,row,"" + df.format((Double.valueOf(tmp_arr.size()) / Double.valueOf(dep_employees.size()))* 100) +" % ,  Completed : "+tmp_arr.size(),times12format));
		}  		
		return row++;
	}
	
	/**
	 * @param dep_map
	 * @param e_map
	 * @return
	 * Method to help get the unique employees 
	 */
	public SumMultiMap consolidateMap(Map dep_map, Map e_map){
		SumMultiMap consolidated_map = new SumMultiMap(new TreeMap());
		Collection dep_values = dep_map.values();
		Collection e_values = e_map.values();				
		Map data_m = (Map)dep_values.toArray()[0];
		Iterator d_iter = data_m.entrySet().iterator();
		while(d_iter.hasNext()){
			Map.Entry entry = (Map.Entry)d_iter.next();			
			DepartmentInStoreData data = (DepartmentInStoreData)entry.getValue();			
			consolidated_map.add(entry.getKey(), data);
		}		
		Map e_m = (Map)e_values.toArray()[0];
		Iterator e_iter = e_m.entrySet().iterator();
		while(e_iter.hasNext()){
			Map.Entry entry = (Map.Entry)e_iter.next();			
			DepartmentInStoreData data = (DepartmentInStoreData)entry.getValue();
			consolidated_map.add(entry.getKey(), data);
		}	
		return consolidated_map;
	}
}
