package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.utils.Utils;
import net.minecraftforge.event.entity.living.LivingEvent;

public class AntiKnockback extends Module {
    public AntiKnockback() {
        super("AntiKnockback", ModuleCategory.combat);
    }

    @Subscribe
    public void onLivingUpdate(ForgeEvent event) {
        if (event.getEvent() instanceof LivingEvent.LivingUpdateEvent) {
            if (Utils.Player.isPlayerInGame() && mc.thePlayer.maxHurtTime > 0 && mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime) {
                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump();
            }
        }
    }
}
