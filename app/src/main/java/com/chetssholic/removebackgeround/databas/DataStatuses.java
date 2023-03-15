package com.chetssholic.removebackgeround.databas;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;



public class DataStatuses {
    private DatabaseReference databaseReference;

    public DataStatuses() {


        databaseReference = FirebaseDatabase.getInstance().getReference(PhotoChanger.class.getSimpleName());

    }
    public  DatabaseReference getdata()
    {
        return databaseReference.child("PhotoChanger");
    }



    public Task<Void> adddata(PhotoChanger status) {


        return  databaseReference.child("PhotoChanger").push().setValue(status);

    }


    public Task<Void> update(String key, HashMap<String, Object> hashmap) {

        return databaseReference.child("PhotoChanger").child(key).updateChildren(hashmap);


    }


}
