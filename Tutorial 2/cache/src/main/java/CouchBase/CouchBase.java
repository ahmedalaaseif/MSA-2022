package CouchBase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;

import java.util.Random;

public class CouchBase {
    public static void main(String[] ags) {

        Cluster cluster = Cluster.connect("127.0.0.1", "ahmed", "123456");
        Bucket bucket = cluster.bucket("employee");

        int id = new Random().nextInt();
        System.out.println("ID "+ id);
        JsonObject user = JsonObject.create()
                .put("firstname", "Walter")
                .put("lastname", "White")
                .put("job", "chemistry teacher")
                .put("age", 50);



        MutationResult response = bucket.defaultCollection().upsert(id+"", user);
        System.out.println(response.toString());

        cluster.disconnect();
    }

}
