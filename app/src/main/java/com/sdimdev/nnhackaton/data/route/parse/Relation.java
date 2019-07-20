package com.sdimdev.nnhackaton.data.route.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Relation {
    private ArrayList<Member> members = new ArrayList<>();
    private HashMap<String, String> tagAttributes = new HashMap<>();
    private HashMap<String, String> attributes = new HashMap<>();

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(LinkedHashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return attributes.get("id");
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public HashMap<String, String> getTagAttributes() {
        return tagAttributes;
    }

    public void setTagAttributes(HashMap<String, String> tagAttributes) {
        this.tagAttributes = tagAttributes;
    }
}
