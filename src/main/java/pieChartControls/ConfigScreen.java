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
                .ofSelected(Config.activationMode)
                .onCycle(selected -> {
                    Config.activationMode = selected;
                    update();
                })
            ));

        options.add(activationKeybind = new LabeledOption()
            .translatableLabel("pieChartControls.activationKeybind")
            .translatableDescription("pieChartControls.activationKeybind.description")
            .add(new KeybindWidget()
                .keybind(Config.activationKeybind)
                .onSelect(keybind -> Config.activationKeybind = keybind)
            ));

        options.add(displayActivationKeybind = new LabeledOption()
            .translatableLabel("pieChartControls.displayActivationKeybind")
            .translatableDescription("pieChartControls.displayActivationKeybind.description")
            .add(CycleButtonWidget
                .ofSelected(Config.displayActivationKeybind)
                .onCycle(selected -> Config.displayActivationKeybind = selected)
            ));

        options.add(indicateInactivity = new LabeledOption()
            .translatableLabel("pieChartControls.indicateInactivity")
            .translatableDescription("pieChartControls.indicateInactivity.description")
            .add(CycleButtonWidget
                .ofSelected(Config.indicateInactivity)
                .onCycle(selected -> Config.indicateInactivity = selected)
            ));



        options.add(controlScheme = new LabeledOption()
            .translatableLabel("pieChartControls.controlScheme")
            .translatableDescription("pieChartControls.controlScheme.description")
            .add(CycleButtonWidget
                .ofSelected(Config.controlScheme)
                .onCycle(selected -> {
                    Config.controlScheme = selected;
                    update();
                })
            ));


        options.add(controlsHotkeysBack = new LabeledOption()
            .translatableLabel("pieChartControls.controls.hotkeys.back")
            .add(new KeybindWidget()
                .keybind(Config.controlsHotkeysBack)
                .onSelect(keybind -> Config.controlsHotkeysBack = keybind)
            ));


        options.add(controlsCustom = new TitledGroup().collapsable(true).indentChildren(true));

        controlsCustom.add(controlsCustomCount = new LabeledOption()
            .translatableLabel("pieChartControls.controls.custom.count")
            .add(controlsCustomRemove = new ButtonWidget().literalLabel("-").onPress(() -> {
                if (!Config.controlsCustomKeybinds.isEmpty()) {
                    Config.controlsCustomKeybinds.removeLast();
                    update();
                }
            }))
            .add(controlsCustomAdd = new ButtonWidget().literalLabel("+").onPress(() -> {
                Config.controlsCustomKeybinds.add(InputConstants.UNKNOWN);
                update();
            })));

        controlsCustom.add(controlsCustomBack = new LabeledOption()
            .translatableLabel("pieChartControls.controls.custom.back")
            .add(new KeybindWidget()
                .keybind(Config.controlsCustomBack)
                .onSelect(keybind -> Config.controlsCustomBack = keybind)
            ));

        controlsCustom.add(controlsCustomKeybinds = new Group());


        options.add(controlsNavigate = new Group());

        controlsNavigate.add(controlsNavigateBack = new LabeledOption()
            .translatableLabel("pieChartControls.controls.navigate.back")
            .add(new KeybindWidget()
                .keybind(Config.controlsNavigateBack)
                .onSelect(keybind -> Config.controlsNavigateBack = keybind)
            ));

        controlsNavigate.add(controlsNavigateSelect = new LabeledOption()
            .translatableLabel("pieChartControls.controls.navigate.select")
            .add(new KeybindWidget()
                .keybind(Config.controlsNavigateSelect)
                .onSelect(keybind -> Config.controlsNavigateSelect = keybind)
            ));

        controlsNavigate.add(controlsNavigateUp = new LabeledOption()
            .translatableLabel("pieChartControls.controls.navigate.up")
            .add(new KeybindWidget()
                .keybind(Config.controlsNavigateUp)
                .onSelect(keybind -> Config.controlsNavigateUp = keybind)
            ));

        controlsNavigate.add(controlsNavigateDown = new LabeledOption()
            .translatableLabel("pieChartControls.controls.navigate.down")
            .add(new KeybindWidget()
                .keybind(Config.controlsNavigateDown)
                .onSelect(keybind -> Config.controlsNavigateDown = keybind)
            ));



        update();
    }

    public void update() {
        activationKeybind.visible(Config.activationMode != Config.ActivationMode.Simultaneous);
        displayActivationKeybind.visible(Config.activationMode != Config.ActivationMode.Simultaneous);
        indicateInactivity.visible(Config.activationMode != Config.ActivationMode.Simultaneous);

        controlsHotkeysBack.visible(Config.controlScheme == Config.ControlScheme.Hotkeys);

        controlsCustom.visible(Config.controlScheme == Config.ControlScheme.Custom);
        controlsCustomRemove.active = !Config.controlsCustomKeybinds.isEmpty();
        controlsCustomAdd.active = Config.controlsCustomKeybinds.size() < 20;
        controlsCustomKeybinds.clear();
        for (int i = 0; i < Config.controlsCustomKeybinds.size(); i++) {
            int finalI = i;
            controlsCustomKeybinds.add(new LabeledOption()
                .translatableLabel("pieChartControls.controls.custom.number", i + 1 + "")
                .add(new KeybindWidget()
                    .keybind(Config.controlsCustomKeybinds.get(i))
                    .onSelect(keybind -> {
                        Config.controlsCustomKeybinds.remove(finalI);
                        Config.controlsCustomKeybinds.add(finalI, keybind);
                    })
                ));
        }

        controlsNavigate.visible(Config.controlScheme == Config.ControlScheme.Navigate);

        init();
    }
}
