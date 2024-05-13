package com.example.projetintegrateur;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

public class pageProduit extends AppCompatActivity implements View.OnClickListener {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 103;

    SQLiteManager sqLiteManager;
    SQLiteDatabase sqLiteDatabase;
    Button buttonRetour;
    Button buttonPhoto;
    ImageView imageProduit;
    EditText editPrix;
    EditText editNom;
    Spinner spinnerCategorie;
    EditText editDescription;
    EditText editQuantite;
    Button buttonAjouterProduit;
    Uri imageGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_produit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonRetour = (Button) findViewById(R.id.buttonRetour);
        buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
        buttonAjouterProduit = (Button) findViewById(R.id.buttonAjouterProduit);
        spinnerCategorie = (Spinner) findViewById(R.id.spinnerCategorie);
        imageProduit = (ImageView) findViewById(R.id.imageProduit);
        editPrix = (EditText) findViewById(R.id.editPrix);
        editNom = (EditText) findViewById(R.id.editNom);
        editQuantite = (EditText) findViewById(R.id.editQuantite);
        editDescription = (EditText) findViewById(R.id.editDescription);

        preparerDb();
        preparerSpinnerCategorie();

        buttonRetour.setOnClickListener(this);
        buttonPhoto.setOnClickListener(this);
        buttonAjouterProduit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonRetour) {
            Intent intent = new Intent(pageProduit.this, pageInventaire.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.buttonGallery) {
            chooseImage();
        }
        else if (v.getId() == R.id.buttonPhoto) {
            askCameraPermissions();
        }
        else if (v.getId() == R.id.buttonAjouterProduit) {
            try {
                Produit nouveauProduit = new Produit();
                imageProduit.buildDrawingCache();

                if (editNom.toString().isEmpty() || editPrix.toString().isEmpty() || editDescription.toString().isEmpty() || editQuantite.toString().isEmpty()) {
                    throw new EmptyField();
                }
                nouveauProduit.setNom(editNom.getText().toString());
                nouveauProduit.setPrix(Float.valueOf(editPrix.getText().toString()));
                nouveauProduit.setDescription(editDescription.getText().toString());
                nouveauProduit.setQuantite(Integer.parseInt(editQuantite.getText().toString()));
                nouveauProduit.setIdCategorie(spinnerCategorie.getSelectedItemPosition());
                nouveauProduit.setPhoto(imageProduit.getDrawingCache());

                sqLiteManager.ajouterProduitDatabase(sqLiteDatabase, nouveauProduit);
                Intent intent = new Intent(pageProduit.this, pageInventaire.class);
                startActivity(intent);
            }
            catch (EmptyField e) {
                e.printStackTrace();
            }
        }
    }

    public void preparerSpinnerCategorie() {
        ArrayAdapter<Categorie> categorieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Categorie.categorieArrayList);
        spinnerCategorie.setAdapter(categorieAdapter);
    }

    public void preparerDb(){
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteDatabase = sqLiteManager.getReadableDatabase();
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else {
            openCamera();
        }
    }
    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    private void chooseImage() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission is Required to use Camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Bitmap image = null;
        assert data != null;

        if(requestCode == CAMERA_REQUEST_CODE){
            image = (Bitmap) data.getExtras().get("data");
        }
        else if (requestCode == GALLERY_REQUEST_CODE) {
            imageGallery = data.getData();
            try {image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageGallery);}
            catch (IOException e) {e.printStackTrace();}
        }
        imageProduit.setImageBitmap(image);
        imageProduit.setVisibility(View.VISIBLE);

    }

}