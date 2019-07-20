package com.sdimdev.nnhackaton.data.route;

import com.sdimdev.nnhackaton.model.entity.route.RouteResult;
import com.sdimdev.nnhackaton.model.repository.RouteRepository;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.reactivex.Single;

public class TestRouteRepositoryImpl implements RouteRepository {
    private String relationId = "269601";
    @Override
    public Single<RouteResult> getRoute() {
        return null;
    }
    private void loadNodes(){


    }
}
