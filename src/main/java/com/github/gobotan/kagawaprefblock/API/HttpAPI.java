package com.github.gobotan.kagawaprefblock.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpAPI {
    public static JSONObject getWhois(String IP) throws MalformedURLException {
        HttpURLConnection connection = null;
        StringBuilder builder = new StringBuilder();
        URL url = new URL("http://ip-api.com/json/"+ IP + "?fields=status,country,regionName");
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();

                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bff = new BufferedReader(isr);

                String line = null;
                while ((line = bff.readLine()) != null) {
                    builder.append(line);
                }

                bff.close();
                isr.close();
                is.close();
            } else {
                System.out.println("そのホームページは存在しないか予期せぬエラーが発生しています。");
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return new JSONObject(builder.toString());
    }
}
