package br.com.danieljunior.givemearecipe.utils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by danieljunior on 20/09/16.
 */
public class JSONUtils {

    public static JSONArray parseToJSONArray(String content) throws JSONException {
        return new JSONArray(content);
    }
}
