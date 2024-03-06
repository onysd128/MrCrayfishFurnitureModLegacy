package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class PlateRenderer extends TileEntitySpecialRenderer<TileEntityPlate>
{
    private EntityItem entityFood = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityPlate plate, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(!plate.getFood().isEmpty())
        {
            entityFood.setItem(plate.getFood());

            GlStateManager.pushMatrix();
            this.entityFood.hoverStart = 0.0F;

            float xOffset = 0.0F;
            float zOffset = 0.0F;

            switch(plate.getRotation())
            {
                case 0:
                    zOffset -= 0.15F;
                    break;
                case 1:
                    xOffset += 0.35F;
                    zOffset += 0.2F;
                    break;
                case 2:
                    zOffset += 0.55F;
                    break;
                case 3:
                    xOffset -= 0.35F;
                    zOffset += 0.2F;
                    break;
            }

            GlStateManager.disableLighting();
            //WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
            //renderer.setBrightness(15728880);

            GlStateManager.translate((float) x + 0.5F + xOffset, (float) y + 0.05F, (float) z + 0.3F + zOffset);
            GlStateManager.rotate(plate.getRotation() * -90F, 0, 1, 0);
            GlStateManager.rotate(180, 0, 1, 1);
            GlStateManager.translate(0, -0.1, 0);
            Minecraft.getMinecraft().getRenderManager().renderEntity(entityFood, 0.0D, 0.0D, 0.075D, 0.0F, 0.0F, false);

            GlStateManager.enableLighting();

            GlStateManager.popMatrix();
        }
    }
}
