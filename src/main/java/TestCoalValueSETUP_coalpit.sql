SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `Storage` ;
CREATE SCHEMA IF NOT EXISTS `Storage` DEFAULT CHARACTER SET utf8 ;
USE `Storage` ;



DROP TABLE IF EXISTS `Storage`.`behavioural` ;
CREATE  TABLE IF NOT EXISTS `Storage`.`behavioural` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `item_id` INT NULL , -- 收款方
  `type` VARCHAR(20) NULL ,
  `license` VARCHAR(30) NULL ,
  `item_type` VARCHAR(50) NULL ,
  `int_value` INT NULL ,
  `string_value` VARCHAR(50) NULL ,

  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `TestCoalValue`.`transaction`
-- --------------------------------------------------d---
DROP TABLE IF EXISTS `Storage`.`brand_enterprise` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`brand_enterprise` (
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `company_id` INT NULL ,



  `item_id` INT NULL ,
  `item_type` VARCHAR(20) NULL , --  销售过的总类

  `index` INT NULL ,

  `note` VARCHAR(150) NULL , --  销售过的总类

  `status` VARCHAR(20) NULL , --  销售过的总类


  `feature` VARCHAR(20) NULL , --  销售过的总类



  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;







-- -----------------------------------------------------
-- Table `TestCoalValue`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`company` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`company` (
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `company_no` VARCHAR(10) NULL ,

  `COMPANY_NAME` VARCHAR(100) NULL ,
  `COMPANY_TYPE` VARCHAR(50) NULL ,
  `LOGO_IMAGE` VARCHAR(1024) NULL ,
  `PHONE_NUMBER` VARCHAR(20) NULL ,
  `FAX_NUMBER` VARCHAR(20) NULL ,
  `WEBSITE_URL` VARCHAR(200) NULL ,
  `COUNTRY` VARCHAR(20) NULL ,
  `CITY` VARCHAR(20) NULL ,
  `LOCATION` VARCHAR(200) NULL ,
  `COMPANY_DESC` TEXT NULL ,
  `COMMENT` VARCHAR(200) NULL ,
  `BACKGROUND_FILE` VARCHAR(1024) NULL ,
  `MAIN_BUSINESS` VARCHAR(1024) NULL ,
  `ORG_CODE_CERTIFICATION` VARCHAR(100) NULL ,
  `ORG_CODE` VARCHAR(100) NULL ,
  `operating_period` DATE NULL ,
  `Business_license` VARCHAR(100) NULL ,
  `legal_representative` VARCHAR(45) NULL ,
  `Tax_account` VARCHAR(45) NULL ,
  `tax_period` DATE NULL ,
  `coal_license` VARCHAR(100) NULL ,
  `coal_license_period` DATE NULL ,
  `bank_name` VARCHAR(45) NULL ,
  `bank_branch` VARCHAR(100) NULL ,
  `bank_account` VARCHAR(45) NULL ,
  `account_name` VARCHAR(45) NULL ,
  `unified_social_credit_code` VARCHAR(45) NULL ,

-- add by silence yuan 2015/06/28
  `apply_verification_time` DATETIME NULL ,
  `approved_time` DATETIME NULL ,
  `status` VARCHAR(20) NULL COMMENT 'APPLYING,APPROVED' ,
  `public` TINYINT(1) NULL,  -- 是否对外公开

  `brand` TINYINT(1) NULL, -- 是否是品牌公司


  `thumbnail_image` VARCHAR(512) NULL ,
  `small_image` VARCHAR(512) NULL ,


  `company_address_id` INT NULL ,

  `district_id` INT NULL ,

  `operational_product` VARCHAR(100) NULL ,


  `abbreviation_name` VARCHAR(45) NULL ,

    `business_Status` VARCHAR(45) NULL ,
    `be_allowed` VARCHAR(45) NULL ,
        `zipcode` VARCHAR(45) NULL ,




  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `TestCoalValue`.`merchant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`configuration` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`configuration` (
  `ID` INT NOT NULL AUTO_INCREMENT ,


  `user_id` INT NULL ,

  `company_id` INT NULL ,

  `mode` VARCHAR(50) NULL ,

  `key` VARCHAR(50) NULL ,


  `string_value` VARCHAR(50) NULL ,
  `int_value` INT NULL ,
  `boolean_value` TINYINT(1) NULL ,


  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;







-- Table `TestCoalValue`.`ore_district`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`equipment` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`equipment` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `pin` VARCHAR(50) NULL ,
  `status` VARCHAR(50) NULL ,
  `user_id` INT NULL ,
  `company_id` INT NULL ,
  `device_Id`   VARCHAR(50) NULL ,

  `no`   VARCHAR(50) NULL ,


  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;





 -- -----------------------------------------------------
-- Table `TestCoalValue`.`instance_company_management`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`instance_transport` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`instance_transport` (
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `note` VARCHAR(50) NULL ,
  `transport_id` INT NULL ,
  `collaborator_id` INT NULL ,
 `plate_number` VARCHAR(50) NULL ,
  `coal_type` VARCHAR(50) NULL ,
    `granularity` VARCHAR(50) NULL ,

  `queuing_Id` INT NULL ,
  `inventory_Id` INT NULL ,

  `time_interval_id` INT NULL ,
  `delivery_Order_Id` INT NULL ,


  `unit_price` DECIMAL(10,2) NULL,

  `net_weight` DECIMAL(10,2) NULL,

  `total_amount` DECIMAL(10,2) NULL,
  `tare_weight` DECIMAL(10,2) NULL,
  `gross_weight` DECIMAL(10,2) NULL,
  `company_id` INT NULL ,
  `type` VARCHAR(50) NULL ,

  `status` VARCHAR(20) NULL ,
  `storage_id` INT NULL ,
  `manufacturer_id` INT NULL ,


  `bound_time` DATETIME NULL ,
  `outbound_time` DATETIME NULL ,


  `financial_status`  VARCHAR(20) NULL ,


  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;



   -- -----------------------------------------------------
-- Table `TestCoalValue`.`ore_district`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`inventory` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`inventory` (
  `ID` INT NOT NULL AUTO_INCREMENT ,


  `storage_id` INT NULL ,
  `company_Id` INT NULL ,


  `quantity_on_hand` DECIMAL(10,2) NULL ,


  `quote` DECIMAL(10,2) NULL ,


  `item_id`INT NULL ,
  `item_type` VARCHAR(20) NULL  ,

  `no` VARCHAR(20) NULL  ,


    `coal_Type` VARCHAR(20) NULL  ,
        `granularity` VARCHAR(20) NULL  ,
  `status` VARCHAR(20) NULL  ,
  `type` VARCHAR(20) NULL  ,
    `synchronized_status` VARCHAR(20) NULL  ,




  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;






        -- -----------------------------------------------------
-- Table `TestCoalValue`.`ore_district`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`item_transfer_entry` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`item_transfer_entry` (
  `ID` INT NOT NULL AUTO_INCREMENT ,


  `transfer_id` INT NULL ,
  `item_id` INT NULL ,
  `item_type` VARCHAR(20) NULL  ,
  `object_id` INT NULL ,


  `amount` DECIMAL(10,2) NULL ,

  `quantity_on_hand` DECIMAL(10,2) NULL ,

  `type` VARCHAR(20) NULL  ,




  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;




DROP TABLE IF EXISTS `Storage`.`line` ;
CREATE  TABLE IF NOT EXISTS `Storage`.`line` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
    `line_name` VARCHAR(20) NULL ,

  `departure_station` INT NOT NULL , -- 收款方
  `arrival_station` INT NOT NULL , -- 收款方
      `description` VARCHAR(30) NULL ,
  `status` VARCHAR(30) NULL , -- 银行承兑汇票 Bank acceptance 商业承兑汇票 Trade acceptance

  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;


DROP TABLE IF EXISTS `Storage`.`location` ;

  CREATE  TABLE IF NOT EXISTS `Storage`.`location` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(20) NULL ,
    `postal_code` VARCHAR(20) NULL ,

  `country_id` INT  , -- 收款方

  `description` VARCHAR(30) NULL ,
  `status` VARCHAR(30) NULL , -- 银行承兑汇票 Bank acceptance 商业承兑汇票 Trade acceptance

  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;



  -- -----------------------------------------------------
-- Table `TestCoalValue`.`ore_district`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`wx_temporary_qrcode` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`wx_temporary_qrcode` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `channel` VARCHAR(50) NULL ,

  `scan_id` INT NULL ,
  `app_id` VARCHAR(50) NULL ,
  `scan_type` VARCHAR(50) NULL ,

  `company_id` INT NULL ,

  `qr_code` VARCHAR(200) NULL ,

  `scan_count` INT NULL ,
  `note` VARCHAR(50) NULL ,

  `content` VARCHAR(50) NULL ,

  `sub_Scene` VARCHAR(50) NULL ,
  `status` VARCHAR(50) NULL ,

  `ticket` VARCHAR(80) NULL ,

  `expire_seconds` INT NULL ,



  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `TestCoalValue`.`merchant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`transport_operation` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`transport_operation` (
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `space_id` INT NULL ,


  `shipment_id` INT NULL ,
  `canvassing_id` INT NULL ,

  `type` VARCHAR(50) NULL ,

  `status` VARCHAR(50) NULL ,

  `gross_weight` DECIMAL(10,2) NULL,

  `net_weight` DECIMAL(10,2) NULL,

  `actual_weight` DECIMAL(10,2) NULL,
  `tare_weight` DECIMAL(10,2) NULL,


  `plate_number` VARCHAR(50) NULL ,

  `vehicle_type` VARCHAR(50) NULL ,
  `cargo_type` VARCHAR(50) NULL ,
  `bound_time` DATETIME NULL ,
  `outbound_time` DATETIME NULL ,

  `order_id` INT NULL ,

  `driver_id` INT NULL ,
  `truck_id` INT NULL ,
  `partner_id` INT NULL ,

  `product_Type_ID` INT NULL ,


  `from_transport` INT NULL ,
  `to_transport` INT NULL ,

  `contract_Id` INT NULL ,


  `id_Number` VARCHAR(50) NULL ,
  `partner_name` VARCHAR(50) NULL ,

  `driver_Name` VARCHAR(50) NULL ,
  `driver_Phone` VARCHAR(50) NULL ,


  `product_Id` INT NULL ,


  `company_id` INT NULL ,

  `product_no` VARCHAR(50) NULL ,


  `marketPrice` DECIMAL(10,2) NULL,
  `purchasePrice` DECIMAL(10,2) NULL,

  `report_delivery_order_id` INT NULL ,

  `payment_status` VARCHAR(50) NULL ,
    `no` VARCHAR(50) NULL ,
  `consignee_id_number` VARCHAR(50) NULL ,



  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;







-- -----------------------------------------------------
-- Table `TestCoalValue`.`merchant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`storage_space` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`storage_space` (
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `company_id` INT NULL ,

  `collaborator_id` INT NULL ,




  `name` VARCHAR(45) NULL ,  -- 目的地,


  `no` VARCHAR(50) NULL ,


  `lng` VARCHAR(20) NULL ,  -- 目的地,
  `lat` VARCHAR(20) NULL ,  -- 目的地,
  `bd09Lng` VARCHAR(20) NULL ,  -- 目的地,
  `bd09Lat` VARCHAR(20) NULL ,  -- 目的地,

  `province` VARCHAR(20) NULL ,  -- 目的地,
  `city` VARCHAR(20) NULL ,  -- 目的地,
  `district` VARCHAR(20) NULL ,  -- 目的地,
  `street` VARCHAR(20) NULL ,  -- 目的地,
  `street_number` VARCHAR(20) NULL ,  -- 目的地,

  `pending_Count` INT NULL ,
    `loading_Count` INT NULL ,
      `associated_storage_id` INT NULL ,
      `district_id` INT NULL ,

  `major` TINYINT(1) NULL,
  `type` VARCHAR(50) NULL ,

  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;



  -- -----------------------------------------------------
-- Table `TestCoalValue`.`report_DELIVERY_ORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`report_DELIVERY_ORDER` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`report_DELIVERY_ORDER` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
   `item_id` INT NULL , -- 前方等待 车辆
   `item_type` VARCHAR(20) NULL , -- 堆场名称

   `synthesized_Id` INT NULL , -- 前方等待 车辆

   `storage_name` VARCHAR(20) NULL , -- 堆场名称
   `storage_no` VARCHAR(20) NULL , -- 堆场编号

   `company_name` VARCHAR(20) NULL , -- 提煤单位
   `company_no` VARCHAR(20) NULL , -- 提煤单位编号

   `waiting_count` INT NULL , -- 前方等待 车辆

   `product_name` VARCHAR(20) NULL , -- 产品 名称
   `product_no` VARCHAR(20) NULL ,-- 产品编号

   `consignor` VARCHAR(20) NULL , -- 挂靠单位
   `consignor_principal` VARCHAR(20) NULL , -- 挂靠单位负责人


   `qrcode` VARCHAR(20) NULL , -- 挂靠单位负责人

   `plate_number` VARCHAR(20) NULL , -- 挂靠单位负责人
   `consignee_name` VARCHAR(20) NULL , -- 委托司机姓名
   `consignee_phone` VARCHAR(20) NULL ,-- 委托司机联系电话
       `consignee_no` VARCHAR(20) NULL ,-- 委托司机编号
    `consignee_id` VARCHAR(20) NULL ,-- 委托司机身份证号码

   `plate_numbers` VARCHAR(20) NULL , -- 车牌号

   `qrcode_url` VARCHAR(150) NULL ,-- 二维码 图片 下载 地址；或者二维码 内容，需要在文档中合成

   `status` VARCHAR(150) NULL ,-- 状态
   `count` VARCHAR(150) NULL ,-- 获取次数
      `access_code` VARCHAR(150) NULL ,-- 获取次数


   `ticket` VARCHAR(50) NULL ,-- 获取次数
  `transport_Operation_Id` INT NULL ,

   `inventory_no` VARCHAR(20) NULL ,-- 获取次数
   `no` VARCHAR(50) NULL ,-- 获取次数

  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `TestCoalValue`.`quality_inspection_report`        add by silence 3/07/2015 未在数据库中建表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`quality_inspection_report` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`quality_inspection_report` ( -- 质检报告表
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `inspection` INT NULL , -- 检验编号

    `company_id` INT NULL , -- 检验编号

  `inspection_organization` VARCHAR(50) NULL ,  -- 质检机构

  `consignment_company` VARCHAR(50) NULL ,  -- 委托单位
  `inspection_location` VARCHAR(50) NULL ,  -- 联系电话
  `customer_service_id` INT NULL ,  -- 检验地点、
  `sampling_personnel` VARCHAR(50) NULL ,  -- 抽样人员

  `inspection_status` VARCHAR(20) NULL ,  --  质检状态： 未处理，已处理，
  `sampling_time` DATETIME NULL , -- 抽样时间
  `remark` VARCHAR(50) NULL ,  -- 备注


  `status` VARCHAR(20) NULL ,  -- 备注


  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;







-- -----------------------------------------------------
-- Table `TestCoalValue`.`quality_test_item`        add by silence 3/07/2015 未在数据库中建表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`quality_test_item` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`quality_test_item` ( -- 质检项目
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `inspection_application_id` INT NULL , --

  `inspection_report_id` INT NULL , --
  `symbol` VARCHAR(50) NULL ,  -- 符号
  `unit` VARCHAR(20) ,  -- 单位、
  `item_value` VARCHAR(50) NULL ,  -- 值(包含小数）

  `insolation_method` VARCHAR(20) NULL ,  --  检验方法，
  `sampling_time` DATETIME NULL , -- 抽样时间
  `note` VARCHAR(50) NULL ,  -- 备注

  `status` VARCHAR(50) NULL ,  -- 备注
  `item_index` INT NULL ,


  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;




  -- -----------------------------------------------------
-- Table `TestCoalValue`.`tag_update`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`plate_recognition` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`plate_recognition` (
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `colour_code` INT NULL ,
  `direction` VARCHAR(50) NULL ,
  `license` VARCHAR(50) NULL ,
  `time_stamp` DATETIME NULL ,
  `serial_no` VARCHAR(50) NULL ,

  `path` VARCHAR(80) NULL ,

  `type` VARCHAR(20) NULL ,
  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `TestCoalValue`.`price_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`price_category` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`price_category` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `item_type` VARCHAR(20) NULL ,

  `item_id` INT NULL , -- 收款方

  `name` VARCHAR(20) NULL ,
  `trend` VARCHAR(20) NULL ,

  `major` TINYINT(1) NULL,

  `value` DECIMAL(10,2) NULL ,

  `seq` INT NULL ,

  `expected_value` DECIMAL(10,2) NULL ,



  `STATUS` VARCHAR(20) NULL ,

  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `TestCoalValue`.`no_generator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`no_generator` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`no_generator` (
  `ID` INT NOT NULL AUTO_INCREMENT ,


  `company_id` INT NULL ,
  `user_id` INT NULL ,

  `order_no` INT NULL ,
  `shipment_no` INT NULL ,


  `type_name` VARCHAR(50) NULL ,

  `value` INT NULL ,

     `CREATE_BY` INT NULL ,
     `CREATE_DATE` DATETIME NULL ,
     `MODIFY_BY` INT NULL ,
     `MODIFY_DATE` DATETIME NULL ,
     `VERSION` INT NULL ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `TestCoalValue`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`product`;

CREATE TABLE IF NOT EXISTS `Storage`.`product` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NO` VARCHAR(45) NULL,

  `NOTE` VARCHAR(500) NULL,

  `item_id` INT NULL ,

  `item_type` VARCHAR(50) NULL ,
`granularity` VARCHAR(45) NULL,
`coal_Type` VARCHAR(45) NULL,

`status` VARCHAR(45) NULL,
  `company_Id` INT NULL ,

    `storage_Id` INT NULL ,



  `CREATE_BY` INT NULL,
  `CREATE_DATE` DATETIME NULL,
  `MODIFY_BY` INT NULL,
  `MODIFY_DATE` DATETIME NULL,
  `VERSION` INT NULL,
  PRIMARY KEY (`ID`))
  ENGINE = InnoDB;



      -- -----------------------------------------------------
-- Table `TestCoalValue`.`ore_district`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`inventory_transfer` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`inventory_transfer` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `amount` DECIMAL(10,2) NULL ,
  `status` VARCHAR(20) NULL  ,
  `type` VARCHAR(20) NULL  ,


    `distributor` INT NULL ,
    `instance_Id` INT NULL ,
        `inventory_id` INT NULL ,

  `balance` DECIMAL(10,2) NULL ,



  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB








      -- -----------------------------------------------------
-- Table `TestCoalValue`.`ore_district`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`ID_identification` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`ID_identification` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `status` VARCHAR(20) NULL  ,
  `type` VARCHAR(20) NULL  ,
    `id_number` VARCHAR(20) NULL  ,


  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB






-- -----------------------------------------------------
-- Table `TestCoalValue`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`distributor` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`distributor` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `unique_Id` VARCHAR(50) NULL ,

  `company_no` VARCHAR(10) NULL ,

  `name` VARCHAR(100) NULL ,
  `type` VARCHAR(50) NULL ,
  `logo` VARCHAR(1024) NULL ,
  `phone_number` VARCHAR(20) NULL ,
  `fax_number` VARCHAR(20) NULL ,


  `command` VARCHAR(200) NULL ,

  `abbreviation_name` VARCHAR(45) NULL ,

    `status` VARCHAR(45) NULL ,

  `advanced_payment_amount` DECIMAL(10,2) NULL ,

    `synchronized_status` VARCHAR(45) NULL ,
  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;







      -- -----------------------------------------------------
-- Table `TestCoalValue`.`ore_district`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Storage`.`advanced_payment_transfer` ;

CREATE  TABLE IF NOT EXISTS `Storage`.`advanced_payment_transfer` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `amount` DECIMAL(10,2) NULL ,
  `status` VARCHAR(20) NULL  ,
  `type` VARCHAR(20) NULL  ,

  `debit_credit_type` VARCHAR(20) NULL  ,
    `distributor_id` INT NULL ,
    `instance_Id` INT NULL ,
        `inventory_id` INT NULL ,

  `balance` DECIMAL(10,2) NULL ,



  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB

  -- -----------------------------------------------------
-- Table `TestCoalValue`.`ore_district`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `storage`.`temporary_qrcode` ;

CREATE  TABLE IF NOT EXISTS `storage`.`temporary_qrcode` (
  `ID` INT NOT NULL AUTO_INCREMENT ,

  `item_id` INT NULL ,
  `item_type` VARCHAR(50) NULL ,
  `type` VARCHAR(50) NULL ,


  `qr_code` VARCHAR(200) NULL ,

  `note` VARCHAR(50) NULL ,

  `content` VARCHAR(50) NULL ,

  `status` VARCHAR(50) NULL ,

  `ticket` VARCHAR(80) NULL ,

  `expire_seconds` INT NULL ,


  `unique_Id` VARCHAR(80) NULL ,

  `CREATE_BY` INT NULL ,
  `CREATE_DATE` DATETIME NULL ,
  `MODIFY_BY` INT NULL ,
  `MODIFY_DATE` DATETIME NULL ,
  `VERSION` INT NULL DEFAULT 1 ,
  PRIMARY KEY (`ID`) )
  ENGINE = InnoDB;









