/*    */ package ee.core;
/*    */ 
/*    */ public class Keys
/*    */ {
/*    */   public static final int EXTRA = 0;
/*    */   public static final int CHARGE = 1;
/*    */   public static final int TOGGLE = 2;
/*    */   public static final int RELEASE = 3;
/*    */   public static final int LEFT_CLICK = 4;
/*    */   public static final int JUMP = 5;
/*    */   public static final int SNEAK = 6;
/*    */ 
/*    */   public static String toString(int var0)
/*    */   {
/* 15 */     switch (var0)
/*    */     {
/*    */     case 0:
/* 18 */       return "KEYS.EXTRA";
/*    */     case 1:
/* 20 */       return "KEYS.CHARGE";
/*    */     case 2:
/* 22 */       return "KEYS.TOGGLE";
/*    */     case 3:
/* 24 */       return "KEYS.RELEASE";
/*    */     case 4:
/* 26 */       return "KEYS.LEFT_CLICK";
/*    */     case 5:
/* 28 */       return "KEYS.JUMP";
/*    */     case 6:
/* 30 */       return "KEYS.SNEAK";
/*    */     }
/* 32 */     return "Unknown Key";
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.core.Keys
 * JD-Core Version:    0.6.2
 */