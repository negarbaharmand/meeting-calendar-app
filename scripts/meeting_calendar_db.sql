CREATE SCHEMA IF NOT EXISTS MEETING_CALENDAR_DB;
USE MEETING_CALENDAR_DB;

CREATE TABLE USERS(
	USERNAME varchar(255) NOT NULL PRIMARY KEY,
	_PASSWORD VARCHAR(255) NOT NULL,
	EXPIRED tinyint DEFAULT FALSE,
    CREATE_DATE DATETIME default NOW()
);

CREATE TABLE MEETING_CALENDARS(
	ID INT NOT null auto_increment primary key,
	USERNAME varchar(255) not null,
    TITLE varchar(255) not null,
    create_date datetime default now(),
    foreign key (USERNAME) references USERS(USERNAME)
);

CREATE TABLE MEETINGS (
	ID INT NOT null auto_increment primary key,
	TITLE varchar(255) not null,
    START_TIME DATETIME NOT NULL,
    END_TIME DATETIME NOT NULL,
    _DESCRIPTION TEXT,
    CALENDAR_ID INT NOT NULL,
	create_date datetime default now(),
	foreign key (CALENDAR_ID) references MEETING_CALENDARS(ID)
);