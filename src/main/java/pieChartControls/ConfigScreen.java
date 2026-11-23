package pieChartControls;

import com.mojang.blaze3d.platform.InputConstants;
import frostyLib.option.*;
import frostyLib.widget.ButtonWidget;
import frostyLib.widget.CycleButtonWidget;
import frostyLib.widget.KeybindWidget;
import net.minecraft.client.gui.screens.Screen;

public class ConfigScreen extends OptionListScreen {
    public LabeledOption activationMode;
    public LabeledOption activationKeybind;
    public LabeledOption displayActivationKeybind;
    public LabeledOption indicateInactivity;

    public LabeledOption controlScheme;

    public LabeledOption controlsHotkeysBack;

    public Group controlsCustom;
    public LabeledOption controlsCustomCount;
    public ButtonWidget controlsCustomRemove;
    public ButtonWidget controlsCustomAdd;
    public LabeledOption controlsCustomBack;
    public Group controlsCustomKeybinds;

    public Group controlsNavigate;
    public LabeledOption controlsNavigateBack;
    public LabeledOption controlsNavigateSelect;
    public LabeledOption controlsNavigateUp;
    public LabeledOption controlsNavigateDown;

    public ConfigScreen(Screen parent) {
        super(parent, "pieChartControls");



        options.add(activationMode = new LabeledOption()
            .translatableLabel("pieChartControls.activationMode")
            .translatableDescription("pieChartControls.activationMode.description")
            .add(CycleButtonWidget
                .ofSelected(Main.config.activationMode)
                .onCycle(selected -> {
                    Main.config.activationMode = selected;
                    update();
                })
            ));

        options.add(activationKeybind = new LabeledOption()
            .translatableLabel("pieChartControls.activationKeybind")
            .translatableDescription("pieChartControls.activationKeybind.description")
            .add(new KeybindWidget()
                .keybind(Main.config.activationKeybind)
                .onSelect(keybind -> Main.config.activationKeybind = keybind)
            ));

        options.add(displayActivationKeybind = new LabeledOption()
            .translatableLabel("pieChartControls.displayActivationKeybind")
            .translatableDescription("pieChartControls.displayActivationKeybind.description")
            .add(CycleButtonWidget
                .ofSelected(Main.config.displayActivationKeybind)
                .onCycle(selected -> Main.config.displayActivationKeybind = selected)
            ));

        options.add(indicateInactivity = new LabeledOption()
            .translatableLabel("pieChartControls.indicateInactivity")
            .translatableDescription("pieChartControls.indicateInactivity.description")
            .add(CycleButtonWidget
                .ofSelected(Main.config.indicateInactivity)
                .onCycle(selected -> Main.config.indicateInactivity = selected)
            ));



        options.add(controlScheme = new LabeledOption()
            .translatableLabel("pieChartControls.controlScheme")
            .translatableDescription("pieChartControls.controlScheme.description")
            .add(CycleButtonWidget
                .ofSelected(Main.config.controlScheme)
                .onCycle(selected -> {
                    Main.config.controlScheme = selected;
                    update();
                })
            ));


        options.add(controlsHotkeysBack = new LabeledOption()
            .translatableLabel("pieChartControls.controls.hotkeys.back")
            .add(new KeybindWidget()
                .keybind(Main.config.controlsHotkeysBack)
                .onSelect(keybind -> Main.config.controlsHotkeysBack = keybind)
            ));


        options.add(controlsCustom = new TitledGroup().collapsable(true).indentChildren(true));

        controlsCustom.add(controlsCustomCount = new LabeledOption()
            .translatableLabel("pieChartControls.controls.custom.count")
            .add(controlsCustomRemove = new ButtonWidget().literalLabel("-").onPress(() -> {
                if (!Main.config.controlsCustomKeybinds.isEmpty()) {
                    Main.config.controlsCustomKeybinds.removeLast();
                    update();
                }
            }))
            .add(controlsCustomAdd = new ButtonWidget().literalLabel("+").onPress(() -> {
                Main.config.controlsCustomKeybinds.add(InputConstants.UNKNOWN);
                update();
            })));

        controlsCustom.add(controlsCustomBack = new LabeledOption()
            .translatableLabel("pieChartControls.controls.custom.back")
            .add(new KeybindWidget()
                .keybind(Main.config.controlsCustomBack)
                .onSelect(keybind -> Main.config.controlsCustomBack = keybind)
            ));

        controlsCustom.add(controlsCustomKeybinds = new Group());


        options.add(controlsNavigate = new Group());

        controlsNavigate.add(controlsNavigateBack = new LabeledOption()
            .translatableLabel("pieChartControls.controls.navigate.back")
            .add(new KeybindWidget()
                .keybind(Main.config.controlsNavigateBack)
                .onSelect(keybind -> Main.config.controlsNavigateBack = keybind)
            ));

        controlsNavigate.add(controlsNavigateSelect = new LabeledOption()
            .translatableLabel("pieChartControls.controls.navigate.select")
            .add(new KeybindWidget()
                .keybind(Main.config.controlsNavigateSelect)
                .onSelect(keybind -> Main.config.controlsNavigateSelect = keybind)
            ));

        controlsNavigate.add(controlsNavigateUp = new LabeledOption()
            .translatableLabel("pieChartControls.controls.navigate.up")
            .add(new KeybindWidget()
                .keybind(Main.config.controlsNavigateUp)
                .onSelect(keybind -> Main.config.controlsNavigateUp = keybind)
            ));

        controlsNavigate.add(controlsNavigateDown = new LabeledOption()
            .translatableLabel("pieChartControls.controls.navigate.down")
            .add(new KeybindWidget()
                .keybind(Main.config.controlsNavigateDown)
                .onSelect(keybind -> Main.config.controlsNavigateDown = keybind)
            ));



        update();
    }

    public void update() {
        activationKeybind.visible(Main.config.activationMode != Config.ActivationMode.Simultaneous);
        displayActivationKeybind.visible(Main.config.activationMode != Config.ActivationMode.Simultaneous);
        indicateInactivity.visible(Main.config.activationMode != Config.ActivationMode.Simultaneous);

        controlsHotkeysBack.visible(Main.config.controlScheme == Config.ControlScheme.Hotkeys);

        controlsCustom.visible(Main.config.controlScheme == Config.ControlScheme.Custom);
        controlsCustomRemove.active = !Main.config.controlsCustomKeybinds.isEmpty();
        controlsCustomAdd.active = Main.config.controlsCustomKeybinds.size() < 20;
        controlsCustomKeybinds.clear();
        for (int i = 0; i < Main.config.controlsCustomKeybinds.size(); i++) {
            int finalI = i;
            controlsCustomKeybinds.add(new LabeledOption()
                .translatableLabel("pieChartControls.controls.custom.number", i + 1 + "")
                .add(new KeybindWidget()
                    .keybind(Main.config.controlsCustomKeybinds.get(i))
                    .onSelect(keybind -> {
                        Main.config.controlsCustomKeybinds.remove(finalI);
                        Main.config.controlsCustomKeybinds.add(finalI, keybind);
                    })
                ));
        }

        controlsNavigate.visible(Main.config.controlScheme == Config.ControlScheme.Navigate);

        init();
    }
}
