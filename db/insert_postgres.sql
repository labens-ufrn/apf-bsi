INSERT INTO apf.role VALUES (1,'ADMIN');
INSERT INTO apf.role VALUES (2,'USER');

INSERT INTO apf.attribution VALUES (1,'PROJECT MANAGER');
INSERT INTO apf.attribution VALUES (2,'PROJECT MEMBER');

/* password = 123456 */
INSERT INTO apf.user (active,email,last_name,name,password) VALUES (
1,'admin@apf.ceres.ufrn.br','APF Owner','Admin',
'$2a$10$RPOEaqsA96T624umYNHMOe3ucdphFSOL1XYmWZckqmQoiSBAzV9ba');

INSERT INTO apf.user_role(user_id, role_id) VALUES ((select user_id from apf.user where email = 'admin@apf.ceres.ufrn.br'), 1);
INSERT INTO apf.user_role(user_id, role_id) VALUES ((select user_id from apf.user where email = 'admin@apf.ceres.ufrn.br'), 2);

INSERT INTO apf.project (active,created_on,description,name,is_private) VALUES (
1,'2019-04-30','Analisador de Pontos de Função','APF Oficial', '0');

INSERT INTO apf.member (created_on,project_id,user_id,attrib_id) VALUES (
'2019-04-04',
(select project_id from apf.project where name = 'APF Oficial'),
(select user_id from apf.user where email = 'admin@apf.ceres.ufrn.br'),
1
);

INSERT INTO apf.data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI Project',
10,2,
'TYPE_ILF',
'2 RET: Project e Member. 10 DET: 6 (project) + 4 (member)',
(select project_id from apf.project where name = 'APF Oficial'),
null);

INSERT INTO apf.data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI User',
8,2,
'TYPE_ILF',
'2 RET: User e Role. 8 DET: 6 (user) + 2 (role)',
(select project_id from apf.project where name = 'APF Oficial'),
null);

INSERT INTO apf.data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI User Story',
4,1,
'TYPE_ILF',
'1 RET: User Story. 4 DET: 4 (us)',
(select project_id from apf.project where name = 'APF Oficial'),
null);

INSERT INTO apf.data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI Data Function',
8,1,
'TYPE_ILF',
'1 RET: Data Function. 8 DET: 8 (df)',
(select project_id from apf.project where name = 'APF Oficial'),
null);

INSERT INTO apf.data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI Transaction Function',
8,1,
'TYPE_ILF',
'1 RET: Transaction Function. 8 DET: 8 (tf)',
(select project_id from apf.project where name = 'APF Oficial'),
null);

INSERT INTO apf.data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI Attribution',
2,1,
'TYPE_ILF',
'1 RET: Attribution. 2 DET: 2 (attrib)',
(select project_id from apf.project where name = 'APF Oficial'),
null);

INSERT INTO apf.transaction_function (det, ftr, name, type, project_id)
VALUES (5, 1, 'Add Project', 'TYPE_EI', (select project_id from apf.project where name = 'APF Oficial'));
