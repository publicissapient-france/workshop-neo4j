package fr.xebia.xke.neo4j.domains.nodes;

import fr.xebia.xke.neo4j.domains.Color;
import fr.xebia.xke.neo4j.domains.ProductType;
import org.neo4j.graphdb.Node;

public class Product{
    private Node internalNode;

    public Product (Node internalNode){
        if (internalNode == null){
            throw new IllegalArgumentException("Product must have an internal node");
        }
        this.internalNode = internalNode;
    }

    public long getPrice() {
        return Long.parseLong((String)internalNode.getProperty("price"));
    }

    public void setPrice(long price) {
        internalNode.setProperty("price", price);
    }

    public String getBrand() {
        return (String)internalNode.getProperty("brand");
    }

    public void setBrand(String brand) {
        internalNode.setProperty("brand", brand);
    }

    public ProductType getType() {
        return (ProductType)internalNode.getProperty("type");
    }

    public void setType(ProductType type) {
        internalNode.setProperty("type", type);
    }

    public Color getColor() {
        return (Color)internalNode.getProperty("color");
    }

    public void setColor(Color color) {
        internalNode.setProperty("color",color);
    }

    public String getName() {
        return (String)internalNode.getProperty("name");
    }

    public void setName(String name) {
        internalNode.setProperty("name",name);
    }

    public Node getInternalNode(){
        return internalNode;
    }
}
