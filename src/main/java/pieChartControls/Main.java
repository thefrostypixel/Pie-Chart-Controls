package pieChartControls;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import frostyLib.util.SavedConfig;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.jspecify.annotations.NonNull;

public class Main implements ModInitializer, ModMenuApi {
    public static void log(Object obj) {
        LogManager.getLogger("Pie-Chart-Controls").info(obj);
    }

    @Override public void onInitialize() {
        SavedConfig.load(Config.class);
        SavedConfig.autoSave(Config.class);
    }

    @Override public @NonNull ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::new;
    }

    public static boolean active = false;
}
