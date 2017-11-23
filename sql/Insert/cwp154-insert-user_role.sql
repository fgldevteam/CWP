
# modify the user_role table

-- display the current roles ids and description of those roles
select * from cwp.user_role

-- insert into the table a new role id with the description of Elearning administrator
insert into cwp.user_role(id,description) values(9, 'Elearning Manager')

-- after previous statement run, should return 9 rows
select * from cwp.user_role


