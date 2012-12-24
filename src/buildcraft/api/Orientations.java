/*    */ package buildcraft.api;
/*    */ 
/*    */ public enum Orientations
/*    */ {
/*  5 */   YNeg, 
/*  6 */   YPos, 
/*  7 */   ZNeg, 
/*  8 */   ZPos, 
/*  9 */   XNeg, 
/* 10 */   XPos, 
/* 11 */   Unknown;
/*    */ 
/*    */   public Orientations reverse()
/*    */   {
/* 15 */     switch (this)
/*    */     {
/*    */     case XNeg:
/* 18 */       return YNeg;
/*    */     case Unknown:
/* 20 */       return YPos;
/*    */     case YNeg:
/* 22 */       return ZNeg;
/*    */     case XPos:
/* 24 */       return ZPos;
/*    */     case ZNeg:
/* 26 */       return XNeg;
/*    */     case YPos:
/* 28 */       return XPos;
/*    */     }
/* 30 */     return Unknown;
/*    */   }
/*    */ 
/*    */   public Orientations rotateLeft()
/*    */   {
/* 36 */     switch (this)
/*    */     {
/*    */     case YNeg:
/* 39 */       return XNeg;
/*    */     case XPos:
/* 41 */       return XPos;
/*    */     case ZNeg:
/* 43 */       return ZPos;
/*    */     case YPos:
/* 45 */       return ZNeg;
/*    */     }
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public static Orientations[] dirs()
/*    */   {
/* 53 */     return new Orientations[] { YNeg, YPos, ZNeg, ZPos, XNeg, XPos };
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     buildcraft.api.Orientations
 * JD-Core Version:    0.6.2
 */