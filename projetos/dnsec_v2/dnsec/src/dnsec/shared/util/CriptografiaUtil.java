package dnsec.shared.util;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;
public final class CriptografiaUtil
{
  private static CriptografiaUtil instance;
  
  private CriptografiaUtil()
  {
  }

  public synchronized String encrypt(String plaintext) throws Exception
  {
    MessageDigest md = null;
    md = MessageDigest.getInstance("SHA");
    md.update(plaintext.getBytes("UTF-8"));
    byte raw[] = md.digest();
    String hash = (new BASE64Encoder()).encode(raw);
    return hash;
  }

  public static synchronized CriptografiaUtil getInstance() 
  {
    if(instance == null)
    {
      return new CriptografiaUtil();
    }
    else    
    {
      return instance;
    }
  }
}