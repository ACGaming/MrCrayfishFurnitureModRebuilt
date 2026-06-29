package com.mrcrayfish.furniture.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.mrcrayfish.furniture.gui.containers.ContainerMailBox;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;

public class GuiModernMailBox extends GuiContainer
{
    private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/modern_mailbox.png");
    private final TileEntityMailBox tileEntityMailBox;

    public GuiModernMailBox(InventoryPlayer inventoryplayer, TileEntityMailBox tileEntityMailBox)
    {
        super(new ContainerMailBox(inventoryplayer, tileEntityMailBox));
        this.tileEntityMailBox = tileEntityMailBox;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String title = I18n.format("container.modern_mailbox");
        if (this.tileEntityMailBox.hasOwner()) title += " (" + this.tileEntityMailBox.getOwner() + ")";
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.6F, 0.6F, 1.0F);
        this.fontRenderer.drawString(title, xSize / 2, 10, 9999999);
        GlStateManager.popMatrix();
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(gui);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }
}