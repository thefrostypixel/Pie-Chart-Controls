package pieChartControls.accessor;

import net.minecraft.client.input.InputWithModifiers;
import org.spongepowered.asm.mixin.injection.callback.Cancellable;

public interface PieChartAccessor {
    void pieChartControls$onKey(int action, InputWithModifiers event, Cancellable cancellable);
}
