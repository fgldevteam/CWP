-- query to verify that record with course_id 988 exist
select * from sum_total_course where course_id = 988;

-- query to delete course record 988
delete from sum_total_course where course_id = 988;

-- query to verify that record with course_id 988 deleted, should return 0 records
select * from sum_total_course where course_id = 988;