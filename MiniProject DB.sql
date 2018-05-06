create table Aprivate (
	id varchar2(400) primary key,
    password varchar2(4000) not null,
	name varchar2(20) not null, 
	age number(3), 		
	gender varchar2(10)
    );
    
create table Agenre(
    genre_code number(2) primary key ,
    genre_name varchar2(100) unique
    );

create table Asinger(
    singer_code number(38) Primary key,
    singer varchar2(100)
    );
    create sequence singerseq;

create table Asong(
    title varchar2(40),
    singer_code number(38),
    play_count number(38) default 0,
    song_code number(38) default 0 primary key,
    genre_code number(2),
    lyric varchar2(4000),
    years number(5),
    constraint Asong_fk foreign key(genre_code) 
	references Agenre(genre_code),
    constraint Asong_fk2 foreign key(singer_code) 
	references Asinger(singer_code)
    );
    create sequence songseq;
    
create table Afavorite(
    id varchar2(400),
    song_code number(38),
    file_path varchar2(300),
    favorite_code varchar2(300) primary key,
    constraint Afavorite_fk foreign key(id) 
	references Aprivate(id) on delete cascade,
    constraint Afavorite_fk2 foreign key(song_code) 
	references Asong(song_code) on delete cascade
    );
    
insert into agenre values(1, '발라드');
insert into agenre values(2, '댄스');
insert into agenre values(3, '힙합');
insert into agenre values(4, '록');
insert into agenre values(5, '클래식');
insert into agenre values(6, '해외음악');
insert into agenre values(7, '기타');