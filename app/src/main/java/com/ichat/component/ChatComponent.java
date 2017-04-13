package com.ichat.component;

import com.ichat.activity.MainActivity;
import com.ichat.adapter.MensagemAdapter;
import com.ichat.module.ChatModule;

import dagger.Component;

/**
 * Created by Douglas on 14/02/2017.
 */

@Component(modules = ChatModule.class)
public interface ChatComponent {

    void inject(MainActivity activity);
    void inject(MensagemAdapter adapter);
}
