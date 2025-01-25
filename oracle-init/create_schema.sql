CREATE USER C##TAXDB IDENTIFIED BY db1234;
GRANT CONNECT, RESOURCE TO C##TAXDB;
ALTER USER C##TAXDB QUOTA UNLIMITED ON USERS;

CONNECT C##TAXDB/db1234;

create table C##TAXDB.CUSTOMER
(
    ID                          NUMBER(19) generated as identity
        primary key,
    ADDRESS                     NVARCHAR2(255),
    BIRTH_OR_ESTABLISHMENT_DATE DATE,
    CUSTOMER_TYPE               NUMBER(3)
        check (customer_type between 0 and 1),
    IDENTITFICATION_NUMBER      NVARCHAR2(255)
        constraint UK2ACE38SSOJSDW41WXRPL6E57O
            unique,
    NAME                        NVARCHAR2(255),
    PHONE_NUMBER                NVARCHAR2(255),
    POSTAL_CODE                 NVARCHAR2(255)
);
/

create table C##TAXDB.USERS
(
    ID       NUMBER(19) generated as identity
        primary key,
    EMAIL    NVARCHAR2(255)
        constraint UK6DOTKOTT2KJSP8VW4D0M25FB7
            unique,
    NAME     NVARCHAR2(255),
    PASSWORD NVARCHAR2(255),
    ROLE     NUMBER(3)
        check (role between 0 and 1)
);
/

create table C##TAXDB.ACCOUNT
(
    ID                 NUMBER(19) generated as identity
        primary key,
    ACCOUNT_NUMBER     NVARCHAR2(255)
        constraint UK66GKCP94ENDMOTFWB8R4OCXM9
            unique,
    ACCOUNT_STATUS     NUMBER(3)
        check (account_status between 0 and 2),
    CREATED_DATE       TIMESTAMP(6) not null,
    LAST_MODIFIED_DATE TIMESTAMP(6) not null,
    REMAINING          NUMBER(19),
    VERSION            NUMBER(19),
    CUSTOMER_ID        NUMBER(19)
        constraint FKNNWPO0LFQ4XAI1RS6887SX02K
            references C##TAXDB.CUSTOMER
);
/

create or replace trigger C##TAXDB.TRG_ACCOUNT_GENERATE_CREATED_DATE_TIME
    before insert
    on C##TAXDB.ACCOUNT
    for each row
BEGIN
    :NEW.CREATED_DATE := SYSTIMESTAMP;
    :NEW.LAST_MODIFIED_DATE := SYSTIMESTAMP;
END;
/

create or replace trigger C##TAXDB.TRG_ACCOUNT_GENERATE_LAST_MODIFIED_DATE_TIME
    before update
    on C##TAXDB.ACCOUNT
    for each row
BEGIN
    :NEW.LAST_MODIFIED_DATE := SYSTIMESTAMP;
END;
/
COMMIT;

create table C##TAXDB.ACCOUNT_HISTORY
(
    ID                 NUMBER(19) generated as identity
        primary key,
    CREATED_DATE       TIMESTAMP(6),
    NEW_ACCOUNT_STATUS NUMBER(3)
        check (new_account_status between 0 and 2),
    NEW_CASH           NUMBER(19),
    OLD_ACCOUNT_STATUS NUMBER(3)
        check (old_account_status between 0 and 2),
    OLD_CASH           NUMBER(19),
    VERSION            NUMBER(19),
    ACCOUNT_ID         NUMBER(19)
        constraint FKFOMK97LCDRNDXOTSO6YCE14N2
            references C##TAXDB.ACCOUNT
                on delete set null,
    CREATED_BY_ID      NUMBER(19)
        constraint FKA8YTQD07YLGSEKNU76WFDCJS
            references C##TAXDB.USERS
);
/
create or replace trigger C##TAXDB.TRG_ACCOUNT_HISTORY_GENERATE_INFO
    before insert
    on C##TAXDB.ACCOUNT_HISTORY
    for each row
BEGIN
    :NEW.CREATED_DATE := SYSTIMESTAMP;
    :NEW.CREATED_BY_ID := 1;
end;
/
COMMIT;

create table C##TAXDB.TRANSACTION
(
    ID                         NUMBER(19) generated as identity
        primary key,
    CREATED_AT                 TIMESTAMP(6),
    DESTINATION_ACCOUNT_NUMBER NVARCHAR2(255),
    SOURCE_ACCOUNT_NUMBER      NVARCHAR2(255),
    STATUS_REASON              NVARCHAR2(255),
    TRACKING_CODE              NVARCHAR2(255),
    TRANSACTION_STATUS         NUMBER(3)
        check (transaction_status between 0 and 1),
    TRANSACTION_TYPE           NUMBER(3)
        check (transaction_type between 0 and 2),
    VALUE                      NUMBER(19)
);
/
create or replace trigger C##TAXDB.TRG_TRANSACTION_GENERATE_CREATED_AT
    before insert
    on C##TAXDB.TRANSACTION
    for each row
BEGIN
    :NEW.CREATED_AT := SYSTIMESTAMP;
end;
/
COMMIT;

INSERT INTO CUSTOMER (ADDRESS, BIRTH_OR_ESTABLISHMENT_DATE, CUSTOMER_TYPE, IDENTITFICATION_NUMBER, NAME,
                      PHONE_NUMBER, POSTAL_CODE)
VALUES (N'آدرس بانک', to_date('2010-01-01','YYYY/MM/DD'), 1, '0000000001', N'بانک', '09211111111', '0000000001');
/
COMMIT;

INSERT INTO ACCOUNT (ACCOUNT_NUMBER, ACCOUNT_STATUS, REMAINING, VERSION, CUSTOMER_ID, CREATED_DATE, LAST_MODIFIED_DATE)
VALUES ('00000000000001', 0, 0, 0, 1, systimestamp, systimestamp);
/
COMMIT;

INSERT INTO C##TAXDB.USERS (EMAIL, NAME, PASSWORD, ROLE)
VALUES ('admin@gmail.com', 'admin', '$2a$12$IaM8RxybQd2yz6dqkKV9FuUPSTHAZT8uUvBsGfYEn2SgmNRyx7aL.', 0);
/
COMMIT;

