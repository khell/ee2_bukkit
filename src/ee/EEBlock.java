/*     */ package ee;
/*     */ 
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.ModLoader;
/*     */ 
/*     */ public class EEBlock
/*     */ {
/*     */   public static BlockEEStone eeStone;
/*     */   public static BlockEEPedestal eePedestal;
/*     */   public static BlockEETorch eeTorch;
/*     */   public static BlockEEChest eeChest;
/*     */   public static BlockEEDevice eeDevice;
/*     */   public static ItemStack alchChest;
/*     */   public static ItemStack collector;
/*     */   public static ItemStack condenser;
/*     */   public static ItemStack dmFurnace;
/*     */   public static ItemStack relay;
/*     */   public static ItemStack dmBlock;
/*     */   public static ItemStack rmBlock;
/*     */   public static ItemStack collector2;
/*     */   public static ItemStack collector3;
/*     */   public static ItemStack relay2;
/*     */   public static ItemStack relay3;
/*     */   public static ItemStack rmFurnace;
/*     */   public static ItemStack pedestal;
/*     */   public static ItemStack iTorch;
/*     */   public static ItemStack novaCatalyst;
/*     */   public static ItemStack novaCataclysm;
/*     */   public static ItemStack transTablet;
/*     */   private static boolean initialized;
/*     */ 
/*     */   public static void init()
/*     */   {
/*  36 */     if (!initialized)
/*     */     {
/*  38 */       initialized = true;
/*  39 */       eeStone = new BlockEEStone(EEBase.props.getInt("BlockEEStone"));
/*  40 */       eePedestal = new BlockEEPedestal(EEBase.props.getInt("BlockEEPedestal"));
/*  41 */       eeTorch = new BlockEETorch(EEBase.props.getInt("BlockEETorch"));
/*  42 */       eeChest = new BlockEEChest(EEBase.props.getInt("BlockEEChest"));
/*  43 */       eeDevice = new BlockEEDevice(EEBase.props.getInt("BlockEEDevice"));
/*  44 */       eeStone.a("eeStone");
/*  45 */       eePedestal.a("eePedestal");
/*  46 */       eeTorch.a("eeTorch");
/*  47 */       eeChest.a("eeChest");
/*  48 */       eeDevice.a("eeDevice");
/*  49 */       eeStone.addTileEntityMapping(0, TileCollector.class);
/*  50 */       eeStone.addTileEntityMapping(1, TileCollector2.class);
/*  51 */       eeStone.addTileEntityMapping(2, TileCollector3.class);
/*  52 */       eeStone.addTileEntityMapping(3, TileDMFurnace.class);
/*  53 */       eeStone.addTileEntityMapping(4, TileRMFurnace.class);
/*  54 */       eeStone.addTileEntityMapping(5, TileRelay.class);
/*  55 */       eeStone.addTileEntityMapping(6, TileRelay2.class);
/*  56 */       eeStone.addTileEntityMapping(7, TileRelay3.class);
/*  57 */       eeStone.addTileEntityMapping(8, TileDMBlock.class);
/*  58 */       eeStone.addTileEntityMapping(9, TileRMBlock.class);
/*  59 */       eeStone.addTileEntityMapping(10, TileNovaCatalyst.class);
/*  60 */       eeStone.addTileEntityMapping(11, TileNovaCataclysm.class);
/*  61 */       eeChest.addTileEntityMapping(0, TileAlchChest.class);
/*  62 */       eeChest.addTileEntityMapping(1, TileCondenser.class);
/*  63 */       eeDevice.addTileEntityMapping(0, TileTransTablet.class);
/*  64 */       ModLoader.registerBlock(eeStone, ItemBlockEEStone.class);
/*  65 */       ModLoader.registerBlock(eeTorch, ItemBlockEETorch.class);
/*  66 */       ModLoader.registerBlock(eePedestal, ItemBlockEEPedestal.class);
/*  67 */       ModLoader.registerBlock(eeChest, ItemBlockEEChest.class);
/*  68 */       ModLoader.registerBlock(eeDevice, ItemBlockEEDevice.class);
/*  69 */       ModLoader.registerTileEntity(TileAlchChest.class, "Alchemical Chest");
/*  70 */       ModLoader.registerTileEntity(TileCollector.class, "Energy Collector");
/*  71 */       ModLoader.registerTileEntity(TileCollector2.class, "Energy Collector MK2");
/*  72 */       ModLoader.registerTileEntity(TileCollector3.class, "Energy Collector MK3");
/*  73 */       ModLoader.registerTileEntity(TileRelay.class, "Antimatter Array");
/*  74 */       ModLoader.registerTileEntity(TileRelay2.class, "Antimatter Array MK2");
/*  75 */       ModLoader.registerTileEntity(TileRelay3.class, "Antimatter Array MK3");
/*  76 */       ModLoader.registerTileEntity(TileCondenser.class, "Energy Condenser");
/*  77 */       ModLoader.registerTileEntity(TileDMBlock.class, "DM Block");
/*  78 */       ModLoader.registerTileEntity(TileRMBlock.class, "RM Block");
/*  79 */       ModLoader.registerTileEntity(TilePedestal.class, "Pedestal");
/*  80 */       ModLoader.registerTileEntity(TileDMFurnace.class, "Dark Matter Furnace");
/*  81 */       ModLoader.registerTileEntity(TileRMFurnace.class, "Red Matter Furnace");
/*  82 */       ModLoader.registerTileEntity(TileNovaCatalyst.class, "Nova Catalyst");
/*  83 */       ModLoader.registerTileEntity(TileNovaCataclysm.class, "Nova Cataclysm");
/*  84 */       ModLoader.registerTileEntity(TileTransTablet.class, "Transmutation Tablet");
/*  85 */       eeStone.setItemName(0, "collector");
/*  86 */       eeStone.setItemName(1, "collector2");
/*  87 */       eeStone.setItemName(2, "collector3");
/*  88 */       eeStone.setItemName(3, "dmFurnace");
/*  89 */       eeStone.setItemName(4, "rmFurnace");
/*  90 */       eeStone.setItemName(5, "relay");
/*  91 */       eeStone.setItemName(6, "relay2");
/*  92 */       eeStone.setItemName(7, "relay3");
/*  93 */       eeStone.setItemName(8, "dmBlock");
/*  94 */       eeStone.setItemName(9, "rmBlock");
/*  95 */       eeStone.setItemName(10, "novaCatalyst");
/*  96 */       eeStone.setItemName(11, "novaCataclysm");
/*  97 */       eeTorch.setItemName(0, "iTorch");
/*  98 */       eePedestal.setItemName(0, "pedestal");
/*  99 */       eeChest.setItemName(0, "alchChest");
/* 100 */       eeChest.setItemName(1, "condenser");
/* 101 */       eeDevice.setItemName(0, "transTablet");
/* 102 */       collector = new ItemStack(eeStone, 1, 0);
/* 103 */       collector2 = new ItemStack(eeStone, 1, 1);
/* 104 */       collector3 = new ItemStack(eeStone, 1, 2);
/* 105 */       dmFurnace = new ItemStack(eeStone, 1, 3);
/* 106 */       rmFurnace = new ItemStack(eeStone, 1, 4);
/* 107 */       relay = new ItemStack(eeStone, 1, 5);
/* 108 */       relay2 = new ItemStack(eeStone, 1, 6);
/* 109 */       relay3 = new ItemStack(eeStone, 1, 7);
/* 110 */       dmBlock = new ItemStack(eeStone, 1, 8);
/* 111 */       rmBlock = new ItemStack(eeStone, 1, 9);
/* 112 */       novaCatalyst = new ItemStack(eeStone, 1, 10);
/* 113 */       novaCataclysm = new ItemStack(eeStone, 1, 11);
/* 114 */       pedestal = new ItemStack(eePedestal, 1, 0);
/* 115 */       iTorch = new ItemStack(eeTorch, 1, 0);
/* 116 */       alchChest = new ItemStack(eeChest, 1, 0);
/* 117 */       condenser = new ItemStack(eeChest, 1, 1);
/* 118 */       transTablet = new ItemStack(eeDevice, 1, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int blockDamageDropped(Block var0, int var1)
/*     */   {
/* 124 */     return EEProxy.blockDamageDropped(var0, var1);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEBlock
 * JD-Core Version:    0.6.2
 */