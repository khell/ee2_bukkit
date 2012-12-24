ee2_bukkit
==========

EE2 v1.4.6.5 for Bukkit by cpw disassembled.

#### Purpose
The purpose of this disassembly is to produce a modified EEMaps class file. I discovered a player exploiting stone slabs to generate EMC, which would have been impossible to resolve without raising the EMC of stone and cobblestone (using TekkitRestrict) as it is impossible to completely remove EMC. Thus this modification to the EE2 base became necessary. It modifies the methods EEMaps.addEMC(int, int, int) and EEMaps.InitEERecipes(). It adds the method EEMaps.canAddEmc(int, int). These methods' bytecode should replace and be inserted into the existing EEMaps.class file. Do not attempt to replace EE2 entirely with this, it is unlikely to work, and certain things will be broken even if it does.

#### Changes	
* Introduces a feature to disable the EMC values of given items through the mod_EE.props file. Example line: "NoEMC=1,2,351:15,16". This will affect all plugins that change EMC values (e.g. TekkitRestrict), ignoring them completely.
* Disables recipes to use the Zero Ring, Ring of Arcana and Evertide Amulet to mass produce water buckets and ice.