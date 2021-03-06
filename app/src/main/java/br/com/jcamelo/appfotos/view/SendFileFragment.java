package br.com.jcamelo.appfotos.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.jcamelo.appfotos.Constants;
import br.com.jcamelo.appfotos.MainActivity;
import br.com.jcamelo.appfotos.R;
import br.com.jcamelo.appfotos.model.AbstractFragment;
import br.com.jcamelo.appfotos.model.ClassFtp;

public class SendFileFragment extends AbstractFragment {

    private ClassFtp conectFTP;
    private List<File> fileListAll;
    private TextView status, desc;
    private MaterialButton buttonSendFtp;
    private ProgressBar progressBar;
    private ImageView check;
    private String displayPorc;
    private Toolbar toolbar;
    private static final int porCent = 100;
    private String ordermServicoCodigo;


    public SendFileFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);


        status = view.findViewById(R.id.text_view_status);
        desc = view.findViewById(R.id.text_view_sending);
        buttonSendFtp = view.findViewById(R.id.button_send_ftp);
        progressBar = view.findViewById(R.id.progressBar);
        check = view.findViewById(R.id.image_view_check);
        toolbar = getActivity().findViewById(R.id.toolbar);

        if (getArguments() != null) {
            ordermServicoCodigo = getArguments().getString("os");
            Log.d("TESTE", ordermServicoCodigo);
        }

        fileListAll = searchFileSend();

        conectFTP = new ClassFtp();
        SendAsync sendAsync = new SendAsync();

        buttonSendFtp.setOnClickListener(v -> {
            if (fileListAll.size() != 0) {
                status.setText("");
                sendAsync.execute();
            } else {
                Toast.makeText(getContext(), "N??o existe arquivo para ser enviado", Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_send_file;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("FTP");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private class SendAsync extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            buttonSendFtp.setEnabled(false);
            desc.setText("Aguarde");
        }

        @Override
        protected String doInBackground(String... strings) {
            boolean access = conectFTP.connect(
                    Constants.hostFtp,
                    Constants.usuarioFtp,
                    Constants.senhaFtp,
                    21);
            try {
                if (access) {
                    for (int i = 0; i < fileListAll.size(); i++) {
                        boolean isSent = conectFTP.upload(fileListAll.get(i).getPath(), fileListAll.get(i).getName());
                        int numberDisplay = (porCent * i) / (fileListAll.size());
                        displayPorc = "Enviando: " + numberDisplay + "%";
                        publishProgress(displayPorc);
                        if (isSent) {
                            deleteFile(fileListAll.get(i));

                        } else {
                            Toast.makeText(getContext(), "Arquivo: " + fileListAll.get(i).getName()
                                            + " n??o pode ser deletado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    conectFTP.disconnect();
                }
            } catch (Exception e){
                Log.d("TESTE", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String text = Arrays.toString(values).replace("[","")
                    .replace("]", "");
            status.setText(text);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            status.setText("100%");
            desc.setText("Processo Finalizado");
            check.setVisibility(View.VISIBLE);


            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                getActivity().getSupportFragmentManager()
                        .popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }, 2000);

        }

    }

    private boolean upload(String path, String name) {
        Log.d("TESTFILESEND", path + name);
        return true;
    }


    private List<File> searchFileSend() {
        List<File> list = new ArrayList<>();
        String[] folder = getResources().getStringArray(R.array.folder);
        if(ordermServicoCodigo != null) {
            for (int i = 0; i < folder.length; i++) {
                String path = getActivity().getExternalFilesDir(folder[i]).toString();
                File directory = new File(path);
                File[] files = directory.listFiles();

                for (File file : files) {
                    String[] segments = file.toString().split("/");
                    String nomeArquivo = segments[segments.length - 1];

                    if (nomeArquivo.startsWith(ordermServicoCodigo)) {
                        list.add(file);
                    }
                }
            }

            String sizeList = "Voc?? tem: " + list.size() + " arquivo para enviar";
            status.setText(sizeList);
        }
        return list;
    }

    private void deleteFile(File file) {
        file.delete();
    }

}
