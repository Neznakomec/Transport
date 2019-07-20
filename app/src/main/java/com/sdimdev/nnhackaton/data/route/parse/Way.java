package com.sdimdev.nnhackaton.data.route.parse;

import java.util.ArrayList;
import java.util.HashMap;

public class Way {
    private HashMap<String, String> wayAttributes = new HashMap<>();
    private ArrayList<String> wayNodes = new ArrayList<>();
    private HashMap<String, String> tagAttributes = new HashMap<>();

    public HashMap<String, String> getWayAttributes() {
        return wayAttributes;
    }

    public ArrayList<String> getWayNodes() {
        return wayNodes;
    }

    public HashMap<String, String> getTagAttributes() {
        return tagAttributes;
    }

    public String getId() {
        return wayAttributes.get("id");
    }
}
