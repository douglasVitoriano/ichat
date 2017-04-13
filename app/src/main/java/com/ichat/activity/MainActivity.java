package com.ichat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ichat.app.ChatApplication;
import com.ichat.R;
import com.ichat.adapter.MensagemAdapter;
import com.ichat.clallback.EnviarMensagemCallBack;
import com.ichat.clallback.OuvirMensagensCallBack;
import com.ichat.component.ChatComponent;
import com.ichat.event.FailureEvent;
import com.ichat.event.MensagemEvent;
import com.ichat.modelo.Mensagem;
import com.ichat.service.ChatService;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Douglas on 09/02/2017.
 */

public class MainActivity extends AppCompatActivity {

    private List<Mensagem> mensagens;

    private ChatComponent component;

    @Inject
    ChatService chatService;

    private int idDoCliente = 1;

    @BindView(R.id.act_main_btn_enviar)
    Button button;
    @BindView(R.id.act_main_et_texto)
    EditText editText;
    @BindView(R.id.lv_mensagem)
    ListView listaDeMensagens;
    @BindView(R.id.act_main_avatar_user)
    ImageView avatar;

    @Inject
    Picasso picasso;

    @Inject
    EventBus eventBus;

    @Inject
    InputMethodManager inputMethodManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        picasso.with(this).load("https://api.adorable.io/avatars/199/" + idDoCliente + ".png").into(avatar);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        if (savedInstanceState != null){
            mensagens = (List<Mensagem>) savedInstanceState.getSerializable("mensagens");
        }else {
            mensagens = new ArrayList<>();
        }

        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);

        listaDeMensagens.setAdapter(adapter);

        Call<Mensagem> mensagemCall = chatService.ouvirMensagens();
        mensagemCall.enqueue(new OuvirMensagensCallBack(eventBus, this));

        eventBus.register(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("mensagens", (ArrayList<Mensagem>) mensagens);
    }

    @OnClick(R.id.act_main_btn_enviar)
    public void enviarMensagem() {
        chatService.enviar(new Mensagem(idDoCliente, editText.getText().toString())).enqueue(new EnviarMensagemCallBack());

        editText.getText().clear();

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Subscribe
    public void colocaNaLista(MensagemEvent mensagemEvent){
        mensagens.add(mensagemEvent.mensagem);
        MensagemAdapter adapter= new MensagemAdapter(idDoCliente, mensagens, this);

        listaDeMensagens.setAdapter(adapter);
    }

    @Subscribe
    public void ouvirMensagem(MensagemEvent mensagemEvent){
        Call<Mensagem> mensagemCall = chatService.ouvirMensagens();
        mensagemCall.enqueue(new OuvirMensagensCallBack(eventBus, this));
    }

    @Subscribe
    public void lidarCom(FailureEvent event) {
        ouvirMensagem(null);
    }

    @Override
    protected void onStop() {
        super.onStop();

        eventBus.unregister(this);
    }
}
