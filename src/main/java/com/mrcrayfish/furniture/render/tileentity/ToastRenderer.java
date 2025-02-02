package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityToaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class ToastRenderer extends TileEntitySpecialRenderer<TileEntityToaster>
{
    private EntityItem[] slots = {new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D), new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D)};

    @Override
    public void render(TileEntityToaster tileEntityToaster, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        int metadata = tileEntityToaster.getBlockMetadata();

        for(int i = 0; i < 2; i++)
        {
            ItemStack slice = tileEntityToaster.getSlice(i);
            if(slice != null)
            {
                this.slots[i].setItem(slice);
                GlStateManager.pushMatrix();
                slots[i].hoverStart = 0.0F;
                GlStateManager.translate((float) x + 0.5F, (float) y + 0.05F, (float) z + 0.3F);

                double xOffset = 0.0D;
                double zOffset = 0.0D;

                switch(metadata)
                {
                    case 0:
                        if(i == 1)
                        {
                            zOffset += 0.27F;
                        }
                        else
                        {
                            zOffset += 0.14F;
                        }
                        break;
                    case 1:
                        xOffset += 0.2F;
                        if(i == 1)
                        {
                            zOffset += 0.07F;
                        }
                        else
                        {
                            zOffset -= 0.06F;
                        }
                        GlStateManager.rotate(270, 0, 1, 0);
                        break;
                    case 2:
                        if(i == 1)
                        {
                            zOffset -= 0.13F;
                        }
                        else
                        {
                            zOffset -= 0.26F;
                        }
                        GlStateManager.rotate(180, 0, 1, 0);
                        break;
                    case 3:
                        xOffset -= 0.2F;
                        if(i == 1)
                        {
                            zOffset += 0.07F;
                        }
                        else
                        {
                            zOffset -= 0.06F;
                        }
                        GlStateManager.rotate(90, 0, 1, 0);
                        break;
                }

                double yOffset = tileEntityToaster.isToasting() ? -0.25 : -0.15;
                Minecraft.getMinecraft().getRenderManager().renderEntity(slots[i], 0.0D + xOffset, yOffset, 0.0D + zOffset, 0.0F, 0.0F, false);
                GlStateManager.popMatrix();
            }
        }
    }
}
