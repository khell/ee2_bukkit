/*     */ package ee;
/*     */ 
/*     */ import ic2.api.Items;
/*     */ import java.util.logging.Logger;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockLeaves;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.ModLoader;
/*     */ 
/*     */ public class EEAddonIC2
/*     */ {
/*     */   public static void initialize()
/*     */   {
/*     */     try
/*     */     {
/*  15 */       EEMaps.addAlchemicalValue(Items.getItem("copperIngot"), (EEMaps.getEMC(Item.IRON_INGOT.id) - 1) / 3);
/*  16 */       EEMaps.addAlchemicalValue(Items.getItem("copperDust"), (EEMaps.getEMC(Item.IRON_INGOT.id) - 1) / 3);
/*  17 */       EEMaps.addAlchemicalValue(Items.getItem("tinIngot"), EEMaps.getEMC(Item.IRON_INGOT.id));
/*  18 */       EEMaps.addAlchemicalValue(Items.getItem("tinDust"), EEMaps.getEMC(Item.IRON_INGOT.id));
/*  19 */       EEMaps.addAlchemicalValue(Items.getItem("bronzeIngot"), (EEMaps.getEMC(Items.getItem("copperIngot")) * 3 + EEMaps.getEMC(Items.getItem("tinIngot"))) / 2);
/*  20 */       EEMaps.addAlchemicalValue(Items.getItem("bronzeDust"), (EEMaps.getEMC(Items.getItem("copperDust")) * 3 + EEMaps.getEMC(Items.getItem("tinDust"))) / 2);
/*  21 */       EEMaps.addAlchemicalValue(Items.getItem("ironDust"), EEMaps.getEMC(Item.IRON_INGOT.id));
/*  22 */       EEMaps.addAlchemicalValue(Items.getItem("goldDust"), EEMaps.getEMC(Item.IRON_INGOT.id) * 8);
/*  23 */       EEMaps.addAlchemicalValue(Items.getItem("silverDust"), EEMaps.getEMC(Item.IRON_INGOT.id) * 2);
/*  24 */       EEMaps.addAlchemicalValue(Items.getItem("resin"), EEMaps.getEMC(Item.SLIME_BALL.id));
/*  25 */       EEMaps.addAlchemicalValue(Items.getItem("rubberWood"), EEMaps.getEMC(Block.LOG.id));
/*  26 */       EEMaps.addAlchemicalValue(Items.getItem("rubberSapling"), EEMaps.getEMC(Block.LOG.id));
/*  27 */       EEMaps.addAlchemicalValue(Items.getItem("rubberLeaves"), EEMaps.getEMC(Block.LEAVES.id));
/*  28 */       EEMaps.addAlchemicalValue(Items.getItem("lavaCell"), EEMaps.getEMC(Item.LAVA_BUCKET.id) - EEMaps.getEMC(Item.BUCKET.id) + EEMaps.getEMC(Items.getItem("tinIngot")) / 4);
/*  29 */       EEMaps.addAlchemicalValue(Items.getItem("uraniumDrop"), EEMaps.getEMC(new ItemStack(EEItem.mobiusFuel)) * 2);
/*  30 */       EEMaps.addChargedItem(Items.getItem("constructionFoamSprayer"));
/*  31 */       EEMaps.addChargedItem(Items.getItem("miningDrill"));
/*  32 */       EEMaps.addChargedItem(Items.getItem("diamondDrill"));
/*  33 */       EEMaps.addChargedItem(Items.getItem("chainsaw"));
/*  34 */       EEMaps.addChargedItem(Items.getItem("electricWrench"));
/*  35 */       EEMaps.addChargedItem(Items.getItem("electricTreetap"));
/*  36 */       EEMaps.addChargedItem(Items.getItem("miningLaser"));
/*  37 */       EEMaps.addChargedItem(Items.getItem("odScanner"));
/*  38 */       EEMaps.addChargedItem(Items.getItem("ovScanner"));
/*  39 */       EEMaps.addChargedItem(Items.getItem("nanoSaber"));
/*  40 */       EEMaps.addChargedItem(Items.getItem("enabledNanoSaber"));
/*  41 */       EEMaps.addChargedItem(Items.getItem("nanoHelmet"));
/*  42 */       EEMaps.addChargedItem(Items.getItem("nanoBodyarmor"));
/*  43 */       EEMaps.addChargedItem(Items.getItem("nanoLeggings"));
/*  44 */       EEMaps.addChargedItem(Items.getItem("nanoBoots"));
/*  45 */       EEMaps.addChargedItem(Items.getItem("quantumHelmet"));
/*  46 */       EEMaps.addChargedItem(Items.getItem("quantumBodyarmor"));
/*  47 */       EEMaps.addChargedItem(Items.getItem("quantumLeggings"));
/*  48 */       EEMaps.addChargedItem(Items.getItem("quantumBoots"));
/*  49 */       EEMaps.addChargedItem(Items.getItem("jetpack"));
/*  50 */       EEMaps.addChargedItem(Items.getItem("electricJetpack"));
/*  51 */       EEMaps.addChargedItem(Items.getItem("batPack"));
/*  52 */       EEMaps.addChargedItem(Items.getItem("lapPack"));
/*  53 */       EEMaps.addChargedItem(Items.getItem("cfPack"));
/*  54 */       EEMaps.addChargedItem(Items.getItem("reBattery"));
/*  55 */       EEMaps.addChargedItem(Items.getItem("chargedReBattery"));
/*  56 */       EEMaps.addChargedItem(Items.getItem("energyCrystal"));
/*  57 */       EEMaps.addChargedItem(Items.getItem("lapotronCrystal"));
/*  58 */       EEMaps.addChargedItem(Items.getItem("filledFuelCan"));
/*  59 */       EEMaps.addChargedItem(Items.getItem("uraniumCell"));
/*  60 */       EEMaps.addChargedItem(Items.getItem("coolingCell"));
/*  61 */       EEMaps.addChargedItem(Items.getItem("depletedIsotopeCell"));
/*  62 */       EEMaps.addChargedItem(Items.getItem("integratedReactorPlating"));
/*  63 */       EEMaps.addChargedItem(Items.getItem("integratedHeatDisperser"));
/*  64 */       EEMaps.addChargedItem(Items.getItem("hydratingCell"));
/*  65 */       EEMaps.addChargedItem(Items.getItem("electricHoe"));
/*  66 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("treetap")), new Object[] { EEMaps.lcov() });
/*  67 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("rubberBoots")), new Object[] { EEMaps.lcov() });
/*  68 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzePickaxe")), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/*  69 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzeAxe")), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/*  70 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzeSword")), new Object[] { EEMaps.mcov(), EEMaps.mcov() });
/*  71 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzeShovel")), new Object[] { EEMaps.mcov() });
/*  72 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzeHoe")), new Object[] { EEMaps.mcov(), EEMaps.mcov() });
/*  73 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzeHelmet")), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/*  74 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzeChestplate")), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/*  75 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzeLeggings")), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/*  76 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("bronzeBoots")), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/*  77 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("compositeArmor")), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/*  78 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("wrench")), new Object[] { EEMaps.mcov() });
/*  79 */       EEMaps.AddRepairRecipe(getRepairable(Items.getItem("cutter")), new Object[] { EEMaps.mcov() });
/*  80 */       EEMaps.addOreBlock(Items.getItem("copperOre"));
/*  81 */       EEMaps.addOreBlock(Items.getItem("tinOre"));
/*  82 */       EEMaps.addOreBlock(Items.getItem("uraniumOre"));
/*  83 */       EEMaps.addLeafBlock(Items.getItem("rubberLeaves"));
/*  84 */       EEMaps.addWoodBlock(Items.getItem("rubberWood"));
/*     */ 
/*  86 */       if (Items.getItem("lavaCell") != null)
/*     */       {
/*  88 */         EEMaps.addFuelItem(Items.getItem("lavaCell").id);
/*     */       }
/*     */ 
/*  91 */       if (Items.getItem("uraniumDrop") != null)
/*     */       {
/*  93 */         EEMaps.addFuelItem(Items.getItem("uraniumDrop").id);
/*     */       }
/*     */ 
/*  96 */       ModLoader.getLogger().fine("[EE2] Loaded EE2-IC2 Addon");
/*     */     }
/*     */     catch (Exception var1)
/*     */     {
/* 100 */       ModLoader.getLogger().warning("[EE2] Could not load EE2-IC2 Addon");
/* 101 */       var1.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ItemStack getRepairable(ItemStack var0)
/*     */   {
/* 107 */     return var0 == null ? null : new ItemStack(var0.id, 1, -1);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEAddonIC2
 * JD-Core Version:    0.6.2
 */