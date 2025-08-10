package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.items.ItemBottle;
import com.mrcrayfish.furniture.tileentity.TileEntityCup;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class CupRenderer extends TileEntitySpecialRenderer<TileEntityCup>
{
    @Override
    public void render(TileEntityCup tileEntityCup, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(tileEntityCup.getDrink() != null)
        {
            ItemStack drink = tileEntityCup.getDrink();
            int servings = drink.getTagCompound().getInteger("Servings");
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate((float) x + 0.5F, (float) y, (float) z + 0.5F);

                GlStateManager.enableBlend();
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GlStateManager.disableLighting();
                GlStateManager.disableTexture2D();
                GlStateManager.color(tileEntityCup.red / 255F, tileEntityCup.green / 255F, tileEntityCup.blue / 255F, 0.8F);
                GlStateManager.enableRescaleNormal();

                renderCuboid(-0.124F, 0.5F * 0.0625F, -0.124F, 0.124F, drink.getItem() instanceof ItemBottle ? 0.1F * servings : 0.4F, 0.124F);

                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
            }
            GlStateManager.popMatrix();
        }
    }

    public void renderCuboid(float x1, float y1, float z1, float x2, float y2, float z2)
    {
        GlStateManager.glBegin(7);
        {
            GlStateManager.glVertex3f(x1, y1, z1);
            GlStateManager.glVertex3f(x1, y1, z2);
            GlStateManager.glVertex3f(x1, y2, z2);
            GlStateManager.glVertex3f(x1, y2, z1);

            GlStateManager.glVertex3f(x2, y1, z1);
            GlStateManager.glVertex3f(x1, y1, z1);
            GlStateManager.glVertex3f(x1, y2, z1);
            GlStateManager.glVertex3f(x2, y2, z1);

            GlStateManager.glVertex3f(x1, y1, z2);
            GlStateManager.glVertex3f(x2, y1, z2);
            GlStateManager.glVertex3f(x2, y2, z2);
            GlStateManager.glVertex3f(x1, y2, z2);

            GlStateManager.glVertex3f(x2, y1, z2);
            GlStateManager.glVertex3f(x2, y1, z1);
            GlStateManager.glVertex3f(x2, y2, z1);
            GlStateManager.glVertex3f(x2, y2, z2);

            GlStateManager.glVertex3f(x1, y2, z1);
            GlStateManager.glVertex3f(x1, y2, z2);
            GlStateManager.glVertex3f(x2, y2, z2);
            GlStateManager.glVertex3f(x2, y2, z1);

            GlStateManager.glVertex3f(x1, y1, z1);
            GlStateManager.glVertex3f(x1, y1, z2);
            GlStateManager.glVertex3f(x2, y1, z2);
            GlStateManager.glVertex3f(x2, y1, z1);
        }
        GlStateManager.glEnd();
    }
}
