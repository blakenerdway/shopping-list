package shoppinglist.beam.products.pipelines.debug;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import shoppinglist.beam.products.pipelines.debug.TargetDebug;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class TargetTest {

   @Test
   public void testTargetParse() throws Exception{
      Gson gson = new Gson();
      Reader jsonReader = Files.newBufferedReader(Paths.get("target-example.json"));
      List<?> lists = gson.fromJson(jsonReader, List.class);
      List<String> singleJsons = new ArrayList<>();
      for (Object jsonObj : lists) {
         String res = gson.toJson(jsonObj);
         singleJsons.add(res);
      }

      TargetDebug.runDebuggingTargetPipeline(singleJsons);
      // close reader
      jsonReader.close();
   }
}
