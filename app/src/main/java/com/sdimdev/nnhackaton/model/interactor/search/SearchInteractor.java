package com.sdimdev.nnhackaton.model.interactor.search;

import com.sdimdev.nnhackaton.model.repository.SearchRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
public class SearchInteractor {
    SearchRepository searchRepository;


    public SearchInteractor(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;

    }

    public Single<List<String>> getAddressesByText(String text) {
        return Single.just(new ArrayList<>());
    }

}
