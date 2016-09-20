package br.com.danieljunior.givemearecipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.danieljunior.givemearecipe.R;

/**
 * Created by danieljunior on 14/09/16.
 */
public class WelcomeFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.welcome_layout, container, false);
        return myView;
    }
}
