package fr.xebia.xke.neo4j;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.io.InputStream;
import java.util.Scanner;


public class AbstractTest {

    public static final String DB_PATH = "neo4jDb-test";
    private static final String DATASET_PATH = "/dataset.cypher";
    private static final String NEO4J_CONF_PATH = "/neo4j.properties";


    protected GraphDatabaseService graphDb;

    @Before
    public void setUp() throws Exception {
         graphDb = new TestGraphDatabaseFactory()//
                        .newImpermanentDatabaseBuilder()//
                        //.newEmbeddedDatabaseBuilder(DB_PATH)//
                        .loadPropertiesFromFile(getClass().getResource(NEO4J_CONF_PATH).getPath())
                        .newGraphDatabase();


        InputStream is = getClass().getResourceAsStream(DATASET_PATH);
        Scanner scanner = new Scanner(is);
        StringBuffer dataSetQuery = new StringBuffer();
        while(scanner.hasNextLine()){
            dataSetQuery.append(scanner.nextLine());
        }
        ExecutionEngine engine = new ExecutionEngine( graphDb );
        ExecutionResult result = engine.execute(dataSetQuery.toString());
    }

    @Test
    public void testThatDatabaseIsInitialised() {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        ExecutionResult result = engine.execute("START n=node(*) RETURN count(n)");
        int nbNodes = Integer.parseInt(result.iterator().next().get("count(n)").toString());
        Assert.assertEquals("After initialization the database should contains 23 nodes.", 23, nbNodes);
    }

    @After
    public void tearDown() throws Exception {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        engine.execute("START n=node(*) MATCH n-[r?]-() WHERE ID(n) <> 0 DELETE n,r");
        graphDb.shutdown();
    }
}

