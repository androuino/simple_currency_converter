package com.intellisrc.mobiledeveloperchallenge.ui;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.intellisrc.mobiledeveloperchallenge.R;
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseFragment;
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseKey;
import com.intellisrc.mobiledeveloperchallenge.ui.main.MainActivity;
import com.zhuinden.simplestack.StateChange;

import java.util.List;

// FIXME: Note: Recompile with -Xlint:unchecked for details.
public class FragmentStateChanger {
    private final MainActivity activity;
    private final FragmentManager fragmentManager;
    private final int containerId;

    public FragmentStateChanger(MainActivity activity, FragmentManager fragmentManager, int containerId) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    public void handleStateChange(StateChange stateChange) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().disallowAddToBackStack();
        if (stateChange.getDirection() == StateChange.FORWARD) {
            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left);
        } else if (stateChange.getDirection() == StateChange.BACKWARD) {
            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right);
        }

        List<BaseKey<?>> previousState = stateChange.getPreviousKeys();
        List<BaseKey<?>> newState = stateChange.getNewKeys();
        for (BaseKey oldKey : previousState) {
            BaseFragment fragment = (BaseFragment) fragmentManager.findFragmentByTag(oldKey.getFragmentTag());
            if (fragment != null) {
                if (!newState.contains(oldKey)) {
                    fragmentTransaction.remove(fragment);
                } else if (!fragment.isDetached()) {
                    fragmentTransaction.detach(fragment);
                }
            }
        }
        for (BaseKey newKey : newState) {
            BaseFragment fragment = (BaseFragment) fragmentManager.findFragmentByTag(newKey.getFragmentTag());
            if (newKey.equals(stateChange.topNewKey())) {
                if (fragment != null) {
                    if (fragment.isRemoving()) { // Fragments are quirky, they die asynchronously. Ignore if they're still there.
                        fragment = newKey.newFragment();
                        fragmentTransaction.replace(containerId, fragment, newKey.getFragmentTag());
                    } else if(fragment.isDetached()) {
                        fragmentTransaction.attach(fragment);
                    }
                } else {
                    fragment = newKey.newFragment();
                    fragmentTransaction.add(containerId, fragment, newKey.getFragmentTag());
                }
            } else {
                if( fragment != null && !fragment.isDetached()) {
                    fragmentTransaction.detach(fragment);
                }
            }
            if (fragment != null) {
                // noinspection unchecked
                fragment.bindViewModel(activity.backstackDelegate.getService(newKey, newKey.getViewModelTag()));
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
