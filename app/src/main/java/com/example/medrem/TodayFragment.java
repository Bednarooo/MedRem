package com.example.medrem;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class TodayFragment extends Fragment {

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        TextView initialText = (TextView) view.findViewById(R.id.todayInitialText);
        Button addOneButton = (Button) view.findViewById(R.id.addOneButton);
        Button goBackButton = (Button) view.findViewById(R.id.goBackButton);
        ListView listViewWithChoices = (ListView) view.findViewById(R.id.possibleChoices);
        ArrayList<String> possibleChoicesArray = new ArrayList<>();
        possibleChoicesArray.add("Lek");
        possibleChoicesArray.add("Pomiar");
        ArrayAdapter<String> possibleChoicesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, possibleChoicesArray);
        listViewWithChoices.setAdapter(possibleChoicesAdapter);
        addOneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addOneButton.setVisibility(View.GONE);
                listViewWithChoices.setVisibility(View.VISIBLE);
                goBackButton.setVisibility(View.VISIBLE);
                initialText.setVisibility(View.GONE);

                /*FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");
                user.put("last", "Lovelace");
                user.put("born", 1815);
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });*/
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackButton.setVisibility(View.GONE);
                listViewWithChoices.setVisibility(View.GONE);
                addOneButton.setVisibility(View.VISIBLE);
                initialText.setVisibility(View.VISIBLE);
            }
        });

        listViewWithChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (possibleChoicesArray.get(position)) {
                    case "Pomiar":
                        Intent switchActivitiyToAddingMeasurementActivity = new Intent(getActivity(), AddingMeasurementActivity.class);
                        startActivity(switchActivitiyToAddingMeasurementActivity);
                        break;
                    case "Lek":
                        Intent switchActivityToAddingMedicineActivity = new Intent(getActivity(), AddingMedicineActivity.class);
                        startActivity(switchActivityToAddingMedicineActivity);
                    default:
                        break;
                }
            }
        });
        return view;
    }
}