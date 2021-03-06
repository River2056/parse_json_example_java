/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package utils;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

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

class AppTest {
    @Test
    void testMapToJsonObject() {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("name", "test");

      JSONObject json = new JSONObject(map);
      System.out.println(json);
    }

    @Test
    void testList() throws IOException {
      InputStream is = ClassLoader.getSystemResourceAsStream("input.json");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder contents = new StringBuilder();
      String line = null;

      while((line = br.readLine()) != null) {
        contents.append(line);
      }

      JSONObject json = new JSONObject(contents.toString());
      Iterator<Entry<String, Object>> it = json.toMap().entrySet().iterator();
      // while(it.hasNext()) {
      //   Entry<String, Object> entry = it.next();
      //   System.out.println(entry.getKey().getClass());
      //   System.out.println(entry.getValue().getClass());
      // }
      while(it.hasNext()) {
        Entry<String, Object> entry = it.next();
        if(ArrayList.class.equals(entry.getValue().getClass())) {
          List<Object> innerList = (List<Object>) entry.getValue();
          for(int i = 0; i < innerList.size(); i++) {
            Object element = innerList.get(i);
            if(element instanceof Map) {
              
            }
          }
        }
      }
    }
}
