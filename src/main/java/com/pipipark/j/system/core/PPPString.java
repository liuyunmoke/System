package com.pipipark.j.system.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.annotation.PPPName;

/**
 * 字符串辅助工具类.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public abstract class PPPString implements IPPPark {
	
	/**
	 * 分割字符串,
	 * 根据有效字符切割.
	 * PPPString.split(null)       = null
     * PPPString.split("")         = []
     * PPPString.split("abc def")  = ["abc", "def"]
     * PPPString.split("abc  def") = ["abc", "def"]
     * PPPString.split(" abc ")    = ["abc"]
	 * @param content
	 * @return array, if content is null will return null;
	 */
	public static final String[] split(String content){
		return StringUtils.split(content);
	}
	
	/**
	 * 分割字符串,
	 * 根据指定字符切割.
	 * <pre>
     * PPPString.split(null, *)         = null
     * PPPString.split("", *)           = []
     * PPPString.split("abc def", null) = ["abc", "def"]
     * PPPString.split("abc def", " ")  = ["abc", "def"]
     * PPPString.split("abc  def", " ") = ["abc", "def"]
     * PPPString.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
	 * @param content
	 * @param separator
	 * @return array if content is null will return null
	 */
	public static final String[] split(String content, String separator){
		return StringUtils.split(content, separator);
	}
	
	/**
	 * 分割字符串,
	 * 根据字符类型切割.
	 * <pre>
	 * PPPString.splitByType(null)         = null
     * PPPString.splitByType("")           = []
     * PPPString.splitByType("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * PPPString.splitByType("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * PPPString.splitByType("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * PPPString.splitByType("number5")    = ["number", "5"]
     * PPPString.splitByType("fooBar")     = ["foo", "Bar"]
     * PPPString.splitByType("foo200Bar")  = ["foo", "200", "Bar"]
     * PPPString.splitByType("ASFRules")   = ["ASF", "Rules"]
	 * </pre>
	 * @param content
	 * @param separator
	 * @return array if content is null will return null;
	 */
	public static final String[] splitByType(String content){
		return StringUtils.splitByCharacterTypeCamelCase(content);
	}
	
	/**
	 * 判断字符串内容,
	 * 是否为空或者内容长度为零(不包括空格).
	 * @param str
	 * @return Boolean
	 */
	public static final Boolean isEmpty(String str){
		return str == null || str.length() == 0;
	}
	
	/**
	 * 判断字符串内容,
	 * 是否为空或者内容长度为零(去除前后空格).
	 * @param str
	 * @return Boolean
	 */
	public static final Boolean isRealEmpty(String str){
		return isEmpty(str) || isEmpty(str.trim());
	}

	/**
	 * 获得某个Class的类名称.
	 * <pre>
	 * com.pipipark.j.system.tool.string.PPPString => "PPPString".
	 * </pre>
	 * @param clazz
	 * @return String
	 */
	public static final String className(Class<?> clazz){
		String name = clazz.getName();
		int index = name.lastIndexOf(".");
		return name.substring(index+1, name.length());
	}
	public static final String className(Object obj){
		return className(obj.getClass());
	}
	
	
	/**
	 * 获得某个Class的别名.
	 * <pre>
	 * @PPPName("users")
	 * public class User{}
	 * 
	 * aliasName(User.class) =====> "users"
	 * </pre>
	 * @param clazz
	 * @return String
	 */
	public static final String aliasName(Class<?> clazz){
		PPPName sn = clazz.getAnnotation(PPPName.class);
		String name;
		if(sn!=null && !PPPString.isRealEmpty(sn.value())){
			name = sn.value();
		}else{
			name = PPPString.lowFirst(PPPString.className(clazz));
		}
		return name;
	}
	public static final String aliasName(Object obj){
		return aliasName(obj.getClass());
	}
	
	
	/**
	 * 字符串MD5加密.
	 * @param text
	 * @return String
	 */
	public static final String md5(String text){
		try {
			if (text == null)
				return "";
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));

			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String[] md5s(String... param){
		String[] md5Tags = new String[param.length];
		for (int i=0;i<param.length;i++) {
			md5Tags[i] = md5(param[i]);
		}
		return md5Tags;
	}
	
	/**
	 * 大写第一个字母.
	 * <pre>
	 * PPPString.upFirst("chinese")  =  "Chinese"
	 * </pre>
	 * @param str
	 */
	public static String upFirst(String str){
		return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toUpperCase()) ;
	}
	
	/**
	 * 小写第一个字母.
	 * <pre>
	 * PPPString.lowFirst("HELLO")  =  "hELLO"
	 * </pre>
	 * @param str
	 */
	public static String lowFirst(String str){
		return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toLowerCase()) ;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(PPPString.split("abs.java"));
	}
}
