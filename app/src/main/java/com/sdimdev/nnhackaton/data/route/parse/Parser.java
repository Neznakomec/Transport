package com.sdimdev.nnhackaton.data.route.parse;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;

import com.sdimdev.nnhackaton.model.entity.route.Route;
import com.sdimdev.nnhackaton.model.entity.route.RoutePoint;
import com.sdimdev.nnhackaton.model.entity.route.RouteResult;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class Parser {
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

    private Context context;

    public Parser(Context context) {
        this.context = context;
    }

    public RouteResult getData(String route) {
        try {
            RelationParser handler = new RelationParser(route);
            parse(handler, "allRoutesRelations.xml");

            Set<String> nodesSet = filterNodesByRelations(handler.relations);
            WaysParser handler2 = new WaysParser(filterWays(handler.relations));
            parse(handler2, "allRoutesWays.xml");
            Set<String> nodesSet2 = new HashSet<>(nodesSet);
            nodesSet2.addAll(filterNodes(handler2.ways));
            NodesParser handler3 = new NodesParser(nodesSet2);
            parse(handler3, "allRoutesNodes.xml");
            return getRes(handler.relations, handler2.ways, handler3.nodes);
        } catch (Throwable throwable) {
            Log.d("Parser", "error", throwable);
            return null;
        }
    }

    private void parse(DefaultHandler handler, String path) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(path);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.setFeature("http://xml.org/sax/features/namespaces", false);
            xr.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
            xr.setContentHandler(handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);
        } catch (SAXException e) {
            Log.d("Parse", "error parse:" + path, e);
        } catch (ParserConfigurationException e) {
            Log.d("Parse", "error parse:" + path, e);
        } catch (IOException e) {
            Log.d("Parse", "error parse:" + path, e);
        }
    }

    private String getAssetsFolder() {
        return "/android_asset/";
    }

    private RouteResult getRes(Map<String, Relation> relations, Map<String, Way> ways,
                               Map<String, Node> nodes) {

        RouteResult result = new RouteResult();
        Random random = new Random();

        List<Route> routes = new ArrayList<>();
        for (Map.Entry<String, Relation> relationEntry : relations.entrySet()) {
            Route route = new Route();
            route.setColor(Color.argb(255, 100 + random.nextInt(154), 100 + random.nextInt(154), 100 + random.nextInt(154)));
            String name = relationEntry.getValue().getAttributes().get("ref");
            route.setName(name);
            List<Member> members = relationEntry.getValue().getMembers();
            for (Member m : members) {
                insertIntoRouteList(route, m, ways, nodes);
            }
            routes.add(route);
        }
        result.setRouteList(routes);
        return result;
    }

    private void insertIntoRouteList(Route route, Member member, Map<String, Way> ways,
                                     Map<String, Node> nodes) {
        if ("way".equals(member.getAttributes().get("type"))) {
            insertWay(route, member.getAttributes().get("ref"), ways, nodes);
        } else if ("node".equals(member.getAttributes().get("type"))) {
            insertNode(route, member.getAttributes().get("ref"), nodes);
        }
    }

    private void insertWay(Route route, String wayId, Map<String, Way> ways,
                           Map<String, Node> nodes) {
        Way way = ways.get(wayId);
        if (way != null) {
            for (String n : way.getWayNodes()) {
                insertNode(route, n, nodes);
            }
        }

    }

    private void insertNode(Route route, String nodeId,
                            Map<String, Node> nodes) {
        Node node = nodes.get(nodeId);
        if (node != null) {
            RoutePoint routePoint = new RoutePoint();
            routePoint.setLat(node.getLat());
            routePoint.setLon(node.getLon());
            route.getRoutePoints().add(routePoint);
        }
    }

    private Set<String> filterNodesByRelations(HashMap<String, Relation> relations) {
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

    private Set<String> filterNodes(HashMap<String, Way> ways) {
        Set<String> existsNodes = new HashSet<>();
        for (Way w : ways.values()) {
            for (String wayNode : w.getWayNodes()) {
                existsNodes.add(wayNode);
            }
        }
        return existsNodes;
    }

    private Set<String> filterWays(HashMap<String, Relation> relations) {
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


    private class WaysParser extends DefaultHandler {
        int level = 0;
        HashMap<String, Way> ways = new HashMap<>();
        private Set<String> waysSet;

        private Way currentWay;

        private int oneLevelType = -1;


        public WaysParser(Set<String> waysSet) {
            this.waysSet = waysSet;
        }


        @Override
        public void startDocument() throws SAXException {
            Log.d("Parse", "startDocument");
            // Тут будет логика реакции на начало документа
        }

        @Override
        public void endDocument() throws SAXException {
            Log.d("Parse", "endDocument");

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            level++;
            if (level == LEVEL_WAY && qName.equals("way")) {
                if (!waysSet.contains(attributes.getValue("id"))) {
                    currentWay = null;
                    return;
                }
                Log.d("Parse", "startElement:" + qName);
                currentWay = new Way();
                oneLevelType = PARSING_WAY;
                for (int i = 0; i < attributes.getLength(); i++) {
                    String k = attributes.getQName(i);
                    String v = attributes.getValue(i);
                    currentWay.getWayAttributes().put(k, v);
                }
            } else if (currentWay != null) {
                if (level == LEVEL_TAG && qName.equals("tag")) {
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
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            // Тут будет логика реакции на конец элемента
            if (level == LEVEL_WAY && qName.equals("way")) {
                if (currentWay != null) {
                    Log.d("Parse", "endElement:" + qName);
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

    private class NodesParser extends DefaultHandler {
        int level = 0;
        HashMap<String, Node> nodes = new HashMap<>();
        private Set<String> nodesSet;
        private Node currentNode;
        private int oneLevelType = -1;


        public NodesParser(Set<String> nodesSet) {
            this.nodesSet = nodesSet;
        }


        @Override
        public void startDocument() throws SAXException {
            Log.d("Parse", "endDocument:");
            // Тут будет логика реакции на начало документа
        }

        @Override
        public void endDocument() throws SAXException {
            Log.d("Parse", "endDocument:");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            Log.d("Parse", "startElement:" + qName);
            level++;
            if (level == LEVEL_NODE && qName.equals("node")) {
                if (!nodesSet.contains(attributes.getValue("id"))) {
                    currentNode = null;
                    return;
                }
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
                if (currentNode != null) {
                    Log.d("Parse", "endElement:" + qName);
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


    private class RelationParser extends DefaultHandler {
        int level = 0;

        HashMap<String, Relation> relations = new HashMap<>();
        private Relation currentRelation;
        private int oneLevelType = -1;
        private String routeId;

        public RelationParser(String routeId) {
            this.routeId = routeId;
        }


        @Override
        public void startDocument() throws SAXException {
            // Тут будет логика реакции на начало документа
            Log.d("Parse", "startDoc");
        }

        @Override
        public void endDocument() throws SAXException {
            Log.d("Parse", "endDocument");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            level++;
            if (level == LEVEL_RELATION && qName.equals("relation")) {
                if (!routeId.equals(attributes.getValue("id"))) {
                    currentRelation = null;
                    return;
                }
                Log.d("Parse", "startElement:" + qName + "-" + attributes.getValue("id"));
                currentRelation = new Relation();
                oneLevelType = PARSING_RELATION;
                for (int i = 0; i < attributes.getLength(); i++) {
                    String k = attributes.getQName(i);
                    String v = attributes.getValue(i);
                    currentRelation.getAttributes().put(k, v);
                }

            } else if (currentRelation != null) {
                Log.d("Parse", "startElement:" + qName);
                if (level == LEVEL_TAG && qName.equals("tag")) {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        String k = attributes.getQName(i);
                        String v = attributes.getValue(i);
                        currentRelation.getTagAttributes().put(k, v);
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
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // Тут будет логика реакции на конец элемента
            if (level == LEVEL_RELATION && qName.equals("relation")) {
                if (currentRelation != null) {
                    Log.d("Parse", "endElement:" + qName);
                    if ("route".equals(currentRelation.getTagAttributes().get("type"))
                            && currentRelation.getId().equals(routeId)) {
                        relations.put(currentRelation.getId(), currentRelation);
                    }
                }
                currentRelation = null;
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
