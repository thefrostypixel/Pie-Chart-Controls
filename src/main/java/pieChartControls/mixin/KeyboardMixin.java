package pieChartControls.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pieChartControls.accessor.PieChartAccessor;

@Mixin(KeyboardHandler.class)
public class KeyboardMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void keyPress(long handle, int action, KeyEvent event, CallbackInfo cancelable) {
        ((PieChartAccessor) Minecraft.getInstance().getDebugOverlay().getProfilerPieChart()).pieChartControls$onKey(action, event, cancelable);
    }
}
