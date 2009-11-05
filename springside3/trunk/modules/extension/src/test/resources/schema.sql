   	drop table ss_log if exists;

        
    create table SS_LOG (
    THREAD_NAME varchar(255),
    LOGGER_NAME varchar(255),
    TIMESTAMP timestamp,
    LEVEL varchar(20),
    MESSAGE varchar(255)
    );