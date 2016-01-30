---------------------------------
---      �ͻ�������                               ---
---------------------------------

CREATE SEQUENCE SEQ_MGR_CUSTOMER
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    NOMAXVALUE
    CACHE 20
    NOORDER
;

---------------------------------
---      �����ͻ���                               ---
---------------------------------

CREATE TABLE MGR_CUSTOMER(
    customerId            NUMBER(15, 0)    NOT NULL,
    firmId                VARCHAR2(16)     NOT NULL,
    name                  VARCHAR2(32)     NOT NULL,
    fullName              VARCHAR2(128),
    cardType              VARCHAR2(2)      NOT NULL,
    card                  VARCHAR2(32)     NOT NULL,
    type                  NUMBER(2, 0)     DEFAULT 0 NOT NULL,
    bankCode              VARCHAR2(2),
    bankAccount           VARCHAR2(32),
    address               VARCHAR2(256),
    contactMan            VARCHAR2(64),
    phone                 VARCHAR2(32),
    email                 VARCHAR2(32),
    postcode              VARCHAR2(16),
    status                CHAR(1)     DEFAULT 'N' NOT NULL,
    note                  VARCHAR2(1024),
    createTime            DATE             DEFAULT sysdate NOT NULL,
    modifyTime            DATE             DEFAULT sysdate NOT NULL,
    userId                VARCHAR2(32)     NOT NULL,
    CONSTRAINT PK_MGR_CUSTOMER PRIMARY KEY (customerId)
)
;

---------------------------------
---      ���Ա��ֶ�����                     ---
---------------------------------
COMMENT ON COLUMN MGR_CUSTOMER.customerId IS '�ͻ�����'
;
COMMENT ON COLUMN MGR_CUSTOMER.firmId IS '�ͻ�����������'
;
COMMENT ON COLUMN MGR_CUSTOMER.name IS '�ͻ���'
;
COMMENT ON COLUMN MGR_CUSTOMER.fullName IS '�ͻ�ȫ��'
;
COMMENT ON COLUMN MGR_CUSTOMER.cardType IS '֤�����ͣ�01 ���֤ 02 �������� 03 Ӫҵִ��'
;
COMMENT ON COLUMN MGR_CUSTOMER.card IS '֤������'
;
COMMENT ON COLUMN MGR_CUSTOMER.type IS '�ͻ����ͣ�0 ˫�߿ͻ� 1 Ψ��ͻ� 2 Ψ���ͻ�'
;
COMMENT ON COLUMN MGR_CUSTOMER.bankCode IS '���д��룺01 ���� 02 ���� 03 ��ͨ 04 ���� 05 ũ��'
;
COMMENT ON COLUMN MGR_CUSTOMER.bankAccount IS '�����˺�'
;
COMMENT ON COLUMN MGR_CUSTOMER.address IS '���ڵ�ַ'
;
COMMENT ON COLUMN MGR_CUSTOMER.contactMan IS '��ϵ��'
;
COMMENT ON COLUMN MGR_CUSTOMER.phone IS '�绰����'
;
COMMENT ON COLUMN MGR_CUSTOMER.email IS '����'
;
COMMENT ON COLUMN MGR_CUSTOMER.postcode IS '��������'
;
COMMENT ON COLUMN MGR_CUSTOMER.status IS '״̬��N ���� E ����'
;
COMMENT ON COLUMN MGR_CUSTOMER.note IS '��ע��Ϣ'
;
COMMENT ON COLUMN MGR_CUSTOMER.createTime IS '����ʱ��'
;
COMMENT ON COLUMN MGR_CUSTOMER.userID IS '����޸Ŀͻ�����Ա'
;

---------------------------------
---      ��Ӳ���Ȩ��                          ---
---------------------------------

delete from C_RIGHT where MODULEID=99;

insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99000000, 1000002, '�ͻ�����', null, null, null, 99, 99, 0, -1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99010000, 99000000, '��Ϣ����', '42_42.gif', null, null, 99, 1, 0, -1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99010100, 99010000, '�ͻ��б�', '29_29.gif', '/dem/customerplay/*', '/dem/customerplay/customerlist.action?sortColumns=order+by+customerId+desc', 99, 1, 0, 0, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99010101, 99010100, '�ͻ�����', null, '/dem/customerplay/customerdetails.action', null, 99, 1, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99010102, 99010100, '��ӿͻ�', null, '/dem/customerplay/add*', null, 99, 2, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99010103, 99010100, '�޸Ŀͻ�', null, '/dem/customerplay/update*', null, 99, 3, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99010104, 99010100, 'ɾ���ͻ�', null, '/dem/customerplay/delate*', null, 99, 4, 0, 1, 0);
commit;




-------------------------------------------------------�Ժ���demoʱ���õı�-----------------------------------------------------------------------

