# Docker Image Building

----

Consider the image built using the following commands:

```dockerfile
FROM ubuntu:15.04
COPY . /app
RUN make /app
CMD python /app/app.py
```

This `Dockerfile` contains four commands, each of which creates a layer. The `FROM` statement starts out by creating a layer from the ubuntu:15.04 image. The `COPY` command adds some files from your Docker client’s current directory. The `RUN` command builds your application using the make command. Finally, the last layer specifies what command to run within the container.

Each layer is only a set of differences from the layer before it. The layers are stacked on top of each other. When a new container is created, you add a new writable layer on top of the underlying layers. This layer is often called the **Container Layer**. All changes made to the running container, such as writing new files, modifying existing files, and deleting files, are written to this thin writable container layer. The diagram below shows a container based on the Ubuntu 15.04 image.

![Layers](layers.jpg)



##### Building A Docker Image

To place your application in an image, a `Dockerfile` must be created, which describes how to build the image. A sample file is provided in the directory, to build the image:

> `docker build -t alpine-docker `

This will build the image with the name *alpine-docker*. Alpine is minimal Linux distribution, other distributions exist and base images exist in the [library hub](https://hub.docker.com/u/library/). More information can be found [here](https://deis.com/blog/2015/creating-sharing-first-docker-image/) and [here](https://docs.docker.com/get-started/part2/).

