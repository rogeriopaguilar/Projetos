package dnsec.shared.util;

public class StringUtil {

	
	
	public static String NullToStr(String str){
		if(str == null)
		{
			str = "";
		}
		return str;
	}

	
	public static String NullToStrTrim(String str){
		if(str == null)
		{
			str = "";
		}
		return str.trim();
	}

	public static String NullToStrUpper(String str){
		if(str == null)
		{
			str = "";
		}
		return str.toUpperCase();
	}

	public static String NullToStrUpperTrim(String str){
		if(str == null)
		{
			str = "";
		}
		return str.trim().toUpperCase();
	}

	
}
