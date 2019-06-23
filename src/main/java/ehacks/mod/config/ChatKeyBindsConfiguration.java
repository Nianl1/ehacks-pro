/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.config;

import com.google.gson.Gson;
import ehacks.mod.util.chatkeybinds.ChatKeyBinding;
import ehacks.mod.util.chatkeybinds.ChatKeyBindingHandler;
import ehacks.mod.wrapper.Wrapper;

import java.io.*;
import java.util.ArrayList;

/**
 * @author radioegor146
 */
public class ChatKeyBindsConfiguration implements IConfiguration {

    public static ChatKeyBindsConfigJson config = new ChatKeyBindsConfigJson();

    private final File configFile;

    public ChatKeyBindsConfiguration() {
        this.configFile = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/chatkeybinds.json");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.configFile);
            config.keybindings = ChatKeyBindingHandler.INSTANCE.keyBindings;
            try (BufferedWriter bufferedwriter = new BufferedWriter(filewriter)) {
                bufferedwriter.write(new Gson().toJson(config));
            }
        } catch (Exception exception) {
        }
    }

    @Override
    public void read() {
        try {
            FileInputStream inputstream = new FileInputStream(this.configFile.getAbsolutePath());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            config = new Gson().fromJson(bufferedreader.readLine(), ChatKeyBindsConfigJson.class);
            ChatKeyBindingHandler.INSTANCE.keyBindings = config.keybindings;
        } catch (Exception ex) {
        }
    }

    @Override
    public String getConfigFilePath() {
        return "chatkeybinds.json";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }

    public static class ChatKeyBindsConfigJson {

        public ArrayList<ChatKeyBinding> keybindings = new ArrayList<>();
    }
}
