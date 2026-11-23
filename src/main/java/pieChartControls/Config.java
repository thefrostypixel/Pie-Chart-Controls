package pieChartControls;

import com.mojang.blaze3d.platform.InputConstants;
import frostyLib.util.SavedConfig;
import frostyLib.widget.CycleButtonWidget;

import java.util.ArrayList;
import java.util.List;

@SavedConfig.Path("Pie-Chart-Controls.json")
public class Config implements SavedConfig {
    @CycleButtonWidget.Cyclable.TranslationPath("pieChartControls.activationMode")
    public enum ActivationMode implements CycleButtonWidget.Cyclable {
        Simultaneous, Hold, Toggle
    }
    public ActivationMode activationMode = ActivationMode.Simultaneous;
    public InputConstants.Key activationKeybind = InputConstants.UNKNOWN;
    public CycleButtonWidget.Togglable displayActivationKeybind = CycleButtonWidget.Togglable.Off;
    public CycleButtonWidget.Togglable indicateInactivity = CycleButtonWidget.Togglable.On;

    @CycleButtonWidget.Cyclable.TranslationPath("pieChartControls.controlScheme")
    public enum ControlScheme implements CycleButtonWidget.Cyclable {
        Vanilla, Hotkeys, Custom, Navigate
    }
    public ControlScheme controlScheme = ControlScheme.Vanilla;

    public InputConstants.Key controlsHotkeysBack = InputConstants.getKey("key.keyboard.0");

    public InputConstants.Key controlsCustomBack = InputConstants.getKey("key.keyboard.0");
    public List<InputConstants.Key> controlsCustomKeybinds = new ArrayList<>(List.of(InputConstants.getKey("key.keyboard.1"), InputConstants.getKey("key.keyboard.2"), InputConstants.getKey("key.keyboard.3"), InputConstants.getKey("key.keyboard.4"), InputConstants.getKey("key.keyboard.5"), InputConstants.getKey("key.keyboard.6"), InputConstants.getKey("key.keyboard.7"), InputConstants.getKey("key.keyboard.8"), InputConstants.getKey("key.keyboard.9")));

    public InputConstants.Key controlsNavigateBack = InputConstants.getKey("key.keyboard.left");
    public InputConstants.Key controlsNavigateSelect = InputConstants.getKey("key.keyboard.right");
    public InputConstants.Key controlsNavigateUp = InputConstants.getKey("key.keyboard.up");
    public InputConstants.Key controlsNavigateDown = InputConstants.getKey("key.keyboard.down");
}
