package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.blocks.BlockOldToilet;
import com.mrcrayfish.furniture.blocks.BlockToilet;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFart;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class InputHandler
{
    public static final KeyBinding KEY_FART =
            new KeyBinding("key.fart.desc", Keyboard.KEY_G, "keys.cfm.category");

    public InputHandler()
    {
        ClientRegistry.registerKeyBinding(KEY_FART);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (!KEY_FART.isPressed())
            return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.world;
        EntityPlayer player = mc.player;

        if (world == null || player == null)
            return;

        BlockPos pos = new BlockPos(
                Math.floor(player.posX),
                Math.floor(player.posY),
                Math.floor(player.posZ)
        );

        Block block = world.getBlockState(pos).getBlock();

        if (block instanceof BlockToilet || block instanceof BlockOldToilet)
        {
            PacketHandler.INSTANCE.sendToServer(new MessageFart());
        }
    }
}
