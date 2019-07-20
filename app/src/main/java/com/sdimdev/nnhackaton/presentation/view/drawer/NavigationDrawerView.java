package com.sdimdev.nnhackaton.presentation.view.drawer;

import android.support.annotation.IntegerRes;
import android.support.annotation.MenuRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.presentation.Screens;

public interface NavigationDrawerView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void selectMenuItem(MenuItem menuItem);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void prepareMenu(@MenuRes int menuId);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError(Throwable th);

    enum MenuItem {
        SEARCH_SCREEN(Screens.SEARCH_SCREEN, R.id.nav_route);

        private final int menuId;
        private final String screen;

        MenuItem(String screen, int menuId) {
            this.screen = screen;
            this.menuId = menuId;
        }

        public static MenuItem forScreen(String screen) {
            for (MenuItem menuItem : MenuItem.values()) {
                if (menuItem.screen.equals(screen))
                    return menuItem;
            }
            return MenuItem.SEARCH_SCREEN;
        }

        public static MenuItem forId(@IntegerRes int resId) {
            for (MenuItem menuItem : MenuItem.values()) {
                if (menuItem.menuId == resId)
                    return menuItem;
            }
            return MenuItem.SEARCH_SCREEN;
        }

        public int getMenuId() {
            return menuId;
        }

        public String getScreen() {
            return screen;
        }
    }
}
