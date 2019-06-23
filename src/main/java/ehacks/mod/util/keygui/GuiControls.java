package ehacks.mod.util.keygui;

import ehacks.mod.api.ModuleController;
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
    private final GuiScreen parentScreen;
    /**
     * The ID of the button that has been pressed.
     */
    public ModuleKeyBinding currentKeyBinding = null;
    public long time;
    public ModuleKeyBinding[] keyBindings = new ModuleKeyBinding[0];
    protected String screenTitle;
    private GuiKeyBindingList keyBindingList;
    private GuiButton resetButton;

    public GuiControls(GuiScreen parentScreen) {
        super();
        this.parentScreen = parentScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        this.keyBindings = new ModuleKeyBinding[ModuleController.INSTANCE.modules.size()];
        for (int i = 0; i < this.keyBindings.length; i++) {
            this.keyBindings[i] = new ModuleKeyBinding(ModuleController.INSTANCE.modules.get(i));
        }
        this.keyBindingList = new GuiKeyBindingList(this, this.mc);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20, "Done"));
        this.buttonList.add(this.resetButton = new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20, "Reset all"));
        this.screenTitle = "EHacks keybindings";
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
            ConfigurationManager.instance().saveConfigs();
        } else if (button.id == 201) {
            for (int i = 0; i < keyBindings.length; ++i) {
                keyBindings[i].setKeyCode(keyBindings[i].getKeyCodeDefault());
            }
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

    public void setKeyBinding(ModuleKeyBinding key, int keyCode) {
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
        boolean isDefault = true;

        for (int i = 0; i < this.keyBindings.length; ++i) {
            if (this.keyBindings[i].getKeyCode() != this.keyBindings[i].getKeyCodeDefault()) {
                isDefault = false;
                break;
            }
        }

        this.resetButton.enabled = !isDefault;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
