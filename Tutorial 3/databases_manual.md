# Databases

----

## SQL

The first generation of data storage systems, data is stored as records on the disk. SQL is used to store data that should be persistent and is not updated frequently.

### PostgreSQL
PostgreSQL can be downloaded from the linux distribution's package manager. MacOS users can install it from brew as follows:

```sh
brew install postgresql
```

Additionally, the *services* package from brew is needed in order to integrate startup scripts with *Launchd*, MacOS default service manager:

```sh
brew tap homebrew/services
```

To list available services:

```sh
brew services list
```

To have a service startup at each login:

```sh
brew start <SERVICE>
```

To simply run the service, without auto-launching on each login:
```sh
brew run <SERVICE>
```

#### Setting Up PostgreSQL
To setup PostgreSQL, make sure that the server is not running then login as postgres user as follows:
```sh
sudo -u postgres -i
```

initialize the data directory

```sh
initdb --locale $LANG -E UTF8 -D '/var/lib/postgres/data'
```

start the server, on Linux as a normal user, run:

```sh
sudo systemctl enable postgresql
sudo systemctl start postgresql
```

return to the `postgres` user and create a user for the server as follows:

```sh
createuser --interactive
```

#### Configuring PostgreSQL

Caching can be setup in PostgreSQL to reduce disk access. Depending on the OS, the configuration file for PostgreSQL is found
> /var/lib/postgres/data/postgresql.conf

Typically to make most use of the cache it should be set to ***half*** the RAM size, the following option should be set accordingly inside the file:
> effective_cache_size = 4GB

Additionally it is possible to enable parallel search queries by setting the following depending on the cores available:
> max_parallel_workers = 8

