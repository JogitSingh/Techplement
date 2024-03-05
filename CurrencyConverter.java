import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder; // Import URLEncoder
import org.json.JSONObject;

public class CurrencyConverter {
    private static final String API_BASE_URL = "https://api.exchangerate.host/latest";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("1. View favorite currencies");
            System.out.println("2. Add favorite currency");
            System.out.println("3. Update favorite currency");
            System.out.println("4. Convert currency");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewFavorites();
                    break;
                case 2:
                    addFavorite();
                    break;
                case 3:
                    updateFavorite();
                    break;
                case 4:
                    convertCurrency();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void viewFavorites() {
        // Implement code to view favorite currencies
    }

    private static void addFavorite() {
        // Implement code to add favorite currency
    }

    private static void updateFavorite() {
        // Implement code to update favorite currency
    }

    private static void convertCurrency() {
        try {
            System.out.print("Enter base currency (e.g., USD): ");
            String baseCurrency = scanner.next();
            System.out.print("Enter target currency (e.g., EUR): ");
            String targetCurrency = scanner.next();
            
            // Encode URL parameters
            String encodedBaseCurrency = URLEncoder.encode(baseCurrency, "UTF-8");
            String encodedTargetCurrency = URLEncoder.encode(targetCurrency, "UTF-8");
            
            // Construct the URL
            URL url = new URL(API_BASE_URL + "?base=" + encodedBaseCurrency + "&symbols=" + encodedTargetCurrency);
            
            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                JSONObject jsonResponse = new JSONObject(response.toString());
                double rate = jsonResponse.getJSONObject("rates").getDouble(targetCurrency);
                System.out.println("1 " + baseCurrency + " = " + rate + " " + targetCurrency);
            } else {
                System.out.println("Failed to fetch data. Response code: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}