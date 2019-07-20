package com.sdimdev.nnhackaton.di.app;

import com.sdimdev.nnhackaton.model.repository.SearchRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@Module
public class SearchGlobalModule {
    @Singleton
    @Provides
    SearchRepository provideSearchRepository() {
        return new SearchRepository() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }
}
