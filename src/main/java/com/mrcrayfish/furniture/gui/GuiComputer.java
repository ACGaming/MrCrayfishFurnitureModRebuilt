package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.gui.containers.ContainerComputer;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageMineBayBuy;
import com.mrcrayfish.furniture.network.message.MessageMineBayClosed;
import com.mrcrayfish.furniture.tileentity.TileEntityComputer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class GuiComputer extends GuiContainer
{
    private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/computer.png");

    private GuiButton left, right;
    private GuiButton button_buy;

    private int itemNum;
    private ItemStack buySlot;
    private TileEntityComputer tileEntityComputer;
    private RecipeData[] itemdata;

    public GuiComputer(InventoryPlayer inventoryplayer, TileEntityComputer tileEntityComputer)
    {
        super(new ContainerComputer(inventoryplayer, tileEntityComputer));
        this.tileEntityComputer = tileEntityComputer;
        this.xSize = 176;
        this.ySize = 187;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(false);

        buttonList.clear();

        int posX = width / 2;
        int posY = height / 2;

        left = new GuiButton(0, posX - 48, posY - 80, 15, 20, "<");
        right = new GuiButton(1, posX + 34, posY - 80, 15, 20, ">");
        button_buy = new GuiButton(2, posX - 48, posY - 57, 29, 20, I18n.format("cfm.button.buy"));

        buttonList.add(left);
        buttonList.add(right);
        buttonList.add(button_buy);

        this.itemdata = Recipes.getMineBayItems();
        this.itemNum = tileEntityComputer.getBrowsingInfo();
        if(itemdata == null || itemdata.length == 0)
        {
            this.itemNum = 0;
        }
        else
        {
            this.itemNum = Math.max(0, Math.min(this.itemNum, itemdata.length - 1));
        }

        this.tileEntityComputer.setBrowsingInfo(this.itemNum);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled) return;

        if(itemdata == null || itemdata.length == 0)
        {
            itemNum = 0;
            return;
        }
        if(guibutton.id == 0)
        {
            itemNum = Math.max(0, itemNum - 1);
        }
        else if(guibutton.id == 1)
        {
            itemNum = Math.min(itemdata.length - 1, itemNum + 1);
        }
        else if(guibutton.id == 2)
        {
            if(itemNum < 0 || itemNum >= itemdata.length) return;

            this.buySlot = this.tileEntityComputer.getStackInSlot(0);
            if(!buySlot.isEmpty())
            {
                ItemStack money = itemdata[itemNum].getCurrency();
                if(buySlot.getItem() == money.getItem() && buySlot.getItemDamage() == money.getItemDamage())
                {
                    PacketHandler.INSTANCE.sendToServer(new MessageMineBayBuy(this.itemNum, this.tileEntityComputer.getPos().getX(), this.tileEntityComputer.getPos().getY(), this.tileEntityComputer.getPos().getZ()));
                }
            }
        }

        this.tileEntityComputer.setBrowsingInfo(itemNum);
    }

    @Override
    public void onGuiClosed()
    {
        PacketHandler.INSTANCE.sendToServer(new MessageMineBayClosed(tileEntityComputer.getPos().getX(), tileEntityComputer.getPos().getY(), tileEntityComputer.getPos().getZ()));
        super.onGuiClosed();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, (ySize - 103), 4210752);

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
        GlStateManager.enableBlend();
        itemRender.zLevel = 100.0F;

        if((itemNum - 1) >= 0 && itemNum - 1 < itemdata.length)
        {
            ItemStack pre = itemdata[itemNum - 1].getInput();
            itemRender.renderItemAndEffectIntoGUI(pre, 57, 16);
            itemRender.renderItemOverlays(this.fontRenderer, pre, 57, 16);
        }

        if(itemNum < itemdata.length)
        {
            ItemStack stock = itemdata[itemNum].getInput();
            itemRender.renderItemAndEffectIntoGUI(stock, 80, 16);
            itemRender.renderItemOverlays(this.fontRenderer, stock, 80, 16);

            ItemStack currency = itemdata[itemNum].getCurrency();
            itemRender.renderItemAndEffectIntoGUI(currency, 73, 40);
            itemRender.renderItemOverlays(this.fontRenderer, currency, 73, 40);
        }

        if((itemNum + 1) < itemdata.length)
        {
            ItemStack post = itemdata[itemNum + 1].getInput();
            itemRender.renderItemAndEffectIntoGUI(post, 103, 16);
            itemRender.renderItemOverlays(this.fontRenderer, post, 103, 16);
        }

        itemRender.zLevel = 0.0F;
        GlStateManager.disableLighting();

        if(itemNum < itemdata.length)
        {
            int price = itemdata[itemNum].getPrice();
            this.fontRenderer.drawString("x" + price, 90, 44, 0);
        }

        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if(itemdata != null && itemNum >= 0 && itemNum < itemdata.length)
        {
            ItemStack stock = itemdata[itemNum].getInput();
            if(this.isPointInRegion(80, 16, 16, 16, mouseX, mouseY))
            {
                this.renderToolTip(stock, mouseX, mouseY);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(gui);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1 - 10, 0, 0, xSize, ySize + 21);
    }
}
