package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockFurnitureTile;
import com.mrcrayfish.furniture.client.AnimatedTexture;
import com.mrcrayfish.furniture.client.GifCache;
import com.mrcrayfish.furniture.client.GifDownloadThread;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class TVRenderer extends TileEntitySpecialRenderer<TileEntityTV>
{
    private static final ResourceLocation NOISE = new ResourceLocation("cfm:textures/noise.png");
    private static final Random RAND = new Random();

    @Override
    public void render(TileEntityTV te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(!te.isPowered())
            return;

        BlockPos pos = te.getPos();
        IBlockState state = te.getWorld().getBlockState(pos);
        if(!state.getPropertyKeys().contains(BlockFurnitureTile.FACING))
            return;

        GlStateManager.pushMatrix();
        {
            GifDownloadThread.ImageDownloadResult result = te.getResult();
            if(result != null && result != GifDownloadThread.ImageDownloadResult.SUCCESS)
            {
                GlStateManager.translate(x, y, z);
                GlStateManager.translate(8 * 0.0625, te.getScreenYOffset() * 0.0625, 8 * 0.0625);
                EnumFacing facing = state.getValue(BlockFurnitureTile.FACING);
                GlStateManager.rotate(facing.getHorizontalIndex() * -90F, 0, 1, 0);
                GlStateManager.translate(-te.getWidth() / 2 * 0.0625, 0, 0);
                GlStateManager.translate(0, 0, te.getScreenZOffset() * 0.0625);
                GlStateManager.translate(te.getWidth() * 0.0625 - 0.0625, te.getHeight() * 0.0625 - 0.0625, 0);
                GlStateManager.scale(1, -1, -1);
                GlStateManager.scale(0.01F, 0.01F, 0.01F);
                GlStateManager.rotate(180F, 0, 1, 0);

                String message = I18n.format(result.getKey());
                FontRenderer renderer = this.getFontRenderer();
                List<String> lines = renderer.listFormattedStringToWidth(message, (int) ((te.getWidth() - 2.0) * 6.3));
                for(int i = 0; i < lines.size(); i++)
                {
                    renderer.drawString(lines.get(i), 0, renderer.FONT_HEIGHT * i, 16777215);
                }
            }
            else
            {
                GlStateManager.translate(x, y, z);
                GlStateManager.enableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GlStateManager.disableLighting();

                double startX = 0.0;
                double startY = 0.0;
                double width = te.getWidth();
                double height = te.getHeight();

                if(te.isLoading())
                {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(NOISE);
                    GlStateManager.glTexParameteri(3553, 10241, 9728);
                    GlStateManager.glTexParameteri(3553, 10240, 9728);

                    //Setups translations
                    GlStateManager.translate(8 * 0.0625, te.getScreenYOffset() * 0.0625, 8 * 0.0625);
                    EnumFacing facing = state.getValue(BlockFurnitureTile.FACING);
                    GlStateManager.rotate(facing.getHorizontalIndex() * -90F, 0, 1, 0);
                    GlStateManager.translate(-te.getWidth() / 2 * 0.0625, 0, 0);
                    GlStateManager.translate(0, 0, te.getScreenZOffset() * 0.0625);

                    double pixelScale = 1.0 / 256;
                    double scaledWidth = te.getWidth() * 4;
                    double scaledHeight = te.getHeight() * 4;
                    double u = ((int)((256 - scaledWidth) * RAND.nextDouble()) * pixelScale);
                    double v = ((int)((256 - scaledHeight) * RAND.nextDouble()) * pixelScale);

                    startX *= 0.0625;
                    startY *= 0.0625;
                    width *= 0.0625;
                    height *= 0.0625;

                    //Render the GIF
                    GlStateManager.translate(0, 0, -0.01 * 0.0625);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder buffer = tessellator.getBuffer();
                    buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                    buffer.pos(startX, startY, 0).tex(u, v).endVertex();
                    buffer.pos(startX, startY + height, 0).tex(u, v + scaledHeight * pixelScale).endVertex();
                    buffer.pos(startX + width, startY + height, 0).tex(u + scaledWidth * pixelScale, v + scaledHeight * pixelScale).endVertex();
                    buffer.pos(startX + width, startY, 0).tex(u + scaledWidth * pixelScale, v).endVertex();
                    tessellator.draw();
                }
                else if(te.isLoaded())
                {
                    AnimatedTexture texture = GifCache.INSTANCE.get(te.getCurrentChannel());
                    if(texture != null)
                    {
                        texture.bind();

                        GlStateManager.glTexParameteri(3553, 10241, 9728);
                        GlStateManager.glTexParameteri(3553, 10240, 9728);

                        if(!te.isStretched())
                        {
                            //Calculates the positioning and scale so the GIF keeps its ratio and renders within the screen
                            double scaleWidth = (double) te.getWidth() / (double) texture.getWidth();
                            double scaleHeight = (double) te.getHeight() / (double) texture.getHeight();
                            double scale = Math.min(scaleWidth, scaleHeight);
                            width = texture.getWidth() * scale;
                            height = texture.getHeight() * scale;
                            startX = (te.getWidth() - width) / 2.0;
                            startY = (te.getHeight() - height) / 2.0;
                        }

                        startX *= 0.0625;
                        startY *= 0.0625;
                        width *= 0.0625;
                        height *= 0.0625;

                        //Setups translations
                        GlStateManager.translate(8 * 0.0625, te.getScreenYOffset() * 0.0625, 8 * 0.0625);
                        EnumFacing facing = state.getValue(BlockFurnitureTile.FACING);
                        GlStateManager.rotate(facing.getHorizontalIndex() * -90F, 0, 1, 0);
                        GlStateManager.translate(-te.getWidth() / 2 * 0.0625, 0, 0);
                        GlStateManager.translate(0, 0, te.getScreenZOffset() * 0.0625);

                        //Render the GIF
                        Tessellator tessellator = Tessellator.getInstance();
                        BufferBuilder buffer = tessellator.getBuffer();
                        GlStateManager.translate(0, 0, -0.01 * 0.0625);
                        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                        buffer.pos(startX, startY, 0).tex(0, 0).endVertex();
                        buffer.pos(startX, startY + height, 0).tex(0, 1).endVertex();
                        buffer.pos(startX + width, startY + height, 0).tex(1, 1).endVertex();
                        buffer.pos(startX + width, startY, 0).tex(1, 0).endVertex();
                        tessellator.draw();
                    }
                    else
                    {
                        String currentChannel = te.getCurrentChannel();
                        if(currentChannel != null)
                        {
                            te.loadUrl(currentChannel);
                        }
                    }
                }
                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
            }
        }
        GlStateManager.popMatrix();
    }
}
