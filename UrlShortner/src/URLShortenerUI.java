package demo;

import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class URLShortenerUI extends JFrame {

    private JLabel longUrlLabel;
    private JTextField longUrlField;
    private JButton createButton;
    private JButton viewShortUrlsButton;
    private JButton viewCountButton;
    private JTextArea outputArea;
    private Map<String, String> shortUrls;
    private Map<String, Integer> clicks;

    public URLShortenerUI() {
        super("URL Shortener");

        shortUrls = new HashMap<>();
        clicks = new HashMap<>();

        longUrlLabel = new JLabel("Enter your long URL: ");
        longUrlField = new JTextField(30);
        
        createButton = new JButton("Create Short URL");
        createButton.setMargin(new Insets(10, 10, 30, 10));
        viewShortUrlsButton = new JButton("View Short URLs");
        viewCountButton = new JButton("View Clicks Count");
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        createButton.addActionListener(new CreateButtonListener());
        viewShortUrlsButton.addActionListener(new ViewShortUrlsButtonListener());
        viewCountButton.addActionListener(new ViewCountButtonListener());

        JPanel inputPanel = new JPanel();
        inputPanel.add(longUrlLabel);
        inputPanel.add(longUrlField);
        inputPanel.add(createButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewShortUrlsButton);
        buttonPanel.add(viewCountButton);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, "North");
        add(buttonPanel, "Center");
        add(scrollPane, "South");

        setSize(500, 350);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class CreateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String longUrl = longUrlField.getText();
            if (shortUrls.containsKey(longUrl)) {
                outputArea.append("Short URL: " + shortUrls.get(longUrl) + "\n");
                clicks.put(longUrl, clicks.get(longUrl) + 1);
            } else {
                String shortUrl = shortenUrl(longUrl);
                if (shortUrl != null) {
                    shortUrls.put(longUrl, shortUrl);
                    clicks.put(longUrl, 1);
                    outputArea.append("Short URL: " + shortUrl + "\n");
                } else {
                    outputArea.append("Error in shortening URL.\n");
                }
            }
        }
    }

private class ViewShortUrlsButtonListener implements ActionListener {
public void actionPerformed(ActionEvent e) {
outputArea.append("Short URLs:\n");
for (Map.Entry<String, String> entry : shortUrls.entrySet()) {
outputArea.append(entry.getKey() + " -> " + entry.getValue() + "\n");
}
}
}
private class ViewCountButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        outputArea.append("Clicks Count:\n");
        for (Map.Entry<String, Integer> entry : clicks.entrySet()) {
            outputArea.append(entry.getKey() + " -> " + entry.getValue() + "\n");
        }
    }
}
private String shortenUrl(String longUrl) {
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

public static void main(String[] args) {
    new URLShortenerUI();
}
}


