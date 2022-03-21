package ArangoDB;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class ArangoDocuments {
    public static void main(String[] args) throws Exception {
        String rootPath = "src/main/resources/";
        InputStream in = new FileInputStream(rootPath + "arango.properties");
        //        System.out.println(System.getProperty("user.dir"));
//        ArangoDB arangoDB = new ArangoDB.Builder().loadProperties(in).build();
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("123").build();
        String database = "demo";
        try {
            arangoDB.createDatabase(database);
            arangoDB.db(database).createCollection("myCollection", null);
            MyObject myObject = new MyObject("Homer", 38);
            arangoDB.db(database).collection("myCollection").insertDocument(myObject);
        } catch (Exception e) {
            e.printStackTrace();

        }


        try {
            String query = "FOR t IN myCollection FILTER t.name == @name RETURN t";
            Map<String, Object> bindVars = new MapBuilder().put("name", "Homer").get();
            ArangoCursor<BaseDocument> cursor = arangoDB.db(database).query(query, bindVars, null,
                    BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                System.out.println("Key: " + aDocument.getKey());
            });
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }
    }
}

class MyObject {

    private String key;
    private String name;
    private int age;

    public MyObject(String name, int age) {
        this();
        this.name = name;
        this.age = age;
    }

    public MyObject() {
        super();
    }

}
