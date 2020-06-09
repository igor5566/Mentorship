package github.core.tools;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class JsonTools {

    public JSONObject removeJsonAttr(JSONObject jsonObject, String concurrentKey, Object concurrentValue) {
        Iterator iterator = jsonObject.keys();
        String key;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            if ((jsonObject.optJSONObject(key)
                    == null) && (jsonObject.optJSONArray(key)
                    == null)) {
                if ((key.equals(concurrentKey)) && (jsonObject.get(key).equals(concurrentValue))) {
                    jsonObject.remove(key)
                    ;
                    iterator = jsonObject.keys();
                }
            }

            if (jsonObject.optJSONObject(key)
                    != null) {
                removeJsonAttr(jsonObject.getJSONObject(key)
                        , concurrentKey, concurrentValue);
            }

            if (jsonObject.optJSONArray(key)
                    != null) {
                JSONArray jsonArray = jsonObject.getJSONArray(key)
                        ;
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.get(i) instanceof JSONObject) {
                        removeJsonAttr(jsonArray.getJSONObject(i), concurrentKey, concurrentValue);
                    }
                }
            }
        }
        return jsonObject;
    }

    public JSONObject updateJson(JSONObject jsonObject, String concurrentKey, Object concurrentValue, Object newValue) {
        Iterator iterator = jsonObject.keys();
        String key;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            if ((jsonObject.optJSONObject(key)
                    == null) && (jsonObject.optJSONArray(key)
                    == null)) {
                if ((key.equals(concurrentKey)) && (jsonObject.get(key).equals(concurrentValue))) {
                    jsonObject.put(key, newValue);
                }
            }

            if (jsonObject.optJSONObject(key)
                    != null) {
                updateJson(jsonObject.getJSONObject(key)
                        , concurrentKey, concurrentValue, newValue);
            }

            if (jsonObject.optJSONArray(key)
                    != null) {
                JSONArray jsonArray = jsonObject.getJSONArray(key)
                        ;
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.get(i) instanceof JSONObject) {
                        updateJson(jsonArray.getJSONObject(i), concurrentKey, concurrentValue, newValue);
                    }
                }
            }
        }
        return jsonObject;
    }
}
