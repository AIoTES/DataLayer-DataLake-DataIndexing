# Data Indexing


## Getting started / Use

This required section should explain the steps needed to use it. Remember Module is fully (or mostly ) functional code, 
thus this section should explain interfaces, UIs, procedures and any other action (i.e. configuration) needed to use 
the module. This section should be targeted to the end user of the module.

## How to build, install, or deploy it



    yarn install

We use yarn scripts and [Webpack][] as our build system.

    ./mvnw
    yarn start

## Building for production

To optimize the DataIntegration application for production, run:

    ./mvnw -Pprod clean package

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using Simlife in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test



## Using Docker to simplify development (optional)

First build the data indexing component using the following command:

    ./mvnw -DskipTests=true -Pprod clean package

Second step is to build docker image. To build docker image, run the following command:

    docker build --no-cache -t docker-activage.satrd.es/dl-data-indexing target
    

Third step is to run the docker image using the following command. For this step you will need `app.yml` file and its associated files (i.e. mongodb.yml and elasticsearch.yml), available in [src/main/docker](src/main/docker).

    docker-compose -f src/main/docker/app.yml up -d

The Data Indexing can be accessed using the following URL:

[http://localhost:4580/#/admin/docs](http://localhost:4580/#/admin/docs)


Last step is to push docker image to the activage docker registry (docker-activage.satrd.es), if everything works fine. Use the following command:

    docker push docker-activage.satrd.es/dl-data-indexing



## Further Information

This section is mandatory; it will explain or link to the tutorial/course where the module itself and how to use it is 
explained. The sections above should be contextualized on the module, as if it is a standalone code. This section, 
however, should address the context of the module as part of AIOTES, thus in this section explanations about the other 
modules which this module interacts with.


## Credits

This recommended section should list the authors of the (template) project, and if possible accessible contact 
information for users to use when doubts arise. It should be easily expandable, for instance add a template to 
include other authors; the recommendation is to define a list using “Name Surname <user@host.name>" format.


## License
    
This is a required section, as we must specify the licence(s) applied to the code. This section should only be a 
summary of the licence, as explained in the licence policy.



<!--- For example, to start a mongodb database in a docker container, run:

    docker-compose -f src/main/docker/mongodb.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mongodb.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw verify -Pprod dockerfile:build dockerfile:tag@version dockerfile:tag@commit

Then run:

    docker-compose -f src/main/docker/app.yml up -d
    -->
