-- Delete query to remove any exsiting courses with the same course_id query
delete from sum_total_course 
where course_id in (-403,743,818,925,926,927,928,929,930,931,737,733,792,766,749);

-- query to verify courses do not exist, should return 0 records
select * from sum_total_course
where course_id in (-403,743,818,925,926,927,928,929,930,931,737,733,792,766,749);


-- Insert statements for the 15 courses
insert into sum_total_course values(-403,1,'CA 202 CM','CA 202 CM','CA 202 Cash Management Assessment');
insert into sum_total_course values(743,1,'104 BK 06','104 BK 06','SA 104 Bike Camp 2006');
insert into sum_total_course values(818,1,'104 HKY 06','104 HKY 06','SA 104 Hockey 2006');
insert into sum_total_course values(925,1,'OW BCrt BT','OW BCrt BT','SA 104 Outerwear Brand Cert 06/07 - Burton');
insert into sum_total_course values(926,1,'OW BCrt CB','OW BCrt CB','SA 104 Outerwear Brand Cert 06/07 - Columbia');
insert into sum_total_course values(927,1,'OW BCrt FF','OW BCrt FF','SA 104 Outerwear Brand Cert 06/07 - Firefly');
insert into sum_total_course values(928,1,'OW BCrt HH','OW BCrt HH','SA 104 Outerwear Brand Cert 06/07 - Helly Hansen');
insert into sum_total_course values(929,1,'OW BCrt OR','OW BCrt OR','SA 104 Outerwear Brand Cert 06/07 - Orage');
insert into sum_total_course values(930,1,'OW BCrt RZ','OW BCrt RZ','SA 104 Outerwear Brand Cert 06/07 - Ripzone');
insert into sum_total_course values(931,1,'OW Bct TNF','OW Bct TNF','SA 104 Outerwear Brand Cert 06/07 - TNF');
insert into sum_total_course values(737,1,'104 PAD 06','104 PAD 06','SA 104 Paddle Sports 2006');
insert into sum_total_course values(792,1,'105 CLB 06','105 CLB 06','SA 105 Climbing 2006');
insert into sum_total_course values(766,1,'105 GLF 06','105 GLF 06','SA 105 Golf 2006');
insert into sum_total_course values(749,1,'105 ILS 06','105 ILS 06','SA 105 Inline 2006');
insert into sum_total_course values(733,1,'105 TEN 06','105 TEN 06','SA 105 Tennis 2006');

-- query to verify courses have been inserted, should return 15 records
select * from sum_total_course
where course_id in (-403,743,818,925,926,927,928,929,930,931,737,733,792,766,749);

