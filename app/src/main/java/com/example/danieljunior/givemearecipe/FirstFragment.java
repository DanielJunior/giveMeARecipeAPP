package com.example.danieljunior.givemearecipe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by danieljunior on 14/09/16.
 */
public class FirstFragment extends Fragment {

    View myView;
    String[] ingredients = {"Tomate", "Frango", "Macarrão", "Ovo"};
    EditText ingredientsText;
    final ArrayList<String> ingredientsSelected = new ArrayList();
    Dialog dialog;
    AlertDialog.Builder builder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.first_layout, container, false);
        ingredientsText = (EditText) myView.findViewById(R.id.ingredients);

        setupListeners();
        dialog = builder.create();

        return myView;
    }

    public void setupListeners() {
        builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Select yours ingredients : ");
        builder.setMultiChoiceItems(ingredients, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            ingredientsSelected.add(ingredients[selectedItemId]);
                        } else if (ingredientsSelected.contains(ingredients[selectedItemId])) {
                            ingredientsSelected.remove(selectedItemId);
                        }
                    }
                })
                .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String result = "";
                        for (String ingredient : ingredientsSelected) {
                            result += ingredient + ",";
                        }
                        ingredientsText.setText(result);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        ingredientsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
}
