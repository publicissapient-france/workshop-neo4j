package fr.xebia.xke.neo4j.domains;

import fr.xebia.xke.neo4j.domains.Color;
import fr.xebia.xke.neo4j.domains.ProductType;
import org.neo4j.graphdb.Node;

public class Product{
    private Node underlyingNode;

    public Product (Node underlyingNode){
        this.underlyingNode = underlyingNode;
    }

    public long getPrice() {
        return Long.parseLong((String)underlyingNode.getProperty("price"));
    }

    public void setPrice(long price) {
        underlyingNode.setProperty("price", price);
    }

    public String getBrand() {
        return (String)underlyingNode.getProperty("brand");
    }

    public void setBrand(String brand) {
        underlyingNode.setProperty("brand", brand);
    }

    public ProductType getType() {
        return (ProductType)underlyingNode.getProperty("type");
    }

    public void setType(ProductType type) {
        underlyingNode.setProperty("type", type);
    }

    public Color getColor() {
        return (Color)underlyingNode.getProperty("color");
    }

    public void setColor(Color color) {
        underlyingNode.setProperty("color",color);
    }

    public String getName() {
        return (String)underlyingNode.getProperty("name");
    }

    public void setName(String name) {
        underlyingNode.setProperty("name",name);
    }

    public Node getUnderlyingNode(){
        return underlyingNode;
    }

    @Override
    public int hashCode()
    {
        return underlyingNode.hashCode();
    }

    @Override
    public boolean equals( Object o )
    {
        return o instanceof Product &&
                underlyingNode.equals( ( (Product)o ).getUnderlyingNode() );
    }

    @Override
    public String toString()
    {
        return "Person[" + getName() + "]";
    }
}
