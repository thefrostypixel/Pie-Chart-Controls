package pieChartControls;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.jspecify.annotations.NonNull;

public class Main implements ModInitializer, ModMenuApi {
    public static void log(Object obj) {
        LogManager.getLogger("Pie-Chart-Controls").info(obj);
    }

    public static Config config;

    @Override
    public void onInitialize() {
        config = new Config().load().autoSave();
    }

    @Override
    public @NonNull ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::new;
    }

    public static boolean active = false;
}
