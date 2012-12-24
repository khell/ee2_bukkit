/*    */ package ee.network;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.server.NetworkManager;
/*    */ 
/*    */ public class EEPacket
/*    */ {
/*    */   protected PacketTypeHandler packetType;
/*    */   protected boolean isChunkDataPacket;
/*    */ 
/*    */   public EEPacket(PacketTypeHandler var1, boolean var2)
/*    */   {
/* 16 */     this.packetType = var1;
/* 17 */     this.isChunkDataPacket = var2;
/*    */   }
/*    */ 
/*    */   public byte[] populate()
/*    */   {
/* 22 */     ByteArrayOutputStream var1 = new ByteArrayOutputStream();
/* 23 */     DataOutputStream var2 = new DataOutputStream(var1);
/*    */     try
/*    */     {
/* 27 */       var2.writeByte(this.packetType.ordinal());
/* 28 */       writeData(var2);
/*    */     }
/*    */     catch (IOException var4)
/*    */     {
/* 32 */       var4.printStackTrace();
/*    */     }
/*    */ 
/* 35 */     return var1.toByteArray();
/*    */   }
/*    */ 
/*    */   public void readPopulate(DataInputStream var1)
/*    */   {
/*    */     try
/*    */     {
/* 42 */       readData(var1);
/*    */     }
/*    */     catch (IOException var3)
/*    */     {
/* 46 */       var3.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void execute(NetworkManager var1)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void writeData(DataOutputStream var1)
/*    */     throws IOException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void readData(DataInputStream var1)
/*    */     throws IOException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setKey(int var1)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setCoords(int var1, int var2, int var3)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setOrientation(byte var1)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setPlayerName(String var1)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setItem(int var1)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setState(boolean var1)
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.network.EEPacket
 * JD-Core Version:    0.6.2
 */