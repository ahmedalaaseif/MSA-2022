# Backend
----


## Netty
Netty is a non-blocking threaded networking library for building servers. 

### Pipelines
It allows for the creation of pipelines to process requests incrementally.

```Java
public static void main(String[] args) throws Exception {
    // Creates an EventLoop that is shareable across clients
	EventLoopGroup group = new NioEventLoopGroup(); 
    try {
    	ServerBootstrap b = new ServerBootstrap();
        b.group(group) // Bootstrap the server to a specific group
          		// Specifies transport protocol for channel
                    .channel(NioServerSocketChannel.class) 
          		// Specifies address for channel
                    .localAddress(new InetSocketAddress(port)) 
          		// Specifies channel handler to call when connection is accepted
                    .childHandler(new ChannelInitializer<SocketChannel>() { 
                        @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                      		// Adds channel handler to pipeline
                            ch.pipeline().addLast(new EchoServerHandler()); 
                        }
                    });
			// Bind server to address, and block (sync method) until it does so
            ChannelFuture f = b.bind().sync(); 
            
            // Returns a future channel that will be notified when shutdown
            f.channel().closeFuture().sync(); 

        } finally {
            group.shutdownGracefully().sync(); // Terminates all threads
        }
    }
```

## Maven

Maven is a Java project management tool, used it to manage the dependencies of a project as well as generating a JAR from the project. There is a [maven repository](https://mvnrepository.com/) that contains a wide assortment of JARs.

Maven has 9 tools:
1. Clean: removes previously generated JARs
2. Validate: validate the project is correct and all necessary information is available, any missing JAR is downloaded from the repository
3. Compile: compile the source code of the project
4. Test: test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
5. Package: take the compiled code and package it in its distributable format, such as a JAR.
6. Verify: run any checks on results of integration tests to ensure quality criteria are met
7. Install: install the package into the local repository, for use as a dependency in other projects locally
8. Site: generates project's site documentation
9. Deploy: done in the build environment, copies the final package to the remote repository for sharing with other developers and projects

When defining a Maven project in Eclipse/Intellij, a *GroupID* and *ArtificatID* needs to be chosen, these reference:
- GroupID: will identify the project uniquely across all projects, so we need to enforce a naming schema. It has to follow the package name rules, what that means it that it has to be at least as a controllable domain name control, multiple subgroups can be created

**Example:** org.apache.maven, guc.facebook, guc.facebook.chat-app

- ArtificatID: is the name of the JAR without version. If you created the JAR then any name can be chosen with only lowercase letters and no symbols. If it's a third party JAR use the name of the JAR as it's distributed.

**Example:** maven, commons-math, chat-app

After creating the project, there will be a POM file created, this is what maven uses to control the project. This file specifies, the Java version to use, the dependencies to add and how to structure the project.

```XML
<!-- Set Java version to compile -->

<build>
    <plugins>
        <plugin>
    		<groupId>org.apache.maven.plugins</groupId>
    		<artifactId>maven-compiler-plugin</artifactId>
    		<version>3.8.0</version>
    		<configuration>
        		<release>11</release>
    		</configuration>
		</plugin>
    </plugins>
</build>

<!-- MongoDB Driver dependency -->

<dependencies>
	<dependency>
    	<groupId>org.mongodb</groupId>
    	<artifactId>mongo-java-driver</artifactId>
    	<version>3.12.8</version>
	</dependency>
</dependencies>
```

### Runnable JARs

To produce runnable JARs in Maven, the *pom.xml* file needs to have the following plugin:
```xml

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.4</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <transformers>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <!-- Specify your main class here -->
                        <mainClass>Main</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```
The main class tag should be changed accordingly, the JAR can now be produced by running the *Package* life-cycle in Maven.


