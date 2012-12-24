/*    */ package ee.network;
/*    */ 
/*    */ import forge.IConnectionHandler;
/*    */ import forge.IPacketHandler;
/*    */ import forge.MessageManager;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.DataInputStream;
/*    */ import net.minecraft.server.NetworkManager;
/*    */ import net.minecraft.server.Packet;
/*    */ import net.minecraft.server.Packet1Login;
/*    */ 
/*    */ public class PacketHandler
/*    */   implements IPacketHandler, IConnectionHandler
/*    */ {
/*    */   public void onConnect(NetworkManager var1)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void onLogin(NetworkManager var1, Packet1Login var2)
/*    */   {
/* 18 */     MessageManager.getInstance().registerChannel(var1, this, "EE2");
/*    */   }
/*    */ 
/*    */   public void onDisconnect(NetworkManager var1, String var2, Object[] var3)
/*    */   {
/* 23 */     MessageManager.getInstance().removeConnection(var1);
/*    */   }
/*    */ 
/*    */   public void onPacketData(NetworkManager var1, String var2, byte[] var3)
/*    */   {
/* 28 */     new DataInputStream(new ByteArrayInputStream(var3));
/* 29 */     EEPacket var5 = PacketTypeHandler.buildPacket(var3);
/* 30 */     var5.execute(var1);
/*    */   }
/*    */ 
/*    */   public static Packet getPacketForSending(EEPacket var0)
/*    */   {
/* 35 */     return PacketTypeHandler.populatePacket(var0);
/*    */   }
/*    */ 
/*    */   public static EEPacket getPacket(PacketTypeHandler var0)
/*    */   {
/* 40 */     EEPacket var1 = PacketTypeHandler.buildPacket(var0);
/* 41 */     return var1;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.network.PacketHandler
 * JD-Core Version:    0.6.2
 */