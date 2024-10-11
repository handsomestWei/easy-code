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

-- 利用branch字段生成树形区域结构的全路径例：
-- 1、添加dblink扩展。同时需要在目标数据库使用dba账号添加dblink扩展
create extension if not exists dblink;

-- 2.1、跨库创建递归视图方案。填入目标数据库连接串
CREATE OR REPLACE RECURSIVE VIEW v_region (external_index_code, index_code, name, parent_index_code, parent_name, tree_level, tree_path, branch) AS
SELECT * from dblink('host=10.194.141.76 port=7017 dbname=xxdb user=user password=pwd',
'select external_index_code, index_code, name, parent_index_code, ''''::text as parent_name, tree_level, tree_path, NAME::text as branch
from tb_region where index_code = ''root00000000''') as region1(external_index_code VARCHAR, index_code VARCHAR, name VARCHAR, parent_index_code VARCHAR,
parent_name VARCHAR, tree_level integer, tree_path VARCHAR, branch VARCHAR)

UNION ALL

SELECT r.external_index_code, r.index_code, r.name, v_region.index_code as parent_index_code, v_region.name as parent_name, r.tree_level, r.tree_path,
v_region.branch || '/' || r.name
from v_region
join (select * from dblink('host=10.194.141.76 port=7017 dbname=xxdb user=user password=pwd',
'select external_index_code, index_code, name, parent_index_code, tree_level, tree_path from tb_region where operate_delete_flag::text = ''false''::text')
as region2(external_index_code VARCHAR, index_code VARCHAR, name VARCHAR, parent_index_code VARCHAR, tree_level integer, tree_path VARCHAR)) r
on r.parent_index_code = v_region.index_code

-- 2.2、非跨库创建递归视图方案
CREATE OR REPLACE RECURSIVE VIEW v_region(region_code, region_name, parent_code, parent_name, dis_order, region_path, branch) AS (
SELECT region1.region_code,
region1.region_name,
region1.parent_code::text,
''::text as parent_name,
region1.dis_order,
region1.region_path,
region1.region_name::text as branch
FROM tb_region region1 where region_code = 'root00000000'

UNION ALL

SELECT r.region_code,
r.region_name,
v_region_1.region_code AS parent_code,
v_region_1.region_name AS parent_name,
r.dis_order,
r.region_path,
(((v_region_1.branch)::text || '/'::text) || (r.region_name)::text)
FROM v_region v_region_1
JOIN (SELECT region2.region_code,
region2.region_name,
region2.parent_code,
region2.dis_order,
region2.region_path
FROM tb_region region2 WHERE region2.region_status = 0) r
ON r.parent_code = v_region_1.region_code
);