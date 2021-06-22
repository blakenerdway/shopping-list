package shoppinglist.beam.products.pipelines.debug;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Blake Ordway (bordway@ihmc.us) on 6/20/2021
 */
@RunWith(JUnit4.class)
public class WalmartTest
{

    @Test
    public void testWalmartParse() throws Exception{
        Gson gson = new GsonBuilder().serializeNulls().create();
        Reader jsonReader = Files.newBufferedReader(Paths.get("walmart-test-1.json"));
        List<?> lists = gson.fromJson(jsonReader, List.class);
        List<String> singleJsons = new ArrayList<>();
        for (Object jsonObj : lists) {
            String res = gson.toJson(jsonObj);
            singleJsons.add(res);
        }
        // close reader
        jsonReader.close();

        WalmartDebug.runDebuggingWalmartPipeline(singleJsons);

    }
    private static final Logger _logger = LoggerFactory.getLogger(WalmartTest.class);
}
