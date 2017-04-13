package com.ichat.app;

import android.app.Application;

import com.ichat.component.ChatComponent;
import com.ichat.component.DaggerChatComponent;
import com.ichat.module.ChatModule;

/**
 * Created by Douglas on 14/02/2017.
 */

public class ChatApplication extends Application{

    private ChatComponent component;

    public void onCreate() {
        component = DaggerChatComponent.builder()
                .chatModule(new ChatModule(this))
                .build();

    }

    public ChatComponent getComponent(){
        return component;
    }
}
