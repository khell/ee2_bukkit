/*      */ package ee;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashMap;
/*      */ import net.minecraft.server.Block;
/*      */ import net.minecraft.server.BlockDeadBush;
/*      */ import net.minecraft.server.BlockFlower;
/*      */ import net.minecraft.server.BlockGrass;
/*      */ import net.minecraft.server.BlockLeaves;
/*      */ import net.minecraft.server.BlockLongGrass;
/*      */ import net.minecraft.server.BlockMycel;
/*      */ import net.minecraft.server.Item;
/*      */ import net.minecraft.server.ItemShears;
/*      */ import net.minecraft.server.ItemStack;
/*      */ import net.minecraft.server.ItemWorldMap;
/*      */ import net.minecraft.server.ModLoader;
/*      */ 
/*      */ public class EEMaps
/*      */ {
/*   12 */   public static HashMap alchemicalValues = new HashMap();
/*   13 */   public static HashMap modBlacklist = new HashMap();
/*   14 */   public static HashMap flyingArmor = new HashMap();
/*   15 */   public static HashMap flyingItems = new HashMap();
/*   16 */   public static HashMap fireImmuneItems = new HashMap();
/*   17 */   public static HashMap fireImmuneArmors = new HashMap();
/*   18 */   public static HashMap durationEffectItems = new HashMap();
/*   19 */   public static HashMap modItems = new HashMap();
/*   20 */   public static HashMap chargedItems = new HashMap();
/*   21 */   public static HashMap pedestalItems = new HashMap();
/*   22 */   public static HashMap chestItems = new HashMap();
/*   23 */   public static HashMap woodBlockRegistry = new HashMap();
/*   24 */   public static HashMap leafBlockRegistry = new HashMap();
/*   25 */   public static HashMap oreBlockRegistry = new HashMap();
/*   26 */   public static HashMap fuelItemRegistry = new HashMap();
/*   27 */   public static HashMap metaMappings = new HashMap();
/*      */ 
/*      */   public static boolean isBlacklisted(String var0)
/*      */   {
/*   31 */     return modBlacklist.containsValue(var0);
/*      */   }
/*      */ 
/*      */   public static void addNameToBlacklist(String var0)
/*      */   {
/*   38 */     for (int var1 = 0; modBlacklist.get(Integer.valueOf(var1)) != null; var1++)
/*   43 */     		modBlacklist.put(Integer.valueOf(var1), var0);
/*      */   }
/*      */ 
/*      */   public static boolean isLeaf(int var0)
/*      */   {
/*   48 */     return leafBlockRegistry.containsValue(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addLeafBlock(int var0)
/*      */   {
/*   55 */     for (int var1 = 0; leafBlockRegistry.get(Integer.valueOf(var1)) != null; var1++)
/*   60 */     leafBlockRegistry.put(Integer.valueOf(var1), Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static boolean isWood(int var0)
/*      */   {
/*   65 */     return woodBlockRegistry.containsValue(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addWoodBlock(int var0)
/*      */   {
/*   72 */     for (int var1 = 0; woodBlockRegistry.get(Integer.valueOf(var1)) != null; var1++)
/*   77 */     woodBlockRegistry.put(Integer.valueOf(var1), Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static boolean isChestItem(int var0)
/*      */   {
/*   82 */     return isChestItem(var0, 0);
/*      */   }
/*      */ 
/*      */   public static boolean isChestItem(int var0, int var1)
/*      */   {
/*   87 */     return chestItems.get(Integer.valueOf(var0)) == null ? false : ((Boolean)((HashMap)chestItems.get(Integer.valueOf(var0))).get(Integer.valueOf(var1))).booleanValue();
/*      */   }
/*      */ 
/*      */   private static void addChestItem(int var0)
/*      */   {
/*   92 */     addChestItem(var0, 0);
/*      */   }
/*      */ 
/*      */   private static void addChestItem(int var0, int var1)
/*      */   {
/*   97 */     HashMap var2 = new HashMap();
/*      */ 
/*   99 */     if (chestItems.get(Integer.valueOf(var0)) != null)
/*      */     {
/*  101 */       var2 = (HashMap)chestItems.get(Integer.valueOf(var0));
/*      */     }
/*      */ 
/*  104 */     var2.put(Integer.valueOf(var1), Boolean.valueOf(true));
/*  105 */     chestItems.put(Integer.valueOf(var0), var2);
/*      */   }
/*      */ 
/*      */   public static void AddRepairRecipe(ItemStack var0, Object[] var1)
/*      */   {
/*  110 */     if (var0 != null)
/*      */     {
/*  112 */       ItemStack var2 = var0.cloneItemStack();
/*  113 */       Object[] var3 = new Object[var1.length + 1];
/*      */ 
/*  115 */       for (int var4 = 0; var4 < var3.length; var4++)
/*      */       {
/*  117 */         if (var4 >= var1.length)
/*      */         {
/*  119 */           var3[var4] = var0;
/*  120 */           break;
/*      */         }
/*      */ 
/*  123 */         var3[var4] = var1[var4];
/*      */       }
/*      */ 
/*  126 */       var2.setData(0);
/*  127 */       ModLoader.addShapelessRecipe(var2, var3);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void AddPSRecipes(ItemStack var0, ItemStack var1, ItemStack var2, ItemStack var3, ItemStack var4, ItemStack var5, ItemStack var6, ItemStack var7, ItemStack var8)
/*      */   {
/*  133 */     ItemStack[] var9 = { var1, var2, var3, var4, var5, var6, var7, var8 };
/*      */ 
/*  135 */     for (int var10 = 2; var10 <= 9; var10++)
/*      */     {
/*  137 */       Object[] var11 = new Object[var10];
/*  138 */       ItemStack[] var12 = new ItemStack[var10 - 1];
/*  139 */       var11[0] = pstone();
/*      */ 
/*  141 */       for (int var13 = 1; var13 < var10; var13++)
/*      */       {
/*  143 */         var11[var13] = var0;
/*  144 */         var12[(var13 - 1)] = var0;
/*      */       }
/*      */ 
/*  147 */       checkRecipe(var9[(var10 - 2)], var12);
/*  148 */       ModLoader.addShapelessRecipe(var9[(var10 - 2)], var11);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void checkRecipe(ItemStack var0, ItemStack[] var1)
/*      */   {
/*  154 */     if ((var0 != null) && (var1[0] != null))
/*      */     {
/*  156 */       int var2 = 0;
/*  157 */       String var3 = var1[0].getItem().getName();
/*  158 */       String var4 = var0.getItem().getName();
/*  159 */       ItemStack[] var5 = var1;
/*  160 */       int var6 = var1.length;
/*      */ 
/*  162 */       for (int var7 = 0; var7 < var6; var7++)
/*      */       {
/*  164 */         ItemStack var8 = var5[var7];
/*      */ 
/*  166 */         if (var8 != null)
/*      */         {
/*  168 */           var2 += getEMC(var8.id, var8.getData() == -1 ? 0 : var8.getData());
/*      */ 
/*  170 */           if (var8.getItem().k())
/*      */           {
/*  172 */             var2 -= getEMC(var8.getItem().j().id);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  177 */       int var9 = getEMC(var0.id, var0.getData()) * var0.count;
/*      */ 
/*  179 */       if (var0.getItem().k())
/*      */       {
/*  181 */         var9 -= getEMC(var0.getItem().j().id);
/*      */       }
/*      */ 
/*  184 */       if (var2 != var9)
/*      */       {
/*  186 */         System.out.println("Inconsistency when combining " + var1.length + " meta " + var1[0].getData() + " " + var3 + " to make " + var4);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  191 */       System.out.println("EMC Consistency Check - error, output or first input returns null..");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean isChargedItem(int var0)
/*      */   {
/*  197 */     return chargedItems.containsValue(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addChargedItem(int var0)
/*      */   {
/*  204 */     for (int var1 = 0; chargedItems.get(Integer.valueOf(var1)) != null; var1++)
/*  209 */     chargedItems.put(Integer.valueOf(var1), Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static boolean isPedestalItem(int var0)
/*      */   {
/*  214 */     return isPedestalItem(var0, 0);
/*      */   }
/*      */ 
/*      */   public static boolean isPedestalItem(int var0, int var1)
/*      */   {
/*  219 */     return pedestalItems.get(Integer.valueOf(var0)) == null ? false : ((Boolean)((HashMap)pedestalItems.get(Integer.valueOf(var0))).get(Integer.valueOf(var1))).booleanValue();
/*      */   }
/*      */ 
/*      */   private static void addPedestalItem(int var0)
/*      */   {
/*  224 */     addPedestalItem(var0, 0);
/*      */   }
/*      */ 
/*      */   private static void addPedestalItem(int var0, int var1)
/*      */   {
/*  229 */     HashMap var2 = new HashMap();
/*      */ 
/*  231 */     if (pedestalItems.get(Integer.valueOf(var0)) != null)
/*      */     {
/*  233 */       var2 = (HashMap)pedestalItems.get(Integer.valueOf(var0));
/*      */     }
/*      */ 
/*  236 */     var2.put(Integer.valueOf(var1), Boolean.valueOf(true));
/*  237 */     pedestalItems.put(Integer.valueOf(var0), var2);
/*      */   }
/*      */ 
/*      */   public static boolean isModItem(int var0)
/*      */   {
/*  242 */     return modItems.containsValue(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addModItem(int var0)
/*      */   {
/*  249 */     for (int var1 = 0; modItems.get(Integer.valueOf(var1)) != null; var1++)
/*  254 */     modItems.put(Integer.valueOf(var1), Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addDurationEffectItem(int var0)
/*      */   {
/*  259 */     durationEffectItems.put(Integer.valueOf(var0), Boolean.valueOf(true));
/*      */   }
/*      */ 
/*      */   public static boolean hasDurationEffect(Item var0)
/*      */   {
/*  264 */     return durationEffectItems.get(Integer.valueOf(var0.id)) == null ? false : ((Boolean)durationEffectItems.get(Integer.valueOf(var0.id))).booleanValue();
/*      */   }
/*      */ 
/*      */   public static boolean isFuel(ItemStack var0)
/*      */   {
/*  269 */     return isFuel(var0.id, var0.getData());
/*      */   }
/*      */ 
/*      */   public static boolean isFuel(int var0, int var1)
/*      */   {
/*  274 */     return fuelItemRegistry.get(Integer.valueOf(var0)) == null ? false : ((HashMap)fuelItemRegistry.get(Integer.valueOf(var0))).containsValue(Integer.valueOf(var1));
/*      */   }
/*      */ 
/*      */   public static void addFuelItem(int var0)
/*      */   {
/*  279 */     addFuelItem(var0, 0);
/*      */   }
/*      */ 
/*      */   public static void addFuelItem(int var0, int var1)
/*      */   {
/*  284 */     HashMap var2 = new HashMap();
/*      */ 
/*  286 */     if (fuelItemRegistry.get(Integer.valueOf(var0)) != null)
/*      */     {
/*  288 */       var2 = (HashMap)fuelItemRegistry.get(Integer.valueOf(var0));
/*      */     }
/*      */ 
/*  293 */     for (int var3 = 0; var2.get(Integer.valueOf(var3)) != null; var3++) {
/*  298 */     var2.put(Integer.valueOf(var3), Integer.valueOf(var1));
/*  299 */     fuelItemRegistry.put(Integer.valueOf(var0), var2);
}
/*      */   }
/*      */ 
/*      */   public static boolean isOreBlock(int var0)
/*      */   {
/*  304 */     return oreBlockRegistry.containsValue(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addOreBlock(int var0)
/*      */   {
/*  311 */     for (int var1 = 0; oreBlockRegistry.get(Integer.valueOf(var1)) != null; var1++)
/*  316 */     oreBlockRegistry.put(Integer.valueOf(var1), Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static int getEMC(ItemStack var0)
/*      */   {
/*  321 */     return getEMC(var0.id, var0.getData()) == 0 ? 0 : getEMC(var0.id) > 0 ? getEMC(var0.id) : var0.d() ? (int)(getEMC(var0.id) * ((var0.i() - var0.getData()) / var0.i())) : var0 == null ? 0 : getEMC(var0.id, var0.getData());
/*      */   }
/*      */ 
/*      */   public static int getEMC(int var0)
/*      */   {
/*  326 */     return getEMC(var0, 0);
/*      */   }
/*      */ 
/*      */   public static int getEMC(int var0, int var1)
/*      */   {
/*  331 */     return ((HashMap)alchemicalValues.get(Integer.valueOf(var0))).get(Integer.valueOf(var1)) == null ? 0 : alchemicalValues.get(Integer.valueOf(var0)) == null ? 0 : ((Integer)((HashMap)alchemicalValues.get(Integer.valueOf(var0))).get(Integer.valueOf(var1))).intValue();
/*      */   }
/*      */ 
/*      */   public static void addEMC(int var0, int var1)
/*      */   {
/*  336 */     addEMC(var0, 0, var1);
/*      */   }
/*      */ 
			public static int canAddEMC(int itemId, int itemSubId) {
				String banned[] = EEBase.props.func_26614_getString("NoEMC").split(",");
				for(int i = 0; i < banned.length; i++) {
					String ids[] = banned[i].split(":");
					if(itemId == Integer.parseInt(ids[0])) {
						if(ids.length == 1 || Integer.parseInt(ids[1]) == itemSubId) //ban all items with itemId if no sub id specified, or ban if matches
							return 0;
					}
				}
				return 1;
			}
			
/*      */   public static void addEMC(int var0, int var1, int var2)
/*      */   {
			   //Note: Item subids are not correctly used by Dark Matter Relays and Transmutation Tablets.
			   //These will treat the item as the main item of subid 0, and so EMC values will not change unless the main item is also blocked.
			   //Example: Bone Meal (351:15) if EMC blocked will still be condensable in DMR or TT as long as 351 (351:0) remains unblocked. The methods for
			   //DMR and TT only check the main item id EMC value, and assumes they are the same. The Energy Condenser does check ids properly however, and
	           //this removes the automation aspect of EMC farming at least.

			   //TODO: Fix DMR and TTs to check the sub id too.
			   if (canAddEMC(var0, var1) == 0) {
				   System.out.println("ALERT: Skipped addEMC call for item id " + var0 + ":" + var1 + "; item is EMC blocked.");
				   return;
			   }
			   
/*  341 */     if (var2 == 0)
/*      */     {
/*  343 */       System.out.println("Error: Alchemical Value of 0 being added to hashmap for item index " + var0 + " of meta " + var1);
/*      */     }
/*      */ 
/*  346 */     HashMap var3 = new HashMap();
/*      */ 
/*  348 */     if (alchemicalValues.get(Integer.valueOf(var0)) != null)
/*      */     {
/*  350 */       var3 = (HashMap)alchemicalValues.get(Integer.valueOf(var0));
/*      */     }
/*      */ 
/*  353 */     var3.put(Integer.valueOf(var1), Integer.valueOf(var2));
/*  354 */     alchemicalValues.put(Integer.valueOf(var0), var3);
/*      */   }
/*      */ 
/*      */   public static int getMeta(int var0)
/*      */   {
/*  359 */     return metaMappings.get(Integer.valueOf(var0)) == null ? 0 : ((Integer)metaMappings.get(Integer.valueOf(var0))).intValue();
/*      */   }
/*      */ 
/*      */   public static void addMeta(int var0, int var1)
/*      */   {
/*  364 */     metaMappings.put(Integer.valueOf(var0), Integer.valueOf(var1));
/*      */   }
/*      */ 
/*      */   public static boolean isFlyingItem(int var0)
/*      */   {
/*  369 */     return flyingItems.containsValue(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addFlyingItem(int var0)
/*      */   {
/*  376 */     for (int var1 = 0; flyingItems.get(Integer.valueOf(var1)) != null; var1++)
/*  381 */     flyingItems.put(Integer.valueOf(var1), Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static boolean isFireImmuneItem(int var0)
/*      */   {
/*  386 */     return fireImmuneItems.containsValue(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addFireImmuneItem(int var0)
/*      */   {
/*  393 */     for (int var1 = 0; fireImmuneItems.get(Integer.valueOf(var1)) != null; var1++)
/*  398 */     fireImmuneItems.put(Integer.valueOf(var1), Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static boolean isFireImmuneArmor(int var0)
/*      */   {
/*  403 */     return fireImmuneArmors.containsValue(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static void addFireImmuneArmor(int var0)
/*      */   {
/*  410 */     for (int var1 = 0; fireImmuneArmors.get(Integer.valueOf(var1)) != null; var1++)
/*  415 */     fireImmuneArmors.put(Integer.valueOf(var1), Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static EEProps InitProps(EEProps var0)
/*      */   {
/*  420 */     var0.getInt("machineFactor", 4);
/*  421 */     var0.getInt("CondenserGUI", 46);
/*  422 */     var0.getInt("CollectorGUI", 47);
/*  423 */     var0.getInt("Collector2GUI", 48);
/*  424 */     var0.getInt("Collector3GUI", 49);
/*  425 */     var0.getInt("AlchChestGUI", 50);
/*  426 */     var0.getInt("DMFurnaceGUI", 51);
/*  427 */     var0.getInt("RMFurnaceGUI", 52);
/*  428 */     var0.getInt("RelayGUI", 53);
/*  429 */     var0.getInt("Relay2GUI", 54);
/*  430 */     var0.getInt("Relay3GUI", 55);
/*  431 */     var0.getInt("AlchBagGUI", 56);
/*  432 */     var0.getInt("TransmutationGUI", 57);
/*  433 */     var0.getInt("PortableTransmutationGUI", 58);
/*  434 */     var0.getInt("PortableCraftingGUI", 59);
/*  435 */     var0.getInt("PedestalGUI", 60);
/*  436 */     var0.getInt("MercurialGUI", 61);
/*  437 */     var0.getInt("BlockEEStone", 175);
/*  438 */     var0.getInt("BlockEEPedestal", 176);
/*  439 */     var0.getInt("BlockEEChest", 177);
/*  440 */     var0.getInt("BlockEETorch", 178);
/*  441 */     var0.getInt("BlockEEDevice", 179);
/*  442 */     var0.getInt("ItemPhilStone", 27270);
/*  443 */     var0.getInt("ItemCatalystStone", 27271);
/*  444 */     var0.getInt("ItemBaseRing", 27272);
/*  445 */     var0.getInt("ItemSoulStone", 27273);
/*  446 */     var0.getInt("ItemEvertide", 27274);
/*  447 */     var0.getInt("ItemVolcanite", 27275);
/*  448 */     var0.getInt("ItemAttractionRing", 27276);
/*  449 */     var0.getInt("ItemIgnitionRing", 27277);
/*  450 */     var0.getInt("ItemGrimarchRing", 27278);
/*  451 */     var0.getInt("ItemHyperkineticLens", 27279);
/*  452 */     var0.getInt("ItemSwiftWolfRing", 27280);
/*  453 */     var0.getInt("ItemHarvestRing", 27281);
/*  454 */     var0.getInt("ItemWatchOfTime", 27282);
/*  455 */     var0.getInt("ItemAlchemicalCoal", 27283);
/*  456 */     var0.getInt("ItemMobiusFuel", 27284);
/*  457 */     var0.getInt("ItemDarkMatter", 27285);
/*  458 */     var0.getInt("ItemCovalenceDust", 27286);
/*  459 */     var0.getInt("ItemDarkPickaxe", 27287);
/*  460 */     var0.getInt("ItemDarkSpade", 27288);
/*  461 */     var0.getInt("ItemDarkHoe", 27289);
/*  462 */     var0.getInt("ItemDarkSword", 27290);
/*  463 */     var0.getInt("ItemDarkAxe", 27291);
/*  464 */     var0.getInt("ItemDarkShears", 27292);
/*  465 */     var0.getInt("ItemDarkHammer", 27299);
/*  466 */     var0.getInt("ItemDarkMatterArmor", 27293);
/*  467 */     var0.getInt("ItemDarkMatterHelmet", 27294);
/*  468 */     var0.getInt("ItemDarkMatterGreaves", 27295);
/*  469 */     var0.getInt("ItemDarkMatterBoots", 27296);
/*  470 */     var0.getInt("ItemEternalDensity", 27297);
/*  471 */     var0.getInt("ItemRepairCharm", 27298);
/*  472 */     var0.getInt("ItemHyperCatalyst", 27300);
/*  473 */     var0.getInt("ItemKleinStar", 27301);
/*  474 */     var0.getInt("ItemKleinStar2", 27302);
/*  475 */     var0.getInt("ItemKleinStar3", 27303);
/*  476 */     var0.getInt("ItemKleinStar4", 27304);
/*  477 */     var0.getInt("ItemKleinStar5", 27305);
/*  478 */     var0.getInt("ItemKleinStar6", 27335);
/*  479 */     var0.getInt("ItemAlchemyBag", 27306);
/*  480 */     var0.getInt("ItemRedMatter", 27307);
/*  481 */     var0.getInt("ItemRedPickaxe", 27308);
/*  482 */     var0.getInt("ItemRedSpade", 27309);
/*  483 */     var0.getInt("ItemRedHoe", 27310);
/*  484 */     var0.getInt("ItemRedSword", 27311);
/*  485 */     var0.getInt("ItemRedAxe", 27312);
/*  486 */     var0.getInt("ItemRedShears", 27313);
/*  487 */     var0.getInt("ItemRedHammer", 27314);
/*  488 */     var0.getInt("ItemAeternalisFuel", 27315);
/*  489 */     var0.getInt("ItemRedKatar", 27316);
/*  490 */     var0.getInt("ItemRedMace", 27317);
/*  491 */     var0.getInt("ItemZeroRing", 27318);
/*  492 */     var0.getInt("ItemRedMatterArmor", 27319);
/*  493 */     var0.getInt("ItemRedMatterHelmet", 27320);
/*  494 */     var0.getInt("ItemRedMatterGreaves", 27321);
/*  495 */     var0.getInt("ItemRedMatterBoots", 27322);
/*  496 */     var0.getInt("ItemRedMatterArmorP", 27323);
/*  497 */     var0.getInt("ItemRedMatterHelmetP", 27324);
/*  498 */     var0.getInt("ItemRedMatterGreavesP", 27325);
/*  499 */     var0.getInt("ItemRedMatterBootsP", 27326);
/*  500 */     var0.getInt("ItemMercurialEye", 27327);
/*  501 */     var0.getInt("ItemArcaneRing", 27328);
/*  502 */     var0.getInt("ItemDiviningRod", 27329);
/*  503 */     var0.getInt("ItemBodyStone", 27332);
/*  504 */     var0.getInt("ItemLifeStone", 27333);
/*  505 */     var0.getInt("ItemMindStone", 27334);
/*  506 */     var0.getInt("ItemTransTablet", 27336);
/*  507 */     var0.getInt("ItemVoidRing", 27337);
/*  508 */     var0.getInt("ItemAlchemyTome", 27338);
/*  509 */     var0.getInt("AllowCollectors", 1);
/*  510 */     var0.getInt("AllowCondensers", 1);
/*  511 */     var0.getInt("AllowRelays", 1);
/*  512 */     var0.getInt("AllowChests", 1);
/*  513 */     var0.getInt("AllowPedestals", 1);
/*  514 */     var0.getInt("AllowFurnaces", 1);
/*  515 */     var0.getInt("AllowInterdiction", 1);
/*  516 */     var0.getInt("AllowIgnition", 1);
/*  517 */     var0.getInt("AllowZeroRing", 1);
/*  518 */     var0.getInt("AllowSwiftWolf", 1);
/*  519 */     var0.getInt("AllowHarvestBand", 1);
/*  520 */     var0.getInt("AllowArcana", 1);
/*  521 */     var0.getInt("AllowArchangel", 1);
/*  522 */     var0.getInt("AllowVoidRing", 1);
/*  523 */     var0.getInt("AllowBlackHoleBand", 1);
/*  524 */     var0.getInt("AllowEternalDensity", 1);
/*  525 */     var0.getInt("AllowSoulstone", 1);
/*  526 */     var0.getInt("AllowBodystone", 1);
/*  527 */     var0.getInt("AllowLifestone", 1);
/*  528 */     var0.getInt("AllowMindstone", 1);
/*  529 */     var0.getInt("AllowRepair", 1);
/*  530 */     var0.getInt("AllowWatchOfTime", 1);
/*  531 */     var0.getInt("AllowMercurial", 1);
/*  532 */     var0.getInt("AllowDCatalyst", 1);
/*  533 */     var0.getInt("AllowHKLens", 1);
/*  534 */     var0.getInt("AllowHCLens", 1);
/*  535 */     var0.getInt("AllowDMTools", 1);
/*  536 */     var0.getInt("AllowRMTools", 1);
/*  537 */     var0.getInt("AllowAlchemyBags", 1);
/*  538 */     var0.getInt("AllowDMArmor", 1);
/*  539 */     var0.getInt("AllowRMArmor", 1);
/*  540 */     var0.getInt("AllowRMArmorPlus", 1);
/*  541 */     var0.getInt("AllowKleinStar", 1);
/*  542 */     var0.getInt("AllowEvertide", 1);
/*  543 */     var0.getInt("AllowVolcanite", 1);
/*  544 */     var0.getInt("AllowDiviningRod", 1);
/*  545 */     var0.getInt("AllowNovaC1", 1);
/*  546 */     var0.getInt("AllowNovaC2", 1);
/*  547 */     var0.getInt("AllowTransmutationTable", 1);
/*  548 */     return var0;
/*      */   }
/*      */ 
/*      */   public static void InitAlchemicalValues()
/*      */   {
/*  553 */     System.out.println("Initializing alchemy values for Equivalent Exchange..");
/*      */ 
/*  556 */     for (int var0 = 0; var0 < 4; var0++)
/*      */     {
/*  558 */       addEMC(Block.LEAVES.id, var0, 1);
/*  559 */       addEMC(Block.LOG.id, var0, 32);
/*  560 */       addEMC(Block.SAPLING.id, var0, getEMC(Block.LOG.id));
/*      */     }
/*      */ 
/*  563 */     addEMC(Item.COAL.id, 1, getEMC(Block.LOG.id));
/*  564 */     addEMC(Item.REDSTONE.id, 0, getEMC(Item.COAL.id, 1) * 2);
/*  565 */     addEMC(Item.COAL.id, 0, getEMC(Item.COAL.id, 1) * 4);
/*  566 */     addEMC(Block.WOOD.id, getEMC(Block.LOG.id) / 4);
/*  567 */     addEMC(Block.WORKBENCH.id, getEMC(Block.WOOD.id) * 4);
/*  568 */     addEMC(Item.WOOD_DOOR.id, getEMC(Block.WOOD.id) * 6);
/*  569 */     addEMC(Block.CHEST.id, getEMC(Block.WOOD.id) * 8);
/*  570 */     addEMC(Block.WOOD_STAIRS.id, getEMC(Block.WOOD.id) * 6 / 4);
/*  571 */     //addEMC(Block.STEP.id, 2, getEMC(Block.WOOD.id) / 2);
/*  572 */     addEMC(Item.BOAT.id, getEMC(Block.WOOD.id) * 5);
/*  573 */     addEMC(Block.WOOD_PLATE.id, getEMC(Block.WOOD.id) * 2);
/*  574 */     addEMC(Block.TRAP_DOOR.id, getEMC(Block.WOOD.id) * 6 / 2);
/*  575 */     addEMC(Item.BOWL.id, getEMC(Block.WOOD.id) * 3 / 4);
/*  576 */     addEMC(Item.STICK.id, getEMC(Block.WOOD.id) / 2);
/*  577 */     addEMC(Block.FENCE.id, getEMC(Item.STICK.id) * 6 / 2);
/*  578 */     addEMC(Block.FENCE_GATE.id, getEMC(Block.WOOD.id) * 2 + getEMC(Item.STICK.id) * 4);
/*  579 */     addEMC(Item.SIGN.id, getEMC(Block.WOOD.id) * 6 + getEMC(Item.STICK.id));
/*  580 */     addEMC(Block.LADDER.id, getEMC(Item.STICK.id) * 7 / 2);
/*  581 */     addEMC(Item.FISHING_ROD.id, getEMC(Item.STICK.id) * 3 + getEMC(Item.STRING.id) * 2);
/*  582 */     addEMC(Item.WOOD_SPADE.id, getEMC(Block.WOOD.id) + getEMC(Item.STICK.id) * 2);
/*  583 */     addEMC(Item.WOOD_SWORD.id, getEMC(Block.WOOD.id) * 2 + getEMC(Item.STICK.id));
/*  584 */     addEMC(Item.WOOD_HOE.id, getEMC(Block.WOOD.id) * 2 + getEMC(Item.STICK.id) * 2);
/*  585 */     addEMC(Item.WOOD_PICKAXE.id, getEMC(Block.WOOD.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  586 */     addEMC(Item.WOOD_AXE.id, getEMC(Block.WOOD.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  587 */     addEMC(Item.BONE.id, 96);
/*      */ 
/*  589 */     for (int var0 = 0; var0 < 16; var0++)
/*      */     {
/*  591 */       if (var0 == 15)
/*      */       {
/*  593 */         addEMC(Item.INK_SACK.id, var0, getEMC(Item.BONE.id) / 3);
/*      */       }
/*  595 */       else if (var0 == 4)
/*      */       {
/*  597 */         addEMC(Item.INK_SACK.id, var0, 864);
/*      */       }
/*  599 */       else if (var0 == 3)
/*      */       {
/*  601 */         addEMC(Item.INK_SACK.id, var0, 128);
/*      */       }
/*      */       else
/*      */       {
/*  605 */         addEMC(Item.INK_SACK.id, var0, 8);
/*      */       }
/*      */     }
/*      */ 
/*  609 */     addEMC(Block.LAPIS_BLOCK.id, getEMC(Item.INK_SACK.id, 4) * 9);
/*  610 */     addEMC(BlockFlower.YELLOW_FLOWER.id, getEMC(Item.INK_SACK.id, 11) * 2);
/*  611 */     addEMC(BlockFlower.RED_ROSE.id, getEMC(Item.INK_SACK.id, 1) * 2);
/*  612 */     addEMC(EEItem.covalenceDust.id, 0, 1);
/*  613 */     addEMC(EEItem.covalenceDust.id, 1, 8);
/*  614 */     addEMC(EEItem.covalenceDust.id, 2, 208);
/*  615 */     addEMC(Block.DIRT.id, 1);
/*  616 */     addEMC(Block.SAND.id, getEMC(Block.DIRT.id));
/*  617 */     addEMC(Block.GRASS.id, getEMC(Block.DIRT.id));
/*  618 */     addEMC(Block.MYCEL.id, getEMC(Block.GRASS.id));
/*  619 */     addEMC(Block.LONG_GRASS.id, getEMC(Block.GRASS.id));
/*  620 */     addEMC(Block.DEAD_BUSH.id, getEMC(Block.LONG_GRASS.id));
/*  621 */     addEMC(Block.WATER_LILY.id, 16);
/*  622 */     addEMC(Block.VINE.id, 8);
/*  623 */     addEMC(Block.SANDSTONE.id, getEMC(Block.SAND.id) * 4);
/*  624 */     //addEMC(Block.STEP.id, 1, getEMC(Block.SANDSTONE.id) / 2);
/*  625 */     addEMC(Block.GLASS.id, getEMC(Block.SAND.id));
/*  626 */     addEMC(Item.GLASS_BOTTLE.id, getEMC(Block.GLASS.id));
/*  627 */     addEMC(Block.GRAVEL.id, getEMC(Block.SANDSTONE.id));
/*  628 */     addEMC(Item.FLINT.id, getEMC(Block.GRAVEL.id));
/*  629 */     addEMC(Block.NETHER_BRICK.id, getEMC(Block.GRAVEL.id));
/*  630 */     addEMC(Block.NETHER_FENCE.id, getEMC(Block.NETHER_BRICK.id));
/*  631 */     addEMC(Block.NETHER_BRICK_STAIRS.id, getEMC(Block.NETHER_BRICK.id) * 6 / 4);
/*  632 */     addEMC(Item.FEATHER.id, 48);
/*  633 */     addEMC(Item.ARROW.id, (getEMC(Item.STICK.id) + getEMC(Item.FLINT.id) + getEMC(Item.FEATHER.id)) / 4);
/*  634 */     addEMC(Block.COBBLESTONE.id, 1);
/*  635 */     addEMC(Block.FURNACE.id, getEMC(Block.COBBLESTONE.id) * 8);
/*  636 */     //addEMC(Block.STEP.id, 3, getEMC(Block.COBBLESTONE.id));
/*  637 */     addEMC(Block.COBBLESTONE_STAIRS.id, getEMC(Block.COBBLESTONE.id) * 6 / 4);
/*  638 */     addEMC(Block.LEVER.id, getEMC(Block.COBBLESTONE.id) + getEMC(Item.STICK.id));
/*  639 */     addEMC(Block.NETHERRACK.id, getEMC(Block.COBBLESTONE.id));
/*  640 */     addEMC(Block.STONE.id, getEMC(Block.COBBLESTONE.id));
/*  641 */     addEMC(Block.WHITESTONE.id, getEMC(Block.NETHERRACK.id));
/*  642 */     addEMC(Block.STONE_BUTTON.id, getEMC(Block.STONE.id) * 2);
/*  643 */     addEMC(Block.STONE_PLATE.id, getEMC(Block.STONE.id) * 2);
/*  644 */     //addEMC(Block.STEP.id, 0, getEMC(Block.STONE.id));
/*      */ 
/*  646 */     for (int var0 = 0; var0 < 4; var0++)
/*      */     {
/*  648 */       addEMC(Block.SMOOTH_BRICK.id, var0, getEMC(Block.STONE.id));
/*      */     }
/*      */ 
/*  651 */     //addEMC(Block.STEP.id, 5, getEMC(Block.SMOOTH_BRICK.id));
/*  652 */     addEMC(Block.STONE_STAIRS.id, getEMC(Block.SMOOTH_BRICK.id) * 6 / 4);
/*  653 */     addEMC(Item.STONE_SPADE.id, getEMC(Block.COBBLESTONE.id) + getEMC(Item.STICK.id) * 2);
/*  654 */     addEMC(Item.STONE_SWORD.id, getEMC(Block.COBBLESTONE.id) * 2 + getEMC(Item.STICK.id));
/*  655 */     addEMC(Item.STONE_HOE.id, getEMC(Block.COBBLESTONE.id) * 2 + getEMC(Item.STICK.id) * 2);
/*  656 */     addEMC(Item.STONE_PICKAXE.id, getEMC(Block.COBBLESTONE.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  657 */     addEMC(Item.STONE_AXE.id, getEMC(Block.COBBLESTONE.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  658 */     addEMC(Item.STRING.id, 12);
/*  659 */     addEMC(Item.BOW.id, getEMC(Item.STICK.id) * 3 + getEMC(Item.STRING.id) * 3);
/*  660 */     addEMC(Item.SLIME_BALL.id, 24);
/*  661 */     addEMC(Block.WEB.id, (getEMC(Item.STRING.id) * 2 + getEMC(Item.SLIME_BALL.id)) / 4);
/*  662 */     addEMC(Block.MOSSY_COBBLESTONE.id, getEMC(Block.COBBLESTONE.id) + getEMC(Item.SEEDS.id) + getEMC(Item.SLIME_BALL.id) * 6);
/*      */ 
/*  664 */     for (int var0 = 0; var0 < 16; var0++)
/*      */     {
/*  666 */       addEMC(Block.WOOL.id, var0, getEMC(Item.STRING.id) * 4);
/*      */     }
/*      */ 
/*  669 */     addEMC(Item.BED.id, getEMC(Block.WOOL.id) * 3 + getEMC(Block.WOOD.id * 3));
/*  670 */     addEMC(Item.PAINTING.id, getEMC(Block.WOOL.id) + getEMC(Item.STICK.id) * 8);
/*  671 */     addEMC(Item.SEEDS.id, 16);
/*  672 */     addEMC(Item.SUGAR_CANE.id, 32);
/*  673 */     addEMC(Item.SUGAR.id, getEMC(Item.SUGAR_CANE.id));
/*  674 */     addEMC(Item.PAPER.id, getEMC(Item.SUGAR_CANE.id));
/*  675 */     addEMC(Item.BOOK.id, getEMC(Item.PAPER.id) * 3);
/*  676 */     addEMC(Block.BOOKSHELF.id, getEMC(Item.BOOK.id) * 3 + getEMC(Block.WOOD.id) * 6);
/*  677 */     addEMC(Item.WHEAT.id, 24);
/*  678 */     addEMC(Item.BREAD.id, getEMC(Item.WHEAT.id) * 3);
/*  679 */     addEMC(Item.COOKIE.id, (getEMC(Item.WHEAT.id) * 2 + getEMC(Item.INK_SACK.id, 3)) / 8);
/*  680 */     addEMC(Block.CACTUS.id, 8);
/*  681 */     addEMC(Item.MELON.id, 16);
/*  682 */     addEMC(Item.MELON_SEEDS.id, getEMC(Item.MELON.id));
/*  683 */     addEMC(Block.MELON.id, getEMC(Item.MELON.id) * 9);
/*  684 */     addEMC(Block.PUMPKIN.id, 144);
/*  685 */     addEMC(Block.JACK_O_LANTERN.id, getEMC(Block.PUMPKIN.id) + getEMC(Block.TORCH.id));
/*  686 */     addEMC(Item.PUMPKIN_SEEDS.id, getEMC(Block.PUMPKIN.id) / 4);
/*  687 */     addEMC(BlockFlower.BROWN_MUSHROOM.id, 32);
/*  688 */     addEMC(BlockFlower.RED_MUSHROOM.id, getEMC(BlockFlower.BROWN_MUSHROOM.id));
/*  689 */     addEMC(Item.MUSHROOM_SOUP.id, getEMC(Item.BOWL.id) + getEMC(BlockFlower.BROWN_MUSHROOM.id) * 2);
/*  690 */     addEMC(Item.LEATHER.id, 64);
/*  691 */     addEMC(Item.LEATHER_BOOTS.id, getEMC(Item.LEATHER.id) * 4);
/*  692 */     addEMC(Item.LEATHER_HELMET.id, getEMC(Item.LEATHER.id) * 5);
/*  693 */     addEMC(Item.LEATHER_LEGGINGS.id, getEMC(Item.LEATHER.id) * 7);
/*  694 */     addEMC(Item.LEATHER_CHESTPLATE.id, getEMC(Item.LEATHER.id) * 8);
/*  695 */     addEMC(Item.ROTTEN_FLESH.id, 24);
/*  696 */     addEMC(Item.APPLE.id, 128);
/*  697 */     addEMC(Item.EGG.id, 32);
/*  698 */     addEMC(Item.BLAZE_ROD.id, 1536);
/*  699 */     addEMC(Item.BLAZE_POWDER.id, getEMC(Item.BLAZE_ROD.id) / 2);
/*  700 */     addEMC(Item.FIREBALL.id, (getEMC(Item.BLAZE_POWDER.id) + getEMC(Item.COAL.id) + getEMC(Item.SULPHUR.id)) / 3);
/*  701 */     addEMC(Item.MAGMA_CREAM.id, getEMC(Item.BLAZE_POWDER.id) + getEMC(Item.SLIME_BALL.id));
/*  702 */     addEMC(Block.BREWING_STAND.id, getEMC(Item.BLAZE_ROD.id) + getEMC(Block.COBBLESTONE.id) * 3);
/*  703 */     addEMC(Item.ENDER_PEARL.id, 1024);
/*  704 */     addEMC(Item.EYE_OF_ENDER.id, getEMC(Item.ENDER_PEARL.id) + getEMC(Item.BLAZE_POWDER.id));
/*  705 */     addEMC(Item.SPIDER_EYE.id, 128);
/*  706 */     addEMC(Item.FERMENTED_SPIDER_EYE.id, getEMC(Item.SPIDER_EYE.id) + getEMC(BlockFlower.BROWN_MUSHROOM.id) + getEMC(Item.SUGAR.id));
/*  707 */     addEMC(Item.NETHER_STALK.id, 24);
/*  708 */     addEMC(Item.PORK.id, 64);
/*  709 */     addEMC(Item.RAW_BEEF.id, getEMC(Item.PORK.id));
/*  710 */     addEMC(Item.RAW_CHICKEN.id, getEMC(Item.PORK.id));
/*  711 */     addEMC(Item.RAW_FISH.id, getEMC(Item.PORK.id));
/*  712 */     addEMC(Item.GRILLED_PORK.id, getEMC(Item.PORK.id));
/*  713 */     addEMC(Item.COOKED_BEEF.id, getEMC(Item.RAW_BEEF.id));
/*  714 */     addEMC(Item.COOKED_CHICKEN.id, getEMC(Item.RAW_CHICKEN.id));
/*  715 */     addEMC(Item.COOKED_FISH.id, getEMC(Item.RAW_FISH.id));
/*  716 */     addEMC(Item.CLAY_BALL.id, 16);
/*  717 */     addEMC(Block.CLAY.id, getEMC(Item.CLAY_BALL.id) * 4);
/*  718 */     addEMC(Item.CLAY_BRICK.id, getEMC(Item.CLAY_BALL.id));
/*  719 */     addEMC(Block.BRICK.id, getEMC(Item.CLAY_BRICK.id) * 4);
/*  720 */     //addEMC(Block.STEP.id, 4, getEMC(Block.BRICK.id) / 2);
/*  721 */     addEMC(Block.BRICK_STAIRS.id, getEMC(Block.BRICK.id) * 6 / 4);
/*  722 */     addEMC(Block.DISPENSER.id, getEMC(Item.BOW.id) + getEMC(Item.REDSTONE.id) + getEMC(Block.COBBLESTONE.id) * 7);
/*  723 */     addEMC(Block.NOTE_BLOCK.id, getEMC(Block.WOOD.id) * 8 + getEMC(Item.REDSTONE.id));
/*  724 */     addEMC(Block.REDSTONE_TORCH_ON.id, getEMC(Item.STICK.id) + getEMC(Item.REDSTONE.id));
/*  725 */     addEMC(Item.DIODE.id, getEMC(Block.STONE.id) * 3 + getEMC(Block.REDSTONE_TORCH_ON.id) * 2 + getEMC(Item.REDSTONE.id));
/*  726 */     addEMC(Item.SULPHUR.id, 192);
/*  727 */     addEMC(EEItem.alchemicalCoal.id, getEMC(Item.COAL.id, 0) * 4);
/*  728 */     addEMC(EEItem.mobiusFuel.id, getEMC(EEItem.alchemicalCoal.id) * 4);
/*  729 */     addEMC(EEItem.aeternalisFuel.id, getEMC(EEItem.mobiusFuel.id) * 4);
/*  730 */     addEMC(Block.TORCH.id, (getEMC(Item.COAL.id, 1) + getEMC(Item.STICK.id)) / 4);
/*  731 */     addEMC(Item.GLOWSTONE_DUST.id, getEMC(Item.REDSTONE.id) * 6);
/*  732 */     addEMC(Block.GLOWSTONE.id, getEMC(Item.GLOWSTONE_DUST.id) * 4);
/*  733 */     addEMC(Block.SOUL_SAND.id, (getEMC(Item.GLOWSTONE_DUST.id) + getEMC(Block.SAND.id) * 8) / 8);
/*  734 */     addEMC(Block.REDSTONE_LAMP_OFF.id, getEMC(Block.GLOWSTONE.id) + getEMC(Item.REDSTONE.id) * 4);
/*  735 */     addEMC(Block.TNT.id, getEMC(Item.SULPHUR.id) * 5 + getEMC(Block.SAND.id) * 4);
/*  736 */     addEMC(EEBlock.eeStone.id, 10, (getEMC(Block.TNT.id) + getEMC(EEItem.mobiusFuel.id)) / 2);
/*  737 */     addEMC(EEBlock.eeStone.id, 11, (getEMC(EEBlock.eeStone.id, 10) + getEMC(EEItem.aeternalisFuel.id)) / 2);
/*  738 */     addEMC(Item.IRON_INGOT.id, 256);
/*  739 */     addEMC(Item.FLINT_AND_STEEL.id, getEMC(Item.FLINT.id) + getEMC(Item.IRON_INGOT.id));
/*  740 */     addEMC(Item.IRON_SPADE.id, getEMC(Item.IRON_INGOT.id) + getEMC(Item.STICK.id) * 2);
/*  741 */     addEMC(Item.IRON_SWORD.id, getEMC(Item.IRON_INGOT.id) * 2 + getEMC(Item.STICK.id));
/*  742 */     addEMC(Item.IRON_HOE.id, getEMC(Item.IRON_INGOT.id) * 2 + getEMC(Item.STICK.id) * 2);
/*  743 */     addEMC(Item.IRON_PICKAXE.id, getEMC(Item.IRON_INGOT.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  744 */     addEMC(Item.IRON_AXE.id, getEMC(Item.IRON_INGOT.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  745 */     addEMC(Block.PISTON.id, getEMC(Item.REDSTONE.id) + getEMC(Item.IRON_INGOT.id) + getEMC(Block.WOOD.id) * 3 + getEMC(Block.COBBLESTONE.id) * 4);
/*  746 */     addEMC(Block.PISTON_STICKY.id, getEMC(Block.PISTON.id) + getEMC(Item.SLIME_BALL.id));
/*  747 */     addEMC(Block.RAILS.id, getEMC(Item.IRON_INGOT.id) * 6 / 16);
/*  748 */     addEMC(Block.DETECTOR_RAIL.id, getEMC(Item.IRON_INGOT.id));
/*  749 */     addEMC(Item.COMPASS.id, getEMC(Item.REDSTONE.id) + getEMC(Item.IRON_INGOT.id) * 4);
/*  750 */     addEMC(Item.MAP.id, getEMC(Item.COMPASS.id) + getEMC(Item.PAPER.id) * 8);
/*  751 */     addEMC(Block.IRON_FENCE.id, getEMC(Item.IRON_INGOT.id) * 6 / 16);
/*  752 */     addEMC(Item.IRON_BOOTS.id, getEMC(Item.IRON_INGOT.id) * 4);
/*  753 */     addEMC(Item.IRON_HELMET.id, getEMC(Item.IRON_INGOT.id) * 5);
/*  754 */     addEMC(Item.IRON_LEGGINGS.id, getEMC(Item.IRON_INGOT.id) * 7);
/*  755 */     addEMC(Item.IRON_CHESTPLATE.id, getEMC(Item.IRON_INGOT.id) * 8);
/*  756 */     addEMC(Item.IRON_DOOR.id, getEMC(Item.IRON_INGOT.id) * 6);
/*  757 */     addEMC(Block.IRON_BLOCK.id, getEMC(Item.IRON_INGOT.id) * 9);
/*  758 */     addEMC(Item.MINECART.id, getEMC(Item.IRON_INGOT.id) * 5);
/*  759 */     addEMC(Item.STORAGE_MINECART.id, getEMC(Item.MINECART.id) + getEMC(Block.CHEST.id));
/*  760 */     addEMC(Item.POWERED_MINECART.id, getEMC(Item.MINECART.id) + getEMC(Block.FURNACE.id));
/*  761 */     addEMC(Item.BUCKET.id, getEMC(Item.IRON_INGOT.id) * 3);
/*  762 */     addEMC(Block.SNOW_BLOCK.id, 1);
/*  763 */     addEMC(Item.WATER_BUCKET.id, getEMC(Item.BUCKET.id) + getEMC(Block.SNOW_BLOCK.id));
/*  764 */     addEMC(Block.ICE.id, getEMC(Block.SNOW_BLOCK.id));
/*  765 */     addEMC(Item.MILK_BUCKET.id, getEMC(Item.WATER_BUCKET.id) + getEMC(Item.SUGAR.id) + getEMC(Item.INK_SACK.id, 15));
/*  766 */     addEMC(Item.LAVA_BUCKET.id, getEMC(Item.BUCKET.id) + getEMC(Item.REDSTONE.id));
/*  767 */     addEMC(Block.OBSIDIAN.id, getEMC(Item.REDSTONE.id));
/*  768 */     addEMC(Item.CAKE.id, getEMC(Item.MILK_BUCKET.id) * 3 - getEMC(Item.BUCKET.id) * 3 + getEMC(Item.SUGAR.id) * 2 + getEMC(Item.WHEAT.id) * 3 + getEMC(Item.EGG.id));
/*  769 */     addEMC(Item.GOLD_INGOT.id, 2048);
/*  770 */     addEMC(Item.GOLD_NUGGET.id, 227);
/*  771 */     addEMC(Item.SPECKLED_MELON.id, getEMC(Item.GOLD_NUGGET.id) + getEMC(Item.MELON.id));
/*  772 */     addEMC(Item.GOLDEN_APPLE.id, getEMC(Item.APPLE.id) + getEMC(Item.GOLD_NUGGET.id) * 8);
/*  773 */     addEMC(Block.GOLDEN_RAIL.id, getEMC(Item.GOLD_INGOT.id));
/*  774 */     addEMC(Item.GOLD_SPADE.id, getEMC(Item.GOLD_INGOT.id) + getEMC(Item.STICK.id) * 2);
/*  775 */     addEMC(Item.GOLD_SWORD.id, getEMC(Item.GOLD_INGOT.id) * 2 + getEMC(Item.STICK.id));
/*  776 */     addEMC(Item.GOLD_HOE.id, getEMC(Item.GOLD_INGOT.id) * 2 + getEMC(Item.STICK.id) * 2);
/*  777 */     addEMC(Item.GOLD_PICKAXE.id, getEMC(Item.GOLD_INGOT.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  778 */     addEMC(Item.GOLD_AXE.id, getEMC(Item.GOLD_INGOT.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  779 */     addEMC(Item.WATCH.id, getEMC(Item.GOLD_INGOT.id) * 4 + getEMC(Item.REDSTONE.id));
/*  780 */     addEMC(Item.GOLD_BOOTS.id, getEMC(Item.GOLD_INGOT.id) * 4);
/*  781 */     addEMC(Item.GOLD_HELMET.id, getEMC(Item.GOLD_INGOT.id) * 5);
/*  782 */     addEMC(Item.GOLD_LEGGINGS.id, getEMC(Item.GOLD_INGOT.id) * 7);
/*  783 */     addEMC(Item.GOLD_CHESTPLATE.id, getEMC(Item.GOLD_INGOT.id) * 8);
/*  784 */     addEMC(Block.GOLD_BLOCK.id, getEMC(Item.GOLD_INGOT.id) * 9);
/*  785 */     addEMC(Item.DIAMOND.id, 8192);
/*  786 */     addEMC(Item.GHAST_TEAR.id, getEMC(Item.DIAMOND.id) / 2);
/*  787 */     addEMC(Block.JUKEBOX.id, getEMC(Item.DIAMOND.id) + getEMC(Block.WOOD.id) * 8);
/*  788 */     addEMC(Block.ENCHANTMENT_TABLE.id, getEMC(Item.DIAMOND.id) * 2 + getEMC(Block.OBSIDIAN.id) * 4 + getEMC(Item.BOOK.id));
/*  789 */     addEMC(Item.DIAMOND_SPADE.id, getEMC(Item.DIAMOND.id) + getEMC(Item.STICK.id) * 2);
/*  790 */     addEMC(Item.DIAMOND_SWORD.id, getEMC(Item.DIAMOND.id) * 2 + getEMC(Item.STICK.id));
/*  791 */     addEMC(Item.DIAMOND_HOE.id, getEMC(Item.DIAMOND.id) * 2 + getEMC(Item.STICK.id) * 2);
/*  792 */     addEMC(Item.DIAMOND_PICKAXE.id, getEMC(Item.DIAMOND.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  793 */     addEMC(Item.DIAMOND_AXE.id, getEMC(Item.DIAMOND.id) * 3 + getEMC(Item.STICK.id) * 2);
/*  794 */     addEMC(Item.DIAMOND_BOOTS.id, getEMC(Item.DIAMOND.id) * 4);
/*  795 */     addEMC(Item.DIAMOND_HELMET.id, getEMC(Item.DIAMOND.id) * 5);
/*  796 */     addEMC(Item.DIAMOND_LEGGINGS.id, getEMC(Item.DIAMOND.id) * 7);
/*  797 */     addEMC(Item.DIAMOND_CHESTPLATE.id, getEMC(Item.DIAMOND.id) * 8);
/*  798 */     addEMC(Block.DIAMOND_BLOCK.id, getEMC(Item.DIAMOND.id) * 9);
/*  799 */     addEMC(EEItem.kleinStar1.id, getEMC(Item.DIAMOND.id) + getEMC(EEItem.mobiusFuel.id) * 8);
/*  800 */     addEMC(EEItem.kleinStar2.id, getEMC(EEItem.kleinStar1.id) * 4);
/*  801 */     addEMC(EEItem.kleinStar3.id, getEMC(EEItem.kleinStar2.id) * 4);
/*  802 */     addEMC(EEItem.kleinStar4.id, getEMC(EEItem.kleinStar3.id) * 4);
/*  803 */     addEMC(EEItem.kleinStar5.id, getEMC(EEItem.kleinStar4.id) * 4);
/*  804 */     addEMC(EEItem.kleinStar6.id, getEMC(EEItem.kleinStar5.id) * 4);
/*  805 */     addEMC(EEItem.darkMatter.id, getEMC(Block.DIAMOND_BLOCK.id) + getEMC(EEItem.aeternalisFuel.id) * 8);
/*  806 */     addEMC(EEItem.redMatter.id, getEMC(EEItem.darkMatter.id) * 3 + getEMC(EEItem.aeternalisFuel.id) * 6);
/*  807 */     addEMC(Item.SHEARS.id, getEMC(Item.IRON_INGOT.id) * 2);
/*  808 */     addEMC(Item.SADDLE.id, getEMC(Item.LEATHER.id) * 3);
/*  809 */     addEMC(Block.DRAGON_EGG.id, getEMC(EEItem.darkMatter.id));
/*  810 */     addEMC(Item.RECORD_11.id, getEMC(Item.GOLD_INGOT.id));
/*  811 */     addEMC(Item.RECORD_1.id, getEMC(Item.RECORD_11.id));
/*  812 */     addEMC(Item.RECORD_3.id, getEMC(Item.RECORD_11.id));
/*  813 */     addEMC(Item.RECORD_2.id, getEMC(Item.RECORD_11.id));
/*  814 */     addEMC(Item.RECORD_4.id, getEMC(Item.RECORD_11.id));
/*  815 */     addEMC(Item.RECORD_5.id, getEMC(Item.RECORD_11.id));
/*  816 */     addEMC(Item.RECORD_6.id, getEMC(Item.RECORD_11.id));
/*  817 */     addEMC(Item.RECORD_7.id, getEMC(Item.RECORD_11.id));
/*  818 */     addEMC(Item.RECORD_8.id, getEMC(Item.RECORD_11.id));
/*  819 */     addEMC(Item.RECORD_9.id, getEMC(Item.RECORD_11.id));
/*  820 */     addEMC(Item.RECORD_10.id, getEMC(Item.RECORD_11.id));
/*  821 */     InitModBlockValues();
/*      */   }
/*      */ 
/*      */   public static void InitModBlockValues()
/*      */   {
/*  826 */     addEMC(EEBlock.eeStone.id, 8, getEMC(EEItem.darkMatter.id));
/*  827 */     addEMC(EEBlock.eeStone.id, 9, getEMC(EEItem.redMatter.id));
/*      */ 
/*  829 */     if (EEBase.props.getInt("AllowCollectors") == 1)
/*      */     {
/*  831 */       addEMC(EEBlock.eeStone.id, 0, getEMC(Block.GLOWSTONE.id) * 6 + getEMC(Block.GLASS.id) + getEMC(Block.DIAMOND_BLOCK.id) + getEMC(Block.FURNACE.id));
/*  832 */       addEMC(EEBlock.eeStone.id, 1, getEMC(Block.GLOWSTONE.id) * 7 + getEMC(EEBlock.eeStone.id, 0) + getEMC(EEItem.darkMatter.id));
/*  833 */       addEMC(EEBlock.eeStone.id, 2, getEMC(Block.GLOWSTONE.id) * 7 + getEMC(EEBlock.eeStone.id, 1) + getEMC(EEItem.redMatter.id));
/*      */     }
/*      */ 
/*  836 */     if (EEBase.props.getInt("AllowFurnaces") == 1)
/*      */     {
/*  838 */       addEMC(EEBlock.eeStone.id, 3, getEMC(Block.FURNACE.id) + getEMC(EEBlock.eeStone.id, 8) * 8);
/*  839 */       addEMC(EEBlock.eeStone.id, 4, getEMC(EEBlock.eeStone.id, 3) + getEMC(EEBlock.eeStone.id, 9) * 3);
/*      */     }
/*      */ 
/*  842 */     if (EEBase.props.getInt("AllowRelays") == 1)
/*      */     {
/*  844 */       addEMC(EEBlock.eeStone.id, 5, getEMC(Block.OBSIDIAN.id) * 7 + getEMC(Block.DIAMOND_BLOCK.id) + getEMC(Block.GLASS.id));
/*  845 */       addEMC(EEBlock.eeStone.id, 6, getEMC(Block.OBSIDIAN.id) * 7 + getEMC(EEBlock.eeStone.id, 5) + getEMC(EEItem.darkMatter.id));
/*  846 */       addEMC(EEBlock.eeStone.id, 7, getEMC(Block.OBSIDIAN.id) * 7 + getEMC(EEBlock.eeStone.id, 6) + getEMC(EEItem.redMatter.id));
/*      */     }
/*      */ 
/*  849 */     if (EEBase.props.getInt("AllowInterdiction") == 1)
/*      */     {
/*  851 */       addEMC(EEBlock.eeTorch.id, 0, (getEMC(Block.REDSTONE_TORCH_ON.id) * 2 + getEMC(Item.DIAMOND.id) * 3 + getEMC(Item.GLOWSTONE_DUST.id) * 3) / 2);
/*      */     }
/*      */ 
/*  854 */     if (EEBase.props.getInt("AllowPedestals") == 1)
/*      */     {
/*  856 */       addEMC(EEBlock.eePedestal.id, 0, getEMC(EEBlock.eeStone.id, 8) * 5 + getEMC(EEItem.redMatter.id) * 4);
/*      */     }
/*      */ 
/*  859 */     if (EEBase.props.getInt("AllowChests") == 1)
/*      */     {
/*  861 */       addEMC(EEBlock.eeChest.id, 0, getEMC(Block.CHEST.id) + getEMC(Item.IRON_INGOT.id) * 2 + getEMC(Item.DIAMOND.id) + getEMC(Block.STONE.id) * 2 + getEMC(EEItem.covalenceDust.id, 0) + getEMC(EEItem.covalenceDust.id, 1) + getEMC(EEItem.covalenceDust.id, 2));
/*      */     }
/*      */ 
/*  864 */     if (EEBase.props.getInt("AllowCondensers") == 1)
/*      */     {
/*  866 */       addEMC(EEBlock.eeChest.id, 1, getEMC(EEBlock.eeChest.id, 0) + getEMC(Block.OBSIDIAN.id) * 4 + getEMC(Item.DIAMOND.id) * 4);
/*      */     }
/*      */ 
/*  869 */     addEMC(EEBlock.eeDevice.id, 0, getEMC(Block.OBSIDIAN.id) * 4 + getEMC(Block.STONE.id) * 4);
/*      */   }
/*      */ 
/*      */   public static void InitChestItems()
/*      */   {
/*  874 */     addChestItem(EEItem.repairCharm.id);
/*  875 */     addChestItem(EEBlock.eeTorch.id, 0);
/*  876 */     addChestItem(EEItem.eternalDensity.id);
/*      */   }
/*      */ 
/*      */   public static void InitBlacklist() {
/*      */   }
/*      */ 
/*      */   public static void InitChargeditems() {
/*  883 */     addChargedItem(EEItem.philStone.id);
/*  884 */     addChargedItem(EEItem.catalystStone.id);
/*  885 */     addChargedItem(EEItem.evertide.id);
/*  886 */     addChargedItem(EEItem.volcanite.id);
/*  887 */     addChargedItem(EEItem.ignitionRing.id);
/*  888 */     addChargedItem(EEItem.zeroRing.id);
/*  889 */     addChargedItem(EEItem.arcaneRing.id);
/*  890 */     addChargedItem(EEItem.grimarchRing.id);
/*  891 */     addChargedItem(EEItem.hyperkineticLens.id);
/*  892 */     addChargedItem(EEItem.watchOfTime.id);
/*  893 */     addChargedItem(EEItem.darkPickaxe.id);
/*  894 */     addChargedItem(EEItem.darkAxe.id);
/*  895 */     addChargedItem(EEItem.darkSpade.id);
/*  896 */     addChargedItem(EEItem.darkHoe.id);
/*  897 */     addChargedItem(EEItem.darkSword.id);
/*  898 */     addChargedItem(EEItem.darkShears.id);
/*  899 */     addChargedItem(EEItem.darkHammer.id);
/*  900 */     addChargedItem(EEItem.kleinStar1.id);
/*  901 */     addChargedItem(EEItem.kleinStar2.id);
/*  902 */     addChargedItem(EEItem.kleinStar3.id);
/*  903 */     addChargedItem(EEItem.kleinStar4.id);
/*  904 */     addChargedItem(EEItem.kleinStar5.id);
/*  905 */     addChargedItem(EEItem.kleinStar6.id);
/*  906 */     addChargedItem(EEItem.hyperCatalyst.id);
/*  907 */     addChargedItem(EEItem.redPickaxe.id);
/*  908 */     addChargedItem(EEItem.redAxe.id);
/*  909 */     addChargedItem(EEItem.redSpade.id);
/*  910 */     addChargedItem(EEItem.redHoe.id);
/*  911 */     addChargedItem(EEItem.redSword.id);
/*  912 */     addChargedItem(EEItem.redShears.id);
/*  913 */     addChargedItem(EEItem.redHammer.id);
/*  914 */     addChargedItem(EEItem.redKatar.id);
/*  915 */     addChargedItem(EEItem.redMace.id);
/*  916 */     addChargedItem(EEItem.mercurialEye.id);
/*      */   }
/*      */ 
/*      */   public static void InitFuelItems()
/*      */   {
/*  921 */     addFuelItem(Item.REDSTONE.id);
/*  922 */     addFuelItem(Item.BLAZE_POWDER.id);
/*  923 */     addFuelItem(Item.COAL.id);
/*  924 */     addFuelItem(Item.COAL.id, 1);
/*  925 */     addFuelItem(Item.BLAZE_ROD.id);
/*  926 */     addFuelItem(Block.GLOWSTONE.id);
/*  927 */     addFuelItem(EEItem.alchemicalCoal.id);
/*  928 */     addFuelItem(Item.GLOWSTONE_DUST.id);
/*  929 */     addFuelItem(Item.SULPHUR.id);
/*  930 */     addFuelItem(EEItem.mobiusFuel.id);
/*  931 */     addFuelItem(EEItem.aeternalisFuel.id);
/*      */   }
/*      */ 
/*      */   public static void InitWoodAndLeafBlocks()
/*      */   {
/*  936 */     addLeafBlock(Block.LEAVES.id);
/*  937 */     addWoodBlock(Block.LOG.id);
/*      */   }
/*      */ 
/*      */   public static void InitPedestalItems()
/*      */   {
/*  942 */     addPedestalItem(EEItem.evertide.id);
/*  943 */     addPedestalItem(EEItem.volcanite.id);
/*  944 */     addPedestalItem(EEItem.soulStone.id);
/*  945 */     addPedestalItem(EEItem.ignitionRing.id);
/*  946 */     addPedestalItem(EEItem.zeroRing.id);
/*  947 */     addPedestalItem(EEItem.grimarchRing.id);
/*  948 */     addPedestalItem(EEItem.swiftWolfRing.id);
/*  949 */     addPedestalItem(EEItem.harvestRing.id);
/*  950 */     addPedestalItem(EEItem.watchOfTime.id);
/*  951 */     addPedestalItem(EEItem.repairCharm.id);
/*  952 */     addPedestalItem(EEItem.attractionRing.id);
/*  953 */     addPedestalItem(EEBlock.eeTorch.id, 0);
/*      */   }
/*      */ 
/*      */   public static void InitMetaData()
/*      */   {
/*  958 */     addMeta(Item.COAL.id, 1);
/*  959 */     addMeta(Block.LOG.id, 2);
/*  960 */     addMeta(Block.LOG.id, 3);
/*  961 */     addMeta(Block.WOOD.id, 1);
/*  962 */     addMeta(Block.WOOD.id, 2);
/*  963 */     addMeta(Block.WOOD.id, 3);
/*  964 */     addMeta(Block.SANDSTONE.id, 1);
/*  965 */     addMeta(Block.SANDSTONE.id, 2);
/*  966 */     addMeta(Block.SAPLING.id, 2);
/*  967 */     addMeta(Block.SAPLING.id, 3);
/*  968 */     addMeta(Block.LEAVES.id, 2);
/*  969 */     addMeta(Block.LEAVES.id, 3);
/*  970 */     addMeta(Block.LONG_GRASS.id, 1);
/*  971 */     addMeta(Block.LONG_GRASS.id, 2);
/*  972 */     addMeta(Block.SMOOTH_BRICK.id, 2);
/*  973 */     addMeta(Block.STEP.id, 5);
/*  974 */     addMeta(Block.WOOL.id, 15);
/*  975 */     addMeta(Item.INK_SACK.id, 15);
/*  976 */     addMeta(EEBlock.eeStone.id, 11);
/*  977 */     addMeta(EEBlock.eeChest.id, 1);
/*  978 */     addMeta(EEItem.covalenceDust.id, 2);
/*      */   }
/*      */ 
/*      */   public static void InitModItems()
/*      */   {
/*  983 */     addModItem(EEItem.covalenceDust.id);
/*  984 */     addModItem(EEItem.philStone.id);
/*  985 */     addModItem(EEItem.catalystStone.id);
/*  986 */     addModItem(EEItem.baseRing.id);
/*  987 */     addModItem(EEItem.evertide.id);
/*  988 */     addModItem(EEItem.volcanite.id);
/*  989 */     addModItem(EEItem.soulStone.id);
/*  990 */     addModItem(EEItem.attractionRing.id);
/*  991 */     addModItem(EEItem.swiftWolfRing.id);
/*  992 */     addModItem(EEItem.ignitionRing.id);
/*  993 */     addModItem(EEItem.zeroRing.id);
/*  994 */     addModItem(EEItem.arcaneRing.id);
/*  995 */     addModItem(EEItem.grimarchRing.id);
/*  996 */     addModItem(EEItem.hyperkineticLens.id);
/*  997 */     addModItem(EEItem.harvestRing.id);
/*  998 */     addModItem(EEItem.watchOfTime.id);
/*  999 */     addModItem(EEItem.eternalDensity.id);
/* 1000 */     addModItem(EEItem.repairCharm.id);
/* 1001 */     addModItem(EEItem.alchemicalCoal.id);
/* 1002 */     addModItem(EEItem.mobiusFuel.id);
/* 1003 */     addModItem(EEItem.darkMatter.id);
/* 1004 */     addModItem(EEItem.darkPickaxe.id);
/* 1005 */     addModItem(EEItem.darkSpade.id);
/* 1006 */     addModItem(EEItem.darkHoe.id);
/* 1007 */     addModItem(EEItem.darkSword.id);
/* 1008 */     addModItem(EEItem.darkShears.id);
/* 1009 */     addModItem(EEItem.darkHammer.id);
/* 1010 */     addModItem(EEItem.kleinStar1.id);
/* 1011 */     addModItem(EEItem.kleinStar2.id);
/* 1012 */     addModItem(EEItem.kleinStar3.id);
/* 1013 */     addModItem(EEItem.kleinStar4.id);
/* 1014 */     addModItem(EEItem.kleinStar5.id);
/* 1015 */     addModItem(EEItem.kleinStar6.id);
/* 1016 */     addModItem(EEItem.alchemyBag.id);
/* 1017 */     addModItem(EEItem.redMatter.id);
/* 1018 */     addModItem(EEItem.hyperCatalyst.id);
/* 1019 */     addModItem(EEItem.redPickaxe.id);
/* 1020 */     addModItem(EEItem.redSpade.id);
/* 1021 */     addModItem(EEItem.redHoe.id);
/* 1022 */     addModItem(EEItem.redSword.id);
/* 1023 */     addModItem(EEItem.redShears.id);
/* 1024 */     addModItem(EEItem.redHammer.id);
/* 1025 */     addModItem(EEItem.redKatar.id);
/* 1026 */     addModItem(EEItem.redMace.id);
/*      */   }
/*      */ 
/*      */   public static void InitOreBlocks()
/*      */   {
/* 1031 */     addOreBlock(Block.COAL_ORE.id);
/* 1032 */     addOreBlock(Block.DIAMOND_ORE.id);
/* 1033 */     addOreBlock(Block.GOLD_ORE.id);
/* 1034 */     addOreBlock(Block.IRON_ORE.id);
/* 1035 */     addOreBlock(Block.LAPIS_ORE.id);
/* 1036 */     addOreBlock(Block.REDSTONE_ORE.id);
/* 1037 */     addOreBlock(Block.GLOWING_REDSTONE_ORE.id);
/*      */   }
/*      */ 
/*      */   public static void InitDurationEffectItems()
/*      */   {
/* 1042 */     addDurationEffectItem(EEItem.harvestRing.id);
/* 1043 */     addDurationEffectItem(EEItem.ignitionRing.id);
/* 1044 */     addDurationEffectItem(EEItem.swiftWolfRing.id);
/* 1045 */     addDurationEffectItem(EEItem.watchOfTime.id);
/* 1046 */     addDurationEffectItem(EEItem.grimarchRing.id);
/* 1047 */     addDurationEffectItem(EEItem.attractionRing.id);
/* 1048 */     addDurationEffectItem(EEItem.eternalDensity.id);
/* 1049 */     addDurationEffectItem(EEItem.arcaneRing.id);
/* 1050 */     addDurationEffectItem(EEItem.zeroRing.id);
/* 1051 */     addDurationEffectItem(EEItem.soulStone.id);
/* 1052 */     addDurationEffectItem(EEItem.bodyStone.id);
/* 1053 */     addDurationEffectItem(EEItem.lifeStone.id);
/* 1054 */     addDurationEffectItem(EEItem.mindStone.id);
/*      */   }
/*      */ 
/*      */   public static void InitFlyingItems()
/*      */   {
/* 1059 */     addFlyingItem(EEItem.volcanite.id);
/* 1060 */     addFlyingItem(EEItem.evertide.id);
/* 1061 */     addFlyingItem(EEItem.swiftWolfRing.id);
/* 1062 */     addFlyingItem(EEItem.arcaneRing.id);
/*      */   }
/*      */ 
/*      */   public static void InitFireImmunities()
/*      */   {
/* 1067 */     addFireImmuneItem(EEItem.volcanite.id);
/* 1068 */     addFireImmuneItem(EEItem.ignitionRing.id);
/* 1069 */     addFireImmuneItem(EEItem.arcaneRing.id);
/* 1070 */     addFireImmuneArmor(EEItem.redMatterArmorP.id);
/*      */   }
/*      */ 
/*      */   public static void InitRepairRecipes()
/*      */   {
/* 1075 */     ModLoader.addShapelessRecipe(coval(40, 0), new Object[] { cobble(1), cobble(1), cobble(1), cobble(1), cobble(1), cobble(1), cobble(1), cobble(1), coal(1, 1) });
/* 1076 */     ModLoader.addShapelessRecipe(coval(40, 1), new Object[] { iing(1), redstone(1) });
/* 1077 */     ModLoader.addShapelessRecipe(coval(40, 2), new Object[] { diamond(1), coal(1, 0) });
/*      */ 
/* 1079 */     if (EEBase.props.getInt("AllowRepair") == 1)
/*      */     {
/* 1081 */       AddRepairRecipe(new ItemStack(Item.LEATHER_CHESTPLATE, 1, -1), multiStack(lcov(), 8));
/* 1082 */       AddRepairRecipe(new ItemStack(Item.LEATHER_BOOTS, 1, -1), multiStack(lcov(), 4));
/* 1083 */       AddRepairRecipe(new ItemStack(Item.LEATHER_LEGGINGS, 1, -1), multiStack(lcov(), 7));
/* 1084 */       AddRepairRecipe(new ItemStack(Item.LEATHER_HELMET, 1, -1), multiStack(lcov(), 5));
/* 1085 */       AddRepairRecipe(new ItemStack(Item.FISHING_ROD, 1, -1), multiStack(lcov(), 1));
/* 1086 */       AddRepairRecipe(new ItemStack(Item.BOW, 1, -1), multiStack(lcov(), 3));
/* 1087 */       AddRepairRecipe(new ItemStack(Item.WOOD_SPADE, 1, -1), multiStack(lcov(), 1));
/* 1088 */       AddRepairRecipe(new ItemStack(Item.WOOD_SWORD, 1, -1), multiStack(lcov(), 1));
/* 1089 */       AddRepairRecipe(new ItemStack(Item.WOOD_HOE, 1, -1), multiStack(lcov(), 1));
/* 1090 */       AddRepairRecipe(new ItemStack(Item.WOOD_AXE, 1, -1), multiStack(lcov(), 1));
/* 1091 */       AddRepairRecipe(new ItemStack(Item.WOOD_PICKAXE, 1, -1), multiStack(lcov(), 1));
/* 1092 */       AddRepairRecipe(new ItemStack(Item.CHAINMAIL_CHESTPLATE, 1, -1), multiStack(lcov(), 8));
/* 1093 */       AddRepairRecipe(new ItemStack(Item.CHAINMAIL_BOOTS, 1, -1), multiStack(lcov(), 4));
/* 1094 */       AddRepairRecipe(new ItemStack(Item.CHAINMAIL_LEGGINGS, 1, -1), multiStack(lcov(), 7));
/* 1095 */       AddRepairRecipe(new ItemStack(Item.CHAINMAIL_HELMET, 1, -1), multiStack(lcov(), 5));
/* 1096 */       AddRepairRecipe(new ItemStack(Item.STONE_SPADE, 1, -1), multiStack(lcov(), 1));
/* 1097 */       AddRepairRecipe(new ItemStack(Item.STONE_SWORD, 1, -1), multiStack(lcov(), 2));
/* 1098 */       AddRepairRecipe(new ItemStack(Item.STONE_HOE, 1, -1), multiStack(lcov(), 2));
/* 1099 */       AddRepairRecipe(new ItemStack(Item.STONE_AXE, 1, -1), multiStack(lcov(), 3));
/* 1100 */       AddRepairRecipe(new ItemStack(Item.STONE_PICKAXE, 1, -1), multiStack(lcov(), 3));
/* 1101 */       AddRepairRecipe(new ItemStack(Item.IRON_CHESTPLATE, 1, -1), multiStack(mcov(), 8));
/* 1102 */       AddRepairRecipe(new ItemStack(Item.IRON_BOOTS, 1, -1), multiStack(mcov(), 4));
/* 1103 */       AddRepairRecipe(new ItemStack(Item.IRON_LEGGINGS, 1, -1), multiStack(mcov(), 7));
/* 1104 */       AddRepairRecipe(new ItemStack(Item.IRON_HELMET, 1, -1), multiStack(mcov(), 5));
/* 1105 */       AddRepairRecipe(new ItemStack(Item.SHEARS, 1, -1), multiStack(mcov(), 1));
/* 1106 */       AddRepairRecipe(new ItemStack(Item.FLINT_AND_STEEL, 1, -1), multiStack(mcov(), 1));
/* 1107 */       AddRepairRecipe(new ItemStack(Item.IRON_SPADE, 1, -1), multiStack(mcov(), 1));
/* 1108 */       AddRepairRecipe(new ItemStack(Item.IRON_SWORD, 1, -1), multiStack(mcov(), 2));
/* 1109 */       AddRepairRecipe(new ItemStack(Item.IRON_HOE, 1, -1), multiStack(mcov(), 2));
/* 1110 */       AddRepairRecipe(new ItemStack(Item.IRON_AXE, 1, -1), multiStack(mcov(), 3));
/* 1111 */       AddRepairRecipe(new ItemStack(Item.IRON_PICKAXE, 1, -1), multiStack(mcov(), 3));
/* 1112 */       AddRepairRecipe(new ItemStack(Item.GOLD_SPADE, 1, -1), multiStack(mcov(), 1));
/* 1113 */       AddRepairRecipe(new ItemStack(Item.GOLD_SWORD, 1, -1), multiStack(mcov(), 1));
/* 1114 */       AddRepairRecipe(new ItemStack(Item.GOLD_HOE, 1, -1), multiStack(mcov(), 1));
/* 1115 */       AddRepairRecipe(new ItemStack(Item.GOLD_AXE, 1, -1), multiStack(mcov(), 1));
/* 1116 */       AddRepairRecipe(new ItemStack(Item.GOLD_PICKAXE, 1, -1), multiStack(mcov(), 1));
/* 1117 */       AddRepairRecipe(new ItemStack(Item.GOLD_CHESTPLATE, 1, -1), multiStack(mcov(), 8));
/* 1118 */       AddRepairRecipe(new ItemStack(Item.GOLD_BOOTS, 1, -1), multiStack(mcov(), 4));
/* 1119 */       AddRepairRecipe(new ItemStack(Item.GOLD_LEGGINGS, 1, -1), multiStack(mcov(), 7));
/* 1120 */       AddRepairRecipe(new ItemStack(Item.GOLD_HELMET, 1, -1), multiStack(mcov(), 5));
/* 1121 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_SPADE, 1, -1), multiStack(hcov(), 1));
/* 1122 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_SWORD, 1, -1), multiStack(hcov(), 2));
/* 1123 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_HOE, 1, -1), multiStack(hcov(), 2));
/* 1124 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_AXE, 1, -1), multiStack(hcov(), 3));
/* 1125 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_PICKAXE, 1, -1), multiStack(hcov(), 3));
/* 1126 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_CHESTPLATE, 1, -1), multiStack(hcov(), 8));
/* 1127 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_BOOTS, 1, -1), multiStack(hcov(), 4));
/* 1128 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_LEGGINGS, 1, -1), multiStack(hcov(), 7));
/* 1129 */       AddRepairRecipe(new ItemStack(Item.DIAMOND_HELMET, 1, -1), multiStack(hcov(), 5));
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void InitEERecipes()
/*      */   {
/* 1135 */     debugRecipes();
/* 1136 */     ModLoader.addShapelessRecipe(new ItemStack(EEItem.mobiusFuel, 4), new Object[] { pstone(), EEItem.aeternalisFuel });
/* 1137 */     ModLoader.addShapelessRecipe(new ItemStack(EEItem.aeternalisFuel), new Object[] { pstone(), EEItem.mobiusFuel, EEItem.mobiusFuel, EEItem.mobiusFuel, EEItem.mobiusFuel });
/* 1138 */     ModLoader.addShapelessRecipe(new ItemStack(EEItem.alchemicalCoal, 4), new Object[] { pstone(), EEItem.mobiusFuel });
/* 1139 */     ModLoader.addShapelessRecipe(new ItemStack(EEItem.mobiusFuel), new Object[] { pstone(), EEItem.alchemicalCoal, EEItem.alchemicalCoal, EEItem.alchemicalCoal, EEItem.alchemicalCoal });
/* 1140 */     ModLoader.addShapelessRecipe(new ItemStack(Item.COAL, 4, 0), new Object[] { pstone(), EEItem.alchemicalCoal });
/* 1141 */     ModLoader.addShapelessRecipe(new ItemStack(EEItem.alchemicalCoal), new Object[] { pstone(), new ItemStack(Item.COAL, 1, 0), new ItemStack(Item.COAL, 1, 0), new ItemStack(Item.COAL, 1, 0), new ItemStack(Item.COAL, 1, 0) });
/* 1142 */     ModLoader.addShapelessRecipe(new ItemStack(Item.COAL, 4, 1), new Object[] { pstone(), new ItemStack(Item.COAL, 1, 0) });
/* 1143 */     ModLoader.addShapelessRecipe(new ItemStack(Item.COAL, 1, 0), new Object[] { pstone(), new ItemStack(Item.COAL, 1, 1), new ItemStack(Item.COAL, 1, 1), new ItemStack(Item.COAL, 1, 1), new ItemStack(Item.COAL, 1, 1) });
/* 1144 */     ModLoader.addShapelessRecipe(new ItemStack(Item.DIAMOND, 1), new Object[] { pstone(), Item.GOLD_INGOT, Item.GOLD_INGOT, Item.GOLD_INGOT, Item.GOLD_INGOT });
/* 1145 */     ModLoader.addShapelessRecipe(new ItemStack(Item.GOLD_INGOT, 4), new Object[] { pstone(), Item.DIAMOND });
/* 1146 */     ModLoader.addShapelessRecipe(new ItemStack(Item.GOLD_INGOT, 1), new Object[] { pstone(), Item.IRON_INGOT, Item.IRON_INGOT, Item.IRON_INGOT, Item.IRON_INGOT, Item.IRON_INGOT, Item.IRON_INGOT, Item.IRON_INGOT, Item.IRON_INGOT });
/* 1147 */     ModLoader.addShapelessRecipe(new ItemStack(Item.IRON_INGOT, 8), new Object[] { pstone(), Item.GOLD_INGOT });
/* 1148 */     //ModLoader.addShapelessRecipe(new ItemStack(Block.ICE), new Object[] { new ItemStack(EEItem.zeroRing, 1, -1), Item.WATER_BUCKET });
/* 1149 */     //ModLoader.addShapelessRecipe(new ItemStack(Block.ICE), new Object[] { new ItemStack(EEItem.arcaneRing, 1, -1), Item.WATER_BUCKET });
/* 1150 */     ModLoader.addShapelessRecipe(new ItemStack(Block.GRASS), new Object[] { new ItemStack(EEItem.harvestRing, 1, -1), Block.DIRT });
/* 1151 */     ModLoader.addShapelessRecipe(new ItemStack(Block.GRASS), new Object[] { new ItemStack(EEItem.arcaneRing, 1, -1), Block.DIRT });
/* 1152 */     //ModLoader.addShapelessRecipe(new ItemStack(Item.WATER_BUCKET), new Object[] { new ItemStack(EEItem.evertide, 1, -1), Item.BUCKET });
/* 1153 */     ModLoader.addShapelessRecipe(new ItemStack(Item.LAVA_BUCKET), new Object[] { new ItemStack(EEItem.volcanite, 1, -1), Item.BUCKET, Item.REDSTONE });
/*      */ 	   
/* 1155 */     if (EEBase.props.getInt("AllowPedestals") == 1)
/*      */     {
/* 1157 */       ModLoader.addRecipe(EEBlock.pedestal, new Object[] { "R#R", "R#R", "###", Character.valueOf('R'), EEItem.redMatter, Character.valueOf('#'), EEBlock.dmBlock });
/*      */     }
/*      */ 
/* 1160 */     ModLoader.addRecipe(new ItemStack(EEItem.philStone), new Object[] { "LRL", "RXR", "LRL", Character.valueOf('R'), Item.REDSTONE, Character.valueOf('L'), Item.GLOWSTONE_DUST, Character.valueOf('X'), Item.DIAMOND });
/* 1161 */     ModLoader.addRecipe(new ItemStack(EEItem.philStone), new Object[] { "RLR", "LXL", "RLR", Character.valueOf('R'), Item.REDSTONE, Character.valueOf('L'), Item.GLOWSTONE_DUST, Character.valueOf('X'), Item.DIAMOND });
/*      */ 
/* 1163 */     if (EEBase.props.getInt("AllowTransmutationTable") == 1)
/*      */     {
/* 1165 */       ModLoader.addRecipe(new ItemStack(EEBlock.eeDevice, 1, 0), new Object[] { "DSD", "SPS", "DSD", Character.valueOf('D'), Block.OBSIDIAN, Character.valueOf('S'), Block.STONE, Character.valueOf('P'), pstone() });
/* 1166 */       ModLoader.addRecipe(new ItemStack(EEItem.transTablet), new Object[] { "DSD", "SPS", "DSD", Character.valueOf('D'), EEBlock.dmBlock, Character.valueOf('S'), Block.STONE, Character.valueOf('P'), new ItemStack(EEBlock.eeDevice, 1, 0) });
/*      */     }
/*      */ 
/* 1169 */     ModLoader.addRecipe(new ItemStack(EEItem.darkMatter), new Object[] { "FFF", "FDF", "FFF", Character.valueOf('D'), Block.DIAMOND_BLOCK, Character.valueOf('F'), EEItem.aeternalisFuel });
/* 1170 */     ModLoader.addRecipe(new ItemStack(EEBlock.eeStone, 4, 8), new Object[] { "DD", "DD", Character.valueOf('D'), EEItem.darkMatter });
/* 1171 */     ModLoader.addRecipe(new ItemStack(EEItem.redMatter), new Object[] { "FFF", "DDD", "FFF", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('F'), EEItem.aeternalisFuel });
/* 1172 */     ModLoader.addRecipe(new ItemStack(EEBlock.eeStone, 4, 9), new Object[] { "DD", "DD", Character.valueOf('D'), EEItem.redMatter });
/*      */ 
/* 1174 */     if (EEBase.props.getInt("AllowDMTools") == 1)
/*      */     {
/* 1176 */       ModLoader.addRecipe(new ItemStack(EEItem.darkPickaxe), new Object[] { "###", " D ", " D ", Character.valueOf('#'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND });
/* 1177 */       ModLoader.addRecipe(new ItemStack(EEItem.darkSpade), new Object[] { " # ", " D ", " D ", Character.valueOf('#'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND });
/* 1178 */       ModLoader.addRecipe(new ItemStack(EEItem.darkHoe), new Object[] { "## ", " D ", " D ", Character.valueOf('#'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND });
/* 1179 */       ModLoader.addRecipe(new ItemStack(EEItem.darkSword), new Object[] { " # ", " # ", " D ", Character.valueOf('#'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND });
/* 1180 */       ModLoader.addRecipe(new ItemStack(EEItem.darkAxe), new Object[] { "## ", "#D ", " D ", Character.valueOf('#'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND });
/* 1181 */       ModLoader.addRecipe(new ItemStack(EEItem.darkShears), new Object[] { " # ", "D  ", Character.valueOf('#'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND });
/* 1182 */       ModLoader.addRecipe(new ItemStack(EEItem.darkHammer), new Object[] { "#D#", " D ", " D ", Character.valueOf('#'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND });
/*      */     }
/*      */ 
/* 1185 */     if (EEBase.props.getInt("AllowRMTools") == 1)
/*      */     {
/* 1187 */       ModLoader.addRecipe(new ItemStack(EEItem.redPickaxe), new Object[] { "###", " T ", " D ", Character.valueOf('#'), EEItem.redMatter, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('T'), new ItemStack(EEItem.darkPickaxe, 1, -1) });
/* 1188 */       ModLoader.addRecipe(new ItemStack(EEItem.redSpade), new Object[] { " # ", " T ", " D ", Character.valueOf('#'), EEItem.redMatter, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('T'), new ItemStack(EEItem.darkSpade, 1, -1) });
/* 1189 */       ModLoader.addRecipe(new ItemStack(EEItem.redHoe), new Object[] { "## ", " T ", " D ", Character.valueOf('#'), EEItem.redMatter, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('T'), new ItemStack(EEItem.darkHoe, 1, -1) });
/* 1190 */       ModLoader.addRecipe(new ItemStack(EEItem.redSword), new Object[] { " # ", " # ", " T ", Character.valueOf('#'), EEItem.redMatter, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('T'), new ItemStack(EEItem.darkSword, 1, -1) });
/* 1191 */       ModLoader.addRecipe(new ItemStack(EEItem.redAxe), new Object[] { "## ", "#T ", " D ", Character.valueOf('#'), EEItem.redMatter, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('T'), new ItemStack(EEItem.darkAxe, 1, -1) });
/* 1192 */       ModLoader.addRecipe(new ItemStack(EEItem.redShears), new Object[] { " #", "T ", Character.valueOf('#'), EEItem.redMatter, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('T'), new ItemStack(EEItem.darkShears, 1, -1) });
/* 1193 */       ModLoader.addRecipe(new ItemStack(EEItem.redHammer), new Object[] { "#D#", " T ", " D ", Character.valueOf('#'), EEItem.redMatter, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('T'), new ItemStack(EEItem.darkHammer, 1, -1) });
/* 1194 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.redKatar), new Object[] { new ItemStack(EEItem.redShears, 1, -1), new ItemStack(EEItem.redAxe, 1, -1), new ItemStack(EEItem.redSword, 1, -1), new ItemStack(EEItem.redHoe, 1, -1), EEItem.redMatter, EEItem.redMatter, EEItem.redMatter, EEItem.redMatter, EEItem.redMatter });
/* 1195 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.redMace), new Object[] { new ItemStack(EEItem.redHammer, 1, -1), new ItemStack(EEItem.redPickaxe, 1, -1), new ItemStack(EEItem.redSpade, 1, -1), EEItem.redMatter, EEItem.redMatter, EEItem.redMatter, EEItem.redMatter, EEItem.redMatter, EEItem.redMatter });
/*      */     }
/*      */ 
/* 1198 */     if (EEBase.props.getInt("AllowDMArmor") == 1)
/*      */     {
/* 1200 */       ModLoader.addRecipe(new ItemStack(EEItem.darkMatterArmor, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), EEItem.darkMatter });
/* 1201 */       ModLoader.addRecipe(new ItemStack(EEItem.darkMatterHelmet, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), EEItem.darkMatter });
/* 1202 */       ModLoader.addRecipe(new ItemStack(EEItem.darkMatterGreaves, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), EEItem.darkMatter });
/* 1203 */       ModLoader.addRecipe(new ItemStack(EEItem.darkMatterBoots, 1), new Object[] { "X X", "X X", Character.valueOf('X'), EEItem.darkMatter });
/*      */     }
/*      */ 
/* 1206 */     if (EEBase.props.getInt("AllowRMArmor") == 1)
/*      */     {
/* 1208 */       ModLoader.addRecipe(new ItemStack(EEItem.redMatterArmor), new Object[] { "XAX", "XXX", "XXX", Character.valueOf('X'), EEItem.redMatter, Character.valueOf('A'), EEItem.darkMatterArmor });
/* 1209 */       ModLoader.addRecipe(new ItemStack(EEItem.redMatterHelmet), new Object[] { "XXX", "XAX", Character.valueOf('X'), EEItem.redMatter, Character.valueOf('A'), EEItem.darkMatterHelmet });
/* 1210 */       ModLoader.addRecipe(new ItemStack(EEItem.redMatterBoots), new Object[] { "XAX", "X X", Character.valueOf('X'), EEItem.redMatter, Character.valueOf('A'), EEItem.darkMatterBoots });
/* 1211 */       ModLoader.addRecipe(new ItemStack(EEItem.redMatterGreaves), new Object[] { "XXX", "XAX", "X X", Character.valueOf('X'), EEItem.redMatter, Character.valueOf('A'), EEItem.darkMatterGreaves });
/*      */     }
/*      */ 
/* 1214 */     if (EEBase.props.getInt("AllowRMArmorPlus") == 1)
/*      */     {
/* 1216 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.redMatterArmorP), new Object[] { EEItem.redMatterArmor, new ItemStack(EEItem.kleinStar6, 1, 1), new ItemStack(EEItem.volcanite, 1, -1), EEItem.bodyStone });
/* 1217 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.redMatterHelmetP), new Object[] { EEItem.redMatterHelmet, new ItemStack(EEItem.kleinStar6, 1, 1), new ItemStack(EEItem.evertide, 1, -1), EEItem.soulStone });
/* 1218 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.redMatterGreavesP), new Object[] { EEItem.redMatterGreaves, new ItemStack(EEItem.kleinStar6, 1, 1), EEItem.eternalDensity, new ItemStack(EEItem.watchOfTime, 1, -1) });
/* 1219 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.redMatterBootsP), new Object[] { EEItem.redMatterBoots, new ItemStack(EEItem.kleinStar6, 1, 1), new ItemStack(EEItem.swiftWolfRing, 1, -1), new ItemStack(EEItem.swiftWolfRing, 1, -1) });
/*      */     }
/*      */ 
/* 1222 */     ModLoader.addRecipe(new ItemStack(EEItem.alchemyTome), new Object[] { "LMH", "KBK", "HML", Character.valueOf('L'), lcov(), Character.valueOf('M'), mcov(), Character.valueOf('H'), hcov(), Character.valueOf('K'), new ItemStack(EEItem.kleinStar6, 1, 1), Character.valueOf('B'), Item.BOOK });
/* 1223 */     ModLoader.addRecipe(new ItemStack(EEItem.alchemyTome), new Object[] { "HML", "KBK", "LMH", Character.valueOf('L'), lcov(), Character.valueOf('M'), mcov(), Character.valueOf('H'), hcov(), Character.valueOf('K'), new ItemStack(EEItem.kleinStar6, 1, 1), Character.valueOf('B'), Item.BOOK });
/*      */ 
/* 1225 */     if (EEBase.props.getInt("AllowFurnaces") == 1)
/*      */     {
/* 1227 */       ModLoader.addRecipe(EEBlock.dmFurnace, new Object[] { "DDD", "DFD", "DDD", Character.valueOf('D'), EEBlock.dmBlock, Character.valueOf('F'), Block.FURNACE });
/* 1228 */       ModLoader.addRecipe(EEBlock.rmFurnace, new Object[] { " R ", "RFR", "   ", Character.valueOf('R'), EEBlock.rmBlock, Character.valueOf('F'), EEBlock.dmFurnace });
/*      */     }
/*      */ 
/* 1231 */     if (EEBase.props.getInt("AllowCollectors") == 1)
/*      */     {
/* 1233 */       ModLoader.addRecipe(EEBlock.collector, new Object[] { "#G#", "#D#", "#F#", Character.valueOf('#'), Block.GLOWSTONE, Character.valueOf('D'), Block.DIAMOND_BLOCK, Character.valueOf('G'), Block.GLASS, Character.valueOf('F'), Block.FURNACE });
/* 1234 */       ModLoader.addRecipe(EEBlock.collector2, new Object[] { "#D#", "#C#", "###", Character.valueOf('#'), Block.GLOWSTONE, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('C'), EEBlock.collector });
/* 1235 */       ModLoader.addRecipe(EEBlock.collector3, new Object[] { "#D#", "#C#", "###", Character.valueOf('#'), Block.GLOWSTONE, Character.valueOf('D'), EEItem.redMatter, Character.valueOf('C'), EEBlock.collector2 });
/*      */     }
/*      */ 
/* 1238 */     if (EEBase.props.getInt("AllowCondensers") == 1)
/*      */     {
/* 1240 */       ModLoader.addRecipe(EEBlock.condenser, new Object[] { "ODO", "DAD", "ODO", Character.valueOf('O'), Block.OBSIDIAN, Character.valueOf('A'), EEBlock.alchChest, Character.valueOf('D'), Item.DIAMOND });
/*      */     }
/*      */ 
/* 1243 */     if (EEBase.props.getInt("AllowChests") == 1)
/*      */     {
/* 1245 */       ModLoader.addRecipe(EEBlock.alchChest, new Object[] { "LMH", "SDS", "ICI", Character.valueOf('L'), new ItemStack(EEItem.covalenceDust, 1, 0), Character.valueOf('M'), new ItemStack(EEItem.covalenceDust, 1, 1), Character.valueOf('H'), new ItemStack(EEItem.covalenceDust, 1, 2), Character.valueOf('C'), Block.CHEST, Character.valueOf('S'), Block.STONE, Character.valueOf('D'), Item.DIAMOND, Character.valueOf('I'), Item.IRON_INGOT });
/*      */     }
/*      */ 
/* 1248 */     if (EEBase.props.getInt("AllowAlchemyBags") == 1)
/*      */     {
/* 1250 */       for (int var0 = 0; var0 < 16; var0++)
/*      */       {
/* 1252 */         ModLoader.addShapelessRecipe(new ItemStack(EEItem.alchemyBag, 1, 15 - var0), new Object[] { new ItemStack(EEItem.alchemyBag, 1, -1), new ItemStack(Item.INK_SACK, 1, var0) });
/* 1253 */         ModLoader.addRecipe(new ItemStack(EEItem.alchemyBag, 1, var0), new Object[] { "HHH", "WCW", "WWW", Character.valueOf('H'), new ItemStack(EEItem.covalenceDust, 1, 2), Character.valueOf('W'), new ItemStack(Block.WOOL, 1, var0), Character.valueOf('C'), EEBlock.alchChest });
/*      */       }
/*      */     }
/*      */ 
/* 1257 */     if (EEBase.props.getInt("AllowNovaC1") == 1)
/*      */     {
/* 1259 */       ModLoader.addShapelessRecipe(new ItemStack(EEBlock.eeStone, 2, 10), new Object[] { Block.TNT, EEItem.mobiusFuel });
/*      */     }
/*      */ 
/* 1262 */     if (EEBase.props.getInt("AllowNovaC2") == 1)
/*      */     {
/* 1264 */       ModLoader.addShapelessRecipe(new ItemStack(EEBlock.eeStone, 2, 11), new Object[] { new ItemStack(EEBlock.eeStone, 1, 10), EEItem.aeternalisFuel });
/*      */     }
/*      */ 
/* 1267 */     if (EEBase.props.getInt("AllowInterdiction") == 1)
/*      */     {
/* 1269 */       ModLoader.addRecipe(new ItemStack(EEBlock.eeTorch, 2), new Object[] { "TDT", "DPD", "GGG", Character.valueOf('T'), Block.REDSTONE_TORCH_ON, Character.valueOf('D'), Item.DIAMOND, Character.valueOf('G'), Item.GLOWSTONE_DUST, Character.valueOf('P'), pstone() });
/*      */     }
/*      */ 
/* 1272 */     if (EEBase.props.getInt("AllowRelays") == 1)
/*      */     {
/* 1274 */       ModLoader.addRecipe(EEBlock.relay, new Object[] { "OGO", "OAO", "OOO", Character.valueOf('A'), Block.DIAMOND_BLOCK, Character.valueOf('O'), Block.OBSIDIAN, Character.valueOf('G'), Block.GLASS });
/* 1275 */       ModLoader.addRecipe(EEBlock.relay2, new Object[] { "ODO", "OAO", "OOO", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('A'), EEBlock.relay, Character.valueOf('O'), Block.OBSIDIAN });
/* 1276 */       ModLoader.addRecipe(EEBlock.relay3, new Object[] { "ODO", "OAO", "OOO", Character.valueOf('D'), EEItem.redMatter, Character.valueOf('A'), EEBlock.relay2, Character.valueOf('O'), Block.OBSIDIAN });
/*      */     }
/*      */ 
/* 1279 */     if (EEBase.props.getInt("AllowDCatalyst") == 1)
/*      */     {
/* 1281 */       ModLoader.addRecipe(new ItemStack(EEItem.catalystStone), new Object[] { "#C#", "CFC", "#C#", Character.valueOf('#'), EEBlock.novaCatalyst, Character.valueOf('C'), EEItem.mobiusFuel, Character.valueOf('F'), new ItemStack(Item.FLINT_AND_STEEL, 1, -1) });
/*      */     }
/*      */ 
/* 1284 */     if (EEBase.props.getInt("AllowHKLens") == 1)
/*      */     {
/* 1286 */       ModLoader.addRecipe(new ItemStack(EEItem.hyperkineticLens), new Object[] { "DDD", "MCM", "DDD", Character.valueOf('D'), Item.DIAMOND, Character.valueOf('C'), EEBlock.novaCatalyst, Character.valueOf('M'), EEItem.darkMatter });
/*      */     }
/*      */ 
/* 1289 */     if (EEBase.props.getInt("AllowHCLens") == 1)
/*      */     {
/* 1291 */       ModLoader.addRecipe(new ItemStack(EEItem.hyperCatalyst), new Object[] { "DDD", "CDL", "DDD", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('C'), new ItemStack(EEItem.catalystStone, 1, -1), Character.valueOf('L'), new ItemStack(EEItem.hyperkineticLens, 1, -1) });
/* 1292 */       ModLoader.addRecipe(new ItemStack(EEItem.hyperCatalyst), new Object[] { "DDD", "LDC", "DDD", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('C'), new ItemStack(EEItem.catalystStone, 1, -1), Character.valueOf('L'), new ItemStack(EEItem.hyperkineticLens, 1, -1) });
/*      */     }
/*      */ 
/* 1295 */     if (EEBase.props.getInt("AllowSoulstone") == 1)
/*      */     {
/* 1297 */       ModLoader.addRecipe(new ItemStack(EEItem.soulStone), new Object[] { "LLL", "DXD", "LLL", Character.valueOf('L'), Item.GLOWSTONE_DUST, Character.valueOf('X'), new ItemStack(Item.INK_SACK, 1, 4), Character.valueOf('D'), EEItem.redMatter });
/*      */     }
/*      */ 
/* 1300 */     if (EEBase.props.getInt("AllowBodystone") == 1)
/*      */     {
/* 1302 */       ModLoader.addRecipe(new ItemStack(EEItem.bodyStone), new Object[] { "LLL", "DXD", "LLL", Character.valueOf('L'), Item.SUGAR, Character.valueOf('X'), new ItemStack(Item.INK_SACK, 1, 4), Character.valueOf('D'), EEItem.redMatter });
/*      */     }
/*      */ 
/* 1305 */     if (EEBase.props.getInt("AllowLifestone") == 1)
/*      */     {
/* 1307 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.lifeStone), new Object[] { EEItem.bodyStone, EEItem.soulStone });
/*      */     }
/*      */ 
/* 1310 */     if (EEBase.props.getInt("AllowMindstone") == 1)
/*      */     {
/* 1312 */       ModLoader.addRecipe(new ItemStack(EEItem.mindStone), new Object[] { "LLL", "DXD", "LLL", Character.valueOf('L'), Item.BOOK, Character.valueOf('X'), new ItemStack(Item.INK_SACK, 1, 4), Character.valueOf('D'), EEItem.redMatter });
/*      */     }
/*      */ 
/* 1315 */     if (EEBase.props.getInt("AllowEvertide") == 1)
/*      */     {
/* 1317 */       ModLoader.addRecipe(new ItemStack(EEItem.evertide), new Object[] { "###", "DDD", "###", Character.valueOf('#'), Item.WATER_BUCKET, Character.valueOf('D'), EEItem.darkMatter });
/*      */     }
/*      */ 
/* 1320 */     if (EEBase.props.getInt("AllowVolcanite") == 1)
/*      */     {
/* 1322 */       ModLoader.addRecipe(new ItemStack(EEItem.volcanite), new Object[] { "BBB", "###", "BBB", Character.valueOf('B'), Item.LAVA_BUCKET, Character.valueOf('#'), EEItem.darkMatter });
/*      */     }
/*      */ 
/* 1325 */     ModLoader.addRecipe(new ItemStack(EEItem.baseRing), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('X'), new ItemStack(EEItem.volcanite, 1, -1) });
/* 1326 */     ModLoader.addRecipe(new ItemStack(EEItem.baseRing), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('X'), Item.LAVA_BUCKET });
/*      */ 
/* 1328 */     if (EEBase.props.getInt("AllowBlackHoleBand") == 1)
/*      */     {
/* 1330 */       ModLoader.addRecipe(new ItemStack(EEItem.attractionRing), new Object[] { "###", "DRD", "###", Character.valueOf('#'), Item.STRING, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('R'), EEItem.baseRing });
/*      */     }
/*      */ 
/* 1333 */     if (EEBase.props.getInt("AllowArchangel") == 1)
/*      */     {
/* 1335 */       ModLoader.addRecipe(new ItemStack(EEItem.grimarchRing), new Object[] { "#F#", "DRD", "#F#", Character.valueOf('#'), Item.BOW, Character.valueOf('F'), Item.FEATHER, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('R'), EEItem.baseRing });
/*      */     }
/*      */ 
/* 1338 */     if (EEBase.props.getInt("AllowIgnition") == 1)
/*      */     {
/* 1340 */       ModLoader.addRecipe(new ItemStack(EEItem.ignitionRing), new Object[] { "#F#", "DRD", "#F#", Character.valueOf('#'), Item.FLINT_AND_STEEL, Character.valueOf('F'), EEItem.mobiusFuel, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('R'), EEItem.baseRing });
/*      */     }
/*      */ 
/* 1343 */     if (EEBase.props.getInt("AllowZeroRing") == 1)
/*      */     {
/* 1345 */       ModLoader.addRecipe(new ItemStack(EEItem.zeroRing), new Object[] { "#F#", "DRD", "#F#", Character.valueOf('#'), Block.SNOW_BLOCK, Character.valueOf('F'), Item.SNOW_BALL, Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('R'), EEItem.baseRing });
/*      */     }
/*      */ 
/* 1348 */     if (EEBase.props.getInt("AllowArcana") == 1)
/*      */     {
/* 1350 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.arcaneRing), new Object[] { new ItemStack(EEItem.ignitionRing, 1, -1), new ItemStack(EEItem.zeroRing, 1, -1), new ItemStack(EEItem.swiftWolfRing, 1, -1), new ItemStack(EEItem.harvestRing, 1, -1), EEItem.redMatter, EEItem.redMatter, EEItem.redMatter, EEItem.redMatter, EEItem.redMatter });
/*      */     }
/*      */ 
/* 1353 */     if (EEBase.props.getInt("AllowVoidRing") == 1)
/*      */     {
/* 1355 */       ModLoader.addShapelessRecipe(new ItemStack(EEItem.voidRing), new Object[] { EEItem.attractionRing, EEItem.eternalDensity, EEItem.redMatter, EEItem.redMatter });
/*      */     }
/*      */ 
/* 1358 */     if (EEBase.props.getInt("AllowSwiftWolf") == 1)
/*      */     {
/* 1360 */       ModLoader.addRecipe(new ItemStack(EEItem.swiftWolfRing), new Object[] { "DFD", "FBF", "DFD", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('F'), Item.FEATHER, Character.valueOf('B'), EEItem.baseRing });
/*      */     }
/*      */ 
/* 1363 */     if (EEBase.props.getInt("AllowHarvestBand") == 1)
/*      */     {
/* 1365 */       ModLoader.addRecipe(new ItemStack(EEItem.harvestRing), new Object[] { "SYS", "DBD", "SRS", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('B'), EEItem.baseRing, Character.valueOf('S'), Block.SAPLING, Character.valueOf('R'), BlockFlower.RED_ROSE, Character.valueOf('Y'), BlockFlower.YELLOW_FLOWER });
/* 1366 */       ModLoader.addRecipe(new ItemStack(EEItem.harvestRing), new Object[] { "SRS", "DBD", "SYS", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('B'), EEItem.baseRing, Character.valueOf('S'), Block.SAPLING, Character.valueOf('R'), BlockFlower.RED_ROSE, Character.valueOf('Y'), BlockFlower.YELLOW_FLOWER });
/*      */     }
/*      */ 
/* 1369 */     if (EEBase.props.getInt("AllowDiviningRod") == 1)
/*      */     {
/* 1371 */       ModLoader.addRecipe(new ItemStack(EEItem.diviningRod, 1, 0), new Object[] { "LLL", "LSL", "LLL", Character.valueOf('S'), Item.STICK, Character.valueOf('L'), new ItemStack(EEItem.covalenceDust, 1, 0) });
/* 1372 */       ModLoader.addRecipe(new ItemStack(EEItem.diviningRod, 1, 1), new Object[] { "LLL", "LSL", "LLL", Character.valueOf('S'), new ItemStack(EEItem.diviningRod, 1, 0), Character.valueOf('L'), new ItemStack(EEItem.covalenceDust, 1, 1) });
/* 1373 */       ModLoader.addRecipe(new ItemStack(EEItem.diviningRod, 1, 2), new Object[] { "LLL", "LSL", "LLL", Character.valueOf('S'), new ItemStack(EEItem.diviningRod, 1, 1), Character.valueOf('L'), new ItemStack(EEItem.covalenceDust, 1, 2) });
/*      */     }
/*      */ 
/* 1376 */     if (EEBase.props.getInt("AllowRepair") == 1)
/*      */     {
/* 1378 */       ModLoader.addRecipe(new ItemStack(EEItem.repairCharm), new Object[] { "LMH", "SBS", "HML", Character.valueOf('S'), Item.STRING, Character.valueOf('B'), Item.PAPER, Character.valueOf('L'), new ItemStack(EEItem.covalenceDust, 1, 0), Character.valueOf('M'), new ItemStack(EEItem.covalenceDust, 1, 1), Character.valueOf('H'), new ItemStack(EEItem.covalenceDust, 1, 2) });
/* 1379 */       ModLoader.addRecipe(new ItemStack(EEItem.repairCharm), new Object[] { "HML", "SBS", "LMH", Character.valueOf('S'), Item.STRING, Character.valueOf('B'), Item.PAPER, Character.valueOf('L'), new ItemStack(EEItem.covalenceDust, 1, 0), Character.valueOf('M'), new ItemStack(EEItem.covalenceDust, 1, 1), Character.valueOf('H'), new ItemStack(EEItem.covalenceDust, 1, 2) });
/*      */     }
/*      */ 
/* 1382 */     if (EEBase.props.getInt("AllowWatchOfTime") == 1)
/*      */     {
/* 1384 */       ModLoader.addRecipe(new ItemStack(EEItem.watchOfTime), new Object[] { "DOD", "GCG", "DOD", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('O'), Block.OBSIDIAN, Character.valueOf('G'), Block.GLOWSTONE, Character.valueOf('C'), Item.WATCH });
/* 1385 */       ModLoader.addRecipe(new ItemStack(EEItem.watchOfTime), new Object[] { "DGD", "OCO", "DGD", Character.valueOf('D'), EEItem.darkMatter, Character.valueOf('O'), Block.OBSIDIAN, Character.valueOf('G'), Block.GLOWSTONE, Character.valueOf('C'), Item.WATCH });
/*      */     }
/*      */ 
/* 1388 */     if (EEBase.props.getInt("AllowMercurial") == 1)
/*      */     {
/* 1390 */       ModLoader.addRecipe(new ItemStack(EEItem.mercurialEye), new Object[] { "OBO", "BRB", "BDB", Character.valueOf('D'), Item.DIAMOND, Character.valueOf('O'), Block.OBSIDIAN, Character.valueOf('R'), EEItem.redMatter, Character.valueOf('B'), Block.BRICK });
/*      */     }
/*      */ 
/* 1393 */     if (EEBase.props.getInt("AllowEternalDensity") == 1)
/*      */     {
/* 1395 */       ModLoader.addRecipe(new ItemStack(EEItem.eternalDensity), new Object[] { "DOD", "MDM", "DOD", Character.valueOf('M'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND, Character.valueOf('O'), Block.OBSIDIAN });
/* 1396 */       ModLoader.addRecipe(new ItemStack(EEItem.eternalDensity), new Object[] { "DMD", "ODO", "DMD", Character.valueOf('M'), EEItem.darkMatter, Character.valueOf('D'), Item.DIAMOND, Character.valueOf('O'), Block.OBSIDIAN });
/*      */     }
/*      */ 
/* 1399 */     if (EEBase.props.getInt("AllowKleinStar") == 1)
/*      */     {
/* 1401 */       ModLoader.addRecipe(new ItemStack(EEItem.kleinStar1), new Object[] { "FFF", "FDF", "FFF", Character.valueOf('F'), EEItem.mobiusFuel, Character.valueOf('D'), Item.DIAMOND });
/* 1402 */       addKleinForMerge(EEItem.kleinStar1);
/* 1403 */       ModLoader.addRecipe(new ItemStack(EEItem.kleinStar2), new Object[] { "FF", "FF", Character.valueOf('F'), new ItemStack(EEItem.kleinStar1, 1, -1) });
/* 1404 */       addKleinForMerge(EEItem.kleinStar2);
/* 1405 */       ModLoader.addRecipe(new ItemStack(EEItem.kleinStar3), new Object[] { "FF", "FF", Character.valueOf('F'), new ItemStack(EEItem.kleinStar2, 1, -1) });
/* 1406 */       addKleinForMerge(EEItem.kleinStar3);
/* 1407 */       ModLoader.addRecipe(new ItemStack(EEItem.kleinStar4), new Object[] { "FF", "FF", Character.valueOf('F'), new ItemStack(EEItem.kleinStar3, 1, -1) });
/* 1408 */       addKleinForMerge(EEItem.kleinStar4);
/* 1409 */       ModLoader.addRecipe(new ItemStack(EEItem.kleinStar5), new Object[] { "FF", "FF", Character.valueOf('F'), new ItemStack(EEItem.kleinStar4, 1, -1) });
/* 1410 */       addKleinForMerge(EEItem.kleinStar5);
/* 1411 */       ModLoader.addRecipe(new ItemStack(EEItem.kleinStar6), new Object[] { "FF", "FF", Character.valueOf('F'), new ItemStack(EEItem.kleinStar5, 1, -1) });
/* 1412 */       addKleinForMerge(EEItem.kleinStar6);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void debugRecipes() {
/*      */   }
/*      */ 
/*      */   public static ItemStack cobble(int var0) {
/* 1420 */     return new ItemStack(Block.COBBLESTONE, var0);
/*      */   }
/*      */ 
/*      */   public static ItemStack iing(int var0)
/*      */   {
/* 1425 */     return new ItemStack(Item.IRON_INGOT, var0);
/*      */   }
/*      */ 
/*      */   public static ItemStack diamond(int var0)
/*      */   {
/* 1430 */     return new ItemStack(Item.DIAMOND, var0);
/*      */   }
/*      */ 
/*      */   public static ItemStack redstone(int var0)
/*      */   {
/* 1435 */     return new ItemStack(Item.REDSTONE, var0);
/*      */   }
/*      */ 
/*      */   public static ItemStack coal(int var0, int var1)
/*      */   {
/* 1440 */     return new ItemStack(Item.COAL, var0, var1);
/*      */   }
/*      */ 
/*      */   public static ItemStack glowdust(int var0)
/*      */   {
/* 1445 */     return new ItemStack(Item.GLOWSTONE_DUST, var0);
/*      */   }
/*      */ 
/*      */   public static ItemStack alcoal(int var0)
/*      */   {
/* 1450 */     return new ItemStack(EEItem.alchemicalCoal, var0);
/*      */   }
/*      */ 
/*      */   public static ItemStack glowblock(int var0)
/*      */   {
/* 1455 */     return new ItemStack(Block.GLOWSTONE, var0);
/*      */   }
/*      */ 
/*      */   public static ItemStack mobius(int var0)
/*      */   {
/* 1460 */     return new ItemStack(EEItem.mobiusFuel, var0);
/*      */   }
/*      */ 
/*      */   public static ItemStack coval(int var0, int var1)
/*      */   {
/* 1465 */     return new ItemStack(EEItem.covalenceDust, var0, var1);
/*      */   }
/*      */ 
/*      */   public static ItemStack lcov()
/*      */   {
/* 1470 */     return coval(1, 0);
/*      */   }
/*      */ 
/*      */   public static ItemStack mcov()
/*      */   {
/* 1475 */     return coval(1, 1);
/*      */   }
/*      */ 
/*      */   public static ItemStack hcov()
/*      */   {
/* 1480 */     return coval(1, 2);
/*      */   }
/*      */ 
/*      */   public static ItemStack pstone()
/*      */   {
/* 1485 */     return new ItemStack(EEItem.philStone, 1, -1);
/*      */   }
/*      */ 
/*      */   public static void addKleinForMerge(Item var0)
/*      */   {
/* 1490 */     EEMergeLib.addMergeOnCraft(var0);
/*      */   }
/*      */ 
/*      */   public static void addRingDestroy(Item var0)
/*      */   {
/* 1495 */     EEMergeLib.addDestroyOnCraft(var0);
/*      */   }
/*      */ 
/*      */   public static Object[] multiStack(ItemStack var0, int var1)
/*      */   {
/* 1500 */     Object[] var2 = new Object[var1];
/*      */ 
/* 1502 */     for (int var3 = 0; var3 < var1; var3++)
/*      */     {
/* 1504 */       var2[var3] = var0;
/*      */     }
/*      */ 
/* 1507 */     return var2;
/*      */   }
/*      */ 
/*      */   public static boolean isValidEDItem(ItemStack var0)
/*      */   {
/* 1512 */     int var1 = var0.id;
/* 1513 */     return (var1 == Block.COBBLESTONE.id) || (var1 == Block.DIRT.id) || (var1 == Block.SAND.id) || (var1 == Block.NETHERRACK.id) || (var1 == Block.SOUL_SAND.id) || (var1 == Block.GRAVEL.id) || (var1 == Block.SANDSTONE.id) || (var1 == Block.OBSIDIAN.id) || (var1 == Block.LEAVES.id) || (var1 == Block.SNOW_BLOCK.id) || (var1 == Item.IRON_INGOT.id) || (var1 == Item.GOLD_INGOT.id) || (var1 == Item.DIAMOND.id) || (var1 == EEItem.darkMatter.id);
/*      */   }
/*      */ 
/*      */   public static void addAlchemicalValue(ItemStack var0, int var1)
/*      */   {
/* 1518 */     if (var0 != null)
/*      */     {
/* 1520 */       addEMC(var0.id, var0.getData(), var1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void addChargedItem(ItemStack var0)
/*      */   {
/* 1526 */     if (var0 != null)
/*      */     {
/* 1528 */       addChargedItem(var0.id);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void addOreBlock(ItemStack var0)
/*      */   {
/* 1534 */     if (var0 != null)
/*      */     {
/* 1536 */       addOreBlock(var0.id);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void addLeafBlock(ItemStack var0)
/*      */   {
/* 1542 */     if (var0 != null)
/*      */     {
/* 1544 */       addLeafBlock(var0.id);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void addWoodBlock(ItemStack var0)
/*      */   {
/* 1550 */     if (var0 != null)
/*      */     {
/* 1552 */       addWoodBlock(var0.id);
/*      */     }
/*      */   }
/*      */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEMaps
 * JD-Core Version:    0.6.2
 */