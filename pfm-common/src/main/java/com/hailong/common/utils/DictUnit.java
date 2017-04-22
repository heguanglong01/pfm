package com.hailong.common.utils;
public class DictUnit
{
   public DictUnit(){
	   
   }

   /**
    * 获取设备状态
    * 0 发货 1：库存 2.正常 3.故障 4.维修  5.丢失 6.报废 7.核实 8.调拨
    * @param devAssetsStatus
    * @return
    */
   public static String getDevAssetsStatus(Integer devAssetsStatus){
	   if(devAssetsStatus!=null){
		   switch (devAssetsStatus) {
				case 0:{
					return "发货";
				}
				case 1:{
				return "库存";
				}
				case 2:{
					return "正常";
				}
				case 3:{
					return "故障 ";
				}
				case 4:{
					return "维修";
				}
				case 5:{
					return "丢失";
				}
				case 6:{
					return "报废";
				}
				case 7:{
					return "核实";
				}
				case 8:{
					return "调拨";
				}
				default:{
					return "";
				}
		   }
	   	 }else{
	   		 return "";
	   	 }	
   }
   
   /**
    * 获取设备状态
    * 0 发货 1：库存 2.正常 3.故障 4.维修  5.丢失 6.报废 7.核实 8.调拨
    * @param devAssetsStatus
    * @return
    */
   public static int getDevAssetsStatusId(String devAssetsStatus){
	   if("发货".equals(devAssetsStatus)){
		   return 0;
	   }
	   if("库存".equals(devAssetsStatus)){
		   return 1;
	   }
	   if("正常".equals(devAssetsStatus)){
		   return 2;
	   }
	   if("故障".equals(devAssetsStatus)){
		   return 3;
	   }
	   if("维修".equals(devAssetsStatus)){
		   return 4;
	   }
	   if("丢失".equals(devAssetsStatus)){
		   return 5;
	   }
	   if("报废".equals(devAssetsStatus)){
		   return 6;
	   }
	   if("核实".equals(devAssetsStatus)){
		   return 7;
	   }
	   if("调拨".equals(devAssetsStatus)){
		   return 8;
	   }
	   return 9; //其他
   }
   
   /**
    * 获取设备安装状态
    * 0.未安装 1.安装正常 2.安装异常
    * @param devAssetsStatus
    * @return
    */
   public static String getisInstall(int isInstall){
	   switch (isInstall) {
			case 0:{
				return "未安装";
			}
			case 1:{
				return "正常";
			}
			case 2:{
				return "异常";
			}
			default:{
				return "";
			}
	   }
   }
   
   /**
    * 根据iccid号获取 运营商
    * @param iccid
    * @return
    */
      public static String getSimType(String iccid){
    	  String iccidType="";
	   if(iccid!=null&&!"".equals(iccid)&&iccid.length()>6){
			String flagStr=iccid.substring(0, 6);
			if(flagStr.equals("898600")||flagStr.equals("898602")){
				iccidType="中国移动";
			}else if(flagStr.equals("898601")||flagStr.equals("898609")){
				iccidType="中国联通";
			}else if(flagStr.equals("898603")||flagStr.equals("898606")){
				iccidType="中国电信";
			}else{}
		}
	   return iccidType;
   }
      /**
       * sim卡状态
       * @param iccid
       * @return
       */
      public static String getSimStatus(String simStatus){
    	  String statusName="";
    	 if(null!=simStatus&&!"".equals(simStatus)){
    		 if(simStatus.equals("1")){
    			 statusName="在线";
    		 }else if(simStatus.equals("2")){
    			 statusName="离线";
    		 }else if(simStatus.equals("3")){
    			 statusName="停卡";
    		 }else if(simStatus.equals("4")){
    			 statusName="未激活";
    		 }else{
    			 
    		 }
    	 }
    	  return statusName;
      }
   
  /**
   * 获取设备安装状态
   * 0.未安装 1.安装正常 2.安装异常
   * @param devAssetsStatus
   * @return
   */
   public static String getLineModel(int lineModel){
	   switch (lineModel) {
			case 1:{
				return "25G";
			}
			case 2:{
				return "25T";
			}
			case 3:{
				return "25K";
			}
			default:{
				return "";
			}
	   }
   }
   public static String getLineModel(String lineModel){
	   if (lineModel!=null&&!"".equals(lineModel)) {
	   if(lineModel.equals("1")){
		   return "25G";
	   }
	   else if(lineModel.equals("2")){
		   return "25T";
	   }
	   else if(lineModel.equals("3")){
		   return "25K";
	   }else{
		   return "";
	   }
	   }
	   return "";
   }
   
   /**
    * 获取设备安装状态
    * 0.未安装 1.安装正常 2.安装异常
    * @param devAssetsStatus
    * @return
    */
    public static String getCarriageType(int carriageType){
 	   switch (carriageType) {
 			case 1:{
 				return "YZ";
 			}
 			case 2:{
 				return "YW";
 			}
 			case 3:{
 				return "RW";
 			}
 			case 4:{
 				return "CA";
 			}
 			case 5:{
 				return "RZ";
 			}
 			default:{
 				return "";
 			}
 	   }
    }
    public static String getCarriageType(String carriageType){
    	if (carriageType!=null&&!"".equals(carriageType)) {
    	if(carriageType.equals("1")){
    		return "YZ";
    	}else if(carriageType.equals("2")){
    		return "YW";
    	}else if(carriageType.equals("3")){
    		return "RW";
    	}else if(carriageType.equals("4")){
    		return "CA";
    	}else if(carriageType.equals("5")){
    		return "RZ";
    	}
     }
    	return "";
    }
    
    /**
     * 获取设备安装状态
     * 0.未安装 1.安装正常 2.安装异常
     * @param devAssetsStatus
     * @return
     */
     public static String getTrobuleType(int trobuleType){
  	   switch (trobuleType) {
  			case 1:{
  				return "403问题";
  			}
  			case 2:{
  				return "404问题";
  			}
  			case 3:{
  				return "验证码获取失败";
  			}
  			case 4:{
  				return "验证失败";
  			}
  			case 5:{
  				return "图片加载不全";
  			}
  			case 6:{
  				return "指示灯异常";
  			}
  			default:{
  				return "其他";
  			}
  	   }
     }
     public static String getTrobuleType(String trobuleType){
    	 if (trobuleType!=null&&!"".equals(trobuleType)) {
    	 if(trobuleType.equals("1")){
    		 return "403问题";
    	 }
    	 else if(trobuleType.equals("2")){
    		 return "404问题";
    	 }
    	 else if(trobuleType.equals("3")){
    		 return "验证码获取失败";
    	 }
    	 else if(trobuleType.equals("4")){
    		 return "验证失败";
    	 }
    	 else if(trobuleType.equals("5")){
    		 return "图片加载不全";
    	 }
    	 else if(trobuleType.equals("6")){
    		 return "指示灯异常";
    	 }
    	 else{
    		 return "其他";
    	 }
    	 }
    	 return "";
     }
     
     /**
      * 获取protal版本
      * 铁路的:包含A的是老版本、包含J的是积分版本、包含N的是统一版本
      * @param devAssetsStatus
      * @return
      */
      public static String getContentVerison(String contentVerison){
    	  if(contentVerison.indexOf("A") != -1){
    		  return "老版";
    	  }else if(contentVerison.indexOf("J") != -1){
    		  return "积分版";
    	  }else if(contentVerison.indexOf("N") != -1){
    		  return "统一版";
    	  }else{
    		  return "";
    	  }
      } 
}
