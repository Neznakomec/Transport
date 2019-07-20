package com.sdimdev.nnhackaton.data.route.parse;

import java.util.HashMap;

public class Node {
    private HashMap<String, String> nodeAttributes = new HashMap<>();
    private HashMap<String, String> tagAttributes = new HashMap<>();

    public HashMap<String, String> getTagAttributes() {
        return tagAttributes;
    }

    public HashMap<String, String> getNodeAttributes() {
        return nodeAttributes;
    }

    public String getId() {
        return nodeAttributes.get("id");
    }


    public double getLat() {
       try{
           return Double.parseDouble(nodeAttributes.get("lat"));
       }catch (NumberFormatException e){
           return 0;
       }
    }


    public double getLon() {
        try{
            return Double.parseDouble(nodeAttributes.get("lon"));
        }catch (NumberFormatException e){
            return 0;
        }
    }

}
