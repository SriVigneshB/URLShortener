package demo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
public class Demo {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		Map<String,String> m=new HashMap<>();
		Map<String, Integer> mp = new HashMap<>();
		int ch;
	       do{
	    	System.out.println("--------------------URL SHORTERNER-------------------");
	        System.out.println("  -------------------------------------------------");
	        System.out.println("\t\t\t1.Create URL \n\t\t\t2.View Short Url\n\t\t\t3.View Count\n\t\t\t4.Stop");
	        System.out.println("  -------------------------------------------------");
	        ch=sc.nextInt();
	        switch(ch){
	        case 1:
	        	 System.out.println("Enter your long url");
	             String longUrl=sc.next();
	             if(m.containsKey(longUrl)) {
	            	 System.out.println(m.get(longUrl));
	            	 mp.put(longUrl,mp.get(longUrl)+1);
	             }
	             else {
	            	 String shortUrl = shortenUrl(longUrl);
	            	 m.put(longUrl, shortUrl);
	            	 mp.put(longUrl,1);
	                 System.out.println("Short URL: " + shortUrl);
	             }
	             break;
	        case 2:
	        	System.out.println(m);
	        	break;
	        
	        case 3:
	        	System.out.println(mp);
	        	break;
		        
	        }
    }while(ch!=4);
	       sc.close();
}
    public static String shortenUrl(String longUrl) {
        try {
            URL url = new URL("http://tinyurl.com/api-create.php?url=" + longUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                System.out.println("Error in shortening URL. Response Code: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
