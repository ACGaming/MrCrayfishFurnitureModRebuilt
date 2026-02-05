package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.blocks.BlockOldToilet;
import com.mrcrayfish.furniture.blocks.BlockToilet;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFart;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class InputHandler {
    public static final KeyBinding KEY_FART =
            new KeyBinding("key.fart.desc", Keyboard.KEY_G, "keys.cfm.category");

    public InputHandler() {
        ClientRegistry.registerKeyBinding(KEY_FART);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!KEY_FART.isPressed())
            return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (player == null)
            return;

        if (player.getRidingEntity() instanceof EntitySeat) {
            EntitySeat seat = (EntitySeat) player.getRidingEntity();
            World world = player.world;
            Block block = world.getBlockState(seat.getPosition()).getBlock();

            if (block instanceof BlockToilet || block instanceof BlockOldToilet) {
                for (int i = 0; i < 8; i++) {
                    double offsetX = (player.getRNG().nextDouble() - 0.5D) * 0.3D;
                    double offsetZ = (player.getRNG().nextDouble() - 0.5D) * 0.3D;
                    double velocityY = 0.05D + player.getRNG().nextDouble() * 0.05D;

                    player.world.spawnParticle(
                            EnumParticleTypes.CLOUD,
                            player.posX + offsetX,
                            player.posY + 0.4D,
                            player.posZ + offsetZ,
                            0.0D,
                            velocityY,
                            0.0D
                    );
                }

                PacketHandler.INSTANCE.sendToServer(new MessageFart());
            }
        }
    }
}
