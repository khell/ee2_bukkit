/*    */ package ee;
/*    */ 
/*    */ import forestry.api.core.ForestryBlock;
/*    */ import forestry.api.core.ItemInterface;
/*    */ import java.util.logging.Logger;
/*    */ import net.minecraft.server.Item;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.ModLoader;
/*    */ 
/*    */ public class EEAddonForestry
/*    */ {
/*    */   private static final int APATITE = 0;
/*    */   private static final int COPPER = 1;
/*    */   private static final int TIN = 2;
/*    */ 
/*    */   public static void initialize()
/*    */   {
/*    */     try
/*    */     {
/* 19 */       EEMaps.addAlchemicalValue(ItemInterface.getItem("apatite"), 192);
/* 20 */       EEMaps.addEMC(ItemInterface.getItem("beeComb").getItem().id, 11, EEMaps.getEMC(Item.DIAMOND.id));
/* 21 */       EEMaps.addAlchemicalValue(ItemInterface.getItem("ingotCopper"), (EEMaps.getEMC(Item.IRON_INGOT.id) - 1) / 3);
/* 22 */       EEMaps.addAlchemicalValue(ItemInterface.getItem("ingotTin"), EEMaps.getEMC(Item.IRON_INGOT.id));
/* 23 */       EEMaps.addAlchemicalValue(ItemInterface.getItem("ingotBronze"), (EEMaps.getEMC(ItemInterface.getItem("ingotCopper")) * 3 + 3 + EEMaps.getEMC(ItemInterface.getItem("ingotTin"))) / 4);
/* 24 */       EEMaps.addOreBlock(new ItemStack(ForestryBlock.resources, 1, 0));
/* 25 */       EEMaps.addOreBlock(new ItemStack(ForestryBlock.resources, 1, 1));
/* 26 */       EEMaps.addOreBlock(new ItemStack(ForestryBlock.resources, 1, 2));
/* 27 */       EEMaps.addEMC(ItemInterface.getItem("honeyDrop").getItem().id, 1, EEMaps.getEMC(Item.DIAMOND.id));
/* 28 */       ModLoader.getLogger().fine("[EE2] Loaded EE2-Forestry Addon");
/*    */     }
/*    */     catch (Exception var1)
/*    */     {
/* 32 */       ModLoader.getLogger().warning("[EE2] Could not load EE2-Forestry Addon");
/* 33 */       var1.printStackTrace(System.err);
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEAddonForestry
 * JD-Core Version:    0.6.2
 */