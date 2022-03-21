package Cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

public class Cassandra {

    public static void main(String[] args) {
        CqlSession session = null;
        try {
            CqlSessionBuilder builder = CqlSession.builder();
            builder.addContactPoint(new InetSocketAddress("127.0.0.1",9042));
            session = builder.build();


            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            System.out.println(row.getString("release_version"));

            String keySpace = "sensors";
            String replicationStrategy = "SimpleStrategy";
            String replicationFactor = "1";

            StringBuilder sb =
                    new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
                            .append(keySpace).append(" WITH replication = {")
                            .append("'class':'").append(replicationStrategy)
                            .append("','replication_factor':").append(replicationFactor)
                            .append("};");

            String query = sb.toString();
            session.execute(query);

            session.execute("USE " + keySpace + ";");

            String TABLE_NAME = "temperature";
            sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                    .append(keySpace + "." + TABLE_NAME).append("(")
                    .append("id int PRIMARY KEY, ")
                    .append("reading double")
                    .append(");");

            session.execute(sb.toString());

            ResultSet result = session.execute("SELECT * FROM " + keySpace + "." + TABLE_NAME + ";");

            result.getColumnDefinitions().forEach(cl -> System.out.println(cl.getName()));

            sb = new StringBuilder("INSERT INTO ")
                    .append(keySpace)
                    .append(".")
                    .append(TABLE_NAME).append("(id,reading)")
                    .append(" VALUES(").append("2").append(",").append("2.8")
                    .append(");");

            session.execute(sb.toString());
        } finally {
            if (session != null) session.close();
        }
    }
}
