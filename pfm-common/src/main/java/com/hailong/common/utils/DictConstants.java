package com.hailong.common.utils;

public class DictConstants
{
   public DictConstants(){
	   
   }

   //line_model车型
   public static final String LINE_MODEL = "line_model";
   
   //carriage_type车种
   public static final String CARRIAGE_TYPE = "carriage_type";
   
   //train_dev_trouble_type故障描述
   public static final String TRAIN_DEV_TROUBLE_TYPE = "train_dev_trouble_type";
   
   //dict_system trouble_type_other 故障描述字典：7：其他
   public static final int TROBULE_TYPE_VALUE_OTHER = 7;
   
   //设备状态：0 发货 1：库存 2.正常 3.故障 4.维修  5.丢失 6.报废 7.核实 8.调拨
   public static final int DEV_STATUS_FH = 0;
   public static final int DEV_STATUS_KC = 1;
   public static final int DEV_STATUS_ZC = 2;
   public static final int DEV_STATUS_GZ = 3;
   public static final int DEV_STATUS_WX = 4;
   public static final int DEV_STATUS_DS = 5;
   public static final int DEV_STATUS_BF = 6;
   public static final int DEV_STATUS_HS = 7;
   public static final int DEV_STATUS_DB = 8;
   
   //安装状态：0 未安装 1：安装正常 2.安装异常
   public static final int INSTALL_STATUS_WAZ = 0;
   public static final int INSTALL_STATUS_ZC = 1;
   public static final int INSTALL_STATUS_YC = 2;
   
   //交通工具类型：1：铁路 2.大巴
   public static final int CAR_TYPE_TRAIN = 1;
   public static final int CAR_TYPE_BUS = 2;
   
   //train_install_change_info final_flag 变动统计 该字段判断该设备记录最终是否改变  0表示否  1表示是
   public static final int TRAIN_CHANGE_FINAL_FLAG_NO = 0;
   public static final int TRAIN_CHANGE_FINAL_FLAG_YES = 1;
   
   //train_install_change_info change_flag 变动统计 判断是路局改变还是状态改变 0都变动  1状态改变   2路局改变 9其他
   public static final int TRAIN_CHANGE_FLAG_ALL = 0;
   public static final int TRAIN_CHANGE_FLAG_STATUS = 1;
   public static final int TRAIN_CHANGE_FLAG_BUREAU = 2;
   public static final int TRAIN_CHANGE_FLAG_OTHER = 9;
   
   //train_dev_trouble trouble_status 故障处理状态：0：未处理， 1：已处理
   public static final int TROBULE_STATUS_ISHANDEL_NO = 0;
   public static final int TROBULE_STATUS_ISHANDEL_YES = 1;
   
   
}
