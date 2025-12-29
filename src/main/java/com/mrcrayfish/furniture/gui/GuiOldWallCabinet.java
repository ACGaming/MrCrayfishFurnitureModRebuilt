package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.gui.containers.ContainerOldWallCabinet;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiOldWallCabinet extends GuiContainer
{
    private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/oldwallcabinet.png");

    public GuiOldWallCabinet(IInventory playerInventory, IInventory wallCabinetInventory)
    {
        super(new ContainerOldWallCabinet(playerInventory, wallCabinetInventory));
        this.xSize = 176;
        this.ySize = 197;
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
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
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
