package com.nextplugins.cash.api.group.impl;

import com.nextplugins.cash.NextCash;
import com.nextplugins.cash.api.group.Group;
import com.nextplugins.cash.api.group.GroupWrapper;
import lombok.val;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class VaultGroupWrapper implements GroupWrapper {

    private Chat chatApi;
    private Permission permissionApi;

    @Override
    public Group getGroup(String player) {
        if (chatApi == null) return new Group();

        try {
            val prefix = chatApi.getPlayerPrefix(Bukkit.getWorlds().get(0), player);
            val suffix = chatApi.getPlayerSuffix(Bukkit.getWorlds().get(0), player);
            return new Group(prefix, suffix);
        } catch (Exception exception) {
            // boa vault !!!
        }

        if (permissionApi == null) return new Group();

        try {
            val primaryGroup = permissionApi.getPrimaryGroup(Bukkit.getWorlds().get(0), player);
            val prefix = chatApi.getGroupPrefix(Bukkit.getWorlds().get(0), primaryGroup);
            val suffix = chatApi.getGroupSuffix(Bukkit.getWorlds().get(0), primaryGroup);

            return new Group(prefix, suffix);
        } catch (Exception exception) {
            return new Group();
        }
    }

    @Override
    public void setup() {
        setupChat();
        setupPermission();
    }

    private void setupPermission() {

        RegisteredServiceProvider<Permission> permissionProvider =
                NextCash.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider == null) return;

        permissionApi = permissionProvider.getProvider();
    }

    private void setupChat() {

        RegisteredServiceProvider<Chat> chatProvider =
                NextCash.getInstance().getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider == null) return;

        chatApi = chatProvider.getProvider();
    }
}
