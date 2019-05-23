package com.hll.test.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class Util {


    /**
     * 判断输入对象是否为空（null/空字符串）
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return str == null || str.toString().isEmpty();
    }

    /**
     * 判断输入字符串是否不为null且不为空
     * 
     * @param str
     * @return
     */
    public static boolean notEmpty(Object str) {

        return !Util.isEmpty(str);
    }

    /***
     * 按照给定格式将字符串转化为java.util.Date类型返回
     * 
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date StrToDate(String dateStr, String formatStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 给定长度字符串str长度小于指定长度len时，在左边补上指定字符charStr
     * 
     * @param str
     * @param charStr
     * @return
     */
    public static String lpad(String str, int len, String charStr) {
        try {

            if (str == null || str.length() >= len) {
                return str;
            }
            StringBuilder bf = new StringBuilder(str);
            while (bf.length() < len) {
                bf.insert(0, charStr);
            }
            return bf.toString();

        } catch (Exception e) {
            return str;
        }
    }

    /**
     * 给定长度字符串str长度小于指定长度len时，在左边补上指定字符charStr
     * 
     * @param str
     * @param charStr
     * @return
     */
    public static String rpad(String str, int len, String charStr) {
        try {

            if (str == null || str.length() >= len) {
                return str;
            }
            StringBuilder bf = new StringBuilder(str);
            while (bf.length() < len) {
                bf.append(charStr);
            }
            return bf.toString();

        } catch (Exception e) {
            return str;
        }
    }

    /**
     * 获取java.util.Date中的年、月、日、时、分、秒
     * 
     * @param date
     *             java.util.Date
     * @param type
     *             类型(YEAR,MONTH,DATE,HOUR,MIN,SEC)
     * @return 从1开始数值，异常返回-1
     */
    public static int getDateAttr(Date date, String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if ("YEAR".equals(type)) {
            return calendar.get(Calendar.YEAR);
        } else if ("MONTH".equals(type)) {
            return calendar.get(Calendar.MONTH) + 1;
        } else if ("DATE".equals(type)) {
            return calendar.get(Calendar.DAY_OF_MONTH);
        } else if ("HOUR".equals(type)) {
            return calendar.get(Calendar.HOUR);
        } else if ("MIN".equals(type)) {
            return calendar.get(Calendar.MINUTE);
        } else if ("SEC".equals(type)) {
            return calendar.get(Calendar.SECOND);
        }
        return -1;
    }

    /**
     * 如果对象为null则返回defaultReturn 否则返回对象的toString结果。
     *
     * @param str
     * @param defaultReturn
     *             当对象为null时，返回该值
     * @return
     */
    public static String toString(Object str, String defaultReturn) {
        if (str == null) {
            return defaultReturn;
        }
        return str.toString();
    }

    /**
     * 若对象可以转换为Integer则返回该对象的number表达值 否则返回defaultReturn。
     *
     * @param number
     * @param defaultReturn
     *             当对象无法转换为Integer时返回该值
     * @return
     */
    public static Integer toInteger(Object number, Integer defaultReturn) {
        if (number == null) {
            return defaultReturn;
        }
        Integer i = 0;
        try {
            i = Integer.valueOf(number.toString().trim());
        } catch (Exception e) {
            i = defaultReturn;
        }
        return i;
    }

    /**
     * 若对象可以转换为Long则返回该对象的long表达值 否则返回defaultReturn。
     *
     * @param number
     * @param defaultReturn
     *             当对象无法转换为long时返回该值
     * @return
     */
    public static Long toLong(Object number, Long defaultReturn) {
        if (number == null) {
            return defaultReturn;
        }
        Long i = 0l;
        try {
            i = Long.valueOf(number.toString().trim());
        } catch (Exception e) {
            i = defaultReturn;
        }
        return i;
    }

    /**
     * 将给定字符串中表达式的{0}...{n}使用后面参数中的对象填充
     *
     * @param expressMsg
     * @param param
     * @return
     */
    public static String formatMsg(String expressMsg, Object... param) {
        return java.text.MessageFormat.format(expressMsg, param);
    }

    /**
     * 正则表达式验证
     *
     * @param value
     *             待验证的字符串
     * @param pattern
     *             正则表达式格式
     * @return 匹配：true，不匹配：false
     * @throws Exception
     */
    public static boolean match(String value, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(value);
        return m.matches();
    }

    /**
     * 去空格 ,为null 时返回指定字符串值
     *
     * @param obj
     * @param defaultReturn
     * @return
     */
    public static String trim(Object obj, String defaultReturn) {
        if (obj == null) {
            return defaultReturn;
        }
        return obj.toString().trim();
    }

    /**
     * 去空格，为null时返回null
     *
     * @param obj
     * @return
     */
    public static String trim(Object obj) {
        return Util.trim(obj, null);
    }

    /**
     * 去除指定字符序列
     *
     * @param obj
     * @param seq
     * @return
     */
    public static String trimString(Object obj, CharSequence seq) {
        if (obj == null) {
            return null;
        }
        if (obj.toString().isEmpty()) {
            return "";
        }
        return obj.toString().replace(seq, "");
    }

    /**
     * 判断指定对象是否为货币金额
     *
     * @param number
     * @return
     */
    public static boolean isMoneyNumber(Object number) {

        if (number == null || number.toString().isEmpty()) {
            return false;
        }

        return Util.match(number.toString(), "^\\d*$|^\\d*[.]\\d{1,2}$");
    }

    /**
     * 输入字符串为空字符串("")时，返回null
     *
     * @param str
     * @return
     */
    public static String blankToNull(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return str;
    }

    /**
     * 输入整形值为(0)时，返回null
     *
     * @param i
     * @return
     */
    public static Integer zeroToNull(Integer i) {
        if (i == null || i == 0) {
            return null;
        }
        return i;
    }

    /**
     * 输入长整形为（0l）时返回null
     *
     * @param l
     * @return
     */
    public static Long zeroToNull(Long l) {
        if (l == null || l == 0l) {
            return null;
        }
        return l;
    }

    /**
     * 输入参数值和(BigDecimal.valueOf(0))相等时，返回null
     *
     * @param d
     * @return
     */
    public static BigDecimal zeroToNull(BigDecimal d) {
        if (d == null || d.compareTo(BigDecimal.valueOf(0)) == 0) {
            return null;
        }
        return d;
    }

    /**
     * 输入参数值为(0d)时，返回null
     *
     * @param d
     * @return
     */
    public static Double zeroToNull(Double d) {
        if (d == null || d == 0d) {
            return null;
        }
        return d;
    }

    /**
     * 判断输入对象是否为空（null/空字符串）
     * @param str
     * @return
     */
    public static String defaultReturn(Object str, String defaultReturn) {
        if (str == null || str.toString().isEmpty()) {
            return defaultReturn;
        }
        return str.toString();
    }

    public static boolean checkNull(Object... args) {
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                return true;
            }
            if (arg instanceof String && ((String) arg).length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static void writeHttpResponse(HttpServletResponse response, Response msg)
            throws IOException {
        writeHttpResponse(response, JSONObject.toJSONString(msg));
    }

    public static void writeHttpResponse(HttpServletResponse response, String msg)
            throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        response.getOutputStream().write(msg.getBytes(com.firenio.baseio.common.Util.UTF8));
    }

    public static String URLEncode(String s) {
        return URLEncode(s, com.firenio.baseio.common.Util.UTF8);
    }

    public static String URLEncode(String s, Charset charset) {
        try {
            return URLEncoder.encode(s, charset.name());
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public static String URLDecode(String s) {
        return URLDecode(s, com.firenio.baseio.common.Util.UTF8);
    }

    public static String URLDecode(String s, Charset charset) {
        try {
            return URLDecoder.decode(s, charset.name());
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    /**
     * 移除分页查询中的OBID
     * @param list
     */
    public static void removeOBID(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (Map map : list) {
            map.remove("OBID");
        }
    }

    public static String getUserHome() {
        String userHome = System.getProperty("user.home");
        if (!userHome.endsWith("/")) {
            userHome += "/";
        }
        return userHome;
    }

    // 获取HttpServletRequest里面的参数，并decode
    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        Map<String, String> params2 = new HashMap<>();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (values.length > 0) {
                params2.put(key, values[0]);
            }
        }
        return params2;
    }

    public static String genMerOrderId(String msgId) {
        String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        String rand = RandomStringUtils.randomNumeric(7);
        return msgId + date + rand;
    }

    public static String genNoWithTime() {
        String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        String rand = RandomStringUtils.randomNumeric(7);
        return date + rand;
    }

    private static String buildUrlParametersStr(Map<String, String> paramMap) {
        Entry entry;
        StringBuffer buffer = new StringBuffer();

        for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext();) {
            entry = (Entry) iterator.next();

            buffer.append(entry.getKey().toString()).append("=");
            try {
                if (entry.getValue() != null
                        && StringUtils.isNotBlank(entry.getValue().toString())) {
                    buffer.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {}

            buffer.append(iterator.hasNext() ? "&" : "");
        }

        return buffer.toString();
    }

    // 使json-lib来进行json到map的转换，fastjson有排序问题，不能用
    public static Map<String, String> jsonToMap(JSONObject json) {
        Map<String, String> map = new HashMap<>();
        for (Object key : json.keySet()) {
            String value = json.getString((String) key);
            map.put((String) key, value);
        }
        return map;
    }

    // 构建签名字符串
    private static String buildSignString(Map<String, String> params) {

        if (params == null || params.size() == 0) {
            return "";
        }

        List<String> keys = new ArrayList<>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key))
                continue;
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            keys.add(key);
        }

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }

        return buf.toString();
    }

    // 根据编码类型获得签名内容byte[]
    private static byte[] getContentBytes(String content) {
        try {
            return content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误");
        }
    }

    public static void removeNullEntry(Map<String, Object> map) {
        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        for (; it.hasNext();) {
            Entry<String, Object> i = it.next();
            if (i.getValue() instanceof String && StringUtil.isBlank((String) i.getValue())) {
                it.remove();
            }
        }
    }

}
