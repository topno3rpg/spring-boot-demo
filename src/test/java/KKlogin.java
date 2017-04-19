import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class KKlogin {
	
	private static char[] HEX_TAB_WEB = "s~0!e@5#c$8%r^6&".toCharArray();
	
	private static char[] HEX_TAB_WEB1 = "K2DEZMIU7OP13YJ8".toCharArray();
	
	public static String S_PRIVATE_KEY = "cc16be4b:346c51d";

	public static void main(String[] args) throws Exception {
//		String url = "https://sapi.kktv1.com/meShow/entrance?parameter={%22roomId%22:79853182,%22sendId%22:%22A946489E226BBB0D7743D0CCBBC1AE9E%22,%22FuncTag%22:40000018,%22userId%22:109574811,%22token%22:%22A28C6EB03051EA4A6DA17A89142107A678%22,%22platform%22:1,%22a%22:1,%22c%22:100101,%22sv%22:%2260~rs0s~~r%26c%268%2688s%22}";
//		url = URLDecoder.decode(url, "UTF-8");
//		System.out.println(url);
		JsonObject json = new JsonObject();
		json.addProperty("roomid", 79853182);;
		json.addProperty("sendId", "A946489E226BBB0D7743D0CCBBC1AE9E");
		json.addProperty("FuncTag", 40000018);
		json.addProperty("userId", 109574811);
		json.addProperty("token", "A28C6EB03051EA4A6DA17A89142107A678");
		json.addProperty("platform", 1);
		json.addProperty("a", 1);
		json.addProperty("c", 100101);
		json.addProperty("sv", "60~rs0s~~r&c&8&88s");
		JsonObject obj = checkSignedValue(json);
		System.out.println(obj);
	}
	
	
	public static JsonObject checkSignedValue(JsonObject jsonObject) throws Exception
	   {
	     JsonElement platformje = jsonObject.get("platform");
	     int platform = -1;
	     if (platformje != null) {
	       try {
	         platform = platformje.getAsInt();
	       }
	       catch (Exception e) {}
	     }
	     
	     if ((platform == -1) || (platform > 4))
	     {
	       JsonObject result = new JsonObject();
	       result.addProperty("TagCode", "40010001");
	       return result;
	     }
	     
	     Set<Map.Entry<String, JsonElement>> es = jsonObject.entrySet();
	    Iterator<Map.Entry<String, JsonElement>> ies = es.iterator();
	    
	    String sv = null;
	    HashMap<String, Object> params = new HashMap();
	    while (ies.hasNext()) {
	      Map.Entry<String, JsonElement> e = (Map.Entry)ies.next();
	      if (((String)e.getKey()).equalsIgnoreCase("sv")) {
	        sv = ((JsonElement)e.getValue()).getAsString();
	      } else
	        params.put(e.getKey(), ((JsonElement)e.getValue()).getAsString());
	    }
	    if ((sv == null) || (params.size() == 0))
	    {
	      JsonObject result = new JsonObject();
	      result.addProperty("TagCode", "40010002");
	      return result;
	    }
	    
	    boolean b = false;
	    
	    if (platform == 1)
	    {
	      b = cslist_web(params, sv);
	    }
	   /* else
	    {
	      b = cslist(params, sv);
	    }*/
	    if (!b)
	    {
	      JsonObject result = new JsonObject();
	      result.addProperty("TagCode", "40010002");
	      return result;
	    }
	    
	    return null;
	  }
	
	public static boolean cslist_web(Map<String, Object> l, String s) {
	     if ((s == null) || (s.isEmpty()))
	       return false;
	     if (false == cs_web(s))
	       return false;
	     String sm = slist_web(l);
	     if ((sm == null) || (sm.isEmpty()))
	       return false;
	     s = s.substring(0, s.length() - 2);
	     if (sm.compareTo(s) == 0)
	       return true;
	     return false;
	   }
	
	/*public static boolean cslist(Map<String, Object> l, String s) {
	     if ((s == null) || (s.isEmpty()))
	       return false;
	     String sm = slist(l);
	     if ((sm == null) || (sm.isEmpty()))
	       return false;
	     if (sm.compareTo(s) == 0)
	       return true;
	     return false;
	   }*/

	public static boolean cs_web(String s) {
	     if (s.length() < 2)
	       return false;
	     int sum = 0;
	     for (int i = 0; i < s.length() - 2; i += 2) {
	       sum += s.charAt(i);
	     }
	     
	     char a = HEX_TAB_WEB[(sum % 16)];
	     char b = HEX_TAB_WEB[(sum % 13)];
	     if ((a == s.charAt(s.length() - 2)) && (b == s.charAt(s.length() - 1)))
	     {
	       return true; }
	     return false;
	   }
	
	public static String slist_web(Map<String, Object> l)
	   {
	     if ((l == null) || (l.size() == 0))
	       return null;
	     try {
	       Set<String> ks = l.keySet();
	       
	       String[] kss = new String[ks.size()];
	       ks.toArray(kss);
	       Arrays.sort(kss, String.CASE_INSENSITIVE_ORDER);
	       
	 
	       String stos = "";
	       for (int i = 0; i < kss.length; i++) {
	         Object o = l.get(kss[i]);
	         if (o != null) {
	           stos = stos + o.toString();
	         }
	       }
	       if (stos.length() < 8) {
	         stos = stos + "0123456789012345";
	       }
	       
	       int[] s1 = new int[8];
	       for (int i = 0; i < stos.length() / 8 - 1; i++) {
	         for (int j = 0; j < 8; j++) {
	           if ((i + 1) * 8 + j < stos.length()) {
	             if (i == 0)
	               s1[j] = stos.charAt(i * 8 + j);
	             s1[j] ^= stos.charAt((i + 1) * 8 + j);
	           }
	         }
	       }
	       
	 
	       String s0 = "";
	       for (int i = 0; i < 8; i++) {
	         s0 = s0 + HEX_TAB_WEB[(s1[i] >>> 3 & 0xF)];
	         s0 = s0 + HEX_TAB_WEB[(s1[i] & 0xF)];
	       }
	       return s0;
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	     return null;
	   }
	
	/*public static String slist(Map<String, Object> l)
	  {
	    if ((l == null) || (l.size() == 0))
	      return null;
	    try {
	      Set<String> ks = l.keySet();
	      
	      String[] kss = new String[ks.size()];
	      ks.toArray(kss);
	      Arrays.sort(kss, String.CASE_INSENSITIVE_ORDER);
	      





	      String stos = "";
	      for (int i = 0; i < kss.length; i++) {
	        stos = stos + kss[i] + ":";
	        Object o = l.get(kss[i]);
	        if (o != null) {
	          stos = stos + o.toString();
	        }
	      }
	      

	      stos = stos + S_PRIVATE_KEY;
	      

	      MessageDigest md = MessageDigest.getInstance("MD5");
	      md.update(stos.getBytes());
	      byte[] tmp = md.digest();
	      

	       byte[] rs = new byte[26];
	       BytesTransformer bts = new BytesTransformer(BytesTransformer.EBT_FOR_SIGN);
	       if (bts.encode(tmp, tmp.length, rs, 26))
	         return new String(rs);
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	     return null;
	   }*/
	
	public static String decodeUserNameAndPassword(JsonObject jsonObject)
	   {
	     JsonElement platformje = jsonObject.get("platform");
	     int platform = -1;
	     if (platformje != null) {
	       try {
	         platform = platformje.getAsInt();
	       }
	       catch (Exception e) {}
	     }
	     
	     if ((platform == -1) || (platform > 4))
	     {
	       return null;
	     }
	     
	     String upDecoded = null;
	     JsonElement up = jsonObject.get("up");
	     if (up != null)
	     {
	       if (platform == 1)
	       {
	         upDecoded = upd_web(up.getAsString());
	       }
	       /*else
	       {
	         upDecoded = upd(up.getAsString());
	       }*/
	     }
	     
	     return upDecoded;
	   }
	
	public static String upd_web(String up) {
	     if ((up == null) || (up.length() < 4) || (up.length() % 2 != 0))
	       return null;
	     int len = up.length() / 2;
	     char[] s1 = new char[len];
	     
	     int j = 0;
	     for (int i = 0; i + 1 < up.length(); i += 2) {
	       int n = getUPIndexOf(up.charAt(i));
	       int m = getUPIndexOf(up.charAt(i + 1));
	       if ((n == -1) || (m == -1))
	         return null;
	       s1[(j++)] = ((char)(n << 4 | m));
	     }
	     int sum = s1[(len - 1)];
	     for (int i = 0; i < len - 1; i++) {
	       int tmp128_126 = i; char[] tmp128_125 = s1;tmp128_125[tmp128_126] = ((char)(tmp128_125[tmp128_126] ^ sum));
	     }
	     String s2 = new String(s1);
	     s2 = s2.substring(0, s2.length() - 1);
	     return s2;
	   }
	
	private static int getUPIndexOf(char a) {
	     int n = -1;
	     for (int j = 0; j < 16; j++) {
	       if (a == HEX_TAB_WEB1[j]) {
	         n = j;
	         break;
	       }
	     }
	     return n;
	   }
}
