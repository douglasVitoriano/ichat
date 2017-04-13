package com.ichat.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ichat.R;
import com.ichat.modelo.Mensagem;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Douglas on 09/02/2017.
 */

public class MensagemAdapter extends BaseAdapter {

    private List<Mensagem> mensagens;
    private Activity activity;
    private int idDoCliente;

    @BindView(R.id.act_msg_tv_texto)
    TextView texto;
    @BindView(R.id.act_msg__iv_avatar_msg)
    ImageView avatar;

    @Inject
    Picasso picasso;

    public MensagemAdapter(int idDoCliente, List<Mensagem> mensagens, Activity activity){
        this.idDoCliente = idDoCliente;
        this.mensagens = mensagens;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Object getItem(int position) {
        return mensagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha =  activity.getLayoutInflater().inflate(R.layout.activity_mensagem, parent, false);

        ButterKnife.bind(this, linha);

        Mensagem mensagem = (Mensagem) getItem(position);

        int idMensagem = mensagem.getId();

        picasso.with(activity).load("https://api.adorable.io/avatars/199/" + idMensagem + ".png").into(avatar);

        if (idDoCliente != mensagem.getId()){
            linha.setBackgroundColor(Color.CYAN);
        }
        texto.setText(mensagem.getTexto());

        return linha;
    }
}