More configuration parameters can be found [here](https://www.postgresql.org/docs/9.6/static/runtime-config.html).

#### Configuring PostgreSQL In Code
To maintain system security, the login parameters for PostgreSQL that will be used in the Java code should be written in a configuration file and read by the code that deals with the database. This configuration file should the be added to the *gitignore* file so that it is not synchronized with the other developers.

**Example:**

> Config File

```sh
#host parameter can be left empty to default to localhost
#port parameter can be left empty to
username=
password=
host=
port=
database=
```
<br>
> Java reader

```java
public static void readConfFile() throws Exception {
    String file = System.getProperty("user.dir") + "/Postgres.conf";
    java.util.List<String> lines = new ArrayList<String>();
    Pattern pattern = Pattern.compile("\\[(.+)\\]");
    Matcher matcher;
    Stream<String> stream = Files.lines(Paths.get(file));
    lines = stream.filter(
      line -> !line.startsWith("#")).collect(Collectors.toList());

    for (int i = 0; i < lines.size(); i++) {
        if (lines.get(i).startsWith("user")) {
            matcher = pattern.matcher(lines.get(i));
            if (matcher.find())
                setDBUser(matcher.group(1));
            else
                throw new Exception("empty user in Postgres.conf");
        }
        if (lines.get(i).startsWith("database")) {
            matcher = pattern.matcher(lines.get(i));
            if (matcher.find())
                setDBName(matcher.group(1));
            else
                throw new Exception("empty database name in Postgres.conf");
        }
        if (lines.get(i).startsWith("pass")) {
            matcher = pattern.matcher(lines.get(i));
            matcher.find();
            setDBPassword(matcher.group(1));
        }
        if (lines.get(i).startsWith("host")) {
            matcher = pattern.matcher(lines.get(i));
            if (matcher.find())
                setDBHost(matcher.group(1));
            else
                setDBHost("localhost");
        }
        if (lines.get(i).startsWith("port")) {
            matcher = pattern.matcher(lines.get(i));
            if (matcher.find())
                setDBPort(matcher.group(1));
            else
                setDBPort("5432");
        }
    }
    if (!formatURL()) {
        throw new Exception("Wrong Format in Postgres.conf");

    }
}

private static boolean formatURL() {
    setDBURL("jdbc://postgresql://" + DB_HOST + ":" + "DB_PORT" + "/" + "DB_NAME");
    Pattern pattern = Pattern.compile("^\\w+:\\w+:\\/{2}\\w+:\\d+\\/\\w+(?:\\W|\\w)*$");
    Matcher matcher = pattern.matcher(DB_URL);
    return matcher.matches();
}
```

-----

## NoSQL
NoSQL databases are a new generation of databases designed for big data applications. Typically SQL databases' performance declines after a certain threshold of saved data on the disk, which is not the case in NoSQL databases.

There are multiple types of NoSQL databases each based on a specific usage, however NoSQL is overall generally used for large data sets.

#### Column Based
These types of databases store data on the disk pages as columns rather than the traditional row layout of SQL databases. For instances given the following table:

| sensor_id | value | date     |
| :-------- | :---- | :------- |
| 1         | 2.0   | 2012-3-4 |
| 2         | 2.1   | 2012-5-4 |
| 3         | 5.0   | 2012-7-4 |
| 4         | 7.6   | 2012-8-4 |
| 5         | 8.1   | 2012-9-4 |


The column `value` will be stored sequentially on the disk rather than having sequential rows stored on the disk. Typically they are used for data-science based applications which require working on large arrays.

#### Key-Value Based
These types of databases store data in a hashmap fashion and do not support complex data structures. Usually found in caches such as Redis as they provide the fastest access rates for data.

#### Document Based
These type of databases store data as JSON/BSON objects and can support data nesting. Typically used in applications that require data to be structred to some degree and can be used to store images. If the database does not support Binary data, the images need to be encoded in Base64 format, that is changing the binary to strings.

```json
{
    "FirstName": "Bob",
    "Address": "5 Oak St.",
    "Hobby": "sailing"
}
```

#### Graph Based
These type of databases store data as a graph, typically used for searching.

![graph](graph.png)

### ArangoDB
ArangoDB implements:
- Graph Based
- Document Based
- Key-Value Based

database types in one server. *The Key-Value implementation in ArangoDB is a document consisting of one entry only.* Arango implements its own querying language named AQL that supports CRUD (Create, Read, Update and Delete), more information can be found [here](https://docs.arangodb.com/3.3/AQL/DataQueries.html).


#### Configuring ArangoDB
After installing ArangoDB, it may not startup due to issues with access permissions, to to solve such issue add your user to the arango group as follows:

```sh
sudo  -i
gpasswd -a <USER> arango
```

then ensure that arango's directories are owned by arango user and arango group:

```sh
chown arangodb:arangodb -R
chmod 770 -R

```

secure the installtion of arango, run the following as your normal user:

```sh
arango-secure-installation
```


start the server:

```sh
sudo systemctl enable arangodb3
sudo systemctl start arangodb3
```

The online interface is running at (http://localhost:8529)

#### Graphs

A graph consists of two items:
- Vertices: this can either be a document of a ***Document Collection*** or of an ***Edge Collection***
- Edges: these are documents stored in ***Edge Collections***


As an analogy, in relational databases, tables may be created to map many-many relations, edge collections are similar to such tables, while vertices are similar to the actual records that the intermediary table connects. Graph based databases offer the ability to join multiple tables together without performing join operations, they simply follow the edges. Additionally attributes may be allocated to the edges and are typically directed graphs.

ArangoDB offers two forms of graphs:
- Named: this type ensures data integrity by imposing strict restrictions on the graph
- Anonymous: this type has more relaxed restrictions and fewer features albeit being faster than named graphs


#### Document Based
Documents are a collection of objects, ArangoDB models documents into JSON objects. The Java driver accepts Java objects as maps them internally to JSON objects. Refer to the code for examples, more information can be found [here](https://github.com/arangodb/arangodb-java-driver).


#### Cassandra
Cassandra is No-SQL column based database with a querying language with a syntax similar to SQL named CQL. Cassandra terminologies:
- Cluster: a collection of nodes or Data Centers arranged in a ring architecture. A name must be assigned to every cluster, which will subsequently be used by the participating nodes
- Keyspace: Similar to relational databases, the schema is the respective keyspace in Cassandra. The keyspace is the outermost container for data in Cassandra. The main attributes to set per keyspace are the Replication Factor, the Replica Placement Strategy and the Column Families
- Column Family: Column Families in Cassandra are like tables in Relational Databases. Each Column Family contains a col lection of rows which are represented by:
  ​                                                           *Map\<RowKey, SortedMap\<ColumnKey, ColumnValue\>\>*
   The key gives the ability to access related data together
- Column: A column in Cassandra is a data structure which contains a column name, a value and a timestamp. The columns and the number of columns in each row may vary in contrast with a relational database where data are well structured



***There is a known limitation with named sessions, that causes the driver to unexpectedly block the calling thread in certain circumstances; if using a fully asynchronous model, a session with no keyspace should be used. Additionally if a `USE` statement is issued, it will change the default keyspace on that session:***

```java
Session session = cluster.connect();
// No default keyspace set, need to prefix:
session.execute("select * from myKeyspace.myTable where id = 1");

session.execute("USE myKeyspace");
// Now the keyspace is set, unqualified query works:
session.execute("select * from myTable where id = 1");
```

#### Replication Strategies

##### SimpleStrategy

Used only for a single datacenter and one rack. This strategy places the first replica on a node determined by the partitioner. Additional replicas are placed on the next nodes clockwise in the ring without considering topology (rack or datacenter location). Used for evaluation and single data center test and development environments only. While developing, the keyspace can be set to *SimpleStrategy*.

##### NetworkTopologyStrategy

This strategy specifies how many replicas to be placed in each datacenter. This strategy places replicas in the same data center by walking the ring clockwise until reaching the first node in another rack. NetworkTopologyStrategy attempts to place replicas on distinct racks because nodes in the same rack (or similar physical grouping) often fail at the same time due to power, cooling, or network issues. When deciding how many replicas to configure in each datacenter, the two primary considerations are:
1. Being able to satisfy reads locally, without incurring cross data-center latency
2. Failure scenarios

The two most common ways to configure multiple datacenter clusters are:
- Two replicas in each datacenter: This configuration tolerates the failure of a single node per replication group and still allows local reads at a consistency level of ONE.
- Three replicas in each datacenter: This configuration tolerates either the failure of one node per replication group at a strong consistency level of LOCAL_QUORUM or multiple node failures per datacenter using consistency level ONE.

Asymmetrical replication groupings are also possible. For example, three replicas can exist in one datacenter to serve real-time application requests and use a single replica elsewhere for running analytics.


##### Consistency Levels - Replication Factor
Consistency levels in Cassandra can be configured to manage availability versus data accuracy. Configure consistency for a session or per individual read or write operation.

###### Write Consistency Level

Levels are sorted from *strongest* to *weakest*


| Level        | Description                              | Usage                                    |
| :----------- | :--------------------------------------- | :--------------------------------------- |
| ALL          | A write must be written to the commit log and memtable on all replica nodes in the cluster for that partition | Provides the highest consistency and the lowest availability of any other level |
| EACH_QUORUM  | Strong consistency. A write must be written to the commit log and memtable on a quorum of replica nodes in each datacenter | Used in multiple datacenter clusters to strictly maintain consistency at the same level in each datacenter. For example, choose this level if you want a read to fail when a datacenter is down and the QUORUM cannot be reached on that datacenter |
| QUORUM       | A write must be written to the commit log and memtable on a quorum of replica nodes across all datacenters | Used in either single or multiple datacenter clusters to maintain strong consistency across the cluster. Used if some level of failure is tolerable |
| LOCAL_QUORUM | Strong consistency. A write must be written to the commit log and memtable on a quorum of replica nodes in the same datacenter as the coordinator. Avoids latency of inter-datacenter communication. | Used in multiple datacenter clusters with a rack-aware replica placement strategy, such as NetworkTopologyStrategy, and a properly configured [snitch](http://docs.datastax.com/en/archived/cassandra/2.0/cassandra/architecture/architectureSnitchesAbout_c.html). Used to maintain consistency locally (within the single datacenter). Can be used with SimpleStrategy |
| ONE          | A write must be written to the commit log and memtable of at least one replica node. | Satisfies the needs of most users because consistency requirements are not stringent. |
| TWO          | A write must be written to the commit log and memtable of at least two replica nodes. | Similar to ONE.                          |
| THREE        | A write must be written to the commit log and memtable of at least three replica nodes. | Similar to TWO.                          |
| LOCAL_ONE    | A write must be sent to, and successfully acknowledged by, at least one replica node in the local datacenter | In a multiple datacenter clusters, a consistency level of `ONE` is often desirable, but cross-DC traffic is not. `LOCAL_ONE`                accomplishes this. For security and quality reasons, this level is used in an offline datacenter to prevent automatic connection to online nodes in other datacenters if an offline node goes down |
| ANY          | A write must be written to at least one node. If all replica nodes for the given        partition key are down, the write can still succeed after a [hinted handoff](https://docs.datastax.com/en/cassandra/3.0/cassandra/operations/opsRepairNodesHintedHandoff.html) has been written. If all replica nodes are down at write time, an `ANY` write is not readable until the replica nodes for that partition have recovered | Provides low latency and a guarantee that a write never fails. Delivers the lowest        consistency and highest availability |


$$
\text{Quorum} = \frac{1}{2}\Sigma_{i}^{N} f_i + 1
$$

###### Read Consistency

Levels are sorted from strongest to weakest

| Level        | Description                              | Usage                                    |
| :----------- | :--------------------------------------- | :--------------------------------------- |
| ALL          | Returns the record after all replicas have responded. The read operation will fail if a replica does not respond | Provides the highest consistency of all levels and the lowest availability of all levels |
| EACH_QUORUM  | -                                        | Not supported for reads                  |
| QUORUM       | Returns the record after a quorum of replicas from all datacenters has responded | Used in either single or multiple datacenter clusters to maintain strong consistency across the cluster. Ensures strong consistency if you can tolerate some level of failure |
| LOCAL_QUORUM | Returns the record after a quorum of replicas in the current datacenter as the coordinator has reported. Avoids latency of inter-datacenter communication | Used in multiple datacenter clusters with a rack-aware replica placement strategy ( NetworkTopologyStrategy) and a properly configured snitch. Fails when using SimpleStrategy |
| ONE          | Returns a response from the closest replica, as determined by the snitch. By default, a read repair runs in the background to make the other replicas consistent | Provides the highest availability of all the levels if you can tolerate a comparatively high probability of stale data being read. The replicas contacted for reads may not always have the most recent write |
| TWO          | Returns the most recent data from two of the closest replicas | Similar to ONE                           |
| THREE        | Returns the most recent data from three of the closest replicas | Similar to TWO                           |
| LOCAL_ONE    | Returns a response from the closest replica in the local datacenter | Same usage as described in the table about write consistency levels |
| SERIAL       | Allows reading the current (and possibly uncommitted) state of data without proposing a new addition or update. If a SERIAL read finds an uncommitted transaction in progress, it will commit the transaction as part of the read. Similar to QUORUM | To read the latest value of a column after a user has invoked a lightweight transaction to write to the column, use SERIAL. Cassandra then checks the inflight lightweight transaction for updates and, if found, returns the latest data |
| LOCAL_SERIAL | Same as SERIAL, but confined to the datacenter. Similar to LOCAL_QUORUM | Used to achieve [linearizable](https://docs.datastax.com/en/cassandra/3.0/cassandra/dml/dmlAboutDataConsistency.html#dmlAboutDataConsistency__linearizable-consistency) consistency for lightweight transactions |



## Caching

Users typically generate multiple requests per application and they may be repeated. Regenerating the data generated for each request again is costly thus caching used. Caches need to be distributed across the applications to avoid bottle-necks.

### Redis

Redis is an in memory key-value data store, with the ability to synchronize to disk to periodically to maintain system state. Depending on the OS being utilized the configuration file generally resides in */etc/redis.conf*.

#### Redis As A Centralized Cache

The default setup of Redis utilizes the server as a centralized cache in which each application can access the same data on the server.

Simple insertion:

```java
Jedis jedis = new Jedis("localhost");
jedis.set("foo", "bar");
String value = jedis.get("foo");
```

Redis supports multiple data structures such as:
- Lists
- HashMaps
- Key-Value Pairs
- Sets

The entire list of data structures can be found [here](https://redis.io/topics/data-types-intro) and the commands that will be used to access them from the [terminal](https://redis.io/commands).

##### Multithreaded Access

If multiple threads from the same host will be accessing the server, a connection pool should be created  to avoid data corruption.

```java
JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
try (Jedis jedis = pool.getResource()) {
  /// ... do stuff here ... for example
  jedis.set("foo", "bar");
  String foobar = jedis.get("foo");
  jedis.zadd("sose", 0, "car"); jedis.zadd("sose", 0, "bike");
  Set<String> sose = jedis.zrange("sose", 0, -1);
}
/// ... when closing your application:
pool.close();
```

##### Redis Pipelining

Redis supports pipelining of operations rather than waiting for a response on each operation, a response is sent in the end of the pipeline.

```java
Pipeline p = jedis.pipelined();
p.set("fool", "bar");
p.zadd("foo", 1, "barowitch");  p.zadd("foo", 0, "barinsky"); p.zadd("foo", 0, "barikoviev");
Response<String> pipeString = p.get("fool");
Response<Set<String>> sose = p.zrange("foo", 0, -1);
p.sync();

int soseSize = sose.get().size();
Set<String> setBack = sose.get();
```

##### Publish-Subscribe

Redis can be used in a publish-subscribe queue like fashion as well.

```java
class MyListener extends JedisPubSub {
        public void onMessage(String channel, String message) {
        }

        public void onSubscribe(String channel, int subscribedChannels) {
        }

        public void onUnsubscribe(String channel, int subscribedChannels) {
        }

        public void onPSubscribe(String pattern, int subscribedChannels) {
        }

        public void onPUnsubscribe(String pattern, int subscribedChannels) {
        }

        public void onPMessage(String pattern, String channel, String message) {
        }
}

MyListener l = new MyListener();

jedis.subscribe(l, "foo");
```



### Redis  As A Distributed Hash Table

***In the example below, three virtual machines were created to allow for seperate IPs, this same procedure applies on different machines.***

An internal network was created on VirtualBox from the preferences with DHCP enabled, and each machine was assigned to it a network card connected to the internal network. To view the ip of each machine:

```bash
ip addr show
```
The config file needs to have the following options set:

|           Parameter           |   Value    | Description                              |
| :---------------------------: | :--------: | ---------------------------------------- |
|             bind              | 10.0.2.10  | Binds the Redis server to the given IP, ***the IP will differ on each machine thus the value on the left is an example*** |
|             port              |    7000    | Binds Redis server to given port, any port of your choice can be chosen as long as it does not conflict another port |
|        cluster-enabled        |    yes     | Enables Redis to run in cluster mode     |
|      cluster-config-file      | nodes.conf | This is an auto generated file by Redis itself, nothing to be written in it |
|     cluster-node-timeout      |    1500    | The maximum amount of time in milliseconds a Redis Cluster node can be unavailable, without it being considered as failing. If a master node is not reachable for more than the specified amount of time, it will be failed over by its slaves |
| cluster-slave-validity-factor |     10     | If set to zero, a slave will always try to failover a master, regardless of the amount of time the link between the master and the slave remained disconnected. If the value is positive, a maximum disconnection time is calculated as the node timeout value multiplied by the factor provided with this option, and if the node is a slave, it will not try to start a failover if the master link was disconnected for more than the specified amount of time |
|   cluster-migration-barrier   |     1      | Minimum number of slaves a master will remain connected with, for another slave to migrate to a master which is no longer covered by any slave |
| cluster-require-full-coverage |    yes     | Set to yes by default, the cluster stops accepting writes if some percentage of the key space is not covered by any node. If set to no, the cluster will still serve queries even if only requests about a subset of keys can be processed |

After editing the redis.conf file, a file must be created to inform redis of its surronding nodes. For a typical cluster setup, a ***minimum*** of three master slaves is needed. The configuration file can be created using the ruby file attached, the redis gem must first be installed however, using:

```bash
gem install redis
```

after that start the cluster using:

```bash
sudo redis-server /etc/redis.conf
```

replication may then be enabled using the ruby file as so:

```bash
./redis-trib.rb create --replicas 1 127.0.0.1:7000 127.0.0.1:7001  127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
```

***the above IPs are examples, make sure to plugin the correct IPs***. The *replicas* flag assigns one slave to each master. The ruby file contains more tools such as sharding and status check, for more details check [here](https://redis.io/topics/cluster-tutorial).

#### Clustering in Jedis

Redis is primarily built for master/slave distribution. This means that write requests have to be explicitly addressed to the master, read requests then can be (but must not necessarily) addressed to the slaves, which alleviates the master. There are two ways to inform a slave it will be enslaved a given master:

- Specify it in the respective section in the Redis Config file of the redis server

- On a given jedis instance, call the slaveOf method and pass IP (or "localhost") and port as argument:

```java
jedis.slaveOf("localhost", 6379);  //  if the master is on the same PC which runs your code
jedis.slaveOf("192.168.1.35", 6379);
```

***Note: since Redis 2.6 slaves are read only by default, so write requests to them will result in an error.***

In case a master goes down, a slave may be promoted to be a new master. Firstly, disable replication of the offline master first, then, enable replication of the remaining slaves to the new master:

```java
slave1jedis.slaveofNoOne();
slave2jedis.slaveof("192.168.1.36", 6379);
```

### CouchBase

Couchbase Server uses buckets to group collections of keys and values logically. A Couchbase cluster can have multiple buckets, each with its own memory quota, number of replica copies, and capabilities. Couchbase Server supports three different types of bucket, the properties of which are described in this section.

#### Couchbase buckets

 These allow data to be automatically replicated for high availability, using the Database Change Protocol (DCP); and dynamically scaled across multiple servers, by means of Cross Datacenter Replication (XDCR).

If a Couchbase bucket's RAM-quota is exceeded, items are ejected. This means that data, which is resident both in memory and on disk, is removed from memory, but not from disk. Therefore, if removed data is subsequently needed, it is reloaded into memory from disk. For a Couchbase bucket, ejection can be either of the following, based on configuration performed at the time of bucket-creation:

- Value-only: Only key-values are removed. Generally, this favors performance at the expense of memory.
- Full: All data — including keys, key-values, and meta-data — is removed. Generally, this favors memory at the expense of performance.

#### Ephemeral buckets

 These are an alternative to Couchbase buckets, to be used whenever persistence is not required: for example, when repeated disk-access involves too much overhead. This allows highly consistent in-memory performance, without disk-based fluctuations. It also allows faster node re-balances and restarts.

If an Ephemeral bucket's RAM-quota is exceeded, one of the following occurs, based on configuration performed at the time of bucket-creation:

- Resident data-items remain in RAM. No additional data can be added; and attempts to add data therefore fail
- Resident data-items are ejected from RAM, to make way for new data. For an Ephemeral bucket, this means that data, which is resident in memory (but, due to this type of bucket, can never be on disk), is removed from memory. Therefore, if removed data is subsequently needed, it cannot be re-acquired from Couchbase Server
- For an Ephemeral bucket, ejection removes all of an item's data: however, a tombstone (a record of the ejected item, which includes keys and metadata) is retained until the next scheduled purge of metadata for the current node

Ephemeral buckets are *Document* based buckets.

#### Memcached buckets

These buckets are similar to Redis. If a Memcached bucket's RAM-quota is exceeded, items are ejected. For a Memcached bucket, this means that data, which is resident in memory (but, due to this type of bucket, can never be on disk), is removed from memory. Therefore, if removed data is subsequently needed, it cannot be re-acquired from Couchbase Server. Ejection removes all of an item's data.


To start CouchBase:

```bash
sudo systemctl start couchbase-server
```

The web interface will be running at (http://localhost:8091), from there you can configure CouchBase for initial setup. Clusters can be created or joined, after the setup.



Creating and joining clusters can be done through the web interface.