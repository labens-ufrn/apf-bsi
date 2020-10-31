# Documento de Visão

Esse documento descreve os Requisitos Funcionais do projeto.

## Perfis dos Usuários

Temos os seguintes perfis/atores:

Perfil                                 | Descrição   |
---------                              | ----------- |
Administrador | Este usuário pode realizar qualquer função.
Gerente de Projeto | Este usuário pode cadastrar e gerenciar projetos e equipe. Pode gerenciar Funções de Dados, Funções de Transação e Lista de User Stories. |
Membro de Projeto | Este usuário pode acessa dados do projeto que é membro da equipe,e adicionar Funções de Dados, Funções de Transação e Lista de User Stories. |

## Lista de Requisitos Funcionais

Requisito                     | Descrição   | Ator |
---------                     | ----------- | ---------- |
RF001 - Adicionar Projeto     | Cadastra um projeto que representa um software em desenvolvimento. Um projeto tem os atributos id, nome, descrição, data de criação, se é privado ou publico, se está ativo, a equipe (conjunto de membros), lista de Funções de Dados, lista de Funções de Transação e a lista de User Stories. | Gerente, Administrador |
RF002 - Detalhar Projeto      | Detalha todos os dados de um projeto. | Gerente, Administrador |
RF003 - Editar Projeto        | Edita os seguintes dados de um projeto: nome, descrição, data de criação, se é privado ou publico,e se está ativo. | Gerente, Administrador |
RF004 - Excluir Projeto       | Exclui um projeto e apaga todos os seus dados. | Gerente, Administrador |

## Lista de Requisitos Não-Funcionais

Requisito                                 | Descrição   |
---------                                 | ----------- |
RNF001 - Deve ser acessível via navegador | Deve abrir perfeitamento no Firefox e no Chrome. |
RNF002 - Consultas deve ser eficiente | O sistema deve executar as consultas em milessegundos |
RNF003 - Log e histórico de acesso e ações | Deve manter um log de todos os acessos e das ações executadas pelo usuário |
