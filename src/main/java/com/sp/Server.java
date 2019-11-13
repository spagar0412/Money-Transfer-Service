package com.sp;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Server class.
 *
 */
public class Server 
{
    private static final String EXCEPTION_MAPPERS = "com.sp.exception";
	private static final String RESOURCES = "com.sp.api";
	private static final int DEFAULT_PORT = 28080;
	// Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:" + DEFAULT_PORT +"/moneytransfer/"; //StringBuilder can be use to cancat

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() 
    {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.sp.api package
    	//JacksonFeature to extend  server support for Json
          
          final ResourceConfig rc = new ResourceConfig().packages(RESOURCES,EXCEPTION_MAPPERS)
        		.register(JacksonFeature.class);
      
        
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException 
    {
        final HttpServer server = startServer();
        System.out.println(String.format("Service started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdown();
    }
}

