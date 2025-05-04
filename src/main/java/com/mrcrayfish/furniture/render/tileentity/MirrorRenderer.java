package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.entity.EntityMirror;
import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import com.mrcrayfish.furniture.proxy.ClientProxy;
import com.mrcrayfish.furniture.tileentity.TileEntityMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MirrorRenderer extends TileEntitySpecialRenderer<TileEntityMirror>
{
    private static Minecraft mc = Minecraft.getMinecraft();
    private int quality = ConfigurationHandler.mirrorQuality;
    private long renderEndNanoTime;

    private static Map<EntityMirror, Integer> registerMirrors = new ConcurrentHashMap<>();
    private static List<Integer> pendingRemoval = Collections.synchronizedList(new ArrayList<>());

    public static void removeRegisteredMirror(EntityMirror entity)
    {
        pendingRemoval.add(registerMirrors.get(entity));
        registerMirrors.remove(entity);
    }

    public static void clearRegisteredMirrors()
    {
        registerMirrors.clear();
    }

    @Override
    public void render(TileEntityMirror mirror, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(!ConfigurationHandler.mirrorEnabled)
            return;

        EntityMirror entityMirror = mirror.getMirror();
        if(entityMirror == null)
            return;

        if(!registerMirrors.containsKey(entityMirror))
        {
            int newTextureId = GlStateManager.generateTexture();
            GlStateManager.bindTexture(newTextureId);
            GlStateManager.glTexImage2D(3553, 0, 6407, quality, quality, 0, 6407, 5121, BufferUtils.createByteBuffer(3 * quality * quality).asIntBuffer());
            GlStateManager.glTexParameteri(3553, 10240, 9728);
            GlStateManager.glTexParameteri(3553, 10241, 9728);
            registerMirrors.put(mirror.getMirror(), newTextureId);
            return;
        }

        entityMirror.rendering = true;

        EnumFacing facing = EnumFacing.getHorizontal(mirror.getBlockMetadata());
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);

            GlStateManager.disableLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.bindTexture(registerMirrors.get(entityMirror));

            GlStateManager.glTexParameteri(3553, 10241, 9728);
            GlStateManager.glTexParameteri(3553, 10240, 9728);

            GlStateManager.translate(x + 0.5, y, z + 0.5);
            GlStateManager.rotate(-90F * facing.getHorizontalIndex() + 180F, 0, 1, 0);
            GlStateManager.translate(-0.5F, 0, -0.43F);

            GlStateManager.enableRescaleNormal();

            // Render
            GlStateManager.glBegin(7);
            {
                GlStateManager.glTexCoord2f(1, 0);
                GlStateManager.glVertex3f(0.0625F, 0.0625F, 0);
                GlStateManager.glTexCoord2f(0, 0);
                GlStateManager.glVertex3f(0.9375F, 0.0625F, 0);
                GlStateManager.glTexCoord2f(0, 1);
                GlStateManager.glVertex3f(0.9375F, 0.9375F, 0);
                GlStateManager.glTexCoord2f(1, 1);
                GlStateManager.glVertex3f(0.0625F, 0.9375F, 0);
            }
            GlStateManager.glEnd();

            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event)
    {
        if(event.phase.equals(TickEvent.Phase.END))
            return;

        if(!ConfigurationHandler.mirrorEnabled)
            return;

        if(!pendingRemoval.isEmpty())
        {
            for(Integer integer : pendingRemoval)
            {
                GlStateManager.deleteTexture(integer);
            }
            pendingRemoval.clear();
        }

        if(mc.inGameHasFocus)
        {
            for(EntityMirror entity : registerMirrors.keySet())
            {
                if(entity == null)
                {
                    registerMirrors.remove(entity);
                    continue;
                }

                if(!entity.rendering)
                    continue;

                if(!mc.player.canEntityBeSeen(entity))
                    continue;

                GameSettings settings = mc.gameSettings;
                RenderGlobal renderBackup = mc.renderGlobal;
                Entity entityBackup = mc.getRenderViewEntity();
                int thirdPersonBackup = settings.thirdPersonView;
                boolean hideGuiBackup = settings.hideGUI;
                int mipmapBackup = settings.mipmapLevels;
                float fovBackup = settings.fovSetting;
                int widthBackup = mc.displayWidth;
                int heightBackup = mc.displayHeight;

                mc.setRenderViewEntity(entity);
                settings.fovSetting = ConfigurationHandler.mirrorFov;
                settings.thirdPersonView = 0;
                settings.hideGUI = true;
                settings.mipmapLevels = 3;
                mc.displayWidth = quality;
                mc.displayHeight = quality;

                ClientProxy.rendering = true;
                ClientProxy.renderEntity = mc.player;

                int fps = Math.max(30, settings.limitFramerate);
                EntityRenderer entityRenderer = mc.entityRenderer;
                entityRenderer.renderWorld(event.renderTickTime, renderEndNanoTime + (1000000000 / fps));

                GlStateManager.bindTexture(registerMirrors.get(entity));
                GL11.glCopyTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 0, 0, quality, quality, 0);

                renderEndNanoTime = System.nanoTime();

                ClientProxy.renderEntity = null;
                ClientProxy.rendering = false;

                mc.renderGlobal = renderBackup;
                mc.setRenderViewEntity(entityBackup);
                settings.fovSetting = fovBackup;
                settings.thirdPersonView = thirdPersonBackup;
                settings.hideGUI = hideGuiBackup;
                settings.mipmapLevels = mipmapBackup;
                mc.displayWidth = widthBackup;
                mc.displayHeight = heightBackup;

                entity.rendering = false;
            }
        }
    }
}
