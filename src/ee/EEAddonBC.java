/*    */ package ee;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.logging.Logger;
/*    */ import net.minecraft.server.Item;
/*    */ import net.minecraft.server.ModLoader;
/*    */ 
/*    */ public class EEAddonBC
/*    */ {
/*    */   public static boolean bcEnergyInstalled;
/*  9 */   public static Item bcItemBucketOil = null;
/*    */ 
/*    */   public static void initialize()
/*    */   {
/*    */     try
/*    */     {
/* 15 */       bcItemBucketOil = (Item)Class.forName("net.minecraft.server.BuildCraftEnergy").getField("bucketOil").get(null);
/* 16 */       EEMaps.addEMC(bcItemBucketOil.id, EEMaps.getEMC(Item.BUCKET.id) + EEMaps.getEMC(Item.GOLD_INGOT.id));
/*    */ 
/* 18 */       if (bcItemBucketOil != null)
/*    */       {
/* 20 */         EEMaps.addFuelItem(bcItemBucketOil.id);
/*    */       }
/*    */ 
/* 23 */       bcEnergyInstalled = true;
/*    */     }
/*    */     catch (Exception var1)
/*    */     {
/* 27 */       bcEnergyInstalled = false;
/* 28 */       ModLoader.getLogger().warning("[EE2] Could not load EE2-BC Energy Addon");
/* 29 */       var1.printStackTrace(System.err);
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEAddonBC
 * JD-Core Version:    0.6.2
 */