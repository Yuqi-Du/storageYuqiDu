package bad_status_code_errors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test1 {

    public static void main(String[] args) {
        int numberOfThreads = 10;

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new APITestTask(i));
            thread.start();
        }
    }

    static class APITestTask implements Runnable {
        private final int threadNumber;

        public APITestTask(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                String API_URL = "https://4d32e377-4e47-454f-aa54-3b7197a83bce-us-west-2.apps.astra-dev.datastax.com:443/api/json/v1/default_keyspace/testTable";
//                String jsonPayload = "{ \"findOne\": {\"filter\": {\"_id\" : \"e3f68f5b-6f6c-4e24-bd13-5a1acefd9e28\"}}}";
                String jsonPayload = "{ \"find\": {\"filter\": {\"name\": \"Coded Cleats\"}}}";

                try {
                    URL url = new URL(API_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Set up the connection for a POST request
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("x-cassandra-token","AstraCS:MQogsfOpJhOmSBNhwXqKSfbk:aea3bbe705a9bb976626a6b88f8735296bc2a633b913fd392d8a88e125f1cf72");
                    connection.setDoOutput(true);

                    // Send the JSON payload
                    try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                        outputStream.writeBytes(jsonPayload);
                        outputStream.flush();
                    }

                    // Get the response from the API
                    int responseCode = connection.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    System.out.println("Response Code: " + responseCode);
                    System.out.println("Response Body: " + response.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
