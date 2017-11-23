package com.fgl.cwp.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;
import com.fgl.cwp.persistence.services.CourseService;
import java.sql.SQLException;
import java.util.Map;
import net.sf.hibernate.HibernateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

public class PersistElearningCoursesAction extends DispatchAction {
	private static final Log log = LogFactory.getLog(PersistElearningCoursesAction.class);
	
	protected ActionForward unspecified(ActionMapping mapping,
			                            ActionForm form, 
			                            HttpServletRequest request,
			                            HttpServletResponse reponse) throws Exception {
		
		return super.unspecified(mapping, form, request, reponse);
	}

	public ActionForward saveCourses(ActionMapping mapping,
			                            ActionForm form, 
			                            HttpServletRequest request,
			                            HttpServletResponse response) throws Exception {
		
	   resetToken(request);
	   ActionContext.initialize(mapping, request, response,	getResources(request));	
	   if( !validateSession(request) ){
	       ActionContext.getActionContext().addError("error", "session.timeout.message");
	       saveContextMessages(request);
	       return mapping.findForward(Constants.SESSION_TIMEOUT);
	   }
	   	  
	   List courses_list=null, desired_names_list=null, reportable_courses=null, original_names=null, displayNames = null;
	   Map params = request.getParameterMap();	
	   Iterator iter = params.entrySet().iterator();
	   
	   while(iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
			String key = (String)entry.getKey();
			String[] values = (String[])params.get(key);
			if( key != null && key.equalsIgnoreCase("courses_ids")){
				courses_list = Arrays.asList(values);
			}else if( key != null && key.equalsIgnoreCase("desired_names")){
				desired_names_list = Arrays.asList(values);
			}else if( key != null && key.equalsIgnoreCase("reportable_courses")){
				reportable_courses = Arrays.asList(values);
			}else if( key !=null && key.equalsIgnoreCase("original_names")){
				original_names = Arrays.asList(values);
			} else if (key != null && key.equalsIgnoreCase("displayNames")) {
				displayNames = Arrays.asList(values);
			}
		}
	   
		String value = Constants.FAILURE;
		try{
			boolean saved =  CourseService.saveCourses(courses_list, original_names, displayNames, desired_names_list, reportable_courses);
			if( saved ) 	value = Constants.SUCCESS;
			
		}catch(HibernateException he){
			log.error("HibernateException : ",he);
			return mapping.findForward(Constants.FAILURE);
		}catch(SQLException sqle){
			log.error("SQLException : ", sqle);
			return mapping.findForward(Constants.FAILURE);
		}catch(Exception e){
			log.error("Exception : ",e);
			return mapping.findForward(Constants.FAILURE);
		}finally{
			CourseService.getAllCourses();
		}
		ActionContext.getActionContext().addMessage("status", "elearning.form.values.saved");
		saveContextMessages(request);
		return mapping.findForward(value);	  
	}

	private boolean validateSession(HttpServletRequest request){		
		ActionContext context = ActionContext.getActionContext();
		User user  = (User)context.getRequest().getSession().getAttribute(Constants.USER);
		Store store = (Store)context.getRequest().getSession().getAttribute(Constants.STORE);
		if( user != null || store !=null)
			return true;
		else return false;
	}
	
	private void saveContextMessages(HttpServletRequest request) {
		ActionContext context = ActionContext.getActionContext();
		if (context.getErrors() != null) {
			saveErrors(request, (ActionMessages) context.getErrors());
		}
		if (context.getMessages() != null) {
			saveMessages(request, context.getMessages());
		}
	}
}
