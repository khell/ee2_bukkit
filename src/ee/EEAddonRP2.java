/*     */ package ee;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.logging.Logger;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockFlower;
/*     */ import net.minecraft.server.BlockLeaves;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.ModLoader;
/*     */ 
/*     */ public class EEAddonRP2
/*     */ {
/*     */   public static boolean rp2BaseIsInstalled;
/*     */   public static boolean rp2WorldIsInstalled;
/*  12 */   public static Block rp2Ores = null;
/*  13 */   public static Block rp2Leaves = null;
/*  14 */   public static Block rp2Logs = null;
/*  15 */   public static Block rp2Stone = null;
/*  16 */   public static Item rp2Resources = null;
/*  17 */   public static Block rp2Plants = null;
/*  18 */   public static Item rp2Seeds = null;
/*  19 */   public static Item rp2PickaxeRuby = null;
/*  20 */   public static Item rp2PickaxeSapphire = null;
/*  21 */   public static Item rp2PickaxeEmerald = null;
/*  22 */   public static Item rp2ShovelRuby = null;
/*  23 */   public static Item rp2ShovelSapphire = null;
/*  24 */   public static Item rp2ShovelEmerald = null;
/*  25 */   public static Item rp2AxeRuby = null;
/*  26 */   public static Item rp2AxeSapphire = null;
/*  27 */   public static Item rp2AxeEmerald = null;
/*  28 */   public static Item rp2SwordRuby = null;
/*  29 */   public static Item rp2SwordSapphire = null;
/*  30 */   public static Item rp2SwordEmerald = null;
/*  31 */   public static Item rp2HoeRuby = null;
/*  32 */   public static Item rp2HoeSapphire = null;
/*  33 */   public static Item rp2HoeEmerald = null;
/*  34 */   public static Item rp2SickleWood = null;
/*  35 */   public static Item rp2SickleStone = null;
/*  36 */   public static Item rp2SickleIron = null;
/*  37 */   public static Item rp2SickleGold = null;
/*  38 */   public static Item rp2SickleDiamond = null;
/*  39 */   public static Item rp2SickleRuby = null;
/*  40 */   public static Item rp2SickleSapphire = null;
/*  41 */   public static Item rp2SickleEmerald = null;
/*  42 */   public static Item rp2HandsawIron = null;
/*  43 */   public static Item rp2HandsawDiamond = null;
/*  44 */   public static Item rp2HandsawRuby = null;
/*  45 */   public static Item rp2HandsawSapphire = null;
/*  46 */   public static Item rp2HandsawEmerald = null;
/*  47 */   public static Item rp2IndigoDye = null;
/*     */ 
/*     */   public static void initBase()
/*     */   {
/*     */     try
/*     */     {
/*  53 */       rp2Resources = (Item)Class.forName("net.minecraft.server.RedPowerBase").getField("itemResource").get(null);
/*  54 */       rp2HandsawIron = (Item)Class.forName("net.minecraft.server.RedPowerBase").getField("itemHandsawIron").get(null);
/*  55 */       rp2HandsawDiamond = (Item)Class.forName("net.minecraft.server.RedPowerBase").getField("itemHandsawDiamond").get(null);
/*  56 */       rp2IndigoDye = (Item)Class.forName("net.minecraft.server.RedPowerBase").getField("itemDyeIndigo").get(null);
/*  57 */       EEMaps.addEMC(rp2Resources.id, 0, EEMaps.getEMC(Item.DIAMOND.id) / 8);
/*  58 */       EEMaps.addEMC(rp2Resources.id, 1, EEMaps.getEMC(Item.DIAMOND.id) / 8);
/*  59 */       EEMaps.addEMC(rp2Resources.id, 2, EEMaps.getEMC(Item.DIAMOND.id) / 8);
/*  60 */       EEMaps.addEMC(rp2HandsawIron.id, EEMaps.getEMC(Item.IRON_INGOT.id) * 4 + EEMaps.getEMC(Item.STICK.id) * 3);
/*  61 */       EEMaps.addEMC(rp2HandsawDiamond.id, EEMaps.getEMC(Item.DIAMOND.id) * 2 + EEMaps.getEMC(Item.IRON_INGOT.id) * 2 + EEMaps.getEMC(Item.STICK.id) * 3);
/*  62 */       EEMaps.addEMC(rp2Resources.id, 3, EEMaps.getEMC(Item.IRON_INGOT.id) * 2);
/*  63 */       EEMaps.addEMC(rp2Resources.id, 4, EEMaps.getEMC(Item.IRON_INGOT.id));
/*  64 */       EEMaps.addEMC(rp2Resources.id, 5, (EEMaps.getEMC(Item.IRON_INGOT.id) - 1) / 3);
/*  65 */       EEMaps.addEMC(rp2Resources.id, 6, EEMaps.getEMC(Item.REDSTONE.id) * 2);
/*  66 */       EEMaps.addEMC(rp2IndigoDye.id, EEMaps.getEMC(Item.INK_SACK.id, 0));
/*  67 */       EEMaps.addMeta(rp2Resources.id, 6);
/*  68 */       EEMaps.addFuelItem(rp2Resources.id, 6);
/*  69 */       rp2BaseIsInstalled = true;
/*  70 */       ModLoader.getLogger().fine("[EE2] Loaded EE2-RP2 Core Addon");
/*     */     }
/*     */     catch (Exception var1)
/*     */     {
/*  74 */       rp2BaseIsInstalled = false;
/*  75 */       ModLoader.getLogger().warning("[EE2] Could not load EE2-RP2 Core Addon");
/*  76 */       var1.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void initWorld()
/*     */   {
/*     */     try
/*     */     {
/*  84 */       rp2Ores = (Block)Class.forName("net.minecraft.server.RedPowerWorld").getField("blockOres").get(null);
/*  85 */       rp2Leaves = (Block)Class.forName("net.minecraft.server.RedPowerWorld").getField("blockLeaves").get(null);
/*  86 */       rp2Logs = (Block)Class.forName("net.minecraft.server.RedPowerWorld").getField("blockLogs").get(null);
/*  87 */       rp2Stone = (Block)Class.forName("net.minecraft.server.RedPowerWorld").getField("blockStone").get(null);
/*  88 */       rp2Plants = (Block)Class.forName("net.minecraft.server.RedPowerWorld").getField("blockPlants").get(null);
/*  89 */       rp2Seeds = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSeeds").get(null);
/*  90 */       rp2PickaxeRuby = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemPickaxeRuby").get(null);
/*  91 */       rp2PickaxeSapphire = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemPickaxeSapphire").get(null);
/*  92 */       rp2PickaxeEmerald = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemPickaxeEmerald").get(null);
/*  93 */       rp2ShovelRuby = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemShovelRuby").get(null);
/*  94 */       rp2ShovelSapphire = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemShovelSapphire").get(null);
/*  95 */       rp2ShovelEmerald = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemShovelEmerald").get(null);
/*  96 */       rp2AxeRuby = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemAxeRuby").get(null);
/*  97 */       rp2AxeSapphire = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemAxeSapphire").get(null);
/*  98 */       rp2AxeEmerald = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemAxeEmerald").get(null);
/*  99 */       rp2SwordRuby = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSwordRuby").get(null);
/* 100 */       rp2SwordSapphire = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSwordSapphire").get(null);
/* 101 */       rp2SwordEmerald = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSwordEmerald").get(null);
/* 102 */       rp2HoeRuby = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemHoeRuby").get(null);
/* 103 */       rp2HoeSapphire = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemHoeSapphire").get(null);
/* 104 */       rp2HoeEmerald = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemHoeEmerald").get(null);
/* 105 */       rp2SickleWood = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSickleWood").get(null);
/* 106 */       rp2SickleStone = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSickleStone").get(null);
/* 107 */       rp2SickleIron = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSickleIron").get(null);
/* 108 */       rp2SickleGold = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSickleGold").get(null);
/* 109 */       rp2SickleDiamond = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSickleDiamond").get(null);
/* 110 */       rp2SickleRuby = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSickleRuby").get(null);
/* 111 */       rp2SickleEmerald = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSickleEmerald").get(null);
/* 112 */       rp2SickleSapphire = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemSickleSapphire").get(null);
/* 113 */       rp2HandsawSapphire = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemHandsawSapphire").get(null);
/* 114 */       rp2HandsawRuby = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemHandsawRuby").get(null);
/* 115 */       rp2HandsawEmerald = (Item)Class.forName("net.minecraft.server.RedPowerWorld").getField("itemHandsawEmerald").get(null);
/* 116 */       EEMaps.addEMC(rp2SickleWood.id, EEMaps.getEMC(Block.WOOD.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id));
/* 117 */       EEMaps.addEMC(rp2SickleStone.id, EEMaps.getEMC(Block.COBBLESTONE.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id));
/* 118 */       EEMaps.addEMC(rp2SickleIron.id, EEMaps.getEMC(Item.IRON_INGOT.id) * 3 + EEMaps.getEMC(Item.STICK.id));
/* 119 */       EEMaps.addEMC(rp2SickleGold.id, EEMaps.getEMC(Item.GOLD_INGOT.id) * 3 + EEMaps.getEMC(Item.STICK.id));
/* 120 */       EEMaps.addEMC(rp2SickleDiamond.id, EEMaps.getEMC(Item.DIAMOND.id) * 3 + EEMaps.getEMC(Item.STICK.id));
/* 121 */       EEMaps.addEMC(rp2SickleRuby.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id));
/* 122 */       EEMaps.addEMC(rp2SickleEmerald.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id));
/* 123 */       EEMaps.addEMC(rp2SickleSapphire.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id));
/* 124 */       EEMaps.addEMC(rp2PickaxeRuby.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 125 */       EEMaps.addEMC(rp2PickaxeEmerald.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 126 */       EEMaps.addEMC(rp2PickaxeSapphire.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 127 */       EEMaps.addEMC(rp2AxeRuby.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 128 */       EEMaps.addEMC(rp2AxeEmerald.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 129 */       EEMaps.addEMC(rp2AxeSapphire.id, EEMaps.getEMC(rp2Resources.id, 0) * 3 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 130 */       EEMaps.addEMC(rp2HoeRuby.id, EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 131 */       EEMaps.addEMC(rp2HoeEmerald.id, EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 132 */       EEMaps.addEMC(rp2HoeSapphire.id, EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id) * 2);
/* 133 */       EEMaps.addEMC(rp2ShovelRuby.id, EEMaps.getEMC(rp2Resources.id, 0) + EEMaps.getEMC(Item.STICK.id) * 2);
/* 134 */       EEMaps.addEMC(rp2ShovelEmerald.id, EEMaps.getEMC(rp2Resources.id, 0) + EEMaps.getEMC(Item.STICK.id) * 2);
/* 135 */       EEMaps.addEMC(rp2ShovelSapphire.id, EEMaps.getEMC(rp2Resources.id, 0) + EEMaps.getEMC(Item.STICK.id) * 2);
/* 136 */       EEMaps.addEMC(rp2SwordRuby.id, EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id));
/* 137 */       EEMaps.addEMC(rp2SwordEmerald.id, EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id));
/* 138 */       EEMaps.addEMC(rp2SwordSapphire.id, EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id));
/* 139 */       EEMaps.addEMC(rp2HandsawSapphire.id, EEMaps.getEMC(Item.IRON_INGOT.id) * 2 + EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id) * 3);
/* 140 */       EEMaps.addEMC(rp2HandsawEmerald.id, EEMaps.getEMC(Item.IRON_INGOT.id) * 2 + EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id) * 3);
/* 141 */       EEMaps.addEMC(rp2HandsawRuby.id, EEMaps.getEMC(Item.IRON_INGOT.id) * 2 + EEMaps.getEMC(rp2Resources.id, 0) * 2 + EEMaps.getEMC(Item.STICK.id) * 3);
/* 142 */       EEMaps.AddRepairRecipe(getRepairable(rp2PickaxeRuby), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 143 */       EEMaps.AddRepairRecipe(getRepairable(rp2PickaxeEmerald), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 144 */       EEMaps.AddRepairRecipe(getRepairable(rp2PickaxeSapphire), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 145 */       EEMaps.AddRepairRecipe(getRepairable(rp2AxeRuby), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 146 */       EEMaps.AddRepairRecipe(getRepairable(rp2AxeEmerald), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 147 */       EEMaps.AddRepairRecipe(getRepairable(rp2AxeSapphire), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 148 */       EEMaps.AddRepairRecipe(getRepairable(rp2SwordRuby), new Object[] { EEMaps.mcov(), EEMaps.mcov() });
/* 149 */       EEMaps.AddRepairRecipe(getRepairable(rp2SwordEmerald), new Object[] { EEMaps.mcov(), EEMaps.mcov() });
/* 150 */       EEMaps.AddRepairRecipe(getRepairable(rp2SwordSapphire), new Object[] { EEMaps.mcov(), EEMaps.mcov() });
/* 151 */       EEMaps.AddRepairRecipe(getRepairable(rp2HoeRuby), new Object[] { EEMaps.mcov(), EEMaps.mcov() });
/* 152 */       EEMaps.AddRepairRecipe(getRepairable(rp2HoeEmerald), new Object[] { EEMaps.mcov(), EEMaps.mcov() });
/* 153 */       EEMaps.AddRepairRecipe(getRepairable(rp2HoeSapphire), new Object[] { EEMaps.mcov(), EEMaps.mcov() });
/* 154 */       EEMaps.AddRepairRecipe(getRepairable(rp2ShovelRuby), new Object[] { EEMaps.mcov() });
/* 155 */       EEMaps.AddRepairRecipe(getRepairable(rp2ShovelEmerald), new Object[] { EEMaps.mcov() });
/* 156 */       EEMaps.AddRepairRecipe(getRepairable(rp2ShovelSapphire), new Object[] { EEMaps.mcov() });
/* 157 */       EEMaps.AddRepairRecipe(getRepairable(rp2SickleWood), new Object[] { EEMaps.lcov(), EEMaps.lcov(), EEMaps.lcov() });
/* 158 */       EEMaps.AddRepairRecipe(getRepairable(rp2SickleStone), new Object[] { EEMaps.lcov(), EEMaps.lcov(), EEMaps.lcov() });
/* 159 */       EEMaps.AddRepairRecipe(getRepairable(rp2SickleIron), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 160 */       EEMaps.AddRepairRecipe(getRepairable(rp2SickleGold), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 161 */       EEMaps.AddRepairRecipe(getRepairable(rp2SickleRuby), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 162 */       EEMaps.AddRepairRecipe(getRepairable(rp2SickleEmerald), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 163 */       EEMaps.AddRepairRecipe(getRepairable(rp2SickleSapphire), new Object[] { EEMaps.mcov(), EEMaps.mcov(), EEMaps.mcov() });
/* 164 */       EEMaps.AddRepairRecipe(getRepairable(rp2SickleDiamond), new Object[] { EEMaps.hcov(), EEMaps.hcov(), EEMaps.hcov() });
/* 165 */       EEMaps.addEMC(rp2Stone.id, 0, EEMaps.getEMC(Block.COBBLESTONE.id));
/* 166 */       EEMaps.addEMC(rp2Stone.id, 1, EEMaps.getEMC(Block.COBBLESTONE.id));
/* 167 */       EEMaps.addEMC(rp2Stone.id, 2, EEMaps.getEMC(Block.COBBLESTONE.id));
/* 168 */       EEMaps.addEMC(rp2Stone.id, 3, EEMaps.getEMC(Block.COBBLESTONE.id));
/* 169 */       EEMaps.addEMC(rp2Stone.id, 4, EEMaps.getEMC(Block.COBBLESTONE.id));
/* 170 */       EEMaps.addMeta(rp2Stone.id, 4);
/* 171 */       EEMaps.addEMC(rp2Seeds.id, 4);
/* 172 */       EEMaps.addEMC(rp2Leaves.id, 0, EEMaps.getEMC(Block.LEAVES.id));
/* 173 */       EEMaps.addEMC(rp2Logs.id, 0, EEMaps.getEMC(Block.LOG.id));
/* 174 */       EEMaps.addEMC(rp2Plants.id, EEMaps.getEMC(Block.RED_ROSE.id));
/* 175 */       EEMaps.addEMC(rp2Ores.id, 6, EEMaps.getEMC(Item.DIAMOND.id) * 2);
/* 176 */       EEMaps.addOreBlock(rp2Ores.id);
/* 177 */       EEMaps.addLeafBlock(rp2Leaves.id);
/* 178 */       EEMaps.addWoodBlock(rp2Logs.id);
/* 179 */       rp2WorldIsInstalled = true;
/* 180 */       ModLoader.getLogger().fine("[EE2] Loaded EE2-RP2 World Addon");
/*     */     }
/*     */     catch (Exception var1)
/*     */     {
/* 184 */       rp2WorldIsInstalled = false;
/* 185 */       ModLoader.getLogger().warning("[EE2] Could not load EE2-RP2 World Addon");
/* 186 */       var1.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ItemStack getRepairable(Item var0)
/*     */   {
/* 192 */     return var0 == null ? null : new ItemStack(var0, 1, -1);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEAddonRP2
 * JD-Core Version:    0.6.2
 */