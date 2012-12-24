/*    */ package ee;
/*    */ 
/*    */ public class TileRMBlock extends TileEE
/*    */ {
/*    */   public boolean canUpdate()
/*    */   {
/*  7 */     return false;
/*    */   }
/*    */ 
/*    */   public int getTextureForSide(int var1)
/*    */   {
/* 12 */     return EEBase.rmBlockSide;
/*    */   }
/*    */ 
/*    */   public int getInventoryTexture(int var1)
/*    */   {
/* 17 */     return EEBase.rmBlockSide;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileRMBlock
 * JD-Core Version:    0.6.2
 */