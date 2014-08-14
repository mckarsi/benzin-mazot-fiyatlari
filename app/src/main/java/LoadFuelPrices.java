import android.os.AsyncTask;

/**
 * Created by mert.karsi on 14.08.2014.
 */
public class LoadFuelPrices extends AsyncTask<Params, Progress, Result>{

    private Exception exception;

    protected void doInBackground(String... urls) {
        try {
            URL url= new URL(urls[0]);
            SAXParserFactory factory =SAXParserFactory.newInstance();
            SAXParser parser=factory.newSAXParser();
            XMLReader xmlreader=parser.getXMLReader();
            RssHandler theRSSHandler=new RssHandler();
            xmlreader.setContentHandler(theRSSHandler);
            InputSource is=new InputSource(url.openStream());
            xmlreader.parse(is);
            return theRSSHandler.getFeed();
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(RSSFeed feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
