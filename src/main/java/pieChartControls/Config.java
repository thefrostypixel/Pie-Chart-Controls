package pieChartControls;

import com.mojang.blaze3d.platform.InputConstants;
import frostyLib.util.SavedConfig;
import frostyLib.widget.CycleButtonWidget;

import java.util.ArrayList;
import java.util.List;

@SavedConfig.Path("Pie-Chart-Controls.json")
public class Config {
    @CycleButtonWidget.Cyclable.TranslationPath("pieChartControls.activationMode")
    public enum ActivationMode implements CycleButtonWidget.Cyclable {
        Simultaneous, Hold, Toggle
    }
    public static ActivationMode activationMode = ActivationMode.Simultaneous;
    public static InputConstants.Key activationKeybind = InputConstants.UNKNOWN;
    public static CycleButtonWidget.Togglable displayActivationKeybind = CycleButtonWidget.Togglable.Off;
    public static CycleButtonWidget.Togglable indicateInactivity = CycleButtonWidget.Togglable.On;

    @CycleButtonWidget.Cyclable.TranslationPath("pieChartControls.controlScheme")
    public enum ControlScheme implements CycleButtonWidget.Cyclable {
        Vanilla, Hotkeys, Custom, Navigate
    }
    public static ControlScheme controlScheme = ControlScheme.Vanilla;

    public static InputConstants.Key controlsHotkeysBack = InputConstants.getKey("key.keyboard.0");

    public static InputConstants.Key controlsCustomBack = InputConstants.getKey("key.keyboard.0");
    public static List<InputConstants.Key> controlsCustomKeybinds = new ArrayList<>(List.of(InputConstants.getKey("key.keyboard.1"), InputConstants.getKey("key.keyboard.2"), InputConstants.getKey("key.keyboard.3"), InputConstants.getKey("key.keyboard.4"), InputConstants.getKey("key.keyboard.5"), InputConstants.getKey("key.keyboard.6"), InputConstants.getKey("key.keyboard.7"), InputConstants.getKey("key.keyboard.8"), InputConstants.getKey("key.keyboard.9")));

    public static InputConstants.Key controlsNavigateBack = InputConstants.getKey("key.keyboard.left");
    public static InputConstants.Key controlsNavigateSelect = InputConstants.getKey("key.keyboard.right");
    public static InputConstants.Key controlsNavigateUp = InputConstants.getKey("key.keyboard.up");
    public static InputConstants.Key controlsNavigateDown = InputConstants.getKey("key.keyboard.down");
}
