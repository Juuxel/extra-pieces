package com.shnupbups.extrapieces.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.register.ModBlocks;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EPConfig {

	public static EPConfig config;
	private static int setsNum = 0;

	public static void init() {
		File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), "extrapieces.json");
		try (FileReader reader = new FileReader(configFile)) {
			System.out.println("[Extra Pieces] Loading config!");
			JsonObject cfg = Jankson.builder().build().load(configFile);
			JsonObject sets = cfg.getObject("sets");
			for (String s : sets.keySet()) {
				JsonObject jsonSet = (JsonObject) sets.get(s);
				PieceSet setPieceSet = PieceSet.fromJson(s, jsonSet);
				setPieceSet.register();
				setsNum++;
			}
			System.out.println("[Extra Pieces] Registered "+setsNum+" PieceSets!");
		} catch (IOException e) {
			System.out.println("[Extra Pieces] No config found, generating!");
			generate(configFile);
		} catch (SyntaxError e) {
			System.out.println("[Extra Pieces] Config has invalid syntax!");
		}
	}

	public static void generate(File config) {
		ModBlocks.generateDefaultSets();
		try (FileWriter writer = new FileWriter(config)) {
			JsonObject cfg = new JsonObject();
			JsonObject sets = new JsonObject();
			for (PieceSet set : PieceSets.registry.values()) {
				if (set != ModBlocks.DUMMY_PIECES)
					sets.put(set.getName(), set.toJson());
			}
			cfg.put("sets", sets);
			writer.write(cfg.toJson(false, true));
		} catch (IOException e2) {
			System.out.println("[Extra Pieces] Failed to write to config file!");
		}
	}

}