create table IF NOT EXISTS LIST_OF_GENRES
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING not null,
    constraint LIST_OF_GENRES_PK
        primary key (GENRE_ID)
);
create table IF NOT EXISTS LIST_OF_MPAS
(
    MPA_ID   INTEGER not null,
    MPA_NAME CHARACTER VARYING not null,
    constraint LIST_OF_MPAS_PK
        primary key (MPA_ID)
);
create table IF NOT EXISTS LIST_OF_DIRECTORS
(
    DIRECTOR_ID   INTEGER auto_increment,
    DIRECTOR_NAME CHARACTER VARYING not null,
    constraint LIST_OF_DIRECTORS_PK
        primary key (DIRECTOR_ID)
);
create table IF NOT EXISTS FILMS
(
    FILM_ID      INTEGER auto_increment,
    FILM_NAME    CHARACTER VARYING      not null,
    DESCRIPTION  CHARACTER VARYING(200) not null,
    RELEASE_DATE DATE                   not null,
    DURATION     INTEGER                not null,
    MPA_ID       INTEGER,
    constraint FILMS_PK
        primary key (FILM_ID),
    constraint FILMS_LIST_OF_MPAS_MPA_ID_FK
        foreign key (MPA_ID) references LIST_OF_MPAS
);
create table IF NOT EXISTS GENRES
(
    FILM_ID  int not null,
    GENRE_ID int not null,
    constraint GENRES_PK
        primary key (FILM_ID, GENRE_ID),
    constraint GENRES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,
    constraint GENRES_LIST_OF_GENRES_GENRE_ID_FK
        foreign key (GENRE_ID) references LIST_OF_GENRES
);
create table IF NOT EXISTS DIRECTORS
(
    DIRECTOR_ID INTEGER not null,
    FILM_ID     INTEGER not null,
    constraint DIRECTORS_PK
        primary key (DIRECTOR_ID, FILM_ID, DIRECTOR_ID),
    constraint DIRECTORS_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,
    constraint DIRECTORS_LIST_OF_DIRECTORS_DIRECTOR_ID_FK
        foreign key (DIRECTOR_ID) references LIST_OF_DIRECTORS
);

create table IF NOT EXISTS USERS
(
    USER_ID      INTEGER auto_increment,
    EMAIL        CHARACTER VARYING      not null,
    LOGIN        CHARACTER VARYING      not null,
    USER_NAME    CHARACTER VARYING,
    BIRTHDAY     DATE                   not null,
    constraint USERS_PK
        primary key (USER_ID)
);
create table IF NOT EXISTS LIKES
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint LIKES_PK
        primary key (FILM_ID, USER_ID),
    constraint LIKES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,
    constraint LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
);
create table IF NOT EXISTS FRIENDSHIP
(
    USER1_ID  INTEGER           not null,
    USER2_ID  INTEGER           not null,
    STATUS    CHARACTER VARYING not null,
    constraint FRIENDSHIP_PK
        primary key (USER1_ID, USER2_ID),
    constraint FRIENDSHIP_USERS_USER1_ID_FK
        foreign key (USER1_ID) references USERS,
    constraint FRIENDSHIP_USERS_USER2_ID_FK_2
        foreign key (USER2_ID) references USERS
);

