
    create table apf.attribution (
       attrib_id  serial not null,
        attrib_name varchar(255),
        primary key (attrib_id)
    );

    create table apf.data_function (
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

    create table apf.member (
       member_id  bigserial not null,
        created_on date not null,
        attrib_id int4 not null,
        project_id int8 not null,
        user_id int8 not null,
        primary key (member_id)
    );

    create table apf.project (
       project_id  bigserial not null,
        active int4,
        created_on date not null,
        description varchar(255) not null,
        is_private boolean not null,
        name varchar(255),
        primary key (project_id)
    );

    create table apf.role (
       role_id  serial not null,
        role_name varchar(255),
        primary key (role_id)
    );

    create table apf.transaction_function (
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

    create table apf.user_role (
       user_id int8 not null,
        role_id int4 not null,
        primary key (user_id, role_id)
    );

    create table apf.user_story (
       user_story_id  bigserial not null,
        description varchar(255),
        name varchar(255),
        project_id int8 not null,
        primary key (user_story_id)
    );

    create table apf.users (
       user_id  bigserial not null,
        active int4,
        email varchar(255),
        last_name varchar(255),
        first_name varchar(255),
        password varchar(255),
        primary key (user_id)
    );

    alter table apf.attribution
       drop constraint UK_ifqougpv1g0br8g67hstq5kxo;

    alter table apf.attribution
       add constraint UK_ifqougpv1g0br8g67hstq5kxo unique (attrib_name);

    alter table apf.project
       drop constraint UK_3k75vvu7mevyvvb5may5lj8k7;

    alter table apf.project
       add constraint UK_3k75vvu7mevyvvb5may5lj8k7 unique (name);

    alter table apf.role
       drop constraint UK_iubw515ff0ugtm28p8g3myt0h;

    alter table apf.role
       add constraint UK_iubw515ff0ugtm28p8g3myt0h unique (role_name);

    alter table apf.data_function
       add constraint FKre6qmiqtq8jycft6y8vabky55
       foreign key (project_id)
       references apf.project;

    alter table apf.data_function
       add constraint FKdlvokmcc2or8dq955n5yisrpo
       foreign key (user_story_id)
       references apf.user_story;

    alter table apf.member
       add constraint FKiiq8mj6y0sssha2n9dekhhha
       foreign key (attrib_id)
       references apf.attribution;

    alter table apf.member
       add constraint FKn4gkqnpww70e7vcyfh0lt2imn
       foreign key (project_id)
       references apf.project;

    alter table apf.member
       add constraint FKe6yo8tn29so0kdd1mw4qk8tgh
       foreign key (user_id)
       references apf.users;

    alter table apf.transaction_function
       add constraint FKiwg15idv7boxrkyjn8c961mll
       foreign key (project_id)
       references apf.project;

    alter table apf.transaction_function
       add constraint FK2cxqcksbnyda0ata54ubjne98
       foreign key (user_story_id)
       references apf.user_story;

    alter table apf.user_role
       add constraint FKa68196081fvovjhkek5m97n3y
       foreign key (role_id)
       references apf.role;

    alter table apf.user_role
       add constraint FKj345gk1bovqvfame88rcx7yyx
       foreign key (user_id)
       references apf.users;

    alter table apf.user_story
       add constraint FKwswc3uhx3p0yuscuxsqh2ywt
       foreign key (project_id)
       references apf.project;

    create table apf.apf_users (
       user_id  bigserial not null,
        active int4,
        email varchar(255),
        last_name varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (user_id)
    );

    alter table apf.member
       add constraint FK9cgbjp4qijgbb1v615civeso2
       foreign key (user_id)
       references apf.apf_users;

    alter table apf.user_role
       add constraint FKa835ft3kmwwybwlus4ld9lqev
       foreign key (user_id)
       references apf.apf_users;

    alter table apf.users
       add column first_name varchar(255);

