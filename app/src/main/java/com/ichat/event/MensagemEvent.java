package com.ichat.event;

import com.ichat.modelo.Mensagem;

/**
 * Created by Douglas on 16/02/2017.
 */
public class MensagemEvent {
    public Mensagem mensagem;

    public MensagemEvent(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
}
