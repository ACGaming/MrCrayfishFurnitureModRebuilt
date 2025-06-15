package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.blocks.tv.Channel;
import com.mrcrayfish.furniture.blocks.tv.Channels;
import com.mrcrayfish.furniture.tileentity.TileEntityRetroTV;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author: MrCrayfish
 */
public class MessageTVPlaySound implements IMessage, IMessageHandler<MessageTVPlaySound, IMessage>
{
    private BlockPos pos;
    private int channelIndex;
    private String name;

    public MessageTVPlaySound()
    {
    }

    public MessageTVPlaySound(BlockPos pos, int channelIndex)
    {
        this.pos = pos;
        this.channelIndex = channelIndex;
        this.name = Channels.getChannel(channelIndex).getChannelName();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
        buf.writeInt(channelIndex);
        ByteBufUtils.writeUTF8String(buf, name);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.channelIndex = buf.readInt();
        this.name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(MessageTVPlaySound message, MessageContext ctx)
    {
        Channel channel = Channels.getChannel(message.name);
        if(channel != null)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                TileEntityRetroTV tileEntity = (TileEntityRetroTV) world.getTileEntity(message.pos);
                if(tileEntity != null)
                {
                    tileEntity.setChannel(message.channelIndex);
                    world.notifyBlockUpdate(message.pos, world.getBlockState(message.pos), world.getBlockState(message.pos), 3);
                    world.markBlockRangeForRenderUpdate(message.pos, message.pos);
                    channel.resetAnimation();
                    ISound sound = Channels.SOUND_POSITIONS.get(message.pos);
                    if(sound != null)
                    {
                        Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
                        Channels.SOUND_POSITIONS.remove(message.pos);
                    }
                    sound = new PositionedSoundRecord(channel.getSound().getSoundName(), SoundCategory.RECORDS, 1.0F, 1.0F, true, 0, ISound.AttenuationType.LINEAR, message.pos.getX() + 0.5F, message.pos.getY() + 0.5F, message.pos.getZ() + 0.5F);
                    Minecraft.getMinecraft().getSoundHandler().playSound(sound);
                    Channels.SOUND_POSITIONS.put(message.pos, sound);
                }
            });
        }
        return null;
    }
}
