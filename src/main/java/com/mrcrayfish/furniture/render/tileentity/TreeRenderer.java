package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityTree;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TreeRenderer extends TileEntitySpecialRenderer<TileEntityTree>
{
    private EntityItem ornament = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityTree tree, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        Block block = tree.getWorld().getBlockState(tree.getPos()).getBlock();

        float yOffset = 0.0F;
        float spread = 0.3F;

        if(block == FurnitureBlocks.TREE_BOTTOM)
        {
            spread = 0.45F;
            yOffset = 0.5F;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + yOffset, (float) z + 0.5F);
        GlStateManager.rotate(90, 0, 1, 0);

        for(int i = 0; i < tree.getSize(); i++)
        {
            ItemStack item = tree.getItem(i);
            if(item != null)
            {
                this.ornament.setItem(item);
                this.ornament.hoverStart = 0.0F;

                GlStateManager.pushMatrix();

                GlStateManager.disableLighting();
                //WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
                //renderer.setBrightness(15728880);

                GlStateManager.rotate(-90 * i, 0, 1, 0);
                GlStateManager.translate(spread, 0.0F, 0.0F);
                GlStateManager.rotate(90, 0, 1, 0);
                GlStateManager.rotate(-15, 1, 0, 0);
                GlStateManager.scale(0.9F, 0.9F, 0.9F);
                Minecraft.getMinecraft().getRenderManager().renderEntity(ornament, 0.0D, 0.0D, 0.0D, 180.0F, 0.0F, false);

                GlStateManager.enableLighting();

                GlStateManager.popMatrix();
            }
        }

        GlStateManager.popMatrix();
    }
}
