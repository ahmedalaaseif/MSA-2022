package ArangoDB;

import com.arangodb.ArangoDB;
import com.arangodb.entity.EdgeDefinition;
import com.arangodb.model.GraphCreateOptions;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class ArangoGraphs {
    public static void main(String[] args) {
    Collection<EdgeDefinition> edgeDefinitions = new ArrayList<>();
    // define the edgeCollection to store the edges
    EdgeDefinition edgeDefinition = new EdgeDefinition().collection("myEdgeCollection").from("myCollection1", "myCollection2").to("myCollection1", "myCollection3");
    // define a set of collections where an edge is going out...
        edgeDefinition.from("myCollection1", "myCollection2");

    // repeat this for the collections where an edge is going into
        edgeDefinition.to("myCollection1", "myCollection3");

        edgeDefinitions.add(edgeDefinition);
    // A graph can contain additional vertex collections, defined in theic void main(String[] args) {


        InputStream in = ArangoDocuments.class.getResourceAsStream("arango.properties");
        ArangoDB arangoDB = new ArangoDB.Builder().loadProperties(in).build();

        // set of orphan collections
        GraphCreateOptions options = new GraphCreateOptions();
        options.orphanCollections("myCollection4", "myCollection5");

        // now it's possible to create a graph
        arangoDB.db("myDatabase").createGraph("myGraph", edgeDefinitions, options);


        MyObject myObject1 = new MyObject("Homer", 38);
        MyObject myObject2 = new MyObject("Marge", 36);
        arangoDB.db("myDatabase").graph("myGraph").vertexCollection("collection1").insertVertex(myObject1, null);
        arangoDB.db("myDatabase").graph("myGraph").vertexCollection("collection3").insertVertex(myObject2, null);
        MyEdge edge = new MyEdge(true);
        arangoDB.db("myDatabase").graph("myGraph").edgeCollection("myEdgeCollection").insertEdge(edge, null);
    }

}

class MyEdge {

private boolean married;
    public MyEdge(boolean married) {
        this();
        this.married = married;
    }

    public MyEdge() {
        super();
    }

}
