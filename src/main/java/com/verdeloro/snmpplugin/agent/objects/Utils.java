package com.verdeloro.snmpplugin.agent.objects;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

public class Utils {


    public int getWorldSize()
    {
        long bytes = 0;
        for (World world : Bukkit.getWorlds()) {
            bytes += this.getFolderSize(world.getWorldFolder());

        }
        return (int) (bytes / (1024*1024));
    }
    public int getWorldSize(World.Environment env)
    {
        long bytes = 0;
        for (World world : Bukkit.getWorlds()) {
            if (world.getEnvironment() == env) {
                bytes = this.getFolderSize(world.getWorldFolder());
                break;
            }

        }
        return (int) (bytes / (1024*1024));
    }
    public long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();

        int count = files.length;

        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            }
            else {
                length += getFolderSize(files[i]);
            }
        }
        return length;
    }
}
