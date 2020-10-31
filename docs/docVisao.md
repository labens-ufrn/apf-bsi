# Documento de Visão

Esse documento descreve os Requisitos Funcionais do projeto.

## Perfis dos Usuários

Temos os seguintes perfis/atores:

Perfil                                 | Descrição   |
---------                              | ----------- |
Administrador | Este usuário pode realizar qualquer função.
Usuário | Usuário comum do sistema, que pode receber atribuições de Gerente e Membro de Projetos |
Gerente de Projeto | Atribuição para um usuário que pode cadastrar e gerenciar projetos e equipe. Pode gerenciar Funções de Dados, Funções de Transação e Lista de User Stories. |
Membro de Projeto | Atribuição para um usuário que pode acessa dados do projeto que é membro da equipe,e adicionar Funções de Dados, Funções de Transação e Lista de User Stories. |

## Lista de Requisitos Funcionais

Requisito                     | Descrição   | Ator |
---------                     | ----------- | ---------- |
RF001 - Cadastrar Usuário   | O usuário pode cadastra-se informando nome, sobrenome, e-mail e senha. O username é o e-mail, e o usuário criado não tem atribuição em nenhum projeto. A conta do usuário é ativada desde o cadastro. | Usuário, Administrador |
RF002 - Login de Usuário   | O usuário o e-mail e senha e é direcionado para a tela inicial com a lista de seus projetos. | Usuário, Administrador |
RF003 - Editar Usuário   | O usuário pode editar qualquer dos seus atributos: nome, sobrenome, e-mail e senha. | Usuário, Administrador |
RF004 - Excluir Usuário   | O usuário não pode excluir sua conta, apenas o administrador. | Usuário, Administrador |
RF005 - Adicionar Projeto     | Cadastra um projeto que representa um software em desenvolvimento. Um projeto tem os atributos id, nome, descrição, data de criação, se é privado ou publico, se está ativo, a equipe (conjunto de membros), lista de Funções de Dados, lista de Funções de Transação e a lista de User Stories. | Gerente, Administrador |
RF006 - Detalhar Projeto      | Detalha todos os dados de um projeto. | Gerente, Membro, Administrador |
RF007 - Consultar Projeto     | Lista todos os projetos de um determinado usuário. | Gerente, Membro, Administrador |
RF008 - Editar Projeto        | Edita os seguintes dados de um projeto: nome, descrição, data de criação, se é privado ou publico,e se está ativo. | Gerente, Administrador |
RF009 - Excluir Projeto       | Exclui um projeto e apaga todos os seus dados (funções de dados, funções de transição, lista de user stories). | Gerente, Administrador |
RF010 - Arquivar Projeto      | Arquivar um projeto coloca ele como inativo e não será mais apresentado na tela inicial do usuário. O usuário poderá listar os projetos arquivados. | Gerente, Administrador |

## Lista de Requisitos Não-Funcionais

Requisito                                 | Descrição   |
---------                                 | ----------- |
RNF001 - Deve ser acessível via navegador | Deve abrir perfeitamento no Firefox e no Chrome. |
RNF002 - Consultas deve ser eficiente | O sistema deve executar as consultas em milessegundos |
RNF003 - Log e histórico de acesso e ações | Deve manter um log de todos os acessos e das ações executadas pelo usuário |
