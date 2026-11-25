package pieChartControls.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import frostyLib.util.Keybind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.debugchart.ProfilerPieChart;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ResultField;
import org.spongepowered.asm.mixin.*;

import org.spongepowered.asm.mixin.injection.callback.Cancellable;
import pieChartControls.Config;
import pieChartControls.Main;
import pieChartControls.accessor.PieChartAccessor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Mixin(ProfilerPieChart.class)
public abstract class PieChartMixin implements PieChartAccessor {
    @Final @Shadow private Font font;
    @Shadow private ProfileResults profilerPieChartResults;
    @Shadow private String profilerTreePath;

    @Overwrite public void profilerPieChartKeyPress(int key) {}

    @Unique private int darken(int color) {
        return color & 0xFF000000 | ((int) (.5 * ((color >> 16) & 0xFF)) << 16) | ((int) (.5 * ((color >> 8) & 0xFF)) << 8) | (int) (.5 * (color & 0xFF));
    }

    @Overwrite public void extractRenderState(GuiGraphicsExtractor graphics/*? if >=26.3 {*/, int scaledScreenWidth, int scaledScreenHeight/*?}*/) {
        if (this.profilerPieChartResults != null) {
            List<ResultField> fields = this.profilerPieChartResults.getTimes(this.profilerTreePath);
            double globalPercentage = fields.removeFirst().globalPercentage;
            int right = graphics.guiWidth() - 10;
            int left = right - 210;
            int bottom = graphics.guiHeight() - 10;
            int statsTop = bottom - fields.size() * this.font.lineHeight;
            graphics.fill(left - 5, statsTop - 134, right + 5, bottom + 5, 0x90505050);
            graphics.profilerChart(fields, left, statsTop - 119, right, statsTop - 5);
            DecimalFormat decimalFormat = new DecimalFormat("##0.00");
            decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
            boolean active = Config.activationMode == Config.ActivationMode.Simultaneous || Main.active || !Config.indicateInactivity.bool;
            Component prefix = "root".equals(this.profilerTreePath) ? Component.empty()
                : Config.controlScheme == Config.ControlScheme.Vanilla ? Component.literal("0")
                : Config.controlScheme == Config.ControlScheme.Hotkeys ? Config.controlsHotkeysBack.getDisplayName()
                : Config.controlScheme == Config.ControlScheme.Custom ? Config.controlsCustomBack.getDisplayName()
                : Component.literal("<");
            graphics.text(this.font, prefix, left - 2, statsTop - 129, active ? 0xFFFFFFFF : darken(0xFFFFFFFF));
            graphics.text(this.font, this.profilerTreePath.replace("\u001E", "."), left + this.font.width(prefix), statsTop - 129, CommonColors.WHITE);
            String usage = decimalFormat.format(globalPercentage) + "%";
            graphics.text(this.font, usage, right - this.font.width(usage), statsTop - 129, CommonColors.WHITE);
            selected = Math.max(Math.min(selected, fields.size() - 1), 0);
            int x = this.font.width("?");
            List<Component> prefixes = new ArrayList<>();
            if (Config.controlScheme == Config.ControlScheme.Navigate) {
                x = Math.max(x, this.font.width(">"));
            } else {
                for (int i = 0; i < Math.min(Config.controlScheme == Config.ControlScheme.Custom ? Config.controlsCustomKeybinds.size() : 9, fields.size()); ++i) {
                    prefixes.add(prefix = Config.controlScheme == Config.ControlScheme.Vanilla ? Component.literal(i + 1 + "")
                        : Config.controlScheme == Config.ControlScheme.Hotkeys ? Keybind.key(Minecraft.getInstance().options.keyHotbarSlots[i]).getDisplayName()
                        : Config.controlsCustomKeybinds.get(i).getDisplayName());
                    x = Math.max(x, this.font.width(prefix));
                }
            }
            if (Config.activationMode != Config.ActivationMode.Simultaneous && Config.displayActivationKeybind.bool) {
                graphics.text(this.font, Config.activationKeybind.getDisplayName()/*.copy().withStyle(style -> style.withBold(Main.active))*/, left - 2, statsTop - 2 - this.font.lineHeight, 0xFFDDDDDD);
            }
            for (int i = 0; i < fields.size(); ++i) {
                int y = statsTop + i * this.font.lineHeight;
                ResultField timing = fields.get(i);
                if (Config.controlScheme == Config.ControlScheme.Navigate) {
                    if (i == selected) {
                        graphics.text(this.font, "unspecified".equals(timing.name) ? "?" : ">", left - 2, y, active ? timing.getColor() : darken(timing.getColor()));
                    }
                } else if (i < prefixes.size()) {
                    graphics.text(this.font, "unspecified".equals(timing.name) ? Component.literal("?") : prefixes.get(i), left - 2, y, active ? timing.getColor() : darken(timing.getColor()));
                }
                graphics.text(this.font, timing.name, left + x, y, timing.getColor());
                String sectionUsage = decimalFormat.format(timing.percentage) + "%";
                graphics.text(this.font, sectionUsage, right - this.font.width(sectionUsage) - 50, y, timing.getColor());
                String totalUsage = decimalFormat.format(timing.globalPercentage) + "%";
                graphics.text(this.font, totalUsage, right - this.font.width(totalUsage), y, timing.getColor());
            }
        }
    }

