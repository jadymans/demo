CREATE DATABASE [demo-db]
GO

USE [demo-db];
GO

CREATE TABLE member (
    username VARCHAR(250) COLLATE Thai_CI_AS NOT NULL,
    password VARCHAR(250) COLLATE Thai_CI_AS NOT NULL,
    address VARCHAR(max) COLLATE Thai_CI_AS NULL,
    phone VARCHAR(10) COLLATE Thai_CI_AS NULL,
    salary int NOT NULL,
    reference_code VARCHAR(12) COLLATE Thai_CI_AS NOT NULL,
    member_type VARCHAR(50) COLLATE Thai_CI_AS NOT NULL,
    create_date datetime NULL,
    update_date datetime NULL,
    CONSTRAINT member_PK PRIMARY KEY (username)
);
GO