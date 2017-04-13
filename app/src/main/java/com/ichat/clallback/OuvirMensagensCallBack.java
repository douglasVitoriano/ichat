package com.ichat.clallback;

import android.content.Context;

import com.ichat.event.FailureEvent;
import com.ichat.event.MensagemEvent;
import com.ichat.modelo.Mensagem;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Douglas on 14/02/2017.
 */

public class OuvirMensagensCallBack implements Callback<Mensagem> {

    private EventBus bus;
    private Context context;

    public OuvirMensagensCallBack(EventBus bus, Context context) {
        this.bus = bus;
        this.context = context;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {

        if (response.isSuccessful()) {
            Mensagem mensagem = response.body();

            bus.post(new MensagemEvent(mensagem));

        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
        bus.post(new FailureEvent());
    }
}
