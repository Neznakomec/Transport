package com.sdimdev.nnhackaton.data.route.parse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class Parse {
    private final static int PARSED_STOP_POSITION = 1;


    private final static int LEVEL_NODE = 2;
    private final static int LEVEL_WAY = 2;
    private final static int LEVEL_RELATION = 2;
    private final static int LEVEL_TAG = 3;
    private final static int LEVEL_ND = 3;
    private final static int LEVEL_MEMBERS = 3;

    private final static int PARSING_NODE = 1;
    private final static int PARSING_WAY = 2;
    private final static int PARSING_RELATION = 3;

    public static void getData(String route) throws Throwable {
       /* SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        Step1Handler handler = new Step1Handler(route);
        String parentFolder = new File(System.getProperty("user.dir")).getParent();
        parser.parse(new File(parentFolder, fileSource), handler);

        Set<String> nodesSet = filterNodesByRelations(handler.relations);

        Step2Handler handler2 = new Step2Handler(nodesSet, filterWays(handler.relations));
        parser.parse(new File(parentFolder, fileSource), handler2);

        Set<String> nodesSet2 = new HashSet<>(nodesSet);

        nodesSet2.addAll(filterNodes(handler2.ways));

        Step3Handler handler3 = new Step3Handler(nodesSet2);
        parser.parse(new File(parentFolder, fileSource), handler3);


        writeNodes(handler3.nodes.values(), "assets/" + suffix + "Nodes.xml");
        writeWays(handler2.ways.values(), "assets/" + suffix + "Ways.xml");
        writeRelations(handler.relations.values(), "assets/" + suffix + "Relations.xml");*/
    }

    private static Set<String> filterNodesByRelations(HashMap<String, Relation> relations) {
        Set<String> existsNodes = new HashSet<>();
        for (Relation r : relations.values()) {
            for (Member m : r.getMembers()) {
                if ("node".equals(m.getAttributes().get("type"))) {
                    existsNodes.add(m.getAttributes().get("ref"));
                }
            }
        }
        return existsNodes;
    }

    private static Set<String> filterNodes(HashMap<String, Way> ways) {
        Set<String> existsNodes = new HashSet<>();
        for (Way w : ways.values()) {
            for (String wayNode : w.getWayNodes()) {
                existsNodes.add(wayNode);
            }
        }
        return existsNodes;
    }

    private static Set<String> filterWays(HashMap<String, Relation> relations) {
        Set<String> existsWays = new HashSet<>();
        for (Relation r : relations.values()) {
            for (Member m : r.getMembers()) {
                if ("way".equals(m.getAttributes().get("type"))) {
                    existsWays.add(m.getAttributes().get("ref"));
                }
            }
        }
        return existsWays;
    }


    private static class Step2Handler extends DefaultHandler {
        int level = 0;
        List<StopPosition> positionList = new ArrayList<>();
        HashMap<String, Way> ways = new HashMap<>();
        private Set<String> nodesSet;
        private Set<String> waysSet;

        private Way currentWay;

        private int oneLevelType = -1;


        public Step2Handler(Set<String> nodesSet, Set<String> waysSet) {
            this.nodesSet = nodesSet;
            this.waysSet = waysSet;
        }


        @Override
        public void startDocument() throws SAXException {
            // Тут будет логика реакции на начало документа
        }

        @Override
        public void endDocument() throws SAXException {

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            level++;
            if (level == LEVEL_WAY && qName.equals("way")) {
                currentWay = new Way();
                oneLevelType = PARSING_WAY;
                for (int i = 0; i < attributes.getLength(); i++) {
                    String k = attributes.getQName(i);
                    String v = attributes.getValue(i);
                    currentWay.getWayAttributes().put(k, v);
                }
            } else if (level == LEVEL_TAG && qName.equals("tag")) {
                String k = attributes.getValue("k");
                String v = attributes.getValue("v");
                if (k != null && v != null) {
                    if (oneLevelType == PARSING_WAY) {
                        currentWay.getTagAttributes().put(k, v);
                    }
                }
            } else if (level == LEVEL_ND && qName.equals("nd")) {
                String ref = attributes.getValue("ref");
                currentWay.getWayNodes().add(ref);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // Тут будет логика реакции на конец элемента
            if (level == LEVEL_WAY && qName.equals("way")) {
                if (waysSet.contains(currentWay.getId())) {
                    ways.put(currentWay.getId(), currentWay);
                }

            }
            level--;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на текст между элементами
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на пустое пространство внутри элементов (пробелы, переносы строчек и так далее).
        }
    }

    private static class Step3Handler extends DefaultHandler {
        int level = 0;
        List<StopPosition> positionList = new ArrayList<>();
        HashMap<String, Node> nodes = new HashMap<>();
        private Set<String> nodesSet;
        private Node currentNode;
        private int oneLevelType = -1;


        public Step3Handler(Set<String> nodesSet) {
            this.nodesSet = nodesSet;
        }


        @Override
        public void startDocument() throws SAXException {
            // Тут будет логика реакции на начало документа
        }

        @Override
        public void endDocument() throws SAXException {

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            level++;
            if (level == LEVEL_NODE && qName.equals("node")) {
                currentNode = new Node();
                oneLevelType = PARSING_NODE;
                for (int i = 0; i < attributes.getLength(); i++) {
                    String k = attributes.getQName(i);
                    String v = attributes.getValue(i);
                    currentNode.getNodeAttributes().put(k, v);
                }
            } else if (level == LEVEL_TAG && qName.equals("tag")) {
                String k = attributes.getValue("k");
                String v = attributes.getValue("v");
                if (k != null && v != null) {
                    if (oneLevelType == PARSING_NODE) {
                        currentNode.getTagAttributes().put(k, v);
                    }
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // Тут будет логика реакции на конец элемента
            if (level == LEVEL_NODE && qName.equals("node")) {
                if (currentNode.getTagAttributes().containsKey("public_transport") &&
                        (
                                "stop_position".equals(currentNode.getTagAttributes().get("public_transport")) ||
                                        "platform".equals(currentNode.getTagAttributes().get("public_transport"))
                        )
                ) {
                    String name = currentNode.getTagAttributes().get("name");
                    if (name == null) {
                        name = "Unknown";
                    }
                    String id = currentNode.getNodeAttributes().get("id");

                    double lon = 0;
                    try {
                        lon = Double.parseDouble(currentNode.getNodeAttributes().get("lon"));
                    } catch (NumberFormatException e) {
                        //nothing
                    }
                    double lat = 0;
                    try {
                        lat = Double.parseDouble(currentNode.getNodeAttributes().get("lat"));
                    } catch (NumberFormatException e) {
                        //nothing
                    }
                    StopPosition position = new StopPosition();
                    position.setName(name);
                    position.setId(id);
                    position.setLat(lat);
                    position.setLon(lon);
                    positionList.add(position);
                }
                if (nodesSet.contains(currentNode.getId())) {
                    nodes.put(currentNode.getId(), currentNode);
                }
            }
            level--;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на текст между элементами
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на пустое пространство внутри элементов (пробелы, переносы строчек и так далее).
        }
    }


    private static class Step1Handler extends DefaultHandler {
        int level = 0;

        HashMap<String, Relation> relations = new HashMap<>();
        private Relation currentRelation;
        private int oneLevelType = -1;
        private String routeId;

        public Step1Handler(String routeId) {
            this.routeId = routeId;
        }


        @Override
        public void startDocument() throws SAXException {
            // Тут будет логика реакции на начало документа
        }

        @Override
        public void endDocument() throws SAXException {

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            level++;
            if (level == LEVEL_RELATION && qName.equals("relation")) {
                currentRelation = new Relation();
                oneLevelType = PARSING_RELATION;
                for (int i = 0; i < attributes.getLength(); i++) {
                    String k = attributes.getQName(i);
                    String v = attributes.getValue(i);
                    currentRelation.getAttributes().put(k, v);
                }
            } else if (level == LEVEL_TAG && qName.equals("tag")) {
                String k = attributes.getValue("k");
                String v = attributes.getValue("v");
                if (k != null && v != null) {
                    if (oneLevelType == PARSING_RELATION) {
                        currentRelation.getTagAttributes().put(k, v);
                    }
                }
            } else if (level == LEVEL_MEMBERS && qName.equals("member")) {
                Member member = new Member();
                for (int i = 0; i < attributes.getLength(); i++) {
                    String k = attributes.getQName(i);
                    String v = attributes.getValue(i);
                    member.getAttributes().put(k, v);
                }
                currentRelation.getMembers().add(member);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // Тут будет логика реакции на конец элемента
            if (level == LEVEL_RELATION && qName.equals("relation")) {
                if ("route".equals(currentRelation.getTagAttributes().get("type"))
                        && currentRelation.getId().equals(routeId)) {
                    relations.put(currentRelation.getId(), currentRelation);
                }
            }
            level--;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на текст между элементами
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на пустое пространство внутри элементов (пробелы, переносы строчек и так далее).
        }
    }
}
