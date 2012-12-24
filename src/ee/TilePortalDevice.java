/*    */ package ee;
/*    */ 
/*    */ public class TilePortalDevice extends TileEE
/*    */ {
/*    */   public int getTextureForSide(int var1)
/*    */   {
/*  7 */     return var1 == 1 ? EEBase.portalDeviceTop : var1 == 0 ? EEBase.portalDeviceBottom : EEBase.portalDeviceSide;
/*    */   }
/*    */ 
/*    */   public int getInventoryTexture(int var1)
/*    */   {
/* 12 */     return var1 == 1 ? EEBase.portalDeviceTop : EEBase.portalDeviceSide;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TilePortalDevice
 * JD-Core Version:    0.6.2
 */