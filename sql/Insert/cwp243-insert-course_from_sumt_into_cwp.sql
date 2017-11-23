# Run the following script against the CWP production DB after testing it against QA

insert into sum_total_course(course_id, 
                             web_reportable, 
                             display_name, 
                             course_shortname, 
                             course_originalname 
                             ) 
            values(1090,1,'SA 102 - Technical Apparel Assessment','sa 102 tap','SA 102 - Technical Apparel Assessment'); 

