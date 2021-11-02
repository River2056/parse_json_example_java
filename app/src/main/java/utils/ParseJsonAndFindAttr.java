package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

public class ParseJsonAndFindAttr {
  
  public static void main(String[] args) throws IOException {
    // get properties to look for
    String jsonConfig = readFileIntoString("config.json");
    JSONObject config = new JSONObject(jsonConfig);
    List<Object> attrs = new JSONArray(config.get("attrs").toString()).toList();

    // read input and parse
    String jsonFileContents = readFileIntoString("input.json");

    JSONObject jsonObj = new JSONObject(jsonFileContents.toString());

    for(int i = 0; i < attrs.size(); i++) {
      lookForJsonAttr(jsonObj, attrs.get(i).toString());
    }
  }

  private static String readFileIntoString(String path) throws IOException {
    InputStream is = ClassLoader.getSystemResourceAsStream(path);
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    StringBuilder contents = new StringBuilder();
    String line = null;

    while((line = br.readLine()) != null) {
      contents.append(line);
    }

    return contents.toString();
  }

  private static void lookForJsonAttr(JSONObject jsonObj, String attr) {
    Iterator<Entry<String, Object>> it = jsonObj.toMap().entrySet().iterator();
    while(it.hasNext()) {
      Entry<String, Object> entry = it.next();
      if(entry.getKey().equals(attr)) {
        System.out.println(String.format("%s: %s", entry.getKey(), entry.getValue()));
      }

      if(HashMap.class.equals(entry.getValue().getClass())) {
        JSONObject innerObj = new JSONObject((Map)entry.getValue());
        lookForJsonAttr(innerObj, attr);
      }

      if(ArrayList.class.equals(entry.getValue().getClass())) {
        List<Object> innerList = (List<Object>) entry.getValue();
        for(int i = 0; i < innerList.size(); i++) {
          Object element = innerList.get(i);
          if(element instanceof Map) {
            JSONObject subMap = new JSONObject((Map)element);
            lookForJsonAttr(subMap, attr);
          }
        }
      }
    }
  }
}
