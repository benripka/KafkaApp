#Confluent Producer + Consumer

<h2>Local Setup</h2>

1. Create a file called java.config somewhere in your system ($HOME/.confluent/java.config for example) to specify the local confluent platform broker host and port. Add the line `bootstrap.servers=localhost:9092` to the file (Or whatever port you're running... This is the default though).
2. Create the ENV variable CONFLUENT_CONFIG_PATH that contains the absolute path to your new java.config file. Use a .env file for this is desired.
3. Run `docker-compose up` from the root directory to spin up all that docker containers that comprise the confluent platform. This may take a while as some of the images are large and will take time to load.
4. Verify that you can view the live confluence-console on http://localhost:9021/clusters
5. Run microservice applications as needed.


