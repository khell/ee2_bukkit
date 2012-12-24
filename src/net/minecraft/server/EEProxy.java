/*     */ package net.minecraft.server;
/*     */ 
/*     */ import ee.EEBase;
/*     */ import ee.EEMaps;
/*     */ import ee.TransTabletData;
/*     */ import forge.DimensionManager;
/*     */ import forge.NetworkMod;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class EEProxy
/*     */ {
/*     */   public static final int MAXWORLDHEIGHT = 256;
/*     */   private static boolean initialized;
/*     */   private static MinecraftServer mc;
/*     */   private static NetworkMod ee;
/*     */   public static World theWorld;
/*  26 */   public static Map<String, TTGroup> ttGroups = new HashMap();
/*  27 */   public static Map<String, TTGroup> playerGroups = new HashMap();
/*     */ 
/*     */   public static void Init(MinecraftServer var0, NetworkMod var1)
/*     */   {
/*  31 */     if (!initialized)
/*     */     {
/*  33 */       initialized = true;
/*     */     }
/*     */ 
/*  36 */     ee = var1;
/*  37 */     mc = var0;
/*  38 */     theWorld = DimensionManager.getWorld(0);
/*     */   }
/*     */ 
/*     */   public static boolean isClient(World var0)
/*     */   {
/*  43 */     return var0.isStatic;
/*     */   }
/*     */ 
/*     */   public static boolean isServer()
/*     */   {
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */   public static Object getTileEntity(IBlockAccess var0, int var1, int var2, int var3, Class var4)
/*     */   {
/*  53 */     if (var2 < 0)
/*     */     {
/*  55 */       return null;
/*     */     }
/*     */ 
/*  59 */     TileEntity var5 = var0.getTileEntity(var1, var2, var3);
/*  60 */     return !var4.isInstance(var5) ? null : var5;
/*     */   }
/*     */ 
/*     */   public static boolean addTTGroup(String groupName, int masterDimension, Integer[] otherDimensions)
/*     */   {
/* 106 */     if (ttGroups.containsKey(groupName))
/*     */     {
/* 108 */       return false;
/*     */     }
/*     */ 
/* 112 */     TTGroup grp = new TTGroup(groupName, masterDimension);
/* 113 */     grp.addDimensions(otherDimensions);
/* 114 */     ttGroups.put(groupName, grp);
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean addPlayerToGroup(String player, String group)
/*     */   {
/* 120 */     TTGroup ttGroup = (TTGroup)ttGroups.get(group);
/* 121 */     if (ttGroup == null) {
/* 122 */       return false;
/*     */     }
/* 124 */     TTGroup pGroup = (TTGroup)playerGroups.get(player);
/* 125 */     if ((pGroup != null) && (pGroup != ttGroup)) {
/* 126 */       return false;
/*     */     }
/* 128 */     ttGroup.addPlayer(player);
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */   public static void removePlayerFromGroup(String player) {
/* 133 */     TTGroup grp = (TTGroup)playerGroups.get(player);
/* 134 */     if (grp != null)
/* 135 */       grp.removePlayer(player);
/*     */   }
/*     */ 
/*     */   public static String getGroupDescription(String gName)
/*     */   {
/* 140 */     TTGroup group = (TTGroup)ttGroups.get(gName);
/* 141 */     if (group == null) {
/* 142 */       return "No such group";
/*     */     }
/* 144 */     return String.format("%s : Dimensions (main %d others %s) members %s", new Object[] { group.getGroupName(), Integer.valueOf(group.getMasterDimension()), group.dimensions, group.players });
/*     */   }
/*     */ 
/*     */   public static TransTabletData getTransData(EntityHuman var0)
/*     */   {
/* 149 */     String playerName = var0.name;
/* 150 */     int dimension = 0;
/* 151 */     if (playerGroups.containsKey(playerName))
/*     */     {
/* 153 */       TTGroup groupList = (TTGroup)playerGroups.get(playerName);
/* 154 */       if (groupList.containsDimension(var0.dimension))
/*     */       {
/* 156 */         playerName = groupList.getGroupName();
/* 157 */         dimension = groupList.getMasterDimension();
/*     */       }
/*     */     }
/*     */ 
/* 161 */     String var1 = "tablet_" + playerName;
/*     */ 
/* 163 */     TransTabletData var2 = (TransTabletData)DimensionManager.getWorld(dimension).a(TransTabletData.class, var1);
/*     */ 
/* 165 */     if (var2 == null)
/*     */     {
/* 167 */       var2 = new TransTabletData(var1);
/* 168 */       var2.a();
/* 169 */       DimensionManager.getWorld(dimension).a(var1, var2);
/*     */     }
/*     */ 
/* 172 */     return var2;
/*     */   }
/*     */ 
/*     */   public static boolean isEntityFireImmune(Entity var0)
/*     */   {
/* 177 */     return var0.fireProof;
/*     */   }
/*     */ 
/*     */   public static int getEntityHealth(EntityLiving var0)
/*     */   {
/* 182 */     return var0.health;
/*     */   }
/*     */ 
/*     */   public static void dealFireDamage(Entity var0, int var1)
/*     */   {
/* 187 */     var0.burn(var1);
/*     */   }
/*     */ 
/*     */   public static int getArmorRating(EntityLiving var0)
/*     */   {
/* 192 */     return var0.lastDamage;
/*     */   }
/*     */ 
/*     */   public static void setArmorRating(EntityLiving var0, int var1)
/*     */   {
/* 197 */     var0.lastDamage = var1;
/*     */   }
/*     */ 
/*     */   public static FoodMetaData getFoodStats(EntityHuman var0)
/*     */   {
/* 202 */     return var0.foodData;
/*     */   }
/*     */ 
/*     */   public static WorldData getWorldInfo(World var0)
/*     */   {
/* 207 */     return var0.worldData;
/*     */   }
/*     */ 
/*     */   public static int getMaxStackSize(Item var0)
/*     */   {
/* 212 */     return var0.maxStackSize;
/*     */   }
/*     */ 
/*     */   public static int blockDamageDropped(Block var0, int var1)
/*     */   {
/* 217 */     return var0.getDropData(var1);
/*     */   }
/*     */ 
/*     */   public static void dropBlockAsItemStack(Block var0, EntityLiving var1, int var2, int var3, int var4, ItemStack var5)
/*     */   {
/* 222 */     var0.a(var1.world, var2, var3, var4, var5);
/*     */   }
/*     */ 
/*     */   public static void setPlayerFireImmunity(EntityHuman var0, boolean var1)
/*     */   {
/* 227 */     var0.fireProof = var1;
/*     */   }
/*     */ 
/*     */   public static void setEMC(ItemStack var0, int var1)
/*     */   {
/* 232 */     EEMaps.addEMC(var0.id, var0.getData(), var1);
/*     */   }
/*     */ 
/*     */   public static void setEMC(int var0, int var1, int var2)
/*     */   {
/* 237 */     EEMaps.addEMC(var0, var1, var2);
/*     */   }
/*     */ 
/*     */   public static void setEMC(int var0, int var1)
/*     */   {
/* 242 */     setEMC(var0, 0, var1);
/*     */   }
/*     */ 
/*     */   public static int getEMC(ItemStack var0)
/*     */   {
/* 247 */     return EEMaps.getEMC(var0);
/*     */   }
/*     */ 
/*     */   public static int getEMC(int var0, int var1)
/*     */   {
/* 252 */     ItemStack var2 = new ItemStack(var0, 1, var1);
/* 253 */     return EEMaps.getEMC(var2);
/*     */   }
/*     */ 
/*     */   public static int getEMC(int var0)
/*     */   {
/* 258 */     ItemStack var1 = new ItemStack(var0, 1, 0);
/* 259 */     return EEMaps.getEMC(var1);
/*     */   }
/*     */ 
/*     */   public static boolean isFuel(ItemStack var0)
/*     */   {
/* 264 */     return isFuel(var0.id, var0.getData());
/*     */   }
/*     */ 
/*     */   public static boolean isFuel(int var0)
/*     */   {
/* 269 */     return isFuel(var0, 0);
/*     */   }
/*     */ 
/*     */   public static boolean isFuel(int var0, int var1)
/*     */   {
/* 274 */     return EEMaps.isFuel(var0, var1);
/*     */   }
/*     */ 
/*     */   public static void addFuel(ItemStack var0)
/*     */   {
/* 279 */     addFuel(var0.id, var0.getData());
/*     */   }
/*     */ 
/*     */   public static void addFuel(int var0)
/*     */   {
/* 284 */     addFuel(var0, 0);
/*     */   }
/*     */ 
/*     */   public static void addFuel(int var0, int var1)
/*     */   {
/* 289 */     EEMaps.addFuelItem(var0, var1);
/*     */   }
/*     */ 
/*     */   public static void handleControl(NetworkManager var0, int var1)
/*     */   {
/* 294 */     NetServerHandler var2 = (NetServerHandler)var0.getNetHandler();
/* 295 */     EntityPlayer var3 = var2.getPlayerEntity();
/*     */ 
/* 297 */     switch (var1)
/*     */     {
/*     */     case 0:
/* 300 */       EEBase.doAlternate(var3.world, var3);
/* 301 */       break;
/*     */     case 1:
/* 303 */       EEBase.doCharge(var3.world, var3);
/* 304 */       break;
/*     */     case 2:
/* 306 */       EEBase.doToggle(var3.world, var3);
/* 307 */       break;
/*     */     case 3:
/* 309 */       EEBase.doRelease(var3.world, var3);
/*     */     case 4:
/*     */     default:
/* 312 */       break;
/*     */     case 5:
/* 314 */       EEBase.doJumpTick(var3.world, var3);
/* 315 */       break;
/*     */     case 6:
/* 317 */       EEBase.doSneakTick(var3.world, var3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void handleTEPacket(int var0, int var1, int var2, byte var3, String var4)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static void handlePedestalPacket(int var0, int var1, int var2, int var3, boolean var4)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static class TTGroup
/*     */   {
/*     */     private String groupName;
/*     */     private int masterDimension;
/*     */     private Set<Integer> dimensions;
/*     */     private Set<String> players;
/*     */ 
/*     */     public TTGroup(String name, int masterDimension)
/*     */     {
/*  71 */       this.masterDimension = masterDimension;
/*  72 */       this.groupName = name;
/*  73 */       this.dimensions = new HashSet();
/*  74 */       this.players = new HashSet();
/*  75 */       this.dimensions.add(Integer.valueOf(masterDimension));
/*     */     }
/*     */ 
/*     */     public boolean containsDimension(int dim) {
/*  79 */       return this.dimensions.contains(Integer.valueOf(dim));
/*     */     }
/*     */ 
/*     */     public String getGroupName() {
/*  83 */       return this.groupName;
/*     */     }
/*     */ 
/*     */     public int getMasterDimension() {
/*  87 */       return this.masterDimension;
/*     */     }
/*     */ 
/*     */     public void addPlayer(String player) {
/*  91 */       this.players.add(player);
/*  92 */       EEProxy.playerGroups.put(player, this);
/*     */     }
/*     */ 
/*     */     public void removePlayer(String player) {
/*  96 */       this.players.remove(player);
/*  97 */       EEProxy.playerGroups.remove(player);
/*     */     }
/*     */ 
/*     */     public void addDimensions(Integer[] dim) {
/* 101 */       this.dimensions.addAll(Arrays.asList(dim));
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     net.minecraft.server.EEProxy
 * JD-Core Version:    0.6.2
 */