package com.fgl.cwp.persistence.db;

public final class Constants {	
	public static final String SUMTOTAL_DB = "sumtotal";
	public static final String CWP_DB = "cwp";
	public static final String SUMTOTAL_SELECTOR="select activity_pk as courseId, activityname as courseName, code as code from tbl_tmx_activity where active =1 and LevelVal = 0 and activity_pk >= 0";
	public static final String CWP_INSERTER="insert into sum_total_course(course_id,web_reportable,display_name,course_shortname,course_originalname) values(?,?,?,?,?)";
}
