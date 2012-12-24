/*    */ package ee;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.server.ItemBlock;
/*    */ import net.minecraft.server.ItemStack;
/*    */ 
/*    */ public class ItemBlockEETorch extends ItemBlock
/*    */ {
/*  9 */   HashMap names = new HashMap();
/*    */ 
/*    */   public ItemBlockEETorch(int var1)
/*    */   {
/* 13 */     super(var1);
/* 14 */     setMaxDurability(0);
/* 15 */     a(true);
/*    */   }
/*    */ 
/*    */   public int filterData(int var1)
/*    */   {
/* 23 */     return var1;
/*    */   }
/*    */ 
/*    */   public void setMetaName(int var1, String var2)
/*    */   {
/* 28 */     this.names.put(Integer.valueOf(var1), var2);
/*    */   }
/*    */ 
/*    */   public String a(ItemStack var1)
/*    */   {
/* 33 */     String var2 = (String)this.names.get(Integer.valueOf(var1.getData()));
/*    */ 
/* 35 */     if (var2 == null)
/*    */     {
/* 37 */       throw new IndexOutOfBoundsException();
/*    */     }
/*    */ 
/* 41 */     return var2;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemBlockEETorch
 * JD-Core Version:    0.6.2
 */