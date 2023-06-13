package com.kiselev.jumba.middle;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.kiselev.jumba.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kiselev.jumba.middle.favourited.FavouritedFragment;
import com.kiselev.jumba.middle.order.OrderFragment;
import com.kiselev.jumba.middle.profile.ProfileFragment;
import com.kiselev.jumba.middle.store.StoreFragment;

    public class BottomBarStart extends Fragment {
        Fragment window = null;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View ret = inflater.inflate(R.layout.bottom_nav_controller, container, false);
            window = new StoreFragment();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.frame_layout, window);
            ft.commit();
            BottomNavigationView bottomNavigationView = ret.findViewById(R.id.nav_view);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.navigation_store) {
                    window = new StoreFragment();
                } else if (id == R.id.navigation_orders) {
                    window = new OrderFragment();
                } else if (id == R.id.navigation_favourited) {
                    window = new FavouritedFragment();
                } else if (id == R.id.navigation_profile) {
                    window = new ProfileFragment();
                }
                FragmentTransaction ft1 = getChildFragmentManager().beginTransaction();
                ft1.addToBackStack(null);
                ft1.replace(R.id.frame_layout, window);
                ft1.commit();
                return true;
            });
            return ret;
        }
    }
