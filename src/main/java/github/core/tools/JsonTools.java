package github.core.tools;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class JsonTools {

    public Map<Object,Object> getMapFromJSONFile(String filePath,  Gson gson) {
        Map<Object,Object> map = new HashMap<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            map = gson.fromJson(reader, Map.class);
            reader.close();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return map;
    }

    public JSONObject getJSONObjectFromFile(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return new JSONObject(content);
    }


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

    public String getJSNONValueByKey(JSONObject jsonObject, String concurrentKey) {
        Iterator iterator = jsonObject.keys();
        String value= "";
        String key;

        while (iterator.hasNext()) {
            key = (String) iterator.next();
            if ((jsonObject.optJSONObject(key)
                    == null) && (jsonObject.optJSONArray(key)
                    == null)) {
                if (key.equals(concurrentKey)) {
                    jsonObject.get(key);
                    iterator = jsonObject.keys();
                }
            }

            if (jsonObject.optJSONObject(key)
                    != null) {
                getJSNONValueByKey(jsonObject.getJSONObject(key)
                        , concurrentKey);
            }

            if (jsonObject.optJSONArray(key)
                    != null) {
                JSONArray jsonArray = jsonObject.getJSONArray(key)
                        ;
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.get(i) instanceof JSONObject) {
                        getJSNONValueByKey(jsonArray.getJSONObject(i), concurrentKey);
                    }
                }
            }
        }
        return value;
    }
}
