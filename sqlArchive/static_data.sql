
set identity_insert user_role on;
insert into user_role (id, description) values (1, 'Administrator');
insert into user_role (id, description) values (2, 'Document Manager');
insert into user_role (id, description) values (3, 'Notice Manager');
insert into user_role (id, description) values (4, 'Store Manager');
insert into user_role (id, description) values (5, 'Regional Manager');
insert into user_role (id, description) values (6, 'Read-Only User');
insert into user_role (id, description) values (7, 'User Manager');
insert into user_role (id, description) values (8, 'Pricing Issues Manager');
set identity_insert user_role off;

set identity_insert functionality on;
insert into functionality (id, description) values (1, 'Create Users');
insert into functionality (id, description) values (2, 'View Users');
insert into functionality (id, description) values (3, 'Edit Users');
insert into functionality (id, description) values (4, 'Delete Users');
insert into functionality (id, description) values (5, 'Create Documents');
insert into functionality (id, description) values (6, 'View Documents');
insert into functionality (id, description) values (7, 'Edit Documents');
insert into functionality (id, description) values (8, 'Delete Documents');
insert into functionality (id, description) values (9, 'Create Notices');
insert into functionality (id, description) values (10, 'View Notices');
insert into functionality (id, description) values (11, 'Edit Notices');
insert into functionality (id, description) values (12, 'Delete Notices');
insert into functionality (id, description) values (13, 'Create Reports');
insert into functionality (id, description) values (14, 'View Reports');
insert into functionality (id, description) values (15, 'View Products');
insert into functionality (id, description) values (16, 'View Pricing Issues');
insert into functionality (id, description) values (17, 'Edit Pricing Issues');
set identity_insert functionality off;


--Admininstrator has all privileges
insert into role_functionality (user_role_id, functionality_id) values (1, 1);
insert into role_functionality (user_role_id, functionality_id) values (1, 2);
insert into role_functionality (user_role_id, functionality_id) values (1, 3);
insert into role_functionality (user_role_id, functionality_id) values (1, 4);
insert into role_functionality (user_role_id, functionality_id) values (1, 5);
insert into role_functionality (user_role_id, functionality_id) values (1, 6);
insert into role_functionality (user_role_id, functionality_id) values (1, 7);
insert into role_functionality (user_role_id, functionality_id) values (1, 8);
insert into role_functionality (user_role_id, functionality_id) values (1, 9);
insert into role_functionality (user_role_id, functionality_id) values (1, 10);
insert into role_functionality (user_role_id, functionality_id) values (1, 11);
insert into role_functionality (user_role_id, functionality_id) values (1, 12);
insert into role_functionality (user_role_id, functionality_id) values (1, 13);
insert into role_functionality (user_role_id, functionality_id) values (1, 14);
insert into role_functionality (user_role_id, functionality_id) values (1, 15);
insert into role_functionality (user_role_id, functionality_id) values (1, 16);
insert into role_functionality (user_role_id, functionality_id) values (1, 17);


--Document Manager can manage documents
insert into role_functionality (user_role_id, functionality_id) values (2, 5);
insert into role_functionality (user_role_id, functionality_id) values (2, 6);
insert into role_functionality (user_role_id, functionality_id) values (2, 7);
insert into role_functionality (user_role_id, functionality_id) values (2, 8);

--Notice Manager can manage notices
insert into role_functionality (user_role_id, functionality_id) values (3, 9);
insert into role_functionality (user_role_id, functionality_id) values (3, 10);
insert into role_functionality (user_role_id, functionality_id) values (3, 11);
insert into role_functionality (user_role_id, functionality_id) values (3, 12);

--Store Manager
insert into role_functionality (user_role_id, functionality_id) values (4, 6);
insert into role_functionality (user_role_id, functionality_id) values (4, 10);
insert into role_functionality (user_role_id, functionality_id) values (4, 13);
insert into role_functionality (user_role_id, functionality_id) values (4, 14);
insert into role_functionality (user_role_id, functionality_id) values (4, 15);

--Regional General Manager
insert into role_functionality (user_role_id, functionality_id) values (5, 6);
insert into role_functionality (user_role_id, functionality_id) values (5, 10);
insert into role_functionality (user_role_id, functionality_id) values (5, 13);
insert into role_functionality (user_role_id, functionality_id) values (5, 14);
insert into role_functionality (user_role_id, functionality_id) values (5, 15);

--Read-Only User
insert into role_functionality (user_role_id, functionality_id) values (6, 6);
insert into role_functionality (user_role_id, functionality_id) values (6, 10);
insert into role_functionality (user_role_id, functionality_id) values (6, 15);

--User Manager
insert into role_functionality (user_role_id, functionality_id) values (7, 1);
insert into role_functionality (user_role_id, functionality_id) values (7, 2);
insert into role_functionality (user_role_id, functionality_id) values (7, 3);
insert into role_functionality (user_role_id, functionality_id) values (7, 4);

--Pricing Issues Manager
insert into role_functionality (user_role_id, functionality_id) values (8, 16);
insert into role_functionality (user_role_id, functionality_id) values (8, 17);
