package deraco.assignment.cities.util;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import deraco.assignment.cities.model.City;

/**
 * Created by giuseppederaco on 16/03/2018.
 */

public class Utilities {

    private static String TAG = JsonParser.class.getSimpleName();

    public static City[] readCities(AssetManager assetManager) {
        InputStream is;
        StringBuilder total = null;
        try {
            is = assetManager.open("cities.json");
        } catch (IOException e) {
            Log.e(TAG, "Could not find the file cities.json");
            return null;
        }

        if (is != null) {
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            total = new StringBuilder();
            String line;
            try {
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
            } catch (IOException e) {
                Log.e(TAG, "Something went wrong while reading the cities.json: " + e.getMessage());
                return null;
            }
        }
        if (total != null) {
            String parsedJson = total.toString();
            return new Gson().fromJson(parsedJson, City[].class);
        }
        return null;
    }
}
