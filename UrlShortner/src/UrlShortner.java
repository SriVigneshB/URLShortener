import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlShortner {
    public static void main(String[] args) {
        String longUrl = "https://www.example.com/very/long/url";
        String shortUrl = shortenUrl(longUrl);
        System.out.println("Short URL: " + shortUrl);
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
