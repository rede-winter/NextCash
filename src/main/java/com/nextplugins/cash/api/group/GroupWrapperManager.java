package com.nextplugins.cash.api.group;

import com.nextplugins.cash.NextCash;
import com.nextplugins.cash.api.group.impl.VaultGroupWrapper;
import lombok.val;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public final class GroupWrapperManager {

    private GroupWrapper wrapper;

    public void init() {
        wrapper = new VaultGroupWrapper();

        val logger = NextCash.getInstance().getLogger();
        if (wrapper == null) {
            logger.warning("[Grupos] Não foi encontrado nenhum plugin de grupos compatível.");
            return;
        }

        wrapper.setup();
        logger.info("[Grupos] Integrado com sucesso com o plugin '"
                + wrapper.getClass().getSimpleName() + "'");
    }

    public Group getGroup(String playerName) {
        if (wrapper == null) return new Group();

        return wrapper.getGroup(playerName);
    }
}
