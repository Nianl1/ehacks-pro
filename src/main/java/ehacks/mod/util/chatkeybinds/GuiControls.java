package ehacks.mod.util.chatkeybinds;

import ehacks.mod.config.ConfigurationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiControls extends GuiScreen {

    /**
     * A reference to the screen object that created this. Used for navigating
     * between screens.
     */
    public GuiScreen parentScreen;
    /**
     * The ID of the button that has been pressed.
     */
    public ChatKeyBinding currentKeyBinding = null;
    public long time;
    protected String screenTitle;
    private GuiKeyBindingList keyBindingList;

    public GuiControls(GuiScreen parentScreen) {
        super();
        this.parentScreen = parentScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        this.keyBindingList = new GuiKeyBindingList(this, this.mc);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20, "Done"));
        this.buttonList.add(new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20, "Add"));
        this.screenTitle = "EHacks chat keybindings";
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
            ConfigurationManager.instance().saveConfigs();
        } else if (button.id == 201) {
            this.mc.displayGuiScreen(new GuiAddKeyBinding(this));
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0 || !this.keyBindingList.mouseClicked(mouseX, mouseY, mouseButton)) {
            try {
                super.mouseClicked(mouseX, mouseY, mouseButton);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setKeyBinding(ChatKeyBinding key, int keyCode) {
        key.setKeyCode(keyCode);
    }

    /**
     * Fired when a key is typed (except F11 who toggle full screen). This is
     * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
     * (character on the key), keyCode (lwjgl Keyboard key code)
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (this.currentKeyBinding != null) {
            if (keyCode == 1) {
                setKeyBinding(this.currentKeyBinding, 0);
            } else {
                setKeyBinding(this.currentKeyBinding, keyCode);
            }

            this.currentKeyBinding = null;
            this.time = Minecraft.getSystemTime();
        } else {
            try {
                super.keyTyped(typedChar, keyCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (keyCode == 1) {
                mc.displayGuiScreen(parentScreen);
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY,
     * renderPartialTicks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
