/*    */ package ee;
/*    */ 
/*    */ import ee.core.GuiIds;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.World;
/*    */ import net.minecraft.server.mod_EE;
/*    */ 
/*    */ public class TileTransTablet extends TileEE
/*    */ {
/*    */   public int getTextureForSide(int var1)
/*    */   {
/* 11 */     return var1 == 1 ? EEBase.transTabletTop : var1 == 0 ? EEBase.transTabletBottom : EEBase.transTabletSide;
/*    */   }
/*    */ 
/*    */   public int getInventoryTexture(int var1)
/*    */   {
/* 16 */     return var1 == 1 ? EEBase.transTabletTop : EEBase.transTabletSide;
/*    */   }
/*    */ 
/*    */   public boolean onBlockActivated(EntityHuman var1)
/*    */   {
/* 21 */     if (!this.world.isStatic)
/*    */     {
/* 23 */       var1.openGui(mod_EE.getInstance(), GuiIds.TRANS_TABLE, this.world, this.x, this.y, this.z);
/*    */     }
/*    */ 
/* 26 */     return true;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileTransTablet
 * JD-Core Version:    0.6.2
 */