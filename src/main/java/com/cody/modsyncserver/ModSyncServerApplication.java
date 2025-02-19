package com.cody.modsyncserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.function.Consumer;

@SpringBootApplication
public class ModSyncServerApplication {
	public static File modsDir = new File(System.getProperty("user.dir"), "client_mods");
	public static File modSyncClientDir = new File(System.getProperty("user.dir"), "mod_sync_client");
	public static File modSyncClientJar;

	public static void main(String[] args) {
		SpringApplication.run(ModSyncServerApplication.class, args);
		if (!modsDir.exists() || !modsDir.isDirectory()) {
			if(!modsDir.mkdirs()) {
				System.out.println("Mods directory doesn't exist. Failed to create mods directory.");
				System.exit(1);
			}
		}
		String found = findClientJar();
		if (found != null) {
			System.out.println(found);
			System.exit(1);
		}
	}

	// Returns null if successful, otherwise returns an error message
	public static String findClientJar() {
		if (!modSyncClientDir.exists() || !modSyncClientDir.isDirectory()) {
			if(!modSyncClientDir.mkdirs()) {
				return "Mod Sync Client directory doesn't exist. Failed to create mod_sync_client directory.";
			}
			// TODO: Automatic download of latest
			return "Mod Sync Client directory is empty.";
		}
		File[] modSyncClientFiles = modSyncClientDir.listFiles();
		if (modSyncClientFiles == null) {
			return "Failed to list mod_sync_client directory.";
		}
		if (modSyncClientFiles.length != 1) {
			return "There should be exactly one file in the mod_sync_client directory.";
		}
		modSyncClientJar = modSyncClientFiles[0];
		if (modSyncClientJar.isDirectory()) {
			return "The file in the mod_sync_client directory should be a JAR file.";
		}
		if (!modSyncClientJar.exists()) {
			return "Failed to find the file in the mod_sync_client directory.";
		}
		if (!modSyncClientJar.getName().endsWith(".jar")) {
			return "The file in the mod_sync_client directory should be a JAR file.";
		}
		return null;

	}

}
