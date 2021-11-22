package com.activeminders.onlinegymapp.Gym.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Adapters.MachinesAdapter;
import com.activeminders.onlinegymapp.Models.Machines;
import com.activeminders.onlinegymapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class MachineFragment extends Fragment {

   //Show list of Machines code
    private RecyclerView machinesrecyclerview;
    private List<Machines> machinesList;
    private Button addMachines;
    private DatabaseReference machinesRf;
    private Machines machines;
    private MachinesAdapter adapter;
    String currentGym;


    //Upload Machines Dialog Box code
    ImageView machineimage;
    Uri uri;
    Uri reportUri;
    StorageTask storageTask;
    String imagUrl="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_machines, container, false);

        machinesrecyclerview=root.findViewById(R.id.machinesRV);
        machinesrecyclerview.setHasFixedSize(true);
        machinesrecyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
        machinesList=new ArrayList<>();
        currentGym=FirebaseAuth.getInstance().getCurrentUser().getUid();
        addMachines=root.findViewById(R.id.ad_machine);

        machinesRf=FirebaseDatabase.getInstance().getReference().child("Gyms").child(currentGym).child("Machines");
        machinesRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        machines=dataSnapshot.getValue(Machines.class);
                        machinesList.add(machines);
                    }
                    adapter=new MachinesAdapter(machinesList,getContext());
                    machinesrecyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addMachines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMachines();
            }
        });

        return root;
    }

    public void AddMachines(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_machine_layout,null);
        EditText ed_machinename = (EditText) view.findViewById(R.id.machinename);
        EditText ed_machinequantity = (EditText) view.findViewById(R.id.quantity);
        machineimage = (ImageView)view.findViewById(R.id.machineimage);
        TextView tv_machineimage = (TextView) view.findViewById(R.id.imgtext);
        Button addmachine=view.findViewById(R.id.addmachinebtn);


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        String filePathAndName = "Machine_Images/" + "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

        progressDialog.setTitle("Adding Machine");
        progressDialog.setMessage("Please wait, while we are adding machine to your list");
        progressDialog.setCanceledOnTouchOutside(false);
        tv_machineimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1000);
            }
        });

        addmachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machinename=ed_machinename.getText().toString();
                String quantity=ed_machinequantity.getText().toString();
                if (machinename.equals("")){
                    Toast.makeText(getContext(), "Please Enter Machine Name", Toast.LENGTH_SHORT).show();
                }else if (quantity.equals("")){
                    Toast.makeText(getContext(), "Please Enter Machine Quantity", Toast.LENGTH_SHORT).show();
                }else if (uri==null){
                    Toast.makeText(getContext(), "Please Select A Machine Image", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();

                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                reportUri = uriTask.getResult();
                                if (uriTask.isSuccessful()) {

                                    DatabaseReference machineRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Machines");
                                    HashMap machineMap=new HashMap();
                                    String key=machineRef.push().getKey();
                                    machineMap.put("machinename",machinename);
                                    machineMap.put("quantity",quantity);
                                    machineMap.put("machineimage",""+reportUri);
                                    machineMap.put("key",key);
                                    machineRef.child(key).setValue(machineMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressDialog.dismiss();
                                                Toast.makeText(getContext(), "Maschine "+machinename+" Added", Toast.LENGTH_SHORT).show();
                                                ed_machinename.setText("");
                                                ed_machinequantity.setText("");
                                                machineimage.setImageResource(R.color.bluebutton);
                                            }else {
                                                progressDialog.dismiss();
                                                Toast.makeText(getContext(), "Maschine "+machinename+" Not Added", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Error..try again", Toast.LENGTH_SHORT).show();
                                }

                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Image not uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }
        });
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog here
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Add ok operation here
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                uri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(uri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                machineimage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }
}