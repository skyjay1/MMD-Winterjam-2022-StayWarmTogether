package hearthjaw;

import hearthjaw.block.HearthgoopBlock;
import hearthjaw.entity.Hearthjaw;
import hearthjaw.item.HearthgoopItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public final class HJRegistry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HJMain.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HJMain.MODID);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HJMain.MODID);

    public static void register() {
        BlockReg.register();
        ItemReg.register();
        EntityReg.register();
    }

    public static final class BlockReg {

        public static void register() {
            BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        }

        public static final RegistryObject<Block> GOO = BLOCKS.register("hearthgoop", () ->
                new HearthgoopBlock(BlockBehaviour.Properties.of(Material.WEB).noCollission().lightLevel(b -> 15)));
    }

    public static final class ItemReg {

        public static void register() {
            ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        }

        public static final RegistryObject<Item> GOO = ITEMS.register("hearthgoop", () ->
                new HearthgoopItem(BlockReg.GOO.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    }

    public static final class EntityReg {

        public static void register() {
            ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
            FMLJavaModLoadingContext.get().getModEventBus().addListener(EntityReg::onRegisterAttributes);
        }

        private static void onRegisterAttributes(final EntityAttributeCreationEvent event) {
            event.put(HEARTHJAW.get(), Hearthjaw.createAttributes().build());
        }

        public static final RegistryObject<EntityType<Hearthjaw>> HEARTHJAW = ENTITY_TYPES.register("hearthjaw", () ->
                EntityType.Builder.of(Hearthjaw::new, MobCategory.CREATURE)
                        .sized(1.39F, 1.6F)
                        .fireImmune()
                        .build("hearthjaw"));
    }
}