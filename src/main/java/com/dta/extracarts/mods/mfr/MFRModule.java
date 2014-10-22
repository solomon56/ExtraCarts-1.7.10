package com.dta.extracarts.mods.mfr;

import com.dta.extracarts.ExtraCarts;
import com.dta.extracarts.ModInfo;
import com.dta.extracarts.Module;
import com.dta.extracarts.mods.mfr.entities.EntityDSUCart;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * Created by Skylar on 10/17/2014.
 */
public class MFRModule extends Module {
	@Override
    public String getModuleName() {
        return "MFR";
    }

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("MineFactoryReloaded")) {
			System.out.println("MFR is loaded");
			MFRItems.init();
			MFRItems.registerItems();
			MFRItems.registerRecipes();
			EntityRegistry.registerModEntity(EntityDSUCart.class, "EntityDSUCart", 9, ExtraCarts.instance, 80, 3, true);
		}
	}
}