/*     */ package ee;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public final class EEProps
/*     */ {
/*     */   private String field_26620_fileName;
/*  24 */   private List field_26618_lines = new ArrayList();
/*  25 */   private Map field_26619_props = new HashMap();
/*     */ 
/*     */   public EEProps(String var1)
/*     */   {
/*  29 */     this.field_26620_fileName = var1;
/*  30 */     File var2 = new File(this.field_26620_fileName);
/*     */ 
/*  32 */     if (var2.exists())
/*     */     {
/*     */       try
/*     */       {
/*  36 */         func_26600_load();
/*     */       }
/*     */       catch (IOException var4)
/*     */       {
/*  40 */         System.out.println("[Props] Unable to load " + this.field_26620_fileName + "!");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  45 */       func_26596_save();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void func_26600_load() throws IOException
/*     */   {
/*  51 */     BufferedReader var1 = new BufferedReader(new InputStreamReader(new FileInputStream(this.field_26620_fileName), "UTF8"));
/*  52 */     this.field_26618_lines.clear();
/*  53 */     this.field_26619_props.clear();
/*     */     String var2;
/*  56 */     while ((var2 = var1.readLine()) != null)
/*     */     {
/*  58 */       String var2 = new String(var2.getBytes(), "UTF-8");
/*  59 */       boolean var3 = false;
/*     */ 
/*  61 */       char var14 = '\000';
/*     */ 
/*  63 */       for (int var4 = 0; (var4 < var2.length()) && (Character.isWhitespace(var14 = var2.charAt(var4))); var4++);
/*  68 */       if ((var2.length() - var4 != 0) && (var2.charAt(var4) != '#') && (var2.charAt(var4) != '!'))
/*     */       {
/*  70 */         boolean var6 = var2.indexOf('\\', var4) != -1;
/*  71 */         StringBuffer var7 = var6 ? new StringBuffer() : null;
/*     */ 
/*  73 */         if (var7 != null)
/*     */         {
/*  75 */           while ((var4 < var2.length()) && (!Character.isWhitespace(var14 = var2.charAt(var4++))) && (var14 != '=') && (var14 != ':'))
/*     */           {
/*  77 */             if ((var6) && (var14 == '\\'))
/*     */             {
/*  79 */               if (var4 == var2.length())
/*     */               {
/*  81 */                 var2 = var1.readLine();
/*     */ 
/*  83 */                 if (var2 == null)
/*     */                 {
/*  85 */                   var2 = "";
/*     */                 }
/*     */ 
/*  88 */                 var4 = 0;
/*     */                 do
/*     */                 {
/*  92 */                   var4++;
/*     */ 
/*  94 */                   if (var4 >= var2.length()) break;  } while (Character.isWhitespace(var14 = var2.charAt(var4)));
/*     */               }
/*     */               else
/*     */               {
/* 102 */                 var14 = var2.charAt(var4++);
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 107 */               switch (var14)
/*     */               {
/*     */               case 'n':
/* 110 */                 var7.append('\n');
/* 111 */                 break;
/*     */               case 'o':
/*     */               case 'p':
/*     */               case 'q':
/*     */               case 's':
/*     */               default:
/* 117 */                 var7.append('\000');
/* 118 */                 break;
/*     */               case 'r':
/* 120 */                 var7.append('\r');
/* 121 */                 break;
/*     */               case 't':
/* 123 */                 var7.append('\t');
/* 124 */                 break;
/*     */               case 'u':
/* 127 */                 if (var4 + 4 <= var2.length())
/*     */                 {
/* 129 */                   char var8 = (char)Integer.parseInt(var2.substring(var4, var4 + 4), 16);
/* 130 */                   var7.append(var8);
/* 131 */                   var4 += 4;
/*     */                 }
/*     */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 138 */         boolean var15 = (var14 == ':') || (var14 == '=');
/*     */         String var9;
/*     */         String var9;
/* 141 */         if (var6)
/*     */         {
/* 143 */           var9 = var7.toString();
/*     */         }
/*     */         else
/*     */         {
/*     */           String var9;
/* 145 */           if ((!var15) && (!Character.isWhitespace(var14)))
/*     */           {
/* 147 */             var9 = var2.substring(var4, var4);
/*     */           }
/*     */           else
/*     */           {
/* 151 */             var9 = var2.substring(var4, var4 - 1);
/*     */           }
/*     */         }
/* 154 */         while ((var4 < var2.length()) && (Character.isWhitespace(var14 = var2.charAt(var4))))
/*     */         {
/* 156 */           var4++;
/*     */         }
/*     */ 
/* 159 */         if ((!var15) && ((var14 == ':') || (var14 == '=')))
/*     */         {
/* 161 */           var4++;
/*     */ 
/* 163 */           while ((var4 < var2.length()) && (Character.isWhitespace(var2.charAt(var4))))
/*     */           {
/* 165 */             var4++;
/*     */           }
/*     */         }
/*     */ 
/* 169 */         if (!var6)
/*     */         {
/* 171 */           this.field_26618_lines.add(var2);
/*     */         }
/*     */         else
/*     */         {
/* 175 */           StringBuilder var10 = new StringBuilder(var2.length() - var4);
/*     */ 
/* 177 */           while (var4 < var2.length())
/*     */           {
/* 179 */             char var11 = var2.charAt(var4++);
/*     */ 
/* 181 */             if (var11 == '\\')
/*     */             {
/* 183 */               if (var4 == var2.length())
/*     */               {
/* 185 */                 var2 = var1.readLine();
/*     */ 
/* 187 */                 if (var2 == null)
/*     */                 {
/*     */                   break;
/*     */                 }
/*     */ 
/* 192 */                 for (var4 = 0; (var4 < var2.length()) && (Character.isWhitespace(var2.charAt(var4))); var4++);
/* 197 */                 var10.ensureCapacity(var2.length() - var4 + var10.length());
/* 198 */                 continue;
/*     */               }
/*     */ 
/* 201 */               char var12 = var2.charAt(var4++);
/*     */ 
/* 203 */               switch (var12)
/*     */               {
/*     */               case 'n':
/* 206 */                 var10.append('\n');
/* 207 */                 break;
/*     */               case 'o':
/*     */               case 'p':
/*     */               case 'q':
/*     */               case 's':
/*     */               default:
/* 213 */                 var10.append('\000');
/* 214 */                 break;
/*     */               case 'r':
/* 216 */                 var10.append('\r');
/* 217 */                 break;
/*     */               case 't':
/* 219 */                 var10.append('\t');
/* 220 */                 break;
/*     */               case 'u':
/* 223 */                 if (var4 + 4 > var2.length())
/*     */                 {
/*     */                   continue;
/*     */                 }
/*     */ 
/* 228 */                 char var13 = (char)Integer.parseInt(var2.substring(var4, var4 + 4), 16);
/* 229 */                 var10.append(var13);
/* 230 */                 var4 += 4;
/*     */               }
/*     */             }
/*     */ 
/* 234 */             var10.append('\000');
/*     */           }
/*     */ 
/* 237 */           this.field_26618_lines.add(var9 + "=" + var10.toString());
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 242 */         this.field_26618_lines.add(var2);
/*     */       }
/*     */     }
/*     */ 
/* 246 */     var1.close();
/*     */   }
/*     */ 
/*     */   public void func_26596_save()
/*     */   {
/* 251 */     FileOutputStream var1 = null;
/*     */     try
/*     */     {
/* 255 */       var1 = new FileOutputStream(this.field_26620_fileName);
/*     */     }
/*     */     catch (FileNotFoundException var11)
/*     */     {
/* 259 */       System.out.println("[Props] Unable to open " + this.field_26620_fileName + "!");
/*     */     }
/*     */ 
/* 262 */     PrintStream var2 = null;
/*     */     try
/*     */     {
/* 266 */       var2 = new PrintStream(var1, true, "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException var10)
/*     */     {
/* 270 */       System.out.println("[Props] Unable to write to " + this.field_26620_fileName + "!");
/*     */     }
/*     */ 
/* 273 */     ArrayList var3 = new ArrayList();
/* 274 */     Iterator var4 = this.field_26618_lines.iterator();
/*     */ 
/* 276 */     while (var4.hasNext())
/*     */     {
/* 278 */       String var5 = (String)var4.next();
/*     */ 
/* 280 */       if (var5.trim().length() == 0)
/*     */       {
/* 282 */         var2.println(var5);
/*     */       }
/* 284 */       else if (var5.charAt(0) == '#')
/*     */       {
/* 286 */         var2.println(var5);
/*     */       }
/* 288 */       else if (var5.contains("="))
/*     */       {
/* 290 */         int var6 = var5.indexOf('=');
/* 291 */         String var7 = var5.substring(0, var6).trim();
/*     */ 
/* 293 */         if (this.field_26619_props.containsKey(var7))
/*     */         {
/* 295 */           String var8 = (String)this.field_26619_props.get(var7);
/* 296 */           var2.println(var7 + "=" + var8);
/* 297 */           var3.add(var7);
/*     */         }
/*     */         else
/*     */         {
/* 301 */           var2.println(var5);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 306 */         var2.println(var5);
/*     */       }
/*     */     }
/*     */ 
/* 310 */     var4 = this.field_26619_props.entrySet().iterator();
/*     */ 
/* 312 */     while (var4.hasNext())
/*     */     {
/* 314 */       Map.Entry var12 = (Map.Entry)var4.next();
/*     */ 
/* 316 */       if (!var3.contains(var12.getKey()))
/*     */       {
/* 318 */         var2.println((String)var12.getKey() + "=" + (String)var12.getValue());
/*     */       }
/*     */     }
/*     */ 
/* 322 */     var2.close();
/*     */     try
/*     */     {
/* 326 */       this.field_26619_props.clear();
/* 327 */       this.field_26618_lines.clear();
/* 328 */       func_26600_load();
/*     */     }
/*     */     catch (IOException var9)
/*     */     {
/* 332 */       System.out.println("[Props] Unable to load " + this.field_26620_fileName + "!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map func_26604_returnMap() throws Exception
/*     */   {
/* 338 */     HashMap var1 = new HashMap();
/* 339 */     BufferedReader var2 = new BufferedReader(new FileReader(this.field_26620_fileName));
/*     */     String var3;
/* 342 */     while ((var3 = var2.readLine()) != null)
/*     */     {
/*     */       String var3;
/* 344 */       if ((var3.trim().length() != 0) && (var3.charAt(0) != '#') && (var3.contains("=")))
/*     */       {
/* 346 */         int var4 = var3.indexOf('=');
/* 347 */         String var5 = var3.substring(0, var4).trim();
/* 348 */         String var6 = var3.substring(var4 + 1).trim();
/* 349 */         var1.put(var5, var6);
/*     */       }
/*     */     }
/*     */ 
/* 353 */     var2.close();
/* 354 */     return var1;
/*     */   }
/*     */ 
/*     */   public boolean func_26610_containsKey(String var1)
/*     */   {
/* 359 */     Iterator var2 = this.field_26618_lines.iterator();
/*     */ 
/* 361 */     while (var2.hasNext())
/*     */     {
/* 363 */       String var3 = (String)var2.next();
/*     */ 
/* 365 */       if ((var3.trim().length() != 0) && (var3.charAt(0) != '#') && (var3.contains("=")))
/*     */       {
/* 367 */         int var4 = var3.indexOf('=');
/* 368 */         String var5 = var3.substring(0, var4);
/*     */ 
/* 370 */         if (var5.equals(var1))
/*     */         {
/* 372 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 377 */     return false;
/*     */   }
/*     */ 
/*     */   public String func_26615_getProperty(String var1)
/*     */   {
/* 382 */     Iterator var2 = this.field_26618_lines.iterator();
/*     */ 
/* 384 */     while (var2.hasNext())
/*     */     {
/* 386 */       String var3 = (String)var2.next();
/*     */ 
/* 388 */       if ((var3.trim().length() != 0) && (var3.charAt(0) != '#') && (var3.contains("=")))
/*     */       {
/* 390 */         int var4 = var3.indexOf('=');
/* 391 */         String var5 = var3.substring(0, var4).trim();
/* 392 */         String var6 = var3.substring(var4 + 1);
/*     */ 
/* 394 */         if (var5.equals(var1))
/*     */         {
/* 396 */           return var6;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 401 */     return "";
/*     */   }
/*     */ 
/*     */   public void func_26606_removeKey(String var1)
/*     */   {
/* 406 */     Boolean var2 = Boolean.valueOf(false);
/*     */ 
/* 408 */     if (this.field_26619_props.containsKey(var1))
/*     */     {
/* 410 */       this.field_26619_props.remove(var1);
/* 411 */       var2 = Boolean.valueOf(true);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 416 */       for (int var3 = 0; var3 < this.field_26618_lines.size(); var3++)
/*     */       {
/* 418 */         String var4 = (String)this.field_26618_lines.get(var3);
/*     */ 
/* 420 */         if ((var4.trim().length() != 0) && (var4.charAt(0) != '#') && (var4.contains("=")))
/*     */         {
/* 422 */           int var5 = var4.indexOf('=');
/* 423 */           String var6 = var4.substring(0, var5).trim();
/*     */ 
/* 425 */           if (var6.equals(var1))
/*     */           {
/* 427 */             this.field_26618_lines.remove(var3);
/* 428 */             var2 = Boolean.valueOf(true);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ConcurrentModificationException var7)
/*     */     {
/* 435 */       func_26606_removeKey(var1);
/* 436 */       return;
/*     */     }
/*     */ 
/* 439 */     if (var2.booleanValue())
/*     */     {
/* 441 */       func_26596_save();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean func_26611_keyExists(String var1)
/*     */   {
/*     */     try
/*     */     {
/* 449 */       return func_26610_containsKey(var1);
/*     */     }
/*     */     catch (Exception var3) {
/*     */     }
/* 453 */     return false;
/*     */   }
/*     */ 
/*     */   public String func_26614_getString(String var1)
/*     */   {
/* 459 */     return func_26610_containsKey(var1) ? func_26615_getProperty(var1) : "";
/*     */   }
/*     */ 
/*     */   public String func_26599_getString(String var1, String var2)
/*     */   {
/* 464 */     if (func_26610_containsKey(var1))
/*     */     {
/* 466 */       return func_26615_getProperty(var1);
/*     */     }
/*     */ 
/* 470 */     func_26616_setString(var1, var2);
/* 471 */     return var2;
/*     */   }
/*     */ 
/*     */   public void func_26616_setString(String var1, String var2)
/*     */   {
/* 477 */     this.field_26619_props.put(var1, var2);
/* 478 */     func_26596_save();
/*     */   }
/*     */ 
/*     */   public int getInt(String var1)
/*     */   {
/* 483 */     return func_26602_getInt(var1);
/*     */   }
/*     */ 
/*     */   public int func_26602_getInt(String var1)
/*     */   {
/* 488 */     return func_26610_containsKey(var1) ? Integer.parseInt(func_26615_getProperty(var1)) : 0;
/*     */   }
/*     */ 
/*     */   public int getInt(String var1, int var2)
/*     */   {
/* 493 */     return func_26608_getInt(var1, var2);
/*     */   }
/*     */ 
/*     */   public int func_26608_getInt(String var1, int var2)
/*     */   {
/* 498 */     if (func_26610_containsKey(var1))
/*     */     {
/* 500 */       return Integer.parseInt(func_26615_getProperty(var1));
/*     */     }
/*     */ 
/* 504 */     func_26603_setInt(var1, var2);
/* 505 */     return var2;
/*     */   }
/*     */ 
/*     */   public void func_26603_setInt(String var1, int var2)
/*     */   {
/* 511 */     this.field_26619_props.put(var1, String.valueOf(var2));
/* 512 */     func_26596_save();
/*     */   }
/*     */ 
/*     */   public double func_26605_getDouble(String var1)
/*     */   {
/* 517 */     return func_26610_containsKey(var1) ? Double.parseDouble(func_26615_getProperty(var1)) : 0.0D;
/*     */   }
/*     */ 
/*     */   public double func_26607_getDouble(String var1, double var2)
/*     */   {
/* 522 */     if (func_26610_containsKey(var1))
/*     */     {
/* 524 */       return Double.parseDouble(func_26615_getProperty(var1));
/*     */     }
/*     */ 
/* 528 */     func_26609_setDouble(var1, var2);
/* 529 */     return var2;
/*     */   }
/*     */ 
/*     */   public void func_26609_setDouble(String var1, double var2)
/*     */   {
/* 535 */     this.field_26619_props.put(var1, String.valueOf(var2));
/* 536 */     func_26596_save();
/*     */   }
/*     */ 
/*     */   public long func_26598_getLong(String var1)
/*     */   {
/* 541 */     return func_26610_containsKey(var1) ? Long.parseLong(func_26615_getProperty(var1)) : 0L;
/*     */   }
/*     */ 
/*     */   public long func_26613_getLong(String var1, long var2)
/*     */   {
/* 546 */     if (func_26610_containsKey(var1))
/*     */     {
/* 548 */       return Long.parseLong(func_26615_getProperty(var1));
/*     */     }
/*     */ 
/* 552 */     func_26617_setLong(var1, var2);
/* 553 */     return var2;
/*     */   }
/*     */ 
/*     */   public void func_26617_setLong(String var1, long var2)
/*     */   {
/* 559 */     this.field_26619_props.put(var1, String.valueOf(var2));
/* 560 */     func_26596_save();
/*     */   }
/*     */ 
/*     */   public boolean func_26612_getBoolean(String var1)
/*     */   {
/* 565 */     return func_26610_containsKey(var1) ? Boolean.parseBoolean(func_26615_getProperty(var1)) : false;
/*     */   }
/*     */ 
/*     */   public boolean func_26597_getBoolean(String var1, boolean var2)
/*     */   {
/* 570 */     if (func_26610_containsKey(var1))
/*     */     {
/* 572 */       return Boolean.parseBoolean(func_26615_getProperty(var1));
/*     */     }
/*     */ 
/* 576 */     func_26601_setBoolean(var1, var2);
/* 577 */     return var2;
/*     */   }
/*     */ 
/*     */   public void func_26601_setBoolean(String var1, boolean var2)
/*     */   {
/* 583 */     this.field_26619_props.put(var1, String.valueOf(var2));
/* 584 */     func_26596_save();
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEProps
 * JD-Core Version:    0.6.2
 */