---------------------------------
---      �ͻ���2����                               ---
---------------------------------

CREATE SEQUENCE SEQ_DEMO_CUSTOMER
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    NOMAXVALUE
    CACHE 20
    NOORDER
;

---------------------------------
---      �����ͻ���                               ---
---------------------------------

CREATE TABLE DEMO_CUSTOMER(
    customerId            NUMBER(15, 0)    NOT NULL,
    firmId                VARCHAR2(16)     NOT NULL,
    name                  VARCHAR2(32)     NOT NULL,
    fullName              VARCHAR2(128),
    cardType              VARCHAR2(2)      NOT NULL,
    card                  VARCHAR2(32)     NOT NULL,
    type                  NUMBER(2, 0)     DEFAULT 0 NOT NULL,
    bankCode              VARCHAR2(2),
    bankAccount           VARCHAR2(32),
    address               VARCHAR2(256),
    contactMan            VARCHAR2(64),
    phone                 VARCHAR2(32),
    email                 VARCHAR2(32),
    postcode              VARCHAR2(16),
    status                CHAR(1)     DEFAULT 'N' NOT NULL,
    note                  VARCHAR2(1024),
    createTime            DATE             DEFAULT sysdate NOT NULL,
    modifyTime            DATE             DEFAULT sysdate NOT NULL,
    userId                VARCHAR2(32)     NOT NULL,
    CONSTRAINT PK_DEMO_CUSTOMER PRIMARY KEY (customerId)
)
;

---------------------------------
---      ���Ա��ֶ�����                     ---
---------------------------------
COMMENT ON COLUMN DEMO_CUSTOMER.customerId IS '�ͻ�����'
;
COMMENT ON COLUMN DEMO_CUSTOMER.firmId IS '�ͻ�����������'
;
COMMENT ON COLUMN DEMO_CUSTOMER.name IS '�ͻ���'
;
COMMENT ON COLUMN DEMO_CUSTOMER.fullName IS '�ͻ�ȫ��'
;
COMMENT ON COLUMN DEMO_CUSTOMER.cardType IS '֤�����ͣ�01 ���֤ 02 �������� 03 Ӫҵִ��'
;
COMMENT ON COLUMN DEMO_CUSTOMER.card IS '֤������'
;
COMMENT ON COLUMN DEMO_CUSTOMER.type IS '�ͻ����ͣ�0 ˫�߿ͻ� 1 Ψ��ͻ� 2 Ψ���ͻ�'
;
COMMENT ON COLUMN DEMO_CUSTOMER.bankCode IS '���д��룺01 ���� 02 ���� 03 ��ͨ 04 ���� 05 ũ��'
;
COMMENT ON COLUMN DEMO_CUSTOMER.bankAccount IS '�����˺�'
;
COMMENT ON COLUMN DEMO_CUSTOMER.address IS '���ڵ�ַ'
;
COMMENT ON COLUMN DEMO_CUSTOMER.contactMan IS '��ϵ��'
;
COMMENT ON COLUMN DEMO_CUSTOMER.phone IS '�绰����'
;
COMMENT ON COLUMN DEMO_CUSTOMER.email IS '����'
;
COMMENT ON COLUMN DEMO_CUSTOMER.postcode IS '��������'
;
COMMENT ON COLUMN DEMO_CUSTOMER.status IS '״̬��N ���� E ����'
;
COMMENT ON COLUMN DEMO_CUSTOMER.note IS '��ע��Ϣ'
;
COMMENT ON COLUMN DEMO_CUSTOMER.createTime IS '����ʱ��'
;
COMMENT ON COLUMN DEMO_CUSTOMER.userID IS '����޸Ŀͻ�����Ա'
;

---------------------------------
---      ��Ӳ���Ȩ��                          ---
---------------------------------

insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99100000, 1000002, '�ͻ�����', null, null, null, 99, 99, 0, -1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99120000, 99100000, '���Թ���', '42_42.gif', null, null, 99, 1, 0, -1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99120100, 99120000, '���Կͻ��б�', '29_29.gif', '/demo/customerplay/*', '/demo/customerplay/customerlist.action?sortColumns=order+by+customerId+desc', 99, 1, 0, 0, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99020101, 99120100, '���Կͻ�����', null, '/demo/customerplay/customerdetails.action', null, 99, 1, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99120102, 99120100, '��Ӳ��Կͻ�', null, '/demo/customerplay/add*', null, 99, 2, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99020103, 99120100, '�޸Ĳ��Կͻ�', null, '/demo/customerplay/update*', null, 99, 3, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (99120104, 99120100, 'ɾ�����Կͻ�', null, '/demo/customerplay/delate*', null, 99, 4, 0, 1, 0);
commit;
