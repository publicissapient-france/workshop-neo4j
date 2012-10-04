package fr.xebia.xke.neo4j.relations;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType{
    SPONSORED, CONTAINS, OWN, COSTS, COLOR, DATE, DAY_OF_WEEK
}
