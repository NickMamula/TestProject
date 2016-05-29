package ekkleric.com.vk.ukrbash;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    JSONObject jsonobject;
    JSONArray jsonarray;
    LinearLayout view;
    TextView dialogTvTime;
    TextView dialogTvTitle;
    ImageView dialogImage;
    Random random = new Random();
    int index;
    RecyclerView rv;
    CustomAdapter myadapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    PopupTask pt;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");


    ArrayList<Worldpopulation> worldpopulations = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        new DownloadJSON().execute();
        pt = new PopupTask();
        pt.execute();


    }


    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(MainActivity.this);

            mProgressDialog.setTitle("TestProject");


            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);

            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            arraylist = new ArrayList<HashMap<String, String>>();

            jsonobject = JSONfunctions
                    .getJSONfromURL("http://www.androidbegin.com/tutorial/jsonparsetutorial.txt");

            try {

                jsonarray = jsonobject.getJSONArray("worldpopulation");

                for (int i = 0; i < jsonarray.length(); i++) {
                    Worldpopulation worldpopulation = new Worldpopulation();
                    Log.d("TTT", jsonarray.getJSONObject(i).toString());

                    jsonobject = jsonarray.getJSONObject(i);

                    worldpopulation.rank = jsonobject.getInt("rank");
                    worldpopulation.population = jsonobject.getString("country");
                    worldpopulation.flag = jsonobject.getString("flag");

                    worldpopulations.add(worldpopulation);
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            rv = (RecyclerView) findViewById(R.id.rv);


            LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
            rv.setLayoutManager(llm);

            myadapter = new CustomAdapter(MainActivity.this, worldpopulations);
            rv.setAdapter(myadapter);
            for (int i = 0; i < worldpopulations.size(); i++) {
                Log.d("TTT", worldpopulations.get(i).rank + " " + worldpopulations.get(i).population +
                        " " + worldpopulations.get(i).flag);
            }
            mProgressDialog.dismiss();
        }
    }

    class PopupTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(120);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.FullHeightDialog);

            view = (LinearLayout) getLayoutInflater()
                    .inflate(R.layout.dialog, null);

            builder.setView(view);


            dialogTvTime = (TextView) view.findViewById(R.id.dialogTvTime);
            dialogTvTime.setText(sdf.format(new Date(System.currentTimeMillis())));

            dialogTvTitle = (TextView) view.findViewById(R.id.dialogTvTitle);
            dialogTvTitle.setText(worldpopulations.get(index).population);

            dialogImage = (ImageView) view.findViewById(R.id.dialogIv);
            ImageLoader imageLoader = new ImageLoader(getApplicationContext());
            imageLoader.DisplayImage(worldpopulations.get(index).flag, dialogImage);


            builder.setCancelable(false)
                    .setPositiveButton("Так",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();
                                    pt = new PopupTask();
                                    pt.execute();
                                    index = (worldpopulations.size());
                                    index = random.nextInt(index);


                                }
                            });
            AlertDialog alert = builder.create();


            alert.show();


        }
    }


}