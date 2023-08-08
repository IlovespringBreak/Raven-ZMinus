package keystrokesmod.client.module.modules.player;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.LookEvent;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ButtonSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Derp extends Module {
    private final TickSetting TimerMethod;
    private final TickSetting HoverSimulation;

    public Derp() {
        super("Derp", ModuleCategory.player);
        this.registerSetting(TimerMethod = new TickSetting("Timer Method", false));
        this.registerSetting(HoverSimulation = new TickSetting("Hover Simulation", false));
    }

    @Subscribe
    public void lookEvent(LookEvent event) {
        int random = (int) (Math.random() * 20) - 10;
        int random2 = (int) (Math.random() * 150) - 100;
        if (!TimerMethod.isToggled() && HoverSimulation.isToggled()) {
            event.setPitch(event.getPitch() + random);
            event.setYaw(event.getYaw() + random2);
        }
    }

    @Subscribe
    public void onRotationUpdate(UpdateEvent event) {
        int random = (int) (Math.random() * 20) - 10;
        int random2 = (int) (Math.random() * 150) - 100;
        if (!TimerMethod.isToggled()) {
            event.setPitch(event.getPitch() + random);
            event.setYaw(event.getYaw() + random2);
            mc.thePlayer.rotationYawHead = event.getYaw() + random2;
            mc.thePlayer.renderYawOffset = event.getYaw() + random2;
        } else {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(event.getYaw() + random2, event.getPitch() + random, mc.thePlayer.onGround));
        }
    }
}