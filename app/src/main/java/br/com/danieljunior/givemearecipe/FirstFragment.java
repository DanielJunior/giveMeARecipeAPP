package br.com.danieljunior.givemearecipe;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.danieljunior.givemearecipe.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import br.com.danieljunior.givemearecipe.utils.NetworkUtils;

/**
 * Created by danieljunior on 14/09/16.
 */
public class FirstFragment extends Fragment {

    ProgressDialog progressDialog;
    View myView;
    String[] ingredients = {"Tomate", "Frango", "Macarrão", "Ovo"};
    EditText ingredientsText;
    Button searchBtn;
    final ArrayList<String> ingredientsSelected = new ArrayList();
    Dialog ingredientsForSelect;
    AlertDialog.Builder dialogBuilder;
    ArrayList<String> loadedIngredients;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.first_layout, container, false);
        ingredientsText = (EditText) myView.findViewById(R.id.ingredients);
        searchBtn = (Button) myView.findViewById(R.id.button);
        IngredientsRequestTask task = new IngredientsRequestTask();
        task.execute();
        return myView;
    }

    public void setupListeners() {

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupIngredientsDialog() {
        dialogBuilder = new AlertDialog.Builder(this.getContext());
        dialogBuilder.setTitle("Selecione seus ingredientes : ");
        dialogBuilder.setMultiChoiceItems(ingredients, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            ingredientsSelected.add(ingredients[selectedItemId]);
                        } else if (ingredientsSelected.contains(ingredients[selectedItemId])) {
                            ingredientsSelected.remove(ingredients[selectedItemId]);
                        }
                    }
                })
                .setPositiveButton("Pronto!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String result = "";
                        for (String ingredient : ingredientsSelected) {
                            result += ingredient + ",";
                        }
                        ingredientsText.setText(result);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        ingredientsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsForSelect.show();
            }
        });
    }

    private class IngredientsRequestTask extends AsyncTask<String, Void, ArrayList<String>> {

        //        private static final String HOST = "http://172.28.0.10:3000";
        private static final String HOST = "http://10.2.121.248:3000";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(getActivity(), "Carregando ingredientes...", "Por favor aguarde...", true);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Carregando ingredientes...");
            progressDialog.setMessage("Por favor aguarde...");
            progressDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            ArrayList<String> ingredients = new ArrayList<>();
            try {
                JSONArray json = NetworkUtils.JSONArrayRequest(HOST);
                for (int i = 0; i < json.length(); i++) {
                    ingredients.add((String) json.getJSONObject(i).get("name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // params comes from the execute() call: params[0] is the url.
            return ingredients;
        }

        @Override
        protected void onPostExecute(ArrayList<String> loadedIngredients) {
            super.onPostExecute(loadedIngredients);
            if (!loadedIngredients.isEmpty()) {
                ingredients = loadedIngredients.toArray(new String[0]);
            }
            setupIngredientsDialog();
            setupListeners();
            ingredientsForSelect = dialogBuilder.create();
            progressDialog.dismiss();
        }
    }
}
