
    create table attribution (
       attrib_id  serial not null,
        attrib_name varchar(255),
        primary key (attrib_id)
    );

    create table data_function (
       data_id  bigserial not null,
        det int8,
        description varchar(255),
        name varchar(255) not null,
        ret int8,
        type varchar(255) not null,
        project_id int8,
        user_story_id int8,
        primary key (data_id)
    );

    create table member (
       member_id  bigserial not null,
        created_on date not null,
        attrib_id int4 not null,
        project_id int8 not null,
        user_id int8 not null,
        primary key (member_id)
    );

    create table project (
       project_id  bigserial not null,
        active int4,
        created_on date not null,
        description varchar(255) not null,
        is_private boolean not null,
        name varchar(255),
        primary key (project_id)
    );

    create table role (
       role_id  serial not null,
        role_name varchar(255),
        primary key (role_id)
    );

    create table transaction_function (
       trans_id  bigserial not null,
        det int4,
        description varchar(255),
        ftr int4,
        name varchar(255) not null,
        type varchar(255) not null,
        project_id int8,
        user_story_id int8,
        primary key (trans_id)
    );

    create table user_role (
       user_id int8 not null,
        role_id int4 not null,
        primary key (user_id, role_id)
    );

    create table user_story (
       user_story_id  bigserial not null,
        description varchar(255),
        name varchar(255),
        project_id int8 not null,
        primary key (user_story_id)
    );

    create table users (
       user_id  bigserial not null,
        active int4,
        email varchar(255),
        last_name varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (user_id)
    );

    alter table attribution 
       add constraint UK_ifqougpv1g0br8g67hstq5kxo unique (attrib_name);

    alter table project 
       add constraint UK_3k75vvu7mevyvvb5may5lj8k7 unique (name);

    alter table role 
       add constraint UK_iubw515ff0ugtm28p8g3myt0h unique (role_name);

    alter table data_function 
       add constraint FKre6qmiqtq8jycft6y8vabky55 
       foreign key (project_id) 
       references project;

    alter table data_function 
       add constraint FKdlvokmcc2or8dq955n5yisrpo 
       foreign key (user_story_id) 
       references user_story;

    alter table member 
       add constraint FKiiq8mj6y0sssha2n9dekhhha 
       foreign key (attrib_id) 
       references attribution;

    alter table member 
       add constraint FKn4gkqnpww70e7vcyfh0lt2imn 
       foreign key (project_id) 
       references project;

    alter table member 
       add constraint FKe6yo8tn29so0kdd1mw4qk8tgh 
       foreign key (user_id) 
       references users;

    alter table transaction_function 
       add constraint FKiwg15idv7boxrkyjn8c961mll 
       foreign key (project_id) 
       references project;

    alter table transaction_function 
       add constraint FK2cxqcksbnyda0ata54ubjne98 
       foreign key (user_story_id) 
       references user_story;

    alter table user_role 
       add constraint FKa68196081fvovjhkek5m97n3y 
       foreign key (role_id) 
       references role;

    alter table user_role 
       add constraint FKj345gk1bovqvfame88rcx7yyx 
       foreign key (user_id) 
       references users;

    alter table user_story 
       add constraint FKwswc3uhx3p0yuscuxsqh2ywt 
       foreign key (project_id) 
       references project;

    create table attribution (
       attrib_id  serial not null,
        attrib_name varchar(255),
        primary key (attrib_id)
    );

    create table data_function (
       data_id  bigserial not null,
        det int8,
        description varchar(255),
        name varchar(255) not null,
        ret int8,
        type varchar(255) not null,
        project_id int8,
        user_story_id int8,
        primary key (data_id)
    );

    create table member (
       member_id  bigserial not null,
        created_on date not null,
        attrib_id int4 not null,
        project_id int8 not null,
        user_id int8 not null,
        primary key (member_id)
    );

    create table project (
       project_id  bigserial not null,
        active int4,
        created_on date not null,
        description varchar(255) not null,
        is_private boolean not null,
        name varchar(255),
        primary key (project_id)
    );

    create table role (
       role_id  serial not null,
        role_name varchar(255),
        primary key (role_id)
    );

    create table transaction_function (
       trans_id  bigserial not null,
        det int4,
        description varchar(255),
        ftr int4,
        name varchar(255) not null,
        type varchar(255) not null,
        project_id int8,
        user_story_id int8,
        primary key (trans_id)
    );

    create table user_role (
       user_id int8 not null,
        role_id int4 not null,
        primary key (user_id, role_id)
    );

    create table user_story (
       user_story_id  bigserial not null,
        description varchar(255),
        name varchar(255),
        project_id int8 not null,
        primary key (user_story_id)
    );

    create table users (
       user_id  bigserial not null,
        active int4,
        email varchar(255),
        last_name varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (user_id)
    );

    alter table attribution 
       add constraint UK_ifqougpv1g0br8g67hstq5kxo unique (attrib_name);

    alter table project 
       add constraint UK_3k75vvu7mevyvvb5may5lj8k7 unique (name);

    alter table role 
       add constraint UK_iubw515ff0ugtm28p8g3myt0h unique (role_name);

    alter table data_function 
       add constraint FKre6qmiqtq8jycft6y8vabky55 
       foreign key (project_id) 
       references project;

    alter table data_function 
       add constraint FKdlvokmcc2or8dq955n5yisrpo 
       foreign key (user_story_id) 
       references user_story;

    alter table member 
       add constraint FKiiq8mj6y0sssha2n9dekhhha 
       foreign key (attrib_id) 
       references attribution;

    alter table member 
       add constraint FKn4gkqnpww70e7vcyfh0lt2imn 
       foreign key (project_id) 
       references project;

    alter table member 
       add constraint FKe6yo8tn29so0kdd1mw4qk8tgh 
       foreign key (user_id) 
       references users;

    alter table transaction_function 
       add constraint FKiwg15idv7boxrkyjn8c961mll 
       foreign key (project_id) 
       references project;

    alter table transaction_function 
       add constraint FK2cxqcksbnyda0ata54ubjne98 
       foreign key (user_story_id) 
       references user_story;

    alter table user_role 
       add constraint FKa68196081fvovjhkek5m97n3y 
       foreign key (role_id) 
       references role;

    alter table user_role 
       add constraint FKj345gk1bovqvfame88rcx7yyx 
       foreign key (user_id) 
       references users;

    alter table user_story 
       add constraint FKwswc3uhx3p0yuscuxsqh2ywt 
       foreign key (project_id) 
       references project;
