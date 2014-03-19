package fr.xebia.xke.neo4j;

import java.io.InputStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import junit.framework.Assert;


public class AbstractTest {

    private static final String DATASET_PATH = "/dataset.cypher";
    private static final String NEO4J_CONF_PATH = "/neo4j.properties";


    protected GraphDatabaseService graphDb;

    @Before
    public void setUp() throws Exception {
        graphDb = new TestGraphDatabaseFactory()//
                .newImpermanentDatabaseBuilder()//
                .loadPropertiesFromFile(getClass().getResource(NEO4J_CONF_PATH).getPath())
                .newGraphDatabase();


        InputStream is = getClass().getResourceAsStream(DATASET_PATH);
        Scanner scanner = new Scanner(is);
        StringBuilder dataSetQuery = new StringBuilder();
        while (scanner.hasNextLine()) {
            dataSetQuery.append(scanner.nextLine());
        }
        ExecutionEngine engine = new ExecutionEngine(graphDb);

        String[] queries = dataSetQuery.toString().split(";");
        for (String query : queries){
            engine.execute(query);
        }
    }

    @Test
    public void testThatDatabaseIsInitialised() {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        ExecutionResult result = engine.execute("START n=node(*) RETURN count(n)");
        int nbNodes = Integer.parseInt(result.iterator().next().get("count(n)").toString());
        int expected = 14;
        Assert.assertEquals("After initialization the database should contains "+expected+" nodes.", expected, nbNodes);
    }

    @After
    public void tearDown() throws Exception {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        engine.execute("START n=node(*) OPTIONAL MATCH n-[r]-m  WITH n, r DELETE n, r");
        graphDb.shutdown();
    }
}

