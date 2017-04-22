package com.hailong.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtils
{

   private static Object initLock = new Object();
   private static MessageDigest digest = null;
   private static final String commonWords[] = { "a", "and", "as", "at", "be", "do", "i", "if", "in", "is", "it", "so",
         "the", "to" };
   private static Map commonWordsMap = null;
   private static Random randGen = null;
   private static char numbersAndLetters[] = null;
   private static SimpleDateFormat datetimeformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public StringUtils()
   {
   }

   public static String getToday(String sFormat)
   {
      SimpleDateFormat dateformatter = new SimpleDateFormat(sFormat);
      return dateformatter.format(new Date());
   }

   public static String getToday()
   {
      SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
      return dateformatter.format(new Date());
   }

   public static String dateTimeToStr(Timestamp d, String sFormat)
   {
      if (d == null)
      {
         return "";
      }
      else
      {
         SimpleDateFormat dateformatter = new SimpleDateFormat(sFormat);
         String s = dateformatter.format(d);
         return s;
      }
   }

   public static Date strToDate(String s)
   {
      try
      {
         Date date = new Date(datetimeformatter.parse(s).getTime());
         Date date2 = date;
         return date2;
      }
      catch (ParseException e)
      {
         e.printStackTrace();
      }
      Date date1 = null;
      return date1;
   }

   public static boolean isChineseChar(String s)
   {
      boolean flag = false;
      if (s == null)
         return true;
      byte abyte0[] = s.getBytes();
      int i = 0;
      do
      {
         if (i >= abyte0.length)
            break;
         if (abyte0[i] < 0)
         {
            flag = true;
            break;
         }
         i++;
      } while (true);
      return flag;
   }

   public static final String nullToEmptyOfStr(String s)
   {
      if (s != null)
         return s.trim();
      else
         return "";
   }

   public static final String nullToEmptyOfStr(String s, String defValue)
   {
      if (s != null)
         return s.trim();
      else
         return defValue;
   }

   public static final String nullToEmptyOfObject(Object o)
   {
      if (o != null)
         return o.toString();
      else
         return "";
   }

   public static final String nullToEmptyOfPath(Object o)
   {
      String separator = System.getProperty("file.separator");
      if (o != null)
      {
         String temp = o.toString();
         if (separator.equals("\\"))
         {// 如果是windows系统
            temp = replace(temp, "\\", "\\\\");
         }
         return temp;
      }
      else
      {
         return "";
      }
   }

   public static final String nvl(String s, String defValue)
   {
      if (s == null || s.equalsIgnoreCase(""))
      {
         return defValue;
      }
      else
      {
         return s;
      }
   }

   private static final String byte2hex(byte b[])
   {
      String hs = "";
      String stmp = "";
      for (int n = 0; n < b.length; n++)
      {
         stmp = Integer.toHexString(b[n] & 0xff);
         if (stmp.length() == 1)
            hs = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(hs)))).append("0")
                  .append(stmp)));
         else
            hs = String.valueOf(hs) + String.valueOf(stmp);
      }

      return hs.toUpperCase();
   }

   public static final String nullToEmpty(Object o, Object defValue)
   {
      if (o != null)
         return o.toString();
      else
         return defValue.toString();
   }

   public static final String escapeErrorChar(String s)
   {
      String s1 = null;
      s1 = s;
      if (s1 == null)
      {
         return s1;
      }
      else
      {
         s1 = replace(s1, "\\", "\\\\");
         s1 = replace(s1, "\"", "\\\"");
         s1 = replace(s1, "\'", "\'");
         return s1;
      }
   }

   public static final boolean strIsDigital(String s)
   {
      boolean flag = false;
      boolean flag1 = true;
      char ac[] = s.toCharArray();
      int i = 0;
      if (i < ac.length && !Character.isDigit(ac[i]))
         flag1 = false;
      return flag1;
   }

   public static final boolean checkIdCard(String s, String s1)
   {
      boolean flag = true;
      String s2 = "";
      String s3 = "";
      String s4 = "";
      String s5 = "";
      if (s.length() != 15 && s.length() != 18 || !isDate(s1))
         flag = false;
      else if (s.length() == 15)
      {
         if (!strIsDigital(s))
         {
            flag = false;
         }
         else
         {
            String s6 = "19".concat(String.valueOf(String.valueOf(s.substring(6, 8))));
            String s8 = s.substring(8, 10);
            String s10 = s.substring(10, 12);
            String s12 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s6)))).append(
                  "-").append(s8).append("-").append(s10)));
            if (!s12.equals(s1))
               flag = false;
         }
      }
      else if (s.substring(17, 18).equals("X") || s.substring(17, 18).equals("x"))
      {
         if (!strIsDigital(s.substring(0, 18)))
            flag = false;
      }
      else if (!strIsDigital(s))
      {
         flag = false;
      }
      else
      {
         String s7 = s.substring(6, 10);
         String s9 = s.substring(10, 12);
         String s11 = s.substring(12, 14);
         String s13 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s7)))).append("-")
               .append(s9).append("-").append(s11)));
         if (!s13.equals(s1))
            flag = false;
      }
      return flag;
   }

   public static boolean isDate(String s)
   {
      boolean flag = false;
      DateFormat dateformat = DateFormat.getDateInstance();
      if (s == null)
         flag = false;
      try
      {
         Date date = dateformat.parse(s);
         flag = true;
      }
      catch (Exception exception)
      {
         flag = false;
      }
      return flag;
   }

   public static String isInputError(int i, int j)
   {
      if ((i & j) > 0)
         return "Error";
      else
         return "True";
   }

   public static Vector splite(String s, String s1)
   {
      Vector vector = new Vector();
      Object obj = null;
      Object obj1 = null;
      boolean flag = false;
      do
      {
         if (s.length() < 0)
            break;
         int i = s.indexOf(s1);
         if (i < 0)
         {
            vector.addElement(s);
            break;
         }
         String s2 = s.substring(0, i);
         vector.addElement(s2);
         s = s.substring(i + s1.length(), s.length());
      } while (true);
      return vector;
   }

   public static String[] split(String strSource, String strDiv)
   {
      int arynum = 0, intIdx = 0, intIdex = 0;
      int div_length = strDiv.length();
      if (strSource.compareTo("") != 0)
      {
         if (strSource.indexOf(strDiv) != -1)
         {
            intIdx = strSource.indexOf(strDiv);
            for (int intCount = 1;; intCount++)
            {
               if (strSource.indexOf(strDiv, intIdx + div_length) != -1)
               {
                  intIdx = strSource.indexOf(strDiv, intIdx + div_length);
                  arynum = intCount;
               }
               else
               {
                  arynum += 2;
                  break;
               }
            }
         }
         else
         {
            arynum = 1;
         }
      }
      else
      {
         arynum = 0;

      }
      intIdx = 0;
      intIdex = 0;
      String[] returnStr = new String[arynum];

      if (strSource.compareTo("") != 0)
      {
         if (strSource.indexOf(strDiv) != -1)
         {
            intIdx = (int) strSource.indexOf(strDiv);
            returnStr[0] = (String) strSource.substring(0, intIdx);
            for (int intCount = 1;; intCount++)
            {
               if (strSource.indexOf(strDiv, intIdx + div_length) != -1)
               {
                  intIdex = (int) strSource.indexOf(strDiv, intIdx + div_length);
                  returnStr[intCount] = (String) strSource.substring(intIdx + div_length, intIdex);
                  intIdx = (int) strSource.indexOf(strDiv, intIdx + div_length);
               }
               else
               {
                  returnStr[intCount] = (String) strSource.substring(intIdx + div_length, strSource.length());
                  break;
               }
            }
         }
         else
         {
            returnStr[0] = (String) strSource.substring(0, strSource.length());
            return returnStr;
         }
      }
      else
      {
         return returnStr;
      }
      return returnStr;
   }

   public static final String replace(String s, String s1, String s2)
   {
      if (s == null)
         return null;
      int i = 0;
      if ((i = s.indexOf(s1, i)) >= 0)
      {
         char ac[] = s.toCharArray();
         char ac1[] = s2.toCharArray();
         int j = s1.length();
         StringBuffer stringbuffer = new StringBuffer(ac.length);
         stringbuffer.append(ac, 0, i).append(ac1);
         i += j;
         int k;
         for (k = i; (i = s.indexOf(s1, i)) > 0; k = i)
         {
            stringbuffer.append(ac, k, i - k).append(ac1);
            i += j;
         }

         stringbuffer.append(ac, k, ac.length - k);
         return stringbuffer.toString();
      }
      else
      {
         return s;
      }
   }

   public static final String replace(String s, String s1, String s2, int pos)
   {
      if (s == null)
         return null;
      if (s2 == null)
         s2 = "";

      int i = 0;

      if ((i = s.indexOf(s1, pos)) >= 0)
      {
         char ac[] = s.toCharArray();
         char ac1[] = s2.toCharArray();
         int j = s1.length();
         StringBuffer stringbuffer = new StringBuffer(ac.length);
         stringbuffer.append(ac, 0, i).append(ac1).append(ac, i + 1, ac.length - i - 1);
         return stringbuffer.toString();
      }
      else
      {
         return s;
      }
   }

   public static final String replaceIgnoreCase(String s, String s1, String s2)
   {
      if (s == null)
         return null;
      String s3 = s.toLowerCase();
      String s4 = s1.toLowerCase();
      int i = 0;
      if ((i = s3.indexOf(s4, i)) >= 0)
      {
         char ac[] = s.toCharArray();
         char ac1[] = s2.toCharArray();
         int j = s1.length();
         StringBuffer stringbuffer = new StringBuffer(ac.length);
         stringbuffer.append(ac, 0, i).append(ac1);
         i += j;
         int k;
         for (k = i; (i = s3.indexOf(s4, i)) > 0; k = i)
         {
            stringbuffer.append(ac, k, i - k).append(ac1);
            i += j;
         }

         stringbuffer.append(ac, k, ac.length - k);
         return stringbuffer.toString();
      }
      else
      {
         return s;
      }
   }

   public static final String replace(String s, String s1, String s2, int ai[])
   {
      if (s == null)
         return null;
      int i = 0;
      if ((i = s.indexOf(s1, i)) >= 0)
      {
         int j = 0;
         j++;
         char ac[] = s.toCharArray();
         char ac1[] = s2.toCharArray();
         int k = s1.length();
         StringBuffer stringbuffer = new StringBuffer(ac.length);
         stringbuffer.append(ac, 0, i).append(ac1);
         i += k;
         int l;
         for (l = i; (i = s.indexOf(s1, i)) > 0; l = i)
         {
            j++;
            stringbuffer.append(ac, l, i - l).append(ac1);
            i += k;
         }

         stringbuffer.append(ac, l, ac.length - l);
         ai[0] = j;
         return stringbuffer.toString();
      }
      else
      {
         return s;
      }
   }

   public static final String getBpelIDType(String bpelTypeID)
   {
      String id = null;
      if (bpelTypeID != null)
      {
         id = replace(bpelTypeID.substring(4), ".", "");
      }
      return id;
   }

   public static final String formatInputStr(String s)
   {
      String s1 = s;
      s1 = nullToEmptyOfStr(s1);
      s1 = escapeHTMLTags(s1);
      return s1;
   }

   public static final String escapeHTMLTags(String s)
   {
      if (s == null || s.length() == 0)
         return s;
      StringBuffer stringbuffer = new StringBuffer(s.length());
      byte byte0 = 32;
      for (int i = 0; i < s.length(); i++)
      {
         char c = s.charAt(i);
         if (c == '<')
         {
            stringbuffer.append("&lt;");
            continue;
         }
         if (c == '>')
            stringbuffer.append("&gt;");
         else
            stringbuffer.append(c);
      }

      return stringbuffer.toString();
   }

   public static final synchronized String hash(String s)
   {
      if (digest == null)
         try
         {
            digest = MessageDigest.getInstance("MD5");
         }
         catch (NoSuchAlgorithmException nosuchalgorithmexception)
         {
            System.err.println("Failed to load the MD5 MessageDigest. Jive will be unable to function normally.");
            nosuchalgorithmexception.printStackTrace();
         }
      digest.update(s.getBytes());
      return toHex(digest.digest());
   }

   public static final String toHex(byte abyte0[])
   {
      StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
      for (int i = 0; i < abyte0.length; i++)
      {
         if ((abyte0[i] & 0xff) < 16)
            stringbuffer.append("0");
         stringbuffer.append(Long.toString(abyte0[i] & 0xff, 16));
      }

      return stringbuffer.toString();
   }

   public static final String[] toLowerCaseWordArray(String s)
   {
      if (s == null || s.length() == 0)
         return new String[0];
      StringTokenizer stringtokenizer = new StringTokenizer(s, " ,\r\n.:/\\+");
      String as[] = new String[stringtokenizer.countTokens()];
      for (int i = 0; i < as.length; i++)
         as[i] = stringtokenizer.nextToken().toLowerCase();

      return as;
   }

   public static final String[] removeCommonWords(String as[])
   {
      if (commonWordsMap == null)
         synchronized (initLock)
         {
            if (commonWordsMap == null)
            {
               commonWordsMap = new HashMap();
               for (int i = 0; i < commonWords.length; i++)
                  commonWordsMap.put(commonWords[i], commonWords[i]);

            }
         }
      ArrayList arraylist = new ArrayList(as.length);
      for (int j = 0; j < as.length; j++)
         if (!commonWordsMap.containsKey(as[j]))
            arraylist.add(as[j]);

      return (String[]) arraylist.toArray(new String[arraylist.size()]);
   }

   public static final String randomString(int i)
   {
      if (i < 1)
         return null;
      if (randGen == null)
         synchronized (initLock)
         {
            if (randGen == null)
            {
               randGen = new Random();
               numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                     .toCharArray();
            }
         }
      char ac[] = new char[i];
      for (int j = 0; j < ac.length; j++)
         ac[j] = numbersAndLetters[randGen.nextInt(71)];

      return new String(ac);
   }

   public static final String chopAtWord(String s, int i)
   {
      if (s == null)
         return s;
      char ac[] = s.toCharArray();
      int j = s.length();
      if (i < j)
         j = i;
      for (int k = 0; k < j - 1; k++)
      {
         if (ac[k] == '\r' && ac[k + 1] == '\n')
            return s.substring(0, k);
         if (ac[k] == '\n')
            return s.substring(0, k);
      }

      if (ac[j - 1] == '\n')
         return s.substring(0, j - 1);
      if (s.length() < i)
         return s;
      for (int l = i - 1; l > 0; l--)
         if (ac[l] == ' ')
            return s.substring(0, l).trim();

      return s.substring(0, i);
   }

   public static final String highlightWords(String s, String as[], String s1, String s2)
   {
      if (s == null || as == null || s1 == null || s2 == null)
         return null;
      for (int i = 0; i < as.length; i++)
      {
         String s3 = s.toLowerCase();
         char ac[] = s.toCharArray();
         String s4 = as[i].toLowerCase();
         int j = 0;
         if ((j = s3.indexOf(s4, j)) < 0)
            continue;
         int k = s4.length();
         StringBuffer stringbuffer = new StringBuffer(ac.length);
         boolean flag = false;
         char c = ' ';
         if (j - 1 > 0)
         {
            c = ac[j - 1];
            if (!Character.isLetter(c))
               flag = true;
         }
         boolean flag1 = false;
         char c1 = ' ';
         if (j + k < ac.length)
         {
            c1 = ac[j + k];
            if (!Character.isLetter(c1))
               flag1 = true;
         }
         if (flag && flag1 || j == 0 && flag1)
         {
            stringbuffer.append(ac, 0, j);
            if (flag && c == ' ')
               stringbuffer.append(c);
            stringbuffer.append(s1);
            stringbuffer.append(ac, j, k).append(s2);
            if (flag1 && c1 == ' ')
               stringbuffer.append(c1);
         }
         else
         {
            stringbuffer.append(ac, 0, j);
            stringbuffer.append(ac, j, k);
         }
         j += k;
         int l;
         for (l = j; (j = s3.indexOf(s4, j)) > 0; l = j)
         {
            boolean flag2 = false;
            char c2 = ac[j - 1];
            if (!Character.isLetter(c2))
               flag2 = true;
            boolean flag3 = false;
            if (j + k < ac.length)
            {
               c1 = ac[j + k];
               if (!Character.isLetter(c1))
                  flag3 = true;
            }
            if (flag2 && flag3 || j + k == ac.length)
            {
               stringbuffer.append(ac, l, j - l);
               if (flag2 && c2 == ' ')
                  stringbuffer.append(c2);
               stringbuffer.append(s1);
               stringbuffer.append(ac, j, k).append(s2);
               if (flag3 && c1 == ' ')
                  stringbuffer.append(c1);
            }
            else
            {
               stringbuffer.append(ac, l, j - l);
               stringbuffer.append(ac, j, k);
            }
            j += k;
         }

         stringbuffer.append(ac, l, ac.length - l);
         s = stringbuffer.toString();
      }

      return s;
   }

   public static final String escapeForXML(String s)
   {
      if (s == null || s.length() == 0)
         return s;
      char ac[] = s.toCharArray();
      StringBuffer stringbuffer = new StringBuffer(ac.length);
      for (int i = 0; i < ac.length; i++)
      {
         char c = ac[i];
         if (c == '<')
         {
            stringbuffer.append("&lt;");
            continue;
         }
         if (c == '>')
         {
            stringbuffer.append("&gt;");
            continue;
         }
         if (c == '"')
         {
            stringbuffer.append("&quot;");
            continue;
         }
         if (c == '&')
            stringbuffer.append("&amp;");
         else
            stringbuffer.append(c);
      }

      return stringbuffer.toString();
   }

   public static final String unescapeFromXML(String s)
   {
      s = replace(s, "&lt;", "<");
      s = replace(s, "&gt;", ">");
      s = replace(s, "&amp;", "&");
      return replace(s, "&quot;", "\"");
   }

   public static final String formatTextArea(String s, int i)
   {
      if (s == null)
         return "";
      String s1 = s.trim();
      Vector vector = null;
      int j = i;
      s1 = String.valueOf(String.valueOf(s1)).concat("\n");
      vector = splite(s1, "\n");
      boolean flag = false;
      boolean flag1 = false;
      String s2 = "";
      String s4 = "";
      String s6 = "";
      char ac[] = s1.toCharArray();
      StringBuffer stringbuffer = new StringBuffer(ac.length);
      for (int k = 0; k < vector.size() - 1; k++)
      {
         String s5 = vector.elementAt(k).toString();
         s5 = replace(s5, "\r", "");
         if (s5.length() > j)
         {
            String s7 = s5;
            for (int l = 0; l < s5.length() / j; l++)
            {
               String s3 = String.valueOf(String.valueOf(s7.substring(0, j))).concat("\n");
               stringbuffer.append(s3.toCharArray(), 0, s3.toCharArray().length);
               s7 = s7.substring(j, s7.length());
            }

            stringbuffer.append(String.valueOf(String.valueOf(s7)).concat("\n").toCharArray(), 0, String.valueOf(
                  String.valueOf(s7)).concat("\n").toCharArray().length);
         }
         else
         {
            stringbuffer.append(String.valueOf(String.valueOf(s5)).concat("\n").toCharArray(), 0, String.valueOf(
                  String.valueOf(s5)).concat("\n").toCharArray().length);
         }
      }

      return stringbuffer.toString().trim();
   }

   public static String doubleToString(double value, int precision)
   {
      String val = Double.toString(value);
      if (val.indexOf(".") > 0 && val.substring(val.indexOf(".")).length() >= precision)
      {
         double add = 0.5D;
         for (int i = 0; i < precision; i++)
            add /= 10;

         double resource = value + add;
         String res = Double.toString(resource);
         String pre = res.substring(0, res.indexOf("."));
         String post = res.substring(res.indexOf("."), res.indexOf(".") + 1 + precision);
         val = String.valueOf(pre) + String.valueOf(post);
      }
      return val;
   }

   public static final String toChinese(String m_str)
   {
      String return_str = "";
      try
      {
         byte m_bstr[] = m_str.getBytes("8859_1");
         return_str = new String(m_bstr, "GB2312");
      }
      catch (Exception e)
      {
         System.err.println("toChinese error caught: ".concat(String.valueOf(String.valueOf(e.getMessage()))));
      }
      return return_str;
   }

   public static final String toGB2312(String m_str)
   {
      return toChinese(m_str);
   }

   public static final String toBig5(String m_str)
   {
      String return_str = "";
      try
      {
         byte m_bstr[] = m_str.getBytes("8859_1");
         return_str = new String(m_bstr, "Big5");
      }
      catch (Exception e)
      {
         System.err.println("toBig5 error caught: ".concat(String.valueOf(String.valueOf(e.getMessage()))));
      }
      return return_str;
   }

   public static final String toISO8859(String m_str)
   {
      String return_str = "";
      try
      {
         byte m_bstr[] = m_str.getBytes("GB2312");
         return_str = new String(m_bstr, "8859_1");
      }
      catch (Exception e)
      {
         System.err.println("toChinese error caught: ".concat(String.valueOf(String.valueOf(e.getMessage()))));
      }
      return return_str;
   }

   public static String arrayToDelimiterStr(String delimiter, String[] str)
   {
      StringBuffer buf = new StringBuffer("");
      int i = 0;
      for (i = 0; i < str.length; i++)
      {
         buf.append(str[i]).append(delimiter);
      }

      if (i > 0)
         buf.delete(buf.length() - delimiter.length(), buf.length());

      return buf.toString();
   }

   public static String[] delimiterStrToArray(String delimiter, String str)
   {
      StringTokenizer st = new StringTokenizer(str, delimiter);// 以/分隔，
      String[] buf = new String[st.countTokens()];

      int i = 0;
      while (st.hasMoreTokens())
         buf[i++] = st.nextToken();

      return buf;
   }

   public static String[] csvToArray(String csv)
   {

      StringTokenizer st = new StringTokenizer(csv, "/");// 以/分隔，
      String[] buf = new String[st.countTokens()];

      int i = 0;
      while (st.hasMoreTokens())
         buf[i++] = st.nextToken();

      return buf;
   }

   public static String[] csvToArray(String csv, String separator)
   {

      StringTokenizer st = new StringTokenizer(csv, separator);// 以separator分隔，
      String[] buf = new String[st.countTokens()];

      int i = 0;
      while (st.hasMoreTokens())
         buf[i++] = st.nextToken();

      return buf;
   }

   public static String toHTMLString(String in)
   {
      StringBuffer out = new StringBuffer();
      for (int i = 0; in != null && i < in.length(); i++)
      {
         char c = in.charAt(i);
         if (c == '\'')
            out.append("&#039;");
         else if (c == '\"')
            out.append("&#034;");
         else if (c == '<')
            out.append("&lt;");
         else if (c == '>')
            out.append("&gt;");
         else if (c == '&')
            out.append("&amp;");
         else if (c == ' ')
            out.append("&nbsp;");
         else if (c == '\n')
            out.append("<br/>");
         else
            out.append(c);
      }
      return out.toString();
   }

   /**
    * 按字节数截取字符串长度,但不能出现半个汉字的情况
    * 
    * @param source
    *            要截取的字符串
    * @length 要截取的长度
    * @return 返回截取后的字符串
    */
   public static String substringB(String source, int lengthB) throws Exception
   {
      String returnStr = null;
      try
      {
         // 如果要截取长度大于字符串的字节数,则返回原字符串
         if (lengthB > source.getBytes().length)
         {
            return source;
            // length = source.getBytes().length;
         }
         else
         {
            int len = lengthB;
            if (lengthB > source.length())
               len = source.length();
            for (int i = len; i > 0; i--)
            {
               String substr = source.substring(0, i);
               if (lengthB >= substr.getBytes().length)
               {
                  returnStr = substr;
                  break;
               }

            }
         }
      }
      catch (Exception e)
      {
         System.out.println("function substringB error : " + e.getMessage());
         throw e;
      }
      return returnStr;
   }

   public static boolean isEmpty(String str)
   {
      if (str == null || "".equals(str))
         return true;
      return false;
   }
   
   /**
    * 用户特殊符号分割 不可操作符
    * @return
    */
   public static String getSplite(){
	   return String.valueOf((char) 0x01);
   }
   /**
    * 根据redis中的key 返回给mac地址
    * @param str HB#ST#FFFFFFFFFF
    * @return FFFFFFFFFF
    */
   public static String getHeartMac(String str){
	   return str.substring(str.lastIndexOf("#")+1,str.length());
   }

   /**
    * 把mac地址去掉冒号
    * @param str FF:FF:FF:FF:FF
    * @return FFFFFFFFFF
    */
   public static String getSpliteMac(String str){
	   return str.replaceAll(":","");
   }
}
