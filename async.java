

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.Date;




public class downloadFile extends AsyncTask<String , Integer,  String> {

    private Context context;
    private final String DIR = "Pics";
    private  ProgressDialog pDialog = null;
    private Activity activity;

    public downloadFile(Context context , Activity activity) {
        this.context = context;
        this.activity = activity;
        initDialog();

    }

    @Override
    protected String doInBackground(String... params) {
        String fileName = "file" + String.valueOf(new Date().getTime());
        File direct =
                new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .getAbsolutePath() + "/" + DIR + "/" );

        direct.mkdirs();
        DownloadManager downloadManager = (DownloadManager) this.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(params[0]);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
               .setAllowedOverRoaming(true).setTitle(fileName).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , File.pathSeparator +  DIR + File.pathSeparator
                + fileName);
        downloadManager.enqueue(request);

        return "Completed";

    }

    @Override
    protected void onPostExecute(String s) {
      if(s.equals("Completed")) {
            Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
        }

    }

    @Override
    protected void onPreExecute() {
       pDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        pDialog.setProgress(values[0]);
    }



    public void initDialog(){
        pDialog = new ProgressDialog(activity , R.style.Custom);
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(true);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

    }












}
