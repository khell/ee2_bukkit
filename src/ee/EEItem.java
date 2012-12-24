/*    */ package ee;
/*    */ 
/*    */ import ee.item.ItemLootBall;
/*    */ import net.minecraft.server.Item;
/*    */ import net.minecraft.server.ModLoader;
/*    */ 
/*    */ public class EEItem
/*    */ {
/*    */   public static Item philStone;
/*    */   public static Item catalystStone;
/*    */   public static Item baseRing;
/*    */   public static Item attractionRing;
/*    */   public static Item grimarchRing;
/*    */   public static Item harvestRing;
/*    */   public static Item ignitionRing;
/*    */   public static Item swiftWolfRing;
/*    */   public static Item zeroRing;
/*    */   public static Item evertide;
/*    */   public static Item volcanite;
/*    */   public static Item hyperkineticLens;
/*    */   public static Item eternalDensity;
/*    */   public static Item watchOfTime;
/*    */   public static Item darkMatter;
/*    */   public static Item darkPickaxe;
/*    */   public static Item darkSpade;
/*    */   public static Item darkSword;
/*    */   public static Item darkAxe;
/*    */   public static Item darkHoe;
/*    */   public static Item darkShears;
/*    */   public static Item darkHammer;
/*    */   public static Item repairCharm;
/*    */   public static Item darkMatterArmor;
/*    */   public static Item darkMatterBoots;
/*    */   public static Item darkMatterGreaves;
/*    */   public static Item darkMatterHelmet;
/*    */   public static Item alchemicalCoal;
/*    */   public static Item mobiusFuel;
/*    */   public static Item aeternalisFuel;
/*    */   public static Item covalenceDust;
/*    */   public static Item kleinStar1;
/*    */   public static Item kleinStar2;
/*    */   public static Item kleinStar3;
/*    */   public static Item kleinStar4;
/*    */   public static Item kleinStar5;
/*    */   public static Item kleinStar6;
/*    */   public static Item alchemyBag;
/*    */   public static Item redPickaxe;
/*    */   public static Item redSpade;
/*    */   public static Item redSword;
/*    */   public static Item redAxe;
/*    */   public static Item redHoe;
/*    */   public static Item redShears;
/*    */   public static Item redHammer;
/*    */   public static Item redKatar;
/*    */   public static Item redMace;
/*    */   public static Item hyperCatalyst;
/*    */   public static Item redMatter;
/*    */   public static Item diviningRod;
/*    */   public static Item mercurialEye;
/*    */   public static Item transTablet;
/*    */   public static Item redMatterArmor;
/*    */   public static Item redMatterBoots;
/*    */   public static Item redMatterGreaves;
/*    */   public static Item redMatterHelmet;
/*    */   public static Item redMatterArmorP;
/*    */   public static Item redMatterBootsP;
/*    */   public static Item redMatterGreavesP;
/*    */   public static Item redMatterHelmetP;
/*    */   public static Item bodyStone;
/*    */   public static Item soulStone;
/*    */   public static Item lifeStone;
/*    */   public static Item mindStone;
/*    */   public static Item arcaneRing;
/*    */   public static Item voidRing;
/*    */   public static Item alchemyTome;
/*    */   public static Item lootBall;
/*    */   private static boolean initialized;
/*    */ 
/*    */   public static void init()
/*    */   {
/* 81 */     if (!initialized)
/*    */     {
/* 83 */       initialized = true;
/* 84 */       philStone = new ItemPhilosopherStone(EEBase.props.getInt("ItemPhilStone")).a(0, 0).a("philStone");
/* 85 */       catalystStone = new ItemCatalystStone(EEBase.props.getInt("ItemCatalystStone")).a(1, 0).a("catalystStone");
/* 86 */       baseRing = new ItemStackable(EEBase.props.getInt("ItemBaseRing")).a(2, 0).a("baseRing");
/* 87 */       attractionRing = new ItemAttractionRing(EEBase.props.getInt("ItemAttractionRing")).a(3, 0).a("attractionRing");
/* 88 */       grimarchRing = new ItemGrimarchRing(EEBase.props.getInt("ItemGrimarchRing")).a(5, 0).a("grimarchRing");
/* 89 */       harvestRing = new ItemHarvestRing(EEBase.props.getInt("ItemHarvestRing")).a(6, 0).a("harvestRing");
/* 90 */       ignitionRing = new ItemIgnitionRing(EEBase.props.getInt("ItemIgnitionRing")).a(8, 0).a("ignitionRing");
/* 91 */       swiftWolfRing = new ItemSwiftWolfRing(EEBase.props.getInt("ItemSwiftWolfRing")).a(10, 0).a("swiftWolfRing");
/* 92 */       zeroRing = new ItemZeroRing(EEBase.props.getInt("ItemZeroRing")).a(14, 0).a("zeroRing");
/* 93 */       evertide = new ItemEvertide(EEBase.props.getInt("ItemEvertide")).a(16, 0).a("evertide");
/* 94 */       volcanite = new ItemVolcanite(EEBase.props.getInt("ItemVolcanite")).a(17, 0).a("volcanite");
/* 95 */       hyperkineticLens = new ItemHyperkineticLens(EEBase.props.getInt("ItemHyperkineticLens")).a(18, 0).a("hyperkineticLens");
/* 96 */       eternalDensity = new ItemEternalDensity(EEBase.props.getInt("ItemEternalDensity")).a(19, 0).a("eternalDensity");
/* 97 */       watchOfTime = new ItemWatchOfTime(EEBase.props.getInt("ItemWatchOfTime")).a(21, 0).a("watchOfTime");
/* 98 */       darkMatter = new ItemStackable(EEBase.props.getInt("ItemDarkMatter")).a(23, 0).a("darkMatter");
/* 99 */       darkPickaxe = new ItemDarkPickaxe(EEBase.props.getInt("ItemDarkPickaxe")).a(24, 0).a("darkPickaxe");
/* 100 */       darkSpade = new ItemDarkSpade(EEBase.props.getInt("ItemDarkSpade")).a(25, 0).a("darkSpade");
/* 101 */       darkSword = new ItemDarkSword(EEBase.props.getInt("ItemDarkSword")).a(26, 0).a("darkSword");
/* 102 */       darkAxe = new ItemDarkAxe(EEBase.props.getInt("ItemDarkAxe")).a(27, 0).a("darkAxe");
/* 103 */       darkHoe = new ItemDarkHoe(EEBase.props.getInt("ItemDarkHoe")).a(28, 0).a("darkHoe");
/* 104 */       darkShears = new ItemDarkShears(EEBase.props.getInt("ItemDarkShears")).a(29, 0).a("darkShears");
/* 105 */       darkHammer = new ItemDarkHammer(EEBase.props.getInt("ItemDarkHammer")).a(30, 0).a("darkHammer");
/* 106 */       repairCharm = new ItemRepairCharm(EEBase.props.getInt("ItemRepairCharm")).a(31, 0).a("repairCharm");
/* 107 */       darkMatterArmor = new ItemDarkArmor(EEBase.props.getInt("ItemDarkMatterArmor"), ModLoader.addArmor("darkmatter"), 1).a(32, 0).a("darkMatterArmor");
/* 108 */       darkMatterBoots = new ItemDarkArmor(EEBase.props.getInt("ItemDarkMatterBoots"), ModLoader.addArmor("darkmatter"), 3).a(33, 0).a("darkMatterBoots");
/* 109 */       darkMatterGreaves = new ItemDarkArmor(EEBase.props.getInt("ItemDarkMatterGreaves"), ModLoader.addArmor("darkmatter"), 2).a(34, 0).a("darkMatterGreaves");
/* 110 */       darkMatterHelmet = new ItemDarkArmor(EEBase.props.getInt("ItemDarkMatterHelmet"), ModLoader.addArmor("darkmatter"), 0).a(35, 0).a("darkMatterHelmet");
/* 111 */       alchemicalCoal = new ItemStackable(EEBase.props.getInt("ItemAlchemicalCoal")).a(36, 0).a("alchemicalCoal");
/* 112 */       mobiusFuel = new ItemStackable(EEBase.props.getInt("ItemMobiusFuel")).a(37, 0).a("mobiusFuel");
/* 113 */       aeternalisFuel = new ItemStackable(EEBase.props.getInt("ItemAeternalisFuel")).a(38, 0).a("aeternalisFuel");
/* 114 */       covalenceDust = new ItemCovalenceDust(EEBase.props.getInt("ItemCovalenceDust")).a(39, 0).a("covalenceDust");
/* 115 */       kleinStar1 = new ItemKleinStar(EEBase.props.getInt("ItemKleinStar"), 1).a(42, 0).a("kleinStar1");
/* 116 */       kleinStar2 = new ItemKleinStar(EEBase.props.getInt("ItemKleinStar2"), 2).a(43, 0).a("kleinStar2");
/* 117 */       kleinStar3 = new ItemKleinStar(EEBase.props.getInt("ItemKleinStar3"), 3).a(44, 0).a("kleinStar3");
/* 118 */       kleinStar4 = new ItemKleinStar(EEBase.props.getInt("ItemKleinStar4"), 4).a(45, 0).a("kleinStar4");
/* 119 */       kleinStar5 = new ItemKleinStar(EEBase.props.getInt("ItemKleinStar5"), 5).a(46, 0).a("kleinStar5");
/* 120 */       kleinStar6 = new ItemKleinStar(EEBase.props.getInt("ItemKleinStar6"), 6).a(47, 0).a("kleinStar6");
/* 121 */       alchemyBag = new ItemAlchemyBag(EEBase.props.getInt("ItemAlchemyBag")).a(48, 0).a("alchemyBag");
/* 122 */       redPickaxe = new ItemRedPickaxe(EEBase.props.getInt("ItemRedPickaxe")).a(64, 0).a("redPickaxe");
/* 123 */       redSpade = new ItemRedSpade(EEBase.props.getInt("ItemRedSpade")).a(65, 0).a("redSpade");
/* 124 */       redSword = new ItemRedSword(EEBase.props.getInt("ItemRedSword")).a(66, 0).a("redSword");
/* 125 */       redAxe = new ItemRedAxe(EEBase.props.getInt("ItemRedAxe")).a(67, 0).a("redAxe");
/* 126 */       redHoe = new ItemRedHoe(EEBase.props.getInt("ItemRedHoe")).a(68, 0).a("redHoe");
/* 127 */       redShears = new ItemRedShears(EEBase.props.getInt("ItemRedShears")).a(69, 0).a("redShears");
/* 128 */       redHammer = new ItemRedHammer(EEBase.props.getInt("ItemRedHammer")).a(70, 0).a("redHammer");
/* 129 */       redKatar = new ItemRedKatar(EEBase.props.getInt("ItemRedKatar")).a(71, 0).a("redKatar");
/* 130 */       redMace = new ItemRedMace(EEBase.props.getInt("ItemRedMace")).a(72, 0).a("redMace");
/* 131 */       hyperCatalyst = new ItemHyperCatalyst(EEBase.props.getInt("ItemHyperCatalyst")).a(73, 0).a("hyperCatalyst");
/* 132 */       redMatter = new ItemStackable(EEBase.props.getInt("ItemRedMatter")).a(74, 0).a("redMatter");
/* 133 */       diviningRod = new ItemDiviningRod(EEBase.props.getInt("ItemDiviningRod")).a(75, 0).a("diviningRod");
/* 134 */       mercurialEye = new ItemMercurialEye(EEBase.props.getInt("ItemMercurialEye")).a(78, 0).a("mercurialEye");
/* 135 */       transTablet = new ItemTransTablet(EEBase.props.getInt("ItemTransTablet")).a(79, 0).a("transTablet");
/* 136 */       redMatterArmor = new ItemRedArmor(EEBase.props.getInt("ItemRedMatterArmor"), ModLoader.addArmor("redmatter"), 1).a(80, 0).a("redMatterArmor");
/* 137 */       redMatterBoots = new ItemRedArmor(EEBase.props.getInt("ItemRedMatterBoots"), ModLoader.addArmor("redmatter"), 3).a(81, 0).a("redMatterBoots");
/* 138 */       redMatterGreaves = new ItemRedArmor(EEBase.props.getInt("ItemRedMatterGreaves"), ModLoader.addArmor("redmatter"), 2).a(82, 0).a("redMatterGreaves");
/* 139 */       redMatterHelmet = new ItemRedArmor(EEBase.props.getInt("ItemRedMatterHelmet"), ModLoader.addArmor("redmatter"), 0).a(83, 0).a("redMatterHelmet");
/* 140 */       redMatterArmorP = new ItemRedArmorPlus(EEBase.props.getInt("ItemRedMatterArmorP"), ModLoader.addArmor("redmatterplus"), 1).a(84, 0).a("redMatterArmorP");
/* 141 */       redMatterBootsP = new ItemRedArmorPlus(EEBase.props.getInt("ItemRedMatterBootsP"), ModLoader.addArmor("redmatterplus"), 3).a(85, 0).a("redMatterBootsP");
/* 142 */       redMatterGreavesP = new ItemRedArmorPlus(EEBase.props.getInt("ItemRedMatterGreavesP"), ModLoader.addArmor("redmatterplus"), 2).a(86, 0).a("redMatterGreavesP");
/* 143 */       redMatterHelmetP = new ItemRedArmorPlus(EEBase.props.getInt("ItemRedMatterHelmetP"), ModLoader.addArmor("redmatterplus"), 0).a(87, 0).a("redMatterHelmetP");
/* 144 */       bodyStone = new ItemBodyStone(EEBase.props.getInt("ItemBodyStone")).a(88, 0).a("bodyStone");
/* 145 */       soulStone = new ItemSoulStone(EEBase.props.getInt("ItemSoulStone")).a(90, 0).a("soulStone");
/* 146 */       lifeStone = new ItemLifeStone(EEBase.props.getInt("ItemLifeStone")).a(92, 0).a("lifeStone");
/* 147 */       mindStone = new ItemMindStone(EEBase.props.getInt("ItemMindStone")).a(94, 0).a("mindStone");
/* 148 */       arcaneRing = new ItemArcaneRing(EEBase.props.getInt("ItemArcaneRing")).a(98, 0).a("arcaneRing");
/* 149 */       voidRing = new ItemVoidRing(EEBase.props.getInt("ItemVoidRing")).a(96, 0).a("voidRing");
/* 150 */       alchemyTome = new ItemStackable(EEBase.props.getInt("ItemAlchemyTome")).a(106, 0).a("alchemyTome");
/* 151 */       lootBall = new ItemLootBall(28298).a(107, 0).a("lootBall");
/* 152 */       philStone.a(philStone);
/* 153 */       evertide.a(evertide);
/* 154 */       volcanite.a(volcanite);
/* 155 */       harvestRing.a(harvestRing);
/* 156 */       zeroRing.a(zeroRing);
/* 157 */       arcaneRing.a(arcaneRing);
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEItem
 * JD-Core Version:    0.6.2
 */