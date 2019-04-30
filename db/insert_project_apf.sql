-- Contagem de Ponto de Função - Sistema APF

INSERT INTO user (active,email,last_name,name,password) VALUES (
1,'admin@apf.ceres.ufrn.br','APF Owner','Admin',
'$2a$10$tCwsAeknezkDzc70t2YnUO2pM3QNMi2SIPGBJEC0aDBGlcoTy5/9a');

INSERT INTO project (active,created_on,description,name,is_private) VALUES (
1,{d '2019-04-30'},'Analisador de Pontos de Função','APF Oficial',0);

INSERT INTO member (created_on,project_id,user_id) VALUES (
{d '2019-04-04'},
(select project_id from project where name = 'APF Oficial'),
(select user_id from user where email = 'admin@apf.ceres.ufrn.br')
);

INSERT INTO data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI Project',
10,2,
'TYPE_ILF',
'2 RET: Project e Member. 10 DET - 6 (project) + 4 (member)',
(select project_id from project where name = 'APF Oficial'),
null);

INSERT INTO data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI User',
8,2,
'TYPE_ILF',
'2 RET: User e Role. 8 DET - 6 (user) + 2 (role)',
(select project_id from project where name = 'APF Oficial'),
null);

INSERT INTO data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI User Story',
4,1,
'TYPE_ILF',
'1 RET: User Story. 4 DET - 4 (us)',
(select project_id from project where name = 'APF Oficial'),
null);

INSERT INTO data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI Data Function',
8,1,
'TYPE_ILF',
'1 RET: Data Function. 8 DET - 8 (df)',
(select project_id from project where name = 'APF Oficial'),
null);

INSERT INTO data_function (name,det,ret,type,description, project_id,user_story_id) VALUES (
'ALI Transaction Function',
8,1,
'TYPE_ILF',
'1 RET: Transaction Function. 8 DET - 8 (tf)',
(select project_id from project where name = 'APF Oficial'),
null);