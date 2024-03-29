/*    */ package ee.network;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.server.EEProxy;
/*    */ import net.minecraft.server.NetworkManager;
/*    */ 
/*    */ public class PedestalPacket extends EEPacket
/*    */ {
/*    */   int x;
/*    */   int y;
/*    */   int z;
/*    */   public int itemId;
/*    */   public boolean activated;
/*    */ 
/*    */   public PedestalPacket()
/*    */   {
/* 19 */     super(PacketTypeHandler.PEDESTAL, true);
/*    */   }
/*    */ 
/*    */   public void setCoords(int var1, int var2, int var3)
/*    */   {
/* 24 */     this.x = var1;
/* 25 */     this.y = var2;
/* 26 */     this.z = var3;
/*    */   }
/*    */ 
/*    */   public void setItem(int var1)
/*    */   {
/* 31 */     this.itemId = var1;
/*    */   }
/*    */ 
/*    */   public void setState(boolean var1)
/*    */   {
/* 36 */     this.activated = var1;
/*    */   }
/*    */ 
/*    */   public void writeData(DataOutputStream var1) throws IOException
/*    */   {
/* 41 */     var1.writeInt(this.x);
/* 42 */     var1.writeInt(this.y);
/* 43 */     var1.writeInt(this.z);
/* 44 */     var1.writeInt(this.itemId);
/* 45 */     var1.writeBoolean(this.activated);
/*    */   }
/*    */ 
/*    */   public void readData(DataInputStream var1) throws IOException
/*    */   {
/* 50 */     this.x = var1.readInt();
/* 51 */     this.y = var1.readInt();
/* 52 */     this.z = var1.readInt();
/* 53 */     this.itemId = var1.readInt();
/* 54 */     this.activated = var1.readBoolean();
/*    */   }
/*    */ 
/*    */   public void execute(NetworkManager var1)
/*    */   {
/* 59 */     EEProxy.handlePedestalPacket(this.x, this.y, this.z, this.itemId, this.activated);
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.network.PedestalPacket
 * JD-Core Version:    0.6.2
 */