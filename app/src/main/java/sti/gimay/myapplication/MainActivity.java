package sti.gimay.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "MainActivity";
    private boolean toggleButtonValue = false;
    private DatabaseReference mDatabase; //variable for Database Reference

    //additional
    private DatabaseReference conditionRef;
    private DatabaseReference statusRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().setTitle("Toggle Demo");

        final ToggleButton toggleButton = findViewById(R.id.toggle_button);

        toggleButtonValue = toggleButton.isChecked();

        toggleButton.setOnCheckedChangeListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot(); // init Database Ref: rice-e-rrigate-test

        conditionRef = mDatabase.child("arduinoDevice");
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Device arduino = dataSnapshot.getValue(Device.class);
                Log.d(TAG, " "+arduino.getWaterLow());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }
        });
        statusRef = mDatabase.child("ledStatus");
        statusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               int signal = dataSnapshot.getValue(int.class);
                Log.d(TAG, " "+signal);
                if(signal == 1)
                    toggleButton.setChecked(true);
                else
                  toggleButton.setChecked(false);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        int value = 0;

        switch (id){
            case R.id.toggle_button:
                toggleButtonValue = isChecked;
                value = isChecked ? 1 : 0;
                break;
        }
        //mDatabase.child("arduinoDevice").child("switch").setValue(value); // Insert Data to Realtime Database: arduinoDevice/switch=value;
        mDatabase.child("ledStatus").setValue(value);
    }
}
