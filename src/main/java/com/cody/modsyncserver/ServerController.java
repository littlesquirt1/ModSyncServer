package com.cody.modsyncserver;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

import static com.cody.modsyncserver.ModSyncServerApplication.*;

@RestController
public class ServerController {
    @GetMapping(value = "/modlist", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getModList() {
        if (!modsDir.exists() || !modsDir.isDirectory()) {
            if(!modsDir.mkdirs()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mods directory doesn't exist. Failed to create mods directory.");
            }
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        String[] mods = modsDir.list();
        if (mods == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to list mods directory.");
        }
        return ResponseEntity.ok(String.join("/", mods));
    }

    @GetMapping(value = "/mod_sync_jar_name", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getModSyncJarName() {
        String found = findClientJar();
        if (found != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(found);
        }
        return ResponseEntity.ok(modSyncClientJar.getName());
    }

    @GetMapping(value = "/mod_sync_jar")
    public ResponseEntity<?> getModSyncJar() {
        String found = findClientJar();
        if (found != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(found);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + modSyncClientJar.getName())
                .body(new FileSystemResource(modSyncClientJar));
    }

}
