package com.example.projetintegrateur;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.io.IOException;
public class pageProduit extends AppCompatActivity implements View.OnClickListener {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 103;

    SQLiteManager sqLiteManager;
    SQLiteDatabase sqLiteDatabase;
    Button buttonRetour;
    Button buttonPhoto;
    Button buttonGallerie;
    Button buttonAjouterProduit;
    Button buttonModifier;
    EditText editPrix;
    EditText editNom;
    EditText editDescription;
    EditText editQuantite;
    TextView viewPrix;
    TextView viewNom;
    TextView viewCategorie;
    TextView viewDescription;
    TextView viewQuantite;
    ImageView imageProduit;
    Spinner spinnerCategorie;
    Uri imageGallery;
    int idProduit;
    boolean pictureChanged;

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

        initWidget();
        preparerDb();
        preparerSpinnerCategorie();
        pictureChanged = false;

        idProduit = getIntent().getIntExtra("idProduit", 0);
        if (idProduit != 0) {
            viewProduit();
            //viewProduitAdmin();
        }
    }
    private void initWidget() {
        buttonRetour = (Button) findViewById(R.id.buttonRetour);
        buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
        buttonAjouterProduit = (Button) findViewById(R.id.buttonProduit);
        buttonGallerie = (Button) findViewById(R.id.buttonGallery);
        buttonModifier = (Button) findViewById(R.id.buttonModifier);
        editPrix = (EditText) findViewById(R.id.editPrix);
        editNom = (EditText) findViewById(R.id.editNom);
        editQuantite = (EditText) findViewById(R.id.editQuantite);
        editDescription = (EditText) findViewById(R.id.editDescription);
        spinnerCategorie = (Spinner) findViewById(R.id.spinnerCategorie);
        imageProduit = (ImageView) findViewById(R.id.imageProduit);
        viewNom = (TextView) findViewById(R.id.viewNom);
        viewPrix = (TextView) findViewById(R.id.viewPrix);
        viewCategorie = (TextView) findViewById(R.id.viewCategorie);
        viewDescription = (TextView) findViewById(R.id.viewDescription);
        viewQuantite = (TextView) findViewById(R.id.viewQuantite);

        buttonRetour.setOnClickListener(this);
        buttonPhoto.setOnClickListener(this);
        buttonAjouterProduit.setOnClickListener(this);
        buttonModifier.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonRetour) {
            startActivity(new Intent(pageProduit.this, pageInventaire.class));
        }
        else if (v.getId() == R.id.buttonGallery) {
            chooseImage();
        }
        else if (v.getId() == R.id.buttonPhoto) {
            askCameraPermissions();
        }
        else if (v.getId() == R.id.buttonProduit) {
            Produit nouveauProduit = new Produit();
            imageProduit.buildDrawingCache();

            if (TextUtils.isEmpty(editNom.getText().toString()) || TextUtils.isEmpty(editPrix.getText().toString()) || TextUtils.isEmpty(editDescription.getText().toString())|| TextUtils.isEmpty(editQuantite.getText().toString())) {
                showDialog();
            }
            else {

                nouveauProduit.setNom(editNom.getText().toString());
                nouveauProduit.setPrix(Float.valueOf(editPrix.getText().toString()));
                nouveauProduit.setDescription(editDescription.getText().toString());
                nouveauProduit.setQuantite(Integer.parseInt(editQuantite.getText().toString()));
                nouveauProduit.setIdCategorie(spinnerCategorie.getSelectedItemPosition());
                nouveauProduit.setPhoto(imageProduit.getDrawingCache());

                sqLiteManager.ajouterProduitDatabase(sqLiteDatabase, nouveauProduit);
                startActivity(new Intent(pageProduit.this, pageInventaire.class));
            }
        }
        else if (v.getId() == R.id.buttonModifier) {
            updaterProduit();
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
            }
            else {Toast.makeText(this, "Camera Permission is Required to use Camera", Toast.LENGTH_SHORT).show();}
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Bitmap image = null;
        if(data != null) {

            if (requestCode == CAMERA_REQUEST_CODE) {
                image = (Bitmap) data.getExtras().get("data");
            }
            else if (requestCode == GALLERY_REQUEST_CODE) {
                imageGallery = data.getData();
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageGallery);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            imageProduit.setImageBitmap(image);
            imageProduit.setVisibility(View.VISIBLE);
            pictureChanged = true;
        }
    }
    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_produit);
        dialog.show();
    }
    private void viewProduit(){
        Produit produit = Produit.getProduitById(idProduit);
        buttonAjouterProduit.setVisibility(View.INVISIBLE);
        buttonPhoto.setVisibility(View.INVISIBLE);
        buttonGallerie.setVisibility(View.INVISIBLE);
        buttonAjouterProduit.setEnabled(false);
        buttonPhoto.setEnabled(false);
        buttonGallerie.setEnabled(false);

        editPrix.setVisibility(View.GONE);
        viewPrix.setVisibility(View.VISIBLE);
        viewPrix.setText(String.valueOf(produit.getPrix()));

        editNom.setVisibility(View.GONE);
        viewNom.setVisibility(View.VISIBLE);
        viewNom.setText(produit.getNom());

        spinnerCategorie.setVisibility(View.GONE);
        viewCategorie.setVisibility(View.VISIBLE);
        viewCategorie.setText(Categorie.getCategorieById(produit.getIdCategorie()).toString());

        editDescription.setVisibility(View.GONE);
        viewDescription.setVisibility(View.VISIBLE);
        viewDescription.setText(produit.getDescription());

        editQuantite.setVisibility(View.GONE);
        viewQuantite.setVisibility(View.VISIBLE);
        viewQuantite.setText(String.valueOf(produit.getQuantite()));

        imageProduit.setImageBitmap(produit.getPhoto());
    }

    private void viewProduitAdmin(){
        Produit produit = Produit.getProduitById(idProduit);
        editPrix.setHint(String.valueOf(produit.getPrix()));
        editNom.setHint(produit.getNom());
        spinnerCategorie.setSelection(produit.getIdCategorie());
        editDescription.setHint(produit.getDescription());
        editQuantite.setHint(String.valueOf(produit.getQuantite()));
        imageProduit.setImageBitmap(produit.getPhoto());

        buttonAjouterProduit.setVisibility(View.INVISIBLE);
        buttonAjouterProduit.setEnabled(false);
        buttonModifier.setVisibility(View.VISIBLE);
        buttonModifier.setEnabled(true);
    }

    private void updaterProduit(){
        Produit vieuxProduit = Produit.getProduitById(idProduit);
        imageProduit.buildDrawingCache();

        if (!editNom.getText().toString().isEmpty() || !editPrix.getText().toString().isEmpty() || !editDescription.getText().toString().isEmpty() || !editQuantite.getText().toString().isEmpty() || spinnerCategorie.getSelectedItemPosition() != vieuxProduit.getIdCategorie() || pictureChanged) {
            Produit produitUpdate = new Produit();
            produitUpdate.setId(vieuxProduit.getId());
            //Nom
            if (editNom.getText().toString().isEmpty()) {produitUpdate.setNom(vieuxProduit.getNom());}
            else {produitUpdate.setNom(editNom.getText().toString());}
            //Prix
            if (editPrix.getText().toString().isEmpty()) {produitUpdate.setPrix(vieuxProduit.getPrix());}
            else {produitUpdate.setPrix(Float.valueOf(editPrix.getText().toString()));}
            //Description
            if (editDescription.getText().toString().isEmpty()) {produitUpdate.setDescription(vieuxProduit.getDescription());}
            else {produitUpdate.setDescription(editDescription.getText().toString());}
            //Quantite
            if (editQuantite.getText().toString().isEmpty()) {produitUpdate.setQuantite(vieuxProduit.getQuantite());}
            else {produitUpdate.setQuantite(Integer.parseInt(editQuantite.getText().toString()));}
            //Categorie
            if (spinnerCategorie.getSelectedItemPosition() == vieuxProduit.getIdCategorie()) {vieuxProduit.setIdCategorie(vieuxProduit.getIdCategorie());}
            else {produitUpdate.setIdCategorie(spinnerCategorie.getSelectedItemPosition());}

            produitUpdate.setPhoto(imageProduit.getDrawingCache());
            sqLiteManager.updaterProduitDatabase(sqLiteDatabase, produitUpdate);

        }
        else{
            showDialog();
        }
    }
}