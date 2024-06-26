/****************************************
 Fichier : pageProduit
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page qui gère l'ajout, la modification, ou le visionement des produits
 Date : 2024-08-03

 Vérification :
 2024-05-23         Jasmin Dubuc        Approuvé
 =========================================================

 Historique de modifications :
 2024-05-27         Jasmin Dubuc        Preparation finales et ajout pour merge qui n'arrivera pas
 =========================================================
 ****************************************/
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
    TextView textHeader;
    SQLiteManager sqLiteManager;
    SQLiteDatabase sqLiteDatabase;
    Button buttonRetour;
    Button buttonPhoto;
    Button buttonGallerie;
    Button buttonAjouterProduit;
    Button buttonModifier;
    Button buttonAjouterPanier;
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
    //int idClient;
    boolean pictureChanged;
    InitButton initButton = new InitButton();
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
        loadFromDbToMemory();
        preparerSpinnerCategorie();
        pictureChanged = false;
        textHeader = findViewById(R.id.textHeader);

        idProduit = getIntent().getIntExtra("idProduit", 0);
        //idClient = getIntent().getIntExtra("idClient", 1);

        //Si aucun idProduit, c'est l'admin qui en rajoute un nouveau
        if (idProduit != 0) {
            //viewProduit();
            viewProduitAdmin();
            textHeader.setText(R.string.pageModifierProduit);
        }
        else {
            textHeader.setText(R.string.pageAjouterProduit);
        }
        /*
        //si idProduit != 0, c'est quelqu'un qui a cliqué sur le ListView
        if (idProduit != 0) {
            //(idClient == 0) == admin
            if (idClient == 0) {
                viewProduitAdmin();
                textHeader.setText("R.string.pageModifierProduit");
            }
            //(idClient != 0) == client
            else {
                viewProduit();
                textHeader.setText("R.string.pageVoirProduit");
            }
        }
        //Si on a passé les conditions du dernier if, ça veut dire que c'est l'admin qui a appuyé sur buttonAjouterProduit
        else {
            textHeader.setText("R.string.pageAjouterProduit");
        }
        */
    }
    private void initWidget() {
        buttonRetour = findViewById(R.id.buttonRetour);
        buttonPhoto = findViewById(R.id.buttonPhoto);
        buttonAjouterProduit = findViewById(R.id.buttonProduit);
        buttonAjouterPanier = findViewById(R.id.buttonAjouterPanier);
        buttonGallerie = findViewById(R.id.buttonGallery);
        buttonModifier =  findViewById(R.id.buttonModifier);
        editPrix = findViewById(R.id.editPrix);
        editNom = findViewById(R.id.editNom);
        editQuantite = findViewById(R.id.editQuantite);
        editDescription = findViewById(R.id.editDescription);
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        imageProduit = findViewById(R.id.imageProduit);
        viewNom = findViewById(R.id.viewNom);
        viewPrix = findViewById(R.id.viewPrix);
        viewCategorie = findViewById(R.id.viewCategorie);
        viewDescription = findViewById(R.id.viewDescription);
        viewQuantite = findViewById(R.id.viewQuantite);

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
            ajouterProduitDatabase();
        }
        else if (v.getId() == R.id.buttonModifier) {
            updaterProduit();
            startActivity(new Intent(pageProduit.this, pageInventaire.class));
        }
        else if(v.getId() == R.id.buttonAjouterPanier) {
            /*
            ProduitCommande nouveauProduitCommande = new ProduitCommande();
            nouveauProduitCommande.setIdProduit(idProduit);
            nouveauProduitCommande.setIdClient(Bright.getIdClient());
            sqLiteManager.ajouterProduitCommandeDatabase(sqLiteDatabase, nouveauProduitCommande);
            */
        }
    }
    public void preparerSpinnerCategorie() {
        ArrayAdapter<Categorie> categorieAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_list, Categorie.categorieArrayList);
        categorieAdapter.setDropDownViewResource(R.layout.my_spinner_list);
        spinnerCategorie.setAdapter(categorieAdapter);
    }
    public void loadFromDbToMemory(){
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteDatabase = sqLiteManager.getReadableDatabase();
        sqLiteManager.populateCategorieListArray();
        sqLiteManager.populateProduitListArray();
    }
    public void ajouterProduitDatabase() {
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
    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else {openCamera();}
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
    private void viewProduit() {
        Produit produit = Produit.getProduitById(idProduit);
        buttonAjouterProduit.setVisibility(View.GONE);
        buttonPhoto.setVisibility(View.GONE);
        buttonGallerie.setVisibility(View.GONE);
        buttonAjouterPanier.setVisibility(View.VISIBLE);
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
        viewCategorie.setText(Categorie.getCategorieById(produit.getIdCategorie() + 1).toString());

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

        buttonAjouterProduit.setVisibility(View.GONE);
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
            if (spinnerCategorie.getSelectedItemPosition() == vieuxProduit.getIdCategorie() && vieuxProduit.getIdCategorie() != 0) {produitUpdate.setIdCategorie(vieuxProduit.getIdCategorie());}
            else {produitUpdate.setIdCategorie(spinnerCategorie.getSelectedItemPosition());}

            produitUpdate.setPhoto(imageProduit.getDrawingCache());
            sqLiteManager.updaterProduitDatabase(sqLiteDatabase, produitUpdate);

        }
        else{
            showDialog();
        }
    }

    public void bInit(View v){
        initButton.click(pageProduit.this, v);
    }
}