package com.hailong.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hailong.common.CacheData;
import com.pfm.shared.AES;
import com.pfm.shared.PFMAES;
import com.pfm.shared.PFMDataObject;

public class PFMHBHandler
{
  public static final Logger logger = LoggerFactory.getLogger(PFMHBHandler.class);
  
  public static PFMDataObject.PFM getHeartbeatDataD(Object d)
  {
    PFMDataObject.PFM obj = null;
    if ((d instanceof PFMDataObject.PFM)) {
      obj = (PFMDataObject.PFM)d;
    } else {
      logger.error("not object --->" + d.getClass().getName() + " to object  HeartbeatServerData.D1");
    }
    return obj;
  }
  
  public static String setMsgType(String r)
  {
    String value = "";
    if (r != null) {
      if (r.startsWith("param=")) {
        value = r.substring(6);
      } else if (r.startsWith("result=")) {
        value = r.substring(7);
      } else if (r.startsWith("paramc=")) {
        value = r.substring(7);
      } else if (r.startsWith("resultc=")) {
        value = r.substring(8);
      }
    }
    return value;
  }
  
  public static PFMDataObject.PFM getPFMPObj(PFMAES.Result pfmr)
  {
    PFMDataObject.PFM pfm = null;
    if ((pfmr.objects != null) && (pfmr.objects.length != 0))
    {
      pfm = getHeartbeatDataD(pfmr.objects[0]);
      if ((pfm.phone == null) || (pfm.phone.length() == 0))
      {
        logger.error("phone is null, data:" + pfmr.data);
        return null;
      }
    }
    return pfm;
  }
  
  public static PFMAES.Result getPFMData(String param)
  {
    PFMAES.Result pfmr = new PFMAES.Result();
    pfmr.data = param;
    pfmr.cls1ForPrefix = PFMDataObject.PFM.class;
    boolean falg = PFMAES.parse(CacheData.encryptionKey, pfmr);
    if (!falg) {
      pfmr = null;
    }
    return pfmr;
  }
  
  public static String getAESData(String data)
  {
    data = setMsgType(data);
    return AES.decrypt(CacheData.encryptionKey, data);
  }
  
  public static String getAESMsg(String data)
  {
    return AES.decrypt(CacheData.encryptionKey, data);
  }
}
