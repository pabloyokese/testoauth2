package customerservice;

import java.nio.charset.Charset;
import java.util.Base64;

public class Test {
	  public static void main(String argsp[]){
	        //String text = "trusted-client:secret";
	        String text = "clientId:clientSecret";
	        String newString= "Basic " + new String(Base64.getEncoder().encode(
	                text.getBytes(Charset.forName("US-ASCII"))));
	        System.out.println(newString);
	    }
}
