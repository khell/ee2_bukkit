/*    */ package ee;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.server.ItemStack;
/*    */ 
/*    */ public class ItemCovalenceDust extends ItemStackable
/*    */ {
/*    */   public ItemCovalenceDust(int var1)
/*    */   {
/* 10 */     super(var1);
/* 11 */     a(true);
/* 12 */     setMaxDurability(0);
/*    */   }
/*    */ 
/*    */   public int getIconFromDamage(int var1)
/*    */   {
/* 17 */     return var1 < 3 ? this.textureId + var1 : this.textureId;
/*    */   }
/*    */ 
/*    */   public void addCreativeItems(ArrayList var1)
/*    */   {
/* 22 */     var1.add(new ItemStack(EEItem.covalenceDust, 1, 0));
/* 23 */     var1.add(new ItemStack(EEItem.covalenceDust, 1, 1));
/* 24 */     var1.add(new ItemStack(EEItem.covalenceDust, 1, 2));
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemCovalenceDust
 * JD-Core Version:    0.6.2
 */