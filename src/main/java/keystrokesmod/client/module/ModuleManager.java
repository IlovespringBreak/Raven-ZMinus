package keystrokesmod.client.module;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module.ModuleCategory;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.module.modules.combat.*;
import keystrokesmod.client.module.modules.config.ConfigSettings;
import keystrokesmod.client.module.modules.minigames.SumoFences;
import keystrokesmod.client.module.modules.movement.*;
import keystrokesmod.client.module.modules.other.*;
import keystrokesmod.client.module.modules.player.*;
import keystrokesmod.client.module.modules.render.*;
import keystrokesmod.client.module.modules.world.*;
import keystrokesmod.client.module.modules.client.*;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.gui.FontRenderer;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public static boolean initialized;
    public GuiModuleManager guiModuleManager;

    public ModuleManager() {
        System.out.println(ModuleCategory.values());
        if(initialized)
            return;
        this.guiModuleManager = new GuiModuleManager();
        addModule(new ChestStealer());
        addModule(new CustomDisabler());
        addModule(new AutoArmour());
        addModule(new Scaffold());
        addModule(new LeftClicker());
        addModule(new AimAssist());
        addModule(new ClickAssist());
        addModule(new DelayRemover());
        addModule(new HitBox());
        addModule(new Reach());
        addModule(new RightClicker());
        addModule(new Velocity());
        addModule(new Disabler());
        addModule(new Fly());
        addModule(new InvMove());
        addModule(new KeepSprint());
        addModule(new NoSlow());
        addModule(new Sprint());
        addModule(new AntiKnockback());
        addModule(new LegitSpeed());
        addModule(new Timer());
        addModule(new VClip());
        addModule(new AutoPlace());
        addModule(new BedAura());
        addModule(new FastPlace());
        addModule(new Freecam());
        addModule(new NoFall());
        addModule(new SafeWalk());
        addModule(new AntiBot());
        addModule(new Chams());
        addModule(new ChestESP());
        addModule(new ClientSpoof());
        addModule(new Nametags());
        addModule(new Derp());
        addModule(new PlayerESP());
        addModule(new HUD());
        addModule(new NoHurtCam());
        addModule(new SlyPort());
        addModule(new FakeChat());
        addModule(new WaterBucket());
        addModule(new Terminal());
        addModule(new GuiModule());
        addModule(new SelfDestruct());
        addModule(new AntiShuffle());
        addModule(new BridgeAssist());
        addModule(new Fullbright());
        addModule(new UpdateCheck());
        addModule(new No003s());
        addModule(new AutoTool());
        addModule(new Blink());
        addModule(new WTap());
        addModule(new BHop());
        addModule(new InstantStop());
        addModule(new MiddleClick());
        addModule(new Projectiles());
        addModule(new FakeHud());
        addModule(new ConfigSettings());
        addModule(new JumpReset());
        addModule(new KillAura());
        addModule(new Targets());
        addModule(new SumoFences());
        addModule(new Spin());
        initialized = true;
    }

    public void addModule(Module m) {
        modules.add(m);
    }

    public void removeModuleByName(String s) {
        Module m = getModuleByName(s);
        modules.remove(m);
    }

    // prefer using getModuleByClazz();
    // ok might add in 1.0.18
    public Module getModuleByName(String name) {
        if (!initialized)
            return null;

        for (Module module : modules)
			if (module.getName().replaceAll(" ", "").equalsIgnoreCase(name) || module.getName().equalsIgnoreCase(name))
                return module;
        return null;
    }

    public Module getModuleByClazz(Class<? extends Module> c) {
        if (!initialized)
            return null;

        for (Module module : modules)
			if (module.getClass().equals(c))
                return module;
        return null;
    }

    public List<Module> getModules() {
        ArrayList<Module> allModules = new ArrayList<>(modules);
        try {
            allModules.addAll(Raven.configManager.configModuleManager.getConfigModules());
        } catch (NullPointerException ignored) {
        }
        try {
            allModules.addAll(guiModuleManager.getModules());
        } catch (NullPointerException ignored) {
        }
        return allModules;
    }

    public List<Module> getConfigModules() {
        List<Module> modulesOfC = new ArrayList<>();

        for (Module mod : getModules())
			if (!mod.isClientConfig())
				modulesOfC.add(mod);

        return modulesOfC;
    }

    public List<Module> getClientConfigModules() {
        List<Module> modulesOfCC = new ArrayList<>();

        for (Module mod : getModules())
			if (mod.isClientConfig())
				modulesOfCC.add(mod);

        return modulesOfCC;
    }

    public List<Module> getModulesInCategory(Module.ModuleCategory categ) {
        ArrayList<Module> modulesOfCat = new ArrayList<>();

        for (Module mod : getModules())
			if (mod.moduleCategory().equals(categ))
				modulesOfCat.add(mod);

        return modulesOfCat;
    }

    public void sort() {
        if (HUD.customFont.isToggled()) {
            modules.sort((o1, o2) -> (int) (FontUtil.two.getStringWidth(o2.getName()) - FontUtil.two.getStringWidth(o1.getName())));
        } else {
            modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
        }
    }

    public int numberOfModules() {
        return modules.size();
    }

    public void sortLongShort() {
        if (HUD.customFont.isToggled()) {
            modules.sort(Comparator.comparingInt(o2 -> (int) FontUtil.two.getStringWidth(o2.getName())));
        } else {
            modules.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())));
        }
    }

    public void sortShortLong() {
        if (HUD.customFont.isToggled()) {
            modules.sort((o1, o2) -> (int) (FontUtil.two.getStringWidth(o2.getName()) - FontUtil.two.getStringWidth(o1.getName())));
        } else {
            modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
        }
    }

    public int getLongestActiveModule(FontRenderer fr) {
        int length = 0;
        for (Module mod : modules)
			if (mod.isEnabled())
				if (fr.getStringWidth(mod.getName()) > length)
					length = fr.getStringWidth(mod.getName());
        return length;
    }

    public int getLongestActiveModuleCustom() {
        int length = 0;
        for (Module mod : modules)
            if (mod.isEnabled())
                if (FontUtil.two.getStringWidth(mod.getName()) > length)
                    length = (int) FontUtil.two.getStringWidth(mod.getName());
        return length;
    }

    public int getBoxHeight(FontRenderer fr, int margin) {
        int length = 0;
        for (Module mod : modules)
			if (mod.isEnabled())
				length += fr.FONT_HEIGHT + margin;
        return length;
    }
    public int getBoxHeightCustom(int margin) {
        int length = 0;
        for (Module mod : modules)
            if (mod.isEnabled())
                length += FontUtil.two.getHeight() + margin;
        return length;
    }

}
