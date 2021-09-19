set client_encoding = 'utf8';
-- 安装扩展包支持uuid函数，主键使用该函数
create extension if not exists  "uuid-ossp";

drop table if exists tb_test;
CREATE TABLE "tb_test" (
	"id" VARCHAR(36) NOT NULL DEFAULT 'uuid_generate_v4()',
	"phone" VARCHAR(48) NULL DEFAULT NULL,
	"create_time" timestamp NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);

comment on table tb_test is '测试表';
COMMENT ON COLUMN "tb_test"."id" IS '主键';
COMMENT ON COLUMN "tb_test"."phone" IS '手机号码';

CREATE INDEX idx_tb_test_phone ON public.tb_test USING btree (phone);

-- 按时间分区，结合before insert触发器，使用INHERITS继承的方式创建分区表。
-- 由于表继承，查询时会查找主表和所有分区表的数据
CREATE OR REPLACE FUNCTION auto_insert_into_tb_test_partition()
  RETURNS trigger AS
$BODY$
DECLARE
    time_column_name 	text ;			-- 父表中用于分区的时间字段的名称[必须首先初始化!!]
    curMM 		varchar(6);		        -- 'YYYYMM'字串,用做分区子表的后缀
    isExist 		boolean;		    -- 分区子表,是否已存在
    parent_table_name varchar;          --父表名称
    startTime 		text;
    endTime		text;
    strSQL  		text;

BEGIN
    -- 调用前,必须首先初始化(时间字段名):time_column_name [直接从调用参数中获取!!]
    time_column_name := TG_ARGV[0];
    parent_table_name :=tg_argv[1];

    -- 判断对应分区表 是否已经存在?
    EXECUTE 'SELECT $1.'||time_column_name INTO strSQL USING NEW;
    curMM := to_char( strSQL::timestamp , 'YYYYMM' );
    select count(*) INTO isExist from pg_class where relname = (parent_table_name||'_'||curMM);

    -- 若不存在, 则插入前需 先创建子分区
    IF ( isExist = false ) THEN
        -- 创建子分区表
        startTime := curMM||'01 00:00:00.000';
        endTime := to_char( startTime::timestamp + interval '1 month', 'YYYY-MM-DD HH24:MI:SS.MS');
        strSQL := 'CREATE TABLE IF NOT EXISTS '||parent_table_name||'_'||curMM||
                  ' ( CHECK('||time_column_name||'>='''|| startTime ||''' AND '
                             ||time_column_name||'< '''|| endTime ||''' )
                          ) INHERITS ('||parent_table_name||') ;'  ;
        EXECUTE strSQL;

        -- 创建索引
        strSQL := 'CREATE INDEX '||parent_table_name||'_'||curMM||'_INDEX_'||time_column_name||' ON '
                  ||parent_table_name||'_'||curMM||' ('||time_column_name||');' ;
        EXECUTE strSQL;

    END IF;

    -- 插入数据到子分区!
    strSQL := 'INSERT INTO '||parent_table_name||'_'||curMM||' SELECT $1.*' ;
    EXECUTE strSQL USING NEW;
    RETURN NULL;
END
$BODY$
  LANGUAGE plpgsql;

-- 创建触发器
create trigger trig_insert_tb_test
    before insert
    on tb_favorite_detail
    for each row
    execute procedure auto_insert_into_tb_test_partition('create_time','tb_test');