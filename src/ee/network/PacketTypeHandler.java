/*    */ package ee.network;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.DataInputStream;
/*    */ import net.minecraft.server.Packet;
/*    */ import net.minecraft.server.Packet250CustomPayload;
/*    */ 
/*    */ public enum PacketTypeHandler
/*    */ {
/* 10 */   KEY(KeyPressedPacket.class), 
/* 11 */   TILE(TileEntityPacket.class), 
/* 12 */   PEDESTAL(PedestalPacket.class);
/*    */ 
/*    */   private Class clazz;
/*    */ 
/*    */   private PacketTypeHandler(Class var3) {
/* 17 */     this.clazz = var3;
/*    */   }
/*    */ 
/*    */   public static EEPacket buildPacket(byte[] var0)
/*    */   {
/* 22 */     ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
/* 23 */     int var2 = var1.read();
/* 24 */     DataInputStream var3 = new DataInputStream(var1);
/* 25 */     EEPacket var4 = null;
/*    */     try
/*    */     {
/* 29 */       var4 = (EEPacket)values()[var2].clazz.newInstance();
/*    */     }
/*    */     catch (Exception var6)
/*    */     {
/* 33 */       var6.printStackTrace();
/*    */     }
/*    */ 
/* 36 */     var4.readPopulate(var3);
/* 37 */     return var4;
/*    */   }
/*    */ 
/*    */   public static EEPacket buildPacket(PacketTypeHandler var0)
/*    */   {
/* 42 */     EEPacket var1 = null;
/*    */     try
/*    */     {
/* 46 */       var1 = (EEPacket)values()[var0.ordinal()].clazz.newInstance();
/*    */     }
/*    */     catch (Exception var3)
/*    */     {
/* 50 */       var3.printStackTrace();
/*    */     }
/*    */ 
/* 53 */     return var1;
/*    */   }
/*    */ 
/*    */   public static Packet populatePacket(EEPacket var0)
/*    */   {
/* 58 */     byte[] var1 = var0.populate();
/* 59 */     Packet250CustomPayload var2 = new Packet250CustomPayload();
/* 60 */     var2.tag = "EE2";
/* 61 */     var2.data = var1;
/* 62 */     var2.length = var1.length;
/* 63 */     var2.lowPriority = var0.isChunkDataPacket;
/* 64 */     return var2;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.network.PacketTypeHandler
 * JD-Core Version:    0.6.2
 */