/*    */ package ee;
/*    */ 
/*    */ import ee.core.GuiIds;
/*    */ import forge.IGuiHandler;
/*    */ import net.minecraft.server.EEProxy;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.TileEntity;
/*    */ import net.minecraft.server.World;
/*    */ 
/*    */ public class EEGuiHandler
/*    */   implements IGuiHandler
/*    */ {
/*    */   public Object getGuiElement(int var1, EntityHuman var2, World var3, int var4, int var5, int var6)
/*    */   {
/* 14 */     TileEntity var7 = null;
/*    */ 
/* 16 */     if (!isItemGui(var1))
/*    */     {
/* 18 */       if (!var3.isLoaded(var4, var5, var6))
/*    */       {
/* 20 */         return null;
/*    */       }
/*    */ 
/* 23 */       var7 = var3.getTileEntity(var4, var5, var6);
/*    */     }
/*    */ 
/* 26 */     if (var1 == GuiIds.COLLECTOR_1)
/*    */     {
/* 28 */       return new ContainerCollector(var2.inventory, (TileCollector)var7);
/*    */     }
/* 30 */     if (var1 == GuiIds.COLLECTOR_2)
/*    */     {
/* 32 */       return new ContainerCollector2(var2.inventory, (TileCollector2)var7);
/*    */     }
/* 34 */     if (var1 == GuiIds.COLLECTOR_3)
/*    */     {
/* 36 */       return new ContainerCollector3(var2.inventory, (TileCollector3)var7);
/*    */     }
/* 38 */     if (var1 == GuiIds.RELAY_1)
/*    */     {
/* 40 */       return new ContainerRelay(var2.inventory, (TileRelay)var7);
/*    */     }
/* 42 */     if (var1 == GuiIds.RELAY_2)
/*    */     {
/* 44 */       return new ContainerRelay2(var2.inventory, (TileRelay2)var7);
/*    */     }
/* 46 */     if (var1 == GuiIds.RELAY_3)
/*    */     {
/* 48 */       return new ContainerRelay3(var2.inventory, (TileRelay3)var7);
/*    */     }
/* 50 */     if (var1 == GuiIds.DM_FURNACE)
/*    */     {
/* 52 */       return new ContainerDMFurnace(var2.inventory, (TileDMFurnace)var7);
/*    */     }
/* 54 */     if (var1 == GuiIds.RM_FURNACE)
/*    */     {
/* 56 */       return new ContainerRMFurnace(var2.inventory, (TileRMFurnace)var7);
/*    */     }
/* 58 */     if (var1 == GuiIds.CONDENSER)
/*    */     {
/* 60 */       return new ContainerCondenser(var2.inventory, (TileCondenser)var7);
/*    */     }
/* 62 */     if (var1 == GuiIds.PEDESTAL)
/*    */     {
/* 64 */       return new ContainerPedestal(var2.inventory, (TilePedestal)var7);
/*    */     }
/* 66 */     if (var1 == GuiIds.TRANS_TABLE)
/*    */     {
/* 68 */       return new ContainerTransmutation(var2.inventory, var2, EEProxy.getTransData(var2));
/*    */     }
/* 70 */     if (var1 == GuiIds.PORT_TRANS_TABLE)
/*    */     {
/* 72 */       return new ContainerTransmutation(var2.inventory, var2, EEProxy.getTransData(var2));
/*    */     }
/* 74 */     if (var1 == GuiIds.ALCH_CHEST)
/*    */     {
/* 76 */       return new ContainerAlchChest((TileAlchChest)var7, var2.inventory, false);
/*    */     }
/* 78 */     if (var1 == GuiIds.ALCH_BAG)
/*    */     {
/* 80 */       AlchemyBagData var9 = ItemAlchemyBag.getBagData(var4, var2, var3);
/* 81 */       return new ContainerAlchChest(var9, var2.inventory, true);
/*    */     }
/* 83 */     if (var1 == GuiIds.MERCURIAL_EYE)
/*    */     {
/* 85 */       MercurialEyeData var8 = ItemMercurialEye.getEyeData(var2, var3);
/* 86 */       return new ContainerMercurial(var2.inventory, var2, var8);
/*    */     }
/*    */ 
/* 90 */     return var1 == GuiIds.PORT_CRAFTING ? new ContainerPortableCrafting(var2.inventory, var2) : null;
/*    */   }
/*    */ 
/*    */   private static boolean isItemGui(int var0)
/*    */   {
/* 96 */     return (var0 == GuiIds.PORT_CRAFTING) || (var0 == GuiIds.MERCURIAL_EYE) || (var0 == GuiIds.PORT_TRANS_TABLE) || (var0 == GuiIds.ALCH_BAG);
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEGuiHandler
 * JD-Core Version:    0.6.2
 */