    @Unique private int selected = 0;

    @Override @Unique public void pieChartControls$onKey(int action, InputWithModifiers event, Cancellable cancellable) {
        if (Minecraft.getInstance()./*? if <26.2 {*//*screen*//*?} else {*/gui.screen()/*?}*/ == null && Minecraft.getInstance().getDebugOverlay().showProfilerChart()) {
            if (Keybind.matches(event, Config.activationKeybind)) {
                if (Config.activationMode == Config.ActivationMode.Hold) {
                    if (action == InputConstants.PRESS) {
                        Main.active = true;
                    } else if (action == InputConstants.RELEASE) {
                        Main.active = false;
                    }
                } else if (Config.activationMode == Config.ActivationMode.Toggle && action == InputConstants.PRESS) {
                    Main.active = !Main.active;
                }
            } else if ((action == InputConstants.PRESS || action == InputConstants.REPEAT) && (Config.activationMode == Config.ActivationMode.Simultaneous || Main.active)) {
                boolean acted = false;
                if (Config.controlScheme == Config.ControlScheme.Vanilla) {
                    if (Keybind.matches(event, InputConstants.getKey("key.keyboard.0"))) {
                        pieChartControls$back();
                        acted = true;
                    } else {
                        int index = Keybind.index(event, InputConstants.getKey("key.keyboard.1"), InputConstants.getKey("key.keyboard.2"), InputConstants.getKey("key.keyboard.3"), InputConstants.getKey("key.keyboard.4"), InputConstants.getKey("key.keyboard.5"), InputConstants.getKey("key.keyboard.6"), InputConstants.getKey("key.keyboard.7"), InputConstants.getKey("key.keyboard.8"), InputConstants.getKey("key.keyboard.9"));
                        if (index > -1) {
                            pieChartControls$select(index);
                            acted = true;
                        }
                    }
                } else if (Config.controlScheme == Config.ControlScheme.Hotkeys) {
                    if (Keybind.matches(event, Config.controlsHotkeysBack)) {
                        pieChartControls$back();
                        acted = true;
                    } else {
                        for (int i = 0; i < Minecraft.getInstance().options.keyHotbarSlots.length; i++) {
                            if (Keybind.matches(event, Minecraft.getInstance().options.keyHotbarSlots[i])) {
                                pieChartControls$select(i);
                                acted = true;
                            }
                        }
                    }
                } else if (Config.controlScheme == Config.ControlScheme.Custom) {
                    if (Keybind.matches(event, Config.controlsCustomBack)) {
                        pieChartControls$back();
                        acted = true;
                    } else {
                        int index = Keybind.index(event, Config.controlsCustomKeybinds);
                        if (index > -1) {
                            pieChartControls$select(index);
                            acted = true;
                        }
                    }
                } else if (Keybind.matches(event, Config.controlsNavigateUp)) {
                    selected--;
                    acted = true;
                } else if (Keybind.matches(event, Config.controlsNavigateDown)) {
                    selected++;
                    acted = true;
                } else if (Keybind.matches(event, Config.controlsNavigateBack)) {
                    pieChartControls$back();
                    acted = true;
                } else if (Keybind.matches(event, Config.controlsNavigateSelect)) {
                    pieChartControls$select(selected);
                    acted = true;
                }
                if (acted && Config.activationMode != Config.ActivationMode.Simultaneous) {
                    cancellable.cancel();
                }
            }
        }
    }

    @Unique private void pieChartControls$select(int index) {
        if (this.profilerPieChartResults != null) {
            List<ResultField> fields = this.profilerPieChartResults.getTimes(this.profilerTreePath);
            if (index + 1 < fields.size() && !"unspecified".equals(fields.get(index + 1).name)) {
                this.profilerTreePath += "\u001E" + fields.get(index + 1).name;
                selected = 0;
            }
        }
    }

    @Unique private void pieChartControls$back() {
        if (this.profilerPieChartResults != null && !"root".equals(this.profilerTreePath)) {
            String parent = this.profilerTreePath.replaceFirst("\u001E[^\u001E]*$", "");
            List<ResultField> fields = this.profilerPieChartResults.getTimes(parent);
            String selectedName = this.profilerTreePath.replaceFirst(".*\u001E", "");
            for (int i = 0; i + 1 < fields.size(); i++) {
                if (selectedName.equals(fields.get(i + 1).name)) {
                    this.profilerTreePath = parent;
                    selected = i;
                    return;
                }
            }
        }
    }
}
