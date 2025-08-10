package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityBlender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class BlenderRenderer extends TileEntitySpecialRenderer<TileEntityBlender>
{
    private EntityItem entityFood = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityBlender blender, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        ItemStack[] ingredients = blender.getIngredients();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.2F, (float) z + 0.5F);
            GlStateManager.scale(0.65F, 0.65F, 0.65F);
            entityFood.hoverStart = 0.0F;
            for(int i = 0; i < ingredients.length; i++)
            {
                if(ingredients[i] != null)
                {
                    entityFood.setItem(ingredients[i]);
                    GlStateManager.rotate(i * -90F, 0, 1, 0);
                    GlStateManager.rotate(blender.progress * 18F, 0, 1, 0);
                    Minecraft.getMinecraft().getRenderManager().renderEntity(entityFood, 0.0D, 0.2D, 0.0D, 0.0F, 0.0F, false);
                }
            }
        }
        GlStateManager.popMatrix();

        if(blender.isBlending() || blender.drinkCount > 0)
        {
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate((float) x + 0.5F, (float) y + 0.05F, (float) z + 0.5F);

                GlStateManager.enableBlend();
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GlStateManager.disableLighting();
                GlStateManager.disableTexture2D();

                float liquidAlpha = blender.isBlending() ? (blender.progress / 250F) : (blender.drinkCount > 0 ? 0.8F : 0.0F);
                GlStateManager.color(blender.currentRed / 255F, blender.currentGreen / 255F, blender.currentBlue / 255F, liquidAlpha);

                GlStateManager.enableRescaleNormal();

                float height = blender.isBlending() ? 0.8F : (0.275F + (0.525F * (blender.drinkCount / 6F)));
                GlStateManager.pushMatrix();
                {
                    renderCuboid(-0.2F, 0.275F, -0.2F, 0.2F, height, 0.2F);
                }
                GlStateManager.popMatrix();

                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
            }
            GlStateManager.popMatrix();
        }

        GlStateManager.color(1F, 1F, 1F);
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
