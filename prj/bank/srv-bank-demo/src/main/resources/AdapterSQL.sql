create table f_b_adapterFirm
(
  bankID   varchar2(32) not null, --ÒøÐÐ±àºÅ
  firmID   varchar2(32) not null, --Ç©Ô¼ºÅ
  money    number(15,2) default 1000000 not null,
  createtime date default sysdate not null
);
alter table f_b_adapterFirm add constraint PK_f_b_adapterFirm primary key (bankID,FIRMID);

/*
alter table f_b_adapterfirm add bankID varchar2(32) null;

update f_b_adapterfirm set bankID='101';
commit;

alter table f_b_adapterfirm modify bankID varchar2(32) not null;

alter table f_b_adapterFirm drop constraint PK_F_B_BANKS_1;

alter table f_b_adapterFirm add constraint PK_f_b_adapterFirm primary key (bankID,FIRMID);
*/