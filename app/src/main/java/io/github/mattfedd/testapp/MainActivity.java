package io.github.mattfedd.testapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.atilika.kuromoji.TokenizerBase;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.atilika.kuromoji.ipadic.Token;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.*;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // TODO : grab webpage text for nhk news http://www3.nhk.or.jp/news/easy/news-list.json
    // TODO : display text on screen
    // TODO : data structures for main text, tokens, count, categorization, elements, etc
    // TODO : import dictionary
    // TODO : import frequency lists
    // TODO : import JLPT lists
    // TODO : import kanji data
    // TODO : internal tracking of "known" stuff

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "This is a test");

        Tokenizer tkzer = new Tokenizer.Builder().mode(TokenizerBase.Mode.NORMAL).build();

        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("お寿司が食べたい。");
        for (Token token : tokens) {
            Log.v(TAG, token.getSurface() + "\t" + token.getAllFeatures());
        }

        String page = "Nothing";
        ListView lv = (ListView) findViewById(R.id.list_view);
        new GetWebdataTask(lv).execute("http://www3.nhk.or.jp/news/easy/news-list.json");

    }

    private List<JSONObject> createListFromRawJSON(String data) {
        List<JSONObject> result = new ArrayList<JSONObject>();

        // Ideally change method based on JSON source format (NHK Easy, NHK, etc)
        // This is for nhk news easy (http://www3.nhk.or.jp/news/easy/news-list.json)

        try {
            JSONArray jarr = new JSONArray(data);
            int jarr_len = 0;
            JSONObject jobj = ((JSONObject) jarr.get(0));
            Iterator<String> keys1 = jobj.keys();
            while(keys1.hasNext()) {
                String key = keys1.next();
                if(jobj.get(key) instanceof JSONArray) {
                    JSONArray jarr2 = (JSONArray) jobj.get(key);
                    int len = jarr2.length();
                    for(int i=0; i<len; i++){
                        JSONObject jobj2 = (JSONObject) jarr2.get(i);
                        result.add(jobj2);
                        Log.v(TAG, jobj2.toString());
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void buildViewFromJSONList(ListView lv, List<JSONObject> list) {

        ArrayAdapter adapter = new ArrayAdapter<JSONObject>(this, R.layout.activity_listview, list);
        lv.setAdapter(adapter);
//        ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
    }


    private class GetWebdataTask extends AsyncTask<String, Void, String> {
        private ListView lv;

        public GetWebdataTask(ListView lv) {
            this.lv = lv;
        }

        @Override
        protected String doInBackground(String ... strings) {
            String result = "UNDEFINED";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                result = builder.toString();

                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String temp) {
            List<JSONObject> list = createListFromRawJSON(temp);
            buildViewFromJSONList(lv, list);
        }
    }

}



