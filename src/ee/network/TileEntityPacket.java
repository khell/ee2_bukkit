/*    */ package ee.network;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.server.EEProxy;
/*    */ import net.minecraft.server.NetworkManager;
/*    */ 
/*    */ public class TileEntityPacket extends EEPacket
/*    */ {
/*    */   public int x;
/*    */   public int y;
/*    */   public int z;
/*    */   public byte direction;
/*    */   String player;
/*    */ 
/*    */   public TileEntityPacket()
/*    */   {
/* 19 */     super(PacketTypeHandler.TILE, true);
/*    */   }
/*    */ 
/*    */   public void setCoords(int var1, int var2, int var3)
/*    */   {
/* 24 */     this.x = var1;
/* 25 */     this.y = var2;
/* 26 */     this.z = var3;
/*    */   }
/*    */ 
/*    */   public void setOrientation(byte var1)
/*    */   {
/* 31 */     this.direction = var1;
/*    */   }
/*    */ 
/*    */   public void setPlayerName(String var1)
/*    */   {
/* 36 */     this.player = var1;
/*    */   }
/*    */ 
/*    */   public void writeData(DataOutputStream var1) throws IOException
/*    */   {
/* 41 */     var1.writeInt(this.x);
/* 42 */     var1.writeInt(this.y);
/* 43 */     var1.writeInt(this.z);
/* 44 */     var1.writeByte(this.direction);
/* 45 */     if (this.player != null)
/* 46 */       var1.writeUTF(this.player);
/*    */     else
/* 48 */       var1.writeUTF("");
/*    */   }
/*    */ 
/*    */   public void readData(DataInputStream var1)
/*    */     throws IOException
/*    */   {
/* 54 */     this.x = var1.readInt();
/* 55 */     this.y = var1.readInt();
/* 56 */     this.z = var1.readInt();
/* 57 */     this.direction = var1.readByte();
/* 58 */     this.player = var1.readUTF();
/*    */   }
/*    */ 
/*    */   public void execute(NetworkManager var1)
/*    */   {
/* 63 */     EEProxy.handleTEPacket(this.x, this.y, this.z, this.direction, this.player);
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.network.TileEntityPacket
 * JD-Core Version:    0.6.2
 */