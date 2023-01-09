package com.nextplugins.cash.ranking;

import com.nextplugins.cash.NextCash;
import com.nextplugins.cash.api.model.Account;
import com.nextplugins.cash.util.text.NumberUtil;
import com.nextplugins.libs.hologramwrapper.Holograms;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class RankingHologram {

    private final Holograms holograms;
    private final RankingLocations locations;

    public RankingHologram(NextCash instance, RankingLocations locations) {
        this.holograms = Holograms.get(instance);
        this.locations = locations;
    }

    public void update(List<Account> accounts) {
        if (holograms == null) return;

        clear();

        final Location location = locations.getLocation(1);
        if (location == null) return;

        final Location baseLocation = location.clone().add(0, (12 * 0.3), 0);

        final List<String> lines = new ArrayList<>();

        lines.add("&6&lRANKING DE CHICLETES");
        lines.add("&7Atualizado a cada 10 minutos");
        lines.add("");

        for (Account account : accounts) {
            System.out.println(account.getOwner() + " - " + NumberUtil.letterFormat(account.getBalance()));
        }

        for (int index = 0; index < accounts.size(); index++) {
            final Account account = accounts.get(index);
            final int position = index + 1;

            lines.add("&e&l" + position + "º &8-&7 " + account.getOwner() + " &8-&6 ✿&e"
                    + NumberUtil.letterFormat(account.getBalance()));
        }

        lines.add("");

        holograms.create(baseLocation, lines);
    }

    public void clear() {
        if (holograms != null) holograms.clear();
    }
}
