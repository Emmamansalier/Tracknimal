package com.eseo.fr.tracknimal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static String resultatWiki;
    private static String idParam;
    private static String resultatParam;
    private static String resultatImage;

    public static TextView indNomText;
    public static TextView indEspeceText;
    public static TextView indSexeText;
    public static TextView indAgeText;
    public static TextView indTailleText;
    public static TextView indMilieuText;
    public static TextView indCommText;

    public static TextView indAB;
    public static TextView indAC;
    public static TextView indAD;
    public static TextView indAE;
    public static TextView indAF;
    public static TextView indAG;
    public static TextView indAH;

    public static TextView indBB;
    public static TextView indBC;
    public static TextView indBD;
    public static TextView indBE;
    public static TextView indBF;
    public static TextView indBG;
    public static TextView indBH;

    public static ImageView indImage;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton indBtnA = findViewById(R.id.indivButtonA);
        ImageButton indBtnB = findViewById(R.id.indivButtonB);

        indNomText = findViewById(R.id.nom);
        indEspeceText = findViewById(R.id.espece);
        indSexeText = findViewById(R.id.sexe);
        indAgeText = findViewById(R.id.age);
        indTailleText = findViewById(R.id.taille);
        indMilieuText = findViewById(R.id.milieu);
        indCommText = findViewById(R.id.commentaire);

        indAB = findViewById(R.id.identifParamA);
        indAC = findViewById(R.id.totaleParamA);
        indAD = findViewById(R.id.minuteParamA);
        indAE = findViewById(R.id.actuelleParamA);
        indAF = findViewById(R.id.moyenneParamA);
        indAG = findViewById(R.id.maximaleParamA);
        indAH = findViewById(R.id.tailleParamA);

        indBB = findViewById(R.id.identifParamB);
        indBC = findViewById(R.id.totaleParamB);
        indBD = findViewById(R.id.minuteParamB);
        indBE = findViewById(R.id.actuelleParamB);
        indBF = findViewById(R.id.moyenneParamB);
        indBG = findViewById(R.id.maximaleParamB);
        indBH = findViewById(R.id.tailleParamB);

        indImage = findViewById(R.id.imageWiki);

        indBtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiParam webServiceParam = new ApiParam("1");
                webServiceParam.execute();
                ApiWiki webServiceWiki = new ApiWiki("1");
                webServiceWiki.execute();
                ApiPhoto webServicePhoto = new ApiPhoto("1");
                webServicePhoto.execute();
            }
        });
        indBtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiParam webServiceParam = new ApiParam("2");
                webServiceParam.execute();
                ApiWiki webServiceWiki = new ApiWiki("2");
                webServiceWiki.execute();
                ApiPhoto webServicePhoto = new ApiPhoto("2");
                webServicePhoto.execute();
            }
        });

        mHandler = new Handler();
        startRepeatingTask();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                ApiParam webServiceParamA = new ApiParam("1");
                webServiceParamA.execute();
                ApiParam webServiceParamB = new ApiParam("2");
                webServiceParamB.execute();
            } finally {
                mHandler.postDelayed(mStatusChecker, 2000);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    public static void getWikiData(String data) {
        try {
            JSONObject json = new JSONObject(data);
            resultatWiki = json.getString("message");
            if (resultatWiki.equals("succes")) {
                indNomText.setText(json.getString("nomIndividu"));
                indEspeceText.setText("Espèce : "+json.getString("espece"));
                indSexeText.setText("Sexe : "+json.getString("sexe"));
                indAgeText.setText("Age : "+json.getString("age")+" ans");
                indTailleText.setText("Taille : "+json.getString("taille"));
                indMilieuText.setText("Milieu : "+json.getString("milieu"));
                indCommText.setText("Commentaire : "+json.getString("commentaire"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getParamData(String data) {
        try {
            JSONObject json = new JSONObject(data);
            resultatParam = json.getString("message");
            if (resultatParam.equals("succes")) {
                idParam = json.getString("idPoisson");
                if (idParam.equals("1")) {
                    indAB.setText(json.getString("identification"));
                    indAC.setText(json.getString("distance"));
                    indAD.setText(json.getString("distanceMinute"));
                    indAE.setText(json.getString("vitesse"));
                    indAF.setText(json.getString("vitesseMoyenne"));
                    indAG.setText(json.getString("vitesseMaximum"));
                    indAH.setText(json.getString("taille"));
                } else if (idParam.equals("2")) {
                    indBB.setText(json.getString("identification"));
                    indBC.setText(json.getString("distance"));
                    indBD.setText(json.getString("distanceMinute"));
                    indBE.setText(json.getString("vitesse"));
                    indBF.setText(json.getString("vitesseMoyenne"));
                    indBG.setText(json.getString("vitesseMaximum"));
                    indBH.setText(json.getString("taille"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getPhotoData(String data, Bitmap img) {
        try {
            JSONObject json = new JSONObject(data);
            resultatImage = json.getString("message");
            if (resultatImage.equals("succes")) {
                indImage.setImageBitmap(img);
            } else {
                indImage.setImageResource(R.drawable.indiv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}