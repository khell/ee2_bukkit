/*    */ package ee.network;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.server.EEProxy;
/*    */ import net.minecraft.server.NetworkManager;
/*    */ 
/*    */ public class KeyPressedPacket extends EEPacket
/*    */ {
/*    */   public int key;
/*    */ 
/*    */   public KeyPressedPacket()
/*    */   {
/* 15 */     super(PacketTypeHandler.KEY, false);
/*    */   }
/*    */ 
/*    */   public void writeData(DataOutputStream var1) throws IOException
/*    */   {
/* 20 */     var1.writeInt(this.key);
/*    */   }
/*    */ 
/*    */   public void readData(DataInputStream var1) throws IOException
/*    */   {
/* 25 */     this.key = var1.readInt();
/*    */   }
/*    */ 
/*    */   public void setKey(int var1)
/*    */   {
/* 30 */     this.key = var1;
/*    */   }
/*    */ 
/*    */   public void execute(NetworkManager var1)
/*    */   {
/* 35 */     EEProxy.handleControl(var1, this.key);
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.network.KeyPressedPacket
 * JD-Core Version:    0.6.2
 */