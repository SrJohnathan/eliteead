package ead.elite.app.br.com.appelite.ead.net.download;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;

/**
 * Created by PC on 16/04/2016.
 */
public class GetDownloads {

    private DownloadManager downloadManager;
    private DownloadManager.Request request;
    private Context context;
    private String url;
    private long code;
    private File direct;

    public GetDownloads() {
    }

    public static boolean getfile(String nome){






        return false;
    }

    public GetDownloads(Context context, String url, String title, String descrition, String nome) {

         direct = new File( "/EliteEad");

        this.url = url;
        this.context = context;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        request = new DownloadManager.Request(uri);
        request.setTitle(title);
        request.setDescription(descrition);
        if (direct.isDirectory()) {
            request.setDestinationInExternalPublicDir(direct.getAbsolutePath(), nome+".pdf");
        } else {
            direct.mkdirs();
            request.setDestinationInExternalPublicDir(direct.getAbsolutePath(), nome+".pdf");
        }
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);



    }


    public void downloadManager() {



        code = downloadManager.enqueue(request);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(code);
        Cursor cursor = downloadManager.query(query);
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);


    }


}
