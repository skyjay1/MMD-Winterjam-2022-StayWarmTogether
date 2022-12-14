package staywarmtogether;

import staywarmtogether.entity.Bloomina;
import staywarmtogether.entity.Rimeite;
import staywarmtogether.entity.RimeiteQueen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public final class SWTEvents {

    public static final class ForgeHandler {

        @SubscribeEvent
        public static void onEntityJoinLevel(final EntityJoinLevelEvent event) {
            if(event.getEntity().level.isClientSide()) {
                return;
            }
            // modify goals
            if(event.getEntity() instanceof PathfinderMob mob) {
                // illager and undead attack bloomina
                if(mob.getMobType() == MobType.ILLAGER || mob.getMobType() == MobType.UNDEAD) {
                    mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(mob, Bloomina.class, 15, true, true,
                            e -> (e instanceof Bloomina bloomina && !bloomina.isHiding())));
                }
                // spiders attack rimeite and rimeite queen
                if(mob.getType() == EntityType.SPIDER || mob.getType() == EntityType.CAVE_SPIDER || mob instanceof Spider) {
                    mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(mob, RimeiteQueen.class, true));
                    mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(mob, Rimeite.class, true));
                }
            }
            // lightning strike
            if(event.getEntity() instanceof LightningBolt bolt) {
                // locate nearby bloominas
                AABB aabb = new AABB(new BlockPos(bolt.position())).inflate(48.0D, 16.0D, 48.0D);
                List<Bloomina> list = bolt.level.getEntitiesOfClass(Bloomina.class, aabb);
                // scare each bloomina
                list.forEach(bloomina -> bloomina.scare());
            }

        }
    }

    public static final class ModHandler {

    }
}
