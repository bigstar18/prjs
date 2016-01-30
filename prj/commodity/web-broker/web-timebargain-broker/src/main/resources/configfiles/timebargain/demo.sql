---------------------------------
---      客户表序列                               ---
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
---      创建客户表                               ---
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
---      测试表字段描述                     ---
---------------------------------
COMMENT ON COLUMN MGR_CUSTOMER.customerId IS '客户表编号'
;
COMMENT ON COLUMN MGR_CUSTOMER.firmId IS '客户所属交易商'
;
COMMENT ON COLUMN MGR_CUSTOMER.name IS '客户名'
;
COMMENT ON COLUMN MGR_CUSTOMER.fullName IS '客户全称'
;
COMMENT ON COLUMN MGR_CUSTOMER.cardType IS '证件类型：01 身份证 02 机构代码 03 营业执照'
;
COMMENT ON COLUMN MGR_CUSTOMER.card IS '证件号码'
;
COMMENT ON COLUMN MGR_CUSTOMER.type IS '客户类型：0 双边客户 1 唯买客户 2 唯卖客户'
;
COMMENT ON COLUMN MGR_CUSTOMER.bankCode IS '银行代码：01 工行 02 中行 03 交通 04 建行 05 农行'
;
COMMENT ON COLUMN MGR_CUSTOMER.bankAccount IS '银行账号'
;
COMMENT ON COLUMN MGR_CUSTOMER.address IS '所在地址'
;
COMMENT ON COLUMN MGR_CUSTOMER.contactMan IS '联系人'
;
COMMENT ON COLUMN MGR_CUSTOMER.phone IS '电话号码'
;
COMMENT ON COLUMN MGR_CUSTOMER.email IS '邮箱'
;
COMMENT ON COLUMN MGR_CUSTOMER.postcode IS '邮政编码'
;
COMMENT ON COLUMN MGR_CUSTOMER.status IS '状态：N 可用 E 禁用'
;
COMMENT ON COLUMN MGR_CUSTOMER.note IS '备注信息'
;
COMMENT ON COLUMN MGR_CUSTOMER.createTime IS '创建时间'
;
COMMENT ON COLUMN MGR_CUSTOMER.userID IS '最后修改客户管理员'
;

---------------------------------
---      添加测试权限                          ---
---------------------------------

delete from C_RIGHT where MODULEID=98;

insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (9801000000, 9900000000, '客户管理', null, null, null, 98, 99, 0, -1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (9801010000, 9801000000, '信息管理', '42_42.gif', null, null, 98, 1, 0, -1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (9801010100, 9801010000, '客户列表', '29_29.gif', '/dem/customerplay/*', '/dem/customerplay/customerlist.action?sortColumns=order+by+customerId+desc', 98, 1, 0, 0, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (9801010101, 9801010100, '客户详情', null, '/dem/customerplay/customerdetails.action', null, 98, 1, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (9801010102, 9801010100, '添加客户', null, '/dem/customerplay/add*', null, 98, 2, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (9801010103, 9801010100, '修改客户', null, '/dem/customerplay/update*', null, 98, 3, 0, 1, 0);
insert into C_RIGHT (ID, PARENTID, NAME, ICON, AUTHORITYURL, VISITURL, MODULEID, SEQ, VISIBLE, TYPE, CATALOGID)
values (9801010104, 9801010100, '删除客户', null, '/dem/customerplay/delate*', null, 98, 4, 0, 1, 0);
commit;
