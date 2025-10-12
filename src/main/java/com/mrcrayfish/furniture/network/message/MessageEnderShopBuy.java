package com.mrcrayfish.furniture.network.message;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.gui.containers.ContainerComputer;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityModernComputer;
import io.netty.buffer.ByteBuf;

public class MessageEnderShopBuy implements IMessage, IMessageHandler<MessageEnderShopBuy, IMessage>
{
    private int itemNum, x, y, z;

    public MessageEnderShopBuy() {}

    public MessageEnderShopBuy(int itemNum, int x, int y, int z)
    {
        this.itemNum = itemNum;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.itemNum = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(itemNum);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageEnderShopBuy message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().player;
        World world = player.world;
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
        if(!world.isAreaLoaded(pos, 0))
            return null;

        Container container = ctx.getServerHandler().player.openContainer;
        if(!(container instanceof ContainerComputer))
            return null;

        TileEntity tileEntity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
        if(tileEntity instanceof TileEntityModernComputer)
        {
            TileEntityModernComputer tileEntityModernComputer = (TileEntityModernComputer) tileEntity;
            if(((ContainerComputer) container).getComputerInventory() != tileEntityModernComputer)
                return null;

            ItemStack buySlot = tileEntityModernComputer.getStackInSlot(0);
            if(buySlot.isEmpty())
                return null;

            RecipeData[] data = Recipes.getEnderShopItems();
            if(message.itemNum < 0 || message.itemNum >= data.length)
                return null;

            RecipeData recipe = data[message.itemNum];
            int price = recipe.getPrice();
            if(recipe.getCurrency().getItem() == buySlot.getItem() && buySlot.getCount() >= price)
            {
                tileEntityModernComputer.takeEmeraldFromSlot(price);
                EntityItem entityItem = new EntityItem(player.world, player.posX, player.posY + 1, player.posZ, data[message.itemNum].getInput().copy());
                player.world.spawnEntity(entityItem);
                Triggers.trigger(Triggers.ENDERSHOP_PURCHASE, player);
                if(entityItem.getItem().getItem() == FurnitureItems.DOG_FOOD)
                    Triggers.trigger(Triggers.PURCHASE_DOG_FOOD, player);
            }
        }
        return null;
    }
}
