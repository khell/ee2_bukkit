package ee;

public abstract interface IEEPowerNet
{
  public static final boolean pullingPower = false;

  public abstract boolean receiveEnergy(int paramInt, byte paramByte, boolean paramBoolean);

  public abstract boolean sendEnergy(int paramInt, byte paramByte, boolean paramBoolean);

  public abstract boolean passEnergy(int paramInt, byte paramByte, boolean paramBoolean);

  public abstract void sendAllPackets(int paramInt);

  public abstract int relayBonus();
}

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.IEEPowerNet
 * JD-Core Version:    0.6.2
 */