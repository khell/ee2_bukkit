/*    */ package ee;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import net.minecraft.server.Item;
/*    */ 
/*    */ public class EEMergeLib
/*    */ {
/*  8 */   public static HashSet mergeOnCraft = new HashSet();
/*  9 */   public static HashSet destroyOnCraft = new HashSet();
/*    */ 
/*    */   public static void addMergeOnCraft(Item var0)
/*    */   {
/* 13 */     mergeOnCraft.add(Integer.valueOf(var0.id));
/*    */   }
/*    */ 
/*    */   public static void addDestroyOnCraft(Item var0)
/*    */   {
/* 18 */     destroyOnCraft.add(Integer.valueOf(var0.id));
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEMergeLib
 * JD-Core Version:    0.6.2
 */