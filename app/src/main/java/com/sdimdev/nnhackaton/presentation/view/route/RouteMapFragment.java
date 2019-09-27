package com.sdimdev.nnhackaton.presentation.view.route;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.di.DIManager;
import com.sdimdev.nnhackaton.di.route.DaggerRouteComponent;
import com.sdimdev.nnhackaton.model.entity.route.Route;
import com.sdimdev.nnhackaton.model.entity.route.RoutePoint;
import com.sdimdev.nnhackaton.model.entity.route.RouteResult;
import com.sdimdev.nnhackaton.presentation.presenter.route.RoutePresenter;
import com.sdimdev.nnhackaton.presentation.view.BaseFragment;
import com.vanniktech.rxpermission.RxPermission;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
public class RouteMapFragment extends BaseFragment implements OnMapReadyCallback, RouteMapView {
    GoogleMap googleMap;
    MarkerOptions[] markers;
    RouteResult routeResult;
    @Inject
    RxPermission rxPermission;
    @Inject
    @InjectPresenter
    RoutePresenter routePresenter;

    View progressBar;
    boolean isEnabledMap = true;

    public static Fragment getInstance() {
        return new RouteMapFragment();
    }

    @ProvidePresenter
    RoutePresenter providePresenter() {
        return routePresenter;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_map_route;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DaggerRouteComponent.builder()
                .appApi(DIManager.get().getAppComponent())
                .build().inject(this);

        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        progressBar = view.findViewById(R.id.progress);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        if (getActivity() != null)
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    return false;
                }
            });
        googleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                if (!isEnabledMap)
                    return;
                /*for (int i = 0; i < markers.length; i++) {
                    if (markers[i].getPosition().equals(marker.getPosition())) {
                        routePresenter.onClinicClicked(list.get(i));
                        return;
                    }
                }*/
            }
        });
        routePresenter.onMapReady();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            rxPermission.requestEach(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
                    .observeOn(AndroidSchedulers.mainThread())
                    .toList()
                    .subscribe();
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void showRoute(RouteResult routeResult) {
        this.routeResult = routeResult;
        if (googleMap != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            //  BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_clinic);
            /*markers = new MarkerOptions[list.size()];
            for (int i = 0; i < list.size(); i++) {
                markers[i] = new MarkerOptions()
                        .position(polyclinic.getCoordinate())
                        .title(polyclinic.getName())
                        .snippet(polyclinic.getAddress())
                        .icon(icon);
                builder.include(polyclinic.getCoordinate());
                googleMap.addMarker(markers[i]);
            }*/
            boolean inculed=false;
            for (Route route : routeResult.getRouteList()) {
                PolylineOptions options = new PolylineOptions();
                options.color(Color.RED);
                options.width(4);
                for (RoutePoint point : route.getRoutePoints()) {
                    LatLng latLng = new LatLng(point.getLat(), point.getLon());
                    options.add(latLng);
                    inculed=true;
                    builder.include(latLng);
                }

                googleMap.addPolyline(options);
            }
            if(inculed) {
                LatLngBounds bounds = builder.build();
                int padding = 20; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.moveCamera(cu);
            }
        }
    }

    @Override
    public void showProgress() {
        new Handler(Looper.getMainLooper())
                .post(() -> {
                    progressBar.setVisibility(View.VISIBLE);
                    isEnabledMap = false;
                });

    }

    @Override
    public void hideProgress() {
        new Handler(Looper.getMainLooper())
                .post(() -> {
                    progressBar.setVisibility(View.GONE);
                    isEnabledMap = true;
                });
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "Ошибка получения данных", Toast.LENGTH_LONG)
                .show();
    }
}
