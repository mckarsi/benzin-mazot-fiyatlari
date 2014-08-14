package util;

import android.os.AsyncTask;

import org.xml.sax.XMLReader;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by mert.karsi on 14.08.2014.
 */
public class LoadFuelPrices extends AsyncTask<String, Void, Boolean>{

    private Exception exception;

    @Override
    protected Boolean doInBackground(String... urls) {
        try {

        } catch (Exception e) {
            this.exception = e;

        }
        return null;
    }
/*
    protected void onPostExecute(RSSFeed feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
*/
}
